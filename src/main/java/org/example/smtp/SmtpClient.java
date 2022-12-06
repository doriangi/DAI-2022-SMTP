package org.example.smtp;

import org.example.mail.Mail;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe qui permet la connexion au serveur SMTP donné en paramètre
 * @author Dorian Gillioz & Oscar Baume
 */
public class SmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    /** Adresse du serveur SMTP */
    private final String address;
    /** Port du serveur SMTP */
    private final int port;

    /** Socket pour se connecter au serveur */
    private Socket socket = null;
    /** Lit les réponses du serveur */
    private BufferedReader reader = null;
    /** Écrit au serveur*/
    private BufferedWriter writer = null;

    /**
     * Constructeur du SmtpClient avec les informations pour se connecter au serveur
     * @param address Adresse du serveur SMTP
     * @param port    Port du serveur SMTP
     */
    public SmtpClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Permet d'envoyer un mail passé en paramètre au serveur,
     * la fonction va aussi gérer la connexion au serveur
     * @param mail Le mail à envoyer au serveur
     */
    public void sendMail(Mail mail) {
        try {
            socket = new Socket(address, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            String read = reader.readLine();
            System.out.println(read);
            send("EHLO " + address + "\r\n");
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
        } catch (IOException | RuntimeException e) {
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
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Envoie le message passé en paramètre au serveur
     * @param message Message à envoyer
     * @throws RuntimeException en cas d'erreur d'envoi de message
     */
    private void send(String message) {
        try {
            System.out.print(message);
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
