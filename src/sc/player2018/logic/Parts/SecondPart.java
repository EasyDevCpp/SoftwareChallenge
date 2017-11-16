package sc.player2018.logic.Parts;

import sc.player2018.logic.FLSLogic;
import sc.plugin2018.*;

public class SecondPart extends FLSLogic {
    private int distances[]=new int[6];
    private boolean enemy_fields[]=new boolean[6];

    public void processAI() {
        Board b=gs.getBoard();
        distances[0]=b.getNextFieldByType(FieldType.HEDGEHOG,player.getFieldIndex())-player.getFieldIndex();
        distances[1]=b.getNextFieldByType(FieldType.HARE,player.getFieldIndex())-player.getFieldIndex();
        distances[2]=b.getNextFieldByType(FieldType.POSITION_1,player.getFieldIndex())-player.getFieldIndex();
        distances[3]=b.getNextFieldByType(FieldType.POSITION_2,player.getFieldIndex())-player.getFieldIndex();
        distances[4]=b.getNextFieldByType(FieldType.SALAD,player.getFieldIndex())-player.getFieldIndex();
        distances[5]=b.getNextFieldByType(FieldType.CARROT,player.getFieldIndex())-player.getFieldIndex();
        enemy_fields[0]=enemyOnNextFieldType(FieldType.HEDGEHOG);
        enemy_fields[1]=enemyOnNextFieldType(FieldType.HARE);
        enemy_fields[2]=enemyOnNextFieldType(FieldType.POSITION_1);
        enemy_fields[3]=enemyOnNextFieldType(FieldType.POSITION_2);
        enemy_fields[4]=enemyOnNextFieldType(FieldType.SALAD);
        enemy_fields[5]=enemyOnNextFieldType(FieldType.CARROT);
        actions.clear();
        logMessage("second part ("+player.getPlayerColor().name()+"): ",true);

        if (player.getCarrots() > 10) {
            if (!enemy_fields[4] && karottenVerbrauch[distances[4]] <= player.getCarrots()) {
                logMessage("goto salad", false);
                actions.add(new Advance(distances[4]));
                newTask = 1;
            } else if ((!enemy_fields[2] && karottenVerbrauch[distances[2]] <= player.getCarrots()) && (!enemy_fields[3] && karottenVerbrauch[distances[3]] <= player.getCarrots())) {
                logMessage("goto position 1 or 2", false);
                if (player.getFieldIndex() > enemy.getFieldIndex()) actions.add(new Advance(distances[2]));
                else if (player.getFieldIndex() < enemy.getFieldIndex()) actions.add(new Advance(distances[3]));
            } else if (!enemy_fields[5] && karottenVerbrauch[distances[5]] <= player.getCarrots() && !(player.getLastNonSkipAction() instanceof ExchangeCarrots)) {
                logMessage("goto carrots " + karottenVerbrauch[distances[5]] + " " + player.getCarrots(), false);
                actions.add(new Advance(distances[5]));
                if (player.getCarrots() < 90) newTask = 2;
                else newTask = 3;
            } else if (!enemy_fields[1] && karottenVerbrauch[distances[1]] <= player.getCarrots() && (player.ownsCardOfType(CardType.EAT_SALAD) || player.ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS) || player.ownsCardOfType(CardType.HURRY_AHEAD))) {
                logMessage("playing card", false);
                actions.add(new Advance(distances[1], 0));
                if (player.ownsCardOfType(CardType.EAT_SALAD)) actions.add(new Card(CardType.EAT_SALAD, 1));
                else if (player.getCarrots() < 50 && player.ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS))
                    actions.add(new Card(CardType.TAKE_OR_DROP_CARROTS, 20, 1));
                else if (player.getCarrots() > 50 && player.ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS))
                    actions.add(new Card(CardType.TAKE_OR_DROP_CARROTS, -20, 1));
                else if (enemy_fields[0] && player.ownsCardOfType(CardType.HURRY_AHEAD))
                    actions.add(new Card(CardType.HURRY_AHEAD, 1));
            } else {
                logMessage("skip", false);
                actions.add(new Skip(1));
            }
        } else {
            if (b.getPreviousFieldByType(FieldType.CARROT, player.getFieldIndex()) != enemy.getFieldIndex()) {
                logMessage("fallback", false);
                actions.add(new FallBack(0));
            } else {
                logMessage("skip", false);
                actions.add(new Skip(1));
            }
        }

        m = new Move(actions);
        m.orderActions();
        actions.clear();
    }

}