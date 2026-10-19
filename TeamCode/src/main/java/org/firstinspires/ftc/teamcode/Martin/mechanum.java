package org.firstinspires.ftc.teamcode.Martin;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;



public class mechanum {

    public final DcMotor frontLeft;
    public final DcMotor frontRight;
    public final DcMotor backRight;
    public final DcMotor backLeft;
    public final IMU imu;
    private double heading = 0;

    private final static double P = 0.045;
    public mechanum(HardwareMap hardwareMap){
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRight =  hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeft = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRight = hardwareMap.get(DcMotor.class, "backRightMotor");
        imu = hardwareMap.get(IMU.class, "imu");


        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(LogoFacingDirection.UP, UsbFacingDirection.BACKWARD)));
        imu.resetYaw();
    }

    public double Getheading(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public void drive(double forward, double strafe, double rotate){


        if (rotate != 0){

            heading = Getheading();

        } else {
            double erreur = heading - Getheading();

            while (erreur > 180) {erreur -= 360;}
            while (erreur < -180) {erreur += 360;}

        }

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
