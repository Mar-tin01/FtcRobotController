package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class shooter {

    static final double PUISSANCE_DEPART = 0.8;   // puissance du moteur 1 au démarrage
    static final double PALIER = 0.05;            // pas de réglage
    static final double PUISSANCE_INVERSE = 0.5;  // marche arrière (dégager un bourrage)

    // Noms des deux moteurs dans la config du robot
    static final String NOM_MOTEUR_1 = "shooterPollen";
    static final String NOM_MOTEUR_2 = "tourelle";

    // Le moteur 2 tourne RATIO_MOTEUR_2 fois plus vite que le moteur 1
    static final double RATIO_MOTEUR_2 = 3.5;

    // Si le moteur 2 tourne dans le mauvais sens, mettre true
    static final boolean INVERSER_MOTEUR_2 = false;

    private DcMotor moteur1;
    private DcMotor moteur2;
    private double puissance = PUISSANCE_DEPART;

    public void init(HardwareMap hdMap) {
        moteur1 = hdMap.get(DcMotor.class, NOM_MOTEUR_1);
        moteur2 = hdMap.get(DcMotor.class, NOM_MOTEUR_2);

        if (INVERSER_MOTEUR_2) {
            moteur2.setDirection(DcMotor.Direction.REVERSE);
        }
    }

    // Monter la puissance d'un palier (maximum 1.0)
    public void augmenter() {
        puissance = Math.min(1.0, Math.round((puissance + PALIER) * 100) / 100.0);
    }

    // Baisser la puissance d'un palier (minimum 0.0)
    public void diminuer() {
        puissance = Math.max(0.0, Math.round((puissance - PALIER) * 100) / 100.0);
    }

    // Puissance réglée pour le moteur 1
    public double getPuissance() {
        return puissance;
    }

    // Puissance réellement envoyée au moteur 2 (plafonnée à 1.0)
    public double getPuissanceMoteur2() {
        return Math.min(1.0, RATIO_MOTEUR_2 * puissance);
    }

    // avant = tir, arriere = marche arrière en cas de problème
    public void drive(boolean avant, boolean arriere) {
        double p1 = 0.0;
        if (avant) {
            p1 = puissance;
        } else if (arriere) {
            p1 = -PUISSANCE_INVERSE;
        }

        // Le moteur 2 est plus rapide, mais une puissance ne peut pas dépasser 1.0
        double p2 = Math.max(-1.0, Math.min(1.0, RATIO_MOTEUR_2 * p1));

        moteur1.setPower(p1);
        moteur2.setPower(p2);
    }
}