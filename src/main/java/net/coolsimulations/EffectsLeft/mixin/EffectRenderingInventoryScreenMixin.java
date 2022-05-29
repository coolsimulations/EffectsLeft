package net.coolsimulations.EffectsLeft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.coolsimulations.EffectsLeft.ELReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectRenderingInventoryScreenMixin<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
	
	private boolean renderIcon;
	private int size;

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
	
	@Inject(method = "renderBackgrounds(Lcom/mojang/blaze3d/vertex/PoseStack;IILjava/lang/Iterable;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 1))
	private void renderBackgroundsM(PoseStack poseStack, int i, int j, Iterable<MobEffectInstance> iterable, boolean bl, CallbackInfo info) {
		if(Minecraft.getInstance().getResourcePackRepository().getSelectedIds().contains("programer_art"))
			RenderSystem.setShaderTexture(0, new ResourceLocation(ELReference.MOD_ID, "textures/gui/container/effects_icon.png"));
	}
	
	@ModifyArgs(method = "renderBackgrounds(Lcom/mojang/blaze3d/vertex/PoseStack;IILjava/lang/Iterable;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 1))
	private void renderBackgroundsI(Args args) {
		
		int l = args.get(3);
		if(Minecraft.getInstance().getResourcePackRepository().getSelectedIds().contains("programer_art"))
			l = 157;
		int m = args.get(4);
		if(Minecraft.getInstance().getResourcePackRepository().getSelectedIds().contains("programer_art"))
			m = 190;
		
		args.set(1, this.leftPos - 36);
		args.set(3, l);
		args.set(4, m);
	}
	
	@Inject(method = "renderIcons(Lcom/mojang/blaze3d/vertex/PoseStack;IILjava/lang/Iterable;Z)V", at = @At("HEAD"))
	private void renderIconsBool(PoseStack poseStack, int i, int j, Iterable<MobEffectInstance> iterable, boolean bl, CallbackInfo info) {
	  renderIcon = bl;
	  size = i;
	}
	
	@ModifyArg(method = "renderIcons(Lcom/mojang/blaze3d/vertex/PoseStack;IILjava/lang/Iterable;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V"), index = 1)
	private int renderIcons(int i) {
		return size + (renderIcon ? 6 : 95);
	}
}
