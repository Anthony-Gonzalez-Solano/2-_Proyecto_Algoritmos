/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.reporte;

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
        image.scaleToFit(200, 200);
        image.setAlignment(Chunk.ALIGN_CENTER);
        document.add(image);
        //aqui el encabezado
        Paragraph encabezado = new Paragraph();
        encabezado.setAlignment(Paragraph.ALIGN_CENTER);
        encabezado.setFont(FontFactory.getFont("Chandas", 16, Font.NORMAL, BaseColor.BLACK));
        encabezado.add("\n\nSherchplit Sistema de Sugerencias de Restaurantes y Supermercados\n\n");
        document.add(encabezado);
        //aqui creamos el pdf
        Paragraph parrafoProductos = new Paragraph();
        parrafoProductos.setAlignment(Paragraph.ALIGN_CENTER);
        parrafoProductos.setFont(FontFactory.getFont("Tahoma", 16, Font.BOLD, BaseColor.BLACK));
        parrafoProductos.add("\n\nRegistro de Productos\n\n");
        document.add(parrafoProductos);
        //aqui agregamos imagen al pdf
        /*Image imageBarra = Image.getInstance("src/assets/barra.jpeg");
        imageBarra.scaleToFit(200, 100);
        imageBarra.setAlignment(Chunk.ALIGN_CENTER);
        document.add(imageBarra);*/
        //aqui se genera el contenido del pdf
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
        //instancias para recorrer los arboles de product y food
        CircularLinkedList listProduct = new CircularLinkedList();
        CircularLinkedList finalListProduct = fillTreeTable(util.Utility.getTreeProducts().getRoot(), listProduct);
        //verificacion y contenido del pdf
        if (!util.Utility.getTreeProducts().isEmpty()) {
            for (int k = 1; k <= finalListProduct.size(); k++) {
                prod = (Product) finalListProduct.getNode(k).data;
                Supermarket sp = null;
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data.getClass()==Supermarket.class){
                        sp = (Supermarket) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                        if(prod.getSupermarketID()==sp.getId()){
                            break;
                        }
                    }
                }
                tableProd.addCell(String.valueOf(prod.getID()));
                tableProd.addCell(prod.getName());
                tableProd.addCell(String.valueOf(prod.getPrice()));
                tableProd.addCell(String.valueOf(sp.getId()));//revisar
            }//for producto
            document.add(tableProd);
        } else {
            //content += "No hay supermercados ni restaurantes agregados por el momento\n";
            Paragraph parrafo = new Paragraph("Lista de supermercados y restaurantes \n\n" + content,
                    FontFactory.getFont("arial",
                            16,
                            Font.BOLD,
                            BaseColor.BLACK
                    ));
            Paragraph parrafoTemp = new Paragraph();
            parrafoTemp.setAlignment(Paragraph.ALIGN_CENTER);
            parrafoTemp.add("\n\nNo hay productos resgistrados por el momento\n\n");
            document.add(tableProd);//Agrega la tabla al documento 
            document.add(parrafoTemp);
            document.add(parrafo);//se agrega el contenido
        }//if productos
        //aqui creamos el pdf
        Paragraph parrafoComidas = new Paragraph();
        parrafoComidas.setAlignment(Paragraph.ALIGN_CENTER);
        parrafoComidas.setFont(FontFactory.getFont("Tahoma", 16, Font.BOLD, BaseColor.BLACK));
        parrafoComidas.add("\n\nRegistro de Comidas\n\n");
        document.add(parrafoComidas);
        //Tabla de supermercados con su contenido
        PdfPTable tableFood = new PdfPTable(4);//campos
        tableFood.addCell("Id");
        tableFood.addCell("Nombre");
        tableFood.addCell("Precio");
        tableFood.addCell("Restaurante");
        //instancias para recorrer los arboles de product y food
        CircularLinkedList listFood = new CircularLinkedList();
        CircularLinkedList finalListFood = fillTreeTable(util.Utility.getTreeFood().getRoot(), listFood);
        //verificacion y contenido del pdf
        if (!util.Utility.getTreeFood().isEmpty()) {
            for (int i = 1; i <= finalListFood.size(); i++) {
                food = (Food) finalListFood.getNode(i).data;
                Restaurant res = null;
                for (int k = 0; k < util.Utility.getlGraphRestaurants_Supermarkets().size(); k++) {
                    if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(k).data.getClass()==Restaurant.class){
                        res = (Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(k).data;
                        if(food.getRestaurantID()==res.getId()){
                            break;
                        }
                    }
                }
                // tableFood.addCell(String.valueOf(food.getId()));
                tableFood.addCell(String.valueOf("null"));
                tableFood.addCell(food.getName());
                tableFood.addCell(String.valueOf(food.getPrice()));
                tableFood.addCell(res.getName());//revisar
            }//for food    
            document.add(tableFood);
        } else {
            //content += "\n" + sup.getAutoID() + " " + sup.getName() + " " + sup.getLocation() + " " /*+ rest.getId() + " "*/ + "\n";
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("\n\nNo hay supermercados resgistrados por el momento\n\n");
            document.add(tableFood);//Agrega la tabla al documento 
            document.add(parrafo);
            document.add(parrafo);//se agrega el contenido
        }//if comidas
        //document.add(tableFood);
        //aqui el footer
        Paragraph footer = new Paragraph();
        footer.setAlignment(Paragraph.ALIGN_BOTTOM);
        footer.setFont(FontFactory.getFont("Tahoma", 14, Font.NORMAL, BaseColor.DARK_GRAY));
        footer.add("\n\nSherchplit Sistema de Sugerencias de Restaurantes y Supermercados\n\n"
                + "Av. 258 San Jose, Costa Rica\n\nTelefono 8888-8888\n\nCorreo");
        document.add(footer);
        /*
        if (found == false) {
            //si no tiene restaurantes ni supermecados se pone este mensaje
            content += "No existen supermercados ni restaurantes registrados\n";
        }
        found = false;
         */
        //document.add (tableRest);
        document.addTitle ("Lista de Productos y Comidas");
        document.addKeywords ("Java, PDF, Lista de Productos y Comidas");
        document.addAuthor ("Projecto 2 Algoritmos");
        document.addCreator ("Grupo No.11");
        document.close();
    }//createPDF

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
            if (node.data.getClass()==Product.class) {
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
/*este en medio del for de producto
for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                        if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Supermarket) {
                            sp = (Supermarket) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                            if (sp.getId() == prod.getSupermarketID()) {
                                tableProd.addCell(sp.getName());//se agrega nombre de super
                            }//if
                        }//if
                    }//for
 */
