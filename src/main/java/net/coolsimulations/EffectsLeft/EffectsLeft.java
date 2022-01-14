package net.coolsimulations.EffectsLeft;

import net.minecraft.Util;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(value = ELReference.MOD_ID)
@Mod.EventBusSubscriber(modid = ELReference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EffectsLeft {

	public EffectsLeft() {
		
		EffectsLeftUpdateHandler.init();
	}
	
	@SubscribeEvent
	public static void onPlayerJoin(ClientPlayerNetworkEvent.LoggedInEvent event) {
		if(EffectsLeftUpdateHandler.isOld == true) {
			event.getPlayer().sendMessage(EffectsLeftUpdateHandler.updateInfo.setStyle(EffectsLeftUpdateHandler.updateInfo.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("el.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/effectsleft"))), Util.NIL_UUID);
			event.getPlayer().sendMessage(EffectsLeftUpdateHandler.updateVersionInfo.setStyle(EffectsLeftUpdateHandler.updateVersionInfo.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("el.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/effectsleft"))), Util.NIL_UUID);
		}
	}
}
