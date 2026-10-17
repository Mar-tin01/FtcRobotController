package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class VariablesPractice extends OpMode {
    @Override
    public void init() {
        int teamNumber = 26762;
        int angleMotor = 90;
        double speedMotor = 0.75;
        boolean clawClosed = true;
        String teamName = "pascal";

        telemetry.addData("team number", teamNumber);
        telemetry.addData("angle motor", angleMotor);
        telemetry.addData("speed motor", speedMotor);
        telemetry.addData("claw closed",clawClosed);
        telemetry.addData("teamName", teamName);
    }

    @Override
    public void loop() {

    }
}
