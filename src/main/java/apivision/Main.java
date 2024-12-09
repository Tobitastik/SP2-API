package apivision;

import apivision.config.ApplicationConfig;
import apivision.config.Populator;


public class Main {
    public static void main(String[] args) {
        Populator.run();
        ApplicationConfig.startServer(7070);
    }
}
