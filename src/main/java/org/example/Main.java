package org.example;

import org.example.mail.Mail;
import org.example.smtp.SmtpClient;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        FileReader fr = new FileReader();
        File victims = new File("C:\\Users\\gilli\\OneDrive\\Documents\\DAI\\DAI-2022-SMTP\\config\\victims\\victims.txt");
        String[] group = fr.readVictims(victims, 4).toArray(new String[0]);

        String from = group[0];
        String[] to = new String[group.length - 1];

        System.arraycopy(group, 1, to, 0, group.length - 1);

        String subject = "Bonjour";
        String content = "Bonjour, voici mon mail\nbonne journée";

        Mail mail = new Mail(from, group, from, to, subject, content);

        File configs = new File("C:\\Users\\gilli\\OneDrive\\Documents\\DAI\\DAI-2022-SMTP\\config\\options\\params.txt");
        String address = fr.readAddress(configs);
        int port = fr.readPort(configs);
        SmtpClient client = new SmtpClient(address, port);

        client.sendMail(mail);
    }
}