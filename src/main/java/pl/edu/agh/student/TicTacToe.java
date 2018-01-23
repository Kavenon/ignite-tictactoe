package pl.edu.agh.student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToe {

	private GameState state;
	
	static int winX_Counter = 0;
	static int winY_Counter = 0;

	public TicTacToe(GameState state) {
		this.state = state;
	}

	public List<GameState> getNextStates(){
    	
    	List<GameState> gameStates = new ArrayList<GameState>();
    	
    	for (int i = 0; i < state.getSize(); i++) {
			int[] tempData = Arrays.copyOf(state.getState(), state.getSize());
			if (tempData[i] == 0) {
				tempData[i] = state.getNextMove();
				gameStates.add(new GameState(tempData, state.getSize(), getNextPlayer(state.getNextMove())));
			}
		}

        return gameStates;
    }

	private void countProbability() {
		Node root = new Node(state.getState());
		generateTree(root, state.getNextMove());
	}
	
	private static void generateTree(Node<int[]> parent, int nextPlayer) {

		Node<int[]> childNode;

		for (int i = 0; i < parent.data.length; i++) {
			int[] tempData = Arrays.copyOf(parent.data, parent.data.length);
			if (tempData[i] == 0) {
				tempData[i] = nextPlayer;
				
				int winner = isGameOver(tempData);
				if(winner != 0){
					if (winner == 1)
						winX_Counter ++;
					else
						winY_Counter ++;
				} else {
					childNode = new Node(tempData);
					parent.addChild(childNode);
					generateTree(childNode, getNextPlayer(nextPlayer));
				}
			}
		}

	}
	
	private static int getNextPlayer(int currentPlayer) {
		return currentPlayer == 1 ? 2 : 1;
	}

	public Double getProbability(int player) {
		countProbability();
		
		if (player == 1)
			return (double) (winX_Counter / (winX_Counter+winY_Counter));
		else if (player == 2)
			return (double) (winY_Counter / (winX_Counter+winY_Counter));
		else 
			return 0.0;
	}
	
	public static int isGameOver(int[] vector) {

		int matrixSize = (int) Math.sqrt(vector.length);
		int[][] gameState = new int[matrixSize][matrixSize];

		for (int i = 0; i < matrixSize; i++) {
			System.arraycopy(vector, i * matrixSize, gameState[i], 0, matrixSize);
		}

		int mapSize = gameState.length;

		for (int i = 0; i < mapSize; i++) {
			int firstPositionInLineState = gameState[i][0];

			if (firstPositionInLineState == 0)
				continue;

			for (int j = 1; j < mapSize; j++) {
				if (gameState[i][j] != firstPositionInLineState)
					break;

				if (j == mapSize - 1 && gameState[i][j] == firstPositionInLineState)
					return firstPositionInLineState;
			}
		}

		for (int i = 0; i < mapSize; i++) {
			int firstPositionInLineState = gameState[0][i];

			if (firstPositionInLineState == 0)
				continue;

			for (int j = 1; j < mapSize; j++) {
				if (gameState[j][i] != firstPositionInLineState)
					break;

				if (j == mapSize - 1 && gameState[j][i] == firstPositionInLineState)
					return firstPositionInLineState;
			}
		}

		int firstPositionInLineState = gameState[0][0];

		if (firstPositionInLineState != 0) {
			for (int j = 1; j < mapSize; j++) {
				if (gameState[j][j] != firstPositionInLineState)
					break;

				if (j == mapSize - 1 && gameState[j][j] == firstPositionInLineState)
					return firstPositionInLineState;
			}
		}

		firstPositionInLineState = gameState[0][mapSize - 1];

		if (firstPositionInLineState != 0) {
			for (int j = 1; j < mapSize; j++) {
				if (gameState[j][mapSize - j - 1] != firstPositionInLineState)
					break;

				if (j == mapSize - 1 && gameState[j][mapSize - j - 1] == firstPositionInLineState)
					return firstPositionInLineState;
			}
		}

		return 0;
	}
}
