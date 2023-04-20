package views;

import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigInteger;
import java.security.Key;

public class DialogCreatePartition extends JDialog {

    private Button add, cancel;

    private JLabel partitionName, partitionSize;
    private JTextField inputPartitionName, inputPartitionSize;

    public DialogCreatePartition(ActionListener actionListener, KeyListener keyListener){
        this.setModal(true);
        this.setTitle("Crear particiones");
        this.setLayout(new GridBagLayout());
        this.setFont(ConstantsGUI.MAIN_MENU);
        this.setSize(420, 320);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.decode("#C9ADA7"));
        this.initComponents(actionListener, keyListener);

    }

    private void initComponents(ActionListener actionListener, KeyListener keyListener) {
        this.partitionName = new JLabel("Nombre");
        this.partitionName.setFont(ConstantsGUI.FONT_TITLE_INPUTS);
        Utilities.addComponent(this, partitionName, 0, 0);

        this.inputPartitionName = new JTextField(10);
        this.inputPartitionName.setSize(100, 50);
        this.inputPartitionName.setPreferredSize(new Dimension(100, 30));
        this.inputPartitionName.setBackground(Color.WHITE);
        this.inputPartitionName.setFont(ConstantsGUI.FONT_INPUTS);
        Utilities.addComponent(this, inputPartitionName, 1, 0);

        this.partitionSize = new JLabel("Tamaño (K)");
        this.partitionSize.setFont(ConstantsGUI.FONT_TITLE_INPUTS);
        Utilities.addComponent(this, partitionSize, 0, 1);

        this.inputPartitionSize = new JTextField(10);
        this.inputPartitionSize.setSize(100, 50);
        this.inputPartitionSize.setPreferredSize(new Dimension(100, 30));
        this.inputPartitionSize.setBackground(Color.WHITE);
        this.inputPartitionSize.setFont(ConstantsGUI.FONT_INPUTS);
        this.inputPartitionSize.addKeyListener(keyListener);
        Utilities.addComponent(this, inputPartitionSize, 1, 1);

        this.add = new Button("Añadir");
        this.add.addActionListener(actionListener);
        this.add.setActionCommand("AñadirParticion");
        this.add.setPreferredSize(new Dimension(150, 35));
        Utilities.addComponent(this, add, 0, 2);

        this.cancel = new Button("Cancelar");
        this.cancel.addActionListener(actionListener);
        this.cancel.setActionCommand("CancelarParticion");
        this.cancel.setPreferredSize(new Dimension(150, 35));
        Utilities.addComponent(this, cancel, 1, 2);
    }

    public String getPartitionName(){
        return this.inputPartitionName.getText();
    }

    public BigInteger getPartitionSize(){
        BigInteger partitionSize = new BigInteger("-1");
        try {
            partitionSize = new BigInteger(this.inputPartitionSize.getText());
        } catch (NumberFormatException numberFormatException){
            System.out.println("Número inválido");

        }
        return partitionSize;
    }

    public void cleanAllFields(){
        this.inputPartitionName.setText("");
        this.inputPartitionSize.setText("");
    }


}
