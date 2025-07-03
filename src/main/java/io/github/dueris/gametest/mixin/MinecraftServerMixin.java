package io.github.dueris.gametest.mixin;

import io.github.dueris.gametest.GameTest;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Shadow @Final public static Logger LOGGER;

	@Inject(method = "tickChildren", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/ServerFunctionManager;tick()V"))
	public void gametest$tick(BooleanSupplier booleanSupplier, CallbackInfo ci) {
		GameTest.tick((MinecraftServer) (Object) this);
	}
}
