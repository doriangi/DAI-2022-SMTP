package org.example;

import org.example.mail.Group;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe qui permet de lire le contenu de fichiers pour préparer le prank
 */
public class FileReader {
    private static final Logger LOG = Logger.getLogger(FileReader.class.getName());

    /**
     * Lit la liste des victimes du fichier et en choisit un nombre de victimes (>= 3) donné,
     * la première victime sera passée pour l'envoyeur
     * @param file Le fichier de victimes
     * @param nbr_victims Le nombre de victimes à piéger
     *                    Note : doit être >= 3
     * @return La liste des victimes
     */
    public List<String> readVictims(File file, int nbr_victims) {
        List<String> all_emails = getStrings(file);     // Liste de tous les emails
        StringBuilder error = new StringBuilder();  // Contient les emails erronés

        if (nbr_victims < 3) {
            throw new RuntimeException("Le nombre de victimes doit être >= 3\n");
        }
        if (all_emails.size() < nbr_victims) {
            throw new RuntimeException("Le nombre de victimes voulu est inférieur au nombre de victimes disponibles\n");
        }

        for (String email : all_emails) {
            // Vérifie que les emails sont corrects, sinon les ajoute dans les emails erronés
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                error.append(email).append("\n");
            }
        }

        if (!error.isEmpty()) {
            throw new RuntimeException("Le(s) email(s) suivant(s) n'est (sont) pas correct(s) :\n" + error);
        }

        List<String> victims = new ArrayList<>();
        Random rdm = new Random();
        String victim;
        // Séléctionne nbr_victims au hasard parmis les victimes possibles
        while (victims.size() < nbr_victims) {
            victim = all_emails.get(rdm.nextInt(all_emails.size()));
            if(!victims.contains(victim)) victims.add(victim);
        }

        return victims;
    }

    /**
     * Récupère l'adresse du serveur stocké dans le fichier config
     * Note : pour indiquer l'adresse dans le fichier, il faut noter ip=...
     * @param file Fichier config contenant l'adresse
     * @return L'adresse du serveur, un string vide si adresse non-trouvée
     */
    public String readAddress(File file) {
        List<String> params = getStrings(file);
        String[] split;
        for (String param : params) {
            // On sépare le message avec le = pour savoir s'il s'agit du champ ip qu'on lit
            split = param.split("=");
            if (split[0].equals("ip")) return split[1];
        }
        return "";
    }

    /**
     * Récupère le port du serveur donné dans le fichier config
     * Note : pour indiquer le port dans le fichier, il faut noter port=...
     * @param file Fichier config contenant le port
     * @return Le port du serveur et -1 si le port n'a pas été trouvé
     */
    public int readPort(File file) {
        List<String> params = getStrings(file);
        String[] split;
        for (String param : params) {
            split = param.split("=");
            if (split[0].equals("port")) return Integer.parseInt(split[1]);
        }
        return -1;
    }

    /**
     * Lit un fichier ligne par ligne
     * @param file le fichier à lire
     * @return Une liste de string avec une valeur par ligne du fichier
     */
    private List<String> getStrings(File file) {
        List<String> params = new ArrayList<>();

        try (FileInputStream input = new FileInputStream(file);
             InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            char c = 0;
            StringBuilder mail = new StringBuilder();
            while(reader.ready()) {
                while (reader.ready() && (c = (char)reader.read()) != '\r') {
                    if (c != '\n') mail.append(c);
                }
                params.add(mail.toString());
                mail = new StringBuilder();
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }

        return params;
    }
}
