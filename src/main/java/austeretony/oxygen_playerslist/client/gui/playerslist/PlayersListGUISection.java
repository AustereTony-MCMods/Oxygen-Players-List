package austeretony.oxygen_playerslist.client.gui.playerslist;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import austeretony.alternateui.screen.core.AbstractGUISection;
import austeretony.alternateui.screen.core.GUIBaseElement;
import austeretony.oxygen_core.client.OxygenManagerClient;
import austeretony.oxygen_core.client.api.ClientReference;
import austeretony.oxygen_core.client.api.OxygenGUIHelper;
import austeretony.oxygen_core.client.api.OxygenHelperClient;
import austeretony.oxygen_core.client.api.PrivilegeProviderClient;
import austeretony.oxygen_core.client.gui.elements.ActivityStatusGUIDDList;
import austeretony.oxygen_core.client.gui.elements.OxygenGUIButtonPanel;
import austeretony.oxygen_core.client.gui.elements.OxygenGUIContextMenu;
import austeretony.oxygen_core.client.gui.elements.OxygenGUIContextMenuElement.ContextMenuAction;
import austeretony.oxygen_core.client.gui.elements.OxygenGUIText;
import austeretony.oxygen_core.client.gui.elements.OxygenGUITextField;
import austeretony.oxygen_core.client.gui.elements.OxygenSorterGUIElement;
import austeretony.oxygen_core.client.gui.elements.OxygenSorterGUIElement.EnumSorting;
import austeretony.oxygen_core.client.gui.settings.GUISettings;
import austeretony.oxygen_core.common.PlayerSharedData;
import austeretony.oxygen_core.common.main.EnumOxygenPrivilege;
import austeretony.oxygen_core.common.util.MathUtils;
import austeretony.oxygen_core.server.OxygenPlayerData.EnumActivityStatus;
import austeretony.oxygen_playerslist.client.input.PlayersListKeyHandler;
import austeretony.oxygen_playerslist.common.main.PlayersListMain;

public class PlayersListGUISection extends AbstractGUISection {

    private final PlayersListGUIScreen screen;

    private OxygenGUIText playersOnlineTextLabel;

    private ActivityStatusGUIDDList activityStatusDDList;

    private OxygenSorterGUIElement statusSorter, usernameSorter;

    private OxygenGUITextField searchField;

    private OxygenGUIButtonPanel playersPanel;

    public PlayersListGUISection(PlayersListGUIScreen screen) {
        super(screen);
        this.screen = screen;
    }

    @Override
    public void init() {
        this.addElement(new PlayersListGUIFiller(0, 0, this.getWidth(), this.getHeight()));
        this.addElement(new OxygenGUIText(4, 5, ClientReference.localize("oxygen_playerslist.gui.playerslist.title"), GUISettings.get().getTitleScale(), GUISettings.get().getEnabledTextColor()));

        this.addElement(this.playersOnlineTextLabel = new OxygenGUIText(0, 18, "", GUISettings.get().getSubTextScale() - 0.05F, GUISettings.get().getEnabledTextColor())); 

        this.addElement(this.statusSorter = new OxygenSorterGUIElement(13, 27, EnumSorting.DOWN, ClientReference.localize("oxygen.sorting.status")));   

        this.statusSorter.setClickListener((sorting)->{
            this.usernameSorter.reset();
            if (sorting == EnumSorting.DOWN)
                this.sortPlayers(0);
            else
                this.sortPlayers(1);
        });

        this.addElement(this.usernameSorter = new OxygenSorterGUIElement(19, 27, EnumSorting.INACTIVE, ClientReference.localize("oxygen.sorting.username")));  

        this.usernameSorter.setClickListener((sorting)->{
            this.statusSorter.reset();
            if (sorting == EnumSorting.DOWN)
                this.sortPlayers(2);
            else
                this.sortPlayers(3);
        });

        this.addElement(this.playersPanel = new OxygenGUIButtonPanel(this.screen, 6, 32, this.getWidth() - 15, 10, 1, MathUtils.clamp(OxygenHelperClient.getMaxPlayers(), 15, 1000), 15, GUISettings.get().getPanelTextScale(), true));
        this.addElement(this.searchField = new OxygenGUITextField(90, 16, 60, 8, 24, "...", 3, false, - 1L));
        this.playersPanel.initSearchField(this.searchField);

        List<ContextMenuAction> actions = OxygenManagerClient.instance().getGUIManager().getContextActions(PlayersListMain.PLAYER_LIST_MENU_SCREEN_ID);
        ContextMenuAction[] array = new ContextMenuAction[actions.size()];
        actions.toArray(array);
        this.playersPanel.initContextMenu(new OxygenGUIContextMenu(GUISettings.get().getContextMenuWidth(), 9, array));

        this.addElement(this.activityStatusDDList = new ActivityStatusGUIDDList(7, 16));

        this.activityStatusDDList.setActivityStatusChangeListener((status)->{
            this.statusSorter.setSorting(EnumSorting.DOWN);
            this.usernameSorter.reset();
            this.sortPlayers(0);
        });
    }

    private void sortPlayers(int mode) {
        List<PlayerSharedData> players = OxygenHelperClient.getPlayersSharedData()
                .stream()
                .filter(s->OxygenHelperClient.isPlayerOnline(s.getPlayerUUID()) 
                        && (isClientPlayer(s) || OxygenHelperClient.getPlayerActivityStatus(s) != EnumActivityStatus.OFFLINE || PrivilegeProviderClient.getValue(EnumOxygenPrivilege.EXPOSE_PLAYERS_OFFLINE.toString(), false)))
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
            this.playersPanel.addButton(new PlayersListEntryGUIButton(sharedData));

        this.playersPanel.getScroller().resetPosition();
        this.playersPanel.getScroller().getSlider().reset();
        
        this.playersPanel.getScroller().updateRowsAmount(MathUtils.clamp(players.size(), 15, OxygenHelperClient.getMaxPlayers()));

        this.searchField.reset();

        this.playersOnlineTextLabel.setDisplayText(String.valueOf(players.size()) + "/" + String.valueOf(OxygenHelperClient.getMaxPlayers()));
        this.playersOnlineTextLabel.setX(this.getWidth() - 6 - this.textWidth(this.playersOnlineTextLabel.getDisplayText(), GUISettings.get().getSubTextScale() - 0.05F));
    }

    private static boolean isClientPlayer(PlayerSharedData sharedData) {
        return sharedData.getPlayerUUID().equals(OxygenHelperClient.getPlayerUUID());
    }

    @Override
    public void handleElementClick(AbstractGUISection section, GUIBaseElement element, int mouseButton) {}

    @Override
    public boolean keyTyped(char typedChar, int keyCode) {
        if (!this.searchField.isDragged() && !this.hasCurrentCallback())
            if (OxygenGUIHelper.isOxygenMenuEnabled()) {
                if (keyCode == PlayersListGUIScreen.PLAYERS_LIST_MENU_ENTRY.getIndex() + 2)
                    this.screen.close();
            } else if (keyCode == PlayersListKeyHandler.PLAYERS_LIST.getKeyCode())
                this.screen.close();
        return super.keyTyped(typedChar, keyCode); 
    }

    public void sharedDataSynchronized() {
        this.activityStatusDDList.updateActivityStatus();
        this.sortPlayers(0);
    }
}
