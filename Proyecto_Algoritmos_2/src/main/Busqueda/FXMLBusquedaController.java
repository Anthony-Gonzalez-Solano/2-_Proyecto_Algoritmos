/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Busqueda;

import domain.list.ListException;
import domain.tree.BTreeNode;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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
    private Polyline Ubic_TierraBlanca2;
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
    private Polyline Ubic_Cervantes2;
    @FXML
    private Polyline Ubic_Cervantes;
    @FXML
    private Polyline Ubic_SantaRosa;
    @FXML
    private Polyline Ubic_Turrialba;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    }

    
    
    
    
    
    
    private void fillCB(BTreeNode node){
        if(node!=null){
            cb_Comidas_Productos.getItems().add(node.data+"");
            fillCB(node.left);
            fillCB(node.right);
        }
    }
    
    
    //Funcionamiento del mapa ----------------------------------------------------------------------------------------------
    @FXML
    private void Ubic_TierraBlanca(MouseEvent event) {
        lbl_Ubicacion.setText("Tiera Blanca");
    }

    @FXML
    private void Ubic_Cartago(MouseEvent event) {
        lbl_Ubicacion.setText("Cartago");
    }

    @FXML
    private void Ubic_CaballoBlanco(MouseEvent event) {
        lbl_Ubicacion.setText("Caballo Blanco");
    }

    @FXML
    private void Ubic_Paraiso(MouseEvent event) {
        lbl_Ubicacion.setText("Paraíso");
    }

    @FXML
    private void Ubic_Ujarras(MouseEvent event) {
        lbl_Ubicacion.setText("Ujarás");
    }

    @FXML
    private void Ubic_Cachi(MouseEvent event) {
        lbl_Ubicacion.setText("Cachi");
    }

    @FXML
    private void Ubic_Orosi(MouseEvent event) {
        lbl_Ubicacion.setText("Orosi");
    }

    @FXML
    private void Ubic_Cervantes(MouseEvent event) {
        lbl_Ubicacion.setText("Cervantes");
    }

    @FXML
    private void Ubic_SantaRosa(MouseEvent event) {
        lbl_Ubicacion.setText("Santa Rosa");
    }

    @FXML
    private void Ubic_Turrialba(MouseEvent event) {
        lbl_Ubicacion.setText("Turrialba");
    }

    @FXML
    private void Ubic_TierraBlancaEntered(MouseEvent event) {
        lbl_UbicacionTemporal.setText("Tiera Blanca");
    }

    @FXML
    private void Ubic_CartagoEntered(MouseEvent event) {
        lbl_UbicacionTemporal.setText("Cartago");
    }

    @FXML
    private void Ubic_CaballoBlancoEntered(MouseEvent event) {
        lbl_UbicacionTemporal.setText("Caballo Blanco");
    }

    @FXML
    private void Ubic_ParaisoEntered(MouseEvent event) {
        lbl_UbicacionTemporal.setText("Paraíso");
    }

    @FXML
    private void Ubic_UjarrasEntered(MouseEvent event) {
        lbl_UbicacionTemporal.setText("Ujarrás");
    }

    @FXML
    private void Ubic_CachiEntered(MouseEvent event) {
        lbl_UbicacionTemporal.setText("Cachi");
    }

    @FXML
    private void Ubic_OrosiEntered(MouseEvent event) {
        lbl_UbicacionTemporal.setText("Orosi");
    }

    @FXML
    private void Ubic_CervantesEntered(MouseEvent event) {
        lbl_UbicacionTemporal.setText("Cervantes");
    }

    @FXML
    private void Ubic_SantaRosaEntered(MouseEvent event) {
        lbl_UbicacionTemporal.setText("Santa Rosa");
    }

    @FXML
    private void Ubic_TurrialbaEntered(MouseEvent event) {
        lbl_UbicacionTemporal.setText("Turrialba");
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
    
 
}
