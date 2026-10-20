package org.firstinspires.ftc.teamcode.Martin;

public class gamepad {
    private static final double DEADZONE = 0.05;
    private static final double pow = 4;
    private static final double coef = 0.2;
    double sortie = 0;
    double x = 0;

    public double Deadzone(double valeur){

        if (Math.abs(valeur)< DEADZONE){
            return 0;
        }
        double v = (Math.abs(valeur)-DEADZONE)/(1 - DEADZONE);

        return Math.copySign(v, valeur);
    }

    public double courbe( double valeur){

        double v = Math.pow(Math.abs(valeur), pow);
        return Math.copySign(v, valeur);
    }
    public double lissage(double cible){

        sortie = sortie + (cible -sortie) * coef;

        return sortie;
    }

    public double traiter ( double valeur){


        x =  courbe(Deadzone(valeur));
        x = lissage(x);

        return x;
    }
}
