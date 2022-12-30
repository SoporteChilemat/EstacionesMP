package Procedure;

import Components.AutoCompletion;
import Components.Button_Eliminar_Cliente_E;
import Components.Button_Eliminar_Cliente_R;
import Components.Button_Enviar_Cliente_E;
import Components.Button_Enviar_Cliente_R;
import Components.CheckBox_CompraProd_E;
import Components.CheckBox_CompraProd_R;
import Components.CheckBox_Compra_E;
import Components.CheckBox_Compra_R;
import Components.CheckBox_Correos_E;
import Components.CheckBox_Correos_R;
import Components.CheckBox_Despacho_E;
import Components.CheckBox_Despacho_R;
import Components.CheckBox_Entregado_E;
import Components.CheckBox_Entregado_R;
import Components.CheckBox_Factura_E;
import Components.CheckBox_Factura_R;
import Components.CheckBox_Transito_E;
import Components.CheckBox_Transito_R;
import Components.ComboBox_TipoDespacho_E;
import Components.ComboBox_TipoDespacho_R;
import Components.Panel_Fecha_E;
import Components.Panel_Fecha_R;
import Components.PopUp_Comentario;
import Components.PopUp_Organismo;
import Components.PopUp_Producto;
import Connect.DbConnection;
import DAO.CRUD_Clientes;
import DAO.CRUD_Correos;
import DAO.CRUD_Cotizaciones;
import DAO.CRUD_Facturas;
import DAO.CRUD_Guias;
import DAO.CRUD_Permisos;
import DAO.CRUD_Productos;
import DAO.CRUD_Transporte;
import Object.Cliente;
import Object.Conductor;
import Object.Correo;
import Object.Cotizacion;
import Object.Factura;
import Object.Guia;
import Object.Permiso;
import Object.Producto;
import Object.Transporte;
import Object.User;
import static Procedure.Functions.validarArchivos;
import Views.Load_Dialog;
import Views.Main;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import net.coderazzi.filters.gui.AutoChoices;
import net.coderazzi.filters.gui.TableFilterHeader;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Jason
 */
public class Procedures {

    public static final String dir = System.getProperty("user.dir");
    public static DbConnection conex;

    public static void crearCarpeta(String name) {
        File fileCarpeta = new File(dir + "\\" + name);
        if (!fileCarpeta.exists()) {
            new File(dir + "\\" + name).mkdir();
        }
    }

