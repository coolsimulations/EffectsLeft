package net.coolsimulations.EffectsLeft;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class EffectsLeft implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		
		EffectsLeftUpdateHandler.init();
		
		FabricLoader.getInstance().getModContainer(ELReference.MOD_ID)
		.map(container -> ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation(ELReference.MOD_ID, "programmer_art"),
				container, Component.translatable("resourcePack.programmer_art.name").append(Component.literal(" (Effect Left)")), ResourcePackActivationType.NORMAL));
	}
}
