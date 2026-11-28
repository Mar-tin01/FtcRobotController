package org.firstinspires.ftc.teamcode.Martin;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class Batterie {

    // La tension varie lentement : inutile de la relire à chaque boucle
    private static final long PERIODE_LECTURE_MS = 200;

    private final VoltageSensor capteur;
    private double tension;
    private long derniereLectureMs;

    public Batterie(HardwareMap hardwareMap) {
        capteur = hardwareMap.voltageSensor.iterator().next();
        tension = capteur.getVoltage();
        derniereLectureMs = System.currentTimeMillis();
    }

    public double getVoltage() {
        long maintenant = System.currentTimeMillis();
        if (maintenant - derniereLectureMs >= PERIODE_LECTURE_MS) {
            tension = capteur.getVoltage();
            derniereLectureMs = maintenant;
        }
        return tension;
    }

    // Puissance corrigée selon la tension de réglage du moteur (vRef)
    public double compenser(double puissance, double vRef) {
        double v = getVoltage();
        if (v < 1) return puissance; // sécurité si lecture nulle
        return puissance * vRef / v;
    }
}
