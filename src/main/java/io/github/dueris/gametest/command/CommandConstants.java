package io.github.dueris.gametest.command;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class CommandConstants {
	public static final Component NOT_PLAYER = Component.literal("This command must be run by a player!");

	public static void notPlayer(@NotNull CommandSourceStack source) {
		source.sendFailure(NOT_PLAYER);
	}
}
