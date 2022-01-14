package net.coolsimulations.EffectsLeft.mixin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Ordering;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.plugin.client.exclusionzones.DefaultPotionEffectExclusionZones;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;

@Mixin(DefaultPotionEffectExclusionZones.class)
public class DefaultPotionEffectExclusionZonesMixin {
	
	@Inject(method = "provide", at = @At(value = "HEAD"), cancellable = true, remap = false, require = 0)
	public void provide(EffectRenderingInventoryScreen<?> screen, CallbackInfoReturnable<Collection<Rectangle>> info) {
		if (screen.canSeeEffects()) {
			Collection<MobEffectInstance> activePotionEffects = Minecraft.getInstance().player.getActiveEffects();
			int x = screen.getGuiLeft() - 124;
			int availableWidth = screen.width - x;
			if (!activePotionEffects.isEmpty() && availableWidth >= 32) {
				boolean fullWidth = x >= 0;
				List<Rectangle> zones = new ArrayList();
				int y = screen.getGuiTop();
				int height = 33;
				if (activePotionEffects.size() > 5) {
					height = 132 / (activePotionEffects.size() - 1);
				}

				for (Iterator var9 = Ordering.natural().sortedCopy(activePotionEffects).iterator(); var9
						.hasNext(); y += height) {
					MobEffectInstance instance = (MobEffectInstance) var9.next();
					zones.add(new Rectangle(fullWidth ? x : screen.getGuiLeft() - 36, y, fullWidth ? 120 : 32, 32));
				}

				info.setReturnValue(zones);
				return;
			} else {
				info.setReturnValue(Collections.emptyList());
				return;
			}
		}
	}

}
