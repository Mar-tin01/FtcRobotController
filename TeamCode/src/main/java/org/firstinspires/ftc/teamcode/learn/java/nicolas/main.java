package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class main extends OpMode {


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
    }


    @Override
    public void loop() {

        forward = -gamepad1.right_stick_y;
        strafe = -gamepad1.right_stick_x;
        rotation = gamepad1.left_stick_x;

        powerServoNectar = (gamepad1.y ? 1.0 : 0.0) - (gamepad1.x ? 1.0 : 0.0);
        powerServoPollen = (gamepad1.b ? 1.0 : 0.0) - (gamepad1.a ? 1.0 : 0.0);

        powerIntake = (gamepad1.right_trigger) -(gamepad1.left_trigger);

        drive.drive(forward, strafe, rotation);


        intake.drive(powerIntake, gamepad1.dpad_left);

        shooter.drive(gamepad1.right_bumper, gamepad1.left_bumper, powerServoNectar, powerServoPollen);

    }
}
