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
import uk.co.haxyshideout.pixelgym.commands.admin.CloseAllCommand;
import uk.co.haxyshideout.pixelgym.commands.admin.SetWarpCommand;
import uk.co.haxyshideout.pixelgym.commands.admin.TestWarpCommand;
import uk.co.haxyshideout.pixelgym.commands.gymleaders.CloseGymCommand;
import uk.co.haxyshideout.pixelgym.commands.admin.GiveBadgeCommand;
import uk.co.haxyshideout.pixelgym.commands.gymleaders.HealCommand;
import uk.co.haxyshideout.pixelgym.commands.all.JoinCommand;
import uk.co.haxyshideout.pixelgym.commands.all.LeadersCommand;
import uk.co.haxyshideout.pixelgym.commands.all.LeaveCommand;
import uk.co.haxyshideout.pixelgym.commands.all.ListCommand;
import uk.co.haxyshideout.pixelgym.commands.all.ListRulesCommand;
import uk.co.haxyshideout.pixelgym.commands.gymleaders.OpenGymCommand;
import uk.co.haxyshideout.pixelgym.commands.all.QueuePositionCommand;
import uk.co.haxyshideout.pixelgym.commands.gymleaders.QuitCommand;
import uk.co.haxyshideout.pixelgym.commands.admin.ReloadCommand;
import uk.co.haxyshideout.pixelgym.commands.all.ScoreboardCommand;
import uk.co.haxyshideout.pixelgym.commands.gymleaders.SendRulesCommand;
import uk.co.haxyshideout.pixelgym.commands.TestCommand;
import uk.co.haxyshideout.pixelgym.config.PixelGymConfig;
import uk.co.haxyshideout.pixelgym.config.serializers.GymDataEntryTypeSerializer;
import uk.co.haxyshideout.pixelgym.config.serializers.GymPokemonEntryTypeSerializer;
import uk.co.haxyshideout.pixelgym.config.serializers.WarpEntryTypeSerializer;
import uk.co.haxyshideout.pixelgym.data.GymDataEntry;
import uk.co.haxyshideout.pixelgym.data.GymPokemonEntry;
import uk.co.haxyshideout.pixelgym.data.WarpEntry;
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

    Optional<EconomyService> economyService;

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
     * TODO
     * particles for people who have completed all enabled gyms
     * Badge lore should contain all the pixelmon names + lvls + hp?
     * signs
     * some way for the players to show off badges in world
     * Logging - save won/loss and times challenged to a external log
     * copy the bloody config file
     * Ability to set pokemon for the gym + badge etc ingame via commands
     * Clean up command classes due to required gymnames etc causing a lot of duplicated code
     */

    private void registerCommands() {
        /**
         * Commands anyone can use
         */
        CommandSpec leadersCommand = CommandSpec.builder().executor(new LeadersCommand()).build();
        CommandSpec listCommand = CommandSpec.builder().executor(new ListCommand()).build();
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

        /**
         * Gym Leader commands
         */
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
        CommandSpec reloadCommand = CommandSpec.builder().executor(new ReloadCommand()).permission("pixelgym.admin").build();
        CommandSpec setWarpCommand = CommandSpec.builder().executor(new SetWarpCommand()).permission("pixelgym.admin")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("gymName"))),
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("warpName")))
                ).build();
        CommandSpec testWarpCommand = CommandSpec.builder().executor(new TestWarpCommand()).permission("pixelgym.admin")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("gymName"))),
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("warpName")))
                ).build();

        /**
         * Register all the sub commands onto the "gym" command
         */
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
                .child(reloadCommand, "reload")
                .child(setWarpCommand, "setwarp")
                .child(testWarpCommand, "testwarp")
                .build();//TODO Needs a wipedata command to wipe someones data completly
        Sponge.getCommandManager().register(this, mainCommand, "gym");

        CommandSpec testCommand = CommandSpec.builder().executor(new TestCommand()).build();
        Sponge.getCommandManager().register(this, testCommand, "gymtest");

    }

    /**
     * Register any extra sponge related data in here such as AbstractData and Global TypeSerializers
     */
    private void registerData() {
        Sponge.getDataManager().register(PlayerGymInfoData.class, ImmutablePlayerGymInfoData.class, new PlayerGymInfoDataBuilder());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(GymDataEntry.class), new GymDataEntryTypeSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(GymPokemonEntry.class), new GymPokemonEntryTypeSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(WarpEntry.class), new WarpEntryTypeSerializer());
    }

    /**
     * Get the economy service if its available
     */
    private void setupEconomy() {
        economyService = Sponge.getServiceManager().provide(EconomyService.class);
        if(!economyService.isPresent()) {
            logger.error("No economy service implementation found, please add a economy plugin to your server");
        }
    }

    public static PixelGym getInstance() {
        return INSTANCE;
    }

    public Optional<EconomyService> getEconomy() {
        return economyService;
    }

    public static Text getPluginPrefix() {
        return Text.of(TextColors.GRAY, "[", TextColors.GREEN, "PixelGym", TextColors.GRAY, "]", TextColors.RESET, " ");
    }

}
