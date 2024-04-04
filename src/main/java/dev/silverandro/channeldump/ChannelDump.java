package dev.silverandro.channeldump;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Set;

public class ChannelDump implements ModInitializer {
    static int tick = 0;
    @Override
    public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (tick++ == 5 * 20) {
                tick = 0;
                List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
                for (ServerPlayerEntity player : players) {
                    Set<Identifier> sendable = ServerPlayNetworking.getSendable(player);
                    StringBuilder output = new StringBuilder()
                            .append("Player: ").append(player.getGameProfile().getName()).append('\n')
                            .append("Channels:").append('\n');
                    for (Identifier id : sendable) {
                        output.append('\t').append(id).append('\n');
                    }
                    System.out.println(output);
                }
            }
        });
    }
}
