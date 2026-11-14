
package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

public class TestLimeLight {

    Limelight3A limelight;
    TargetSelector targetSelector;

    double rotationAuto = 0.0; // correction de la rotation calculé par le robot
    double meilleurTx = 0.0; // angle horizontal du tag sélectionné
    int meilleurID = -1; //ID du meilleur apriltag detecté (-1 = aucun tag selectionné)
    boolean tagDetecte = false; // dès qu'un tag est détecté ça passe à true

    // Initialiser la Limelight
    public void init(HardwareMap hardwareMap, TargetSelector targetSelector) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        this.targetSelector = targetSelector;
        // Sélectionner le pipeline 0 puis démarrer la Limelight
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    // Mettre à jour les détections et calculer la rotation automatique
    public void update(Telemetry telemetry) {

        // Valeurs réinitialisées à chaque boucle
        rotationAuto = 0.0; // correction de la rotation calculée par le robot
        meilleurTx = 0.0; // angle horizontal du tag sélectionné
        tagDetecte = false; // dès qu'un tag est détecté, ça passe à true
        meilleurID = -1;//réinitialise l'ID du tag selectionné

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
            LLResultTypes.FiducialResult meilleurTag = targetSelector.choisirMeilleurTag(result.getFiducialResults());

            if(meilleurTag != null) {
                meilleurTx = meilleurTag.getTargetXDegrees();
                meilleurID = meilleurTag.getFiducialId();
                tagDetecte = true;

                 }

            telemetry.addData("ID AprilTag", meilleurID);
            telemetry.addData("Tx du tag", meilleurTx);


        } else {
            telemetry.addData("Cible", "Non detectee");

        }

        // Calculer la correction avec le tag sélectionné
        if (tagDetecte) {
            double kP = 0.02; //le coefficient de correction proportionnelle.
            rotationAuto = kP * meilleurTx; // calcule la commande de rotation automatique.

            rotationAuto = Math.max(-0.3, Math.min(0.3, rotationAuto)); // limite la commande de rotation entre -0.3 et +0.3.

            telemetry.addData("Meilleur Tx", meilleurTx);
            telemetry.addData("meilleurID", meilleurID);
            telemetry.addData("Rotation calculee", rotationAuto);
        } else {
            telemetry.addData("AprilTag", "Aucun tag exploitable");
        }
    }

    // Récupérer la correction calculée
    public double getRotationAuto() {
        return rotationAuto;
    }

    // recupere l'ID du tag selectionné
    public int getMeilleurID() {
        return meilleurID;
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
