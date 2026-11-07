package org.firstinspires.ftc.teamcode.Martin;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;


public class Batterie {

    private VoltageSensor capteur;

    public Batterie(HardwareMap hardwareMap) {
        capteur = hardwareMap.voltageSensor.iterator().next();
    }

    public double getVoltage() {
        return capteur.getVoltage();
    }

    // Puissance corrigée selon la tension de réglage du moteur (vRef)
    public double compenser(double puissance, double vRef) {

        double v = getVoltage();
        if (v < 1) return puissance; // sécurité si lecture nulle
        return puissance * vRef / v;
    }
}