package io.github.dueris.gametest.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.brigadier.CommandDispatcher;
import io.github.dueris.gametest.command.BeginTestCommand;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Commands.class)
public class CommandsMixin {
	@Shadow @Final private static Logger LOGGER;

	@Shadow @Final private CommandDispatcher<CommandSourceStack> dispatcher;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void registerCommands(Commands.CommandSelection commandSelection, CommandBuildContext commandBuildContext, CallbackInfo ci) {
		BeginTestCommand.register(this.dispatcher, commandBuildContext, commandSelection);
	}

	@ModifyExpressionValue(method = "performCommand", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;isDebugEnabled()Z"))
	public boolean printTheDebug(boolean original) {
		return true;
	}
}
