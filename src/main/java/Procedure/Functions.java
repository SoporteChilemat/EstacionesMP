/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Procedure;

import Object.Cliente;
import Object.Correo;
import Object.Guia;
import Object.Producto;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.awt.Image;
import java.io.File;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Jason
 */
public class Functions {

    public static int sumDiasDate(Date startDate, Date endDate) {

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        int cont = 0;
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            cont++;
        }
        return cont;
    }

    public static int sumDias(String inicio, String termino) {
        int cont = 0;
        if (!inicio.equals("") && !termino.equals("")) {
            try {
                inicio = cambiarFormatoFechaOC(inicio);
                termino = cambiarFormatoFechaOC(termino);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date startDate = formatter.parse(inicio);
                Date endDate = formatter.parse(termino);

                Calendar start = Calendar.getInstance();
                start.setTime(startDate);
                Calendar end = Calendar.getInstance();
                end.setTime(endDate);

                for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                    cont++;
                }

            } catch (ParseException ex) {
                Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cont;
    }

    public static String cambiarFormatoFechaOC(String fecha) {
        String invertida = "";
        if (fecha.contains(" ")) {
            fecha = fecha.substring(0, fecha.indexOf(" ")).trim();
        }
        int indexOf = fecha.indexOf("-");
        if (indexOf == 4) {
            String[] split = fecha.split("-");
            invertida = split[2] + "-" + split[1] + "-" + split[0];
            fecha = invertida;
        }

        return fecha;
    }

    public static Guia getGuia(String guia) {

        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("plugins.always_open_pdf_externally", false);
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", "");
        chromePrefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--savebrowsing-disable-download-protection");
        options.addArguments("--host-resolver-rules=MAP www.google-analytics.com 127.0.0.1");
        options.addArguments("--host-resolver-rules=MAP www.google-analytics.com 127.0.0.1");

        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
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
        select.selectByIndex(6);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("mantenedor_formulario_docu_fecha_emision__desde")));
        driver.findElement(By.id("mantenedor_formulario_docu_fecha_emision__desde")).clear();
        driver.findElement(By.id("mantenedor_formulario_docu_fecha_emision__desde")).sendKeys(fecha1);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("mantenedor_formulario_docu_fecha_emision__hasta")));
        driver.findElement(By.id("mantenedor_formulario_docu_fecha_emision__hasta")).clear();
        driver.findElement(By.id("mantenedor_formulario_docu_fecha_emision__hasta")).sendKeys(fecha2);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("mantenedor_formulario_docu_folio__desde")));
        driver.findElement(By.id("mantenedor_formulario_docu_folio__desde")).clear();
        driver.findElement(By.id("mantenedor_formulario_docu_folio__desde")).sendKeys(guia);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("mantenedor_formulario_docu_folio__hasta")));
        driver.findElement(By.id("mantenedor_formulario_docu_folio__hasta")).clear();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("div_btn_2")));
        WebElement element = driver.findElement(By.id("div_btn_2"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div[2]/table/tbody[2]/tr/td[10]/div/a[1]")));
        String attribute = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div[2]/table/tbody[2]/tr/td[10]/div/a[1]")).getAttribute("href");

        System.out.println("attribute " + attribute);

        driver.get(attribute);

        String text1 = driver.findElement(By.tagName("body")).getText();
        text1 = text1.replace("This XML file does not appear to have any style information associated with it. The document tree is shown below.", "").trim();
        text1 = text1.replace("&", "Y");

        driver.close();
        driver.quit();

        Document doc = convertStringToDocument(text1);
        Guia g = new Guia();
        if (doc != null) {
            getInfo(doc, g);
            g.setDetalle(getDetalles(doc));
        }

        return g;
    }

    private static Document convertStringToDocument(String xmlStr) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            return doc;
        } catch (Exception e) {
            return null;
        }
    }

    private static ArrayList<Producto> getDetalles(Document doc) {

        ArrayList<Producto> arrProductos = new ArrayList<>();

        NodeList nList = doc.getElementsByTagName("Detalle");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
//            System.out.println("");    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Producto p = new Producto();

                Element eElement = (Element) node;

//                System.out.println("Producto : " + eElement.getElementsByTagName("NmbItem").item(0).getTextContent());
                p.setProducto(eElement.getElementsByTagName("NmbItem").item(0).getTextContent());

//                System.out.println("Cantidad : " + eElement.getElementsByTagName("QtyItem").item(0).getTextContent());
                p.setCantidad(Integer.parseInt(eElement.getElementsByTagName("QtyItem").item(0).getTextContent()));

//                System.out.println("Codigo   : " + eElement.getElementsByTagName("VlrCodigo").item(0).getTextContent());
                p.setCodigo(eElement.getElementsByTagName("VlrCodigo").item(0).getTextContent());

//                System.out.println("Precio Unitario   : " + eElement.getElementsByTagName("PrcItem").item(0).getTextContent());
                p.setValorNeto(Double.parseDouble(eElement.getElementsByTagName("PrcItem").item(0).getTextContent()));

                arrProductos.add(p);
            }
        }
        return arrProductos;
    }

    private static void getInfo(Document doc, Guia g) {

        NodeList nList = doc.getElementsByTagName("IdDoc");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            System.out.println("");    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) node;

//                System.out.println("Producto : " + eElement.getElementsByTagName("Folio").item(0).getTextContent());
                g.setGuia(Integer.parseInt(eElement.getElementsByTagName("Folio").item(0).getTextContent()));

