package sc.player2018.logic.Parts;

import sc.plugin2018.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SecondPart {
    private int[] ncarrots={1,3,6,10,15,21,28,36,45,55,66,78,91,105,120,136,153,171,190,210,231,253,276,300,325,351,378,406,435,465,496,528,561,595,630,666,703,741,780,820,861,903,946,990};

    private GameState gs;
    private Player p;
    private Player enemy;
    private ArrayList<Action> actions=new ArrayList<>();
    private int distances[]=new int[6];
    private boolean enemy_fields[]=new boolean[6];
    private BufferedWriter fout;
    private Move m;

    public SecondPart(BufferedWriter f){
        fout=f;
    }

    public void update(GameState gs, Player p, Player enemy) {
        this.gs = gs;
        this.p = p;
        this.enemy = enemy;
    }

    public void processAI() {
        Board b=gs.getBoard();
        distances[0]=ncarrots[b.getNextFieldByType(FieldType.HEDGEHOG,p.getFieldIndex())-p.getFieldIndex()];
        distances[1]=ncarrots[b.getNextFieldByType(FieldType.HARE,p.getFieldIndex())-p.getFieldIndex()];
        distances[2]=ncarrots[b.getNextFieldByType(FieldType.POSITION_1,p.getFieldIndex())-p.getFieldIndex()];
        distances[3]=ncarrots[b.getNextFieldByType(FieldType.POSITION_2,p.getFieldIndex())-p.getFieldIndex()];
        distances[4]=ncarrots[b.getNextFieldByType(FieldType.SALAD,p.getFieldIndex())-p.getFieldIndex()];
        distances[5]=ncarrots[b.getNextFieldByType(FieldType.CARROT,p.getFieldIndex())-p.getFieldIndex()];
        enemy_fields[0]=enemyOnNextFieldType(FieldType.HEDGEHOG);
        enemy_fields[1]=enemyOnNextFieldType(FieldType.HARE);
        enemy_fields[2]=enemyOnNextFieldType(FieldType.POSITION_1);
        enemy_fields[3]=enemyOnNextFieldType(FieldType.POSITION_2);
        enemy_fields[4]=enemyOnNextFieldType(FieldType.SALAD);
        enemy_fields[5]=enemyOnNextFieldType(FieldType.CARROT);
        
        if(!enemy_fields[1]&&distances[1]<=p.getCarrots()) {
            actions.add(new Advance(distances[1]));
            if(p.ownsCardOfType(CardType.EAT_SALAD)) {
                actions.add(new Card(CardType.EAT_SALAD,1));
            } else if(p.getCarrots()<=10&&p.ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS)) {
                actions.add(new Card(CardType.TAKE_OR_DROP_CARROTS,20));
            } else if(p.getCarrots()>=80&&p.ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS)) {
                actions.add(new Card(CardType.TAKE_OR_DROP_CARROTS,-20));
            } else if(enemy_fields[0]&&p.ownsCardOfType(CardType.HURRY_AHEAD)) {
                actions.add(new Card(CardType.HURRY_AHEAD,1));
            }
        } else if((!enemy_fields[2]&&distances[2]<=p.getCarrots())||(!enemy_fields[3]&&distances[3]<=p.getCarrots())) {
            if(p.getFieldIndex()>enemy.getFieldIndex()) {
                actions.add(new Advance(distances[2]));
            } else {
                actions.add(new Advance(distances[3]));
            }
        } else if(!enemy_fields[5]&&distances[5]<=p.getCarrots()) {
            actions.add(new Advance(distances[5]));
            if(p.getCarrots()<20) {
                actions.add(new ExchangeCarrots(10));
            } else if(p.getCarrots()>80) {
                actions.add(new ExchangeCarrots(-10));
            } else {
                actions.add(new Skip());
            }
        }
        m = new Move(actions);
        m.orderActions();
    }

    private boolean enemyOnNextFieldType(FieldType type){
        return gs.getBoard().getNextFieldByType(type, p.getFieldIndex()) == enemy.getFieldIndex();
    }

    private boolean enemyOnPreviousFieldType(FieldType type){
        return gs.getBoard().getPreviousFieldByType(type, p.getFieldIndex()) == enemy.getFieldIndex();
    }

    public Move getMove() {
        return m;
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
}
