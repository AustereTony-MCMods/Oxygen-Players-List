package austeretony.oxygen_player_list.client.gui.player_list;

import austeretony.oxygen_core.client.api.OxygenClient;
import austeretony.oxygen_core.client.gui.base.Alignment;
import austeretony.oxygen_core.client.gui.base.core.OxygenScreen;
import austeretony.oxygen_core.client.gui.base.core.Section;
import austeretony.oxygen_core.client.gui.base.core.Workspace;
import austeretony.oxygen_core.client.gui.menu.OxygenMenuEntry;
import austeretony.oxygen_core.client.util.MinecraftClient;
import austeretony.oxygen_player_list.client.gui.menu.PlayerListScreenEntry;
import austeretony.oxygen_player_list.client.settings.PlayerListSettings;
import austeretony.oxygen_player_list.common.main.PlayerListMain;
import net.minecraft.client.gui.GuiScreen;

public class PlayerListScreen extends OxygenScreen {

    public static final OxygenMenuEntry PLAYER_LIST_SCREEN_ENTRY = new PlayerListScreenEntry();

    private PlayerListSection section;

    @Override
    public void initGui() {
        super.initGui();
        OxygenClient.requestSharedDataSync(PlayerListMain.SCREEN_ID_PLAYER_LIST, false);
    }

    @Override
    public int getScreenId() {
        return PlayerListMain.SCREEN_ID_PLAYER_LIST;
    }

    @Override
    public Workspace createWorkspace() {
        Workspace workspace = new Workspace(this, 200, 218);
        workspace.setAlignment(Alignment.valueOf(PlayerListSettings.PLAYER_LIST_SCREEN_ALIGNMENT.asString()), 0, 0);
        return workspace;
    }

    @Override
    public void addSections() {
        getWorkspace().addSection(section = new PlayerListSection(this));
    }

    @Override
    public Section getDefaultSection() {
        return section;
    }

    public static void open() {
        MinecraftClient.displayGuiScreen(new PlayerListScreen());
    }

    public static void sharedDataSynchronized() {
        GuiScreen screen = MinecraftClient.getCurrentScreen();
        if (screen instanceof PlayerListScreen) {
            ((PlayerListScreen) screen).section.sharedDataSynchronized();
        }
    }
}
