package sc.player2018.logic.Parts;

import sc.player2018.logic.FLSLogic;
import sc.plugin2018.*;
import java.util.ArrayList;


public class FirstPart extends FLSLogic {

    public void processAI(){
        logMessage("first part (" + player.getPlayerColor().name() + "): ", true);

        Board b = gs.getBoard();
        ArrayList<Action> actions = new ArrayList<>();

        if (step == 0) { //to first salad
            if (!enemyOnNextFieldType(FieldType.SALAD) && karottenVerbrauch[b.getNextFieldByType(FieldType.SALAD, player.getFieldIndex()) - player.getFieldIndex()] <= player.getCarrots()) {
                logMessage("step: 0, to salad", false);
                actions.add(new Advance(b.getNextFieldByType(FieldType.SALAD, player.getFieldIndex()) - player.getFieldIndex()));
                newTask = 1;
                step = 1;
            } else {
                if (enemyOnNextFieldType(FieldType.SALAD)) {
                    if (step0WasAtHare || b.getNextFieldByType(FieldType.POSITION_2, player.getFieldIndex()) < b.getNextFieldByType(FieldType.HARE, player.getFieldIndex())) {
                        if (karottenVerbrauch[b.getNextFieldByType(FieldType.POSITION_2, player.getFieldIndex()) - player.getFieldIndex()] <= player.getCarrots()) {
                            actions.add(new Advance(b.getNextFieldByType(FieldType.POSITION_2, player.getFieldIndex()) - player.getFieldIndex()));
                            step0WasAtHare = false;
                            logMessage("step: 0, to pos2", false);
                        } else {
                            actions.add(new Skip(1));
                            logMessage("step: 0, to pos2 skip", false);
                        }
                    } else {
                        if (karottenVerbrauch[b.getNextFieldByType(FieldType.HARE, player.getFieldIndex()) - player.getFieldIndex()] <= player.getCarrots()) { //stage0WasAtHare == false
                            actions.add(new Advance(b.getNextFieldByType(FieldType.HARE, player.getFieldIndex()) - player.getFieldIndex()));
                            actions.add(new Card(CardType.EAT_SALAD, 1));

                            step0WasAtHare = true;
                            logMessage("step: 0, to hare", false);
                        } else {
                            actions.add(new Skip(1));
                            logMessage("step: 0, to hare skip", false);
                        }
                    }
                } else {
                    logMessage("step: 0, to salad", false);
                    actions.add(new Advance(b.getNextFieldByType(FieldType.SALAD, player.getFieldIndex()) - player.getFieldIndex()));
                    newTask = 1;
                    step = 1;
                }
            }
        } else if (step == 1) {
            if (player.getFieldIndex() > enemy.getFieldIndex()) {
                if (!enemyOnNextFieldType(FieldType.SALAD) && karottenVerbrauch[b.getNextFieldByType(FieldType.SALAD, player.getFieldIndex())] <= player.getCarrots()) {
                    actions.add(new Advance(b.getNextFieldByType(FieldType.SALAD, player.getFieldIndex()) - player.getFieldIndex()));  //go to second part
                    newTask = 1;
                    step = 2;
                    logMessage("step: 1, to salad", false);
                } else if (!enemyOnNextFieldType(FieldType.POSITION_1) && karottenVerbrauch[b.getNextFieldByType(FieldType.POSITION_1, player.getFieldIndex()) - player.getFieldIndex()] <= player.getCarrots()) {
                    actions.add(new Advance(b.getNextFieldByType(FieldType.POSITION_1, player.getFieldIndex()) - player.getFieldIndex()));
                    logMessage("step: 1, to pos1", false);
                } else if (!enemyOnNextFieldType(FieldType.HARE) && player.getCards().contains(CardType.EAT_SALAD) && karottenVerbrauch[b.getNextFieldByType(FieldType.HARE, player.getFieldIndex()) - player.getFieldIndex()] <= player.getCarrots()) {
                    actions.add(new Advance(b.getNextFieldByType(FieldType.HARE, player.getFieldIndex()) - player.getFieldIndex()));
                    actions.add(new Card(CardType.EAT_SALAD, 1));
                    logMessage("step: 1, to hare", false);
                } else if (!enemyOnNextFieldType(FieldType.CARROT) && karottenVerbrauch[b.getNextFieldByType(FieldType.CARROT, player.getFieldIndex()) - player.getFieldIndex()] <= player.getCarrots()) {
                    actions.add(new Advance(b.getNextFieldByType(FieldType.CARROT, player.getFieldIndex()) - player.getFieldIndex()));
                    newTask = 2;
                    logMessage("step: 1, to carrot", false);
                }
            } else {
                if (!enemyOnNextFieldType(FieldType.SALAD) && karottenVerbrauch[b.getNextFieldByType(FieldType.SALAD, player.getFieldIndex()) - player.getFieldIndex()] <= player.getCarrots()) {
                    actions.add(new Advance(b.getNextFieldByType(FieldType.SALAD, player.getFieldIndex()) - player.getFieldIndex())); //go to second part
                    newTask = 1;
                    step = 2;
                    logMessage("step: 1, to salad", false);
                } else if (b.getNextFieldByType(FieldType.CARROT, player.getFieldIndex()) < b.getNextFieldByType(FieldType.SALAD, player.getFieldIndex()) && !enemyOnNextFieldType(FieldType.CARROT) && karottenVerbrauch[b.getNextFieldByType(FieldType.CARROT, player.getFieldIndex()) - player.getFieldIndex()] <= player.getCarrots()) {
                    actions.add(new Advance(b.getNextFieldByType(FieldType.CARROT, player.getFieldIndex()) - player.getFieldIndex()));
                    newTask = 2;
                    logMessage("step: 1, to carrot", false);
                } else if (b.getPreviousFieldByType(FieldType.HEDGEHOG, player.getFieldIndex()) < b.getNextFieldByType(FieldType.SALAD, player.getFieldIndex()) && !enemyOnPreviousFieldType(FieldType.HEDGEHOG)) {
                    actions.add(new FallBack(0));
                    logMessage("step: 1, to hedgehog", false);
                } else {
                    actions.add(new Skip(1));
                    logMessage("step: 1, skip", false);
                }
            }
        } else if (step == 2) {
            if (!enemyOnNextFieldType(FieldType.POSITION_1) && karottenVerbrauch[b.getNextFieldByType(FieldType.POSITION_1, player.getFieldIndex()) - player.getFieldIndex()] <= player.getCarrots()) {
                actions.add(new Advance(b.getNextFieldByType(FieldType.POSITION_1, player.getFieldIndex()) - player.getFieldIndex()));
                logMessage("step: 2, to pos1", false);
                logMessage(" !!!!! Now playing: SecondPart", false);
            } else if (!enemyOnNextFieldType(FieldType.CARROT) && karottenVerbrauch[b.getNextFieldByType(FieldType.CARROT, player.getFieldIndex()) - player.getFieldIndex()] <= player.getCarrots()) {
                actions.add(new Advance(b.getNextFieldByType(FieldType.CARROT, player.getFieldIndex()) - player.getFieldIndex()));
                logMessage("step: 2, to carrot", false);
                logMessage(" !!!!! Now playing: SecondPart", false);
            } else if (!enemyOnNextFieldType(FieldType.HEDGEHOG) && karottenVerbrauch[b.getNextFieldByType(FieldType.HEDGEHOG, player.getFieldIndex()) - player.getFieldIndex()] <= player.getCarrots()) {
                actions.add(new Advance(b.getNextFieldByType(FieldType.HEDGEHOG, player.getFieldIndex()) - player.getFieldIndex()));
                logMessage("step: 2, to headgehog", false);
                logMessage(" !!!!! Now playing: SecondPart", false);
            } else {
                actions.add(new Skip(1));
                logMessage("step: 2, skip", false);
            }
        }

        m = new Move(actions);
        m.orderActions();
        actions.clear();
    }
}
