package club.sk1er.patcher.screen.render.overlay;

import club.sk1er.patcher.Patcher;
import club.sk1er.patcher.config.PatcherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class OverlayHandler {

    private boolean toggledTab;
    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void keyPress(InputEvent.KeyInputEvent event) {
        if (mc.gameSettings.keyBindPlayerList.isPressed()) this.toggledTab = !this.toggledTab;
    }

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent.Post event) {
        //#if MC==10809
        RenderGameOverlayEvent.ElementType type = event.type;
        ScaledResolution res = event.resolution;
        //#else
        //$$ RenderGameOverlayEvent.ElementType type = event.getType();
        //$$ ScaledResolution res = event.getResolution();
        //#endif
        if (type != RenderGameOverlayEvent.ElementType.ALL) return;

        final int scaledWidth = res.getScaledWidth();

        final Scoreboard scoreboard = mc.theWorld.getScoreboard();
        final ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(0);

        if (PatcherConfig.toggleTab && this.toggledTab && !mc.gameSettings.keyBindPlayerList.isKeyDown()) {
            final GuiPlayerTabOverlay tabOverlay = mc.ingameGUI.getTabList();
            if (mc.isIntegratedServerRunning() && mc.thePlayer.sendQueue.getPlayerInfoMap().size() <= 1 && objective == null) {
                tabOverlay.updatePlayerList(false);
            } else {
                tabOverlay.updatePlayerList(true);
                tabOverlay.renderPlayerlist(scaledWidth, scoreboard, objective);
            }
        }
    }
}
