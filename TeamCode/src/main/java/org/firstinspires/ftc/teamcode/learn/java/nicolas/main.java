package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class main extends OpMode {

    BaseMecanum drive = new BaseMecanum();
    intake intake = new intake();
    shooter shooter = new shooter();
    TestLimeLight testLimeLight = new TestLimeLight();

    double forward, strafe, rotation;
    double powerServoNectar, powerServoPollen, powerIntake;

    @Override
    public void init() {

        drive.init(hardwareMap);
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        testLimeLight.init(hardwareMap);

    }


    @Override
    public void loop() {
        // Mettre à jour les détections et calculer la rotation automatique
        testLimeLight.update(telemetry);

        // Déplacements manuels
        forward = gamepad1.right_stick_y;
        strafe = gamepad1.right_stick_x;

        // Rotation manuelle ou automatique
        if (gamepad1.dpad_up) {
            if (testLimeLight.isTagDetecte()) {
                rotation = testLimeLight.getRotationAuto();
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

        telemetry.addData("Mode rotation", gamepad1.dpad_up ? "Automatique" : "Manuel");
        telemetry.update();
    }
}
