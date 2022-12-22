package Components;

import Views.Main;
import Views.Popup_Producto_Dialog;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

public class PopUp_Producto extends DefaultCellEditor implements TableCellEditor {

    JTable table;

    public PopUp_Producto(JTable table) {
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

            String guia = table.getValueAt(table.getSelectedRow(), 0).toString();
            Popup_Producto_Dialog popup = new Popup_Producto_Dialog(Integer.parseInt(guia),table);

            popup.setLocationRelativeTo(Main.frame);
            popup.setResizable(true);
            popup.setVisible(true);

            fireEditingStopped();

        });
        return editorComponent;
    }

}
