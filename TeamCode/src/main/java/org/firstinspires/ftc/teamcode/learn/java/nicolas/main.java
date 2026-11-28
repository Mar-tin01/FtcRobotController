package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class main extends OpMode {

    protected boolean allianceRouge; // défini par mainRouge ou mainBleu

    BaseMecanum drive = new BaseMecanum();
    intake intake = new intake();
    shooter shooter = new shooter();
    TestLimeLight testLimeLight = new TestLimeLight();
    TargetSelector targetSelector ;
    turret turret = new turret();
    double forward, strafe, rotation;
    double powerIntake;
    boolean modeManuel = false;      // false = tourelle auto, true = copilote
    boolean boutonPrecedent = false; // état du bouton à la boucle d'avant
    boolean boutonPlusPrecedent = false;
    boolean boutonMoinsPrecedent = false;
    @Override
    public void init() {

        drive.init(hardwareMap);
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        targetSelector = new TargetSelector(allianceRouge);
        testLimeLight.init(hardwareMap, targetSelector);
        turret.init(hardwareMap);

    }


    @Override
    public void loop() {
        // Mettre à jour les détections et calculer la rotation automatique
        testLimeLight.update(telemetry);

        // Déplacements manuels
        forward = -gamepad1.right_stick_y;
        strafe = gamepad1.right_stick_x;
        rotation = gamepad1.left_stick_x;

        // Commandes des mécanismes
        powerIntake = gamepad1.right_trigger - gamepad1.left_trigger;

        // Envoyer les commandes aux sous-systèmes
        drive.drive(forward, strafe, rotation);

        intake.drive(powerIntake, gamepad1.dpad_right);
// Réglage de la puissance du shooter : b = +0.05, a = -0.05 (un appui = un palier)
        if (gamepad1.b && !boutonPlusPrecedent) {
            shooter.augmenter();
        }
        if (gamepad1.a && !boutonMoinsPrecedent) {
            shooter.diminuer();
        }
        boutonPlusPrecedent = gamepad1.b;
        boutonMoinsPrecedent = gamepad1.a;
        shooter.drive(gamepad1.right_bumper);
        // Bascule auto / manuel avec gamepad2.y (un appui = un changement)
        if (gamepad2.y && !boutonPrecedent) {
            modeManuel = !modeManuel;
        }
        boutonPrecedent = gamepad1.y;

// Tourelle : manuel (copilote) ou automatique (Limelight)
        if (modeManuel) {
            turret.drive(0.4 * gamepad2.right_stick_x);
        } else if (gamepad1.dpad_left || gamepad1.dpad_down) {
            double powerTourelle = (gamepad1.dpad_left ? 0.3 : 0.0) - (gamepad1.dpad_down ? 0.3 : 0.0);
            turret.drive(powerTourelle);
        } else if (testLimeLight.isTagDetecte()) {
            turret.driveAuto(testLimeLight.getRotationAuto());
        } else {
            turret.drive(0.0);
        }
        telemetry.addData("Puissance shooter", shooter.getPuissance());
        telemetry.addData("Angle tourelle", turret.getAngle());
        telemetry.addData("Alliance", allianceRouge ? "Rouge" : "Bleue");
        telemetry.update();
    }
}
