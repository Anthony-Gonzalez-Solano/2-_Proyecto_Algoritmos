/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.reportes;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import domain.Food;
import domain.Product;
import domain.Restaurant;
import domain.Supermarket;
import domain.list.ListException;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;

/**
 * FXML Controller class
 *
 * @author nicole
 */
public class FXMLReporte_SupermercadosRestaurantesController implements Initializable {

    @FXML
    private AnchorPane ap;
    @FXML
    private BorderPane bp;

    private static String pdfPath;
    private Supermarket supermarket;
    private Restaurant restaurant;
    private Product product;
    private Food food;
    private SwingController swingController;
    private JComponent viewerPanel;
    private util.FileTXT txt;

    @FXML
    private Button btnEnter;
    @FXML
    private TextField TxtFieldStudId;
    @FXML
    private Text putTxt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bp.setVisible(true);
        try {
            createViewer(bp);//se genera el lector, se pone aqui ya que en otro lado genera una exception
            File supeRest = new File("supermercados_restaurantes.txt");

            createPDF(supeRest);//se crea el pdf
//            createViewer(bp);
            openDocument("ReporteSupermercadosRestaurantes.pdf");

        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLReporte_SupermercadosRestaurantesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLReporte_SupermercadosRestaurantesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FXMLReporte_SupermercadosRestaurantesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FXMLReporte_SupermercadosRestaurantesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FXMLReporte_SupermercadosRestaurantesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(FXMLReporte_SupermercadosRestaurantesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLReporte_SupermercadosRestaurantesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ListException ex) {
            Logger.getLogger(FXMLReporte_SupermercadosRestaurantesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLReporte_SupermercadosRestaurantesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createPDF(File newPDF) throws FileNotFoundException, DocumentException, ListException, BadElementException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("ReporteSupermercadosRestaurantes.pdf"));
        String content = "";
        document.open();
        //aqui agregamos imagen al pdf
        Image image = Image.getInstance("src/assets/logoNombre.jpeg");
        image.scaleToFit(250, 250);
        image.setAlignment(Chunk.ALIGN_CENTER);
        document.add(image);
        //aqui se genera el contenido del pdf
        Supermarket sup = null;
        Restaurant rest = null;
        boolean found = false;
        //Tabla de restaurantes con su contenido
        PdfPTable tableRest = new PdfPTable(3);//campos
        tableRest.addCell("Id");
        tableRest.addCell("Nombre");
        tableRest.addCell("Ubicación");
        //Tabla de supermercados con su contenido
        PdfPTable tableSuper = new PdfPTable(3);//campos
        tableSuper.addCell("Id");
        tableSuper.addCell("Nombre");
        tableSuper.addCell("Ubicación");
        //si no hay supermercados o restaurantes se pone un mensaje pero se espera que el usuario no lo vea
        if (util.Utility.getlGraphRestaurants_Supermarkets().isEmpty() == true) {
            content += "No hay supermercados ni restaurantes agregados por el momento\n";
        } else {
            for (int k = 1; k <= util.Utility.getlGraphRestaurants_Supermarkets().size(); k++) {
                rest = (Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(k).data;
                tableRest.addCell(String.valueOf(restaurant.getId()));
                tableRest.addCell(restaurant.getName());
                tableRest.addCell(restaurant.getLocation());
                //content += "\n" + rest.getAutoId() + " " + rest.getName() + " " + rest.getLocation() + " " /*+ rest.getId() + " "*/ + "\n";
                for (int i = 1; i <= util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    sup = (Supermarket) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    tableSuper.addCell(String.valueOf(supermarket.getId()));
                    tableSuper.addCell(supermarket.getName());
                    tableSuper.addCell(supermarket.getLocation());
                    //content += "\n" + sup.getAutoID() + " " + sup.getName() + " " + sup.getLocation() + " " /*+ rest.getId() + " "*/ + "\n";
                }//for supermarket
                document.add(tableSuper);
                if (found == false) {
                    //si no tiene restaurantes ni supermecados se pone este mensaje
                    content += "No existen supermercados ni restaurantes registrados\n";
                }
                found = false;
            }//for restaurant
            document.add(tableRest);
        }
        //se controla el tamaño,tipo y color de la letra
        Paragraph parrafo = new Paragraph("Lista de supermercados y restaurantes \n\n" + content,
                FontFactory.getFont("arial",
                        12,
                        Font.BOLD,
                        BaseColor.BLACK
                ));
        document.add(parrafo);//se agrega el contenido
        //metadatos
        document.addTitle("Lista de cursos retirados");
        document.addKeywords("Java, PDF, Lista de Cursos Retirados");
        document.addAuthor("Projecto 2 Algoritmos");
        document.addCreator("Grupo No.3");
        document.close();
    }

    private void createViewer(BorderPane bp) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException {
        //se usa LookAndFeel para establecer el estilo y color ya que es un componente Java Swing
        ColorUIResource backgroundUI = new ColorUIResource(0x023c4f);
        ColorUIResource textUI = new ColorUIResource(0xFFFAFA);
        ColorUIResource controlBackgroundUI = new ColorUIResource(0x023c4f);
        ColorUIResource infoBackgroundUI = new ColorUIResource(0x023c4f);
        ColorUIResource infoUI = new ColorUIResource(0x023c4f);
        ColorUIResource lightBackgroundUI = new ColorUIResource(0x023c4f);
        ColorUIResource focusUI = new ColorUIResource(0x023c4f);

        UIManager.put("control", backgroundUI);
        UIManager.put("text", textUI);
        UIManager.put("nimbusLightBackground", lightBackgroundUI);
        UIManager.put("info", infoUI);
        UIManager.put("nimbusInfoBlue", infoBackgroundUI);
        UIManager.put("nimbusBase", controlBackgroundUI);

        UIManager.put("nimbusBlueGrey", controlBackgroundUI);
        UIManager.put("nimbusFocus", focusUI);
        for (UIManager.LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(lafInfo.getName())) {
                UIManager.setLookAndFeel(lafInfo.getClassName());
                break;
            }
        }
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    swingController = new SwingController();
                    swingController.setIsEmbeddedComponent(true);
                    PropertiesManager properties = new PropertiesManager(System.getProperties(),
                    ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
                    properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, "false");
                    properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE, "false");
                    properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, "false");
                    properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.25");
                    properties.setBoolean(PropertiesManager.PROPERTY_SHOW_STATUSBAR_VIEWMODE, Boolean.FALSE);
                    properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_PAGENAV, "false");
                    ResourceBundle messageBundle = ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                    new FontPropertiesManager(properties, System.getProperties(), messageBundle);
                    //se genera el lector
                    swingController.getDocumentViewController().setAnnotationCallback(
                            new org.icepdf.ri.common.MyAnnotationCallback(swingController.getDocumentViewController()));
                    SwingViewBuilder factory = new SwingViewBuilder(swingController, properties);
                    viewerPanel = factory.buildViewerPanel();
                    viewerPanel.setForeground(Color.red);
                    factory.buildToolToolBar().setOpaque(true);
                    viewerPanel.revalidate();
                    SwingNode swingNode = new SwingNode();
                    swingNode.setContent(viewerPanel);
                    bp.setCenter(swingNode);
                    swingController.setToolBarVisible(false);
                    swingController.setUtilityPaneVisible(false);
                }
            });
        } catch (InvocationTargetException ex) {
            Logger.getLogger(FXMLReporte_SupermercadosRestaurantesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void openDocument(String document) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                swingController.openDocument(document);
                viewerPanel.revalidate();
            }
        });
    }

}
