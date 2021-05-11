package austeretony.oxygen_player_list.client.settings;

import austeretony.oxygen_core.client.api.OxygenClient;
import austeretony.oxygen_core.client.gui.base.Alignment;
import austeretony.oxygen_core.client.settings.SettingType;
import austeretony.oxygen_core.client.settings.SettingValue;
import austeretony.oxygen_core.client.settings.gui.SettingWidgets;
import austeretony.oxygen_core.common.util.value.ValueType;
import austeretony.oxygen_player_list.common.main.PlayerListMain;

public final class PlayerListSettings {

    public static final SettingValue
    PLAYER_LIST_SCREEN_ALIGNMENT = OxygenClient.registerSetting(PlayerListMain.MOD_ID, SettingType.INTERFACE, "Player List", "alignment",
            ValueType.STRING, "player_list_screen_alignment", Alignment.CENTER.toString(), SettingWidgets.screenAlignmentList()),

    ADD_PLAYER_LIST_SCREEN_TO_OXYGEN_MENU = OxygenClient.registerSetting(PlayerListMain.MOD_ID, SettingType.COMMON, "Player List", "oxygen_menu",
            ValueType.BOOLEAN, "add_player_list_screen", true, SettingWidgets.checkBox());

    private PlayerListSettings() {}

    public static void register() {}
}
