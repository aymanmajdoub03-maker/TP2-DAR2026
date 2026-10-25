package packetserveur;

import java.io.*;
import java.net.*;

public class Serveur {
    public static void main(String[] args) throws Exception {
        // --- MÉTHODE DE CONNEXION ---
        ServerSocket socketServeur = new ServerSocket(1234);
        System.out.println("Le serveur attend un client...");

        Socket socket = socketServeur.accept();
        System.out.println("Un client est connecté");

        // c) Le serveur reçoit l'objet (ObjectInputStream)
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Operation op = (Operation) ois.readObject();

        // d) Le serveur récupère les données et affiche
        System.out.println("Opération reçue : " + op.operande1 + " " + op.operateur + " " + op.operande2);

        // e) Le serveur effectue le calcul
        String resultat = calculer(op);

        // f) Le serveur renvoie le résultat au client
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(resultat);
        oos.flush();

        // Fermeture
        socket.close();
        socketServeur.close();
    }

    private static String calculer(Operation op) {
        switch (op.operateur) {
            case "+": return String.valueOf(op.operande1 + op.operande2);
            case "-": return String.valueOf(op.operande1 - op.operande2);
            case "*": return String.valueOf(op.operande1 * op.operande2);
            case "/":
                if (op.operande2 == 0) return "Erreur : Division par zéro";
                return String.valueOf(op.operande1 / op.operande2);
            default: return "Erreur : Opérateur inconnu";
        }
    }
}