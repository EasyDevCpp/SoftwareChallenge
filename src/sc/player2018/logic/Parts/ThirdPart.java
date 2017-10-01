package sc.player2018.logic.Parts;

import sc.plugin2018.GameState;
import sc.plugin2018.Move;
import sc.plugin2018.Player;

public class ThirdPart {
    private int[] karottenVerbrauch = {1,3,6,10,15,21,28,36,45,55,66,78,91,105,120,136,153,171,190,210,231,253,276,300,325,351,378,406,435,465,496,528,561,595,630,666,703,741,780,820,861,903,946,990};

    private GameState gs;
    private Player p;
    private Player enemy;
    private int moveId;

    public ThirdPart(GameState gs, Player p, Player enemy){
        this.gs = gs;
        this.p = p;
        this.enemy = enemy;
        this.moveId = 0;
    }

    public void processAI(){
        System.out.println("Working on third part...");
    }

    public Move getMove(){
        return gs.getPossibleMoves().get(moveId);
    }
}
