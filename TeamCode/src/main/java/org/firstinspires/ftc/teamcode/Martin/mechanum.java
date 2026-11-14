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

    /// Compensation Batterie
    private Batterie batterie;
    public double Vref = 12.0;          // plus bas = robot plus bridé
    public boolean compensation = true; // false = désactivée

    public mechanum(HardwareMap hardwareMap, Batterie batterie){

        this.batterie = batterie;

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

        double fl = forward + strafe + rotate;
        double fr = forward - strafe - rotate;
        double bl = forward - strafe + rotate;
        double br = forward + strafe - rotate;

        // Compensation AVANT la normalisation
        if (compensation) {
            double k = batterie.compenser(1.0, Vref);
            fl *= k;
            fr *= k;
            bl *= k;
            br *= k;
        }

        double max = 1.0;
        max = Math.max(max, Math.abs(fl));
        max = Math.max(max, Math.abs(fr));
        max = Math.max(max, Math.abs(bl));
        max = Math.max(max, Math.abs(br));

        frontLeft.setPower(fl / max);
        frontRight.setPower(fr / max);
        backLeft.setPower(bl / max);
        backRight.setPower(br / max);
    }

    public void driveField(double forward, double strafe, double rotate, double cap){
        double f = strafe * Math.sin(-cap) + forward * Math.cos(-cap);
        double s = strafe * Math.cos(-cap) - forward * Math.sin(-cap);
        drive(f, s, rotate);
    }
}