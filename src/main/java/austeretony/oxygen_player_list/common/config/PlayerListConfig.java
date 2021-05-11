package austeretony.oxygen_player_list.common.config;

import austeretony.oxygen_core.common.config.AbstractConfig;
import austeretony.oxygen_core.common.config.ConfigValue;
import austeretony.oxygen_core.common.config.ConfigValueUtils;
import austeretony.oxygen_player_list.common.main.PlayerListMain;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class PlayerListConfig extends AbstractConfig {

    public static final ConfigValue
            ENABLE_PLAYER_LIST_SCREEN_KEY = ConfigValueUtils.getBoolean("client", "enable_player_list_screen_key", true),
            PLAYER_LIST_SCREEN_KEY_ID = ConfigValueUtils.getInt("client", "player_list_screen_key_id", Keyboard.KEY_TAB);

    @Override
    public String getDomain() {
        return PlayerListMain.MOD_ID;
    }

    @Override
    public String getVersion() {
        return PlayerListMain.VERSION_CUSTOM;
    }

    @Override
    public String getFileName() {
        return "player_list.json";
    }

    @Override
    public void getValues(List<ConfigValue> values) {
        values.add(ENABLE_PLAYER_LIST_SCREEN_KEY);
        values.add(PLAYER_LIST_SCREEN_KEY_ID);
    }
}
