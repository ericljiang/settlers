package me.ericjiang.frontiersmen.library;

import static org.easymock.EasyMock.*;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import java.security.GeneralSecurityException;
import java.util.function.Consumer;
import me.ericjiang.frontiersmen.library.player.ClientConnectionEvent;
import me.ericjiang.frontiersmen.library.player.ClientDisconnectionEvent;
import me.ericjiang.frontiersmen.library.player.PingEvent;
import me.ericjiang.frontiersmen.library.player.PlayerConnection;
import me.ericjiang.frontiersmen.library.player.PlayerConnectionEvent;
import me.ericjiang.frontiersmen.library.player.PlayerDisconnectionEvent;
import me.ericjiang.frontiersmen.library.player.PongEvent;

public class MultiplayerModuleTest extends EasyMockSupport {

    private static final String PLAYER_ID = "1";

    private MultiplayerModule module;

    @Before
    public void before() {
        module = new MultiplayerModule() {
            @Override
            protected StateEvent toStateEvent() {
                return null;
            }

            @Override
            protected String getIdentifier() {
                return null;
            }

            @Override
            protected boolean allowConnection(String playerId) {
                return true;
            }
        };
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldTriggerClientAndPlayerConnectionEvents() throws GeneralSecurityException {
        IMocksControl control = createStrictControl();
        Consumer<ClientConnectionEvent> clientConnectionEventHandler = control.createMock(Consumer.class);
        Consumer<PlayerConnectionEvent> playerConnectionEventHandler = control.createMock(Consumer.class);
        PlayerConnection connection1 = createNiceMock(PlayerConnection.class);
        PlayerConnection connection2 = createNiceMock(PlayerConnection.class);

        clientConnectionEventHandler.accept(anyObject(ClientConnectionEvent.class));
        expectLastCall();
        playerConnectionEventHandler.accept(anyObject(PlayerConnectionEvent.class));
        expectLastCall();
        clientConnectionEventHandler.accept(anyObject(ClientConnectionEvent.class));
        expectLastCall();
        replayAll();

        module.on(ClientConnectionEvent.class, clientConnectionEventHandler);
        module.on(PlayerConnectionEvent.class, playerConnectionEventHandler);
        module.addConnection(PLAYER_ID, connection1);
        module.addConnection(PLAYER_ID, connection2);
        verifyAll();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldTriggerClientAndPlayerDisonnectionEvents() throws GeneralSecurityException {
        IMocksControl control = createStrictControl();
        Consumer<ClientDisconnectionEvent> clientDisconnectionEventHandler = control.createMock(Consumer.class);
        Consumer<PlayerDisconnectionEvent> playerDisconnectionEventHandler = control.createMock(Consumer.class);
        PlayerConnection connection1 = createNiceMock(PlayerConnection.class);
        PlayerConnection connection2 = createNiceMock(PlayerConnection.class);

        clientDisconnectionEventHandler.accept(anyObject(ClientDisconnectionEvent.class));
        expectLastCall();
        clientDisconnectionEventHandler.accept(anyObject(ClientDisconnectionEvent.class));
        expectLastCall();
        playerDisconnectionEventHandler.accept(anyObject(PlayerDisconnectionEvent.class));
        expectLastCall();
        replayAll();

        module.on(ClientDisconnectionEvent.class, clientDisconnectionEventHandler);
        module.on(PlayerDisconnectionEvent.class, playerDisconnectionEventHandler);
        module.addConnection(PLAYER_ID, connection1);
        module.addConnection(PLAYER_ID, connection2);
        module.removeConnection(PLAYER_ID, connection1, "");
        module.removeConnection(PLAYER_ID, connection2, "");
        verifyAll();
    }

    @Test
    public void shouldPongOnPing() throws GeneralSecurityException {
        PlayerConnection playerConnection = createNiceMock(PlayerConnection.class);
        playerConnection.transmit(isA(PongEvent.class));
        expectLastCall();
        replayAll();

        module.addConnection(PLAYER_ID, playerConnection);
        PingEvent ping = new PingEvent();
        ping.setPlayerId(PLAYER_ID);
        module.handleEvent(ping);
        verifyAll();
    }
}
