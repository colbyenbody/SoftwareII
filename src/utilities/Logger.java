package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger class is used to catalog login information from the login attempts
 */
public class Logger {

    /**
     * Logger method is used to create the login activity file and append username and date to file
     * @throws IOException if there is an IO error
     * @param userName username
     * @param successBool successful login
     */
    public static void auditLogin(String userName, Boolean successBool) throws IOException {
        String loginPath = "login_activity.txt";
        try {
            BufferedWriter logger = new BufferedWriter(new FileWriter(loginPath, true));
            logger.append(ZonedDateTime.now(ZoneOffset.UTC).toString() + " UTC-LOGIN ATTEMPT-USERNAME: " + userName +
                    " LOGIN SUCCESSFUL: " + successBool.toString() + "\n");
            logger.flush();
            logger.close();
        }
        catch (IOException error) {
            error.printStackTrace();
        }
    }
}