package sc.player2018.logic.Parts;

import sc.plugin2018.*;

import java.util.ArrayList;
import java.util.Random;

public class FirstPart {
    private int[] karottenVerbrauch = {1,3,6,10,15,21,28,36,45,55,66,78,91,105,120,136,153,171,190,210,231,253,276,300,325,351,378,406,435,465,496,528,561,595,630,666,703,741,780,820,861,903,946,990};

    //set in Constructor
    private GameState gs;
    private Player p;
    private Player enemy;
    private int moveId;
    private Board board;

    //own variables
    private int saladId;
    private Move m;

    public FirstPart(){
        moveId = 0;

        saladId = 0;
    }

    public void update(GameState gs, Player p, Player enemy) {
        this.gs = gs;
        this.p = p;
        this.enemy = enemy;
    }

    public void processAI(){
        System.out.println("Working on first part...");

        Random r = new Random();
        moveId = r.nextInt(gs.getPossibleMoves().size());


        /*if(saladId < 10){                                         //So gehen spezifische Züge mit eigens erstellten Aktionen. Hat bisher keinen tieferen Sinn und mindestens 1 Stunde gedauert das herauszufinden
            first();
        }

        ArrayList<Action> actions = new ArrayList<>();
        //actions.add(new ExchangeCarrots(10));
        actions.add(new Advance(1));
        m = new Move(actions);*/
    }

    private void first(){
        /*if(enemyOnNextFieldType(FieldType.SALAD)){
            moveId = board.getNextFieldByType(FieldType.CARROT, p.getFieldIndex());
        }*/
    }

    private boolean enemyOnNextFieldType(FieldType type){
        //return board.getNextFieldByType(type, p.getFieldIndex()) == board.getNextFieldByType(type, enemy.getFieldIndex());
        return false;
    }

    public Move getMove(){
        return gs.getPossibleMoves().get(moveId);
        //return m;
    }
}
