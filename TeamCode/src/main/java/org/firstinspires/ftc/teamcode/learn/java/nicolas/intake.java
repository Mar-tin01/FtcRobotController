package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class intake {

    private CRServo servoLeftIntake;
    private CRServo servoRightIntake;

    private DcMotor intakeMotor;

    public void init(HardwareMap hdMap) {


        intakeMotor = hdMap.get(DcMotor.class, "intake");
        servoLeftIntake = hdMap.get(CRServo.class, "servoLeftIntake");
        servoRightIntake = hdMap.get(CRServo.class, "servoRightIntake");
          servoRightIntake.setDirection(CRServo.Direction.REVERSE);

        //intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void drive(double powerIntake, boolean powerServo) {

        intakeMotor.setPower(powerIntake);


        servoLeftIntake.setPower(powerServo ? 1.0 : 0.0);
        servoRightIntake.setPower(powerServo ? 1.0 : 0.0);


    }

}
