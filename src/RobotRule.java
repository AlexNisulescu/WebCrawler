/**
 *
 * This class is a helper for the Crawler class
 * All it does is override the toString() method
 * @author Alexandru Nisulescu
 */
public class RobotRule
{
    public String userAgent;
    public String rule;

    /**
     * Empty constructor
     */
    RobotRule() {

    }

    /**
     *
     * @return the new formatted rule
     */
    @Override public String toString()
    {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append("   userAgent: " + this.userAgent + NEW_LINE);
        result.append("   rule: " + this.rule + NEW_LINE);
        result.append("}");
        return result.toString();
    }
}