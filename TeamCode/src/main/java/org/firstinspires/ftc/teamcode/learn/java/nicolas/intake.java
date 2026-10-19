package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class intake {

    private DcMotor intakeMotor ;

    public void init(HardwareMap hdMap){


        intakeMotor = hdMap.get(DcMotor.class, "intake");

        //intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void drive(boolean powerIntake) {

        if(powerIntake == true) {
            intakeMotor.setPower(1);
        }
        else if (powerIntake == false) {
            intakeMotor.setPower(0);
        }

    }

}
