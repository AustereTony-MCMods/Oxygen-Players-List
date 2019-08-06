package austeretony.oxygen_playerslist.client.gui.playerslist;

import austeretony.alternateui.screen.core.AbstractGUISection;
import austeretony.alternateui.screen.core.GUIBaseElement;
import austeretony.alternateui.screen.core.GUIWorkspace;
import austeretony.oxygen.client.gui.SynchronizedGUIScreen;
import austeretony.oxygen_playerslist.common.main.PlayersListMain;
import net.minecraft.util.ResourceLocation;

public class PlayersListGUIScreen extends SynchronizedGUIScreen {

    public static final ResourceLocation PLAYERSLIST_MENU_BACKGROUND = new ResourceLocation(PlayersListMain.MODID, "textures/gui/playerslist/playerslist_menu.png");

    protected PlayersListGUISection playerslistSection;

    public PlayersListGUIScreen() {
        super(PlayersListMain.PLAYER_LIST_MENU_SCREEN_ID);
    }

    @Override
    protected GUIWorkspace initWorkspace() {
        return new GUIWorkspace(this, 190, 199);
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

    @Override
    public void loadData() {
        this.playerslistSection.sortPlayers(0);
    }
}
