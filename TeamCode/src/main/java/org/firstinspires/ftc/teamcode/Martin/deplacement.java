package org.firstinspires.ftc.teamcode.Martin;

public class deplacement {

    private Odometrie odo;
    private mechanum base;

    // Réglages
    private static final double kP = 0.01;           // puissance par mm d'erreur
    private static final double kCap = 1.0;          // puissance par radian d'erreur
    private static final double PUISSANCE_MAX = 0.5; // limite pour les premiers tests
    private static final double TOL_MM = 20;         // tolérance position
    private static final double TOL_CAP = Math.toRadians(2); // tolérance cap

    public deplacement(Odometrie odo, mechanum base) {
        this.odo = odo;
        this.base = base;
    }

    // À appeler à chaque tour de loop(). Renvoie true quand le robot est arrivé.
    public boolean allerA(double cibleX, double cibleY, double cibleCap) {
        double x = odo.getX();
        double y = odo.getY();
        double cap = odo.getCap();

        // 1. Erreurs dans le repère terrain
        double ex = cibleX - x;
        double ey = cibleY - y;
        double ecap = cibleCap - cap;

        // Ramener l'erreur de cap entre -PI et PI
        while (ecap > Math.PI) ecap -= 2 * Math.PI;
        while (ecap < -Math.PI) ecap += 2 * Math.PI;

        // 5. Arrivé ?
        double distance = Math.hypot(ex, ey);
        if (distance < TOL_MM && Math.abs(ecap) < TOL_CAP) {
            base.drive(0, 0, 0);
            return true;
        }

        // 2. Correcteur P
        double forward = kP * ex;
        double strafe = -kP * ey;   // signe à vérifier au test
        double rot = -kCap * ecap;  // signe à vérifier au test

        // 3. Limiter
        forward = limiter(forward);
        strafe = limiter(strafe);
        rot = limiter(rot);

        // 4. Piloter (conversion terrain -> robot par driveField)
        base.driveField(forward, strafe, rot, cap);
        return false;
    }

    private double limiter(double v) {
        return Math.max(-PUISSANCE_MAX, Math.min(PUISSANCE_MAX, v));
    }
}