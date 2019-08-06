package austeretony.oxygen_playerslist.client.gui.playerslist;

import austeretony.oxygen.client.core.api.ClientReference;
import austeretony.oxygen.client.sync.gui.api.IGUIHandlerClient;

public class PlayersListGUIHandler implements IGUIHandlerClient {

    @Override
    public void open() {
        ClientReference.displayGuiScreen(new PlayersListGUIScreen());
    }
}
