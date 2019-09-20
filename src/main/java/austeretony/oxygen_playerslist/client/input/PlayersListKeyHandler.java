package austeretony.oxygen_playerslist.client.input;

import org.lwjgl.input.Keyboard;

import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_playerslist.client.gui.playerslist.PlayersListGUIScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class PlayersListKeyHandler {

    public static final KeyBinding PLAYERS_LIST = new KeyBinding("key.oxygen_playerslist.openPlayersList", Keyboard.KEY_TAB, "Oxygen");

    public PlayersListKeyHandler() {
        ClientReference.registerKeyBinding(PLAYERS_LIST);
    }

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {        
        if (PLAYERS_LIST.isPressed())
            ClientReference.displayGuiScreen(new PlayersListGUIScreen());
    }
}
