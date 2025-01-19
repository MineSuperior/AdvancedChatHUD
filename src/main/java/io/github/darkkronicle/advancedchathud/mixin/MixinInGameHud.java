package io.github.darkkronicle.advancedchathud.mixin;

import fi.dy.masa.malilib.event.RenderEventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LayeredDrawer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InGameHud.class, priority = 1150)
public abstract class MixinInGameHud {
    @Shadow
    @Final
    private MinecraftClient client;
    @Shadow
    @Final
    private LayeredDrawer layeredDrawer;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        this.layeredDrawer.addLayer(this::renderChatOverwrite);
    }

    private void renderChatOverwrite(DrawContext context, RenderTickCounter tickCounter) {
        ((RenderEventHandler) RenderEventHandler.getInstance()).onRenderGameOverlayPost(context, this.client, tickCounter.getTickDelta(false));
    }
}
