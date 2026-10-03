package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class TeamMemberPractice extends OpMode {

    Pince pince;

    @Override
    public void init() {
        pince = new Pince(hardwareMap);

    }

    @Override
    public void loop() {

        if(gamepad1.a){
            pince.ouvrir();
        }

        if(gamepad1.b){
            pince.ferme();
        }

        telemetry.addData("Pince", pince.getEtat());
        telemetry.update();

    }
}

