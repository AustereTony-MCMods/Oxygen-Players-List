package austeretony.oxygen_playerslist.client;

import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.shared.SharedDataSyncManagerClient.SharedDataSyncListener;
import austeretony.oxygen_playerslist.client.gui.playerslist.PlayersListScreen;

public class PlayersListSharedDataListener implements SharedDataSyncListener {

    @Override
    public void synced() {
        ClientReference.delegateToClientThread(()->{
            if (ClientReference.hasActiveGUI() && ClientReference.getCurrentScreen() instanceof PlayersListScreen)
                ((PlayersListScreen) ClientReference.getCurrentScreen()).sharedDataSynchronized();
        });
    }
}
