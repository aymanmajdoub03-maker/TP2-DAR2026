package packetclient;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import packetserveur.Operation; // Importation de la classe du serveur

public class Client {
    public static void main(String[] args) throws Exception {
        // --- MÉTHODE DE CONNEXION ---
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Je suis connecté");

        // Préparation des données par l'utilisateur
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le premier opérande : ");
        double op1 = scanner.nextDouble();

        System.out.print("Entrez l'opérateur (+, -, *, /) : ");
        String op = scanner.next();

        System.out.print("Entrez le deuxième opérande : ");
        double op2 = scanner.nextDouble();

        // a) & b) Création de l'objet et envoi au serveur
        Operation operation = new Operation(op1, op, op2);

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(operation);
        oos.flush();

        // g) Le client lit et affiche le résultat
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        String resultat = (String) ois.readObject();

        System.out.println("Le résultat reçu du serveur est : " + resultat);

        // Fermeture
        socket.close();
        scanner.close();
    }
}