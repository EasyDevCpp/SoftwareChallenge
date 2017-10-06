package sc.player2018.logic.Parts;

import sc.plugin2018.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class FirstPart {
    private int[] karottenVerbrauch = {1,3,6,10,15,21,28,36,45,55,66,78,91,105,120,136,153,171,190,210,231,253,276,300,325,351,378,406,435,465,496,528,561,595,630,666,703,741,780,820,861,903,946,990};

    //set in Constructor
    private GameState gs;
    private Player p;
    private Player enemy;
    private int moveId;

    //own variables
    private int step;
    private Move m;
    private BufferedWriter fout;

    public FirstPart(BufferedWriter f){
        moveId = 0;
        step = 0;
        fout = f;
    }

    public void update(GameState gs, Player p, Player enemy) {
        this.gs = gs;
        this.p = p;
        this.enemy = enemy;
    }

    public void processAI(){
        logMessage("Working on first part...", true);


        Random r = new Random();
        moveId = r.nextInt(gs.getPossibleMoves().size());

        /*Board b = gs.getBoard();
        ArrayList<Action> actions = new ArrayList<>();


        if(step < 1){
            if(!enemyOnNextFieldType(FieldType.SALAD) && karottenVerbrauch[b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()){

            }
        }


        m = new Move(actions);*/
    }

    private boolean enemyOnNextFieldType(FieldType type){
        return gs.getBoard().getNextFieldByType(type, p.getFieldIndex()) == gs.getBoard().getNextFieldByType(type, enemy.getFieldIndex());
    }

    private void logMessage(String msg, boolean newLine){
        try {
            if(newLine) msg = "\n" + msg;
            fout.write(msg);
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Move getMove(){
        return gs.getPossibleMoves().get(moveId);
        //return m;
    }
}
