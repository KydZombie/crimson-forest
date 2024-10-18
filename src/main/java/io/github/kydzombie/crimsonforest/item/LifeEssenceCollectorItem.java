package io.github.kydzombie.crimsonforest.item;

import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.item.thermos.LifeTunedThermosItem;
import io.github.kydzombie.crimsonforest.item.thermos.ThermosItem;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class LifeEssenceCollectorItem extends TemplateItem implements CustomTooltipProvider {

    public static final int MAX_ESSENCE = 2000;
    public static final int MILLIBUCKETS_PER_KILL = 100;
    public static final String ESSENCE_NBT = "crimsonforest:essence";

    public LifeEssenceCollectorItem(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(1);
        setMaxDamage(32);
    }

    public int getEssence(ItemStack stack) {
        return stack.getStationNbt().getInt(ESSENCE_NBT);
    }

    public void setEssence(ItemStack stack, int essence) {
        stack.getStationNbt().putInt(ESSENCE_NBT, Math.min(MAX_ESSENCE, essence));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.deathTime == 0 && target.lastHealth > 0 && target.health <= 0) {
            System.out.println("Adding essence!");
            setEssence(stack, getEssence(stack) + MILLIBUCKETS_PER_KILL);
        }

        stack.damage(1, attacker);
        return true;
    }

    @Override
    public int getAttackDamage(Entity attackedEntity) {
        return 4;
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        return new String[] {
                originalTooltip,
                I18n.getTranslation(getTranslationKey() + ".essence_text", + getEssence(stack))
        };
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        int currentEssence = getEssence(stack);
        if (currentEssence <= 0) return stack;
        int spentEssence = 0;
        if (AccessoryAccess.hasAnyAccessoriesOfType(user, "thermos")) {
            ItemStack[] thermoses = AccessoryAccess.getAccessories(user, "thermos");
            for (ItemStack thermosStack : thermoses) {
                if (currentEssence - spentEssence <= 0) break;
                if (thermosStack.getItem() instanceof LifeTunedThermosItem thermos) {
                    int millibuckets = thermos.getMillibuckets(thermosStack);
                    int spentMillibuckets = Math.min(currentEssence - spentEssence, thermos.maxMillibuckets - millibuckets);
                    if (spentMillibuckets > 0) {
                        thermos.setMillibuckets(thermosStack, millibuckets + spentMillibuckets);
                        spentEssence += spentMillibuckets;
                    }
                }
            }
        }

        for (int i = 0; i < user.inventory.size(); i++) {
            if (currentEssence - spentEssence <= VialItem.MILLIBUCKETS_PER_VIAL) break;
            ItemStack vialStack = user.inventory.getStack(i);
            if (vialStack != null && vialStack.getItem() == TheCrimsonForest.emptyVialItem) {
                user.inventory.setStack(i, new ItemStack(TheCrimsonForest.lifeVialItem));
                spentEssence += VialItem.MILLIBUCKETS_PER_VIAL;
                break;
            }
        }

        if (spentEssence != 0) {
            setEssence(stack, currentEssence - spentEssence);
            user.swingHand();
            return stack;
        }
        return super.use(stack, world, user);
    }
}
