package controller.slashCommand;

import java.sql.SQLException;
import java.util.StringTokenizer;
import model.enumerations.UserStateType;
import controller.MainController;

public class SlashCommand {

    private MainController controller;

    public SlashCommand(MainController controller) {
        this.controller = controller;
    }

    public boolean isSlashCommand(String str) {

        if (str.length() <= 0 || str.charAt(0) != '/') {
            return false;
        }

        str = str.substring(1);
        StringTokenizer tokenizer = new StringTokenizer(str);
        String token = "";
        boolean hasSetPresence = false;
        int numberOfTokens = 0;

        while (tokenizer.hasMoreTokens() && numberOfTokens <= 4) {
            token += tokenizer.nextToken();
            numberOfTokens++;

            if (numberOfTokens == 1) {
                // one token
                if (token.compareToIgnoreCase(UserStateType.ONLINE.toString()) == 0
                        || // online
                        token.compareToIgnoreCase("available") == 0 || // online
                        token
                                .compareToIgnoreCase(UserStateType.AWAY
                                        .toString()) == 0 || // away
                        token
                                .compareToIgnoreCase(UserStateType.BUSY
                                        .toString()) == 0 || // busy
                        token.compareToIgnoreCase(UserStateType.INVISIBLE
                                .toString()) == 0 || // invisible
                        token.compareToIgnoreCase(UserStateType.LUNCH
                                .toString()) == 0 || // lunch
                        token.compareToIgnoreCase(UserStateType.OFFLINE
                                .toString()) == 0 || // offline
                        token.compareToIgnoreCase("brb") == 0) { // brb (be
                                                                 // right back)
                    hasSetPresence = true;
                    break;
                } else {
                    token += " ";
                }
            } else if (numberOfTokens == 2) {
                // two tokens
                if (token.compareToIgnoreCase(UserStateType.NOT_AVAILABLE
                        .toString()) == 0) { // not available
                    hasSetPresence = true;
                    break;
                } else {
                    token += " ";
                }
            } else if (numberOfTokens == 3) {
                // three tokens
                if (token.compareToIgnoreCase(UserStateType.BRB.toString()) == 0
                        || // brb (be right back)
                        token.compareToIgnoreCase(UserStateType.PHONE
                                .toString()) == 0) { // on the phone
                    hasSetPresence = true;
                    break;
                } else {
                    token += " ";
                }
            } else if (numberOfTokens == 4) {
                // four tokens
                if (token.compareToIgnoreCase(UserStateType.NOT_BE_DISTURBED
                        .toString()) == 0) {
                    // not to be disturbed
                    hasSetPresence = true;
                    break;
                } else {
                    token += " ";
                }
            }
        }

        if (hasSetPresence) { // set up the status
            // eg. /away I am away
            // new presence: away
            // new status msg: I am away
            try {
                controller.setPresence(token);

                if (tokenizer.countTokens() > 0) {
                    // will be used to set the status message
                    controller.setStatus(str.substring(token.length() + 1),
                            false);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            controller.setStatus(str, false);
        }

        return true;
    }
}
