package sc.player2018.logic.Parts;
import sc.player2018.logic.Parts.extendedAI.ExtendedAI;
import sc.plugin2018.*;
import java.util.ArrayList;

public class SecondPart extends Part{
    private int distances[]=new int[6];
    private boolean enemy_fields[]=new boolean[6];

    public SecondPart() {
        //set Quality here
    }

    @Override
    public void processAI() {
        if(super.getPlayer().getSalads() > 1) {
            if(super.getPlayer().getCarrots() - super.getKarrotCosts()[super.getGameState().getBoard().getNextFieldByType(FieldType.SALAD, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] > 0) {
                if(super.getEnemy().getFieldIndex() != super.getGameState().getBoard().getNextFieldByType(FieldType.SALAD, super.getPlayer().getFieldIndex())) {
                    super.getActions().add(new Advance(super.getDistance(FieldType.SALAD), 0));
                } else if(0 <super.getPlayer().getCarrots() - super.getKarrotCosts()[super.getGameState().getBoard().getNextFieldByType(FieldType.SALAD, super.getGameState().getBoard().getNextFieldByType(FieldType.SALAD, super.getPlayer().getFieldIndex())+1) - super.getPlayer().getFieldIndex()]) {
                    super.getActions().add(new Advance((super.getGameState().getBoard().getNextFieldByType(FieldType.SALAD, super.getGameState().getBoard().getNextFieldByType(FieldType.SALAD, super.getPlayer().getFieldIndex())+1) - super.getPlayer().getFieldIndex()), 0));
                }
                super.setNewTask(1);
            } else if(super.getPlayer().ownsCardOfType(CardType.EAT_SALAD) && isMovePlayable(1, FieldType.HARE)) {
                super.getActions().add(new Advance(super.getDistance(FieldType.HARE), 0));
                super.getActions().add(new Card(CardType.EAT_SALAD,1));
            } else if(super.getPlayer().ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS) && isMovePlayable(1, FieldType.HARE)) {
                super.getActions().add(new Advance(super.getDistance(FieldType.HARE), 0));
                if(super.getPlayer().getCarrots() > 20) {
                    super.getActions().add(new Card(CardType.TAKE_OR_DROP_CARROTS, -20 ,1));
                } else {
                    super.getActions().add(new Card(CardType.TAKE_OR_DROP_CARROTS, 20 ,1));
                }
            } else if(isMovePlayable(1, FieldType.CARROT)) {
                super.getActions().add(new Advance(super.getDistance(FieldType.CARROT)));
                super.setNewTask(2);
            } else {
                super.setActions(new ArrayList<Action>(ExtendedAI.getRandomMove(super.getGameState()).getActions()));
            }
        } else {
            if(super.getPlayer().getCarrots() - super.getKarrotCosts()[super.getGameState().getBoard().getNextFieldByType(FieldType.GOAL, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] >= 0 && super.getPlayer().getCarrots() - super.getKarrotCosts()[super.getGameState().getBoard().getNextFieldByType(FieldType.GOAL, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= 10) {
                super.getActions().add(new Advance(super.getDistance(FieldType.GOAL), 0));
            } else if(super.getPlayer().getCarrots() >= super.getKarrotCosts()[44-super.getPlayer().getFieldIndex()]) {
                super.getActions().add(new Advance(44-super.getPlayer().getFieldIndex()));
            } else {
                super.setActions(new ArrayList<Action>(ExtendedAI.getRandomMove(super.getGameState()).getActions()));
            }
        }
    }
}
