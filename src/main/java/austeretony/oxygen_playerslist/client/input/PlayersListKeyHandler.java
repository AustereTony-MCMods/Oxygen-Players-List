package austeretony.oxygen_playerslist.client.input;

import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.api.OxygenGUIHelper;
import austeretony.oxygen_playerslist.client.gui.playerslist.PlayersListScreen;
import austeretony.oxygen_playerslist.common.config.PlayersListConfig;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class PlayersListKeyHandler {

    private KeyBinding playersListKeybinding;

    public PlayersListKeyHandler() {        
        if (PlayersListConfig.ENABLE_PLAYERSLIST_KEY.asBoolean() && !OxygenGUIHelper.isOxygenMenuEnabled())
            ClientReference.registerKeyBinding(this.playersListKeybinding = new KeyBinding("key.oxygen_playerslist.playersList", PlayersListConfig.PLAYERSLIST_KEY.asInt(), "Oxygen")); 
    }

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {        
        if (this.playersListKeybinding != null && this.playersListKeybinding.isPressed())
            ClientReference.displayGuiScreen(new PlayersListScreen());
    }

    public KeyBinding getPlayersListKeybinding() {
        return this.playersListKeybinding;
    }
}
