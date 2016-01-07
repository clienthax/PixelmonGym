package uk.co.haxyshideout.pixelgym.commands;

import com.google.common.collect.Lists;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.entity.living.player.Player;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymData;

import java.util.ArrayList;

public class GymTestCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = (Player) src;
        ArrayList<String> list = Lists.newArrayList();
        PlayerGymData gymData = new PlayerGymData(list);
        DataTransactionResult result = player.offer(gymData);//success

        PlayerGymData gymData1 = player.get(PlayerGymData.class).get();
        System.out.println(gymData1.getValues());

        System.out.println(result);

        return CommandResult.success();
    }
}
