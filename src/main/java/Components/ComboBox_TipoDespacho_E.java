package Components;

import DAO.CRUD_Guias;
import Procedure.Procedures;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Jason
 */
public class ComboBox_TipoDespacho_E extends AbstractCellEditor implements ActionListener, TableCellEditor {

    JPanel jPanel2;
    JComboBox jComboBox1;
    JTable table;

    public ComboBox_TipoDespacho_E(JTable table) {

        this.table = table;

        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox1.addActionListener(this);

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
    public Object getCellEditorValue() {
        return jComboBox1.getSelectedIndex();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int selectedIndex = jComboBox1.getSelectedIndex();

        int col = table.getColumn("Tipo Despacho").getModelIndex();
        int colGuia = table.getColumn("Guia").getModelIndex();
        int row = table.getSelectedRow();

        System.out.println("colGuia " + colGuia);
        System.out.println("row " + row);

        Integer guia = Integer.valueOf(table.getValueAt(row, colGuia).toString());

        if (CRUD_Guias.setTipoDespacho(selectedIndex, guia)) {
            table.setValueAt(selectedIndex, row, col);

            ImageIcon icon = new ImageIcon(Procedures.dir + "\\Sources\\check.png");
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);

            jComboBox1.setSelectedIndex(selectedIndex);

            JOptionPane.showConfirmDialog(Views.Main.frame, "¡ Cambio Exitoso !", "Guardar", JOptionPane.DEFAULT_OPTION, 1, icon);
        } else {
            JOptionPane.showMessageDialog(Views.Main.frame, "Ups algo salio mal ", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        stopCellEditing();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        Procedures.pintarPanel(jPanel2, table, row);
//        System.out.println(Integer.parseInt(value.toString()));

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
