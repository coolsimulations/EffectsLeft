package net.coolsimulations.EffectsLeft.mixin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.client.EffectRenderer;
import net.minecraftforge.client.RenderProperties;

@Mixin(targets = "mezz.jei.plugins.vanilla.InventoryEffectRendererGuiHandler")
public class InventoryEffectRendererGuiHandlerMixin <T extends AbstractContainerMenu> {

	@Inject(method = "getGuiExtraAreas", at = @At(value = "HEAD"), cancellable = true, remap = false, require = 0)
	public void getGuiExtraAreas(EffectRenderingInventoryScreen<T> containerScreen, CallbackInfoReturnable<List<Rect2i>> info) {
		Minecraft minecraft = containerScreen.getMinecraft();
		LocalPlayer player = minecraft.player;
		if (player != null) {
			Collection<MobEffectInstance> activePotionEffects = player.getActiveEffects();

			if (!activePotionEffects.isEmpty()) {
				List<Rect2i> areas = new ArrayList<>();
				int x = containerScreen.getGuiLeft() - 124;
				int y = containerScreen.getGuiTop();
				int availableSpace = containerScreen.width - x;
				boolean fullWidth = x >= 0;
				int width = fullWidth ? 120 : 32;

				int height = 33;
				if (activePotionEffects.size() > 5) {
					height = 132 / (activePotionEffects.size() - 1);
				}
				for (MobEffectInstance potionEffect : activePotionEffects) {
					EffectRenderer effectRenderer = RenderProperties.getEffectRenderer(potionEffect);
					if (effectRenderer.shouldRender(potionEffect)) {
						areas.add(new Rect2i(fullWidth ? x : containerScreen.getGuiLeft() - 36, y, width, height));
						y += height;
					}
				}
				info.setReturnValue(areas);
				return;
			}
		}
	}

}
