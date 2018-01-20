package pl.edu.agh.student;

import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.util.Random;

public class Cluster {
    public static void main(String[] args) {

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setLocalHost("127.0.0.1");
        cfg.setIgniteInstanceName(""+new Random().nextInt());

        Ignition.start(cfg);

    }
}
