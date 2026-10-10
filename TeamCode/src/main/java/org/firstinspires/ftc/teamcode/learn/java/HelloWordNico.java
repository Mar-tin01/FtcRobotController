package org.firstinspires.ftc.teamcode.learn.java;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;



@Autonomous
@Disabled
public class HelloWordNico extends OpMode {

    @Override
    public void init() {
        telemetry.addData("Hello", "Nicolas");
    }

    @Override
    public void loop() {

    }
}


