package pl.edu.agh.student;

import org.apache.ignite.compute.*;
import org.apache.ignite.resources.JobContextResource;
import org.apache.ignite.resources.TaskSessionResource;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

class SplitExampleCharacterCountTask extends ComputeTaskSplitAdapter<String, Integer> {
    /**
     * Splits the received string to words, creates a child job for each word, and sends
     * these jobs to other nodes for processing. Each such job simply prints out the received word.
     *
     * @param clusterSize Number of available cluster nodes. Note that returned number of
     *                    jobs can be less, equal or greater than this cluster size.
     * @param arg         Task execution argument. Can be {@code null}.
     * @return The list of child jobs.
     */
    @Override
    protected Collection<? extends ComputeJob> split(int clusterSize, String arg) {
        Collection<ComputeJob> jobs = new LinkedList<>();

        for (final String word : arg.split(" ")) {
            jobs.add(new ComputeJobAdapter() {
                @TaskSessionResource
                private ComputeTaskSession ses;

                @JobContextResource
                private ComputeJobContext jobCtx;
                @Nullable
                @Override
                public Object execute() {

                    System.out.println();
                    System.out.println(">>> Printing '" + word + "' on this node from ignite job." + ses.getTaskNodeId());

                    // Return number of letters in the word.
                    return word.length();
                }
            });
        }

        return jobs;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public Integer reduce(List<ComputeJobResult> results) {
        int sum = 0;

        for (ComputeJobResult res : results)
            sum += res.<Integer>getData();

        return sum;
    }
}