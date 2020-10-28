package sschr15.qol.code;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ref {
    public static final String MOD_ID = "qol";
    public static final String MOD_NAME = "QualityOfLife";
    public static final String VERSION = "1.1.0";

    private static final Thread preLaunchWindowThread = new Thread(new PreLaunchWindow());
    private static boolean hasStarted = false;

    public static void startPreLaunchWindow() {
        if (hasStarted) return;
        preLaunchWindowThread.start();
        try {
            preLaunchWindowThread.join(1000);
        } catch (InterruptedException ignored) {}
        hasStarted = true;
    }

    public static void stopPreLaunchWindow() {
        preLaunchWindowThread.interrupt();
    }

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static class ConflictingResource {
        public final String setPrefix;
        public final String resourceName;
        public final String expectedPrefix;

        public ConflictingResource(String setPrefix, String resourceName, String expectedPrefix) {
            this.setPrefix = setPrefix;
            this.resourceName = resourceName;
            this.expectedPrefix = expectedPrefix;
        }
    }
}
