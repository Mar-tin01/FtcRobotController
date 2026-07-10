package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;



@Disabled
@TeleOp
public class HelloWold  extends OpMode {
    @Override
    public void init() {
        telemetry.addData("Hello", "Wrold");

    }
    
    @Override
    public void loop() {

    }
}