    public static void conectarBD() {

        try {
            conex = new DbConnection();
            Thread thread = new Thread(() -> {
                while (true) {

                    try {
                        if (conex.getConnection().isClosed()) {
                            conex = new DbConnection();
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Procedures.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(Procedures.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Procedures.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            thread.start();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println("Desconectando...");
                        conex.desconectar();
                    } catch (SQLException ex) {
                        Logger.getLogger(Procedures.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(Procedures.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //------------------------- LLENAR TABLAS -------------------------------------------------
    public static void procedimientoCotizaciones(JTable table, int flag) {

        ArrayList<Cotizacion> cotizaciones = CRUD_Cotizaciones.getCotizaciones();

        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        Object[] fila = new Object[modelo.getColumnCount()];
        DecimalFormat df = new DecimalFormat("$###,###");

        for (int i = 0; i < cotizaciones.size(); i++) {
            Cotizacion c = cotizaciones.get(i);

            fila[0] = c.getCotizacion();

            if (c.getFechaCierre() != null) {
                fila[1] = Functions.cambiarFormatoFechaOC(c.getFechaCierre());
            } else {
                fila[1] = "";
            }

            fila[2] = c.getNumOc();
            fila[3] = Functions.cambiarFormatoFechaOC(c.getFechaOc());

            if (c.getFechaT() != null) {
                fila[4] = Functions.stringToDateFechaTermino(Functions.cambiarFormatoFechaOC(c.getFechaT()));
            } else {
                fila[4] = "";
            }

            if (c.getFechaOc() != null && c.getFechaT() != null) {
                fila[5] = Functions.sumDias(c.getFechaOc(), c.getFechaT());
            } else {
                fila[5] = 0;
            }

            fila[6] = df.format(Integer.valueOf(c.getValor()));
            fila[7] = c.getGuia();

            modelo.addRow(fila);
        }
        table.setModel(modelo);
        renderCotizacion(table);
        if (flag == 0) {
            agregarFiltro(table);
        }

    }

    public static void procedimientoGuias(JTable table, int flag) {
        ArrayList<Guia> guias = CRUD_Guias.getGuias();
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        Object[] fila = new Object[modelo.getColumnCount()];

        for (int i = 0; i < guias.size(); i++) {
            Guia g = guias.get(i);

            fila[0] = g.getGuia();

            if (g.getFecha() != null) {
                fila[1] = g.getFecha();
            } else {
                fila[1] = "";
            }
            fila[2] = g.getEntregado() != 0;
            fila[3] = g.getComprar() != 0;

            if (g.getComentario() != null) {
                fila[4] = g.getComentario();
            } else {
                fila[4] = "";
            }
            if (g.getStatusOC() != null) {
                fila[5] = g.getStatusOC();
            } else {
                fila[5] = "0";
            }
            fila[6] = g.getEnviadoT() != 0;
            fila[7] = g.getEstadofactura() != 0;

            modelo.addRow(fila);
        }
        table.setModel(modelo);
        renderGuias(table);
        if (flag == 0) {
            agregarFiltro(table);
        }

    }

    public static void procedimientoTransporte(JTable table, int flag) {
        ArrayList<Transporte> transitos = CRUD_Transporte.getTransitos();
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        Object[] fila = new Object[modelo.getColumnCount()];

        for (int i = 0; i < transitos.size(); i++) {
            Transporte t = transitos.get(i);

            fila[0] = t.getNumGuia();
            if (t.getDir() != null) {
                fila[1] = t.getDir();
            } else {
                fila[1] = "";
            }
            fila[2] = t.getTransito() != 1;

            fila[3] = t.getNombre();
            fila[4] = t.getPatente();
            if (t.getFecha() != null) {
                fila[5] = Functions.formatFecha(t.getFecha());
            } else {
                fila[5] = "";
            }

            modelo.addRow(fila);
        }
        table.setModel(modelo);
        renderTransporte(table);
        if (flag == 0) {
            agregarFiltro(table);
        }
    }

    public static void procedimientoProductos(JTable table, int guia, JTable tableMain) {

        ArrayList<Producto> arrProductos = CRUD_Productos.getProductos(guia);
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        Object[] fila = new Object[modelo.getColumnCount()];
        for (int i = 0; i < arrProductos.size(); i++) {
            Producto p = arrProductos.get(i);

            fila[0] = p.getCodigo();
            fila[1] = p.getProducto();
            fila[2] = p.getCantidad();
            fila[3] = p.getValorNeto();
            fila[4] = p.getComprar() != 0;
            modelo.addRow(fila);
        }
        table.setModel(modelo);
        renderProductos(table, tableMain);
        agregarFiltro(table);
    }

    public static void procedimientoClientes(JTable table) {
        ArrayList<Cliente> clientes = CRUD_Clientes.getClientes();
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        Object[] fila = new Object[modelo.getColumnCount()];
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);

            fila[0] = c.getCliente();
            fila[1] = Functions.formatearRut(c.getRut());
            fila[2] = 0;
            modelo.addRow(fila);
        }
        table.setModel(modelo);
        renderClientes(table);
        agregarFiltro(table);
    }

    public static void procedimientoCorreos(JTable table) {
        ArrayList<Correo> correos = CRUD_Correos.getCorreos();
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        Object[] fila = new Object[modelo.getColumnCount()];
        for (int i = 0; i < correos.size(); i++) {
            Correo c = correos.get(i);

            fila[0] = c.getMail();
            fila[1] = c.getCliente();
            fila[2] = Functions.formatearRut(c.getRut());
            fila[3] = c.getSeleccion() != 0;
            fila[4] = 0;
            modelo.addRow(fila);
        }
        table.setModel(modelo);
        renderCorreos(table);
        agregarFiltro(table);
    }

    public static void procedimientoFacturas(JTable table, int flag, User user) {
        ArrayList<Factura> facturas = CRUD_Facturas.getFacturas();
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        Object[] fila = new Object[modelo.getColumnCount()];
        for (int i = 0; i < facturas.size(); i++) {
            Factura f = facturas.get(i);

            fila[0] = f.getGuia();
            fila[1] = f.getNumOc();
            fila[2] = f.getFactura();
            fila[3] = f.getCliente();
            modelo.addRow(fila);
        }
        table.setModel(modelo);
        renderFacturas(table, user);
        if (flag == 0) {
            agregarFiltro(table);
        }

    }

    public static void procedimientoPermisos(JTable table) {

        ArrayList<Permiso> usuariosPermiso = CRUD_Permisos.getUsuariosPermiso();
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        Object[] fila = new Object[modelo.getColumnCount()];

        for (int i = 0; i < usuariosPermiso.size(); i++) {
            Permiso p = usuariosPermiso.get(i);

            fila[0] = p.getNombre();
            fila[1] = p.isAdmini();
            fila[2] = p.isCoti();
            fila[3] = p.isGuias();
            fila[4] = p.isDespacho();
            fila[5] = p.isFactura();

            modelo.addRow(fila);
        }
        table.setModel(modelo);
        renderPermiso(table);

    }

    public static void renderProductos(JTable table, JTable tableMain) {

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(JLabel.CENTER);

        table.getColumn("Codigo").setCellRenderer(render);
        table.getColumn("Producto").setCellRenderer(render);
        table.getColumn("Precio").setCellRenderer(render);
        table.getColumn("Cantidad").setCellRenderer(render);

        table.getColumn("Comprar").setCellEditor(new CheckBox_CompraProd_E(table, tableMain));
        table.getColumn("Comprar").setCellRenderer(new CheckBox_CompraProd_R());

        table.setRowHeight(25);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
    }

    public static void renderTransporte(JTable table) {
        DefaultTableCellRenderer render = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                cell.setBackground(new Color(255, 204, 51));
                cell.setForeground(Color.BLACK);
                return cell;
            }
        };
        render.setHorizontalAlignment(JLabel.CENTER);

        table.getColumn("Guia").setCellRenderer(render);
        table.getColumn("Direccion").setCellRenderer(render);
        table.getColumn("Transito").setCellRenderer(render);
        table.getColumn("Conductor").setCellRenderer(render);
        table.getColumn("Vehiculo").setCellRenderer(render);
        table.getColumn("Fecha").setCellRenderer(render);

        table.setRowHeight(25);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);

//          TRANSITO
        table.getColumn("Transito").setCellRenderer(new CheckBox_Transito_R());
        table.getColumn("Transito").setCellEditor(new CheckBox_Transito_E(table));
    }

    public static void renderGuias(JTable table) {
        DefaultTableCellRenderer render = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                pintarPanel(cell, table, row);
                return cell;
            }
        };
        render.setHorizontalAlignment(JLabel.CENTER);

        table.getColumn("Guia").setCellRenderer(render);
        table.getColumn("Fecha").setCellRenderer(render);
        table.getColumn("Comentario").setCellRenderer(render);

        table.setRowHeight(25);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);

        table.getColumn("Guia").setCellEditor(new PopUp_Producto(table));

        table.getColumn("Comentario").setCellEditor(new PopUp_Comentario(table));

        table.getColumn("Tipo Despacho").setCellRenderer(new ComboBox_TipoDespacho_R());
        table.getColumn("Tipo Despacho").setCellEditor(new ComboBox_TipoDespacho_E(table));

        table.getColumn("Entregado").setCellRenderer(new CheckBox_Entregado_R());
        table.getColumn("Entregado").setCellEditor(new CheckBox_Entregado_E(table));
        table.getColumn("Comprar").setCellRenderer(new CheckBox_Compra_R());
        table.getColumn("Comprar").setCellEditor(new CheckBox_Compra_E(table));
        table.getColumn("En Despacho").setCellRenderer(new CheckBox_Despacho_R());
        table.getColumn("En Despacho").setCellEditor(new CheckBox_Despacho_E(table));
        table.getColumn("Facturar").setCellRenderer(new CheckBox_Factura_R());
        table.getColumn("Facturar").setCellEditor(new CheckBox_Factura_E(table));

    }

    public static void renderCotizacion(JTable table) {
        DefaultTableCellRenderer Renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String guia = table.getValueAt(row, table.getColumn("Guia").getModelIndex()).toString();

                if (guia.equals("0")) {
                    cell.setBackground(new Color(245, 120, 120));
                } else {
                    cell.setBackground(new Color(255, 198, 153));
                }
                cell.setForeground(Color.BLACK);
                return cell;
            }
        };
        Renderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(table.getColumn("Guia").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Cotización").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("F.Cierre").getModelIndex()).setCellRenderer(Renderer);
//        table.getColumnModel().getColumn(table.getColumn("F.Termino").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Días Comp.").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("OC MP").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("F.OC").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Valor").getModelIndex()).setCellRenderer(Renderer);

        table.getColumn("Cotización").setCellEditor(new PopUp_Organismo(table));

        table.getColumn("F.Termino").setCellEditor(new Panel_Fecha_E(table));
        table.getColumn("F.Termino").setCellRenderer(new Panel_Fecha_R());

        table.setRowHeight(25);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
    }

    public static void renderClientes(JTable table) {
        DefaultTableCellRenderer Renderer = new DefaultTableCellRenderer();
        Renderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(table.getColumn("Cliente").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Rut").getModelIndex()).setCellRenderer(Renderer);

        table.getColumn("Eliminar").setCellRenderer(new Button_Eliminar_Cliente_R());
        table.getColumn("Eliminar").setCellEditor(new Button_Eliminar_Cliente_E(table, 0));

        table.setRowHeight(25);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
    }

    public static void renderCorreos(JTable table) {
        DefaultTableCellRenderer Renderer = new DefaultTableCellRenderer();
        Renderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(table.getColumn("Correo").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Cliente").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Rut").getModelIndex()).setCellRenderer(Renderer);

        table.getColumn("Seleccion").setCellRenderer(new CheckBox_Correos_R());
        table.getColumn("Seleccion").setCellEditor(new CheckBox_Correos_E(table));

        table.getColumn("Eliminar").setCellRenderer(new Button_Eliminar_Cliente_R());
        table.getColumn("Eliminar").setCellEditor(new Button_Eliminar_Cliente_E(table, 1));

        table.setRowHeight(25);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
    }

    public static void renderFacturas(JTable table, User user) {
        DefaultTableCellRenderer Renderer = new DefaultTableCellRenderer();
        Renderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(table.getColumn("Guia").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("OC").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Factura").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Cliente").getModelIndex()).setCellRenderer(Renderer);

        table.getColumn("Enviar").setCellRenderer(new Button_Enviar_Cliente_R(user));
        table.getColumn("Enviar").setCellEditor(new Button_Enviar_Cliente_E(table, user));

        table.setRowHeight(25);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
    }

