package src.john01dav.jrandomizer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class JRandomizer extends JFrame {
    private JRandomizer instance;

    private JRandomizerActionListener actionListener;

    private DefaultListModel<String> listModel;

    private SpringLayout layout;
    private JLabel numberToSelect;
    private JTextField countEntry;
    private JButton randomize;
    private JList jList;
    private JTextField newEntry;
    private JButton addEntry;
    private JButton removeEntry;

    public static void main(String[] args) {
        new JRandomizer().start();
    }

    private void start(){
        instance = this;

        actionListener = new JRandomizerActionListener();

        listModel = new DefaultListModel<>();

        layout = new SpringLayout();
        setLayout(layout);

        numberToSelect = new JLabel("Number of entries to select:");
        add(numberToSelect);

        countEntry = new JTextField();
        add(countEntry);

        randomize = new JButton("Randomize!");
        randomize.setActionCommand("randomize");
        randomize.addActionListener(actionListener);
        add(randomize);

        jList = new JList();
        jList.setModel(listModel);
        add(jList);

        newEntry = new JTextField();
        add(newEntry);

        addEntry = new JButton("+");
        addEntry.setActionCommand("addEntry");
        addEntry.addActionListener(actionListener);
        add(addEntry);

        removeEntry = new JButton("-");
        removeEntry.setActionCommand("removeEntry");
        removeEntry.addActionListener(actionListener);
        add(removeEntry);

        //numberToSelect
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, numberToSelect, 0, SpringLayout.VERTICAL_CENTER, countEntry);
        layout.putConstraint(SpringLayout.WEST, numberToSelect, 10, SpringLayout.WEST, getContentPane());

        //countEntry
        layout.putConstraint(SpringLayout.NORTH, countEntry, 10, SpringLayout.NORTH, getContentPane());
        layout.putConstraint(SpringLayout.EAST, countEntry, -5, SpringLayout.WEST, randomize);
        layout.putConstraint(SpringLayout.WEST, countEntry, 5, SpringLayout.EAST, numberToSelect);
        layout.putConstraint(SpringLayout.SOUTH, countEntry, -5, SpringLayout.NORTH, jList);

        //randomize
        layout.putConstraint(SpringLayout.NORTH, randomize, 10, SpringLayout.NORTH, getContentPane());
        layout.putConstraint(SpringLayout.EAST, randomize, -10, SpringLayout.EAST, getContentPane());

        //jList
        layout.putConstraint(SpringLayout.NORTH, jList, 5, SpringLayout.SOUTH, randomize);
        layout.putConstraint(SpringLayout.EAST, jList, -10, SpringLayout.EAST, getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, jList, -5, SpringLayout.NORTH, addEntry);
        layout.putConstraint(SpringLayout.WEST, jList, 10, SpringLayout.WEST, getContentPane());

        //newEntry
        layout.putConstraint(SpringLayout.EAST, newEntry, -5, SpringLayout.WEST, addEntry);
        layout.putConstraint(SpringLayout.SOUTH, newEntry, -10, SpringLayout.SOUTH, getContentPane());
        layout.putConstraint(SpringLayout.WEST, newEntry, 10, SpringLayout.WEST, getContentPane());
        layout.putConstraint(SpringLayout.NORTH, newEntry, 5, SpringLayout.SOUTH, jList);

        //addEntry
        layout.putConstraint(SpringLayout.EAST, addEntry, -5, SpringLayout.WEST, removeEntry);
        layout.putConstraint(SpringLayout.SOUTH, addEntry, -10, SpringLayout.SOUTH, getContentPane());

        //removeEntry
        layout.putConstraint(SpringLayout.EAST, removeEntry, -10, SpringLayout.EAST, getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, removeEntry, -10, SpringLayout.SOUTH, getContentPane());

        setTitle("JRandomizer");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class JRandomizerActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println("Action Command: " + e.getActionCommand());
            switch(e.getActionCommand()){
                case "addEntry":
                    String requestedEntry = newEntry.getText();
                    for(int x=0;x<listModel.size();x++){
                        String entry = listModel.getElementAt(x);
                        if(entry.equalsIgnoreCase(requestedEntry)){
                            JOptionPane.showMessageDialog(instance, "The entry \"" + requestedEntry + "\" is already taken!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    listModel.addElement(requestedEntry);
                break;
                case "removeEntry":
                    int selectedIndex = jList.getSelectedIndex();
                    if(selectedIndex < 0){
                        JOptionPane.showMessageDialog(instance, "You must select an entry before removing it.", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        listModel.remove(jList.getSelectedIndex());
                    }
                break;
                case "randomize":
                    Random random = new Random(System.currentTimeMillis());
                    String numberString = countEntry.getText();
                    int number;


                    try{
                        number = Integer.parseInt(numberString);

                        if(listModel.size() < number) throw new IllegalArgumentException();

                        while(listModel.size() > number){
                            listModel.remove(random.nextInt(listModel.size()));
                        }

                        JOptionPane.showMessageDialog(instance, number + " entries have been selected!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                    }catch(NumberFormatException e2){
                        JOptionPane.showMessageDialog(instance, "Invalid number \"" + numberString + "\".", "Error", JOptionPane.ERROR_MESSAGE);
                    }catch(IllegalArgumentException e2){
                        JOptionPane.showMessageDialog(instance, "You have requested to select more entries than there are.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                break;
            }
        }
    }

}
