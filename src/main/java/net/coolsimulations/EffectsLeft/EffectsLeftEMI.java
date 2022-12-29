package net.coolsimulations.EffectsLeft;

import java.util.Collection;

import dev.emi.emi.EmiExclusionAreas;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.config.EmiConfig;
import dev.emi.emi.mixin.accessor.HandledScreenAccessor;
import net.coolsimulations.EffectsLeft.mixin.AbstractContainerScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;

public class EffectsLeftEMI implements EmiPlugin {

	@Override
	public void register(EmiRegistry registry) {
		
		EmiExclusionAreas.generic.remove(0);
		
		registry.addGenericExclusionArea((screen, consumer) -> {
			if (screen instanceof EffectRenderingInventoryScreen<?> inv) {
				Minecraft client = Minecraft.getInstance();
				Collection<MobEffectInstance> collection = client.player.getActiveEffects();
				if (!collection.isEmpty()) {
					int k = 33;
					if (collection.size() > 5) {
						k = 132 / (collection.size() - 1);
					}
					int right = ((AbstractContainerScreenAccessor) screen).getLeftPos() - 36;
					int rightWidth = inv.width - right;
					if (rightWidth >= 32) {
						int top = ((AbstractContainerScreenAccessor) inv).getTopPos();
						int height = (collection.size() - 1) * k + 32;
						int left, width;
						if (EmiConfig.moveEffects) {
							int size = collection.size();
							top = ((AbstractContainerScreenAccessor) inv).getTopPos() - 34;
							if (((Object) screen) instanceof CreativeModeInventoryScreen) {
								top -= 28;
							}
							int xOff = 34;
							if (size == 1) {
								xOff = 122;
							} else if (size > 5) {
								xOff = (((HandledScreenAccessor) inv).getBackgroundWidth() - 32) / (size - 1);
							}
							width = Math.max(122, (size - 1) * xOff + 32);
							left = ((AbstractContainerScreenAccessor) inv).getLeftPos() + (((HandledScreenAccessor) inv).getBackgroundWidth() - width) / 2;
							height = 32;
						} else {
							left = right;
							width = 32;
						}
						consumer.accept(new Bounds(left, top, width, height));
					}
				}
			}
		});
		
		EmiExclusionAreas.fromClass.replace(EffectRenderingInventoryScreen.class, EmiExclusionAreas.generic);
	}
}
