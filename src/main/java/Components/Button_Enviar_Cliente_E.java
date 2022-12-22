package Components;

import DAO.CRUD_Permisos;
import Object.Permiso;
import Object.User;
import Procedure.Procedures;
import static Procedure.Procedures.crearCarpeta;
import static Procedure.Procedures.dir;
import static Procedure.Procedures.esperarPegarGuia;
import static Procedure.Procedures.limpiarCarpetaFacturas;
import Views.Drops_Dialog;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class Button_Enviar_Cliente_E extends AbstractCellEditor implements ActionListener, TableCellEditor {

    JButton jButton1;
    JPanel jPanel5;
    JTable table;

    public Button_Enviar_Cliente_E(JTable table, User user) {

        this.table = table;

        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();

        jButton1.setText("Enviar");
        Permiso permiso = CRUD_Permisos.getPermiso(user.getNombre());
        jButton1.setEnabled(permiso.isFactura());

        jButton1.addActionListener(this);

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
        int colOC = table.getColumn("OC").getModelIndex();
        int colCliente = table.getColumn("Cliente").getModelIndex();
        int colFactura = table.getColumn("Factura").getModelIndex();
        int colGuia = table.getColumn("Guia").getModelIndex();

        String oc = table.getValueAt(row, colOC).toString();
        String cliente = table.getValueAt(row, colCliente).toString();
        String factura = table.getValueAt(row, colFactura).toString();
        String guia = table.getValueAt(row, colGuia).toString();

        String rutaDescarga = dir + "\\Facturas";
        limpiarCarpetaFacturas(rutaDescarga);
        crearCarpeta("Facturas");

        Drops_Dialog d = new Views.Drops_Dialog(Views.Main.frame, true, guia);
        d.setLocationRelativeTo(Views.Main.frame);
        d.setVisible(true);
        esperarPegarGuia(rutaDescarga);

        Procedures.procedimientoDescargarFactura(factura, guia, oc, cliente);

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return jPanel5;
    }

}
