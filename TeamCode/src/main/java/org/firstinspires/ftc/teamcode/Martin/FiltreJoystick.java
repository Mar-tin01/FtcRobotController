package org.firstinspires.ftc.teamcode.Martin;

// Filtre d'un axe de joystick : zone morte -> courbe -> lissage.
// (ancienne classe "gamepad")
public class FiltreJoystick {

    private static final double DEADZONE = 0.05;
    private static final double PUISSANCE_COURBE = 4;
    private static final double COEF_LISSAGE = 0.85;

    private double sortie = 0;

    public double deadzone(double valeur) {
        if (Math.abs(valeur) < DEADZONE) {
            return 0;
        }
        double v = (Math.abs(valeur) - DEADZONE) / (1 - DEADZONE);
        return Math.copySign(v, valeur);
    }

    public double courbe(double valeur) {
        double v = Math.pow(Math.abs(valeur), PUISSANCE_COURBE);
        return Math.copySign(v, valeur);
    }

    public double lissage(double cible) {
        sortie = sortie + (cible - sortie) * COEF_LISSAGE;
        return sortie;
    }

    public double traiter(double valeur) {
        double x = courbe(deadzone(valeur));
        return lissage(x);
    }
}
