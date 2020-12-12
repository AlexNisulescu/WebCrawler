
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import  java.util.ArrayList;

public class Parser
{
    private String title;
    private String body;
    final private String htmlPage;
    private ArrayList<String> urlList;

    public Parser( String htmlContent ) {
        this.htmlPage = htmlContent;
    }

    public void parse(String htmlContent) throws BadArgumentsException {
        Parser parser = new Parser(htmlContent);
        this.urlList = getURLs(parser.htmlPage);
        this.title = getTitle(parser.htmlPage);
        this.body = getBody((parser.htmlPage));
    }

    public String getTitle(String htmlContent) throws BadArgumentsException {
        String result = null;
        try
        {
            Pattern pattern  = Pattern.compile("\\<title>(.*)\\</title>", Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
            Matcher matcher = pattern.matcher(htmlContent);
            while (matcher.find())
            {
                result = matcher.group(1);
            }
            return result;
        }
        catch(Exception e){
            System.out.println("An error occurred.");
            e.printStackTrace();
            throw new BadArgumentsException("Bad argument for extract title");
        }
    }

    public String getBody( String htmlContent ) throws BadArgumentsException {
        String result = null;
        try
        {
            Pattern pattern  = Pattern.compile(".*?<body.*?>(.*?)</body>.*?", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(htmlContent);
            while (matcher.find())
            {
                result = matcher.group(1);
            }
            return result;
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
            throw new BadArgumentsException("Bad argument for extract body");
        }
    }

    public ArrayList<String> getURLs(String htmlContent) throws BadArgumentsException {
        try
        {
            String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>]*)\\s*>";
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(htmlContent);
            ArrayList<String> result = new ArrayList<String>();

            while (matcher.find())
            {
                int start = matcher.start();
                int end = matcher.end();
                String url = htmlContent.substring(start, end);
                result.add(url);
            }

            return result;
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
            throw new BadArgumentsException("Bad argument for extract URLs");
        }
    }
}
