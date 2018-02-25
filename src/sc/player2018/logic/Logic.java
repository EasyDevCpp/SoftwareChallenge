package sc.player2018.logic;

import sc.player2018.Starter;
import sc.player2018.logic.Parts.FirstPart;
import sc.player2018.logic.Parts.SecondPart;
import sc.player2018.logic.Parts.ThirdPart;
import sc.plugin2018.*;
import sc.shared.GameResult;
import sc.shared.PlayerColor;
import java.io.*;
import java.util.ArrayList;

public class Logic implements IGameHandler {
    private Starter client;
    private GameState gs;
    private Player p;
    private Player enemy;

    private FirstPart first;
    private SecondPart second;
    private ThirdPart third;
    private BufferedWriter fout;

    public Logic(Starter client) {
        System.out.println("Starte");
        this.client = client;

        try {
            String file = Logic.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();            //erzeugt log-Datei am Speicherort der .jar
            file = file.substring(0, file.lastIndexOf("/")) + "/log.txt";
            fout = new BufferedWriter(new FileWriter(new File(file), true));

            fout.write("\n\n*********************\n\tnew match\n*********************");
            fout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        first=new FirstPart(fout);
        second=new SecondPart(fout);
        third=new ThirdPart(fout);
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
        Move m = null;
        if(p.getFieldIndex()<23) {
            first.update(gs,p,enemy);
            first.processAI();
            m = first.getMove();
        } else if(p.getFieldIndex()<43) {
            second.update(gs,p,enemy);
            second.processAI();
            m = second.getMove();
        } else if(p.getFieldIndex()<=65) {
            third.update(gs,p,enemy);
            third.processAI();
            m = third.getMove();
        }

        if(!ExtendedAI.isMovePossible(m,gs)) {
            m = ExtendedAI.getRandomMove(gs);
        }

        sendAction(m);
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
