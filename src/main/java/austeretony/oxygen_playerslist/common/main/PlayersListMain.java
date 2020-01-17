package austeretony.oxygen_playerslist.common.main;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.api.OxygenGUIHelper;
import austeretony.oxygen_core.client.api.OxygenHelperClient;
import austeretony.oxygen_core.client.command.CommandOxygenClient;
import austeretony.oxygen_core.client.gui.settings.SettingsScreen;
import austeretony.oxygen_core.common.api.CommonReference;
import austeretony.oxygen_core.common.api.OxygenHelperCommon;
import austeretony.oxygen_playerslist.client.PlayersListManagerClient;
import austeretony.oxygen_playerslist.client.PlayersListSharedDataListener;
import austeretony.oxygen_playerslist.client.command.PlayersListArgumentClient;
import austeretony.oxygen_playerslist.client.gui.playerslist.PlayersListScreen;
import austeretony.oxygen_playerslist.client.gui.settings.PlayersListSettingsContainer;
import austeretony.oxygen_playerslist.client.input.PlayersListKeyHandler;
import austeretony.oxygen_playerslist.client.settings.EnumPlayersListClientSetting;
import austeretony.oxygen_playerslist.client.settings.gui.EnumPlayersListGUISetting;
import austeretony.oxygen_playerslist.common.config.PlayersListConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(
        modid = PlayersListMain.MODID, 
        name = PlayersListMain.NAME, 
        version = PlayersListMain.VERSION,
        dependencies = "required-after:oxygen_core@[0.10.0,);",
        clientSideOnly = true,
        certificateFingerprint = "@FINGERPRINT@",
        updateJSON = PlayersListMain.VERSIONS_FORGE_URL)
public class PlayersListMain {

    public static final String 
    MODID = "oxygen_playerslist",    
    NAME = "Oxygen: Players List",
    VERSION = "0.10.0",
    VERSION_CUSTOM = VERSION + ":beta:0",
    GAME_VERSION = "1.12.2",
    VERSIONS_FORGE_URL = "https://raw.githubusercontent.com/AustereTony-MCMods/Oxygen-Players-List/info/mod_versions_forge.json";

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static final int 
    PLAYER_LIST_MOD_INDEX = 5,

    PLAYER_LIST_MENU_SCREEN_ID = 50;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        OxygenHelperCommon.registerConfig(new PlayersListConfig());
        if (event.getSide() == Side.CLIENT)
            CommandOxygenClient.registerArgument(new PlayersListArgumentClient());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {    
            PlayersListManagerClient.create();
            OxygenGUIHelper.registerScreenId(PLAYER_LIST_MENU_SCREEN_ID);
            OxygenGUIHelper.registerOxygenMenuEntry(PlayersListScreen.PLAYERS_LIST_MENU_ENTRY);
            OxygenHelperClient.registerSharedDataSyncListener(PLAYER_LIST_MENU_SCREEN_ID, new PlayersListSharedDataListener());
            EnumPlayersListClientSetting.register();
            EnumPlayersListGUISetting.register();
            SettingsScreen.registerSettingsContainer(new PlayersListSettingsContainer());

            //disabling and removing vanilla 'Tab Overlay' key binding
            ClientReference.getGameSettings().keyBindPlayerList.setKeyCode(0);
            ClientReference.getGameSettings().keyBindings = ArrayUtils.remove(ClientReference.getGameSettings().keyBindings, 12); 
        }
    }
}
