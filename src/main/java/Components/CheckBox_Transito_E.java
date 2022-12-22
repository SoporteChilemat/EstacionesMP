/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import DAO.CRUD_Guias;
import Procedure.Functions;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Jason
 */
public class CheckBox_Transito_E extends AbstractCellEditor implements ActionListener, TableCellEditor {

    JPanel jPanel2;
    JCheckBox jCheckBox1;
    JTable table;
//    JTable tableMain;

    public CheckBox_Transito_E(JTable table) {
        jPanel2 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        this.table = table;
//        this.tableMain = tableMain;

        jCheckBox1.addActionListener(this);
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, 3)
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }

    @Override
    public Object getCellEditorValue() {
        return table.getValueAt(table.getSelectedRow(), table.getColumn("Transito").getModelIndex());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = table.getSelectedRow();
        int col = table.getColumn("Transito").getModelIndex();
        int colGuia = table.getColumn("Guia").getModelIndex();
        int colFecha = table.getColumn("Fecha").getModelIndex();
        int guia = 0;
        boolean selected = jCheckBox1.isSelected();
        String fecha = "";

        if (selected) {
            fecha = Functions.getFecha2();
            table.setValueAt(selected, row, col);
            table.setValueAt(fecha, row, colFecha);
            guia = Integer.parseInt(table.getValueAt(row, colGuia).toString());

            if (CRUD_Guias.setFechaTransito(guia, Functions.getFecha())) {
                JOptionPane.showConfirmDialog(Views.Main.frame, "¡ Cambio Exitoso !", "Guardar", JOptionPane.DEFAULT_OPTION, 1, Functions.imagenCheck());
            } else {
                JOptionPane.showMessageDialog(Views.Main.frame, "Ups algo salio mal ", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        stopCellEditing();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        jPanel2.setBackground(new Color(255, 204, 51));
        jPanel2.setForeground(Color.BLACK);
        if ((boolean) value) {
            jCheckBox1.setEnabled(!(boolean) value);
        } else {
            jCheckBox1.setEnabled(!(boolean) value);
        }

        jCheckBox1.setSelected((boolean) value);
        jCheckBox1.setHorizontalAlignment(JCheckBox.CENTER);
        table.repaint();
        return jPanel2;
    }

}
