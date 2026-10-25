package packetserveur;

import java.io.*;
import java.net.*;

public class Serveur {
    public static void main(String[] args) throws IOException {

        ServerSocket socketServeur = new ServerSocket(1234);
        System.out.println("Le serveur attend un client...");

        Socket socket = socketServeur.accept();
        System.out.println("Un client est connecté");


        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Lecture de l'opération envoyée par le client
        String request = in.readLine();

        if (request != null) {
            System.out.println("Opération reçue : " + request);

            // Calcul et envoi de la réponse
            String result = calculate(request);
            out.println(result);
        }

        // Fermeture
        socket.close();
        socketServeur.close();
    }

    // Méthode de calcul SANS REGEX
    private static String calculate(String operation) {
        operation = operation.trim();
        int opIndex = -1;
        char operator = ' ';

        // On cherche manuellement l'opérateur dans la chaîne
        // On commence à 1 pour gérer les nombres négatifs (ex: -5+3)
        for (int i = 1; i < operation.length(); i++) {
            char c = operation.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                opIndex = i;
                operator = c;
                break;
            }
        }

        if (opIndex == -1) {
            return "Erreur : Aucun opérateur valide trouvé.";
        }

        try {
            // On sépare la chaîne en deux nombres
            String leftStr = operation.substring(0, opIndex).trim();
            String rightStr = operation.substring(opIndex + 1).trim();

            double op1 = Double.parseDouble(leftStr);
            double op2 = Double.parseDouble(rightStr);
            double resultat = 0;

            switch (operator) {
                case '+': resultat = op1 + op2; break;
                case '-': resultat = op1 - op2; break;
                case '*': resultat = op1 * op2; break;
                case '/':
                    if (op2 == 0) return "Erreur : Division par zéro.";
                    resultat = op1 / op2;
                    break;
            }
            return String.valueOf(resultat);

        } catch (NumberFormatException e) {
            return "Erreur : Les opérandes ne sont pas des nombres valides.";
        }
    }
}