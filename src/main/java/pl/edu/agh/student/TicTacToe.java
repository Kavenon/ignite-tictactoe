package pl.edu.agh.student;

import java.util.Collections;
import java.util.List;

public class TicTacToe {

    private GameState state;

    public TicTacToe(GameState state) {
        this.state = state;
    }

    public List<GameState> getNextStates(){
        // Todo: implement
        return Collections.emptyList();
    }


    public Double getProbability(int player){
        return 0.0;
    }
}
