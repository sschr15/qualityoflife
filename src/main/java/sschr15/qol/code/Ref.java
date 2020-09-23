package sschr15.qol.code;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ref {
    public static final String MOD_ID = "qol";
    public static final String MOD_NAME = "QualityOfLife";
    public static final String VERSION = "1.0-SNAPSHOT";

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
