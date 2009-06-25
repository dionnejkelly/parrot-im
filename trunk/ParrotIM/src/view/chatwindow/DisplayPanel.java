package view.chatwindow;

import java.awt.*;
import java.util.*;
import model.*;
import model.enumerations.UpdatedType;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

public class DisplayPanel extends JPanel implements Observer {
    private JEditorPane txtPane;
    private Model model;
    private JLabel title;

    // private String previousText;

    public DisplayPanel(Model model) {
        setLayout(new BorderLayout());
        
        this.model = model;
        this.model.addObserver(this);

        txtPane = new JEditorPane();
        txtPane.setPreferredSize(new Dimension(250, 300));
        
        JScrollPane chatWindowScroller = new JScrollPane(txtPane);
        chatWindowScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatWindowScroller.setPreferredSize(new Dimension(250, 300));
        chatWindowScroller.setMinimumSize(new Dimension(10, 10)); 
        chatWindowScroller.getInputContext();

        
        txtPane.setEditable(false);
        txtPane.setEditorKit(new HTMLEditorKit());
        
       
		
        if (model.getActiveConversation() != null) {
            txtPane.setText(model.getActiveConversation().displayMessages());
        } else {
            txtPane.setText("");
        }

        title = new JLabel();
        title.setText(model.getActiveConversation().getUser().getNickname());

        if (model.getActiveConversation().getUser().isBlocked()) {
            title.setForeground(Color.LIGHT_GRAY.darker());
        } else if (model.getActiveConversation().getUser().getState().toString().equals("Available")) {
            title.setForeground(Color.GREEN.darker());
        } else if (model.getActiveConversation().getUser().getState().toString().equals("dnd")) {
            title.setForeground(Color.ORANGE.darker());
        } else if (model.getActiveConversation().getUser().getState().toString().equals("away")) {
            title.setForeground(Color.ORANGE.darker());
        } else {
            title.setForeground(Color.RED.darker());
        }
        
        //JButton fullView = new JButton("Full");
        //JButton simpleView = new JButton("Simple");

        JToolBar bar1 = new JToolBar();
        bar1.setFloatable(false);
        bar1.add(title);
        //bar1.add(fullView);
        //bar1.add(simpleView);

        JToolBar bar2 = new JToolBar();
        bar2.setFloatable(false);
        JLabel isTyping = new JLabel("Ready...");
        bar2.add(isTyping);

        // add to panel
        add(bar1, BorderLayout.NORTH);
        add(bar2, BorderLayout.SOUTH);
        add(chatWindowScroller, BorderLayout.CENTER);
    }

    public JEditorPane getTxtPane() {
        return txtPane;
    }

    public void update(Observable o, Object arg) {
        /* May want to update this to update line-per-line */
        if (arg == UpdatedType.CHAT) {
            txtPane.setText(model.getActiveConversation().displayMessages());
            title.setText(model.getActiveConversation().getUser().getNickname());
            
            if (model.getActiveConversation().getUser().isBlocked()) {
                title.setForeground(Color.LIGHT_GRAY.darker());
            } else if (model.getActiveConversation().getUser().getState().toString().equals("Available")) {
                title.setForeground(Color.GREEN.darker());
            } else if (model.getActiveConversation().getUser().getState().toString().equals("dnd")) {
                title.setForeground(Color.ORANGE.darker());
            } else if (model.getActiveConversation().getUser().getState().toString().equals("away")) {
                title.setForeground(Color.ORANGE.darker());
            } else {
                title.setForeground(Color.RED.darker());
            }
            
        }
        return;
    }

}