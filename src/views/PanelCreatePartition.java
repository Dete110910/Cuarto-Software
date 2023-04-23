package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigInteger;

public class PanelCreatePartition extends JPanel {

    private Button add, cancel;

    private JLabel partitions,partitionName, partitionSize;
    private JTextField inputPartitionName, inputPartitionSize;

    public PanelCreatePartition(ActionListener actionListener, KeyListener keyListener){
        this.setLayout(new GridBagLayout());
        this.setFont(ConstantsGUI.MAIN_MENU);
        this.setSize(420, 420);
        this.setPreferredSize(new Dimension(460, 200));
        this.setBackground(Color.decode("#C9ADA7"));
        this.initComponents(actionListener, keyListener);

    }

    private void initComponents(ActionListener actionListener, KeyListener keyListener) {
        this.partitions = new JLabel("Crear Particiones");
        this.partitions.setFont(ConstantsGUI.FONT_TITLE_INPUTS);
        this.addComponent(this.partitions, 0, 0);

        this.partitionName = new JLabel("Nombre");
        this.partitionName.setFont(ConstantsGUI.FONT_TITLE_INPUTS);
        Utilities.addComponent(this, partitionName, 0, 1);

        this.inputPartitionName = new JTextField(10);
        this.inputPartitionName.setSize(100, 50);
        this.inputPartitionName.setPreferredSize(new Dimension(100, 30));
        this.inputPartitionName.setBackground(Color.WHITE);
        this.inputPartitionName.setFont(ConstantsGUI.FONT_INPUTS);
        Utilities.addComponent(this, inputPartitionName, 1, 1);

        this.partitionSize = new JLabel("Tamaño (K)");
        this.partitionSize.setFont(ConstantsGUI.FONT_TITLE_INPUTS);
        Utilities.addComponent(this, partitionSize, 0, 2);

        this.inputPartitionSize = new JTextField(10);
        this.inputPartitionSize.setSize(100, 50);
        this.inputPartitionSize.setPreferredSize(new Dimension(100, 30));
        this.inputPartitionSize.setBackground(Color.WHITE);
        this.inputPartitionSize.setFont(ConstantsGUI.FONT_INPUTS);
        this.inputPartitionSize.addKeyListener(keyListener);
        Utilities.addComponent(this, inputPartitionSize, 1, 2);

        this.add = new Button("Añadir");
        this.add.addActionListener(actionListener);
        this.add.setActionCommand("AñadirParticion");
        this.add.setPreferredSize(new Dimension(150, 35));
        Utilities.addComponent(this, add, 0, 3);

        this.cancel = new Button("Listo");
        this.cancel.addActionListener(actionListener);
        this.cancel.setActionCommand("CancelarParticion");
        this.cancel.setPreferredSize(new Dimension(150, 35));
        Utilities.addComponent(this, cancel, 1, 3);


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

    public void addComponent(JComponent component, int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.add(component, gbc);
    }


    public void setPartitionName(String name) {
        this.inputPartitionName.setText(name);
    }
    public void setPartitionSize(String size) {
        this.inputPartitionSize.setText(size);
    }

    public void changeButtonToModify() {
        this.add.setText("Modificar");
        this.add.setActionCommand("ConfirmarModificacionParticion");
    }

    public void changeButtonText() {
        this.cancel.setText("Cancelar");
    }
}
