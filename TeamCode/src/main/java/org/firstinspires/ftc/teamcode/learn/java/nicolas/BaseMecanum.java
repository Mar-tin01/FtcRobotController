package org.firstinspires.ftc.teamcode.learn.java.nicolas;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;



public class BaseMecanum {
    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor,backRightMotor ;


    public void init(HardwareMap hwMap) {

        frontLeftMotor = hwMap.get(DcMotor.class, "front left motor");
        frontRightMotor = hwMap.get(DcMotor.class, "front right motor");
        backLeftMotor = hwMap.get(DcMotor.class, "back left motor");
        backRightMotor = hwMap.get(DcMotor.class, "back right motor");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }




    public void drive( double forward, double straff, double rotation) {

        double frontLeftPower = forward + straff + rotation;
        double backLeftPower = forward - straff + rotation;
        double frontRightPower = forward - straff - rotation;
        double backRightPower = forward + straff - rotation;

        double maxPower = 1.0;
        double maxSpeed = 1.0;


        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        frontLeftMotor.setPower(maxSpeed * (frontLeftPower / maxPower));
        backLeftMotor.setPower(maxSpeed * (backLeftPower / maxPower));
        frontRightMotor.setPower(maxSpeed *(frontRightPower / maxPower));
        backRightMotor.setPower(maxSpeed * (backRightPower / maxPower));


    }


}