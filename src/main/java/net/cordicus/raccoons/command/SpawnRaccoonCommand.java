package net.cordicus.raccoons.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.entity.custom.RaccoonEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpawnRaccoonCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(
                CommandManager.literal("spawnraccoon").requires((source) -> { return source.hasPermissionLevel(2); }) // requires permissions
                        .then(CommandManager.argument("type", StringArgumentType.string())
                                .executes(context -> {
                                    ServerCommandSource source = context.getSource();
                                    ServerPlayerEntity player = source.getPlayer();
                                    if (player == null) {
                                        return 0;
                                    }
                                    String type = StringArgumentType.getString(context, "type");
                                    spawnRaccoon(player.getWorld(), player.getBlockPos(), type, player);
                                    player.sendMessage(Text.literal("Spawned a " + type + " raccoon!"), false);
                                    return 1;
                                })
                        )
        );
    }

    private static void spawnRaccoon(World world, BlockPos pos, String type, PlayerEntity player) {
        int raccoonType = 0;
        switch (type.toLowerCase()) {
            case "albino":
                raccoonType = 2;
                break;
            case "amethyst":
                raccoonType = 1;
                break;
            case "normal":
            default:
                raccoonType = 0;
                break;
        }
        RaccoonEntity raccoon = new RaccoonEntity(RaccoonsRabiesEntities.RACCOON, world);
        raccoon.setPosition(pos.getX(), pos.getY(), pos.getZ());
        raccoon.setRaccoonType(raccoonType);
        world.spawnEntity(raccoon);
    }
}
