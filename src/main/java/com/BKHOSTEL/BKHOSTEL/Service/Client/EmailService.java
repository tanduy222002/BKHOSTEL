package com.BKHOSTEL.BKHOSTEL.Service.Client;


import com.BKHOSTEL.BKHOSTEL.Dto.EmailDetailDto;

public interface EmailService {
    void sendEmail(EmailDetailDto emailDetailDto);
    void SendEmailWithAttachments(EmailDetailDto emailDetailDto);

    void sendEmailWithHtmlContent(EmailDetailDto emailDetailDto);
}
