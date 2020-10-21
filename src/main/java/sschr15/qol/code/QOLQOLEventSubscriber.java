package sschr15.qol.code;

import sschr15.qol.api.annotations.qolevents.QOLSubscriber;
import sschr15.qol.api.annotations.qolevents.QOLSubscriber.*;

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

//    @TransformClass("net.minecraft.client.Minecraft")
//    public static void transformMinecraft(ClassNode classNode, String className) {
//        AtomicBoolean b = new AtomicBoolean(false);
//        classNode.methods.forEach(methodNode -> {
//            Ref.LOGGER.info(methodNode.name);
//        });
//    }
}
