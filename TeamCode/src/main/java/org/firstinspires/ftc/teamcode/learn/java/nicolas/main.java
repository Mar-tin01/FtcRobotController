package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class main extends OpMode {


    BaseMecanum drive = new BaseMecanum();
    intake intake = new intake();

    double forward, straff, rotation;

    @Override
    public void init() {

        drive.init(hardwareMap);
        intake.init(hardwareMap);
    }




    @Override
    public void loop() {

        forward = -gamepad1.right_stick_y;
        straff = gamepad1.right_stick_x;
        rotation = gamepad1.left_stick_x;

        if (gamepad1.dpad_right) {
            drive.drive(0, 0.5, 0);
        } else if (gamepad1.dpad_left) {
            drive.drive(0, -0.5, 0);
        } else {
            drive.drive(forward, straff, rotation);
        }
        telemetry.addData("forward / straff / rotation", "%.2f / %.2f / %.2f", forward, straff, rotation);
        telemetry.update();

        drive.drive(forward, straff, rotation);


        intake.drive(gamepad1.right_bumper);

    }





}
