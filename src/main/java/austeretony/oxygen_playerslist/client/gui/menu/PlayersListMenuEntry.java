package austeretony.oxygen_playerslist.client.gui.menu;

import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.gui.menu.OxygenMenuEntry;
import austeretony.oxygen_playerslist.client.gui.playerslist.PlayersListScreen;
import austeretony.oxygen_playerslist.client.settings.EnumPlayersListClientSetting;
import austeretony.oxygen_playerslist.common.config.PlayersListConfig;
import austeretony.oxygen_playerslist.common.main.PlayersListMain;

public class PlayersListMenuEntry implements OxygenMenuEntry {

    @Override
    public int getId() {
        return PlayersListMain.PLAYER_LIST_MENU_SCREEN_ID;
    }

    @Override
    public String getLocalizedName() {
        return ClientReference.localize("oxygen_playerslist.gui.playerslist.title");
    }

    @Override
    public int getKeyCode() {
        return PlayersListConfig.PLAYERSLIST_KEY.asInt();
    }

    @Override
    public boolean isValid() {
        return EnumPlayersListClientSetting.ADD_PLAYERS_LIST.get().asBoolean();
    }

    @Override
    public void open() {
        ClientReference.displayGuiScreen(new PlayersListScreen());
    }
}
