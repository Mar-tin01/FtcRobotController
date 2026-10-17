package org.firstinspires.ftc.teamcode.Martin;

public class gamepad {
    private static final double DEADZONE = 0.05;
    private static final double pow = 4;

    public static double Deadzone(double valeur){

        if (Math.abs(valeur)< DEADZONE){
            return 0;
        }
        double v = (Math.abs(valeur)-DEADZONE)/(1 - DEADZONE);

        return Math.copySign(v, valeur);
    }

    public static double courbe( double valeur){

        double v = Math.pow(valeur, pow);
        return Math.copySign(v, valeur);
    }

    public static double traiter ( double valeur){
        return courbe(Deadzone(valeur));
    }
}