    public static void renderPermiso(JTable table) {
        DefaultTableCellRenderer Renderer = new DefaultTableCellRenderer();
        Renderer.setHorizontalAlignment(JLabel.CENTER);
        String[] columnas = {"Administracion", "Cotizacion", "Guias", "Despacho", "Factura"};

        table.getColumnModel().getColumn(table.getColumn("Nombre").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Administracion").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Cotizacion").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Guias").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Despacho").getModelIndex()).setCellRenderer(Renderer);
        table.getColumnModel().getColumn(table.getColumn("Factura").getModelIndex()).setCellRenderer(Renderer);

        for (String columna : columnas) {
            TableColumn column = table.getColumnModel().getColumn(table.getColumn(columna).getModelIndex());
            column.setCellEditor(table.getDefaultEditor(Boolean.class));
            column.setCellRenderer(table.getDefaultRenderer(Boolean.class));
        }

        table.setRowHeight(25);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
    }

    //-----------------------------------------------------------------------------------------
    public static void pintarPanel(Component componente, JTable table, int row) {
        String comentario = table.getValueAt(row, table.getColumn("Comentario").getModelIndex()).toString();
        String entregado = table.getValueAt(row, table.getColumn("Entregado").getModelIndex()).toString();
        String transporte = table.getValueAt(row, table.getColumn("En Despacho").getModelIndex()).toString();

        if (entregado.equals("true")) {
            //VERDE
            componente.setBackground(new Color(77, 255, 106));
        } else if (transporte.equals("true")) {
            //AMARILLO
            componente.setBackground(new Color(255, 204, 51));
        } else if (!comentario.equals("")) {
            //MORADO
            componente.setBackground(new Color(255, 77, 255));
        } else {
            //BEIGE
            componente.setBackground(new Color(255, 198, 153));
        }
        componente.setForeground(Color.BLACK);

    }

    public static void pintarPanelFechaTermino(Component cell, JTable table, int row) {

        String guia = table.getValueAt(row, table.getColumn("Guia").getModelIndex()).toString();

        if (guia.equals("0")) {
            cell.setBackground(new Color(245, 120, 120));
        } else {
            cell.setBackground(new Color(255, 198, 153));
        }
        cell.setForeground(Color.BLACK);

    }

    public static void agregarFiltro(JTable table) {
        try {
            TableFilterHeader filterheader = new TableFilterHeader(table, AutoChoices.ENABLED);
        } catch (Exception ex) {

        }
    }

    public static void llenarComboBoxCotizaciones(JComboBox j) {
        try {
            ArrayList<Integer> arrCotis = CRUD_Cotizaciones.getCotizacionesSinGuia();
            for (Integer coti : arrCotis) {
                j.addItem(coti);
            }
            AutoCompletion.enable(j);
        } catch (Exception e) {
            System.out.println("ComboBox" + e);
        }
    }

    public static void llenarComboBoxGuias(JComboBox j) {
        try {
            ArrayList<Integer> guiasConTransporte = CRUD_Transporte.getGuiasConTransporte();
            for (Integer guias : guiasConTransporte) {
                j.addItem(guias);
            }
            AutoCompletion.enable(j);
        } catch (Exception e) {
            System.out.println("ComboBox Guias" + e);
        }
    }

    public static void llenarComboBoxGuiasFacturas(JComboBox j) {
        try {
            ArrayList<Integer> guiasEntregadas = CRUD_Transporte.getGuiasEntregadas();
            for (Integer guias : guiasEntregadas) {
                j.addItem(guias);
            }
            AutoCompletion.enable(j);
        } catch (Exception e) {
            System.out.println("ComboBox Guias" + e);
        }
    }

    public static void llenarComboBoxPatentes(JComboBox j, int flag) {
        try {
            ArrayList<Transporte> patentes = CRUD_Transporte.getPatentes();

            for (Transporte t : patentes) {
                j.addItem(t.getPatente());
            }
            if (flag == 0) {
                AutoCompletion.enable(j);
            }

        } catch (Exception e) {
            System.out.println("ComboBox Guias" + e);
        }
    }

    public static void llenarComboBoxNombres(JComboBox j, int flag) {
        try {
            ArrayList<Conductor> nombresConductores = CRUD_Transporte.getNombresConductores();

            for (Conductor c : nombresConductores) {
                j.addItem(c.getNombre());
            }
            if (flag == 0) {
                AutoCompletion.enable(j);
            }

        } catch (Exception e) {
            System.out.println("ComboBox Guias" + e);
        }
    }

    public static void llenarComboBoxClientes(JComboBox j) {
        try {
            ArrayList<String> clientes = CRUD_Clientes.getNombresClientes();

            for (String nombreCliente : clientes) {
                j.addItem(nombreCliente);
            }
            AutoCompletion.enable(j);
        } catch (Exception e) {
            System.out.println("ComboBox Guias" + e);
        }
    }

    public static void llenarComboBoxCorreos(JComboBox jClientes, JComboBox jRut) {
        try {
            ArrayList<Cliente> clientes = CRUD_Clientes.getClientes();
            for (Cliente c : clientes) {
                jClientes.addItem(c.getCliente());
                jRut.addItem(Functions.formatearRut(c.getRut()));
            }
            AutoCompletion.enable(jClientes);
            AutoCompletion.enable(jRut);

        } catch (Exception e) {
            System.out.println("ComboBox Correos " + e);
        }
    }

    public static void setAsignarConductorAGuia(JTable table, String guia, String patente, String nombre) {

        for (int i = 0; i < table.getRowCount(); i++) {
            String guiaTabla = table.getValueAt(i, 0).toString();
            if (guia.equals(guiaTabla)) {
                table.setValueAt(patente, i, 4);
                table.setValueAt(nombre, i, 3);
                break;
            }
        }

        int idPatente = CRUD_Transporte.getIdPatente(patente, nombre);
        if (CRUD_Guias.setTransporte(idPatente, Integer.parseInt(guia))) {
            JOptionPane.showConfirmDialog(Main.frame, "¡ Vehiculo asignado con exito !", "LOGRADO", JOptionPane.DEFAULT_OPTION, 1, Functions.imagenCheck());
        } else {
            JOptionPane.showMessageDialog(Main.frame, "No se pudo asignar la el vehiculo a la Guia N°" + guia, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void setNombreConductor(String patente, JTextField textfield) {
        ArrayList<Transporte> patentes = CRUD_Transporte.getPatentes();
        for (Transporte t : patentes) {
            if (patente.equals(t.getPatente())) {
                textfield.setText(t.getNombre());
                break;
            }
        }

    }

    public static void agregarGuia(String guia, String coti, JTable table, JTable tableCoti) {

        Load_Dialog l = new Load_Dialog(Main.frame, false);
        l.jLabel2.setText("Obteniendo Información de Guia N°" + guia);
        l.setLocationRelativeTo(Main.frame);

        /*
                        MODIFICADO
        Se eliminaron los IF anidados para una mejor lectura.
        Se crea la funcion messageError, para no repetir codigo.
            Ideal repetir proceso de IF para las demas funciones
         */
        CompletableFuture.runAsync(() -> {
            l.setVisible(true);
            Guia g = Functions.getGuia(guia);
            l.jLabel2.setText("Ingresando Guia N°" + guia);
            esperar(2000);
            if (g.getGuia() <= 0) {
                l.dispose();
                messageError("Guia N°" + guia + " invalida");
                return;
            }
            if (!CRUD_Guias.ingresarGuia(g)) {
                l.dispose();
                messageError("No se pudo registrar la Guia N°" + guia);
                return;
            }
            if (!CRUD_Productos.ingresarProductos(g.getDetalle(), g.getGuia())) {
                l.dispose();
                messageError("No se pudo registrar los productos de la Guia N°" + guia);
                return;
            }
            if (!CRUD_Cotizaciones.setGuiaCotizacion(Integer.parseInt(coti), Integer.parseInt(guia))) {
                l.dispose();
                messageError("No se pudo parear la Guia N°" + guia + " con la Cotización " + coti);
                return;
            }

            l.dispose();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] agregar = Functions.agregarGuia(g);
            model.addRow(agregar);
            table.repaint();
            Procedures.setGuiaTablaCoti(coti, tableCoti, g.getGuia());

            JOptionPane.showConfirmDialog(Main.frame, "¡ Guia N°" + guia + " Registrada con exito !", "LOGRADO", JOptionPane.DEFAULT_OPTION, 1, Functions.imagenCheck());
        });
    }

    public static void messageError(String message) {
        JOptionPane.showMessageDialog(Main.frame, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public static void esperar(int miliSeg) {
        try {
            Thread.sleep(miliSeg);
        } catch (InterruptedException ex) {
            Logger.getLogger(Procedures.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setGuiaTablaCoti(String coti, JTable table, int guia) {

        int columna = table.getColumn("Cotización").getModelIndex();
        int columnGuia = table.getColumn("Guia").getModelIndex();

        for (int i = 0; i < table.getRowCount(); i++) {
            String cotizacion = table.getValueAt(i, columna).toString();
            if (cotizacion.equals(coti)) {
                table.setValueAt(guia, i, columnGuia);
                break;
            }
        }
    }

//--------------------------    PROCEDIMIENTO FACTURA   --------------------------------------------
    public static void procedimientoDescargarFactura(String factura, String guia, String oc, String cliente) {
        String rutaDescarga = dir + "\\Facturas";
        ArrayList<String> arrCorreos = CRUD_Correos.getCorreosSeleccionados(cliente);
        if (arrCorreos.isEmpty()) {
            JOptionPane.showMessageDialog(Main.frame, "El CLIENTE " + cliente + " NO TIENE CORREOS ACTIVOS", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            irPorArchivosFactura(factura, rutaDescarga, guia, oc, arrCorreos);
        }

    }

    public static void irPorArchivosFactura(String factura, String rutaDescarga, String guia, String oc, ArrayList<String> arrCorreos) {
        Load_Dialog l = new Load_Dialog(Main.frame, false);
        l.jLabel2.setText("Descargando Factura N°" + factura);
        l.setLocationRelativeTo(Main.frame);
        CompletableFuture.runAsync(() -> {
            l.setVisible(true);
            descargarArchivosFactura(factura, rutaDescarga);
            esperar(2000);
            l.jLabel2.setText("Validando descarga de archivos de la Factura N°" + factura);
            if (validarArchivos(rutaDescarga, factura, guia)) {
                l.jLabel2.setText("Preparando archivos para enviar");
                if (Functions.enviar(factura, oc, arrCorreos)) {
                    JOptionPane.showConfirmDialog(Main.frame, "¡ Correo Enviado  !", "ENVIADO", JOptionPane.DEFAULT_OPTION, 1, Functions.imagenCheck());
                    l.dispose();
                } else {
                    l.dispose();
                    JOptionPane.showMessageDialog(Main.frame, "No se logro enviar el correo", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                l.dispose();
                JOptionPane.showMessageDialog(Main.frame, "No se pudieron descargar todos los archivos realcionado con la Factura N°" + factura, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void descargarArchivosFactura(String factura, String rutaDescarga) {

        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("plugins.always_open_pdf_externally", true);
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", rutaDescarga);
        chromePrefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--savebrowsing-disable-download-protection");
        options.addArguments("--host-resolver-rules=MAP www.google-analytics.com 127.0.0.1");

//        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("prefs", chromePrefs);
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        String ruta = "http://192.168.5.216/login/index.php?session=1";
        driver.get(ruta);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("username")));
        driver.findElement(By.id("username")).sendKeys("76008058-6");

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("password")));
        driver.findElement(By.id("password")).sendKeys("76008");

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("div_btn_submit")));
        driver.findElement(By.id("div_btn_submit")).submit();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        String fecha2 = dtf.format(now);
        String[] split = fecha2.split("-");
        String fecha1 = split[0] + "-" + split[1] + "-" + (Integer.parseInt(split[2]) - 5);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("mantenedor_formulario_tido_id")));
        Select select = new Select(driver.findElement(By.id("mantenedor_formulario_tido_id")));
        select.selectByIndex(4);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("mantenedor_formulario_docu_fecha_emision__desde")));
        driver.findElement(By.id("mantenedor_formulario_docu_fecha_emision__desde")).clear();
        driver.findElement(By.id("mantenedor_formulario_docu_fecha_emision__desde")).sendKeys(fecha1);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("mantenedor_formulario_docu_fecha_emision__hasta")));
        driver.findElement(By.id("mantenedor_formulario_docu_fecha_emision__hasta")).clear();
        driver.findElement(By.id("mantenedor_formulario_docu_fecha_emision__hasta")).sendKeys(fecha2);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("mantenedor_formulario_docu_folio__desde")));
        driver.findElement(By.id("mantenedor_formulario_docu_folio__desde")).clear();
        driver.findElement(By.id("mantenedor_formulario_docu_folio__desde")).sendKeys(factura);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("mantenedor_formulario_docu_folio__hasta")));
        driver.findElement(By.id("mantenedor_formulario_docu_folio__hasta")).clear();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("mantenedor_formulario_docu_folio__hasta")));
        driver.findElement(By.id("mantenedor_formulario_docu_folio__hasta")).clear();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("div_btn_2")));
        WebElement element = driver.findElement(By.id("div_btn_2"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"yui-dt0-bdrow0-cell9\"]/div/a[2]")));
        driver.findElement(By.xpath("//*[@id=\"yui-dt0-bdrow0-cell9\"]/div/a[2]")).click();

        esperarDescargarArchivo(rutaDescarga);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div[2]/table/tbody[2]/tr/td[10]/div/a[1]")));
        String attribute = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div[2]/table/tbody[2]/tr/td[10]/div/a[1]")).getAttribute("href");

        System.out.println("attribute " + attribute);

        driver.get(attribute);

        String text1 = driver.findElement(By.tagName("body")).getText();

        driver.close();
        driver.quit();

        procedimientoArchivo(rutaDescarga, factura, text1);

    }

    private static void procedimientoArchivo(String rutaDescarga, String factura, String text) {
        crearXML(factura);
        writeXML(text, factura);
        cambiarNombre(rutaDescarga, factura);
    }

    public static void esperarDescargarArchivo(String ruta) {
        File carpetaFacturas = new File(ruta);
        boolean bool = false;
        while (bool) {
            String[] list = carpetaFacturas.list();
            if (list.length > 1) {
                bool = true;
            }
            esperar(1000);
        }

    }

    public static void esperarPegarGuia(String rutaDescarga) {
        File carpetaFacturas = new File(rutaDescarga);
        boolean bool = false;
        while (bool) {
            String[] list = carpetaFacturas.list();
            if (list.length > 0) {
                bool = true;
            }
            esperar(1000);
        }
    }

    public static void limpiarCarpetaFacturas(String ruta) {

        File carpetaFacturas = new File(ruta);
        do {
            try {
                FileUtils.forceDelete(carpetaFacturas);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        } while (carpetaFacturas.exists());
    }

    private static void crearXML(String factura) {
        try {
            File myObj = new File(dir + "\\Facturas\\" + factura + ".xml");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    private static void writeXML(String text, String factura) {
        try {
            try ( FileWriter myWriter = new FileWriter(dir + "\\Facturas\\" + factura + ".xml")) {
                myWriter.write(text);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    private static void cambiarNombre(String ruta, String factura) {
        File facturaFold = new File(ruta);
        String[] list = facturaFold.list();
        for (String name : list) {
            if (name.contains("76008058")) {
                File pdf = new File(ruta + "\\" + name);
                pdf.renameTo(new File(ruta + "\\" + factura + ".pdf"));
                break;
            }
        }
    }

    public static void cambiarNombreGuia(String ruta, String guia) {
        File facturaFold = new File(ruta);
        String[] list = facturaFold.list();
        for (String name : list) {
            if (name.contains(".pdf")) {
                File pdf = new File(ruta + "\\" + name);
                pdf.renameTo(new File(ruta + "\\" + guia + ".pdf"));
                break;
            }
        }
    }

    public static void filtro(Date startDate, Date endDate, JTable table, JLabel label) {

        LocalDate date = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate minusDays = date.minusDays(1);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date datex = Date.from(minusDays.atStartOfDay(defaultZoneId).toInstant());

        LocalDate dateEnd = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate plusDays = dateEnd.plusDays(1);
        Date datexEnd = Date.from(plusDays.atStartOfDay(defaultZoneId).toInstant());

        List<RowFilter<Object, Object>> filters = new ArrayList<>(2);
        filters.add(RowFilter.dateFilter(ComparisonType.AFTER, datex));
        filters.add(RowFilter.dateFilter(ComparisonType.BEFORE, datexEnd));

        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dtm);
        table.setRowSorter(tr);
        RowFilter<Object, Object> rf = RowFilter.andFilter(filters);
        tr.setRowFilter(rf);
        label.setText(Functions.sumValores(table));

    }

    public static void quitarFiltro(JTable table) {

        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dtm);
        tr.setSortable(0, false);
        table.setRowSorter(null);

    }

    public static void exportDataToExcel(JTable table, String nombre) {
        String ruta = nombre + ".xlsx";

        try {
            final File archivoXLS = new File(ruta);
            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();

            final Workbook libro = new XSSFWorkbook();
            try ( FileOutputStream archivo = new FileOutputStream(archivoXLS)) {

                final Sheet hoja = libro.createSheet(nombre);
                hoja.setDisplayGridlines(true);
                final org.apache.poi.ss.usermodel.Font headerFont = libro.createFont();
                headerFont.setColor(IndexedColors.WHITE.index);
                final CellStyle headerCellStyle = hoja.getWorkbook().createCellStyle();
                headerCellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
                headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                headerCellStyle.setFont(headerFont);
                final int rowCount = table.getRowCount();

                System.out.println("rowCount " + rowCount);

                int columTable = table.getColumnCount();
                if (nombre.equals("Facturacion")) {
                    columTable = table.getColumnCount() - 1;
                }

                for (int f = 0; f < table.getRowCount(); ++f) {
                    final Row fila = hoja.createRow(f);
                    for (int c = 0; c < columTable; ++c) {
                        final Cell celda = fila.createCell(c);
                        if (f == 0) {
                            celda.setCellStyle(headerCellStyle);
                            celda.setCellValue(table.getColumnName(c));
                        }
                    }
                }

                int filaInicio = 1;
                for (int f2 = 0; f2 < table.getRowCount(); ++f2) {
                    final Row fila2 = hoja.createRow(filaInicio);
                    ++filaInicio;
                    for (int c2 = 0; c2 < columTable; ++c2) {
                        final Cell celda2 = fila2.createCell(c2);
                        if (table.getValueAt(f2, c2) instanceof Double) {
                            celda2.setCellValue(Double.parseDouble(table.getValueAt(f2, c2).toString()));
                        } else if (table.getValueAt(f2, c2) instanceof Float) {
                            celda2.setCellValue(Float.parseFloat(table.getValueAt(f2, c2).toString()));
                        } else if (table.getValueAt(f2, c2) instanceof Boolean) {
                            String bool = "";
                            if ((Boolean) table.getValueAt(f2, c2)) {
                                bool = "Sí";
                            } else {
                                bool = "No";
                            }
                            celda2.setCellValue(bool);
                        } else if (table.getValueAt(f2, c2) instanceof Date) {
                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            String strDate = "";
                            try {
                                strDate = dateFormat.format(table.getValueAt(f2, c2));
                            } catch (Exception ex) {
                            }
                            celda2.setCellValue(strDate);

                        } else {
                            try {
                                celda2.setCellValue(Integer.parseInt(table.getValueAt(f2, c2).toString().replace(".", "")));
                            } catch (NumberFormatException ex) {
                                celda2.setCellValue(table.getValueAt(f2, c2).toString());
                            }
                        }
                    }
                }
                libro.write(archivo);
            }
            Desktop.getDesktop().open(archivoXLS);
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
    }

}
