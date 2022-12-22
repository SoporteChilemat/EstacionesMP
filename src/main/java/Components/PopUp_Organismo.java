package Components;

import Views.Popup_Organismo_Dialog;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

public class PopUp_Organismo extends DefaultCellEditor implements TableCellEditor {

    JTable table;

    public PopUp_Organismo(JTable table) {
        super(new JTextField());
        setClickCountToStart(0);

        this.table = table;

        editorComponent = new JButton();
        editorComponent.setBackground(new Color(245, 120, 120));
        editorComponent.setFocusable(false);
    }

    @Override
    public Object getCellEditorValue() {
        return table.getValueAt(table.getSelectedRow(), 0).toString();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        SwingUtilities.invokeLater(() -> {

            String organismo = DAO.CRUD_Licitaciones.getOrgnismo(Integer.parseInt(table.getValueAt(row, table.getColumn("Cotización").getModelIndex()).toString()));

            Popup_Organismo_Dialog popup = new Popup_Organismo_Dialog();

            Point p = editorComponent.getLocationOnScreen();

            popup.setLocation(p.x, p.y + editorComponent.getSize().height);
            popup.jLabel1.setText(organismo.toUpperCase());
            popup.setResizable(true);
            popup.setVisible(true);

            fireEditingStopped();

        });
        return editorComponent;
    }

}
