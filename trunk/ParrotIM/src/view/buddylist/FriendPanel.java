package view.buddylist;

import javax.swing.JPanel;

public class FriendPanel extends JPanel {

    private FriendWrapper wrapper;

    public void setWrapper(FriendWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public FriendWrapper getWrapper() {
        return this.wrapper;
    }

}
