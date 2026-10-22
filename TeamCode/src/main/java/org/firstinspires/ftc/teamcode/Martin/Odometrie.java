package org.firstinspires.ftc.teamcode.Martin;
import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.MM;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;



public class Odometrie {
    private GoBildaPinpointDriver pinpoint;
    double Xoffset = 0;
    double Yoffset = 0;
    private double x;
    private double y;
    public final double circonference_mm = 2 * Math.PI * 16;
    public final double mm_par_tick = circonference_mm / 2000;
    private int tickXprec = 0;
    private int tickYprec = 0;
    private double CAPprec = 0;



    public Odometrie(HardwareMap hardwareMap, double Xoffset, double Yoffset) {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        this.Xoffset = Xoffset;
        this.Yoffset = Yoffset;
        pinpoint.setOffsets(Xoffset, Yoffset, MM);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();
    }

    public void move(double X_mm, double Y_mm, double cap_rad) {


        x += X_mm * Math.cos(cap_rad) - Y_mm * Math.sin(cap_rad);
        y += X_mm * Math.sin(cap_rad) + Y_mm * Math.cos(cap_rad);
    }

    public void update() {
        pinpoint.update();

        // Valeurs
        int tickX = pinpoint.getEncoderX();
        int tickY = pinpoint.getEncoderY();
        double CAP = pinpoint.getHeading(AngleUnit.RADIANS);

        // Calcul Delta
        double DeltaX = tickX - tickXprec;
        double DeltaY = tickY - tickYprec;
        double DeltaCap = CAP - CAPprec;

        // Mise à jour
        tickXprec = tickX;
        tickYprec = tickY;
        CAPprec = CAP;

        if (DeltaCap > Math.PI) {
            DeltaCap -= 2*Math.PI;
        } else if (DeltaCap < -Math.PI) {
            DeltaCap += 2*Math.PI;
        }



        // Offset

        double arcX = Xoffset * DeltaCap;
        double arcY = Yoffset * DeltaCap;

        double X_mm = DeltaX * mm_par_tick + arcX;
        double Y_mm = DeltaY * mm_par_tick - arcY;

        // Utiliser

        move(X_mm, Y_mm, CAP);
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
}
