package ru.nopanching;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import ru.nopanching.event.EventHandler;

public class Command {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("panching")
                .executes(Command::executeTestCommand)
        );
    }
    private static int executeTestCommand(CommandContext<CommandSource> context) {
        CommandSource source = context.getSource();

        if (EventHandler.commandBreak) {
            source.sendSuccess(new TranslationTextComponent("command.true"), true);
        } else {
            source.sendSuccess(new TranslationTextComponent("command.false"), true);
        }

        EventHandler.commandBreak = !EventHandler.commandBreak;

        return 1;
    }
}
