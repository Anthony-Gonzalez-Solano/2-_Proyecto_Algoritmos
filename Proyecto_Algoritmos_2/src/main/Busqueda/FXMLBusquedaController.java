/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Busqueda;

import domain.Dijkstra;
import domain.Food;
import domain.Product;
import domain.Restaurant;
import domain.Supermarket;
import domain.graph.Vertex;
import domain.list.ListException;
import domain.tree.BTreeNode;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polyline;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Anthony G.S
 */
public class FXMLBusquedaController implements Initializable {

    @FXML
    private RadioButton Rbtn_Foods;
    @FXML
    private ToggleGroup comidas_productos;
    @FXML
    private RadioButton Rbtn_Products;
    @FXML
    private ComboBox<String> cb_Comidas_Productos;
    @FXML
    private Button btn_Recomendacion;
    @FXML
    private Label lbl_Ubicacion;
    @FXML
    private Label lbl_UbicacionTemporal;
    @FXML
    private Polyline Ubic_TierraBlanca;
    @FXML
    private Polyline Ubic_Cartago;
    @FXML
    private Polyline Ubic_CaballoBlanco;
    @FXML
    private Polyline Ubic_Paraiso;
    @FXML
    private Polyline Ubic_Ujarras;
    @FXML
    private Polyline Ubic_Cachi;
    @FXML
    private Polyline Ubic_Orosi;
    @FXML
    private Polyline Ubic_Cervantes;
    @FXML
    private Polyline Ubic_SantaRosa;
    @FXML
    private Polyline Ubic_Turrialba;
    private Alert a;
    private String[] Recomend;
    private String recomendations;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Recomend = new String[3];
        a = new Alert(Alert.AlertType.ERROR);
        DialogPane dp = a.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dp.getStyleClass().add("myDialog");
        try {
            for (int i = 0; i < util.Utility.getmGraphPlace().size(); i++) {
                Vertex v = util.Utility.getmGraphPlace().getVertexByIndex(i);
                if(v.data.equals("Tierra Blanca")){
                    Ubic_TierraBlanca.setOpacity(0.0);
                }
                if(v.data.equals("Caballo Blanco")){
                    Ubic_CaballoBlanco.setOpacity(0.0);
                }
                if(v.data.equals("Cachí")){
                    Ubic_Cachi.setOpacity(0.0);
                }
                if(v.data.equals("Cartago")){
                    Ubic_Cartago.setOpacity(0.0);
                }
                if(v.data.equals("Cervantes")){
                    Ubic_Cervantes.setOpacity(0.0);
                }
                if(v.data.equals("Orosi")){
                    Ubic_Orosi.setOpacity(0.0);
                }
                if(v.data.equals("Paraíso")){
                    Ubic_Paraiso.setOpacity(0.0);
                }
                if(v.data.equals("Santa Rosa")){
                    Ubic_Turrialba.setOpacity(0.0);
                }
                if(v.data.equals("Turrialba")){
                    Ubic_SantaRosa.setOpacity(0.0);
                }
                if(v.data.equals("Ujarrás")){
                    Ubic_Ujarras.setOpacity(0.0);
                } 
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLBusquedaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void Rbtn_Foods(ActionEvent event) {
        if(!util.Utility.getTreeFood().isEmpty()){
            cb_Comidas_Productos.getItems().clear();
            cb_Comidas_Productos.getSelectionModel().clearSelection();
            fillCB(util.Utility.getTreeFood().getRoot());
        }
    }

    @FXML
    private void Rbtn_Products(ActionEvent event) {
        if(!util.Utility.getTreeProducts().isEmpty()){
            cb_Comidas_Productos.getItems().clear();
            cb_Comidas_Productos.getSelectionModel().clearSelection();
            fillCB(util.Utility.getTreeProducts().getRoot());
        }
    }

    @FXML
    private void btn_Recomendacion(ActionEvent event) {
        Recomend[0]="-";
        Recomend[1]="-";
        Recomend[2]="-";
        if(cb_Comidas_Productos.getSelectionModel().getSelectedIndex()!=-1){
            try {
                if(util.Utility.getmGraphPlace().size()>1){
                    if(!lbl_Ubicacion.getText().isEmpty()){
                        Dijkstra d = new Dijkstra();
                        int[][] intMatrix = new int[util.Utility.getmGraphPlace().size()][util.Utility.getmGraphPlace().size()];
                        for (int i = 0; i < util.Utility.getmGraphPlace().size(); i++) {
                            for (int j = 0; j < util.Utility.getmGraphPlace().size(); j++) {
                                intMatrix[i][j] = (int) util.Utility.getmGraphPlace().getAdjacencyMatrix()[i][j];
                            }
                        }
                        d.dijkstra(intMatrix, util.Utility.getmGraphPlace().getIndexOfVertex(lbl_Ubicacion.getText()));
                        ArrayList<String> distances = d.getSolution();
                        
                        
                        for (int j = 0; j < util.Utility.getlGraphRestaurants_Supermarkets().size(); j++) {
                            if(Rbtn_Foods.isSelected())
                                if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexList()[j].data.getClass()==Restaurant.class){
                                    Restaurant r = (Restaurant)util.Utility.getlGraphRestaurants_Supermarkets().getVertexList()[j].data;
                                    if(r.getLocation().equals(lbl_Ubicacion.getText())){
                                        searchFood(util.Utility.getTreeFood().getRoot(), r,cb_Comidas_Productos.getSelectionModel().getSelectedItem(),0);                                      
                                    }
                                }
                            else
                               if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexList()[j].data.getClass()==Supermarket.class){
                                    Supermarket s = (Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexList()[j].data;
                                    if(s.getLocation().equals(lbl_Ubicacion.getText())){
                                        searchProduct(util.Utility.getTreeProducts().getRoot(), null,cb_Comidas_Productos.getSelectionModel().getSelectedItem(),0);                     
                                    }
                                }     
                        } 
                        
                        if(Recomend[2].equals("-")){
                            for (int i = 0; i < distances.size(); i++) {
                                String location = (String) util.Utility.getmGraphPlace().getVertexList()[Integer.parseInt(distances.get(i).split(",")[0].split("-")[1])].data;
                                       
                                for (int j = 0; j < util.Utility.getlGraphRestaurants_Supermarkets().size(); j++) {
                                    if(Rbtn_Foods.isSelected())
                                        if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexList()[j].data.getClass()==Restaurant.class){
                                            Restaurant r = (Restaurant)util.Utility.getlGraphRestaurants_Supermarkets().getVertexList()[j].data;
                                            if(r.getLocation().equals(location)){
                                                searchFood(util.Utility.getTreeFood().getRoot(), r,cb_Comidas_Productos.getSelectionModel().getSelectedItem(),0);                                      
                                            }
                                        }
                                    else
                                       if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexList()[j].data.getClass()==Supermarket.class){
                                            Supermarket s = (Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexList()[j].data;
                                            if(s.getLocation().equals(location)){
                                                searchProduct(util.Utility.getTreeProducts().getRoot(), s,cb_Comidas_Productos.getSelectionModel().getSelectedItem(),0);                     
                                            }
                                        }     
                                }  
                            }
                        }
                        String user = "";
                        if(util.Utility.getIntro()==null){
                            user="admin";
                        }else{
                           user=util.Utility.getIntro().getUser();
                        }
                        Date date = new Date();
                        String[] hour = date.toString().split(" ")[3].split(":");
                        recomendations = "Recomendaciones/Ubicacion Actual: "+lbl_Ubicacion.getText()+"/Producto: "+cb_Comidas_Productos.getSelectionModel().getSelectedItem()+
                                "/1° - "+Recomend[0]+"/2° - "+Recomend[1]+"/3° - "+Recomend[2]+"/"+util.Utility.dateFormat(date) + " - "+hour[0]+":"+hour[1]+"/"+user;
                        String[] s = recomendations.split("/");
                        a.setHeaderText(s[0]+"                                                                    \n"+s[1]+"\n"+s[2]);
                        a.setContentText(s[3]+"\n"+s[4]+"\n"+s[5]);
                        FileTXT txt = new FileTXT();
                        txt.writeFile("Busquedas.txt", recomendations);
                        util.Utility.getListSearchs().add(recomendations);
                        a.showAndWait();
                        
                        boolean exit = false;
                        while(exit==false){
                            a.setAlertType(Alert.AlertType.CONFIRMATION);
                            ButtonType b1 = new ButtonType("Enviar", ButtonBar.ButtonData.YES);
                            ButtonType b2 = new ButtonType("No Enviar", ButtonBar.ButtonData.NO);
                            a.getButtonTypes().setAll(b1,b2);
                            a.setTitle("Exception Dialog");
                            a.setHeaderText("¿Desea enviar las recomendaciones a un correo?");
                            a.setContentText("Could not find file blabla.txt!");
                            Label label = new Label("Ingrese un correo");
                            TextField tf = new TextField();
                            tf.setPrefWidth(350);
                            GridPane expContent = new GridPane();
                            expContent.setMaxWidth(Double.MAX_VALUE);
                            expContent.add(label, 0, 0);
                            expContent.add(tf, 0, 1);
                            a.getDialogPane().setContent(expContent);
                            Optional<ButtonType> result = a.showAndWait();
                            if(result.get()==b1){
                                if(!tf.getText().isEmpty() && util.Utility.ValidarMail(tf.getText())){
                                    sendEmail(tf.getText());
                                    exit=true;
                                }else{
                                    a.setAlertType(Alert.AlertType.ERROR);
                                    a.setHeaderText("El correo no es valido");
                                    a.setContentText("Verifique que sea correcto");
                                    a.showAndWait(); 
                                }
                            }else{
                                exit=true;
                            }
                        }
                    }else{
                        a.setHeaderText("No seleciono su ubicaicon");
                        a.setContentText("Escoja en el mapa mostrado");
                        a.showAndWait(); 
                    } 
                }else{
                    a.setHeaderText("No hay lugares seleccionados para la busqueda");
                    a.setContentText("vaya a 'Grafo Lugares' y seleccione algunos lugares ");
                    a.showAndWait();
                }
            } catch (ListException ex) {
                Logger.getLogger(FXMLBusquedaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            a.setHeaderText("Debe selecionar el producto a buscar");
            a.setContentText(" ");
            a.showAndWait();
        }
    }

    
    private void fillCB(BTreeNode node){
        if(node!=null){
            if(cb_Comidas_Productos.getItems().isEmpty()){
                cb_Comidas_Productos.getItems().add(node.data+"");
            }
            if(!cb_Comidas_Productos.getItems().contains(node.data+"")){
                cb_Comidas_Productos.getItems().add(node.data+"");
            }
            
            fillCB(node.left);
            fillCB(node.right);
        }
    }
    
    private void searchFood(BTreeNode node,Restaurant r,String name,int dis){
        if(node!=null){
            Food f = (Food) node.data;
            if(f.getRestaurantID()==r.getId()){
                if(f.getName().equals(name)){
                    if(Recomend[0].equals("-")){
                        Recomend[0]="Restaurante "+r.getName()+" a "+dis+" km, en "+r.getLocation()+", a un precio de "+f.getPrice()+" ₡";
                    }
                    if(Recomend[1].equals("-")){
                        if(!Recomend[0].equals("Restaurante "+r.getName()+" a "+dis+" km, en "+r.getLocation()+", a un precio de "+f.getPrice()+" ₡"))
                            Recomend[1]="Restaurante "+r.getName()+" a "+dis+" km, en "+r.getLocation()+", a un precio de "+f.getPrice()+" ₡";
                    }
                    if(Recomend[2].equals("-")){
                        if(!Recomend[0].equals("Restaurante "+r.getName()+" a "+dis+" km, en "+r.getLocation()+", a un precio de "+f.getPrice()+" ₡"))
                            if(!Recomend[1].equals("Restaurante "+r.getName()+" a "+dis+" km, en "+r.getLocation()+", a un precio de "+f.getPrice()+" ₡"))
                                Recomend[2]="Restaurante "+r.getName()+" a "+dis+" km, en "+r.getLocation()+", a un precio de "+f.getPrice()+" ₡";
                    }
                }
            }
            searchFood(node.left,r,name,dis);
            searchFood(node.right,r,name,dis);
        }
    }
    
        private void searchProduct(BTreeNode node,Supermarket p,String name,int dis){
        if(node!=null){
            Product pro = (Product) node.data;
            if(pro.getSupermarketID()==p.getId()){
                if(pro.getName().equals(name)){
                    if(Recomend[0].equals("-")){
                        Recomend[0]="Supermercado "+p.getName()+" a "+dis+" km, en "+p.getLocation()+", a un precio de "+pro.getPrice()+" ₡";
                    }
                    if(Recomend[1].equals("-")){
                        if(!Recomend[0].equals("Supermercado "+p.getName()+" a "+dis+" km, en "+p.getLocation()+", a un precio de "+pro.getPrice()+" ₡"))
                            Recomend[1]="Supermercado "+p.getName()+" a "+dis+" km, en "+p.getLocation()+", a un precio de "+pro.getPrice()+" ₡";
                    }
                    if(Recomend[2].equals("-")){
                        if(!Recomend[0].equals("Supermercado "+p.getName()+" a "+dis+" km, en "+p.getLocation()+", a un precio de "+pro.getPrice()+" ₡"))
                            if(!Recomend[1].equals("Supermercado "+p.getName()+" a "+dis+" km, en "+p.getLocation()+", a un precio de "+pro.getPrice()+" ₡"))
                                Recomend[2]="Supermercado "+p.getName()+" a "+dis+" km, en "+p.getLocation()+", a un precio de "+pro.getPrice()+" ₡";
                    }
                }
            }
            searchProduct(node.left,p,name,dis);
            searchProduct(node.right,p,name,dis);
        }
    }
    
    
       public void sendEmail(String mail) throws ListException{
    // Recipient's email ID needs to be mentioned.
        String to = mail;

        // Sender's email ID needs to be mentioned
        String from = "xx.ucrfake.xx@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass 
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("xx.ucrfake.xx@gmail.com", "UCRfake123");

            }

        });
        //session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Recomendaciondes de Sherchplit");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart attachmentPart = new MimeBodyPart();

