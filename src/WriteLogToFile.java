import java.io.IOException;
import  java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;



public class WriteLogToFile
{
        final private String message;
        public WriteLogToFile(String messageLog)
            {
            this.message = messageLog;
            }
         public void writeError(String messageLog) throws IOException
         {
            WriteLogToFile writeLogToFile = new WriteLogToFile(messageLog);
            Logger logger = Logger.getLogger(writeLogToFile.message);
            FileHandler fileHandler = new FileHandler("app.log", true);
            logger.addHandler(fileHandler);

            if (logger.isLoggable(Level.INFO))
                {
                    logger.info("Information message");
                }
            if (logger.isLoggable(Level.WARNING))
                {
                    logger.info("Warning message");
                }
    }

}
