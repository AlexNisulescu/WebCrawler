import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import  java.util.logging.*;


public class WriteLogToFile
{
        final private String message;
        private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        public WriteLogToFile(String messageLog)
        {
            this.message = messageLog;
        }

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
                logger.log(Level.ALL, "Logger file not working.", e);
            }
        }
        public void info()
        {
            logger.info(this.message);
        }
        public void warning()
        {
            logger.warning(this.message);
        }
        public void error()
        {
            logger.severe(this.message);
        }

}
