package net.coolsimulations.EffectsLeft;

import net.fabricmc.api.ClientModInitializer;

public class EffectsLeft implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		
		EffectsLeftUpdateHandler.init();
	}
}
