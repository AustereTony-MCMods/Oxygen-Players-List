package austeretony.oxygen_playerslist.client.gui.playerslist;

import austeretony.oxygen.client.gui.BackgroundGUIFiller;
import austeretony.oxygen.client.gui.settings.GUISettings;

public class PlayersListGUIFiller extends BackgroundGUIFiller {

    public PlayersListGUIFiller(int xPosition, int yPosition, int width, int height) {             
        super(xPosition, yPosition, width, height, PlayersListGUIScreen.PLAYERSLIST_MENU_BACKGROUND);
    }

    @Override
    public void drawDefaultBackground() {
        drawRect(- 1, - 1, this.getWidth() + 1, this.getHeight() + 1, GUISettings.instance().getBaseGUIBackgroundColor());//main background
        drawRect(0, 0, this.getWidth(), 13, GUISettings.instance().getAdditionalGUIBackgroundColor());//title background
        drawRect(0, 14, 76, 23, GUISettings.instance().getAdditionalGUIBackgroundColor());//search panel background
        drawRect(77, 14, this.getWidth(), 23, GUISettings.instance().getAdditionalGUIBackgroundColor());//status background
        drawRect(0, 24, this.getWidth(), 34, GUISettings.instance().getAdditionalGUIBackgroundColor());//sorters background
        drawRect(0, 35, this.getWidth() - 3, this.getHeight(), GUISettings.instance().getPanelGUIBackgroundColor());//panel background
        drawRect(this.getWidth() - 2, 35, this.getWidth(), this.getHeight(), GUISettings.instance().getAdditionalGUIBackgroundColor());//slider background
    }
}
