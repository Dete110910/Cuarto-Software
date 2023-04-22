package views;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelTable extends JPanel {

    private DefaultTableModel modelTable;
    private JTable tableProcess;
    private JScrollPane scroll;

    public PanelTable(){
        this.initComponents();
        this.setVisible(true);
    }

    private void initComponents(){
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setBackground(Color.WHITE);
        String[] headers  = ConstantsGUI.HEADERS;
        this.modelTable = new DefaultTableModel();
        this.modelTable.setColumnIdentifiers(headers);

        this.tableProcess = new JTable();
        this.tableProcess.setModel(this.modelTable);
        this.tableProcess.getTableHeader().setReorderingAllowed(false);
        this.tableProcess.getTableHeader().setBackground(Color.decode("#4a4e69"));
        this.tableProcess.getTableHeader().setForeground(Color.WHITE);
        this.tableProcess.getTableHeader().setFont(ConstantsGUI.FONT_TABLE_HEADER);
        this.tableProcess.setFillsViewportHeight(true);
        this.tableProcess.setFont(ConstantsGUI.FONT_TABLE);
        this.tableProcess.setBackground(Color.decode("#F2E9E4"));
        this.tableProcess.setOpaque(true);
        this.tableProcess.setRowHeight(25);


        this.scroll = new JScrollPane(this.tableProcess);
        this.scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#204051")),
                "Procesos Existentes", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, ConstantsGUI.FONT_TABLE_HEADER, Color.decode("#204051")));
        this.scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(this.scroll, BorderLayout.PAGE_END);
        setBorder(null);
    }

    public void setTableProcess(DefaultTableModel defaultTableModel){
        this.modelTable = defaultTableModel;
        this.tableProcess.setModel(defaultTableModel);
    }

    public int getIndexDataProcess(){
        return this.tableProcess.getSelectedRow();
    }

    public void changeTitle(String title){
        this.scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#204051")),
                title, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, ConstantsGUI.FONT_TABLE_HEADER, Color.decode("#204051")));
    }

    public void setEnableTable(boolean isEnable){
        this.tableProcess.setEnabled(isEnable);
    }
}
