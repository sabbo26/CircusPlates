package eg.edu.alexu.csd.oop.game67;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class logger implements Observer {
    public static final Logger MyLog = Logger.getLogger(logger.class.getName());
    FileHandler fileHandler = null;
    private String msg;
    private boolean isGameEnd = false;
    private File f = new File("logger.txt");
    private static logger instance;

    private logger() {
        try {
            f.createNewFile();
            fileHandler = new FileHandler("logger.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyLog.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        MyLog.setLevel(Level.INFO);
        // MyLog.setUseParentHandlers(false); //true when in console
    }

    public static logger getInstance() {
        if (instance == null) {
            instance = new logger();
        }
        return instance;
    }

    @Override
    public void update(Object arg, String GameEnd) {
        if (isGameEnd) return;
        if (GameEnd != null) {
            if (GameEnd.contains("lose")) {
                msg = "Game Over!";
                isGameEnd = true;
                MyLog.info(msg);
            } else {
                msg = "The bomb is almost here!!";
                MyLog.warning(msg);
            }

        } else {
            if (arg instanceof String) {
                msg = "An item is catched!";
            } else {
                msg = "The score increased! | " + "Score = " + arg;
            }
            MyLog.info(msg);
        }

    }
}
