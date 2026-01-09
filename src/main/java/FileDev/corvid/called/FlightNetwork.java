package FileDev.corvid.called;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;

public class FlightNetwork {

    // Unique packet ID for toggling flight
    public static final Identifier TOGGLE_FLIGHT_ID = Identifier.of("corvid", "toggle_flight");

    /** Client-only: send a toggle flight packet to the server */
    public static void sendTogglePacket() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer()); // empty packet
        ClientPlayNetworking.send("TOGGLE_FLIGHT_ID");
    }

    /** Server-only: register the handler for when the client sends the toggle packet */
    public static void registerServerHandler() {
        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_FLIGHT_ID,
                (ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) -> {

                    ServerPlayerEntity player = handler.player;

                    // Run flight toggle safely on the main server thread
                    player.getServer().submit(() -> FlightController.toggleFlight(player));
                });
    }
}
