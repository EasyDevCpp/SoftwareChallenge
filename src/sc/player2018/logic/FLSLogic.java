package sc.player2018.logic;

import sc.player2018.logic.Parts.FirstPart;
import sc.player2018.logic.Parts.Part;
import sc.player2018.logic.Parts.SecondPart;
import sc.player2018.logic.Parts.ThirdPart;
import sc.plugin2018.GameState;
import sc.plugin2018.Move;

public class FLSLogic {
    private Part first;
    private Part second;
    private Part third;
    private GameState gameState;
    private Move m;
    
    public FLSLogic(){
        first = new FirstPart();
        second = new SecondPart();
        third = new ThirdPart();
    }
    
    public void update(GameState gs){
        first.update(gs);
        second.update(gs);
        third.update(gs);
        gameState = gs;
    }
    
    public void play(){
        if(gameState.getCurrentPlayer().getFieldIndex()<23) {
            first.play();
            m = first.getMove();
        } else if(gameState.getCurrentPlayer().getFieldIndex()<43) {
            second.play();
            m = second.getMove();
        } else if(gameState.getCurrentPlayer().getFieldIndex()<=65) {
            third.play();
            m = third.getMove();
        }
    }

    public Move getMove() {
        return m;
    }
    public void setMove(Move m) {
        this.m = m;
    }
}
