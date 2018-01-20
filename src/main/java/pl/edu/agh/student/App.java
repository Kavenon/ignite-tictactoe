package pl.edu.agh.student;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

public class App {

    public static void main( String[] args ){

        IgniteConfiguration clientConfig = new IgniteConfiguration();
        clientConfig.setLocalHost("127.0.0.1");
        clientConfig.setIgniteInstanceName("client");
        clientConfig.setClientMode(true);

        Ignite client = Ignition.start(clientConfig);

        IgniteCompute compute = client.compute();
        int cnt = compute.execute(SplitExampleCharacterCountTask.class, "Hello Grid Enabled World!");
        System.out.println("Result: " + cnt);

        client.close();

    }


}
