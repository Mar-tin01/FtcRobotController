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
        powerIntake = gamepad2.right_trigger - gamepad2.left_trigger;

        // Envoyer les commandes aux sous-systèmes
        drive.drive(forward, strafe, rotation);

        intake.drive(powerIntake, gamepad1.dpad_right);
// Puissance du shooter (copilote) : triangle = +0.05, carré = -0.05
        if (gamepad2.y && !boutonPlusPrecedent) {
            shooter.augmenter();
        }
        if (gamepad2.x && !boutonMoinsPrecedent) {
            shooter.diminuer();
        }
        boutonPlusPrecedent = gamepad2.y;
        boutonMoinsPrecedent = gamepad2.x;

// Tir (copilote) : bumper droit = tir, bumper gauche = marche arrière
        shooter.drive(gamepad2.right_bumper, gamepad2.left_bumper);


// Joystick du copilote, avec une petite zone morte pour éviter les dérives
        double stickTourelle = Math.abs(gamepad2.left_stick_x) > 0.05 ? gamepad2.left_stick_x : 0.0;

// Le copilote a la main s'il maintient la croix, ou si aucun tag n'est visible
        boolean controleCopilote = gamepad2.a || !testLimeLight.isTagDetecte();

        if (controleCopilote) {
            turret.drive(0.4 * stickTourelle);
        } else {
            turret.driveAuto(testLimeLight.getRotationAuto());
        }
        telemetry.addData("Puissance shooter", shooter.getPuissance());
        telemetry.addData("Angle tourelle", turret.getAngle());
        telemetry.addData("Alliance", allianceRouge ? "Rouge" : "Bleue");
        telemetry.addData("Aligne", testLimeLight.isAligne());
        telemetry.addData("Tourelle", controleCopilote ? "COPILOTE" : "AUTO (tag suivi)");
        telemetry.addData("Puissance moteur 2", shooter.getPuissanceMoteur2());
        telemetry.update();
    }
    @Override
    public void stop() {
        turret.drive(0.0);
        testLimeLight.stop();
    }
}
