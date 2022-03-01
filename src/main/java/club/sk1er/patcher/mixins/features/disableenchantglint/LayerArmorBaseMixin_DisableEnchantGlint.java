package club.sk1er.patcher.mixins.features.disableenchantglint;

import club.sk1er.patcher.config.PatcherConfig;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArmorBase.class)
public class LayerArmorBaseMixin_DisableEnchantGlint {
    @Inject(method = "renderGlint", at = @At("HEAD"), cancellable = true)
    //#if MC==10809
    private void patcher$disableEnchantGlint(CallbackInfo ci) {
    //#else
    //$$ private static void patcher$disableEnchantGlint(CallbackInfo ci) {
    //#endif
        if (PatcherConfig.disableEnchantmentGlint) ci.cancel();
    }
}
