package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.DAO.OtpDao;
import com.BKHOSTEL.BKHOSTEL.DAO.UserDao;
import com.BKHOSTEL.BKHOSTEL.Dto.EmailDetailDto;
import com.BKHOSTEL.BKHOSTEL.Dto.GenerateOtpRequestDto;
import com.BKHOSTEL.BKHOSTEL.Entity.Otp;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Exception.InvalidRequestException;
import com.BKHOSTEL.BKHOSTEL.Exception.NotFoundException;
import com.BKHOSTEL.BKHOSTEL.Exception.OtpException;
import com.BKHOSTEL.BKHOSTEL.Service.Client.EmailService;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;

@Service
public class ForgotPasswordService {
    @Value("${OTP_EXPIRATION}")
    private long OTP_EXPIRATION;

    private EmailService emailServiceImpl;

    private UserDao userDaoImpl;

    private OtpDao otpDaoImpl;

    private JwtTokenService jwtTokenService;

    private PasswordEncoder encoder;

    @Autowired
    public ForgotPasswordService(EmailService emailServiceImpl, UserDao userDaoImpl, OtpDao otpDaoImpl, JwtTokenService jwtTokenService, PasswordEncoder encoder) {
        this.emailServiceImpl = emailServiceImpl;
        this.userDaoImpl = userDaoImpl;
        this.otpDaoImpl = otpDaoImpl;
        this.jwtTokenService = jwtTokenService;
        this.encoder = encoder;
    }

    @Transactional
    public void SendOtpByEmail(Otp otp,String email) throws IOException {

        EmailDetailDto emailDetailDto = new EmailDetailDto();
        emailDetailDto.setRecipient(email);
        emailDetailDto.setSubject("Otp for reset password");
        ClassPathResource resource = new ClassPathResource("templates/generate-otp-template.html");
        InputStream inputStream = resource.getInputStream();
        String htmlContent;
        try (Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")) {
            htmlContent= scanner.hasNext() ? scanner.next() : "";
        }

        htmlContent=htmlContent.replace("{User}", emailDetailDto.getRecipient());
        htmlContent=htmlContent.replace("{OTP}",otp.getCode());
        emailDetailDto.setMsgBody(htmlContent);
        emailServiceImpl.sendEmailWithHtmlContent(emailDetailDto);

    }



    public static String readHtmlTemplateFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }
    @Transactional
    public Otp generateOtp(String identifier, String userName) throws IOException{
        User user = userDaoImpl.findByUserName(userName);
        if (user == null){
            throw new NotFoundException("User not found");
        }
        if(!user.getEmail().equals(identifier)){
            throw new InvalidRequestException("Email mismatch with username");
        }

        String code = String.valueOf((int) ((Math.random() * (999999 - 100000)) + 100000));
        Otp otp;

        if (user.getOtp()!=null){
            otp=user.getOtp();
        } else{
            otp=new Otp();
        }

        otp.setCode(code);
        otp.setExpiredDate(
                new Date(System.currentTimeMillis()+ OTP_EXPIRATION)
        );

        otpDaoImpl.save(otp);
        user.setOtp(otp);
        userDaoImpl.save(user);
        SendOtpByEmail(otp,identifier);
        return otp;
    }


    public String verifyOtp(String otpCode,String identifier){
        User user =userDaoImpl.findByEmail(identifier);
        if (user == null){
            throw new NotFoundException("Email not found");
        }

        Otp otp =user.getOtp();
        if(!otpCode.equals(otp.getCode())){
            throw new InvalidRequestException("otp is not correct");
        }
        if(otp.getExpiredDate().getTime()<System.currentTimeMillis()){
            throw new OtpException("Otp was expired");
        }

        if(!user.getEmail().equals(identifier)){
            throw new OtpException("Otp mismatch with identifier");
        }
        String token =jwtTokenService.generateToken(user);
        return token;


    }
    @Transactional
    public String resetPassword(String newPassword) {
        User user=UserService.getUserByAuthContext();
        user.setPassword(encoder.encode(newPassword));
        userDaoImpl.save(user);
        return "New password was updated";
    }

}
