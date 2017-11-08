package sc.player2018.logic.Parts;


import sc.player2018.Starter;
import sc.player2018.logic.RandomLogic;
import sc.plugin2018.*;

import java.util.*;
import sc.plugin2018.util.GameRuleLogic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

public class ThirdPart {
    private int[] karottenVerbrauch = {1,3,6,10,15,21,28,36,45,55,66,78,91,105,120,136,153,171,190,210,231,253,276,300,325,351,378,406,435,465,496,528,561,595,630,666,703,741,780,820,861,903,946,990};

    //given vars
    private GameState gs;
    private Player p;
    private Player enemy;
    private int moveId;

    //own vars
    private Move m;
    private BufferedWriter fout;
    private int newTask; //1=Salad, 2=Carrot+, 3=Carrot-

    public ThirdPart(BufferedWriter f){
        moveId = 0;
        fout = f;
        newTask = 0;
    }

    public void update(GameState gs, Player p, Player enemy) {
        this.gs = gs;
        this.p = p;
        this.enemy = enemy;
    }

    private void logMessage(String msg, boolean newLine) {
        try {
            if (newLine) msg = "\n" + msg;
            fout.write(msg);
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processAI(){
        logMessage("third part ("+p.getPlayerColor().name()+"): ",true);

        Board b = gs.getBoard();
        ArrayList<Action> actions = new ArrayList<>();


        m = new Move(actions);
        m.orderActions();
    }

    private boolean enemyOnNextFieldType(FieldType type){
        return gs.getBoard().getNextFieldByType(type, p.getFieldIndex()) == enemy.getFieldIndex();
    }

    private boolean enemyOnPreviousFieldType(FieldType type){
        return gs.getBoard().getPreviousFieldByType(type, p.getFieldIndex()) == enemy.getFieldIndex();
    }

    public Move getMove(){
        return m;
    }
}