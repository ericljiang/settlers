package me.ericjiang.frontiersmen.config;

import dagger.Component;
import me.ericjiang.frontiersmen.library.auth.Authenticator;
import me.ericjiang.frontiersmen.websockets.WebSocketTranslator;

import javax.inject.Named;
import javax.inject.Singleton;

@Component(modules = {
    AuthenticatorModule.class,
    DatabaseModule.class,
    FrontiersmenModule.class,
    PregameModule.class,
    GameModule.class,
    LobbyModule.class
})
@Singleton
public interface Frontiersmen {

    int port();

    @Named("lobbyWebSocketHandler") WebSocketTranslator lobbyWebSocketHandler();

    @Named("pregameWebSocketHandler") WebSocketTranslator pregameWebSocketHandler();

    @Named("gameWebSocketHandler") WebSocketTranslator gameWebSocketHandler();

    Authenticator authenticator();

}
