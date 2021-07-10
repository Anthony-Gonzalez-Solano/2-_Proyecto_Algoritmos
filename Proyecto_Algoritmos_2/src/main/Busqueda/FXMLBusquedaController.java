/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Busqueda;

import domain.Dijkstra;
import domain.graph.Vertex;
import domain.list.ListException;
import domain.tree.BTreeNode;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        if(cb_Comidas_Productos.getSelectionModel().getSelectedIndex()!=-1){
            try {
                if(util.Utility.getmGraphPlace().size()>1){
                    if(lbl_Ubicacion.getText().isEmpty()){
                        Dijkstra d = new Dijkstra();
                        int[][] intMatrix = new int[util.Utility.getmGraphPlace().size()][util.Utility.getmGraphPlace().size()];
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                intMatrix[i][j] = (int) util.Utility.getmGraphPlace().getAdjacencyMatrix()[i][j];
                            }
                        }
                        d.dijkstra(intMatrix, util.Utility.getmGraphPlace().getIndexOfVertex(lbl_Ubicacion.getText()));
                        ArrayList<String> distances = d.getSolution();
                        
                        
                        
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
            cb_Comidas_Productos.getItems().add(node.data+"");
            fillCB(node.left);
            fillCB(node.right);
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
            message.setSubject("Cambio de contraseña ");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart attachmentPart = new MimeBodyPart();

            MimeBodyPart textPart = new MimeBodyPart();
            
            
            try {

                File f =new File("Logo_Texto.png");

                attachmentPart.attachFile(f);
                textPart.setText("Su contraseña temporal para el cambio de contraseña es :\n");
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
