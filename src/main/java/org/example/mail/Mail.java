package org.example.mail;

public class Mail {
    private String mailFrom;
    private String rcptTo[];
    private String from;
    private String to[];
    private String subject;
    private String content;

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
