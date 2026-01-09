package FileDev.corvid.called;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.Set;

public class FlightController {

    private static final Set<ServerPlayerEntity> flyingPlayers = new HashSet<>();

    public static void controller() {
        FlightNetwork.registerServerHandler();
        registerTickManager();
    }

    public static void toggleFlight(ServerPlayerEntity player) {
        if (flyingPlayers.contains(player)) flyingPlayers.remove(player);
        else flyingPlayers.add(player);

        System.out.println("[CORVID] Flight toggled for " + player.getName().getString());
    }

    private static void registerTickManager() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (flyingPlayers.contains(player)) applyFlight(player);
            }
        });
    }

    private static void applyFlight(ServerPlayerEntity player) {
        Vec3d vel = player.getVelocity();
        vel = vel.add(0, 0.1, 0); // constant upward motion for testing
        player.setVelocity(vel);
        player.velocityModified = true;
    }
}
