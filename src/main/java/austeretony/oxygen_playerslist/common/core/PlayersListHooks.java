package austeretony.oxygen_playerslist.common.core;

public class PlayersListHooks {

    //Hook to <net.minecraft.client.gui.GuiIngame> class to <renderGameOverlay()> method.
    //Hook to <net.minecraftforge.client.GuiIngameForge> class to <renderPlayerList()> method.
    public static boolean isTabOverlayEnabled() {
        return false;
    }
}
