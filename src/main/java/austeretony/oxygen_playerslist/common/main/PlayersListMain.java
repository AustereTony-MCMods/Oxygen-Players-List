package austeretony.oxygen_playerslist.common.main;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import austeretony.oxygen.client.api.OxygenGUIHelper;
import austeretony.oxygen.client.core.api.ClientReference;
import austeretony.oxygen.common.api.OxygenHelperServer;
import austeretony.oxygen.common.core.api.CommonReference;
import austeretony.oxygen.common.main.OxygenMain;
import austeretony.oxygen_playerslist.client.gui.playerslist.PlayersListGUIHandler;
import austeretony.oxygen_playerslist.client.input.PlayersListKeyHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(
        modid = PlayersListMain.MODID, 
        name = PlayersListMain.NAME, 
        version = PlayersListMain.VERSION,
        dependencies = "required-after:oxygen@[0.8.0,);",//TODO Always check required Oxygen version before build
        certificateFingerprint = "@FINGERPRINT@",
        updateJSON = PlayersListMain.VERSIONS_FORGE_URL)
public class PlayersListMain {

    public static final String 
    MODID = "oxygen_playerslist",    
    NAME = "Oxygen: Players List",
    VERSION = "0.8.0",
    VERSION_CUSTOM = VERSION + ":beta:0",
    GAME_VERSION = "1.12.2",
    VERSIONS_FORGE_URL = "https://raw.githubusercontent.com/AustereTony-MCMods/Oxygen-Players-List/info/mod_versions_forge.json";

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static final int 
    PLAYER_LIST_MOD_INDEX = 5,//Teleportation - 1, Groups - 2, Exchange - 3, Merchants - 4, Friends List - 6, Interaction - 7, Mail - 8, Chat - 9

    PLAYER_LIST_MENU_SCREEN_ID = 50;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        OxygenHelperServer.registerSharedDataIdentifierForScreen(PLAYER_LIST_MENU_SCREEN_ID, OxygenMain.ACTIVITY_STATUS_SHARED_DATA_ID);
        OxygenHelperServer.registerSharedDataIdentifierForScreen(PLAYER_LIST_MENU_SCREEN_ID, OxygenMain.DIMENSION_SHARED_DATA_ID);
        if (event.getSide() == Side.CLIENT) {    
            CommonReference.registerEvent(new PlayersListKeyHandler());
            OxygenGUIHelper.registerScreenId(PLAYER_LIST_MENU_SCREEN_ID);
            OxygenGUIHelper.registerSharedDataListenerScreen(PLAYER_LIST_MENU_SCREEN_ID, new PlayersListGUIHandler());
            //disabling and removing vanilla 'Tab Overlay' key binding
            ClientReference.getGameSettings().keyBindPlayerList.setKeyCode(0);
            ClientReference.getGameSettings().keyBindings = ArrayUtils.remove(ClientReference.getGameSettings().keyBindings, 12); 
        }
    }
}