//                System.out.println("Producto : " + eElement.getElementsByTagName("FchEmis").item(0).getTextContent());
                g.setFecha(eElement.getElementsByTagName("FchEmis").item(0).getTextContent());

            }
        }

        nList = doc.getElementsByTagName("Caratula");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            System.out.println("");    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) node;

                String hora = eElement.getElementsByTagName("TmstFirmaEnv").item(0).getTextContent();
                int indexOf = hora.indexOf("T");
                hora = hora.substring(indexOf + 1);
                g.setHora(hora);

            }
        }
    }

    public static Object[] agregarGuia(Guia g) {
        return new Object[]{g.getGuia(), g.getFecha(), false, false, "", 0, false, false};
    }

    public static Object[] agregarCliente(Cliente c) {
        return new Object[]{c.getCliente(), formatearRut(c.getRut())};
    }

    public static Object[] agregarCorreo(Correo c) {
        return new Object[]{c.getMail(), c.getCliente(), formatearRut(c.getRut()), false};
    }

    public static Object[] agregarFactura(String factura, String oc, String guia, String cliente) {
        return new Object[]{factura, oc, guia, cliente};
    }

    public static boolean valdiarCompra(JTable table) {
        int col = table.getColumn("Comprar").getModelIndex();

        for (int i = 0; i < table.getRowCount(); i++) {
            String selec = table.getValueAt(i, col).toString();
            System.out.println(selec);
            if (selec.equals("true")) {
                return true;
            }
        }
        return false;
    }

    public static ImageIcon imagenCheck() {

        ImageIcon icon = new ImageIcon(Procedures.dir + "\\Sources\\check.png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

        return icon;
    }

    public static String getFecha() {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyy hh:mm");
        return dateFormat.format(new Date());
    }

    public static String getFecha2() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm");
        return dateFormat.format(new Date());
    }

    public static String formatFecha(String fecha) {
        try {
            Date date1 = new SimpleDateFormat("ddMMyy hh:mm").parse(fecha);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm");
            return dateFormat.format(date1);
        } catch (ParseException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "01/01/2000";
    }

    public static boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }

    public static String formatearRut(String rut) {
        int cont = 0;
        String format;
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");
        format = "-" + rut.substring(rut.length() - 1);
        for (int i = rut.length() - 2; i >= 0; i--) {
            format = rut.substring(i, i + 1) + format;
            cont++;
            if (cont == 3 && i != 0) {
                format = "." + format;
                cont = 0;
            }
        }
        return format;
    }

    public static boolean validarArchivos(String rutaDescarga, String factura, String guia) {

        File facturaFold = new File(rutaDescarga);
        String[] list = facturaFold.list();
        int cont = 0;

        for (String name : list) {
            if (name.equals(factura + ".pdf")) {
                cont++;
            } else if (name.equals(factura + ".xml")) {
                cont++;
            } else if (name.contains("GUIA " + guia + ".pdf")) {
                cont++;
            }
        }
        return cont == 3;

    }

    public static boolean enviar(String factura, String oc, ArrayList<String> arrCorreos) {

        String mensaje = "";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtppro.zoho.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, null);
        session.setDebug(false);
        try {
            BodyPart texto = new MimeBodyPart();

            mensaje += "Estimado:\n"
                    + "\n"
                    + "Junto con saludar adjunto Factura(s) " + factura + " OC " + oc + "\n"
                    + "\n"
                    + "\n"
                    + "<h3>Para pagos con transferencias depositar en cta cte 6405811-8 Banco Santander Rut:76.008.058-6 Comercial Francisco Toso Ltda enviar comprobante de pago a Pagos@ftoso.cl</h3>"
                    + "\n"
                    + "Atte\n";
            texto.setContent(mensaje, "text/html");
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);

            //agregar GUIA
            String[] pathnames = arrFiles();
            for (String pathname : pathnames) {
                MimeBodyPart adjunto = new MimeBodyPart();
                adjunto.setDataHandler(new DataHandler(new FileDataSource(Procedures.dir + "\\Facturas\\" + pathname)));
                adjunto.setFileName(pathname);
                multiParte.addBodyPart(adjunto);
            }

            MimeMessage message = new MimeMessage(session);
            message.setContent(multiParte);

            message.setFrom(new InternetAddress("Richard Moya - Ferreteria Francisco Toso <robot.descarga@ftoso.cl>"));

            for (String correo : arrCorreos) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
            }
            message.setSubject("Emisión Factura Electrónica / Comercial Francisco Toso Ltda");

            Transport t = session.getTransport("smtp");
            t.connect("robot.descarga@ftoso.cl", "Ferreteria2017*");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static String[] arrFiles() {
        File f = new File(Procedures.dir + "\\Facturas");
        String[] pathnames = f.list();

        return pathnames;
    }

    public static String sumValores(JTable table) {
        long sumaValores = 0;
        DecimalFormat df = new DecimalFormat("$###,###");
        int col = table.getColumn("Valor").getModelIndex();

        for (int i = 0; i < table.getRowCount(); i++) {
            String fechaTermino = table.getValueAt(i, col).toString().replace("$", "").replace(".", "");
            sumaValores += Long.parseLong(fechaTermino);
        }

        return df.format(sumaValores);
    }

    public static Date stringToDateFechaTermino(String fecha) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date startDate = formatter.parse(fecha);
            return startDate;
        } catch (ParseException ex) {
            Logger.getLogger(Procedures.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public static int permiso(boolean permiso) {
        int p = 0;
        if (permiso) {
            p = 1;
        }
        return p;
    }

}
