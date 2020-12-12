import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import  java.util.logging.*;

/**
 * This class is intended to store messages (errors, information, warnings) in external file.
 */
public class WriteLogToFile
{

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * This method configures the logger with the following: the file in which the messages will be written,
     * the way they are written, the log level.
     * @throws IOException
     */
    public void setupLogger() throws IOException
    {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);

        try {
            FileHandler fileHandler = new FileHandler("app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
        }catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
            throw new FileNotFoundException("The file can't be found for logger");
        }
    }

    /**
     * This method allows to write information messages in file.
     * @param message the message to write to file
     */
    public void info(String message) { logger.info(message); }

    /**
     * This method allows to write warning messages in file.
     * @param message the message to write to file
     */
    public void warning(String message) { logger.warning(message); }

    /**
     * This method allows to write error messages in file.
     * @param message the message to write to file
     */
    public void error(String message) { logger.severe(message); }

}
