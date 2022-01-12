package net.coolsimulations.EffectsLeft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

@Mixin(AbstractContainerScreen.class)
public interface AbstractContainerScreenAccessor {
	
    @Accessor("leftPos")
	int getLeftPos();
	
    @Accessor("topPos")
	int getTopPos();

}
