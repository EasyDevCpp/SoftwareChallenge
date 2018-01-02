package sc.player2018.logic;

import sc.player2018.Starter;
import sc.plugin2018.GameState;
import sc.plugin2018.IGameHandler;
import sc.plugin2018.Move;
import sc.plugin2018.Player;
import sc.shared.GameResult;
import sc.shared.PlayerColor;

public class Logic implements IGameHandler {
    private Starter client;
    private FLSLogic fls;

    public Logic(Starter client) {
        this.client = client;
        fls = new FLSLogic();
    }

    @Override
    public void onUpdate(Player player, Player otherPlayer) {
        //do nothing, becuase in gameState
    }

    @Override
    public void onUpdate(GameState gameState) {
        fls.update(gameState);
    }

    @Override
    public void onRequestAction() {
        fls.play();
        sendAction(fls.getMove());
    }

    @Override
    public void sendAction(Move move) {
        client.sendMove(move);
    }

    @Override
    public void gameEnded(GameResult gameResult, PlayerColor playerColor, String errorMsg) {
        if(playerColor.name() != null && !playerColor.name().equals("")){
            System.out.println("Das Spiel wurde beendet! " + gameResult.getScores().toString());
        } else{
            System.out.println("Kein Spieler hat gewonnen hat gewonnen. ERROR:" + errorMsg);
            fls.onError();
        }
    }
}
