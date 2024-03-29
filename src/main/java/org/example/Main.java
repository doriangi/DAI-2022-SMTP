package org.example;

import org.example.mail.Mail;
import org.example.prank.PrankManager;
import org.example.smtp.SmtpClient;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int nbr_groups;
        int nbr_victims;
        String victim_path;
        String msg_path;

        try {
            if (args.length != 4) {
                throw new NumberFormatException();
            }
            nbr_groups = Integer.parseInt(args[0]);
            nbr_victims = Integer.parseInt(args[1]);
            victim_path = args[2];
            msg_path = args[3];
            Paths.get(victim_path); // Si le fichier est incorrect, InvalidPathException va être levé
            Paths.get(msg_path);
        } catch (NumberFormatException | InvalidPathException e) {
            System.out.println("Le format pour les 4 paramètres sont :");
            System.out.println("(int)<nombre de groupes> (int)<nombre de victimes> " +
                               "<chemin absolu du fichier contenant les victimes> " +
                               "<chemin absolu du fichier contenant les messages>");
            return;
        }

        PrankManager manager = new PrankManager(nbr_groups);

        for (int i = 0; i < nbr_groups; ++i) {
            try {
                manager.addGroup(victim_path, nbr_victims);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        String config = "..\\config\\options\\params.txt";
        SmtpClient client = manager.client(config);

        List<Mail> pranks = new ArrayList<>();
        for (int i = 0; i < nbr_groups; ++i) {
            System.out.printf("Mail #%d :\n", i + 1);
            pranks.add(manager.setPrankMail(manager.getGroup(i), msg_path));
            client.sendMail(pranks.get(i));
            System.out.println();
        }

    }
}