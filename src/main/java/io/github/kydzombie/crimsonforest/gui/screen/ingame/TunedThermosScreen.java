package io.github.kydzombie.crimsonforest.gui.screen.ingame;

import io.github.kydzombie.crimsonforest.gui.screen.TunedThermosScreenHandler;
import io.github.kydzombie.crimsonforest.item.thermos.TunedThermosInventory;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class TunedThermosScreen extends HandledScreen {
    private static final int BAR_X = 6;
    private static final int BAR_Y = 5;
    private static final int BAR_WIDTH = 28;
    private static final int FULL_BAR_HEIGHT = 76;
    private static final int LIFE_U = 0;
    private static final int LIFE_V = 166;
    private static final int NATURE_U = 28;
    private static final int NATURE_V = 166;
    private final TunedThermosInventory inventory;

    public TunedThermosScreen(PlayerInventory playerInventory, TunedThermosInventory inventory) {
        super(new TunedThermosScreenHandler(playerInventory, inventory));
        this.inventory = inventory;
    }

    @Override
    protected void drawForeground() {
        this.textRenderer.draw(inventory.getName(), 60, 6, 4210752);
        this.textRenderer.draw("Inventory", 8, this.backgroundHeight - 96 + 2, 4210752);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int var2 = minecraft.textureManager.getTextureId("/assets/crimsonforest/gui/tuned_thermos.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(var2);
        int var3 = (width - backgroundWidth) / 2;
        int var4 = (height - backgroundHeight) / 2;
        drawTexture(var3, var4, 0, 0, backgroundWidth, backgroundHeight);

        int essence = inventory.thermosItem.getEssence(inventory.thermosStack, inventory.essenceType);
        int maxMillibuckets = inventory.thermosItem.maxEssence;
        int bar_height = (int) ((essence / (float) maxMillibuckets) * FULL_BAR_HEIGHT);
        int bar_offset = (FULL_BAR_HEIGHT - bar_height);

        if (inventory.essenceType == EssenceType.LIFE) {
            drawTexture(var3 + BAR_X, var4 + BAR_Y + bar_offset, LIFE_U, LIFE_V + bar_offset, BAR_WIDTH, bar_height);
        } else {
            drawTexture(var3 + BAR_X, var4 + BAR_Y + bar_offset, NATURE_U, NATURE_V + bar_offset, BAR_WIDTH, bar_height);
        }
    }

    @Override
    public void removed() {
        super.removed();
        if (this.minecraft.player != null) {
            container.onClosed(minecraft.player);
        }
    }
}
