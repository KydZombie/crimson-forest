package io.github.kydzombie.crimsonforest.client;

import io.github.kydzombie.crimsonforest.block.entity.MortarAndPestleBlockEntity;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import org.lwjgl.opengl.GL11;

public class MortarAndPestleBlockEntityRenderer extends BlockEntityRenderer {
    static final double MIN_HORIZONTAL = .375;
    static final double MAX_HORIZONTAL = .625;
    static final double MIN_VERTICAL = .0625;
    static final double MAX_VERTICAL = .1875;

    @Override
    public void render(BlockEntity blockEntity, double x, double y, double z, float tickDelta) {
        int essence = ((MortarAndPestleBlockEntity) blockEntity).getEssence();
        if (essence <= 0) return;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        double draw_y = y + MIN_VERTICAL + ((MAX_VERTICAL - MIN_VERTICAL) * (essence / (float) MortarAndPestleBlockEntity.MAX_ESSENCE));

        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.startQuads();
        tessellator.color(EssenceType.NATURE.color, 127);
        tessellator.vertex(x + MAX_HORIZONTAL, draw_y, z + MAX_HORIZONTAL);
        tessellator.vertex(x + MAX_HORIZONTAL, draw_y, z + MIN_HORIZONTAL);
        tessellator.vertex(x + MIN_HORIZONTAL, draw_y, z + MIN_HORIZONTAL);
        tessellator.vertex(x + MIN_HORIZONTAL, draw_y, z + MAX_HORIZONTAL);
        tessellator.draw();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();
    }
}
