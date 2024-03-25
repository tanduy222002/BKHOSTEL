package com.BKHOSTEL.BKHOSTEL.Service.Client;
import com.BKHOSTEL.BKHOSTEL.DAO.RechargeDao;
import com.BKHOSTEL.BKHOSTEL.DAO.UserDao;
import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import com.BKHOSTEL.BKHOSTEL.Exception.RechargeFailException;
import com.BKHOSTEL.BKHOSTEL.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RechargeServiceImpl implements RechargeService {
    private RechargeDao rechargeDaoImpl;
    private UserDao userDaoImpl;

    @Autowired
    public RechargeServiceImpl(RechargeDao rechargeDaoImpl, UserDao userDaoImpl) {
        this.rechargeDaoImpl = rechargeDaoImpl;
        this.userDaoImpl = userDaoImpl;
    }




    @Transactional
    public String createRechargeLog(String amount){
        User user=UserService.getUserByAuthContext();
        Recharge recharge = new Recharge(new Date(),
                amount,
                "Nap tien vao tai khoan KH: " + user.getId() +" " + amount+"VND",
                "PENDING",
                user);
        recharge=rechargeDaoImpl.save(recharge);
        return recharge.getId().toString();

    }

    @Override
    public String processRecharge(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        int amount = Integer.parseInt(req.getParameter("amount"));
        String vnp_TxnRef = createRechargeLog(String.valueOf(amount));
        String vnp_OrderInfo = "Thanh toan GD OrderId:" + vnp_TxnRef;
        String vnp_IpAddr = VnpConfig.getIpAddress(req);
        String vnp_TmnCode = VnpConfig.vnp_TmnCode;
        amount=amount*100;
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = req.getParameter("bankcode");
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VnpConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Date d = new Date();
        System.out.println("crt: "+d.getTime());
        SimpleDateFormat sdf1= new SimpleDateFormat("yyyyMMddHHmmss");
        sdf1.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String vnp_CreateDate = sdf1.format(d);
        System.out.println(vnp_CreateDate);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);



//Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpConfig.hmacSHA512(VnpConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnpConfig.vnp_PayUrl + "?" + queryUrl;
        System.out.println("PaymentUrl: " + paymentUrl);
        return paymentUrl;
    }
    @Override
    public String rechargeReturnUrl(HttpServletRequest req, HttpServletResponse res){
        Map fields = new HashMap();
        for (Enumeration params = req.getParameterNames(); params.hasMoreElements();) {
            String fieldName = (String) params.nextElement();
            String fieldValue = req.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = req.getParameter("vnp_SecureHash");

        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }

            if ("00".equals(req.getParameter("vnp_ResponseCode"))) {

                return "recharge_success";
            } else {
                return "recharge_failure";
            }


    }

    @Override
    public void saveRecharge(HttpServletRequest req, HttpServletResponse res) {
        Map fields = new HashMap();
        for (Enumeration params = req.getParameterNames(); params.hasMoreElements();) {
            String fieldName = (String) params.nextElement();
            String fieldValue = req.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0))
            {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = req.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType"))
        {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash"))
        {
            fields.remove("vnp_SecureHash");
        }

        System.out.println(req.getParameter("vnp_ResponseCode"));

        // Check checksum


        Recharge recharge=rechargeDaoImpl.findById(req.getParameter("vnp_TxnRef"));
        if(recharge==null)
            throw new RechargeFailException("Recharge not found");
        Integer amount= Integer.parseInt(req.getParameter("vnp_Amount"))/100;
        if (!recharge.getAmount().equals(String.valueOf(amount))){
            throw new RechargeFailException("Recharge amount wrong");
        }
        if ("00".equals(req.getParameter("vnp_ResponseCode"))){
            recharge.setStatus("SUCCESS");
        }else
            recharge.setStatus("FAILURE");
        User user=recharge.getUser();
        Double newBalance=user.getBalance()+(double)amount;
        user.setBalance(newBalance);
        rechargeDaoImpl.save(recharge);
        userDaoImpl.save(user);
    }
}
