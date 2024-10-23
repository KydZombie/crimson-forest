package io.github.kydzombie.crimsonforest.client.entity;

import io.github.kydzombie.crimsonforest.entity.VinelashAttackEntity;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class VinelashAttackEntityRenderer extends EntityRenderer {
    static final double MIN_HORIZONTAL = -.5;
    static final double MAX_HORIZONTAL = .5;

    @Override
    public void render(Entity entity, double x, double y, double z, float yaw, float pitch) {
        VinelashAttackEntity attack = (VinelashAttackEntity) entity;

        Tessellator tessellator = Tessellator.INSTANCE;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glTranslated(x, y, z);
        GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
        tessellator.startQuads();
        tessellator.color(55, 148, 110, 127);
        tessellator.vertex(MAX_HORIZONTAL, 0, MAX_HORIZONTAL);
        tessellator.vertex(MAX_HORIZONTAL, 0, MIN_HORIZONTAL);
        tessellator.vertex(MIN_HORIZONTAL, 0, MIN_HORIZONTAL);
        tessellator.vertex(MIN_HORIZONTAL, 0, MAX_HORIZONTAL);
        tessellator.draw();
        GL11.glRotatef(-yaw, 0.0F, 1.0F, 0.0F);
        GL11.glTranslated(-x, -y, -z);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();
    }
}
