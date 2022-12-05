package org.example.prank;

import org.example.FileReader;
import org.example.mail.Mail;
import org.example.smtp.SmtpClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Permet la création de groupes de victimes et la préparation d'un mail piégé à envoyer
 */
public class PrankManager {

    /** La liste des groupes à piéger */
    private final List<List<String>> groups = new ArrayList<>();
    /** Le nombre de groupes à créer */
    private final int size;
    /** Le file reader qui va permettre les actions d'ouverture de fichiers */
    private final FileReader fr = new FileReader();

    /**
     * Initialise le PrankGenerator avec une taille donnée
     * @param size Le nombre de groupes à créer
     */
    public PrankManager(int size) {
        this.size = size;
    }

    /**
     * Ajoute un groupe dans la campagne de prank
     * @param victimFilePath Le chemin du fichier qui contient les victimes
     * @param nbrVictims     Le nombre de victimes que l'on veut piéger dans ce groupe
     */
    public void addGroup(String victimFilePath, int nbrVictims) {
        if (groups.size() >= size) return;
        File v = new File(victimFilePath);
        List<String> victims = fr.readVictims(v, nbrVictims);
        groups.add(victims);
    }

    /**
     * Récupère le groupe à l'id donné en paramètre
     * @param i L'id du groupe
     * @return Le groupe correspondant
     */
    public List<String> getGroup(int i) {
        return groups.get(i);
    }

    /**
     * Prépare le mail piège du groupe en paramètre
     * @param group         Le groupe à piéger
     * @param msgFilePath   Le chemin qui contient la liste des messages à envoyer
     * @return Un objet Mail qui contient toutes les infos du groupe ainsi que le message à envoyer
     */
    public Mail setPrankMail(List<String> group, String msgFilePath) {
        String from = group.get(0);
        String[] to = new String[group.size() - 1];
        System.arraycopy(group.toArray(new String[0]), 1, to, 0, group.size() - 1);

        File msg = new File(msgFilePath);
        String[] subject_msg = fr.readMessage(msg);
        String subject = subject_msg[0];
        String content = subject_msg[1];

        return new Mail(from, group.toArray(new String[0]), from, to, subject, content);
    }

    /**
     * Permet de se créer un client smtp au serveur Mail en fonction des configurations données
     * @param configFilePath Chemin du fichier config qui contient l'adresse et le port du serveur
     * @return Un Smtp Client qui permettra l'envoi du mail
     */
    public SmtpClient client(String configFilePath) {
        File configs = new File(configFilePath);
        String address = fr.readAddress(configs);
        int port = fr.readPort(configs);
        return new SmtpClient(address, port);
    }
}
