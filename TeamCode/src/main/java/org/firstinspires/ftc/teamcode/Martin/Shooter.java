package org.firstinspires.ftc.teamcode.Martin;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

// Deux shooters (Nectar / Pollen) à vitesse RÉGULÉE + deux tourelles à servo CR.
// Vitesse régulée = tirs réguliers quelle que soit la batterie.
public class Shooter {

    // /!\ À VÉRIFIER AU PREMIER TEST : si une roue tourne à l'envers, passer sa constante à true
    private static final boolean INVERSER_NECTAR = false;
    private static final boolean INVERSER_POLLEN = false;

    private static final double RPM_PAR_DEFAUT = 3000; // à régler selon le moteur / le tir
    private static final double TOLERANCE = 0.03;      // 3 % autour de la cible = "prêt"

    private final DcMotorEx shooterNectar;
    private final DcMotorEx shooterPollen;
    private final CRServo tourelleNectar;
    private final CRServo tourellePollen;

    private double rpmCible = RPM_PAR_DEFAUT;
    private boolean nectarActif = false;
    private boolean pollenActif = false;

    public Shooter(HardwareMap hardwareMap) {
        shooterNectar = hardwareMap.get(DcMotorEx.class, "shooterNectar");
        shooterPollen = hardwareMap.get(DcMotorEx.class, "shooterPollen");
        tourelleNectar = hardwareMap.get(CRServo.class, "tourelleNectar");
        tourellePollen = hardwareMap.get(CRServo.class, "tourellePollen");

        shooterNectar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterPollen.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (INVERSER_NECTAR) shooterNectar.setDirection(DcMotor.Direction.REVERSE);
        if (INVERSER_POLLEN) shooterPollen.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setRpmCible(double rpm) {
        rpmCible = Math.max(0, rpm);
    }

    public double getRpmCible() {
        return rpmCible;
    }

    // Allume / éteint chaque shooter (à appeler à chaque boucle)
    public void drive(boolean nectar, boolean pollen) {
        nectarActif = nectar;
        pollenActif = pollen;

        appliquer(shooterNectar, nectar);
        appliquer(shooterPollen, pollen);
    }

    private void appliquer(DcMotorEx moteur, boolean actif) {
        if (actif) {
            moteur.setVelocity(rpmCible * 6, AngleUnit.DEGREES); // 1 tr/min = 6 °/s
        } else {
            moteur.setPower(0);
        }
    }

    // Servos des tourelles : -1..1 (pas de retour de position avec des CRServo)
    public void tourelle(double nectar, double pollen) {
        tourelleNectar.setPower(nectar);
        tourellePollen.setPower(pollen);
    }

    public double getRpmNectar() {
        return shooterNectar.getVelocity(AngleUnit.DEGREES) / 6;
    }

    public double getRpmPollen() {
        return shooterPollen.getVelocity(AngleUnit.DEGREES) / 6;
    }

    // true si au moins un shooter est allumé et que tous les shooters allumés sont à la vitesse cible
    public boolean pret() {
        if (!nectarActif && !pollenActif) return false;
        if (nectarActif && !proche(getRpmNectar())) return false;
        if (pollenActif && !proche(getRpmPollen())) return false;
        return true;
    }

    private boolean proche(double rpm) {
        return Math.abs(Math.abs(rpm) - rpmCible) <= rpmCible * TOLERANCE;
    }
}
