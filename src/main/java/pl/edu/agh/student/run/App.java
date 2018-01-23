package pl.edu.agh.student.run;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import pl.edu.agh.student.TicTacToeTask;
import pl.edu.agh.student.model.GameState;

public class App {

    public static void main( String[] args ){

        // ======= Init Ignite
        IgniteConfiguration clientConfig = new IgniteConfiguration();
        clientConfig.setLocalHost("127.0.0.1");
        clientConfig.setIgniteInstanceName("client");
        clientConfig.setClientMode(true);

        // ======= Configuration for remote node discovery
//        TcpDiscoverySpi spi = new TcpDiscoverySpi();
//        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
//        ipFinder.setAddresses(Collections.singletonList("108.48.19.91"));
//        spi.setIpFinder(ipFinder);
//        clientConfig.setDiscoverySpi(spi);

        // ======= Start client
        Ignite client = Ignition.start(clientConfig);
        IgniteCompute compute = client.compute();

        // ======= Initial state
        int gameSize = 3;
        int[] initialState = new int[gameSize*gameSize];
        GameState initialGameState = new GameState(initialState, initialState.length, 1);

        // ======= Run
        Double cnt = compute.execute(TicTacToeTask.class, initialGameState);

        System.out.println("Result: " + cnt);

        // ======= Close client
        client.close();

    }


}
