package austeretony.oxygen_player_list.common.main;

import austeretony.oxygen_core.client.api.OxygenClient;
import austeretony.oxygen_core.client.command.CommandOxygenClient;
import austeretony.oxygen_core.client.gui.menu.OxygenMenuHelper;
import austeretony.oxygen_core.client.util.MinecraftClient;
import austeretony.oxygen_core.common.api.OxygenCommon;
import austeretony.oxygen_core.common.main.OxygenMain;
import austeretony.oxygen_core.common.util.MinecraftCommon;
import austeretony.oxygen_player_list.client.command.PlayerListArgumentClient;
import austeretony.oxygen_player_list.client.event.PlayerListEventsClient;
import austeretony.oxygen_player_list.client.gui.player_list.PlayerListScreen;
import austeretony.oxygen_player_list.client.settings.PlayerListSettings;
import austeretony.oxygen_player_list.common.config.PlayerListConfig;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.ArrayUtils;

@Mod(
        modid = PlayerListMain.MOD_ID,
        name = PlayerListMain.NAME,
        version = PlayerListMain.VERSION,
        dependencies = "required-after:oxygen_core@[0.12.0,);",
        clientSideOnly = true,
        certificateFingerprint = "@FINGERPRINT@",
        updateJSON = PlayerListMain.VERSIONS_FORGE_URL)
public class PlayerListMain {

    public static final String
            MOD_ID = "oxygen_player_list",
            NAME = "Oxygen: Player List",
            VERSION = "0.12.0",
            VERSION_CUSTOM = VERSION + ":beta:0",
            VERSIONS_FORGE_URL = "https://raw.githubusercontent.com/AustereTony-MCMods/Oxygen-Players-List/info/versions.json";

    //oxygen module index
    public static final int MODULE_INDEX = 3;

    //screen id
    public static final int SCREEN_ID_PLAYER_LIST = 30;

    //key binding id
    public static final int KEYBINDING_ID_OPEN_PLAYER_LIST = 30;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        OxygenCommon.registerConfig(new PlayerListConfig());
        if (event.getSide() == Side.CLIENT) {
            CommandOxygenClient.registerArgument(new PlayerListArgumentClient());
            OxygenClient.registerKeyBind(
                    KEYBINDING_ID_OPEN_PLAYER_LIST,
                    "key.oxygen_player_list.open_player_list",
                    OxygenMain.KEY_BINDINGS_CATEGORY,
                    PlayerListConfig.PLAYER_LIST_SCREEN_KEY_ID::asInt,
                    PlayerListConfig.ENABLE_PLAYER_LIST_SCREEN_KEY::asBoolean,
                    true,
                    () -> OxygenClient.openScreen(SCREEN_ID_PLAYER_LIST));
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            MinecraftCommon.registerEventHandler(new PlayerListEventsClient());
            PlayerListSettings.register();
            OxygenMenuHelper.addMenuEntry(PlayerListScreen.PLAYER_LIST_SCREEN_ENTRY);
            OxygenClient.registerSharedDataSyncListener(SCREEN_ID_PLAYER_LIST, PlayerListScreen::sharedDataSynchronized);
            OxygenClient.registerScreen(SCREEN_ID_PLAYER_LIST, PlayerListScreen::open);

            //disabling and removing vanilla 'Tab Overlay' key binding
            GameSettings settings = MinecraftClient.getGameSettings();
            settings.keyBindPlayerList.setKeyCode(0);
            settings.keyBindings = ArrayUtils.remove(settings.keyBindings, 12);
        }
    }
}
