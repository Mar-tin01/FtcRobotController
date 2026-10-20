package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class intake {

    private CRServo servoLeftIntake;
    private CRServo servoRightIntake;

    private DcMotor intakeMotor ;

    public void init(HardwareMap hdMap){


        intakeMotor = hdMap.get(DcMotor.class, "intake");
        servoLeftIntake = hdMap.get(CRServo.class,"servoLeftIntake");
        servoRightIntake = hdMap.get(CRServo.class,"servoRightIntake");
        servoRightIntake.setDirection(CRServo.Direction.REVERSE);

        //intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void drive(boolean powerIntake, boolean powerServo) {

        if(powerIntake == true) {
            intakeMotor.setPower(1);
        }
        else if (powerIntake == false) {
            intakeMotor.setPower(0);
        }
        if(powerServo) {
            servoLeftIntake.setPower(1.0);
            servoRightIntake.setPower(1.0);

        }
        else {
            servoLeftIntake.setPower(0.0);
            servoRightIntake.setPower(0.0);

        }

    }

}
