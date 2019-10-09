package austeretony.oxygen_playerslist.common.main;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.api.OxygenGUIHelper;
import austeretony.oxygen_core.client.api.OxygenHelperClient;
import austeretony.oxygen_core.common.api.CommonReference;
import austeretony.oxygen_playerslist.client.PlayersListSharedDataListener;
import austeretony.oxygen_playerslist.client.gui.playerslist.PlayersListGUIScreen;
import austeretony.oxygen_playerslist.client.input.PlayersListKeyHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(
        modid = PlayersListMain.MODID, 
        name = PlayersListMain.NAME, 
        version = PlayersListMain.VERSION,
        dependencies = "required-after:oxygen_core@[0.9.2,);",
        clientSideOnly = true,
        certificateFingerprint = "@FINGERPRINT@",
        updateJSON = PlayersListMain.VERSIONS_FORGE_URL)
public class PlayersListMain {

    public static final String 
    MODID = "oxygen_playerslist",    
    NAME = "Oxygen: Players List",
    VERSION = "0.9.1",
    VERSION_CUSTOM = VERSION + ":beta:0",
    GAME_VERSION = "1.12.2",
    VERSIONS_FORGE_URL = "https://raw.githubusercontent.com/AustereTony-MCMods/Oxygen-Players-List/info/mod_versions_forge.json";

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static final int 
    PLAYER_LIST_MOD_INDEX = 5,

    PLAYER_LIST_MENU_SCREEN_ID = 50;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {    
            if (!OxygenGUIHelper.isOxygenMenuEnabled())
                CommonReference.registerEvent(new PlayersListKeyHandler());
            OxygenGUIHelper.registerScreenId(PLAYER_LIST_MENU_SCREEN_ID);
            OxygenGUIHelper.registerOxygenMenuEntry(PlayersListGUIScreen.PLAYERS_LIST_MENU_ENTRY);
            OxygenHelperClient.registerSharedDataSyncListener(PLAYER_LIST_MENU_SCREEN_ID, new PlayersListSharedDataListener());
            //disabling and removing vanilla 'Tab Overlay' key binding
            ClientReference.getGameSettings().keyBindPlayerList.setKeyCode(0);
            ClientReference.getGameSettings().keyBindings = ArrayUtils.remove(ClientReference.getGameSettings().keyBindings, 12); 
        }
    }
}
