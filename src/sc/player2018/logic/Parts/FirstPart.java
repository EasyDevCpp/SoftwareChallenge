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
    private String doThis;
    private boolean step0WasAtHare;
    private Move m;
    private BufferedWriter fout;

    public FirstPart(BufferedWriter f){
        moveId = 0;
        step = 0;
        fout = f;
        doThis = "";
        step0WasAtHare = false;
    }

    public void update(GameState gs, Player p, Player enemy) {
        this.gs = gs;
        this.p = p;
        this.enemy = enemy;
    }

    public void processAI(){
        logMessage("first part (" + p.getPlayerColor().name() + "): ", true);

        Board b = gs.getBoard();
        ArrayList<Action> actions = new ArrayList<>();

        if(doThis.equals("eatSalad") || doThis.equals("eatCarrot")){
            if(doThis.equals("eatSalad")){
                actions.add(new EatSalad(0));
                doThis = "";
                logMessage("step: 0, eat salad", false);
            } else if(doThis.equals("eatCarrot")){
                actions.add(new ExchangeCarrots(+10));
                doThis = "";
                logMessage("step: 0, eat carrot", false);
            }
        } else{
            if(step == 0) { //to first salad
                if (!enemyOnNextFieldType(FieldType.SALAD) && karottenVerbrauch[b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) {
                    logMessage("step: 0, to salad", false);
                    actions.add(new Advance(b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) - p.getFieldIndex()));
                    doThis = "eatSalad";
                    step = 1;
                } else {
                    if (enemyOnNextFieldType(FieldType.SALAD)) {
                        if(step0WasAtHare || b.getNextFieldByType(FieldType.POSITION_2, p.getFieldIndex()) < b.getNextFieldByType(FieldType.HARE, p.getFieldIndex())){
                            if(karottenVerbrauch[b.getNextFieldByType(FieldType.POSITION_2, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()){
                                actions.add(new Advance(b.getNextFieldByType(FieldType.POSITION_2, p.getFieldIndex()) - p.getFieldIndex()));
                                step0WasAtHare = false;
                                logMessage("step: 0, to pos2", false);
                            } else{
                                actions.add(new Skip(1));
                                logMessage("step: 0, to pos2 skip", false);
                            }
                        } else{
                            if(karottenVerbrauch[b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()){ //stage0WasAtHare == false
                                actions.add(new Advance(b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) - p.getFieldIndex()));
                                actions.add(new Card(CardType.EAT_SALAD, 1));

                                step0WasAtHare = true;
                                logMessage("step: 0, to hare", false);
                            } else{
                                actions.add(new Skip(1));
                                logMessage("step: 0, to hare skip", false);
                            }
                        }
                    } else {
                        logMessage("step: 0, to salad", false);
                        actions.add(new Advance(b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) - p.getFieldIndex()));
                        doThis = "eatSalad";
                        step = 1;
                    }
                }
            } else if(step == 1){
                if(p.getFieldIndex() > enemy.getFieldIndex()){
                    if(!enemyOnNextFieldType(FieldType.SALAD) && karottenVerbrauch[b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex())] <= p.getCarrots()){
                        actions.add(new Advance(b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) - p.getFieldIndex()));  //go to second part
                        doThis = "eatSalad";
                        step = 2;
                        logMessage("step: 1, to salad", false);
                    } else if(!enemyOnNextFieldType(FieldType.POSITION_1) && karottenVerbrauch[b.getNextFieldByType(FieldType.POSITION_1, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()){
                        actions.add(new Advance(b.getNextFieldByType(FieldType.POSITION_1, p.getFieldIndex()) - p.getFieldIndex()));
                        logMessage("step: 1, to pos1", false);
                    } else if(!enemyOnNextFieldType(FieldType.HARE) && p.getCards().contains(CardType.EAT_SALAD) && karottenVerbrauch[b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()){
                        actions.add(new Advance(b.getNextFieldByType(FieldType.HARE, p.getFieldIndex()) - p.getFieldIndex()));
                        actions.add(new Card(CardType.EAT_SALAD, 1));
                        logMessage("step: 1, to hare", false);
                    } else if(!enemyOnNextFieldType(FieldType.CARROT) && karottenVerbrauch[b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()){
                        actions.add(new Advance(b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) - p.getFieldIndex()));
                        doThis = "eatCarrot";
                        logMessage("step: 1, to carrot", false);
                    }
                } else{
                    if(!enemyOnNextFieldType(FieldType.SALAD) && karottenVerbrauch[b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()){
                        actions.add(new Advance(b.getNextFieldByType(FieldType.SALAD, p.getFieldIndex()) - p.getFieldIndex())); //go to second part
                        doThis = "eatSalad";
                        step = 2;
                        logMessage("step: 1, to salad", false);
                    } else if(b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) < b.getNextFieldByType(FieldType.SALAD,p.getFieldIndex()) && !enemyOnNextFieldType(FieldType.CARROT) && karottenVerbrauch[b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()){
                        actions.add(new Advance(b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) - p.getFieldIndex()));
                        doThis = "eatCarrot";
                        logMessage("step: 1, to carrot", false);
                    } else if(b.getPreviousFieldByType(FieldType.HEDGEHOG, p.getFieldIndex()) < b.getNextFieldByType(FieldType.SALAD,p.getFieldIndex()) && !enemyOnPreviousFieldType(FieldType.HEDGEHOG)){
                        actions.add(new FallBack(0));
                        logMessage("step: 1, to hedgehog", false);
                    } else{
                        actions.add(new Skip(1));
                        logMessage("step: 1, skip", false);
                    }
                }
            } else if(step == 2){
                if(!enemyOnNextFieldType(FieldType.POSITION_1) && karottenVerbrauch[b.getNextFieldByType(FieldType.POSITION_1, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()){
                    actions.add(new Advance(b.getNextFieldByType(FieldType.POSITION_1, p.getFieldIndex()) - p.getFieldIndex()));
                    logMessage("step: 2, to pos1", false);
                    logMessage(" !!!!! Now playing: SecondPart", false);
                } else if(!enemyOnNextFieldType(FieldType.CARROT) && karottenVerbrauch[b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()){
                    actions.add(new Advance(b.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) - p.getFieldIndex()));
                    logMessage("step: 2, to carrot", false);
                    logMessage(" !!!!! Now playing: SecondPart", false);
                } else if(!enemyOnNextFieldType(FieldType.HEDGEHOG) && karottenVerbrauch[b.getNextFieldByType(FieldType.HEDGEHOG, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()){
                    actions.add(new Advance(b.getNextFieldByType(FieldType.HEDGEHOG, p.getFieldIndex()) - p.getFieldIndex()));
                    logMessage("step: 2, to headgehog", false);
                    logMessage(" !!!!! Now playing: SecondPart", false);
                } else{
                    actions.add(new Skip(1));
                    logMessage("step: 2, skip", false);
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
        //return gs.getPossibleMoves().get(moveId);
        return m;
    }
}
