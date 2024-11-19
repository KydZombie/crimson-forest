package io.github.kydzombie.crimsonforest.client;

import io.github.kydzombie.crimsonforest.block.entity.BasinBlockEntity;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class BasinBlockEntityRenderer extends BlockEntityRenderer {
    static final double MIN_HORIZONTAL = .125;
    static final double MAX_HORIZONTAL = .875;
    static final double MIN_VERTICAL = .75;
    static final double MAX_VERTICAL = .875;

    @SuppressWarnings("deprecation")
    private static final Minecraft minecraft = (Minecraft) FabricLoader.getInstance().getGameInstance();

    @Override
    public void render(BlockEntity blockEntity, double x, double y, double z, float tickDelta) {
        BasinBlockEntity basin = ((BasinBlockEntity) blockEntity);
        EssenceType essenceType = basin.getEssenceType();
        long essence = basin.getEssence();

        GL11.glPushMatrix();

        ItemStack stack = basin.getStack(0);

        if (stack != null) {
            if (basin.renderedItem == null) {
                PlayerEntity player = minecraft.player;
                basin.renderedItem = new ItemEntity(player.world, basin.x, basin.y + 1, basin.z, stack);
            }

            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.0F, (float) z + 0.5F);
            EntityRenderDispatcher.INSTANCE.render(basin.renderedItem, 0.0, 0.0, 0.0, 0.0F, 0.0F);
            GL11.glTranslatef(-((float) x + 0.5F), -((float) y + 1.0F), -((float) z + 0.5F));
        }

        if (essenceType != null && essence > 0) {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            double percent = (essence / (float) BasinBlockEntity.MAX_ESSENCE);
            if (essence == BasinBlockEntity.MAX_ESSENCE / 2) percent += 0.01f;

            double draw_y = y + MIN_VERTICAL + ((MAX_VERTICAL - MIN_VERTICAL) * percent);

            Tessellator tessellator = Tessellator.INSTANCE;
            tessellator.startQuads();
            tessellator.color(essenceType.color, 127);
            tessellator.vertex(x + MAX_HORIZONTAL, draw_y, z + MAX_HORIZONTAL);
            tessellator.vertex(x + MAX_HORIZONTAL, draw_y, z + MIN_HORIZONTAL);
            tessellator.vertex(x + MIN_HORIZONTAL, draw_y, z + MIN_HORIZONTAL);
            tessellator.vertex(x + MIN_HORIZONTAL, draw_y, z + MAX_HORIZONTAL);
            tessellator.draw();

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        GL11.glPopMatrix();
    }
}
