package austeretony.oxygen_playerslist.client.gui.playerslist;

import austeretony.alternateui.screen.core.AbstractGUIScreen;
import austeretony.alternateui.screen.core.AbstractGUISection;
import austeretony.alternateui.screen.core.GUIBaseElement;
import austeretony.alternateui.screen.core.GUIWorkspace;
import austeretony.oxygen.common.api.OxygenGUIHelper;
import austeretony.oxygen_playerslist.common.main.PlayersListMain;
import net.minecraft.util.ResourceLocation;

public class PlayersListGUIScreen extends AbstractGUIScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(PlayersListMain.MODID, "textures/gui/playerslist/background.png");

    protected PlayerListGUISection mainSection;

    private boolean initialized;

    @Override
    protected GUIWorkspace initWorkspace() {
        return new GUIWorkspace(this, 200, 199);
    }

    @Override
    protected void initSections() {
        this.mainSection = new PlayerListGUISection(this);
        this.getWorkspace().initSection(this.mainSection);        
    }

    @Override
    protected AbstractGUISection getDefaultSection() {
        return this.mainSection;
    }

    @Override
    public void handleElementClick(AbstractGUISection section, GUIBaseElement element) {}

    @Override
    protected boolean doesGUIPauseGame() {
        return false;
    }

    @Override
    public void updateScreen() {    
        super.updateScreen();
        if (!this.initialized//reduce map calls
                && OxygenGUIHelper.isNeedSync(PlayersListMain.PLAYER_LIST_SCREEN_ID)
                && OxygenGUIHelper.isScreenInitialized(PlayersListMain.PLAYER_LIST_SCREEN_ID)
                && OxygenGUIHelper.isDataRecieved(PlayersListMain.PLAYER_LIST_SCREEN_ID)) {
            this.initialized = true;
            OxygenGUIHelper.resetNeedSync(PlayersListMain.PLAYER_LIST_SCREEN_ID);
            this.mainSection.sortPlayers(0);
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        OxygenGUIHelper.resetNeedSync(PlayersListMain.PLAYER_LIST_SCREEN_ID);
        OxygenGUIHelper.resetScreenInitialized(PlayersListMain.PLAYER_LIST_SCREEN_ID);
        OxygenGUIHelper.resetDataRecieved(PlayersListMain.PLAYER_LIST_SCREEN_ID);
    }
}
