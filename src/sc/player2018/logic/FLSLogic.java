package sc.player2018.logic;
import sc.player2018.logic.Parts.FirstPart;
import sc.player2018.logic.Parts.Part;
import sc.player2018.logic.Parts.SecondPart;
import sc.player2018.logic.Parts.ThirdPart;
import sc.plugin2018.GameState;
import sc.plugin2018.Move;

public class FLSLogic {
    private Part[] parts;
    private Log logPlayer;
    private Log logEnemy;
    private GameState gameState;
    private Move m;
    private boolean aiMove = false;

    public FLSLogic(){
        parts = new Part[3];
        parts[0] = new FirstPart();
        parts[1] = new SecondPart();
        parts[2] = new ThirdPart();
        logPlayer = new Log("log");
        logEnemy = new Log("log_enemy");
    }

    public void update(GameState gs){
        gameState = gs;
        parts[0].update(gs);
        parts[1].update(gs);
        parts[2].update(gs);

        if(gs.getCurrentPlayer().getFieldIndex() == 0 && gs.getOtherPlayer().getFieldIndex() == 0){
            logPlayer.printFields(gs.getBoard());
        } else if(gs.getCurrentPlayer().getFieldIndex() == 64 || gs.getOtherPlayer().getFieldIndex() == 64){
            logPlayer.onGameEnd();
        } /* else{
            log.logEnemy(gameState);
        }*/
    }
    
    public void play(){
        try{
            logEnemy.logEnemy(gameState);
        }catch (Exception e){
            System.out.println("NullPointer beim ersten Zug, allerdings unwichtig.");
        }

        if(gameState.getCurrentPlayer().getFieldIndex()<23) {
            aiMove = parts[0].play();
            m = parts[0].getMove();
            logPlayer.setLastPlayedPart(parts[0]);
        } else if(gameState.getCurrentPlayer().getFieldIndex()<43) {
            aiMove = parts[1].play();
            m = parts[1].getMove();
            logPlayer.setLastPlayedPart(parts[1]);
        } else if(gameState.getCurrentPlayer().getFieldIndex()<=65) {
            aiMove = parts[2].play();
            m = parts[2].getMove();
            logPlayer.setLastPlayedPart(parts[2]);
        }
        logPlayer.logMove(gameState, m, aiMove);
    }

    public void onError(){
        logPlayer.write("\n\nERROR:");
        logPlayer.logMove(gameState, m, aiMove);
    }

    public Move getMove() {
        return m;
    }
    public void setMove(Move m) {
        this.m = m;
    }
}
