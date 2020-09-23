package sschr15.qol.mixin.common;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sschr15.qol.api.annotations.qolevents.WittyCommentSubscriber;
import sschr15.qol.code.QualityOfLife;
import sschr15.qol.util.QOLSubscriberCaller;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraftforge.fml.common.Loader;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Mixin(CrashReport.class)
public abstract class CrashReportMixin {
    @Shadow @Final private CrashReportCategory systemDetailsCategory;
    @Shadow public abstract String getCauseStackTraceOrString();

    @Redirect(method = "getCompleteReport", at = @At(value = "INVOKE", target = "Lnet/minecraft/crash/CrashReport;getCauseStackTraceOrString()Ljava/lang/String;"))
    private String getThing(CrashReport self) {
        StringBuilder builder = new StringBuilder();
        try {
            if (!Loader.isModLoaded("vanillafix")) {
                if (QualityOfLife.doesModFixCrashes())
                    builder.append("VanillaFix was not found, but another mod saves crashes!");
                else
                    builder.append("VanillaFix was not found! To keep crashes from completely " +
                            "closing the game, download it or another mod that does a similar thing.");
            }
        } catch (Throwable t) {
            builder.append("There was an error attempting to detect VanillaFix: ").append(t.getMessage());
        }
        return builder.toString() + "\n\n" + this.getCauseStackTraceOrString();
    }

    @Inject(method = "getWittyComment", at = @At("HEAD"), cancellable = true)
    private static void getWittyComment(CallbackInfoReturnable<String> cir) {
        Random random = new Random(System.currentTimeMillis());
        List<Method> methods = QOLSubscriberCaller.getMethods(WittyCommentSubscriber.class);
        Collections.shuffle(methods, random);
        String output = null;
        Iterator<Method> methodIterator = methods.iterator();
        while (output == null && methodIterator.hasNext()) {
            try {
                output = (String) methodIterator.next().invoke(new Object());
            } catch (ReflectiveOperationException ignored) {}
        }
        if (output != null) cir.setReturnValue(output);
    }
}
