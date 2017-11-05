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
        System.out.println("Working on third part...");

        Board b = gs.getBoard();
        ArrayList<Action> actions = new ArrayList<>();
        int toGo = 64-p.getFieldIndex();


        if (newTask != 0) {
            if (newTask == 1) {
                actions.add(new EatSalad(0));
            } else if (newTask == 2){
                actions.add(new ExchangeCarrots(+10));
            } else if (newTask == 3){
                actions.add(new ExchangeCarrots(-10));
            }
            newTask = 0;
        } else {
            if (p.getSalads() == 0 && (p.getCarrots()-karottenVerbrauch[toGo]) <= 10 && (p.getCarrots()-karottenVerbrauch[toGo]) >= 0) {
                actions.add(new Advance(toGo));
                logMessage("advancing into goal", true);
            } else if (p.getSalads() != 0) { //radical anti-cabbage action
                if (b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) > -1 && !enemyOnNextFieldType(FieldType.SALAD) && karottenVerbrauch[b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) {
                    actions.add(new Advance(b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) - p.getFieldIndex()));
                    newTask = 1;
                    logMessage("to salad", false);
                } else {
                    if (enemyOnNextFieldType(FieldType.SALAD)) {
                        if (b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) > -1 && karottenVerbrauch[b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots() && p.ownsCardOfType(CardType.EAT_SALAD)) {
                            actions.add(new Advance(b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) - p.getFieldIndex()));
                            actions.add(new Card(CardType.EAT_SALAD,1));
                            logMessage("to hare, play eat_salad", false);
                        } else if (b.getNextFieldByType(FieldType.POSITION_2, p.getFieldIndex()) > -1 && karottenVerbrauch[b.getNextFieldByType(FieldType.POSITION_2,p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) {
                            actions.add(new Advance(b.getNextFieldByType(FieldType.POSITION_2,p.getFieldIndex()) - p.getFieldIndex()));
                            logMessage("to pos2", false);
                        } else if (b.getNextFieldByType(FieldType.POSITION_1, p.getFieldIndex()) > -1 && karottenVerbrauch[b.getNextFieldByType(FieldType.POSITION_1,p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) {
                            actions.add(new Advance(b.getNextFieldByType(FieldType.POSITION_1,p.getFieldIndex()) - p.getFieldIndex()));
                            logMessage("to pos1", false);
                        } else if (b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) > -1 && karottenVerbrauch[b.getNextFieldByType(FieldType.CARROT,p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) {
                            actions.add(new Advance(b.getNextFieldByType(FieldType.CARROT,p.getFieldIndex()) - p.getFieldIndex()));
                            newTask = 2;
                            logMessage("to carrot", false);
                        } else if (GameRuleLogic.isValidToFallBack(gs)) { //karottenVerbrauch[p.getFieldIndex() - b.getPreviousFieldByType(FieldType.HEDGEHOG,p.getFieldIndex())] <= p.getCarrots()
                            actions.add(new FallBack());
                            logMessage("to hedgehog",false);
                        } else {
                            actions.add(new Skip());
                            logMessage("skip move", false);
                        }
                    } else {
                        if (b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) > -1 && karottenVerbrauch[b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) {
                            actions.add(new Advance(b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) - p.getFieldIndex()));
                            newTask = 1;
                            logMessage("to salad", false);
                        } else if (b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) > -1 && karottenVerbrauch[b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots() && p.ownsCardOfType(CardType.EAT_SALAD)) {
                            actions.add(new Advance(b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) - p.getFieldIndex()));
                            actions.add(new Card(CardType.EAT_SALAD,1));
                            logMessage("to hare, play eat_salad", false);
                        } else if (b.getNextFieldByType(FieldType.POSITION_2, p.getFieldIndex()) > -1 && karottenVerbrauch[b.getNextFieldByType(FieldType.POSITION_2,p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) {
                            actions.add(new Advance(b.getNextFieldByType(FieldType.POSITION_2,p.getFieldIndex()) - p.getFieldIndex()));
                            logMessage("to pos2", false);
                        } else if (b.getNextFieldByType(FieldType.POSITION_1, p.getFieldIndex()) > -1 && karottenVerbrauch[b.getNextFieldByType(FieldType.POSITION_1,p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) {
                            actions.add(new Advance(b.getNextFieldByType(FieldType.POSITION_1,p.getFieldIndex()) - p.getFieldIndex()));
                            logMessage("to pos1", false);
                        } else if (b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) > -1 && karottenVerbrauch[b.getNextFieldByType(FieldType.CARROT,p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) {
                            actions.add(new Advance(b.getNextFieldByType(FieldType.CARROT,p.getFieldIndex()) - p.getFieldIndex()));
                            if((p.getCarrots()-karottenVerbrauch[toGo]) > 10) { newTask = 3; } else { newTask = 2; }
                            logMessage("to carrot", false);
                        } else if (GameRuleLogic.isValidToFallBack(gs)) {
                            actions.add(new FallBack());
                            logMessage("to hedgehog",false);
                        } else {
                            actions.add(new Skip());
                            logMessage("skip move", false);
                        }

                    }
                }
            } else { //radical anti-carrot action
                if (b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) > -1 && !enemyOnNextFieldType(FieldType.CARROT) && karottenVerbrauch[b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) {
                    actions.add(new Advance(b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) - p.getFieldIndex()));
                    if((p.getCarrots()-karottenVerbrauch[toGo]) > 10) { newTask = 3; } else { newTask = 2; }
                    logMessage("to carrot", false);
                } else {
                    if (b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) > -1 && enemyOnNextFieldType(FieldType.CARROT)) {
                        if (b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) > -1 && karottenVerbrauch[b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots() && p.ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS)) {
                            actions.add(new Advance(b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) - p.getFieldIndex()));
                            if(p.getCarrots()-karottenVerbrauch[64-b.getNextFieldByType(FieldType.HARE, p.getFieldIndex())] > 10) {
                                actions.add(new Card(CardType.TAKE_OR_DROP_CARROTS,20, 1));
                            } else {
                                actions.add(new Card(CardType.TAKE_OR_DROP_CARROTS,-20, 1));
                            }
                            logMessage("to hare, play take_or_drop_carrots", false);
                        } else if ((p.getCarrots()-karottenVerbrauch[toGo]) < 0) {
                            if (b.getNextFieldByType(FieldType.POSITION_2, p.getFieldIndex()) > -1 && karottenVerbrauch[b.getNextFieldByType(FieldType.POSITION_2,p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) {
                                actions.add(new Advance(b.getNextFieldByType(FieldType.POSITION_2,p.getFieldIndex()) - p.getFieldIndex()));
                                logMessage("to pos2", false);
                            } else if (b.getNextFieldByType(FieldType.POSITION_1, p.getFieldIndex()) > -1 && karottenVerbrauch[b.getNextFieldByType(FieldType.POSITION_1,p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) {
                                actions.add(new Advance(b.getNextFieldByType(FieldType.POSITION_1, p.getFieldIndex()) - p.getFieldIndex()));
                                logMessage("to pos1", false);
                            }
                        } else if (GameRuleLogic.isValidToFallBack(gs)) {
                            actions.add(new FallBack(p.getFieldIndex() - b.getPreviousFieldByType(FieldType.HEDGEHOG,p.getFieldIndex())));
                            logMessage("to hedgehog",false);
                        }
                    } else {
                        actions.add(new Skip());
                        logMessage("skip move", false);
                    }
                }
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

    public Move getMove(){
        return m;
    }
}
