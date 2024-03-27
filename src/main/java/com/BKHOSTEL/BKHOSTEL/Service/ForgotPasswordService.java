package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.DAO.OtpDao;
import com.BKHOSTEL.BKHOSTEL.DAO.UserDao;
import com.BKHOSTEL.BKHOSTEL.Dto.EmailDetailDto;
import com.BKHOSTEL.BKHOSTEL.Dto.GenerateOtpRequestDto;
import com.BKHOSTEL.BKHOSTEL.Entity.Otp;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Exception.NotFoundException;
import com.BKHOSTEL.BKHOSTEL.Exception.OtpException;
import com.BKHOSTEL.BKHOSTEL.Service.Client.EmailService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

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
    public ForgotPasswordService(EmailService emailServiceImpl, UserDao userDaoImpl, OtpDao otpDaoImpl, JwtTokenService jwtTokenService) {
        this.emailServiceImpl = emailServiceImpl;
        this.userDaoImpl = userDaoImpl;
        this.otpDaoImpl = otpDaoImpl;
        this.jwtTokenService = jwtTokenService;
    }

    @Transactional
    public Otp generateOtpAndSendByEmail(GenerateOtpRequestDto otpRequest) throws Exception{
        Otp otp = generateOtp(otpRequest.getIdentifier());
        EmailDetailDto emailDetailDto = new EmailDetailDto();
        emailDetailDto.setRecipient(otpRequest.getIdentifier());
        emailDetailDto.setSubject("Otp for reset password");
        // Read the HTML template into a String variable
        Path currentPath = Paths.get( "src", "main", "resources","templates", "generate-otp-template.html");
        String absolutePath = currentPath.toAbsolutePath().toString();
        String htmlContent = readHtmlTemplateFromFile(absolutePath);
        htmlContent=htmlContent.replace("{User}", emailDetailDto.getRecipient());
        htmlContent=htmlContent.replace("{OTP}",otp.getCode());
        emailDetailDto.setMsgBody(htmlContent);
        emailServiceImpl.sendEmailWithHtmlContent(emailDetailDto);
        return otp;

    }



    public static String readHtmlTemplateFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }
    @Transactional
    public Otp generateOtp(String email){
        User user = userDaoImpl.findByEmail(email);
        if (user == null){
            throw new NotFoundException("Email not found");
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
        return otp;
    }

    public String verifyOtp(String otpCode,String identifier){
        User user =userDaoImpl.findByEmail(identifier);
        if (user == null){
            throw new NotFoundException("Email not found");
        }
        Otp otp =user.getOtp();
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
    public String resetPassword(String newPassword, @NotBlank(message = "Otp code must not be blank") String code, @NotBlank(message = "Identifier that used to get otp must not be blank") String identifier) {
        User user=UserService.getUserByAuthContext();
        user.setPassword(encoder.encode(newPassword));
        userDaoImpl.save(user);
        return "New password was updated";
    }

}
