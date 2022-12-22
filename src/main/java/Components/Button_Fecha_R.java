package Components;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Jason
 */
public class Button_Fecha_R extends JPanel implements TableCellRenderer {

    JLabel jLabel2;
    JButton jButton2;

    public Button_Fecha_R() {

        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jButton2.setText("Cambiar");
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this);
        this.setLayout(jPanel1Layout);
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
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String guia = table.getValueAt(row, table.getColumn("Guia").getModelIndex()).toString();
        if (guia.equals("0")) {
            this.setBackground(new Color(245, 120, 120));
        } else {
            this.setBackground(new Color(255, 198, 153));
        }
        this.setForeground(Color.BLACK);

        if (value.toString().equals("")) {
            this.jLabel2.setVisible(false);
            this.jButton2.setVisible(true);
            jLabel2.setText("");
        } else {
            this.jButton2.setVisible(false);
            this.jLabel2.setVisible(true);
            jLabel2.setText(value.toString());
        }
        return this;
    }
}
