package Components;

import Views.Fecha_Dialog;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Jason
 */
public class Button_Fecha_E extends AbstractCellEditor implements ActionListener, TableCellEditor {

    JPanel jPanel5;
    JLabel jLabel2;
    JButton jButton2;
    JTable table;

    @Override
    public Object getCellEditorValue() {
        return jLabel2.getText();
    }

    public Button_Fecha_E(JTable table) {

        this.table = table;

        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();

        jButton2.setText("Cambiar");
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, 0))
        );
        jButton2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stopCellEditing();
        Fecha_Dialog fd = new Fecha_Dialog(Views.Main.frame, true, table);
        fd.setLocationRelativeTo(Views.Main.frame);
        fd.setVisible(true);
        jLabel2.setText(table.getValueAt(table.getSelectedRow(), table.getColumn("F.Inicio").getModelIndex()).toString());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        String guia = table.getValueAt(row, table.getColumn("Guia").getModelIndex()).toString();
        if (guia.equals("0")) {
            jPanel5.setBackground(new Color(245, 120, 120));
        } else {
            jPanel5.setBackground(new Color(255, 198, 153));
        }
        jPanel5.setForeground(Color.BLACK);

        if (value.toString().equals("")) {
            this.jLabel2.setVisible(false);
            this.jButton2.setVisible(true);
            jLabel2.setText("");
        } else {
            this.jButton2.setVisible(false);
            this.jLabel2.setVisible(true);
            jLabel2.setText(value.toString());
        }
        return jPanel5;
    }
}
