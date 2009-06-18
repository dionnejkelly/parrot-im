import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SelectingJListSample {
  public static void main(String args[]) {
    String labels[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
    String test[] = { "A1","B2", "C3","D4","E5","F6","G7","H8", "I9","J10" };
    JFrame frame = new JFrame("Selecting JList");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container contentPane = frame.getContentPane();

    JList jlist = new JList(labels);
    jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scrollPane1 = new JScrollPane(jlist);
    contentPane.add(scrollPane1, BorderLayout.WEST);
    final DefaultListModel rightList = new DefaultListModel();
    rightList.addElement("Testing");
    
    JList jlist2 = new JList(rightList);
    JScrollPane scrollPane2 = new JScrollPane(jlist2);
    contentPane.add(scrollPane2, BorderLayout.EAST);

    ListSelectionListener listSelectionListener = new ListSelectionListener() 
    {
      public void valueChanged(ListSelectionEvent listSelectionEvent) 
      {
        boolean adjust = listSelectionEvent.getValueIsAdjusting();
        if (!adjust) 
        {
         JList list = (JList) listSelectionEvent.getSource();
         int selections[] = list.getSelectedIndices();
         Object selectionValues[] = list.getSelectedValues();
          for (int i = 0, n = selections.length; i < n; i++) {
            if (i == 0) {
              System.out.print("  Selection: " + selections[i]);
            }
          }
          System.out.println();
          rightList.clear();
          rightList.addElement("blah");
        }
      }
    };
    jlist.addListSelectionListener(listSelectionListener);

    MouseListener mouseListener = new MouseAdapter() 
    {
      public void mouseClicked(MouseEvent mouseEvent) 
      {
        JList theList = (JList) mouseEvent.getSource();
        if (mouseEvent.getClickCount() == 2) {
          int index = theList.locationToIndex(mouseEvent.getPoint());
          if (index >= 0) {
            Object o = theList.getModel().getElementAt(index);
            System.out.println("Double-clicked on: " + o.toString());
          }
        }
      }
    };
    jlist.addMouseListener(mouseListener);

    frame.setSize(350, 200);
    frame.setVisible(true);
  }
}



           