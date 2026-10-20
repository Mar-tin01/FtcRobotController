package org.firstinspires.ftc.teamcode.Martin;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class mechanum {

    public final DcMotor frontLeft;
    public final DcMotor frontRight;
    public final DcMotor backRight;
    public final DcMotor backLeft;
    private double heading = 0;

    private final static double P = 0.045;
    public mechanum(HardwareMap hardwareMap){
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRight =  hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeft = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRight = hardwareMap.get(DcMotor.class, "backRightMotor");


        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    public void drive(double forward, double strafe, double rotate){

        double fl = forward + strafe + rotate; // 1
        double fr = forward - strafe - rotate; // -1
        double bl = forward - strafe + rotate; // -1
        double br = forward + strafe - rotate; // 1


        double max = 1.0;
        max = Math.max(max, Math.abs(fl)); // 1
        max = Math.max(max, Math.abs(fr)); // 1
        max = Math.max(max, Math.abs(bl)); // 1
        max = Math.max(max, Math.abs(br)); // 1

        frontLeft.setPower(fl / max);
        frontRight.setPower(fr / max);
        backLeft.setPower(bl / max);
        backRight.setPower(br / max);


    }
}
