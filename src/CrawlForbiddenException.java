import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * This class implements a custom exception that is used when the download
 * of the content is not allowed
 * @author Alexandru Nisulescu
 */
public class CrawlForbiddenException extends Exception{
    private String argsError;

    /**
     * The constructor that handles the error when it is thrown
     * @param errorMessage is the error message
     */
    public CrawlForbiddenException(String errorMessage) {
        super(errorMessage);
        this.argsError = errorMessage;
    }

    /**
     * The method that is used to write the error to the log file
     */
    public void throwExc() {
        System.out.println(this.argsError);
        WriteLogToFile logger=new WriteLogToFile();
        try{
            logger.setupLogger();
            logger.error(this.argsError);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
