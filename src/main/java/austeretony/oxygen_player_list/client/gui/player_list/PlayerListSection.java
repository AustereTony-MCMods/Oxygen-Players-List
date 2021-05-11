package austeretony.oxygen_player_list.client.gui.player_list;

import austeretony.oxygen_core.client.api.OxygenClient;
import austeretony.oxygen_core.client.api.PrivilegesClient;
import austeretony.oxygen_core.client.gui.base.Texts;
import austeretony.oxygen_core.client.gui.base.background.Background;
import austeretony.oxygen_core.client.gui.base.button.VerticalSlider;
import austeretony.oxygen_core.client.gui.base.context.ContextAction;
import austeretony.oxygen_core.client.gui.base.core.Section;
import austeretony.oxygen_core.client.gui.base.list.ScrollableList;
import austeretony.oxygen_core.client.gui.base.special.ActivityStatusSwitcher;
import austeretony.oxygen_core.client.gui.base.special.Sorter;
import austeretony.oxygen_core.client.gui.base.text.TextField;
import austeretony.oxygen_core.client.gui.base.text.TextLabel;
import austeretony.oxygen_core.client.gui.context.ContextActionsRegistry;
import austeretony.oxygen_core.client.gui.util.OxygenGUIUtils;
import austeretony.oxygen_core.client.gui.util.SharedDataListSorter;
import austeretony.oxygen_core.common.main.CorePrivileges;
import austeretony.oxygen_core.common.main.OxygenMain;
import austeretony.oxygen_core.common.player.ActivityStatus;
import austeretony.oxygen_core.common.player.shared.PlayerSharedData;
import austeretony.oxygen_player_list.common.main.PlayerListMain;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerListSection extends Section {

    private ScrollableList<UUID> playersList;
    private TextField usernameField;
    private TextLabel playersAmountLabel;
    private Sorter statusSorter, usernameSorter, dimensionSorter;
    private ActivityStatusSwitcher statusSwitcher;

    public PlayerListSection(PlayerListScreen screen) {
        super(screen, "", true);
    }

    @Override
    public void init() {
        addWidget(new Background.UnderlinedTitle(this));
        addWidget(new TextLabel(4, 12, Texts.title("oxygen_player_list.gui.player_list.title")));

        addWidget(playersAmountLabel = new TextLabel(0, 22, Texts.additionalDark("")));
        addWidget(statusSwitcher = new ActivityStatusSwitcher(100, 15, true));

        addWidget(playersList = new ScrollableList<>(6, 30, 17, getWidth() - 6 * 2 - 3, 10));
        VerticalSlider slider = new VerticalSlider(6 + playersList.getWidth() + 1, 30, 2, 186);
        addWidget(slider);
        playersList.setSlider(slider);
        addWidget(usernameField = new TextField(6, 15, 70, OxygenMain.USERNAME_FIELD_LENGTH));
        playersList.setSearchField(usernameField);

        List<ContextAction<UUID>> contextActions = new ArrayList<>();
        ContextActionsRegistry.getContextActions(PlayerListMain.SCREEN_ID_PLAYER_LIST)
                .forEach(contextActions::add);
        if (!contextActions.isEmpty()) {
            playersList.createContextMenu(contextActions);
        }

        addWidget(statusSorter = new Sorter(13, 25, Sorter.State.DOWN, localize("oxygen_core.gui.sorter.by_activity_status"))
                .setStateChangeListener((previous, current) -> {
                    usernameSorter.setState(Sorter.State.INACTIVE);
                    dimensionSorter.setState(Sorter.State.INACTIVE);

                    sortPlayersList(current == Sorter.State.DOWN
                            ? SharedDataListSorter.BY_ACTIVITY_STATUS : SharedDataListSorter.BY_ACTIVITY_STATUS_REVERSED);
                }));
        addWidget(usernameSorter = new Sorter(19, 25, Sorter.State.INACTIVE, localize("oxygen_core.gui.sorter.by_username"))
                .setStateChangeListener((previous, current) -> {
                    statusSorter.setState(Sorter.State.INACTIVE);
                    dimensionSorter.setState(Sorter.State.INACTIVE);

                    sortPlayersList(current == Sorter.State.DOWN
                            ? SharedDataListSorter.BY_USERNAME : SharedDataListSorter.BY_USERNAME_REVERSED);
                }));
        addWidget(dimensionSorter = new Sorter(25, 25, Sorter.State.INACTIVE, localize("oxygen_core.gui.sorter.by_dimension_name"))
                .setStateChangeListener((previous, current) -> {
                    statusSorter.setState(Sorter.State.INACTIVE);
                    usernameSorter.setState(Sorter.State.INACTIVE);

                    sortPlayersList(current == Sorter.State.DOWN
                            ? SharedDataListSorter.BY_DIMENSION_NAME : SharedDataListSorter.BY_DIMENSION_NAME_REVERSED);
                }));
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (!usernameField.isFocused()) {
            OxygenGUIUtils.closeScreenOnKeyPress(getScreen(), keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    private void sortPlayersList(SharedDataListSorter sorter) {
        playersList.clear();
        List<PlayerSharedData> onlinePlayers = OxygenClient.getOnlinePlayersSharedData()
                .stream()
                .filter(e -> OxygenClient.getPlayerActivityStatus(e) != ActivityStatus.OFFLINE
                        || PrivilegesClient.getBoolean(CorePrivileges.EXPOSE_OFFLINE_PLAYERS.getId(), false))
                .collect(Collectors.toList());
        sorter.getConsumer().accept(onlinePlayers);

        for (PlayerSharedData sharedData : onlinePlayers) {
            playersList.addElement(new PlayerListEntry(sharedData));
        }

        String amountStr = onlinePlayers.size() + "/" + OxygenClient.getMaxPlayers();
        playersAmountLabel.getText().setText(amountStr);
        playersAmountLabel.setX(getWidth() - 6 - (int) playersAmountLabel.getText().getWidth());
    }

    public void sharedDataSynchronized() {
        statusSwitcher.updateStatus();
        sortPlayersList(SharedDataListSorter.BY_ACTIVITY_STATUS);
    }
}
