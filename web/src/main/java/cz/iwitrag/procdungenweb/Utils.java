package cz.iwitrag.procdungenweb;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper class with static helper methods
 */
public class Utils {

    /**
     * Converts exception stack trace to String
     * @param ex Exception
     * @return StackTrace as raw multiline String
     */
    public static String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
