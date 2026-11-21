package org.firstinspires.ftc.teamcode.Martin;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

// Base mecanum (ancienne classe "mechanum").
// Convention : forward > 0 = avant, strafe > 0 = droite, rotate > 0 = sens horaire.
public class Mecanum {

    public final DcMotorEx frontLeft;
    public final DcMotorEx frontRight;
    public final DcMotorEx backLeft;
    public final DcMotorEx backRight;

    private final Batterie batterie;

    // Compensation batterie
    public double vRef = 12.0;          // plus bas = robot plus bridé
    public boolean compensation = true; // false = désactivée

    // Limite globale de vitesse (mode précision)
    private double vitesseMax = 1.0;

    public Mecanum(HardwareMap hardwareMap, Batterie batterie) {
        this.batterie = batterie;

        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeftMotor");
        backRight = hardwareMap.get(DcMotorEx.class, "backRightMotor");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    // 0..1 : plafond de puissance appliqué à tous les moteurs
    public void setVitesseMax(double vitesseMax) {
        this.vitesseMax = Math.max(0, Math.min(1, vitesseMax));
    }

    public void drive(double forward, double strafe, double rotate) {

        double fl = forward + strafe + rotate;
        double fr = forward - strafe - rotate;
        double bl = forward - strafe + rotate;
        double br = forward + strafe - rotate;

        // Compensation AVANT la normalisation
        if (compensation) {
            double k = batterie.compenser(1.0, vRef);
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

        frontLeft.setPower(vitesseMax * fl / max);
        frontRight.setPower(vitesseMax * fr / max);
        backLeft.setPower(vitesseMax * bl / max);
        backRight.setPower(vitesseMax * br / max);
    }

    // Commande dans le repère terrain (cap en radians, anti-horaire positif)
    public void driveField(double forward, double strafe, double rotate, double cap) {
        double f = strafe * Math.sin(-cap) + forward * Math.cos(-cap);
        double s = strafe * Math.cos(-cap) - forward * Math.sin(-cap);
        drive(f, s, rotate);
    }

    public double getRpmFL() { return frontLeft.getVelocity(AngleUnit.DEGREES) / 6; }
    public double getRpmFR() { return frontRight.getVelocity(AngleUnit.DEGREES) / 6; }
    public double getRpmBL() { return backLeft.getVelocity(AngleUnit.DEGREES) / 6; }
    public double getRpmBR() { return backRight.getVelocity(AngleUnit.DEGREES) / 6; }
}
