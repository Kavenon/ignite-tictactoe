package pl.edu.agh.student.model;

public class GameState {

    // 0 - nothing
    // 1 - X
    // 2 - O
    private int[] state;
    private int size;
    private int nextMove;

    public GameState(int[] state, int size, int nextMove) {
        this.state = state;
        this.size = size;
        this.nextMove = nextMove;
    }

    public int[] getState() {
        return state;
    }

    public void setState(int[] state) {
        this.state = state;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNextMove() {
        return nextMove;
    }

    public void setNextMove(int nextMove) {
        this.nextMove = nextMove;
    }
}
