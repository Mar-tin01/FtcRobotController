package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class GamePadPractice extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {

        double speedForward = -gamepad1.left_stick_y / 2.0;
        double diffence = gamepad1.left_stick_x - gamepad1.right_stick_x;

        telemetry.addData("xl", gamepad1.left_stick_x);
        telemetry.addData("yl", speedForward);
        telemetry.addData("A button", gamepad1.a);
        telemetry.addData("xr", gamepad1.right_stick_x);
        telemetry.addData("yr", gamepad1.right_stick_y);
        telemetry.addData("button B", gamepad1.b);
        telemetry.addData("difference between x right et x right", diffence);
    }
}


