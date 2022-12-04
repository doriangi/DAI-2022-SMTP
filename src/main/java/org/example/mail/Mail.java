package org.example.mail;

import java.util.List;

/**
 * Stocke les informations d'un mail
 */
public class Mail {
    private String mailFrom;
    private String[] rcptTo;
    private String from;
    private String[] to;
    private String subject;
    private String content;

    /**
     * Constructeur du mail
     * @param mailFrom  Section "MAIL FROM:" du mail
     * @param rcptTo    Section "RCPT TO:" du mail
     * @param from      Section "From:" du mail dans la partie Data
     * @param to        Section "To:" du mail dans la partie Data
     * @param subject   Section objet du mail dans la partie Data
     * @param content   Section contenu du mail dans la partie Data
     */
    public Mail(String mailFrom, String[] rcptTo, String from, String[] to, String subject, String content) {
        this.mailFrom = mailFrom;
        this.rcptTo = rcptTo;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public String[] getRcptTo() {
        return rcptTo;
    }

    public String getFrom() {
        return from;
    }

    public String[] getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}
