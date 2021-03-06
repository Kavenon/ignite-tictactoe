package pl.edu.agh.student;

import org.apache.ignite.compute.*;
import org.apache.ignite.resources.TaskSessionResource;
import org.jetbrains.annotations.Nullable;
import pl.edu.agh.student.model.GameState;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TicTacToeTask extends ComputeTaskSplitAdapter<GameState, Double> {

    private final static int PLAYER_TO_CHECK = 1;

    @Override
    protected Collection<? extends ComputeJob> split(int clusterSize, GameState arg) {
        Collection<ComputeJob> jobs = new LinkedList<>();

        TicTacToe ticTacToe = new TicTacToe(arg);

        List<GameState> nextStates = ticTacToe.getNextStates();

        for (final GameState nextState : nextStates) {
            jobs.add(new ComputeJobAdapter() {
                @TaskSessionResource
                private ComputeTaskSession ses;

                @Nullable
                @Override
                public Object execute() {

                    TicTacToe ticTacToeInsideTask = new TicTacToe(nextState);
                    Double probability = ticTacToeInsideTask.getProbability(PLAYER_TO_CHECK);

                    System.out.println();
                    System.out.println(">>> Printing on this node from ignite job." + ses.getTaskNodeId());
                    System.out.println(">>> RESULT: " + probability);

                    return probability;
                }
            });
        }

        return jobs;
    }

    @Nullable
    @Override
    public Double reduce(List<ComputeJobResult> results) {
        double sum = 0;
        for (ComputeJobResult res : results)
            sum += res.<Double>getData();
        return sum / results.size();
    }
}