package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@Disabled

@TeleOp
public class VariablePractice extends OpMode {
    @Override
    public void init() {
        int teamnumber = 26762;
        double motorSpeed = 0.75;
        boolean clawclosed = false;
        String name = "Martin";
        int degMotor = 100;

        telemetry.addData("Team Number", teamnumber);
        telemetry.addData("Motor Speed", motorSpeed);
        telemetry.addData("Claw Closed", clawclosed);
        telemetry.addData("Team Name", name);
        telemetry.addData("Motor Angle", degMotor);
    }

    @Override
    public void loop() {

    }
}
