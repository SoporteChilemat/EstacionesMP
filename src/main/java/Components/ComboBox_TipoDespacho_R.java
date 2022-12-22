/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import Procedure.Procedures;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Jason
 */
public class ComboBox_TipoDespacho_R extends JPanel implements TableCellRenderer {

    JPanel jPanel2;
    JComboBox jComboBox1;

    public ComboBox_TipoDespacho_R() {

        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Parcial Pendiente", "Parcial Solicitado", "Total", "Cerrado"}));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE)
                                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE)
                                .addGap(0, 0, 0))
        );
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Procedures.pintarPanel(jPanel2, table, row);

        try {
            jComboBox1.setSelectedIndex(Integer.parseInt(value.toString()));
        } catch (NumberFormatException e) {
            System.out.println(e);
        }

        String comentario = table.getValueAt(row, table.getColumn("Comentario").getModelIndex()).toString();
        String entregado = table.getValueAt(row, table.getColumn("Entregado").getModelIndex()).toString();
        String transporte = table.getValueAt(row, table.getColumn("En Despacho").getModelIndex()).toString();

        if (entregado.equals("true")) {
            //VERDE
            jComboBox1.setBackground(new Color(77, 255, 106));
            jComboBox1.setForeground(new Color(77, 255, 106));
        } else if (transporte.equals("true")) {
            //AMARILLO
            jComboBox1.setBackground(new Color(255, 204, 51));
            jComboBox1.setForeground(new Color(255, 204, 51));
        } else if (!comentario.equals("")) {
            //MORADO
            jComboBox1.setBackground(new Color(255, 77, 255));
            jComboBox1.setForeground(new Color(255, 77, 255));
        } else {
            //BEIGE
            jComboBox1.setBackground(new Color(255, 198, 153));
            jComboBox1.setForeground(new Color(255, 198, 153));
        }

        jComboBox1.setForeground(Color.BLACK);

        return jPanel2;
    }

}
