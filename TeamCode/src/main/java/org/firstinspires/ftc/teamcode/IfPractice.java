package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class IfPractice extends OpMode {
    @Override
    public void init() {


    }
    @Override
    public void loop() {
        double turbo = 0.5;
        double motorSpeed = -gamepad1.left_stick_y;
        if (!gamepad1.a){
            motorSpeed *= turbo;
        }
        else{
            motorSpeed *= 1;
        }

        telemetry.addData("left Stick Value", motorSpeed);
    }
}
