package auxiliary;

import usability.patterns.Consistency;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {

    private JCheckBox checkboxOne = new JCheckBox("Css");
    private JCheckBox checkboxTwo = new JCheckBox("Position");
    private JCheckBox checkboxThree = new JCheckBox("Size");

    public boolean cssFlag = true;
    public boolean positionFlag = true;
    public boolean sizeFlag = true;

    public Gui() {

        initUI();
    }

    private void initUI() {


        JButton ok = new JButton("OK");

        setLayout(new FlowLayout());


        checkboxOne.setSelected(true);
        checkboxTwo.setSelected(true);
        checkboxThree.setSelected(true);
        // add the check boxes to this frame
        add(checkboxOne);
        add(checkboxTwo);
        add(checkboxThree);

        add(ok);
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Ok Button clicked.");
                System.setProperty("webdriver.chrome.driver",
                        "./resources/chromedriver.exe");
                Gui.super.dispose();
                DriverHandler dh = new DriverHandler();
                Consistency consistency = new Consistency();
                //consistency.run(cssFlag, positionFlag, sizeFlag);
                DriverHandler.getDriver().quit();
            }
        });

        // add action listener for the check boxes
        ActionListener actionListener = new ActionHandler();
        checkboxOne.addActionListener(actionListener);
        checkboxTwo.addActionListener(actionListener);
        checkboxThree.addActionListener(actionListener);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JCheckBox checkbox = (JCheckBox) event.getSource();
            if (checkbox.isSelected()) {
                if (checkbox == checkboxOne) {
                    System.out.println("Selected " + checkbox.getText());
                    cssFlag = true;
                } else if (checkbox == checkboxTwo) {
                    positionFlag = true;
                    System.out.println("Selected " + checkbox.getText());
                } else if (checkbox == checkboxThree) {
                    sizeFlag = true;
                    System.out.println("Selected " + checkbox.getText());
                }
            } else {
                if (checkbox == checkboxOne) {
                    cssFlag = false;
                    System.out.println("Deselected " + checkbox.getText());
                } else if (checkbox == checkboxTwo) {
                    positionFlag = false;
                    System.out.println("Deselected " + checkbox.getText());
                } else if (checkbox == checkboxThree) {
                    sizeFlag = false;
                    System.out.println("Deselected " + checkbox.getText());
                }
            }

        }
    }


}
