package org.firstinspires.ftc.teamcode.learn.java.nicolas;
import java.util.List;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
//déterminer si un AprilTag correspond à l'alliance du robot.
public class TargetSelector {

    private final boolean allianceRouge ;

    public TargetSelector(boolean allianceRouge, boolean cotePublic) {

        this.allianceRouge = allianceRouge;

    }

    public boolean isCorrectTag(int id) {

        if(allianceRouge) {

            return id >= 30 && id <= 37;

            }
         else {

            return id >= 38 && id <= 45;

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

        return meilleurTag;

    }


}
