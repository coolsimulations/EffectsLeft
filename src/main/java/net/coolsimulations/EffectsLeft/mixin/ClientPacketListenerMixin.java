package net.coolsimulations.EffectsLeft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.coolsimulations.EffectsLeft.EffectsLeftUpdateHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

	@Shadow
	private Minecraft minecraft;

	@Inject(at = @At("TAIL"), method = "handleLogin", cancellable = true)
	public void handleLogin(ClientboundLoginPacket packet, CallbackInfo info) {

		if(EffectsLeftUpdateHandler.isOld == true) {
			this.minecraft.player.sendSystemMessage(EffectsLeftUpdateHandler.updateInfo.setStyle(EffectsLeftUpdateHandler.updateInfo.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("el.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/effectsleft-fabric"))));
			this.minecraft.player.sendSystemMessage(EffectsLeftUpdateHandler.updateVersionInfo.setStyle(EffectsLeftUpdateHandler.updateVersionInfo.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("el.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/effectsleft-fabric"))));
		}
	}

}
