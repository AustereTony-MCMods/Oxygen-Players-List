package austeretony.oxygen_playerslist.client.gui.playerslist;

import java.util.UUID;

import austeretony.oxygen.client.api.OxygenHelperClient;
import austeretony.oxygen.client.core.api.ClientReference;
import austeretony.oxygen.client.gui.IndexedGUIButton;
import austeretony.oxygen.client.gui.OxygenGUITextures;
import austeretony.oxygen.common.api.EnumDimension;
import austeretony.oxygen.common.main.SharedPlayerData;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;

public class PlayersListEntryGUIButton extends IndexedGUIButton<UUID> {

    private String dimension;

    private int statusIconU, pingIconV;

    private boolean initialized;

    public PlayersListEntryGUIButton(SharedPlayerData sharedData) {
        super(sharedData.getPlayerUUID());
        this.dimension = EnumDimension.getLocalizedNameFromId(OxygenHelperClient.getPlayerDimension(sharedData));
        this.setDisplayText(sharedData.getUsername());//need for search mechanic
        this.statusIconU = OxygenHelperClient.getPlayerStatus(sharedData).ordinal() * 3;
        NetworkPlayerInfo info = ClientReference.getPlayerInfo(sharedData.getPlayerUUID());
        int responseTime = 1000;
        if (info != null)//mc may sync data faster than oxygen, so NetworkPlayerInfo may be null if player left the game
            responseTime = info.getResponseTime();
        if (responseTime < 0)
            this.pingIconV = 5;
        else if (responseTime < 150)
            this.pingIconV = 0;
        else if (responseTime < 300)
            this.pingIconV = 1;
        else if (responseTime < 600)
            this.pingIconV = 2;
        else if (responseTime < 1000)
            this.pingIconV = 3;
        else
            this.pingIconV = 4;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (this.isVisible()) {  
            GlStateManager.pushMatrix();           
            GlStateManager.translate(this.getX(), this.getY(), 0.0F);
            int color, textColor, textY;                      
            if (!this.isEnabled()) {                 
                color = this.getDisabledBackgroundColor();
                textColor = this.getDisabledTextColor();           
            } else if (this.isHovered() || this.isToggled()) {                 
                color = this.getHoveredBackgroundColor();
                textColor = this.getHoveredTextColor();
            } else {                   
                color = this.getEnabledBackgroundColor(); 
                textColor = this.getEnabledTextColor();      
            }
            drawRect(0, 0, this.getWidth(), this.getHeight(), color);
            textY = (this.getHeight() - this.textHeight(this.getTextScale())) / 2 + 1;
            GlStateManager.pushMatrix();           
            GlStateManager.translate(24.0F, textY, 0.0F); 
            GlStateManager.scale(this.getTextScale(), this.getTextScale(), 0.0F); 
            this.mc.fontRenderer.drawString(this.getDisplayText(), 0, 0, textColor, this.isTextShadowEnabled());
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();    
            GlStateManager.translate(100.0F, textY, 0.0F); 
            GlStateManager.scale(this.getTextScale(), this.getTextScale(), 0.0F); 
            this.mc.fontRenderer.drawString(this.dimension, 0, 0, textColor, this.isTextShadowEnabled());
            GlStateManager.popMatrix();
            this.mc.getTextureManager().bindTexture(OxygenGUITextures.STATUS_ICONS); 
            drawCustomSizedTexturedRect(7, 3, this.statusIconU, 0, 3, 3, 12, 3);   
            this.mc.getTextureManager().bindTexture(OxygenGUITextures.PING_ICONS); 
            drawCustomSizedTexturedRect(this.getWidth() - 20, 2, 0, this.pingIconV * 6, 10, 6, 10, 36);  
            GlStateManager.popMatrix();
        }
    }
}
