package austeretony.oxygen_playerslist.client.gui.playerslist;

import austeretony.alternateui.screen.core.GUIAdvancedElement;
import austeretony.oxygen.client.core.api.ClientReference;
import austeretony.oxygen.client.gui.BackgroundGUIFiller;
import austeretony.oxygen.client.gui.settings.GUISettings;
import net.minecraft.client.renderer.GlStateManager;

public class PlayerListBackgroundGUIFiller extends BackgroundGUIFiller {

    private final boolean textureExist;

    public PlayerListBackgroundGUIFiller(int xPosition, int yPosition, int width, int height) {             
        super(xPosition, yPosition, width, height);
        this.textureExist = ClientReference.isTextureExist(PlayersListGUIScreen.BACKGROUND_TEXTURE);
    }

    @Override
    public void draw(int mouseX, int mouseY) {  
        GlStateManager.pushMatrix();            
        GlStateManager.translate(this.getX(), this.getY(), 0.0F);            
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);                      
        if (this.textureExist) {  
            GlStateManager.enableBlend();    
            this.mc.getTextureManager().bindTexture(PlayersListGUIScreen.BACKGROUND_TEXTURE);                         
            GUIAdvancedElement.drawCustomSizedTexturedRect( - GUISettings.instance().getTextureOffsetX(), - GUISettings.instance().getTextureOffsetY(), 
                    0, 0, this.getTextureWidth(), this.getTextureHeight(), this.getTextureWidth(), this.getTextureHeight());             
            GlStateManager.disableBlend();   
        } else {
            drawRect(- 1, - 1, this.getWidth() + 1, this.getHeight() + 1, GUISettings.instance().getBaseGUIBackgroundColor());//main background
            drawRect(0, 0, this.getWidth(), 13, GUISettings.instance().getAdditionalGUIBackgroundColor());//title background
            drawRect(0, 14, 76, 23, GUISettings.instance().getAdditionalGUIBackgroundColor());//search panel background
            drawRect(77, 14, this.getWidth(), 23, GUISettings.instance().getAdditionalGUIBackgroundColor());//status background
            drawRect(0, 24, this.getWidth(), 34, GUISettings.instance().getAdditionalGUIBackgroundColor());//sorters background
            drawRect(0, 35, this.getWidth() - 3, this.getHeight(), GUISettings.instance().getPanelGUIBackgroundColor());//panel background
            drawRect(this.getWidth() - 2, 35, this.getWidth(), this.getHeight(), GUISettings.instance().getAdditionalGUIBackgroundColor());//slider background
        }
        GlStateManager.popMatrix();            
    }
}
