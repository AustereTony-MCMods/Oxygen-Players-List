package austeretony.oxygen_playerslist.common.config;

import java.util.List;

import austeretony.oxygen_core.common.api.CommonReference;
import austeretony.oxygen_core.common.api.config.AbstractConfig;
import austeretony.oxygen_core.common.config.ConfigValue;
import austeretony.oxygen_core.common.config.ConfigValueUtils;
import austeretony.oxygen_playerslist.common.main.PlayersListMain;

public class PlayersListConfig extends AbstractConfig {

    public static final ConfigValue
    ENABLE_PLAYERSLIST_KEY = ConfigValueUtils.getValue("client", "enable_players_list_key", true),
    PLAYERSLIST_KEY = ConfigValueUtils.getValue("client", "players_list_key", 15);

    @Override
    public String getDomain() {
        return PlayersListMain.MODID;
    }

    @Override
    public String getVersion() {
        return PlayersListMain.VERSION_CUSTOM;
    }

    @Override
    public String getExternalPath() {
        return CommonReference.getGameFolder() + "/config/oxygen/players-list.json";
    }

    @Override
    public void getValues(List<ConfigValue> values) {
        values.add(ENABLE_PLAYERSLIST_KEY);
        values.add(PLAYERSLIST_KEY);
    }
}
