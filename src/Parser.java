
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import  java.util.ArrayList;

/**
 * This class parse the HTML pages and extracts the title, the body
 * and the URLs found in this pages.
 */
public class Parser
{
    private String htmlPath;
    /**
     * This method helps parser object to take the source from which information is extracted.
     * @param htmlPath path to HTML page
     * @throws BadArgumentsException
     */
    public void parse(String htmlPath)
    {
        this.htmlPath = htmlPath;
    }

    /**
     * This method gets content from HTML page from a given path
     * @return
     * @throws FileNotFoundException
     */
    public String getStringContent() throws FileNotFoundException {
        try {
            StringBuilder contentBuilder = new StringBuilder();
            BufferedReader in = new BufferedReader(new FileReader(this.htmlPath));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
            String htmlContent = contentBuilder.toString();
            return htmlContent;
        } catch(Exception e){
            System.out.println("An error occurred.");
            e.printStackTrace();
            throw new FileNotFoundException("The file does not exist");
        }
    }
    /**
     * The method gets the title from HTML content. It uses regex to match the title.
     * @return the title found
     */
    public String getTitle(){
        String result = null;
        try
        {
            Pattern pattern  = Pattern.compile("\\<title>(.*)\\</title>", Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
            Matcher matcher = pattern.matcher(getStringContent());
            while (matcher.find())
            {
                result = matcher.group(1);
            }

        }
        catch(Exception e){
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
        return result;
    }

    /**
     * The method gets the body from HTML content. It uses regex to match the body.
     * @return the body found
     */
    public String getBody(){
        String result = null;
        try
        {
            Pattern pattern  = Pattern.compile(".*?<body.*?>(.*?)</body>.*?", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(getStringContent());
            while (matcher.find())
            {
                result = matcher.group(1);
            }
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * The method gets the URLs from HTML content. It uses regex to match the URLs.
     * @return an array of URLs
     */
    public ArrayList<String> getURLs() {
        ArrayList<String> result = new ArrayList<String>();
        try {
            String htmlContent = getStringContent();
            String patternString = "/(http|https|ftp|ftps)\:\/\/[a-zA-Z0-9\-\.]+\.[a-zA-Z]{2,3}(\/\S*)?/";
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(htmlContent);

            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                String url = htmlContent.substring(start, end);
                result.add(url);
            }

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return result;
    }
}
