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
import domain.list.CircularLinkedList;
import domain.list.ListException;
import domain.tree.BTreeNode;
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
public class FXMLReporte_ProductosComidasController implements Initializable {

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
            File supeRest = new File("productos_comidas.txt");

            createPDF(supeRest);//se crea el pdf
//            createViewer(bp);
            openDocument("ReporteProductosComidas.pdf");

        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLReporte_ProductosComidasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLReporte_ProductosComidasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FXMLReporte_ProductosComidasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FXMLReporte_ProductosComidasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FXMLReporte_ProductosComidasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(FXMLReporte_ProductosComidasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLReporte_ProductosComidasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ListException ex) {
            Logger.getLogger(FXMLReporte_ProductosComidasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLReporte_ProductosComidasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createPDF(File newPDF) throws FileNotFoundException, DocumentException, ListException, BadElementException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("ReporteProductosComidas.pdf"));
        String content = "";
        document.open();
        //aqui agregamos imagen al pdf
        Image image = Image.getInstance("src/assets/logoNombre.jpeg");
        image.scaleToFit(250, 250);
        image.setAlignment(Chunk.ALIGN_CENTER);
        document.add(image);
        //aqui se genera el contenido del pdf
        Product prod = null;
        Food food = null;
        boolean found = false;
        //Tabla de restaurantes con su contenido
        PdfPTable tableProd = new PdfPTable(4);//campos
        tableProd.addCell("Id");
        tableProd.addCell("Nombre");
        tableProd.addCell("Precio");
        tableProd.addCell("Supermercado");
        //Tabla de supermercados con su contenido
        PdfPTable tableFood = new PdfPTable(4);//campos
        tableFood.addCell("Id");
        tableFood.addCell("Nombre");
        tableFood.addCell("Precio");
        tableFood.addCell("Restaurante");
        //instancias para recorrer los arboles de product y food
        CircularLinkedList listProduct = new CircularLinkedList();
        CircularLinkedList finalListProduct = fillTreeTable(util.Utility.getTreeProducts().getRoot(), listProduct);
        CircularLinkedList listFood = new CircularLinkedList();
        CircularLinkedList finalListFood = fillTreeTable(util.Utility.getTreeFood().getRoot(), listFood);
        //si no hay supermercados o restaurantes se pone un mensaje pero se espera que el usuario no lo vea
        if (util.Utility.getTreeProducts().isEmpty() == true) {
            content += "No hay productos agregados por el momento\n";
        } else {
            Supermarket sp=null;
            for (int k = 1; k <= finalListProduct.size(); k++) {
                prod = (Product) finalListProduct.getNode(k).data;
                tableProd.addCell(String.valueOf(prod.getID()));
                tableProd.addCell(prod.getName());
                tableProd.addCell(String.valueOf(prod.getPrice()));
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Supermarket){
                        sp=(Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                            if(sp.getId()==prod.getSupermarketID())
                                tableProd.addCell(sp.getName());//se agrega nombre de super
                    }
                }
                
            }//for restaurant
            tableProd.completeRow();//las tablas hechas en itext ignoran filas que no esten completas
            document.add(tableProd);
            
            if (found == false) {
                //si no tiene productos se pone este mensaje
                content += "No hay registro de productos\n";
            }
            found = false;
            //document.add(tableProd);
        }
        if (util.Utility.getTreeFood().isEmpty() == true) {
            content += "No hay comidas agregadas por el momento\n";
        } else {
            for (int i = 1; i <= finalListFood.size(); i++) {
                food = (Food) finalListFood.getNode(i).data;
                // tableFood.addCell(String.valueOf(food.getId()));
                tableFood.addCell(food.getName());
                tableFood.addCell(String.valueOf(food.getPrice()));
                tableFood.addCell(restaurant.getName());//revisar
            }//for restaurant    
            document.add(tableFood);
            if (found == false) {
                //si no tiene restaurantes ni supermecados se pone este mensaje
                content += "No hay registro de comidas\n";
            }
            found = false;
            //document.add(tableProd);
        }
        //se controla el tamaño,tipo y color de la letra
        Paragraph parrafo = new Paragraph("Lista de productos y comidas \n\n" + content,
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
            Logger.getLogger(FXMLReporte_ProductosComidasController.class.getName()).log(Level.SEVERE, null, ex);
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

    private CircularLinkedList fillTreeTable(BTreeNode node, CircularLinkedList circularList) {
        if (node != null) {
            fillTreeTable(node.left, circularList);
            if (node.data instanceof Product) {
                Product product = (Product) node.data;
                circularList.add(product);
            } else {
                Food f = (Food) node.data;
                circularList.add(f);
            }
            fillTreeTable(node.right, circularList);
        }
        return circularList;
    }

}
