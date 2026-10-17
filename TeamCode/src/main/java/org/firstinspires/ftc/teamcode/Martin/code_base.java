package org.firstinspires.ftc.teamcode.Martin;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp (name = "base")
public class code_base extends OpMode {
    private mechanum base;


    @Override
    public void init(){
        base = new mechanum(hardwareMap);
    }

    @Override
    public void loop() {

        base.drive(gamepad1.right_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);

    }
}
