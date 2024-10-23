package io.github.kydzombie.crimsonforest.entity;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

@HasTrackingParameters(trackingDistance = 50, sendVelocity = TriState.TRUE, updatePeriod = 1)
public class VinelashAttackEntity extends Entity implements EntitySpawnDataProvider {
    LivingEntity owner;
    int lifetime = 0;

    public VinelashAttackEntity(World world) {
        super(world);
        this.setBoundingBoxSpacing(.5F, .5F);
    }

    public VinelashAttackEntity(World world, LivingEntity owner) {
        super(world);
        this.setBoundingBoxSpacing(.5F, .5F);
        this.setPositionAndAnglesKeepPrevAngles(owner.x, owner.y + (double) owner.getEyeHeight(), owner.z, owner.yaw, owner.pitch);
        this.velocityX = -MathHelper.sin(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI);
        this.velocityZ = MathHelper.cos(this.yaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float) Math.PI);
        this.velocityY = -MathHelper.sin(this.pitch / 180.0F * (float) Math.PI);
        this.setVelocity(this.velocityX, this.velocityY, this.velocityZ, 0.75F, 1.0F);
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        float var9 = MathHelper.sqrt(x * x + y * y + z * z);
        x /= var9;
        y /= var9;
        z /= var9;
        x += this.random.nextGaussian() * 0.0075F * (double) divergence;
        y += this.random.nextGaussian() * 0.0075F * (double) divergence;
        z += this.random.nextGaussian() * 0.0075F * (double) divergence;
        x *= speed;
        y *= speed;
        z *= speed;
        this.velocityX = x;
        this.velocityY = y;
        this.velocityZ = z;
        float var10 = MathHelper.sqrt(x * x + z * z);
        this.prevYaw = this.yaw = (float) (Math.atan2(x, z) * 180.0 / (float) Math.PI);
        this.prevPitch = this.pitch = (float) (Math.atan2(y, var10) * 180.0 / (float) Math.PI);
        this.lifetime = 0;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return TheCrimsonForest.NAMESPACE.id("vinelash_attack");
    }

    @Override
    public void tick() {
        super.tick();

        lifetime++;
        if (lifetime >= 120) {
            markDead();
            System.out.println("DEAD");
            return;
        }

//        boolean entangledOne = false;
//        @SuppressWarnings("unchecked")
//        List<Entity> collidingEntities = this.world.getEntities(this, boundingBox);
//        for (Entity entity : collidingEntities) {
//            if (entity instanceof PlayerEntity) continue;
//            if (entity instanceof Entangleable entangleable) {
//                entangleable.setEntangledTime(80);
//                System.out.println("ENTANGLED!");
//                entangledOne = true;
//            }
//        }

//        if (entangledOne) {
//            System.out.println("ENTANGLED DEAD");
//            markDead();
//            return;
//        }

        this.x = this.x + this.velocityX;
        this.y = this.y + this.velocityY;
        this.z = this.z + this.velocityZ;
    }

    @Override
    public Box getCollisionAgainstShape(Entity other) {
        return other.boundingBox;
    }

    @Override
    public Box getBoundingBox() {
        return boundingBox;
    }

    @Override
    public void onCollision(Entity otherEntity) {
        System.out.println("COLLISION");
        super.onCollision(otherEntity);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {

    }

    @Environment(EnvType.CLIENT)
    @Override
    public float getShadowRadius() {
        return 0;
    }
}
