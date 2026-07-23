package com.example.originmodstudy.item;

import com.example.originmodstudy.effect.ModEffects;
import com.example.originmodstudy.util.OriginUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;

/**
 * A light, fast throwing spear. Anyone can craft and swing/throw it, but the Bleed-on-hit (both
 * melee and thrown) and the airborne throw bonus only apply for the Harpy origin — same
 * hit-time-gating pattern as FangItem/PetrifyingTridentItem, see OriginUtil for why.
 *
 * <p>Lighter/faster than the vanilla trident stats {@link TridentItem} would otherwise give it
 * (8.0 damage / -2.9 speed) — {@code getDefaultAttributeModifiers} is overridden with its own
 * Multimap since TridentItem builds its modifiers in its constructor from hardcoded constants,
 * not a field subclasses can adjust.
 *
 * <p>The airborne-throw bonus and thrown-hit Bleed live in {@code ThrownTridentMixin}, not here:
 * vanilla's {@code ThrownTrident.onHitEntity} deals its own damage directly (confirmed by
 * decompiling the real class), entirely bypassing {@code hurtEnemy} — that method is melee-only.
 */
public class HarpyJavelinItem extends TridentItem {
	private static final ResourceLocation HARPY_ORIGIN_ID = new ResourceLocation("arachne", "harpy");
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;

	public HarpyJavelinItem(Properties properties) {
		super(properties);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
				BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 6.0, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(
				BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.4, AttributeModifier.Operation.ADDITION));
		this.defaultModifiers = builder.build();
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
		if (slot == EquipmentSlot.MAINHAND) {
			return this.defaultModifiers;
		}
		return super.getDefaultAttributeModifiers(slot);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		boolean result = super.hurtEnemy(stack, target, attacker);
		applyBleed(target, attacker);
		return result;
	}

	/** Shared with ThrownTridentMixin so the thrown-hit path applies the exact same rule. */
	public static void applyBleed(LivingEntity target, LivingEntity attacker) {
		if (target.getMobType() != MobType.UNDEAD && OriginUtil.hasOrigin(attacker, HARPY_ORIGIN_ID)) {
			target.addEffect(new MobEffectInstance(ModEffects.BLEED, 200, 0));
		}
	}

	public static boolean isHarpyOrigin(LivingEntity entity) {
		return OriginUtil.hasOrigin(entity, HARPY_ORIGIN_ID);
	}
}
