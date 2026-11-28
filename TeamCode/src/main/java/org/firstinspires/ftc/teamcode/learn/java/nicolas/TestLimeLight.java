package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

public class TestLimeLight {

    // Constantes de réglage
    static final double KP = 0.008;          // gain de la correction proportionnelle
    static final double ROTATION_MAX = 0.15; // puissance max de la rotation automatique
    static final double DEADBAND = 1.0; //  tolérance en degrés
    static final double KD = 0.0005;
    double dernierTx = 0.0;
    long dernierTemps = 0;

    Limelight3A limelight;
    TargetSelector targetSelector;

    double rotationAuto = 0.0; // correction de la rotation calculée par le robot
    double meilleurTx = 0.0; // angle horizontal du tag sélectionné
    int meilleurID = -1; // ID du meilleur apriltag détecté (-1 = aucun tag sélectionné)
    boolean tagDetecte = false; // dès qu'un tag est détecté, ça passe à true

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
        rotationAuto = 0.0;
        meilleurTx = 0.0;
        tagDetecte = false;
        meilleurID = -1;

        // Vérifier la connexion et récupérer le résultat
        telemetry.addData("LL connectee", limelight.isConnected());
        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid() && result.getStaleness() < 100) {
            double tx = result.getTx(); // angle horizontal global de la cible, en degrés
            double ty = result.getTy(); // angle vertical global de la cible, en degrés

            telemetry.addData("Cible", "Resultat valide");
            telemetry.addData("Tx global", tx);
            telemetry.addData("Ty global", ty);

            // Chercher le tag le plus proche du centre horizontal
            LLResultTypes.FiducialResult meilleurTag = targetSelector.choisirMeilleurTag(result.getFiducialResults());

            if (meilleurTag != null) {
                meilleurTx = meilleurTag.getTargetXDegrees();
                meilleurID = meilleurTag.getFiducialId();
                tagDetecte = true;

                telemetry.addData("ID AprilTag", meilleurID);
                telemetry.addData("Tx du tag", meilleurTx);
            }

        } else {
            telemetry.addData("Cible", "Non detectee");
        }

        // Calculer la correction avec le tag sélectionné
        if (tagDetecte) {

            // Vitesse de variation de Tx (degrés par seconde)
            long maintenant = System.nanoTime();
            double dt = (maintenant - dernierTemps) / 1e9;
            double derivee = (dernierTemps != 0 && dt > 0 && dt < 0.2) ? (meilleurTx - dernierTx) / dt : 0.0;
            dernierTx = meilleurTx;
            dernierTemps = maintenant;

            if (Math.abs(meilleurTx) < DEADBAND) {
                rotationAuto = 0.0; // assez bien aligné, on arrête de corriger
            } else {
                rotationAuto = KP * meilleurTx + KD * derivee;
                rotationAuto = Math.max(-ROTATION_MAX, Math.min(ROTATION_MAX, rotationAuto));
            }

            telemetry.addData("Rotation calculee", rotationAuto);

        } else {
            telemetry.addData("AprilTag", "Aucun tag exploitable");
        }
    }
    // Récupérer la correction calculée
    public double getRotationAuto() {
        return rotationAuto;
    }

    // Récupérer l'ID du tag sélectionné
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
    // Vrai si un tag est visible et centré (dans la zone morte)
    public boolean isAligne() {
        return tagDetecte && Math.abs(meilleurTx) < DEADBAND;
    }
}