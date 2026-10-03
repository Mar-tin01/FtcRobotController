package org.firstinspires.ftc.teamcode.Martin;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class IfTrain extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        boolean a = gamepad1.a;
        if(a){
            telemetry.addData("Bouton ", "Pressé");
        }
        else{
            telemetry.addData("Bouton ", "Non Pressé");
        }
        telemetry.update();

    }
}
