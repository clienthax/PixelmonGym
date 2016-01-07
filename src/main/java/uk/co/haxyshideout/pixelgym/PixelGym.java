package uk.co.haxyshideout.pixelgym;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.economy.EconomyService;
import uk.co.haxyshideout.pixelgym.commands.GymTestCommand;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.ImmutablePlayerGymData;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymData;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymDataBuilder;

import java.util.Optional;

@Plugin(id = "pixelmongym", name = "PixelmonGym", version = "1.0")
public class PixelGym {

    @Inject
    Logger logger;

    EconomyService economyService;

    @Listener
    public void init(GameInitializationEvent event) {
        registerData();
        registerCommands();
    }

    @Listener
    public void postInit(GamePostInitializationEvent event) {
        setupEconomy();

    }

    private void registerCommands() {
        CommandSpec testCommand = CommandSpec.builder().executor(new GymTestCommand()).build();
        Sponge.getCommandManager().register(this, testCommand, "gymtest");
    }

    private void registerData() {
        Sponge.getDataManager().register(PlayerGymData.class, ImmutablePlayerGymData.class, new PlayerGymDataBuilder());
    }

    private void setupEconomy() {
        Optional<EconomyService> economyServiceOptional = Sponge.getServiceManager().provide(EconomyService.class);
        if(economyServiceOptional.isPresent()) {
            economyService = economyServiceOptional.get();
        } else {
            logger.error("No economy service implementation found, please add a economy plugin to your server");
        }
    }


}
