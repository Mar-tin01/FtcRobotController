package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@Disabled
@TeleOp
public class HelloWorld extends OpMode {

    @Override
    public void init() {
        telemetry.addData("Hello", "World");
    }

    @Override
    public void loop() {
        telemetry.addData("MOTOR_Left_Y : ", gamepad1.left_stick_y);
        telemetry.addData("MOTOR_Left_X : ", gamepad1.left_stick_x);
        telemetry.update();

    }
}
