package org.example;

public class Message {
    private Person mailFrom;
    private Person rcptTo[];
    private Person from;
    private Person to[];
    private String subject;
    private String content;

    public Message(Person mailFrom, Person[] rcptTo, Person from, Person[] to, String subject, String content) {
        this.mailFrom = mailFrom;
        this.rcptTo = rcptTo;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }
}
