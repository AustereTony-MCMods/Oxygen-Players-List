package austeretony.oxygen_playerslist.client;

import austeretony.oxygen.client.core.api.ClientReference;
import austeretony.oxygen.common.api.OxygenGUIHelper;
import austeretony.oxygen.common.main.OxygenMain;
import austeretony.oxygen_playerslist.client.gui.playerslist.PlayersListGUIScreen;
import austeretony.oxygen_playerslist.common.main.PlayersListMain;
import austeretony.oxygen_playerslist.common.network.server.SPPlayersListRequest;

public class PlayersListManagerClient {

    public static void openPlayersListSynced() {
        OxygenGUIHelper.needSync(PlayersListMain.PLAYER_LIST_SCREEN_ID);
        OxygenMain.network().sendToServer(new SPPlayersListRequest(SPPlayersListRequest.EnumRequest.OPEN_PLAYERS_LIST));
    }

    public static void openPlayersListDelegated() {
        ClientReference.getMinecraft().addScheduledTask(new Runnable() {//because of OpenGL context available only in main client thread

            @Override
            public void run() {
                openPlayersList();
            }
        });
    }

    private static void openPlayersList() {
        ClientReference.displayGuiScreen(new PlayersListGUIScreen());
    }
}
