package sc.player2018.logic;
import sc.player2018.logic.Parts.FirstPart;
import sc.player2018.logic.Parts.Part;
import sc.player2018.logic.Parts.SecondPart;
import sc.player2018.logic.Parts.ThirdPart;
import sc.plugin2018.GameState;
import sc.plugin2018.Move;

public class FLSLogic {
    private Part[] parts;
    private Log log;
    private GameState gameState;
    private Move m;
    private boolean hasPlayed;
    private boolean aiMove = false;

    public FLSLogic(){
        parts = new Part[3];
        parts[0] = new FirstPart();
        parts[1] = new SecondPart();
        parts[2] = new ThirdPart();
        log = new Log();
    }

    public void update(GameState gs){
        gameState = gs;
        parts[0].update(gs);
        parts[1].update(gs);
        parts[2].update(gs);

        if(gs.getCurrentPlayer().getFieldIndex() == 0 && gs.getOtherPlayer().getFieldIndex() == 0){
            log.printFields(gs.getBoard());
        } else if(gs.getCurrentPlayer().getFieldIndex() == 64 || gs.getOtherPlayer().getFieldIndex() == 64){
            log.onGameEnd();
        } /* else{
            log.logEnemy(gameState);
        }*/
    }
    
    public void play(){
        if(gameState.getCurrentPlayer().getFieldIndex()<23) {
            aiMove = parts[0].play();
            m = parts[0].getMove();
            log.setLastPlayedPart(parts[0]);
        } else if(gameState.getCurrentPlayer().getFieldIndex()<43) {
            aiMove = parts[1].play();
            m = parts[1].getMove();
            log.setLastPlayedPart(parts[1]);
        } else if(gameState.getCurrentPlayer().getFieldIndex()<=65) {
            aiMove = parts[2].play();
            m = parts[2].getMove();
            log.setLastPlayedPart(parts[2]);
        }
        log.logMove(gameState, m, aiMove);
    }

    public Move getMove() {
        return m;
    }
    public void setMove(Move m) {
        this.m = m;
    }
}
