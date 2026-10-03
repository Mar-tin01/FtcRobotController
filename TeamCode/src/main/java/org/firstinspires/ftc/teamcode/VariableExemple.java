package org.firstinspires.ftc.teamcode.Martin;

import android.os.DropBoxManager;
import android.view.InputDevice;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp

public class VariableExemple extends OpMode {

    @Override
    public void init() {
        int Robothauteur = 0;
        double MotorSpeed = 0.5;
        boolean PinceFermee = true;

        telemetry.addData("Hauteur du Robot ", Robothauteur);
        telemetry.addData("Vitesse du robot ", MotorSpeed);
        telemetry.addData("Pince Fermée ", PinceFermee);
    }
    @Override
    public void loop() {
    }

}
