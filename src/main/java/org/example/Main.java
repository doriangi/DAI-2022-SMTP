package org.example;

import org.example.mail.Mail;
import org.example.smtp.SmtpClient;

public class Main {
    public static void main(String[] args) {
        String mailFrom = "gilliozdorian@gmail.com";
        String[] rcptTo = { "dorian.gillioz@heig-vd.ch", "super@mail.com" };
        String from = "doriancool@gmail.com";
        String[] to = { "gilliozdorian@gmail.com", "personne@mail.com" };
        String subject = "Bonjour";
        String content = "Bonjour, voici mon mail\nbonne journée";

        Mail mail = new Mail(mailFrom, rcptTo, from, to, subject, content);
        SmtpClient client = new SmtpClient("localhost", 25);

        client.sendMail(mail);
    }
}