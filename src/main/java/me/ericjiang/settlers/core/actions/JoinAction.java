package me.ericjiang.settlers.core.actions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ericjiang.settlers.core.game.Game;
import me.ericjiang.settlers.core.game.Game.Color;

@NoArgsConstructor
public class JoinAction extends PlayerAction {

    @Getter
    private Color color;

    public JoinAction(String playerId, String playerName, Color color) {
        super(playerId, playerName);
        this.color = color;
    }

    @Override
    public void accept(Game game) {
        game.handleJoinAction(this);
    }
}