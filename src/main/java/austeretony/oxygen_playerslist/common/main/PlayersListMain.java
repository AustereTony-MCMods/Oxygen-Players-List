package austeretony.oxygen_playerslist.common.main;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import austeretony.oxygen.client.core.api.ClientReference;
import austeretony.oxygen.common.api.OxygenGUIHelper;
import austeretony.oxygen.common.api.OxygenHelperServer;
import austeretony.oxygen.common.core.api.CommonReference;
import austeretony.oxygen.common.main.OxygenMain;
import austeretony.oxygen_playerslist.client.input.PlayersListKeyHandler;
import austeretony.oxygen_playerslist.common.network.client.CPPlayersListCommand;
import austeretony.oxygen_playerslist.common.network.server.SPPlayersListRequest;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(
        modid = PlayersListMain.MODID, 
        name = PlayersListMain.NAME, 
        version = PlayersListMain.VERSION,
        dependencies = "required-after:oxygen@[0.7.0,);",//TODO Always check required Oxygen version before build
        certificateFingerprint = "@FINGERPRINT@",
        updateJSON = PlayersListMain.VERSIONS_FORGE_URL)
public class PlayersListMain {

    public static final String 
    MODID = "oxygen_playerslist",    
    NAME = "Oxygen: Players List",
    VERSION = "0.1.0",
    VERSION_CUSTOM = VERSION + ":alpha:0",
    GAME_VERSION = "1.12.2",
    VERSIONS_FORGE_URL = "https://raw.githubusercontent.com/AustereTony-MCMods/Oxygen-Players-List/info/mod_versions_forge.json";

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static final int 
    PLAYER_LIST_MOD_INDEX = 5,

    PLAYER_LIST_SCREEN_ID = 50;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        OxygenMain.network().registerPacket(CPPlayersListCommand.class);
        OxygenMain.network().registerPacket(SPPlayersListRequest.class);

        OxygenHelperServer.registerSharedDataIdentifierForScreen(PLAYER_LIST_SCREEN_ID, OxygenMain.ACTIVITY_STATUS_SHARED_DATA_ID);
        OxygenHelperServer.registerSharedDataIdentifierForScreen(PLAYER_LIST_SCREEN_ID, OxygenMain.DIMENSION_SHARED_DATA_ID);

        if (event.getSide() == Side.CLIENT) {    
            CommonReference.registerEvent(new PlayersListKeyHandler());

            OxygenGUIHelper.registerScreenId(PLAYER_LIST_SCREEN_ID);

            OxygenGUIHelper.registerSharedDataListenerScreen(PLAYER_LIST_SCREEN_ID);

            //disabling and removing vanilla Tab key binding
            ClientReference.getGameSettings().keyBindPlayerList.setKeyCode(0);
            ClientReference.getGameSettings().keyBindings = ArrayUtils.remove(ClientReference.getGameSettings().keyBindings, 12); 
        }
    }
}
