package fr.flastar.magiqolsky.utils;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandSource;

public class CommandUtils {
    public static boolean isCommandAvailable(String command) {
        ClientPlayNetworkHandler handler = MinecraftClient.getInstance().getNetworkHandler();

        if (handler != null) {
            CommandDispatcher<CommandSource> dispatcher = handler.getCommandDispatcher();

            return dispatcher.getRoot().getChild(command) != null;
        }

        return false;
    }
}
