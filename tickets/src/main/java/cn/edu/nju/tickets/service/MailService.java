package cn.edu.nju.tickets.service;

public interface MailService {

    void sendMail(String to, String subject, String content);

}
