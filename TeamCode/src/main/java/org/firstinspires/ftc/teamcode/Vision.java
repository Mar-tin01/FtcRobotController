package org.firstinspires.ftc.teamcode.Martin;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

// Limelight : détecte le meilleur AprilTag de notre alliance et calcule une rotation auto
// (ancienne classe "TestLimeLight"). rotationAuto > 0 = sens horaire, comme Mecanum.drive.
public class Vision {

    // Réglages (à ajuster au test)
    private static final double KP = 0.02;           // puissance par degré d'erreur
    private static final double ROTATION_MAX = 0.3;  // plafond de la rotation auto
    private static final double ROTATION_MIN = 0.06; // puissance mini pour que le robot bouge
    private static final double ZONE_MORTE_DEG = 1.0; // en dessous : considéré aligné

    private final Limelight3A limelight;
    private final TargetSelector selecteur;

    private double rotationAuto = 0.0;
    private double meilleurTx = 0.0; // angle horizontal du tag choisi (degrés)
    private double meilleurTy = 0.0; // angle vertical du tag choisi (degrés), utile pour la distance
    private int meilleurID = -1;     // -1 = aucun tag choisi
    private boolean tagDetecte = false;

    public Vision(HardwareMap hardwareMap, TargetSelector selecteur) {
        this.selecteur = selecteur;
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    // À appeler à chaque boucle
    public void update() {
        rotationAuto = 0.0;
        meilleurTx = 0.0;
        meilleurTy = 0.0;
        meilleurID = -1;
        tagDetecte = false;

        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            LLResultTypes.FiducialResult tag = selecteur.choisirMeilleurTag(result.getFiducialResults());

            if (tag != null) {
                meilleurTx = tag.getTargetXDegrees();
                meilleurTy = tag.getTargetYDegrees();
                meilleurID = tag.getFiducialId();
                tagDetecte = true;
            }
        }

        if (tagDetecte && Math.abs(meilleurTx) > ZONE_MORTE_DEG) {
            double r = KP * meilleurTx;
            r = Math.max(-ROTATION_MAX, Math.min(ROTATION_MAX, r));
            if (Math.abs(r) < ROTATION_MIN) {
                r = Math.copySign(ROTATION_MIN, r);
            }
            rotationAuto = r;
        }
    }

    public void afficher(Telemetry telemetry) {
        telemetry.addData("LL connectee", limelight.isConnected());
        if (tagDetecte) {
            telemetry.addData("Tag ID", meilleurID);
            telemetry.addData("Tag tx", meilleurTx);
            telemetry.addData("Tag ty", meilleurTy);
            telemetry.addData("Rotation auto", rotationAuto);
        } else {
            telemetry.addData("AprilTag", "Aucun tag exploitable");
        }
    }

    public double getRotationAuto() { return rotationAuto; }
    public double getTx() { return meilleurTx; }
    public double getTy() { return meilleurTy; }
    public int getMeilleurID() { return meilleurID; }
    public boolean isTagDetecte() { return tagDetecte; }

    public void stop() {
        limelight.stop();
    }
}
