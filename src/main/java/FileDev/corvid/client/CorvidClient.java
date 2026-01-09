package FileDev.corvid.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import FileDev.corvid.client.FlightKeybinds;
import FileDev.corvid.called.FlightNetwork;

public class CorvidClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FlightKeybinds.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (FlightKeybinds.TOGGLE_FLIGHT.wasPressed()) {
                FlightNetwork.sendTogglePacket(); // client-only
            }
        });
    }
}
