package uk.co.haxyshideout.pixelgym;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.haxyshideout.pixelgym.commands.CloseAllCommand;
import uk.co.haxyshideout.pixelgym.commands.CloseGymCommand;
import uk.co.haxyshideout.pixelgym.commands.GiveBadgeCommand;
import uk.co.haxyshideout.pixelgym.commands.HealCommand;
import uk.co.haxyshideout.pixelgym.commands.JoinCommand;
import uk.co.haxyshideout.pixelgym.commands.LeadersCommand;
import uk.co.haxyshideout.pixelgym.commands.LeaveCommand;
import uk.co.haxyshideout.pixelgym.commands.ListCommand;
import uk.co.haxyshideout.pixelgym.commands.ListRulesCommand;
import uk.co.haxyshideout.pixelgym.commands.OpenGymCommand;
import uk.co.haxyshideout.pixelgym.commands.QueuePositionCommand;
import uk.co.haxyshideout.pixelgym.commands.QuitCommand;
import uk.co.haxyshideout.pixelgym.commands.ScoreboardCommand;
import uk.co.haxyshideout.pixelgym.commands.SendRulesCommand;
import uk.co.haxyshideout.pixelgym.commands.TestCommand;
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;
import uk.co.haxyshideout.pixelgym.config.serializers.GymDataEntryTypeSerializer;
import uk.co.haxyshideout.pixelgym.config.serializers.GymPokemonEntryTypeSerializer;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.GymPokemonEntry;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.ImmutablePlayerGymInfoData;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoData;
import uk.co.haxyshideout.pixelgym.data.spongedata.player.gym.PlayerGymInfoDataBuilder;
import uk.co.haxyshideout.pixelgym.events.EventHandler;

import java.util.Optional;

@Plugin(id = "pixelmongym", name = "PixelmonGym", version = "1.0")
public class PixelGym {

    @Inject
    Logger logger;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configurationLoader;

    private static PixelGym INSTANCE;

    EconomyService economyService;

    @Listener
    public void init(GameInitializationEvent event) {
        INSTANCE = this;
        registerData();
        registerCommands();
        new PixelGymConfig(configurationLoader);
        Sponge.getEventManager().registerListeners(this, new EventHandler());
    }

    @Listener
    public void postInit(GamePostInitializationEvent event) {
        setupEconomy();
    }

    /**
     * particles for people who have completed all enabled gyms
     * Badge lore should contain all the pixelmon names + lvls + hp?
     * signs
     * some way for the players to show off badges in world
     * Logging - save won/loss and times challenged to a external log
     * copy the bloody config file
     */

    private void registerCommands() {
        CommandSpec leadersCommand = CommandSpec.builder().executor(new LeadersCommand()).build();
        CommandSpec listCommand = CommandSpec.builder().executor(new ListCommand()).build();
        CommandSpec sendRulesCommand = CommandSpec.builder().executor(new SendRulesCommand())//Auto complete is kinda broken on child args for some reason - sendrules playername gymname
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("gymName")))
                )
                .description(Text.of(TextColors.GREEN, "/gym sendrules (Username) <gym name>", TextColors.DARK_GREEN, " - Force shows the specified gym\'s rules to the player specified."))//Never shown anywhere!?
                .build();
        CommandSpec openGymCommand = CommandSpec.builder().executor(new OpenGymCommand())
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("gymName")))
                ).build();
        CommandSpec closeGymCommand = CommandSpec.builder().executor(new CloseGymCommand())
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("gymName")))
                ).build();
        CommandSpec rulesCommand = CommandSpec.builder().executor(new ListRulesCommand())
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("gymName")))
                ).build();
        CommandSpec joinCommand = CommandSpec.builder().executor(new JoinCommand())
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("gymName")))
                ).build();
        CommandSpec leaveCommand = CommandSpec.builder().executor(new LeaveCommand())
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("gymName")))
                ).build();
        CommandSpec queuePositionCommand = CommandSpec.builder().executor(new QueuePositionCommand())
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("gymName")))
                ).build();
        CommandSpec scoreboardCommand = CommandSpec.builder().executor(new ScoreboardCommand()).build();
        CommandSpec healCommand = CommandSpec.builder().executor(new HealCommand()).build();
        CommandSpec quitCommand = CommandSpec.builder().executor(new QuitCommand()).build();
        /**
         * Admin commands
         */
        CommandSpec closeAllCommand = CommandSpec.builder().executor(new CloseAllCommand()).permission("pixelgym.admin").build();
        CommandSpec giveBadgeCommand = CommandSpec.builder().executor(new GiveBadgeCommand()).permission("pixelgym.admin")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("gymName")))
                ).build();


        CommandSpec mainCommand = CommandSpec.builder()
                .child(leadersCommand, "leaders")
                .child(listCommand, "list")
                .child(sendRulesCommand, "sendrules")
                .child(openGymCommand, "opengym")
                .child(closeGymCommand, "closegym")
                .child(rulesCommand, "rules")
                .child(joinCommand, "join")
                .child(leaveCommand, "leave")
                .child(queuePositionCommand, "check", "position")
                .child(scoreboardCommand, "scoreboard")
                .child(closeAllCommand, "closeall")
                .child(healCommand, "heal")
                .child(quitCommand, "quit")
                .child(giveBadgeCommand, "givebadge")
                .build();
        Sponge.getCommandManager().register(this, mainCommand, "gym");

        CommandSpec testCommand = CommandSpec.builder().executor(new TestCommand()).build();
        Sponge.getCommandManager().register(this, testCommand, "gymtest");

    }

    /**
     * Register any extra sponge related data in here
     */
    private void registerData() {
        Sponge.getDataManager().register(PlayerGymInfoData.class, ImmutablePlayerGymInfoData.class, new PlayerGymInfoDataBuilder());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(GymDataEntry.class), new GymDataEntryTypeSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(GymPokemonEntry.class), new GymPokemonEntryTypeSerializer());
    }

    private void setupEconomy() {
        Optional<EconomyService> economyServiceOptional = Sponge.getServiceManager().provide(EconomyService.class);
        if(economyServiceOptional.isPresent()) {
            economyService = economyServiceOptional.get();
        } else {
            logger.error("No economy service implementation found, please add a economy plugin to your server");
        }
    }

    public static PixelGym getInstance() {
        return INSTANCE;
    }

}
