package pl.edu.agh.student.run;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import pl.edu.agh.student.model.GameState;
import pl.edu.agh.student.TicTacToeTask;

public class App {

    public static void main( String[] args ){

        // ======= Init Ignite
        IgniteConfiguration clientConfig = new IgniteConfiguration();
        clientConfig.setLocalHost("127.0.0.1");
        clientConfig.setIgniteInstanceName("client");
        clientConfig.setClientMode(true);

        // ======= Start client
        Ignite client = Ignition.start(clientConfig);
        IgniteCompute compute = client.compute();

        // ======= Run task
        // Todo: generate random (or load from file) initial game state
        GameState initialGameState = new GameState(new int[]{}, 0, 1);
        Double cnt = compute.execute(TicTacToeTask.class, initialGameState);

        System.out.println("Result: " + cnt);

        // ======= Close client
        client.close();

    }


}
