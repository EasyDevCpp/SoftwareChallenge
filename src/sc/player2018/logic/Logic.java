package sc.player2018.logic;

import sc.player2018.Starter;
import sc.player2018.logic.Parts.FirstPart;
import sc.player2018.logic.Parts.SecondPart;
import sc.player2018.logic.Parts.ThirdPart;
import sc.plugin2018.GameState;
import sc.plugin2018.IGameHandler;
import sc.plugin2018.Move;
import sc.plugin2018.Player;
import sc.shared.GameResult;
import sc.shared.PlayerColor;

import java.util.ArrayList;
import java.util.Random;

public class Logic implements IGameHandler {
    private Starter client;
    private GameState gs;
    private Player p;
    private Player enemy;

    private FirstPart first;
    private SecondPart second;
    private ThirdPart third;

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
        if(gs.getTurn()<=10) {
            first=new FirstPart(gs,p,enemy);
            first.processAI();
            sendAction(first.getMove());
        } else if(gs.getTurn()<=25) {
            second=new SecondPart(gs,p,enemy);
            second.processAI();
            sendAction(second.getMove());
        } else if(gs.getTurn()<=30) {
            third=new ThirdPart(gs,p,enemy);
            third.processAI();
            sendAction(third.getMove());
        }
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
