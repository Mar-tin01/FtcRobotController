package org.firstinspires.ftc.teamcode.Martin;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

// Active le bulk caching : toutes les lectures d'un hub (encodeurs, vitesses...)
// sont regroupées en une seule requête par boucle au lieu d'une par appel.
// À appeler UNE fois dans init() de chaque OpMode.
public class Hubs {

    private Hubs() {}

    public static void activerBulkCaching(HardwareMap hardwareMap) {
        for (LynxModule hub : hardwareMap.getAll(LynxModule.class)) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
    }
}
