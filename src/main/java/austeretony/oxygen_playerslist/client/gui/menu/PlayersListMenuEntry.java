package austeretony.oxygen_playerslist.client.gui.menu;

import org.lwjgl.input.Keyboard;

import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.gui.menu.OxygenMenuEntry;
import austeretony.oxygen_playerslist.client.gui.playerslist.PlayersListScreen;
import austeretony.oxygen_playerslist.client.settings.EnumPlayersListClientSetting;

public class PlayersListMenuEntry implements OxygenMenuEntry {

    @Override
    public int getId() {
        return 50;
    }

    @Override
    public String getLocalizedName() {
        return ClientReference.localize("oxygen_playerslist.gui.playerslist.title");
    }

    @Override
    public int getKeyCode() {
        return Keyboard.KEY_TAB;
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
