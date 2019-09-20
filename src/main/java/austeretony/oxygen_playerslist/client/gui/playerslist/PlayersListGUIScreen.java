package austeretony.oxygen_playerslist.client.gui.playerslist;

import austeretony.alternateui.screen.core.AbstractGUIScreen;
import austeretony.alternateui.screen.core.AbstractGUISection;
import austeretony.alternateui.screen.core.GUIBaseElement;
import austeretony.alternateui.screen.core.GUIWorkspace;
import austeretony.oxygen_core.client.api.OxygenHelperClient;
import austeretony.oxygen_core.client.gui.menu.OxygenMenuEntry;
import austeretony.oxygen_playerslist.common.main.PlayersListMain;

public class PlayersListGUIScreen extends AbstractGUIScreen {

    public static final OxygenMenuEntry PLAYERS_LIST_MENU_ENTRY = new PlayersListMenuEntry();

    protected PlayersListGUISection playerslistSection;

    public PlayersListGUIScreen() {
        OxygenHelperClient.syncSharedData(PlayersListMain.PLAYER_LIST_MENU_SCREEN_ID);
    }

    @Override
    protected GUIWorkspace initWorkspace() {
        return new GUIWorkspace(this, 190, 196);
    }

    @Override
    protected void initSections() {
        this.getWorkspace().initSection(this.playerslistSection = new PlayersListGUISection(this));        
    }

    @Override
    protected AbstractGUISection getDefaultSection() {
        return this.playerslistSection;
    }

    @Override
    public void handleElementClick(AbstractGUISection section, GUIBaseElement element) {}

    @Override
    protected boolean doesGUIPauseGame() {
        return false;
    }

    public void sharedDataSynchronized() {
        this.playerslistSection.sharedDataSynchronized();
    }
}
