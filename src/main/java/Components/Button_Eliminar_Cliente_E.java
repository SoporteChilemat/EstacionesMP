package Components;

import DAO.CRUD_Clientes;
import DAO.CRUD_Correos;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class Button_Eliminar_Cliente_E extends AbstractCellEditor implements ActionListener, TableCellEditor {

    JButton jButton1;
    JPanel jPanel5;
    JTable table;
    int flag;

    public Button_Eliminar_Cliente_E(JTable table, int flag) {

        this.table = table;
        this.flag = flag;

        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton1.addActionListener(this);
        jButton1.setText("Eliminar");
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0))
        );

    }

    @Override
    public Object getCellEditorValue() {
        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = table.getSelectedRow();
        int colCliente = table.getColumn("Cliente").getModelIndex();
        int colRut = table.getColumn("Rut").getModelIndex();
        String rut = table.getValueAt(row, colRut).toString().replace(".", "").replace("-", "");
        String cliente = table.getValueAt(row, colCliente).toString();

        if (flag == 0) {
            if (CRUD_Clientes.eliminarCliente(cliente, rut)) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(row);
                Views.Main.jComboBox4.removeItem(cliente);
                table.revalidate();
                table.repaint();
            } else {
                JOptionPane.showMessageDialog(jPanel5, "NO SE PUEDE ELIMIAR UN CLIENTE QUE TENGA CORREOS O FACTURAS ASOCIADAS", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            }
            //eliminar cliente
        } else {
            int colcorreo = table.getColumn("Correo").getModelIndex();
            String correo = table.getValueAt(row, colcorreo).toString();
            if (CRUD_Correos.eliminarCorreo(correo, rut)) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(row);
                table.revalidate();
                table.repaint();
            }
        }

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return jPanel5;
    }

}
