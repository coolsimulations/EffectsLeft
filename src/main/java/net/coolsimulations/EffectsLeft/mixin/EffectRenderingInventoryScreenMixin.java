package net.coolsimulations.EffectsLeft.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.coolsimulations.EffectsLeft.ELReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectRenderingInventoryScreenMixin<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

	public EffectRenderingInventoryScreenMixin(T abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
	}
	
	@ModifyVariable(method = "canSeeEffects()Z", at = @At("STORE"), ordinal = 0)
	private int canSeeEffects(int i) {
		return i = this.leftPos - 124;
	}

	@ModifyVariable(method = "renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;II)V", at = @At("STORE"), ordinal = 2)
	private int renderEffects(int k) {
		return k = this.leftPos - 124;
	}
	
	@ModifyVariable(method = "renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;II)V", at = @At("STORE"), ordinal = 0)
	private boolean renderEffectsJ(boolean bl) {
		return (this.leftPos - 124) >= 0;
	}
	
	@Overwrite
	private void renderBackgrounds(PoseStack poseStack, int i, int j, Iterable<MobEffectInstance> iterable, boolean bl) {
		RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);
		int k = this.topPos;

		for (Iterator var7 = iterable.iterator(); var7.hasNext(); k += j) {
			MobEffectInstance mobEffectInstance = (MobEffectInstance) var7.next();
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			if (bl) {
				this.blit(poseStack, i, k, 0, 166, 120, 32);
			} else {
				if(Minecraft.getInstance().getResourcePackRepository().getSelectedIds().contains("programer_art")) {
					RenderSystem.setShaderTexture(0, new ResourceLocation(ELReference.MOD_ID, "textures/gui/container/effects_icon.png"));
					this.blit(poseStack, this.leftPos - 36, k, 157, 190, 32, 32);
				} else
					this.blit(poseStack, this.leftPos - 36, k, 0, 198, 32, 32);
			}
		}

	}
	
	@Overwrite
	private void renderIcons(PoseStack poseStack, int i, int j, Iterable<MobEffectInstance> iterable, boolean bl) {
		MobEffectTextureManager mobEffectTextureManager = this.minecraft.getMobEffectTextures();
		int k = this.topPos;

		for (Iterator var8 = iterable.iterator(); var8.hasNext(); k += j) {
			MobEffectInstance mobEffectInstance = (MobEffectInstance) var8.next();
			MobEffect mobEffect = mobEffectInstance.getEffect();
			TextureAtlasSprite textureAtlasSprite = mobEffectTextureManager.get(mobEffect);
			RenderSystem.setShaderTexture(0, textureAtlasSprite.atlas().location());
			blit(poseStack, i + (bl ? 6 : 95), k + 7, this.getBlitOffset(), 18, 18, textureAtlasSprite);
		}

	}
}
