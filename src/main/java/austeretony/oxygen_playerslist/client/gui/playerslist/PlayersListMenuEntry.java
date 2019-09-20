package austeretony.oxygen_playerslist.client.gui.playerslist;

import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.gui.menu.AbstractMenuEntry;

public class PlayersListMenuEntry extends AbstractMenuEntry {

    @Override
    public String getName() {
        return "oxygen_playerslist.gui.playerslist.title";
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void open() {
        ClientReference.displayGuiScreen(new PlayersListGUIScreen());
    }
}
