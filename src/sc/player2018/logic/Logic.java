package sc.player2018.logic;

import sc.player2018.Starter;
import sc.plugin2018.GameState;
import sc.plugin2018.IGameHandler;
import sc.plugin2018.Move;
import sc.plugin2018.Player;
import sc.shared.GameResult;
import sc.shared.PlayerColor;

import java.util.Random;

public class Logic implements IGameHandler {
    Starter client;
    GameState gs;
    Player p;
    Player enemy;

    public Logic(Starter client) {
        System.out.println("Starte");
        this.client = client;
    }

    @Override
    public void onUpdate(Player player, Player otherPlayer) {
        p = player;
        enemy = otherPlayer;
    }

    @Override
    public void onUpdate(GameState gameState) {
        gs = gameState;
    }

    @Override
    public void onRequestAction() {
        //Hier kommt der wichtige Kram rein
        Random r = new Random();
        int temp = r.nextInt(gs.getPossibleMoves().size());

        System.out.println(temp + "/" + gs.getPossibleMoves().size());

        sendAction(gs.getPossibleMoves().get(temp));
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
        }
    }
}
