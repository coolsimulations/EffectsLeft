package net.coolsimulations.EffectsLeft;

import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = ELReference.MOD_ID)
@Mod.EventBusSubscriber(modid = ELReference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EffectsLeft {

	public EffectsLeft() {

		EffectsLeftUpdateHandler.init();

        FMLJavaModLoadingContext.get().getModEventBus().register(this);
		ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> "client-only", (v, n) -> n));
	}

	@SubscribeEvent
	public static void onPlayerJoin(ClientPlayerNetworkEvent.LoggingIn event) {
		if(EffectsLeftUpdateHandler.isOld == true) {
			event.getPlayer().sendSystemMessage(EffectsLeftUpdateHandler.updateInfo.setStyle(EffectsLeftUpdateHandler.updateInfo.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("el.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/effectsleft"))));
			event.getPlayer().sendSystemMessage(EffectsLeftUpdateHandler.updateVersionInfo.setStyle(EffectsLeftUpdateHandler.updateVersionInfo.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("el.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/effectsleft"))));
		}
	}

	@SubscribeEvent
	public void addPackFinders(AddPackFindersEvent event)
	{
		if (event.getPackType() == PackType.CLIENT_RESOURCES)
		{
			event.addRepositorySource(consumer -> consumer.accept(Pack.readMetaAndCreate(
					"effectsleft:programmer_art",
					Component.translatable("resourcePack.programmer_art.name").append(Component.literal(" (Effect Left)")),
					false,
					path -> new PathPackResources(path, ModList.get().getModFileById(ELReference.MOD_ID).getFile().findResource("programmer_art"), true),
					PackType.CLIENT_RESOURCES,
					Pack.Position.TOP,
					PackSource.BUILT_IN
					)));
		}
	}
}
