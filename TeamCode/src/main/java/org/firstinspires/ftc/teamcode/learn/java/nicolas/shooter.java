package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class shooter {

    static final double PUISSANCE_DEPART = 0.8;   // puissance au démarrage
    static final double PALIER = 0.05;            // pas de réglage
    static final double PUISSANCE_INVERSE = 0.5;  // puissance en marche arrière (dégager un bourrage)

    private DcMotor shooterPollen;
    private double puissance = PUISSANCE_DEPART;

    public void init(HardwareMap hdMap) {
        shooterPollen = hdMap.get(DcMotor.class, "shooterPollen");
    }

    // Monter la puissance d'un palier (maximum 1.0)
    public void augmenter() {
        puissance = Math.min(1.0, Math.round((puissance + PALIER) * 100) / 100.0);
    }

    // Baisser la puissance d'un palier (minimum 0.0)
    public void diminuer() {
        puissance = Math.max(0.0, Math.round((puissance - PALIER) * 100) / 100.0);
    }

    public double getPuissance() {
        return puissance;
    }

    // avant = tir, arriere = marche arrière en cas de problème
    public void drive(boolean avant, boolean arriere) {
        if (avant) {
            shooterPollen.setPower(puissance);
        } else if (arriere) {
            shooterPollen.setPower(-PUISSANCE_INVERSE);
        } else {
            shooterPollen.setPower(0.0);
        }
    }
}