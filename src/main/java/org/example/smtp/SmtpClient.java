package org.example.smtp;

import org.example.mail.Mail;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private String address;
    private int port;

    private Socket socket = null;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    public SmtpClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void sendMail(Mail mail) {
        try {
            socket = new Socket(address, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            String read = reader.readLine();
            System.out.println(read);
            send("EHLO localhost\r\n");
            do {
                read = reader.readLine();
                System.out.println(read);
                // To detect if we printed all the params
            } while (!read.split(" ")[0].equals("250"));

            send("MAIL FROM: " + mail.getMailFrom() + "\r\n");
            read = reader.readLine();
            System.out.println(read);

            for (String person : mail.getRcptTo()) {
                send("RCPT TO: " + person + "\r\n");
                read = reader.readLine();
                System.out.println(read);
            }

            send("DATA\r\n");
            read = reader.readLine();
            System.out.println(read);

            send("Content-Type: text/plain; charset=\"utf-8\"\r\n");
            send("From: " + mail.getFrom() + "\r\n");

            StringBuilder to = new StringBuilder();
            to.append("To: ");
            to.append(mail.getTo()[0]);
            for (int i = 1; i < mail.getTo().length; ++i) {
                to.append(", ").append(mail.getTo()[i]);
            }
            to.append("\r\n");
            send(to.toString());

            send("Subject: " + mail.getSubject() + "\r\n");

            send("\r\n" + mail.getContent() + "\r\n.\r\n");
            read = reader.readLine();
            System.out.println(read);

            socket.close();
            reader.close();
            writer.close();
        } catch (IOException e) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    LOG.log(Level.SEVERE, e1.getMessage(), e1);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e2) {
                    LOG.log(Level.SEVERE, e2.getMessage(), e2);
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e3) {
                    LOG.log(Level.SEVERE, e3.getMessage(), e3);
                }
            }
        }
    }

    /**
     * Envoie le message passé en paramètre au serveur
     * @param message Message à enoyer
     */
    private void send(String message) {
        try {
            System.out.print(message);
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
