package org.example.mail;

/**
 * Stocke les informations d'un mail
 *
 * @param mailFrom Section "MAIL FROM:" du mail
 * @param rcptTo   Section "RCPT TO:" du mail
 * @param from     Section "From:" du mail dans la partie Data
 * @param to       Section "To:" du mail dans la partie Data
 * @param subject  Section objet du mail dans la partie Data
 * @param content  Section contenu du mail dans la partie Data
 * @author Dorian Gillioz & Oscar Baume
 */
public record Mail(String mailFrom, String[] rcptTo, String from, String[] to, String subject, String content) {
    /**
     * Constructeur du mail
     *
     * @param mailFrom Section "MAIL FROM:" du mail
     * @param rcptTo   Section "RCPT TO:" du mail
     * @param from     Section "From:" du mail dans la partie Data
     * @param to       Section "To:" du mail dans la partie Data
     * @param subject  Section objet du mail dans la partie Data
     * @param content  Section contenu du mail dans la partie Data
     */
    public Mail {
    }

}
