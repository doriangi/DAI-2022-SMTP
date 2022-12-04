package org.example;

import org.example.mail.Mail;
import org.example.prank.PrankManager;
import org.example.smtp.SmtpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        BufferedReader stdin = null;

        try {
            stdin = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Veuillez entrer le chemin complet du fichier contenant les victimes :");
            String victim_path = stdin.readLine();

            System.out.println("Veuillez entrer le chemin complet du fichier contenant la liste des messages :");
            String msg_path = stdin.readLine();

            System.out.println("Combien de groupes voulez-vous créer ?");
            int size = Integer.parseInt(stdin.readLine());
            PrankManager generator = new PrankManager(size);

            int nbr_victims = 0;
            for (int i = 0; i < size; ++i) {
                System.out.printf("Combien de victimes voulez-vous dans le groupe #%d ?\n", i + 1);
                nbr_victims = Integer.parseInt(stdin.readLine());
                List<String> victims = generator.addGroup(victim_path, nbr_victims);
                System.out.printf("Groupe #%d créé\n", i + 1);
                System.out.print("Victimes du groupe :\n");
                for (String victim : victims) System.out.println(victim);
            }

            String choice = "";
            do {
                int grp_number = 1;
                if (size > 1) { // Pas besoin de demander quel groupe quand il n'y a qu'un
                    System.out.println("Quel groupe voulez-vous piéger ?");
                    grp_number = Integer.parseInt(stdin.readLine());
                }
                Mail prank = generator.setPrankMail(generator.getGroup(grp_number - 1), msg_path);

                String currentPath = new java.io.File(".").getCanonicalPath();
                String config = currentPath + "\\config\\options\\params.txt";
                SmtpClient client = generator.connect(config);
                client.sendMail(prank);
                System.out.printf("Groupe #%d piégé\n", grp_number);

                System.out.println("Voulez-vous piéger un autre groupe ? [Y/N]");
                choice = stdin.readLine();
            } while (choice.equalsIgnoreCase("Y"));
            stdin.close();
        } catch (IOException e) {
            if (stdin != null) {
                try {
                    stdin.close();
                } catch (IOException e1) {
                    LOG.log(Level.SEVERE, e.getMessage());
                }
            }
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }
}