/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import DAO.CRUD_Guias;
import DAO.CRUD_Productos;
import Procedure.Functions;
import static Procedure.Procedures.pintarPanel;
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
public class CheckBox_Compra_E extends AbstractCellEditor implements ActionListener, TableCellEditor {

    JPanel jPanel2;
    JCheckBox jCheckBox1;
    JTable table;

    public CheckBox_Compra_E(JTable table) {
        jPanel2 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        this.table = table;

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
        return table.getValueAt(table.getSelectedRow(), table.getColumn("Comprar").getModelIndex());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = table.getSelectedRow();
        int col = table.getColumn("Comprar").getModelIndex();
        int colGuia = table.getColumn("Guia").getModelIndex();
        int guia = Integer.parseInt(table.getValueAt(row, colGuia).toString());
        int resp = 0;
        boolean selected = jCheckBox1.isSelected();
        String text = "";

        if (selected) {
            text = "Desea seleccionar todos los productos para comprar";
        } else {
            text = "¿Todos los productos fueron comprados?";
        }

        resp = JOptionPane.showConfirmDialog(Views.Main.frame, text, "Información", JOptionPane.YES_NO_OPTION, 1);
        if (0 == resp) {
            System.out.println("res " + resp);
            System.out.println("selected " + selected);

            CRUD_Productos.setComprarNumGuia(selected, guia);
            table.setValueAt(selected, row, col);

            if (CRUD_Guias.setComprar(jCheckBox1.isSelected(), guia)) {

                JOptionPane.showConfirmDialog(Views.Main.frame, "¡ Cambio Exitoso !", "Guardar", JOptionPane.DEFAULT_OPTION, 1, Functions.imagenCheck());
            } else {
                JOptionPane.showMessageDialog(Views.Main.frame, "Ups algo salio mal ", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("res " + resp);
            System.out.println("selected " + !selected);
            table.setValueAt(!selected, row, col);
        }
        stopCellEditing();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        pintarPanel(jPanel2, table, row);
        jCheckBox1.setSelected((boolean) value);
        jCheckBox1.setHorizontalAlignment(JCheckBox.CENTER);
        table.repaint();
        return jPanel2;
    }

}
