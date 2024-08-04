package com.example.mymod.util.mixin;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PacketBuffer.class)
public class ModIDHiderMixin {
    private static final String MOD_ID = "mymod";

    @Redirect(method = "writeResourceLocation", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ResourceLocation;getResourceDomain()Ljava/lang/String;"))
    private String writeResourceLocation(ResourceLocation resourceLocation) {
        if (resourceLocation.getResourceDomain().equals(MOD_ID)) {
            return "minecraft";
        } else {
            return resourceLocation.getResourceDomain();
        }
    }
}