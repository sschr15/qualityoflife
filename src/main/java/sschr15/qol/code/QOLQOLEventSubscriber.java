package sschr15.qol.code;

import sschr15.qol.api.annotations.qolevents.QOLSubscriber;
import sschr15.qol.api.annotations.qolevents.RunOnCoremodLoad;
import sschr15.qol.api.annotations.qolevents.WittyCommentSubscriber;

import java.util.Random;

@QOLSubscriber(Ref.MOD_ID)
public class QOLQOLEventSubscriber {
    @WittyCommentSubscriber
    public static String getWittyComment() {
        return new Random().nextInt(65536) < 8 ? "OW KNOWS" : null;
    }

    @RunOnCoremodLoad
    public static void onCoremodLoad() {
        Ref.LOGGER.info("Ran on Coremod load!");
    }
}
