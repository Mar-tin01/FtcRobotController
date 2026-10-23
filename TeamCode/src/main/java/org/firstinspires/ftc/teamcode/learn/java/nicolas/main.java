package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResultTypes;


@TeleOp
public class main extends OpMode {

    Limelight3A limelight;
    BaseMecanum drive = new BaseMecanum();
    intake intake = new intake();
    shooter shooter = new shooter();


    double forward, strafe, rotation;
    double powerServoNectar, powerServoPollen, powerIntake;

    @Override
    public void init() {

        drive.init(hardwareMap);
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//pas bien compris les 2 ligne en dessous
        limelight.pipelineSwitch(0);
        limelight.start();

    }


    @Override
    public void loop() {

        // Valeurs réinitialisées à chaque boucle
        double rotationAuto = 0.0;
        double meilleurTx = 0.0;
        double meilleurEcart = Double.MAX_VALUE;
        boolean tagDetecte = false;

        // Vérifier la connexion et récupérer le résultat
        telemetry.addData("LL connectee", limelight.isConnected());
        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {

            double tx = result.getTx();
            double ty = result.getTy();

            telemetry.addData("Cible", "Resultat valide");
            telemetry.addData("Tx global", tx);
            telemetry.addData("Ty global", ty);

            // Chercher le tag le plus proche du centre horizontal
            for (LLResultTypes.FiducialResult tag : result.getFiducialResults()) {

                int id = tag.getFiducialId();
                double txTag = tag.getTargetXDegrees();
                double ecart = Math.abs(txTag);

                telemetry.addData("ID AprilTag", id);
                telemetry.addData("Tx du tag", txTag);

                if (ecart < meilleurEcart) {
                    meilleurEcart = ecart;
                    meilleurTx = txTag;
                    tagDetecte = true;
                }
            }

        } else {
            telemetry.addData("Cible", "Non detectee");
        }

        // Calculer la correction avec le tag sélectionné
        if (tagDetecte) {
            double kP = 0.02;
            rotationAuto = kP * meilleurTx;

            rotationAuto = Math.max(-0.3, Math.min(0.3, rotationAuto));

            telemetry.addData("Meilleur Tx", meilleurTx);
            telemetry.addData("Rotation calculee", rotationAuto);
        } else {
            telemetry.addData("AprilTag", "Aucun tag exploitable");
        }

        // Déplacements manuels
        forward = -gamepad1.right_stick_y;
        strafe = -gamepad1.right_stick_x;

        // Rotation manuelle ou automatique
        if (gamepad1.right_stick_button) {
            if (tagDetecte) {
                rotation = rotationAuto;
            } else {
                rotation = 0.0;
            }
        } else {
            rotation = gamepad1.left_stick_x;
        }

        // Commandes des mécanismes
        powerServoNectar = (gamepad1.y ? 1.0 : 0.0) - (gamepad1.x ? 1.0 : 0.0);
        powerServoPollen = (gamepad1.b ? 1.0 : 0.0) - (gamepad1.a ? 1.0 : 0.0);
        powerIntake = gamepad1.right_trigger - gamepad1.left_trigger;

        // Envoyer les commandes aux sous-systèmes
        drive.drive(forward, strafe, rotation);
        intake.drive(powerIntake, gamepad1.dpad_left);
        shooter.drive(gamepad1.right_bumper, gamepad1.left_bumper, powerServoNectar, powerServoPollen);

        telemetry.addData("Mode rotation", gamepad1.right_stick_button ? "Automatique" : "Manuel");
        telemetry.update();
    }
}
