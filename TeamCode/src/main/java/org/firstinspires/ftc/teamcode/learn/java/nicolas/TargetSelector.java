package org.firstinspires.ftc.teamcode.learn.java.nicolas;
import java.util.List;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
//déterminer si un AprilTag correspond à l'alliance du robot.
public class TargetSelector {

    private final boolean allianceRouge ;
    private final boolean cotePublic;

    public TargetSelector(boolean allianceRouge, boolean cotePublic) {

        this.allianceRouge = allianceRouge;
        this.cotePublic = cotePublic;

    }

    public boolean isCorrectTag(int id) {

        if(allianceRouge) {
            if (cotePublic) {
                return id >= 34 && id <= 37;
            }else {
                return id >= 30 && id <= 33;
            }
        } else {
            if (cotePublic) {
                return id >= 38 && id <= 41;
            } else {
                return id >= 42 && id <= 45;
            }
        }
    }

    public LLResultTypes.FiducialResult choisirMeilleurTag(List<LLResultTypes.FiducialResult> tags){

        LLResultTypes.FiducialResult meilleurTag = null;
        double meilleurEcart = Double.MAX_VALUE;

        for (LLResultTypes.FiducialResult tag : tags) {
            int id = tag.getFiducialId();

            //verifiersi le tag correspond a notre alliance et de notre coté
            if (isCorrectTag(id)) {

                double txTag = tag.getTargetXDegrees();
                double ecart = Math.abs(txTag);

                //garder le tag le plus proche du centre
                if (ecart < meilleurEcart) {
                    meilleurEcart = ecart;
                    meilleurTag = tag;
                }
            }
        }
    }



}
