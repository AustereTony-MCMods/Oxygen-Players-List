package austeretony.oxygen_playerslist.client.gui.playerslist;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import austeretony.alternateui.screen.core.AbstractGUISection;
import austeretony.alternateui.screen.core.GUIBaseElement;
import austeretony.oxygen_core.client.OxygenManagerClient;
import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.api.EnumBaseGUISetting;
import austeretony.oxygen_core.client.api.OxygenGUIHelper;
import austeretony.oxygen_core.client.api.OxygenHelperClient;
import austeretony.oxygen_core.client.api.PrivilegesProviderClient;
import austeretony.oxygen_core.client.gui.elements.OxygenActivityStatusSwitcher;
import austeretony.oxygen_core.client.gui.elements.OxygenContextMenu;
import austeretony.oxygen_core.client.gui.elements.OxygenContextMenu.OxygenContextMenuAction;
import austeretony.oxygen_core.client.gui.elements.OxygenDefaultBackgroundFiller;
import austeretony.oxygen_core.client.gui.elements.OxygenScrollablePanel;
import austeretony.oxygen_core.client.gui.elements.OxygenSorter;
import austeretony.oxygen_core.client.gui.elements.OxygenSorter.EnumSorting;
import austeretony.oxygen_core.client.gui.elements.OxygenTextField;
import austeretony.oxygen_core.client.gui.elements.OxygenTextLabel;
import austeretony.oxygen_core.common.EnumActivityStatus;
import austeretony.oxygen_core.common.PlayerSharedData;
import austeretony.oxygen_core.common.main.EnumOxygenPrivilege;
import austeretony.oxygen_core.common.util.MathUtils;
import austeretony.oxygen_playerslist.client.PlayersListManagerClient;
import austeretony.oxygen_playerslist.common.config.PlayersListConfig;
import austeretony.oxygen_playerslist.common.main.PlayersListMain;

public class PlayersListSection extends AbstractGUISection {

    private final PlayersListScreen screen;

    private OxygenTextLabel playersOnlineTextLabel;

    private OxygenActivityStatusSwitcher activityStatusDDList;

    private OxygenSorter statusSorter, usernameSorter;

    private OxygenTextField searchField;

    private OxygenScrollablePanel playersPanel;

    public PlayersListSection(PlayersListScreen screen) {
        super(screen);
        this.screen = screen;
    }

    @Override
    public void init() {
        this.addElement(new OxygenDefaultBackgroundFiller(0, 0, this.getWidth(), this.getHeight()));
        this.addElement(new OxygenTextLabel(4, 12, ClientReference.localize("oxygen_playerslist.gui.playerslist.title"), EnumBaseGUISetting.TEXT_TITLE_SCALE.get().asFloat(), EnumBaseGUISetting.TEXT_ENABLED_COLOR.get().asInt()));

        this.addElement(this.playersOnlineTextLabel = new OxygenTextLabel(0, 23, "", EnumBaseGUISetting.TEXT_SUB_SCALE.get().asFloat() - 0.05F, EnumBaseGUISetting.TEXT_ENABLED_COLOR.get().asInt()));

        this.addElement(this.statusSorter = new OxygenSorter(13, 27, EnumSorting.DOWN, ClientReference.localize("oxygen_core.gui.status")));   

        this.statusSorter.setClickListener((sorting)->{
            this.usernameSorter.reset();
            if (sorting == EnumSorting.DOWN)
                this.sortPlayers(0);
            else
                this.sortPlayers(1);
        });

        this.addElement(this.usernameSorter = new OxygenSorter(19, 27, EnumSorting.INACTIVE, ClientReference.localize("oxygen_core.gui.username")));  

        this.usernameSorter.setClickListener((sorting)->{
            this.statusSorter.reset();
            if (sorting == EnumSorting.DOWN)
                this.sortPlayers(2);
            else
                this.sortPlayers(3);
        });

        this.addElement(this.playersPanel = new OxygenScrollablePanel(this.screen, 6, 32, this.getWidth() - 15, 10, 1, 100, 15, EnumBaseGUISetting.TEXT_PANEL_SCALE.get().asFloat(), true));
        this.addElement(this.searchField = new OxygenTextField(90, 16, 60, 24, ""));
        this.playersPanel.initSearchField(this.searchField);

        List<OxygenContextMenuAction> actions = OxygenManagerClient.instance().getGUIManager().getContextActions(PlayersListMain.PLAYER_LIST_MENU_SCREEN_ID);
        OxygenContextMenuAction[] array = new OxygenContextMenuAction[actions.size()];
        actions.toArray(array);
        this.playersPanel.initContextMenu(new OxygenContextMenu(array));

        this.addElement(this.activityStatusDDList = new OxygenActivityStatusSwitcher(7, 16));

        this.activityStatusDDList.setActivityStatusChangeListener((status)->{
            this.statusSorter.setSorting(EnumSorting.DOWN);
            this.usernameSorter.reset();
            this.sortPlayers(0);
        });
    }

