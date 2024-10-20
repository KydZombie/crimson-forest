package io.github.kydzombie.crimsonforest.item;

import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.item.thermos.LifeTunedThermosItem;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

public class LifeEssenceCollectorItem extends TemplateItem implements EssenceContainer, CustomTooltipProvider {
    private final int damage;
    private final int maxEssence;
    private final int millibucketsPerKill;
    private static final String ESSENCE_NBT = "crimsonforest:essence";

    public LifeEssenceCollectorItem(Identifier identifier, int damage, int millibucketsPerKill, int maxEssence, int maxDamage) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(1);
        setMaxDamage(maxDamage);
        setHandheld();
        this.damage = damage;
        this.millibucketsPerKill = millibucketsPerKill;
        this.maxEssence = maxEssence;
    }

    @Override
    public List<EssenceType> getEssenceTypes(ItemStack stack) {
        return List.of(EssenceType.LIFE);
    }

    @Override
    public int getMaxEssence(ItemStack stack, EssenceType type) {
        if (type != EssenceType.LIFE) return 0;
        return maxEssence;
    }

    @Override
    public int getEssence(ItemStack stack, EssenceType type) {
        if (type != EssenceType.LIFE) return 0;
        return stack.getStationNbt().getInt(ESSENCE_NBT);
    }

    @Override
    public void setEssence(ItemStack stack, EssenceType type, int value) {
        if (type != EssenceType.LIFE) return;
        stack.getStationNbt().putInt(ESSENCE_NBT, value);
    }

    @Override
    public boolean canGiveEssence(ItemStack stack, EssenceType type) {
        return false;
    }

    protected void onKill(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        giveEssence(stack, EssenceType.LIFE, millibucketsPerKill);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.deathTime == 0 && target.lastHealth > 0 && target.health <= 0) {
            onKill(stack, target, attacker);
        }

        stack.damage(1, attacker);
        return true;
    }

    @Override
    public int getAttackDamage(Entity attackedEntity) {
        return damage;
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        return new String[] {
                originalTooltip,
                I18n.getTranslation("essence_collector.crimsonforest.essence_text", + getEssence(stack, EssenceType.LIFE))
        };
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        int currentEssence = getEssence(stack, EssenceType.LIFE);
        if (currentEssence <= 0) return stack;
        int spentEssence = 0;
        if (AccessoryAccess.hasAnyAccessoriesOfType(user, "thermos")) {
            ItemStack[] thermoses = AccessoryAccess.getAccessories(user, "thermos");
            for (ItemStack thermosStack : thermoses) {
                if (currentEssence - spentEssence <= 0) break;
                if (thermosStack.getItem() instanceof LifeTunedThermosItem thermos) {
                    int millibuckets = thermos.getEssence(thermosStack, EssenceType.LIFE);
                    int spentMillibuckets = Math.min(currentEssence - spentEssence, thermos.maxEssence - millibuckets);
                    if (spentMillibuckets > 0) {
                        thermos.setEssence(thermosStack, EssenceType.LIFE, millibuckets + spentMillibuckets);
                        spentEssence += spentMillibuckets;
                    }
                }
            }
        }

        for (int i = 0; i < user.inventory.size(); i++) {
            if (currentEssence - spentEssence <= VialItem.ESSENCE_PER_VIAL) break;
            ItemStack vialStack = user.inventory.getStack(i);
            if (vialStack != null && vialStack.getItem() == TheCrimsonForest.emptyVialItem) {
                user.inventory.addStack(new ItemStack(TheCrimsonForest.lifeVialItem));
                vialStack.count--;
                if (vialStack.count <= 0) {
                    user.inventory.setStack(i, null);
                }
                spentEssence += VialItem.ESSENCE_PER_VIAL;
                break;
            }
        }

        if (spentEssence != 0) {
            setEssence(stack, EssenceType.LIFE, currentEssence - spentEssence);
            user.swingHand();
            return stack;
        }
        return super.use(stack, world, user);
    }
}
