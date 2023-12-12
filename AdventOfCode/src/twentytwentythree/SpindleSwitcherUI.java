package twentytwentythree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;


public class SpindleSwitcherUI {
    private static SpindleSwitcherUI INSTANCE;
    SpindleSwitcher spindleSwitcher;
    Map<String, JLabel> formLabels = new HashMap<>();
    Map<String, JTextField> formTextFields = new HashMap<>();
    Map<String, JToggleButton> formToggleButtons = new HashMap();
    JTextArea outputTextArea;

    public static SpindleSwitcherUI getInstance(){
        if (null == INSTANCE) {
            INSTANCE = new SpindleSwitcherUI();
        }
        return INSTANCE;
    }
    public void displayOptions(SpindleSwitcher spindleSwitcher) {
        this.spindleSwitcher = spindleSwitcher;
        // Basic swing frame
        JFrame jFrame = new JFrame("Spindle Switcher");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel selectionPanel = getSelectionPanel();
        JPanel confirmationPanel = getConfirmationPanel();
        JPanel outputPanel = getOutputPanel();

        contentPane.add(selectionPanel);
        contentPane.add(confirmationPanel);
        contentPane.add(outputPanel);
        jFrame.setContentPane(contentPane);
        jFrame.pack();
        jFrame.setVisible(true);
    }
    public JPanel getConfirmationPanel(){
        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new GridLayout(1, 3));
        JLabel blank1 = new JLabel();
        JButton btnConfirm = new JButton("Apply");
        btnConfirm.addActionListener(e -> spindleSwitcher.applyChanges());
        JLabel blank2 = new JLabel();
        confirmPanel.add(blank1);
        confirmPanel.add(btnConfirm);
        confirmPanel.add(blank2);
        return confirmPanel;
    }

    public JPanel getSelectionPanel(){
        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(1000, 400));
        GridBagLayout layout = new GridBagLayout();
        controlPanel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addDataEntryRow("Load Product Code", controlPanel, layout, gbc, 0);
        IntStream.rangeClosed(1, 12)
                .forEach(i -> addToggleButtonRowToGridbag(String.format("Spindle %s", i), controlPanel, layout, gbc, i));

        setBorder("Spindle settings", controlPanel);
        return controlPanel;
    }

    public JPanel getOutputPanel() {
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new GridLayout(1, 1));
        outputTextArea = new JTextArea(16, 58);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setBorder("Updated", scrollPane);
        outputPanel.add(scrollPane);
        return outputPanel;
    }

    public void addDataEntryRow(String buttonName, JPanel controlPanel, GridBagLayout theLayout, GridBagConstraints gbc, Integer row) {
        JTextField textFieldUser = new JFormattedTextField();
        textFieldUser.setColumns(12);
        formTextFields.put(buttonName, textFieldUser);
        JButton buttonSetUser = new JButton(buttonName);
        JLabel labelUser = new JLabel("", SwingConstants.CENTER);
        formLabels.put(buttonName, labelUser);
        buttonSetUser.addActionListener(e -> initialiseWithProductCode(formTextFields.get(buttonName), formLabels.get(buttonName)));
        addKeyboardPressToButton(buttonSetUser);
        addobjects(textFieldUser, controlPanel, theLayout, gbc, 0, row, 1, 1);
        addobjects(buttonSetUser, controlPanel, theLayout, gbc, 1, row, 1, 1);
        addobjects(labelUser, controlPanel, theLayout, gbc, 2, row, 1, 1);
    }

    public void addToggleButtonRowToGridbag(String buttonName, JPanel controlPanel, GridBagLayout theLayout, GridBagConstraints gbc, Integer row){
        JLabel spindleLbl = new JLabel(buttonName, SwingConstants.CENTER);
        formLabels.put(buttonName, spindleLbl);
        JToggleButton btn = new SpindleSwitcherUI.MyToggleButton("Enabled");
        btn.setBackground(Color.GREEN);
        btn.setSelected(true);
        formToggleButtons.put(buttonName, btn);
        btn.addItemListener(e -> applyStatusToButton(formToggleButtons.get(buttonName)));

        addobjects(spindleLbl, controlPanel,  theLayout, gbc, 0, row, 1, 1);
        addobjects(btn, controlPanel, theLayout, gbc, 1, row, 1, 1);
        JLabel colorsPerSpindle = new JLabel("", SwingConstants.LEFT);
        formLabels.put(buttonName + "out", colorsPerSpindle);
        addobjects(colorsPerSpindle, controlPanel, theLayout, gbc, 2, row, 3, 1);
    }

    public void addobjects(Component componente, Container yourcontainer, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight){
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        layout.setConstraints(componente, gbc);
        yourcontainer.add(componente);
    }

    public void applyTextToLabel(JTextField source, JLabel target) {
        target.setText(source.getText().replaceAll(",", ""));
    }

    public void initialiseWithProductCode(JTextField source, JLabel target) {
        applyTextToLabel(source, target);
        spindleSwitcher.getInitialSettings();
    }

    public void applyStatusToButton(JToggleButton source){
        if (source.isSelected()) {
            source.setBackground(Color.GREEN);
            source.setText("Enabled");
        } else {
            source.setBackground(Color.RED);
            source.setText("Disabled");
        }
    }

    public void addKeyboardPressToButton(JButton button) {
        InputMap inputMap = button.getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "pressed");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
    }

    public void setBorder(String borderTitle, JComponent jComponent) {
        jComponent.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder(borderTitle),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                        jComponent.getBorder()));
    }

    public void updateTextArea(String s, JTextArea outputTextArea) {
        outputTextArea.append(s + System.getProperty("line.separator"));
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());
    }
    public JTextArea getOutputTextArea() {
        return outputTextArea;
    }
    public Map<String, JLabel> getFormLabels() {
        return formLabels;
    }
    public Map<String, JTextField> getFormTextFields() {
        return formTextFields;
    }
    public Map<String, JToggleButton> getFormToggleButtons() {
        return formToggleButtons;
    }

    class MyToggleButton extends JToggleButton{
        // Extend JToggleButton as default Look & Feel doesn't repaint button
        // in green when re-enabled.
        public MyToggleButton(String text) {
            super(text);
            super.setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

    }
}