    private void sortPlayers(int mode) {
        List<PlayerSharedData> players = OxygenHelperClient.getPlayersSharedData()
                .stream()
                .filter(s->isPlayerAvailable(s))
                .collect(Collectors.toList());

        if (mode == 0)
            Collections.sort(players, (s1, s2)->OxygenHelperClient.getPlayerActivityStatus(s1).ordinal() - OxygenHelperClient.getPlayerActivityStatus(s2).ordinal());
        else if (mode == 1)
            Collections.sort(players, (s1, s2)->OxygenHelperClient.getPlayerActivityStatus(s2).ordinal() - OxygenHelperClient.getPlayerActivityStatus(s1).ordinal());
        else if (mode == 2)
            Collections.sort(players, (s1, s2)->s1.getUsername().compareTo(s2.getUsername()));
        else if (mode == 3)
            Collections.sort(players, (s1, s2)->s2.getUsername().compareTo(s1.getUsername()));

        this.playersPanel.reset();
        for (PlayerSharedData sharedData : players)
            this.playersPanel.addEntry(new PlayersListPanelEntry(sharedData));

        this.searchField.reset();

        this.playersOnlineTextLabel.setDisplayText(String.valueOf(players.size()) + "/" + String.valueOf(MathUtils.greaterOfTwo(players.size(), OxygenHelperClient.getMaxPlayers())));
        this.playersOnlineTextLabel.setX(this.getWidth() - 6 - this.textWidth(this.playersOnlineTextLabel.getDisplayText(), this.playersOnlineTextLabel.getTextScale()));

        int maxRows = MathUtils.clamp(players.size(), 15, MathUtils.greaterOfTwo(players.size(), OxygenHelperClient.getMaxPlayers()));
        this.playersPanel.getScroller().reset();
        this.playersPanel.getScroller().updateRowsAmount(maxRows);
    }

    public static boolean isPlayerAvailable(PlayerSharedData sharedData) {
        if (sharedData != null
                && OxygenHelperClient.isPlayerOnline(sharedData.getIndex())
                && PrivilegesProviderClient.getAsBoolean(EnumOxygenPrivilege.EXPOSE_OFFLINE_PLAYERS.id(), OxygenHelperClient.getPlayerActivityStatus(sharedData) != EnumActivityStatus.OFFLINE))
            return true;
        return false;
    }

    @Override
    public void handleElementClick(AbstractGUISection section, GUIBaseElement element, int mouseButton) {}

    @Override
    public boolean keyTyped(char typedChar, int keyCode) {
        if (!this.searchField.isDragged() && !this.hasCurrentCallback())
            if (OxygenGUIHelper.isOxygenMenuEnabled()) {
                if (keyCode == PlayersListScreen.PLAYERS_LIST_MENU_ENTRY.getKeyCode())
                    this.screen.close();
            } else if (PlayersListConfig.ENABLE_PLAYERSLIST_KEY.asBoolean() 
                    && keyCode == PlayersListManagerClient.instance().getKeyHandler().getPlayersListKeybinding().getKeyCode())
                this.screen.close();
        return super.keyTyped(typedChar, keyCode); 
    }

    public void sharedDataSynchronized() {
        this.activityStatusDDList.updateActivityStatus();
        this.sortPlayers(0);
    }
}
