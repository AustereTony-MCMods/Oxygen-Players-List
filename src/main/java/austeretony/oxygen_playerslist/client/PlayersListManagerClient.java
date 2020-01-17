package austeretony.oxygen_playerslist.client;

import austeretony.oxygen_core.common.api.CommonReference;
import austeretony.oxygen_playerslist.client.input.PlayersListKeyHandler;

public class PlayersListManagerClient {

    private static PlayersListManagerClient instance;

    private final PlayersListKeyHandler keyHandler = new PlayersListKeyHandler();

    private PlayersListManagerClient() {
        CommonReference.registerEvent(this.keyHandler);
    }

    public static void create() {
        if (instance == null)
            instance = new PlayersListManagerClient();
    }

    public static PlayersListManagerClient instance() {
        return instance;
    }

    public PlayersListKeyHandler getKeyHandler() {
        return this.keyHandler;
    }
}
