package sc.player2018.logic.Parts;

import sc.plugin2018.*;
import sc.player2018.logic.Parts.extendedAI.ExtendedAI;

import java.util.ArrayList;

public abstract class Part {
    private Move m;
    private ArrayList<Action> actions;
    private static int newTask;
    private int[] karrotCosts = {0,1,3,6,10,15,21,28,36,45,55,66,78,91,105,120,136,153,171,190,210,231,253,276,300,325,351,378,406,435,465,496,528,561,595,630,666,703,741,780,820,861,903,946,990};
    private GameState gs;
    private Player player;
    private Player enemy;
    private double quality;

    public Part(){
        actions = new ArrayList<Action>();
        quality = 0.65;
    }

    public void processAI(){}

    public void update(GameState gs){
        this.gs = gs;
        player = gs.getCurrentPlayer();
        enemy = gs.getOtherPlayer();
    }

    public boolean play(){
        boolean aiMove = false;

        actions.clear();

        if(newTask != 0 || gs.getBoard().getTypeAt(player.getFieldIndex()) == FieldType.SALAD) {
            if (newTask == 1 || gs.getBoard().getTypeAt(player.getFieldIndex()) == FieldType.SALAD) {
                actions.add(new EatSalad(0));
            } else if (newTask == 2){
                actions.add(new ExchangeCarrots(+10));
            } else if (newTask == 3){
                actions.add(new ExchangeCarrots(-10));
            }
            newTask = 0;
        } else{
            Move aimove;
            ExtendedAI ai=new ExtendedAI();
            aimove=ai.getMostEfficientMove(gs,3,0);
            if(aimove!=null) {
                actions=new ArrayList<Action>(aimove.getActions());
                aiMove=true;
            } else {
                processAI();
                //Super sick fallback if someone of us fucks up...
                //Not quite sure it's working or not...
                if(!ExtendedAI.isMovePossible(new Move(actions),gs)) {
                    aimove=ExtendedAI.getRandomMove(gs);
                    actions=new ArrayList<Action>(aimove.getActions());
                    aiMove=true;
                } else {
                    aiMove = false;
                }
            }
        }

        m = new Move(actions);
        m.orderActions();

        return aiMove;
    }

    @Deprecated
    public boolean enemyOnNextFieldType(FieldType type){
        return gs.getBoard().getNextFieldByType(type, player.getFieldIndex()) == enemy.getFieldIndex();
    }
    @Deprecated
    public boolean enemyOnPreviousFieldType(FieldType type){
        return gs.getBoard().getPreviousFieldByType(type, player.getFieldIndex()) == enemy.getFieldIndex();
    }
    public int getDistance(FieldType f){
        return gs.getNextFieldByType(f, player.getFieldIndex()) - player.getFieldIndex();
    }
    public boolean isMovePlayable(int direction, FieldType f){
        if(direction == 0 && gs.getBoard().getPreviousFieldByType(f, player.getFieldIndex()) > -1 && gs.getBoard().getPreviousFieldByType(f, player.getFieldIndex()) != enemy.getFieldIndex()) return true; //previousField
        else if(direction == 1 && gs.getBoard().getNextFieldByType(f, player.getFieldIndex()) > -1 && gs.getBoard().getNextFieldByType(f, player.getFieldIndex()) != enemy.getFieldIndex() && karrotCosts[gs.getBoard().getNextFieldByType(f, player.getFieldIndex()) - player.getFieldIndex()] <= player.getCarrots()) return true; //nextField
        return false;
    }

    public Move getMove() {
        return m;
    }
    public void setMove(Move m) {
        this.m = m;
    }
    public ArrayList<Action> getActions() {
        return actions;
    }
    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }
    public int getNewTask() {
        return newTask;
    }
    public void setNewTask(int newTask) {
        this.newTask = newTask;
    }
    public int[] getKarrotCosts() {
        return karrotCosts;
    }
    public void setKarrotCosts(int[] karrotCosts) {
        this.karrotCosts = karrotCosts;
    }
    public GameState getGameState() {
        return gs;
    }
    public void setGameState(GameState gs) {
        this.gs = gs;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public Player getEnemy() {
        return enemy;
    }
    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }
    public double getQuality() {return quality;}
    public void setQuality(double q) {quality=q;}
}
