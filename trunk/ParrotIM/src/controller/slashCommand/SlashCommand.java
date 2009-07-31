package controller.slashCommand;

import java.sql.SQLException;
import java.util.StringTokenizer;

import model.Model;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;
import controller.MainController;
import controller.services.BadConnectionException;

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

        //System.out.println("hasSetPresence: " + hasSetPresence);

        if (hasSetPresence) { // set up the status
            // eg. /away I am away
            // new presence: away
            // new status msg: I am away
            try {
                controller.setPresence(token);
                //System.out.println("Status: " + token);

                if (tokenizer.countTokens() > 0) {
                    // will be used to set the status message
//                    System.out.println("status msg: "
//                            + str.substring(token.length() + 1));
                    controller.setStatus(str.substring(token.length() + 1),
                            false);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //System.out.println("status msg: " + str);
            controller.setStatus(str, false);
        }

        return true;
    }

    public static void main(String[] args) {
        MainController c = new MainController(new Model());
        try {
            c.loginAsGuest(ServerType.GOOGLE_TALK, "cmpt275testing@gmail.com",
                    "abcdefghi");

            SlashCommand cmd = new SlashCommand(c);

            // non slash command
            //System.out.println(cmd.isSlashCommand("non slash command"));
   

            // status message only
           // System.out.println(cmd
            //        .isSlashCommand("/I am changing my status message"));

            // presence only
            // one token
            //System.out.println(cmd.isSlashCommand("/online"));
  

           // System.out.println(cmd.isSlashCommand("/available"));
      

           // System.out.println(cmd.isSlashCommand("/away"));
          

//            System.out.println(cmd.isSlashCommand("/brb"));
//            System.out.println();
//
//            System.out.println(cmd.isSlashCommand("/busy"));
//            System.out.println();
//
//            System.out.println(cmd.isSlashCommand("/invisible"));
//            System.out.println();
//
//            System.out.println(cmd.isSlashCommand("/lunch"));
//            System.out.println();
//
//            System.out.println(cmd.isSlashCommand("/offline"));
//            System.out.println();
//
//            // two tokens
//            System.out.println(cmd.isSlashCommand("/not available"));
//            System.out.println();

            // three tokens
//            System.out.println(cmd.isSlashCommand("/be right back"));
//            System.out.println();
//
//            System.out.println(cmd.isSlashCommand("/on the phone"));
//            System.out.println();
//
//            // four tokens
//            System.out.println(cmd.isSlashCommand("/not to be disturbed"));
//            System.out.println();
//
//            // status + presence
//            System.out.println(cmd
//                    .isSlashCommand("/not available I am so awesome"));
//            System.out.println();
//
//            System.out.println(cmd.isSlashCommand("/brb another brb"));
//            System.out.println();
//
//            System.out.println(cmd
//                    .isSlashCommand("/be right back this is supposed to work"));
//            System.out.println();
//
//            System.out
//                    .println(cmd
//                            .isSlashCommand("/not to be disturbed hahaha this is my message!"));
//            System.out.println();
        } catch (BadConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
