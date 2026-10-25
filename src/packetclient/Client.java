package packetclient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 1234);
        System.out.println("Je suis connecté");


        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez votre opération (ex: 55*25 ou 88-8) : ");
        String operation = scanner.nextLine().trim();

        // Envoi de la requête au serveur
        out.println(operation);

        // Lecture de la réponse
        String resultat = in.readLine();
        if (resultat != null) {
            System.out.println("Le résultat reçu est : " + resultat);
        }

        // Fermeture
        socket.close();
        scanner.close();
    }
}