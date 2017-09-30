package sc.player2018.logic.Parts;

import sc.plugin2018.GameState;
import sc.plugin2018.Player;

public class FirstPart {
    GameState gs;
    Player p;
    Player enemy;

    public FirstPart(GameState gs, Player p, Player enemy){
        this.gs = gs;
        this.p = p;
        this.enemy = enemy;
    }
}
