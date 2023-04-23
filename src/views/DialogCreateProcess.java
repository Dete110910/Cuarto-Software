package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigInteger;

public class DialogCreateProcess extends JDialog {

    private Button create, cancel;

    private JLabel nameProcess, timeProcess, sizeProcess, blockProcess, partition;
    private JTextField inputNameProcess, inputTimeProcess, inputSizeProcess;
    private JRadioButton yesBlockProcess, noBlockProcess;
    private ButtonGroup blockProcessGroup;
    private JPanel panelBlockButtons;

    private JComboBox<String> partitionCombo;




    public DialogCreateProcess(ActionListener actionListener, KeyListener keyListener) {
        this.setModal(true);
        this.setTitle("Crear Proceso");
        this.setLayout(new GridBagLayout());
        this.setFont(ConstantsGUI.MAIN_MENU);
        this.setSize(420, 320);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.decode("#C9ADA7"));
        initComponents(actionListener, keyListener);
    }

    private void initComponents(ActionListener actionListener, KeyListener keyListener) {
        this.nameProcess = new JLabel("Nombre");
        this.nameProcess.setFont(ConstantsGUI.FONT_TITLE_INPUTS);
        Utilities.addComponent(this, this.nameProcess, 0, 0);

        this.inputNameProcess = new JTextField(10);
        this.inputNameProcess.setSize(100, 50);
        this.inputNameProcess.setPreferredSize(new Dimension(100, 30));
        this.inputNameProcess.setBackground(Color.WHITE);
        this.inputNameProcess.setFont(ConstantsGUI.FONT_INPUTS);
        Utilities.addComponent(this, this.inputNameProcess, 1, 0);

        this.timeProcess = new JLabel("Tiempo");
        this.timeProcess.setFont(ConstantsGUI.FONT_TITLE_INPUTS);
        Utilities.addComponent(this, this.timeProcess, 0, 1);

        this.inputTimeProcess = new JTextField(10);
        this.inputTimeProcess.addKeyListener(keyListener);
        this.inputTimeProcess.setSize(100, 50);
        this.inputTimeProcess.setPreferredSize(new Dimension(100, 30));
        this.inputTimeProcess.setBackground(Color.WHITE);
        this.inputTimeProcess.setFont(ConstantsGUI.FONT_INPUTS);
        Utilities.addComponent(this, this.inputTimeProcess, 1, 1);

        this.sizeProcess = new JLabel("Tamaño");
        this.sizeProcess.setFont(ConstantsGUI.FONT_TITLE_INPUTS);
        Utilities.addComponent(this, this.sizeProcess, 0, 2);

        this.inputSizeProcess = new JTextField(10);
        this.inputSizeProcess.addKeyListener(keyListener);
        this.inputSizeProcess.setSize(100, 50);
        this.inputSizeProcess.setPreferredSize(new Dimension(100, 30));
        this.inputSizeProcess.setBackground(Color.WHITE);
        this.inputSizeProcess.setFont(ConstantsGUI.FONT_INPUTS);
        Utilities.addComponent(this, this.inputSizeProcess, 1, 2);

        this.blockProcess = new JLabel("Bloquear");
        this.blockProcess.setFont(ConstantsGUI.FONT_TITLE_INPUTS);
        Utilities.addComponent(this, blockProcess, 0, 3);

        this.blockProcessGroup = new ButtonGroup();

        this.yesBlockProcess = new JRadioButton("Sí");
        this.yesBlockProcess.setBackground(Color.decode("#C9ADA7"));
        this.yesBlockProcess.setForeground(Color.BLACK);
        this.yesBlockProcess.setFont(ConstantsGUI.FONT_MENU_ACTIONS);

        this.noBlockProcess = new JRadioButton("No");
        this.noBlockProcess.setBackground(Color.decode("#C9ADA7"));
        this.noBlockProcess.setForeground(Color.BLACK);
        this.noBlockProcess.setFont(ConstantsGUI.FONT_MENU_ACTIONS);

        this.blockProcessGroup.add(this.yesBlockProcess);
        this.blockProcessGroup.add(this.noBlockProcess);
        this.yesBlockProcess.setSelected(true);

        this.panelBlockButtons = new JPanel();
        this.panelBlockButtons.setBackground(Color.decode("#C9ADA7"));
        this.panelBlockButtons.add(this.yesBlockProcess);
        this.panelBlockButtons.add(this.noBlockProcess);

        Utilities.addComponent(this, this.panelBlockButtons, 1, 3);

        this.partition = new JLabel("Partición");
        this.partition.setFont(ConstantsGUI.FONT_TITLE_INPUTS);
        Utilities.addComponent(this, this.partition, 0, 4);


        this.partitionCombo = new JComboBox<>();
        this.partitionCombo.setSize(150, 50);
        this.partitionCombo.setPreferredSize(new Dimension(150,30));
        this.partitionCombo.setBackground(Color.WHITE);
        this.partitionCombo.setFont(ConstantsGUI.FONT_INPUTS);

        Utilities.addComponent(this, this.partitionCombo, 1, 4);

        this.create = new Button("Añadir");
        this.create.addActionListener(actionListener);
        this.create.setActionCommand("AñadirProceso");
        this.create.setPreferredSize(new Dimension(150, 35));
        Utilities.addComponent(this, create, 0, 5);

        this.cancel = new Button("Cancelar");
        this.cancel.addActionListener(actionListener);
        this.cancel.setActionCommand("CancelarAñadirProceso");
        this.cancel.setPreferredSize(new Dimension(150, 35));
        Utilities.addComponent(this, cancel, 1, 5);

    }


    public void setProcessName(String nameProcess) {
        this.inputNameProcess.setText(nameProcess);
    }

    public String getNameProcess() {
        return inputNameProcess.getText();
    }

    public void setTimeProcess(BigInteger timeProcess) {
        this.inputTimeProcess.setText(timeProcess.toString());
    }

    public BigInteger getTimeProcess() {
        BigInteger timeProcess = new BigInteger("-1");
        try {
            timeProcess = new BigInteger(this.inputTimeProcess.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Número inválido");

        }
        return timeProcess;
    }

    public void setProcessSize(BigInteger processSize){
        this.inputSizeProcess.setText(processSize.toString());
    }
    public BigInteger getProcessSize(){
        BigInteger sizeProcess = new BigInteger("-1");
        try {
            sizeProcess = new BigInteger(this.inputSizeProcess.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Número inválido");

        }
        return sizeProcess;
    }

    public void setIsBlock(boolean isBlock){
        if(isBlock){
            this.yesBlockProcess.setSelected(true);
            this.noBlockProcess.setSelected(false);
        }
        else{
            this.yesBlockProcess.setSelected(false);
            this.noBlockProcess.setSelected(true);
        }
    }

    public boolean getIsBlock() {
        return this.yesBlockProcess.isSelected();
    }



    public void changeButtonToModify() {
        this.create.setText("Modificar");
        this.create.setActionCommand("ConfirmarModificacionProceso");
    }

    public void changeButtonToCreate() {
        this.create.setText("Añadir");
        this.create.setActionCommand("AñadirProceso");
    }

    public void cleanAllFields() {
        this.inputNameProcess.setText("");
        this.inputTimeProcess.setText("");
        this.inputSizeProcess.setText("");
        this.partitionCombo.setSelectedIndex(0);
        this.yesBlockProcess.setSelected(true);
    }

    public String getSelectedElementFirstCombo(){
        return (String) this.partitionCombo.getSelectedItem();
    }

    public void setValuesCombo(String[] values){
        this.partitionCombo.setModel(new DefaultComboBoxModel<>(values));
    }



}