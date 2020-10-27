package sschr15.qol.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sschr15.qol.api.annotations.qolevents.QOLSubscriber;
import sschr15.qol.util.QOLSubscriberCaller;

import net.minecraft.util.HttpUtil;

import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import java.util.TimerTask;

/**
 * :)
 */
@Mixin(targets = "net/minecraft/profiler/Snooper$1")
public abstract class AntiSnooperMixin extends TimerTask {
    @Redirect(method = "run()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/HttpUtil;postMap(Ljava/net/URL;Ljava/util/Map;ZLjava/net/Proxy;)Ljava/lang/String;"))
    private String stopPostMap(URL url, Map<String, Object> map, boolean skipLoggingErrors, Proxy proxy) {
        boolean disable = QOLSubscriberCaller.getQolSubscribers().stream()
                .map(c -> c.getAnnotation(QOLSubscriber.class))
                .anyMatch(QOLSubscriber::disableSnooper);
        return disable ? null : HttpUtil.postMap(url, map, skipLoggingErrors, proxy);
    }
}
