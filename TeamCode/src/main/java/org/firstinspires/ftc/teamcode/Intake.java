package org.firstinspires.ftc.teamcode.Martin;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

// Intake : un moteur (avaler / rejeter) + deux servos à rotation continue.
public class Intake {

    private final DcMotorEx moteur;
    private final CRServo servoGauche;
    private final CRServo servoDroit;

    public Intake(HardwareMap hardwareMap) {
        moteur = hardwareMap.get(DcMotorEx.class, "intake");
        servoGauche = hardwareMap.get(CRServo.class, "servoLeftIntake");
        servoDroit = hardwareMap.get(CRServo.class, "servoRightIntake");
        servoDroit.setDirection(CRServo.Direction.REVERSE);
    }

    // gauche = gâchette qui avale, droite = gâchette qui rejette, servos = on/off
    public void drive(double gauche, double droite, boolean servos) {
        if (gauche > 0.1) {
            moteur.setPower(1);
        } else if (droite > 0.1) {
            moteur.setPower(-1);
        } else {
            moteur.setPower(0);
        }

        double puissanceServos = servos ? 1.0 : 0.0;
        servoGauche.setPower(puissanceServos);
        servoDroit.setPower(puissanceServos);
    }

    public double getRPM() {
        return moteur.getVelocity(AngleUnit.DEGREES) / 6;
    }
}
