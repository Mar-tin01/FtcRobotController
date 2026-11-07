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

    /// Compensation Batterie;
    private Batterie batterie;
    public double Vref = 12.0;
    public boolean compensation = True;
    public mechanum(HardwareMap hardwareMap){

        this.batterie

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



        if(compensation){
            double k = batterie.compenser(1.0, Vref);
            fl *= k;
            fr *= k;
            bl *= k;
            br *= k;

        }
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
