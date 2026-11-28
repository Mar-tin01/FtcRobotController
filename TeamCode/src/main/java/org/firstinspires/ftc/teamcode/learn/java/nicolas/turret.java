package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class turret {

    static final double SENS_AUTO = 1.0;          // mettre -1.0 si la tourelle s'éloigne du tag
    static final double TICKS_PAR_DEGRE = 1.0;    // À CALIBRER
    static final double ANGLE_MAX = 350.0;        // limite côté +
    static final double ANGLE_MIN = -350.0;       // limite côté -

    static final double SCAN_POWER = 0.15;        // vitesse du balayage (lent)
    static final double MARGE_BUTEE = 20.0;       // demi-tour 20° avant la butée

    private DcMotor tourelle;
    private int directionBalayage = 1;            // 1 = l'angle augmente, -1 = il diminue

    public void init(HardwareMap hdMap) {
        tourelle = hdMap.get(DcMotor.class, "tourelle");
        tourelle.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // position actuelle = 0
        tourelle.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);    // on pilote en puissance
        tourelle.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Angle actuel de la tourelle, en degrés (0 = position au démarrage)
    public double getAngle() {
        return tourelle.getCurrentPosition() / TICKS_PAR_DEGRE;
    }

    // Envoie la puissance en bloquant tout mouvement qui dépasserait les limites
    private void setPowerLimite(double power) {
        double angle = getAngle();
        if (angle >= ANGLE_MAX && power > 0) {
            power = 0.0;
        }
        if (angle <= ANGLE_MIN && power < 0) {
            power = 0.0;
        }
        tourelle.setPower(power);
    }

    // Commande manuelle
    public void drive(double power) {
        setPowerLimite(power);
    }

    // Commande automatique venant de la Limelight
    public void driveAuto(double correction) {
        setPowerLimite(SENS_AUTO * correction);
    }

    // Balayage lent : va-et-vient entre les butées pour retrouver un tag
    public void balayer() {
        double angle = getAngle();

        if (angle >= ANGLE_MAX - MARGE_BUTEE) {
            directionBalayage = -1;
        } else if (angle <= ANGLE_MIN + MARGE_BUTEE) {
            directionBalayage = 1;
        }

        setPowerLimite(directionBalayage * SCAN_POWER);
    }
}