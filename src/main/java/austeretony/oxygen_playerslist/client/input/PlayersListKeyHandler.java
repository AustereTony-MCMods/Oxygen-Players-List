package austeretony.oxygen_playerslist.client.input;

import org.lwjgl.input.Keyboard;

import austeretony.oxygen.client.core.api.ClientReference;
import austeretony.oxygen.common.main.OxygenMain;
import austeretony.oxygen_playerslist.client.PlayersListManagerClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class PlayersListKeyHandler {

    public static final KeyBinding PLAYERS_LIST = new KeyBinding("key.oxygen_playerslist.openPlayersList", Keyboard.KEY_TAB, OxygenMain.NAME);

    public PlayersListKeyHandler() {
        ClientReference.registerKeyBinding(PLAYERS_LIST);
    }

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {        
        if (PLAYERS_LIST.isPressed())
            PlayersListManagerClient.openPlayersListSynced();
    }
}
