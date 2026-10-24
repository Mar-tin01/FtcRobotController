
package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

public class TestLimeLight {

    Limelight3A limelight;

    double rotationAuto = 0.0; // correction de la rotation calculé par le robot
    double meilleurTx = 0.0; // angle horizontal du tag sélectionné
    double meilleurEcart = Double.MAX_VALUE; // Double.MAX_VALUE = se fait remplacer par le tag le plus proche en degrés horizontaux
    boolean tagDetecte = false; // dès qu'un tag est détecté ça passe à true

    // Initialiser la Limelight
    public void init(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        // Sélectionner le pipeline 0 puis démarrer la Limelight
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    // Mettre à jour les détections et calculer la rotation automatique
    public void update(Telemetry telemetry) {

        // Valeurs réinitialisées à chaque boucle
        rotationAuto = 0.0; // correction de la rotation calculée par le robot
        meilleurTx = 0.0; // angle horizontal du tag sélectionné
        meilleurEcart = Double.MAX_VALUE; // Double.MAX_VALUE = se fait remplacer par le tag le plus proche en degrés horizontaux
        tagDetecte = false; // dès qu'un tag est détecté, ça passe à true

        // Vérifier la connexion et récupérer le résultat
        telemetry.addData("LL connectee", limelight.isConnected());
        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {

            double tx = result.getTx(); //récupère l'angle horizontal global de la cible détectée, en degrés
            double ty = result.getTy(); //récupère l'angle vertical global de la cible détectée, en degrés

            telemetry.addData("Cible", "Resultat valide");
            telemetry.addData("Tx global", tx);
            telemetry.addData("Ty global", ty);

            // Chercher le tag le plus proche du centre horizontal
            for (LLResultTypes.FiducialResult tag : result.getFiducialResults()) { // defini le type d'objet analyser puis le nom donné à les/l'AprilTag(s) en cours de traitement. puis recupere les données

                // difinition des tags
                int id = tag.getFiducialId();
                double txTag = tag.getTargetXDegrees();
                double ecart = Math.abs(txTag); //Math.abs()  =   valeur absolue

                telemetry.addData("ID AprilTag", id);
                telemetry.addData("Tx du tag", txTag);

                //choisit le plus centré
                if (ecart < meilleurEcart) {
                    meilleurEcart = ecart; // remplace le meilleur écart enregistré par celui du tag que l'on vient de trouver.
                    meilleurTx = txTag; //mémorise l'angle horizontal du tag sélectionné
                    tagDetecte = true;
                }
            }

        } else {
            telemetry.addData("Cible", "Non detectee");
        }

        // Calculer la correction avec le tag sélectionné
        if (tagDetecte) {
            double kP = 0.02; //le coefficient de correction proportionnelle.
            rotationAuto = kP * meilleurTx; // calcule la commande de rotation automatique.

            rotationAuto = Math.max(-0.3, Math.min(0.3, rotationAuto)); // limite la commande de rotation entre -0.3 et +0.3.

            telemetry.addData("Meilleur Tx", meilleurTx);
            telemetry.addData("Rotation calculee", rotationAuto);
        } else {
            telemetry.addData("AprilTag", "Aucun tag exploitable");
        }
    }

    // Récupérer la correction calculée
    public double getRotationAuto() {
        return rotationAuto;
    }

    // Vérifier si un tag a été détecté
    public boolean isTagDetecte() {
        return tagDetecte;
    }

    // Arrêter la Limelight
    public void stop() {
        if (limelight != null) {
            limelight.stop();
        }
    }
}
