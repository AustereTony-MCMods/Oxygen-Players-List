package austeretony.oxygen_playerslist.client.gui.playerslist;

import austeretony.alternateui.screen.core.AbstractGUIScreen;
import austeretony.alternateui.screen.core.AbstractGUISection;
import austeretony.alternateui.screen.core.GUIBaseElement;
import austeretony.alternateui.screen.core.GUIWorkspace;
import austeretony.alternateui.util.EnumGUIAlignment;
import austeretony.oxygen_core.client.api.OxygenHelperClient;
import austeretony.oxygen_core.client.gui.menu.OxygenMenuEntry;
import austeretony.oxygen_playerslist.client.gui.menu.PlayersListMenuEntry;
import austeretony.oxygen_playerslist.client.settings.gui.EnumPlayersListGUISetting;
import austeretony.oxygen_playerslist.common.main.PlayersListMain;

public class PlayersListScreen extends AbstractGUIScreen {

    public static final OxygenMenuEntry PLAYERS_LIST_MENU_ENTRY = new PlayersListMenuEntry();

    protected PlayersListSection playersListSection;

    public PlayersListScreen() {
        OxygenHelperClient.syncSharedData(PlayersListMain.PLAYER_LIST_MENU_SCREEN_ID);
    }

    @Override
    protected GUIWorkspace initWorkspace() {
        EnumGUIAlignment alignment = EnumGUIAlignment.CENTER;
        switch (EnumPlayersListGUISetting.PLAYERS_LIST_ALIGNMENT.get().asInt()) {
        case - 1: 
            alignment = EnumGUIAlignment.LEFT;
            break;
        case 0:
            alignment = EnumGUIAlignment.CENTER;
            break;
        case 1:
            alignment = EnumGUIAlignment.RIGHT;
            break;    
        default:
            alignment = EnumGUIAlignment.CENTER;
            break;
        }
        return new GUIWorkspace(this, 190, 198).setAlignment(alignment, 0, 0);
    }

    @Override
    protected void initSections() {
        this.getWorkspace().initSection(this.playersListSection = new PlayersListSection(this));        
    }

    @Override
    protected AbstractGUISection getDefaultSection() {
        return this.playersListSection;
    }

    @Override
    public void handleElementClick(AbstractGUISection section, GUIBaseElement element) {}

    @Override
    protected boolean doesGUIPauseGame() {
        return false;
    }

    public void sharedDataSynchronized() {
        this.playersListSection.sharedDataSynchronized();
    }
}
