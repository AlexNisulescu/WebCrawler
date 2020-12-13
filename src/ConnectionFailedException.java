import java.io.IOException;

/**
 *
 * This class implements a custom exception that is used when the website
 * can't be reached
 * @author Alexandru Nisulescu
 */
public class ConnectionFailedException extends Exception {
    private String connError;

    /**
     * The constructor that handles the error when it is thrown
     * @param errorMessage is the error message
     */
    public ConnectionFailedException(String errorMessage) {
        super(errorMessage);
        this.connError=errorMessage;
    }

    /**
     * The method that is used to write the error to the log file
     */
    public void throwExc() {
        System.out.println(this.connError);
        WriteLogToFile logger=new WriteLogToFile(this.connError);
        try{
            logger.setupLogger();
            logger.error();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
