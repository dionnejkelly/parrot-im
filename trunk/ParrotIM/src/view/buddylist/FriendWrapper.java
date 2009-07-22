package view.buddylist;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import model.dataType.TwitterUserData;
import model.dataType.UserData;
import model.enumerations.UserStateType;

public class FriendWrapper implements Observer {

    private UserData user;

    private JLabel labelRepresentation;

    public FriendWrapper(UserData user) {
        this.user = user;
        this.labelRepresentation = new JLabel(this.toString());
        this.labelRepresentation.setForeground(this.retrieveLabelColor());

        this.user.addObserver(this);
    }

    public UserData getUser() {
        return this.user;
    }

    public JLabel getLabelRepresentation() {
        return this.labelRepresentation;
    }

    public Color retrieveLabelColor() {
        Color labelColor = null;
        int minutesSinceUpdate = 16384;

        if (user.isBlocked()) {
            labelColor = Color.LIGHT_GRAY.darker();
        } else if (user instanceof TwitterUserData) {
            minutesSinceUpdate =
                    ((TwitterUserData) user).getMinutesSinceUpdate();
            if (minutesSinceUpdate < 60) {
                labelColor = Color.GREEN.darker();
            } else if (minutesSinceUpdate < 300) {
                labelColor = Color.ORANGE.darker();
            } else {
                labelColor = Color.RED.darker();
            }
        } else if (user.getState() == UserStateType.ONLINE) {
            labelColor = Color.GREEN.darker();
        } else if (user.getState() == UserStateType.BUSY) {
            labelColor = Color.ORANGE.darker();
        } else if (user.getState() == UserStateType.AWAY) {
            labelColor = Color.ORANGE.darker();
        } else {
            labelColor = Color.RED.darker();
        }

        return labelColor;
    }

    public String toString() {
        String toReturn = "";
        int minutesSinceUpdate = 16384;

        if (user.isBlocked()) {
            toReturn = "  Blocked: " + user.getUserID() + " *";
        } else if (user instanceof TwitterUserData) {
            minutesSinceUpdate =
                    ((TwitterUserData) user).getMinutesSinceUpdate();
            toReturn =
                    "  " + user.getNickname() + " -- " + user.getStatus()
                            + " (Changed: " + minutesSinceUpdate
                            + " minutes ago)";
        } else if (user.getState() == UserStateType.ONLINE) {
            if (user.getNickname().trim().equals("")) {
                toReturn =
                        "  " + user.getUserID() + " -- " + user.getStatus()
                                + " (" + user.getState() + ")";
            } else {
                toReturn =
                        "  " + user.getNickname() + " -- " + user.getStatus()
                                + " (" + user.getState() + ")";
            }

        } else if (user.getState() == UserStateType.BUSY) {
            if (user.getNickname().trim().equals("")) {
                toReturn =
                        "  " + user.getUserID() + " -- " + user.getStatus()
                                + " (Busy)";
            } else {
                toReturn =
                        "  " + user.getNickname() + " -- " + user.getStatus()
                                + " (Busy)";
            }

        } else if (user.getState() == UserStateType.AWAY) {
            if (user.getNickname().trim().equals("")) {
                toReturn =
                        "  " + user.getUserID() + " -- " + user.getStatus()
                                + " (Away)";
            } else {
                toReturn =
                        "  " + user.getNickname() + " -- " + user.getStatus()
                                + " (Away)";
            }

        } else {
            if (user.getNickname().trim().equals("")) {
                toReturn =
                        "  " + user.getUserID() + " -- " + user.getStatus()
                                + " (" + user.getState() + ")";
            } else {
                toReturn =
                        "  " + user.getNickname() + " -- " + user.getStatus()
                                + " (" + user.getState() + ")";
            }
        }

        return toReturn;
    }

    public void forceUpdate() {
        this.labelRepresentation.setText(this.toString());
        this.labelRepresentation.setForeground(this.retrieveLabelColor());

        return;
    }

    public void update(Observable o, Object arg) {
        this.labelRepresentation.setText(this.toString());
        this.labelRepresentation.setForeground(this.retrieveLabelColor());

        return;
    }

}
