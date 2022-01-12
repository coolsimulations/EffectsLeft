package net.coolsimulations.EffectsLeft.mixin;

import java.io.File;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.coolsimulations.EffectsLeft.programmerartinjector.ProgrammerArtResourcePack;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.server.packs.PackResources;

/***
 * 
 * @author ExtraCraftTX
 * @see https://github.com/ExtraCrafTX/ProgrammerArtInjector
 *
 */
@Mixin(ClientPackSource.class)
public class ClientPackSourceMixin {
	
	@Inject(method = "createProgrammerArtZipPack", at = @At("HEAD"), cancellable = true)
    private static void onCreateProgrammerResourcePack(File file, CallbackInfoReturnable<PackResources> info){
        info.setReturnValue(new ProgrammerArtResourcePack(file));
    }

}
