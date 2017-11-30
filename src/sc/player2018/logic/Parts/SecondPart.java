package sc.player2018.logic.Parts;
import sc.player2018.logic.Parts.extendedAI.Sigmoid;
import sc.plugin2018.*;

public class SecondPart extends Part{
    private int distances[]=new int[6];
    private boolean enemy_fields[]=new boolean[6];

    @Override
    public void processAI() {
        if(Sigmoid.getMostEfficientAction(super.getGameState())!=null) {
            super.getActions().add(Sigmoid.getMostEfficientAction(super.getGameState()));
        } else {
            Board b=super.getGameState().getBoard();
            distances[0]=b.getNextFieldByType(FieldType.HEDGEHOG,super.getPlayer().getFieldIndex())-super.getPlayer().getFieldIndex();
            distances[1]=b.getNextFieldByType(FieldType.HARE,super.getPlayer().getFieldIndex())-super.getPlayer().getFieldIndex();
            distances[2]=b.getNextFieldByType(FieldType.POSITION_1,super.getPlayer().getFieldIndex())-super.getPlayer().getFieldIndex();
            distances[3]=b.getNextFieldByType(FieldType.POSITION_2,super.getPlayer().getFieldIndex())-super.getPlayer().getFieldIndex();
            distances[4]=b.getNextFieldByType(FieldType.SALAD,super.getPlayer().getFieldIndex())-super.getPlayer().getFieldIndex();
            distances[5]=b.getNextFieldByType(FieldType.CARROT,super.getPlayer().getFieldIndex())-super.getPlayer().getFieldIndex();
            enemy_fields[0]=enemyOnNextFieldType(FieldType.HEDGEHOG);
            enemy_fields[1]=enemyOnNextFieldType(FieldType.HARE);
            enemy_fields[2]=enemyOnNextFieldType(FieldType.POSITION_1);
            enemy_fields[3]=enemyOnNextFieldType(FieldType.POSITION_2);
            enemy_fields[4]=enemyOnNextFieldType(FieldType.SALAD);
            enemy_fields[5]=enemyOnNextFieldType(FieldType.CARROT);

            if(!enemy_fields[4]&&super.getKarrotCosts()[distances[4]]<=super.getPlayer().getCarrots()) {
                super.getActions().add(new Advance(distances[4]));
                super.setNewTask(1);
            } else if((!enemy_fields[2]&&super.getKarrotCosts()[distances[2]]<=super.getPlayer().getCarrots())&&(!enemy_fields[3]&&super.getKarrotCosts()[distances[3]]<=super.getPlayer().getCarrots())) {
                if(super.getPlayer().getFieldIndex()>super.getEnemy().getFieldIndex()) super.getActions().add(new Advance(distances[2]));
                else if(super.getPlayer().getFieldIndex()<super.getEnemy().getFieldIndex()) super.getActions().add(new Advance(distances[3]));
            } else if(!enemy_fields[5]&&super.getKarrotCosts()[distances[5]]<=super.getPlayer().getCarrots()&&!(super.getPlayer().getLastNonSkipAction() instanceof ExchangeCarrots)) {
                super.getActions().add(new Advance(distances[5]));
                if(super.getPlayer().getCarrots()<90) super.setNewTask(2);
                else super.setNewTask(3);
            } else if(!enemy_fields[1]&&super.getKarrotCosts()[distances[1]]<=super.getPlayer().getCarrots()&&(super.getPlayer().ownsCardOfType(CardType.EAT_SALAD)||super.getPlayer().ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS)||super.getPlayer().ownsCardOfType(CardType.HURRY_AHEAD))) {
                super.getActions().add(new Advance(distances[1], 0));
                if(super.getPlayer().ownsCardOfType(CardType.EAT_SALAD)) super.getActions().add(new Card(CardType.EAT_SALAD,1));
                else if(super.getPlayer().getCarrots()<50&&super.getPlayer().ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS)) super.getActions().add(new Card(CardType.TAKE_OR_DROP_CARROTS, 20,1));
                else if(super.getPlayer().getCarrots()>50&&super.getPlayer().ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS)) super.getActions().add(new Card(CardType.TAKE_OR_DROP_CARROTS, -20,1));
                else if(enemy_fields[0]&&super.getPlayer().ownsCardOfType(CardType.HURRY_AHEAD)) super.getActions().add(new Card(CardType.HURRY_AHEAD,1));
            } else {
                super.getActions().add(new Skip(1));
            }
        }
    }
}