            MimeBodyPart textPart = new MimeBodyPart();
            
            
            try {

                File f =new File("src/main/Logo_Texto.png");

                attachmentPart.attachFile(f);
                String[] s = recomendations.split("/");
                if(util.Utility.getIntro()==null){
                    textPart.setText(s[0]+"\n"+s[1]+"\n"+s[2]+"\n \n"+s[3]+"\n"+s[4]+"\n"+s[5]+"\n \n"+s[5]);
                }else{
                   textPart.setText(s[0]+"para"+util.Utility.getIntro().getUser()+"\n"+s[1]+"\n"+s[2]+"\n \n"+s[3]+"\n"+s[4]+"\n"+s[5]); 
                }
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);

            } catch (IOException e) {

                e.printStackTrace();

            }

            message.setContent(multipart);

            //System.out.println("sending...");
            // Send message
            Transport.send(message);
            //System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
    
    
    
    //Funcionamiento del mapa ----------------------------------------------------------------------------------------------
    @FXML
    private void Ubic_TierraBlanca(MouseEvent event) {
        if(Ubic_TierraBlanca.getOpacity()==0.0){
            lbl_Ubicacion.setText("Tiera Blanca");
        }else{
            a.setHeaderText("Tierra Blanca no esta los lugares\ndesignados a la busqueda");
            a.setContentText("Verifiquelo en 'Grafos Lugares'");
            a.showAndWait();
        }
    }

    @FXML
    private void Ubic_Cartago(MouseEvent event) {
        if(Ubic_Cartago.getOpacity()==0.0){
            lbl_Ubicacion.setText("Cartago");
        }else{
            a.setHeaderText("Cartago no esta los lugares\ndesignados a la busqueda");
            a.setContentText("Verifiquelo en 'Grafos Lugares'");
            a.showAndWait();
        }
    }

    @FXML
    private void Ubic_CaballoBlanco(MouseEvent event) {
        if(Ubic_CaballoBlanco.getOpacity()==0.0){
        lbl_Ubicacion.setText("Caballo Blanco");
        }else{
            a.setHeaderText("Caballo Blanco no esta los lugares\ndesignados a la busqueda");
            a.setContentText("Verifiquelo en 'Grafos Lugares'");
            a.showAndWait();
        }
    }

    @FXML
    private void Ubic_Paraiso(MouseEvent event) {
        if(Ubic_Paraiso.getOpacity()==0.0){
        lbl_Ubicacion.setText("Paraíso");
        }else{
            a.setHeaderText("Paraíso no esta los lugares\ndesignados a la busqueda");
            a.setContentText("Verifiquelo en 'Grafos Lugares'");
            a.showAndWait();
        }
    }

    @FXML
    private void Ubic_Ujarras(MouseEvent event) {
        if(Ubic_Ujarras.getOpacity()==0.0){
        lbl_Ubicacion.setText("Ujarás");
        }else{
            a.setHeaderText("Ujarás no esta los lugares\ndesignados a la busqueda");
            a.setContentText("Verifiquelo en 'Grafos Lugares'");
            a.showAndWait();
        }
    }

    @FXML
    private void Ubic_Cachi(MouseEvent event) {
        if(Ubic_Cachi.getOpacity()==0.0){
        lbl_Ubicacion.setText("Cachi");
        }else{
            a.setHeaderText("Cachi no esta los lugares\ndesignados a la busqueda");
            a.setContentText("Verifiquelo en 'Grafos Lugares'");
            a.showAndWait();
        }
    }

    @FXML
    private void Ubic_Orosi(MouseEvent event) {
        if(Ubic_Orosi.getOpacity()==0.0){
        lbl_Ubicacion.setText("Orosi");
        }else{
            a.setHeaderText("Orosi no esta los lugares\ndesignados a la busqueda");
            a.setContentText("Verifiquelo en 'Grafos Lugares'");
            a.showAndWait();
        }
    }

    @FXML
    private void Ubic_Cervantes(MouseEvent event) {
        if(Ubic_Cervantes.getOpacity()==0.0){
        lbl_Ubicacion.setText("Cervantes");
        }else{
            a.setHeaderText("Cervantes no esta los lugares\ndesignados a la busqueda");
            a.setContentText("Verifiquelo en 'Grafos Lugares'");
            a.showAndWait();
        }
    }

    @FXML
    private void Ubic_SantaRosa(MouseEvent event) {
        if(Ubic_SantaRosa.getOpacity()==0.0){
        lbl_Ubicacion.setText("Santa Rosa");
        }else{
            a.setHeaderText("Santa Rosa no esta los lugares\ndesignados a la busqueda");
            a.setContentText("Verifiquelo en 'Grafos Lugares'");
            a.showAndWait();
        }
    }

    @FXML
    private void Ubic_Turrialba(MouseEvent event) {
        if(Ubic_Turrialba.getOpacity()==0.0){
        lbl_Ubicacion.setText("Turrialba");
        }else{
            a.setHeaderText("Turrialba no esta los lugares\ndesignados a la busqueda");
            a.setContentText("Verifiquelo en 'Grafos Lugares'");
            a.showAndWait();
        }
    }

    @FXML
    private void Ubic_TierraBlancaEntered(MouseEvent event) {
        if(Ubic_TierraBlanca.getOpacity()==0.0){
            lbl_UbicacionTemporal.setText("Tiera Blanca");
        }else{
            lbl_UbicacionTemporal.setText("-");
        }
    }

    @FXML
    private void Ubic_CartagoEntered(MouseEvent event) {
        if(Ubic_Cartago.getOpacity()==0.0){
            lbl_UbicacionTemporal.setText("Cartago");
        }else{
            lbl_UbicacionTemporal.setText("-");
        }
    }

    @FXML
    private void Ubic_CaballoBlancoEntered(MouseEvent event) {
        if(Ubic_CaballoBlanco.getOpacity()==0.0){
            lbl_UbicacionTemporal.setText("Caballo Blanco");
        }else{
            lbl_UbicacionTemporal.setText("-");
        }
    }

    @FXML
    private void Ubic_ParaisoEntered(MouseEvent event) {
        if(Ubic_Paraiso.getOpacity()==0.0){
            lbl_UbicacionTemporal.setText("Paraíso");
        }else{
            lbl_UbicacionTemporal.setText("-");
        }
    }

    @FXML
    private void Ubic_UjarrasEntered(MouseEvent event) {
        if(Ubic_Ujarras.getOpacity()==0.0){
            lbl_UbicacionTemporal.setText("Ujarrás");
        }else{
            lbl_UbicacionTemporal.setText("-");
        }
    }

    @FXML
    private void Ubic_CachiEntered(MouseEvent event) {
        if(Ubic_Cachi.getOpacity()==0.0){
            lbl_UbicacionTemporal.setText("Cachi");
        }else{
            lbl_UbicacionTemporal.setText("-");
        }
    }

    @FXML
    private void Ubic_OrosiEntered(MouseEvent event) {
        if(Ubic_Orosi.getOpacity()==0.0){
            lbl_UbicacionTemporal.setText("Orosi");
        }else{
            lbl_UbicacionTemporal.setText("-");
        }
    }

    @FXML
    private void Ubic_CervantesEntered(MouseEvent event) {
        if(Ubic_Cervantes.getOpacity()==0.0){
            lbl_UbicacionTemporal.setText("Cervantes");
        }else{
            lbl_UbicacionTemporal.setText("-");
        }
    }

    @FXML
    private void Ubic_SantaRosaEntered(MouseEvent event) {
        if(Ubic_SantaRosa.getOpacity()==0.0){
            lbl_UbicacionTemporal.setText("Santa Rosa");
        }else{
            lbl_UbicacionTemporal.setText("-");
        }
    }

    @FXML
    private void Ubic_TurrialbaEntered(MouseEvent event) {
        if(Ubic_Turrialba.getOpacity()==0.0){
            lbl_UbicacionTemporal.setText("Turrialba");
        }else{
            lbl_UbicacionTemporal.setText("-");
        }
    }

    @FXML
    private void Ubic_TierraBlancaExited(MouseEvent event) {
        lbl_UbicacionTemporal.setText("-");
    }

    @FXML
    private void Ubic_CartagoExited(MouseEvent event) {
        lbl_UbicacionTemporal.setText("-");
    }

    @FXML
    private void Ubic_CaballoBlancoExited(MouseEvent event) {
        lbl_UbicacionTemporal.setText("-");
    }

    @FXML
    private void Ubic_ParaisoExited(MouseEvent event) {
        lbl_UbicacionTemporal.setText("-");
    }

    @FXML
    private void Ubic_UjarrasExited(MouseEvent event) {
        lbl_UbicacionTemporal.setText("-");
    }

    @FXML
    private void Ubic_CachiExited(MouseEvent event) {
        lbl_UbicacionTemporal.setText("-");
    }

    @FXML
    private void Ubic_OrosiExited(MouseEvent event) {
        lbl_UbicacionTemporal.setText("-");
    }

    @FXML
    private void Ubic_CervantesExited(MouseEvent event) {
        lbl_UbicacionTemporal.setText("-");
    }

    @FXML
    private void Ubic_SantaRosaExited(MouseEvent event) {
        lbl_UbicacionTemporal.setText("-");
    }

    @FXML
    private void Ubic_TurrialbaExited(MouseEvent event) {
        lbl_UbicacionTemporal.setText("-");
    }
    
 
}
