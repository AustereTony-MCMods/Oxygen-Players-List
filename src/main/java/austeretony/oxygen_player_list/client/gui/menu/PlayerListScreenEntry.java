package austeretony.oxygen_player_list.client.gui.menu;

import austeretony.oxygen_core.client.gui.menu.OxygenMenuEntry;
import austeretony.oxygen_core.client.util.MinecraftClient;
import austeretony.oxygen_player_list.client.settings.PlayerListSettings;
import austeretony.oxygen_player_list.common.config.PlayerListConfig;
import austeretony.oxygen_player_list.common.main.PlayerListMain;
import net.minecraft.util.ResourceLocation;

public class PlayerListScreenEntry implements OxygenMenuEntry {

    private static final ResourceLocation ICON = new ResourceLocation(PlayerListMain.MOD_ID,
            "textures/gui/menu/player_list.png");

    @Override
    public int getScreenId() {
        return PlayerListMain.SCREEN_ID_PLAYER_LIST;
    }

    @Override
    public String getDisplayName() {
        return MinecraftClient.localize("oxygen_player_list.gui.player_list.title");
    }

    @Override
    public int getPriority() {
        return 300;
    }

    @Override
    public ResourceLocation getIconTexture() {
        return ICON;
    }

    @Override
    public int getKeyCode() {
        return PlayerListConfig.PLAYER_LIST_SCREEN_KEY_ID.asInt();
    }

    @Override
    public boolean isValid() {
        return PlayerListSettings.ADD_PLAYER_LIST_SCREEN_TO_OXYGEN_MENU.asBoolean();
    }
}
