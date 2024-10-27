package io.github.kydzombie.crimsonforest.gui.screen.ingame;

import io.github.kydzombie.crimsonforest.block.entity.CrudeForgeBlockEntity;
import io.github.kydzombie.crimsonforest.block.entity.CrudeSoulInfuserBlockEntity;
import io.github.kydzombie.crimsonforest.gui.screen.CrudeForgeScreenHandler;
import io.github.kydzombie.crimsonforest.gui.screen.CrudeSoulInfuserScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class CrudeSoulInfuserScreen extends HandledScreen {
    CrudeSoulInfuserBlockEntity blockEntity;

    public CrudeSoulInfuserScreen(PlayerInventory playerInventory, CrudeSoulInfuserBlockEntity blockEntity) {
        super(new CrudeSoulInfuserScreenHandler(playerInventory, blockEntity));
        this.blockEntity = blockEntity;
    }

    @Override
    protected void drawForeground() {
        this.textRenderer.draw("Crude Forge", 60, 6, 4210752);
        this.textRenderer.draw("Inventory", 8, this.backgroundHeight - 96 + 2, 4210752);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int var2 = minecraft.textureManager.getTextureId("/assets/crimsonforest/gui/crude_forge.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(var2);
        int var3 = (width - backgroundWidth) / 2;
        int var4 = (height - backgroundHeight) / 2;
        drawTexture(var3, var4, 0, 0, backgroundWidth, backgroundHeight);
        if (blockEntity.isBurning()) {
            int fuelDelta = blockEntity.getFuelTimeDelta(12);
            if (fuelDelta > 0) {
                this.drawTexture(var3 + 37, var4 + 36 + 12 - fuelDelta, 176, 12 - fuelDelta, 14, fuelDelta + 2);
            }
        }

        int var6 = blockEntity.getCookTimeDelta(24);
        this.drawTexture(var3 + 79, var4 + 34, 176, 14, var6 + 1, 16);
    }
}
