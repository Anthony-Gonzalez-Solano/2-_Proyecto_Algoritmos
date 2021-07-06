/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.list.CircularLinkedList;
import domain.list.ListException;
import com.sun.javafx.sg.prism.NGCanvas;
import domain.Security;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
import javax.swing.JOptionPane;
import org.icepdf.ri.common.FileExtensionUtils;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Anthony G.S
 */
public class FXMLSecurityController implements Initializable {


    private String temporalPass;
    @FXML
    private Pane P_selection;
    @FXML
    private BorderPane bp;
    @FXML
    private Button btn_SingIn;
    @FXML
    private Button btn_LogIn;
    @FXML
    private TextField tf_SingIn_User;
    @FXML
    private TextField tf_SingIn_Password;
    @FXML
    private Button btn_SingIn_SingIn;
    @FXML
    private Button btn_SingIn_Cancel;
    @FXML
    private Pane P_SingIn;
    @FXML
    private TextField tf_SingIn_PasswordComfirm;
    @FXML
    private Pane P_LogIn;
    @FXML
    private TextField tf_LogIn_User;
    @FXML
    private TextField tf_LogIn_Password;
    @FXML
    private Button btn_LogIn_LogIn;
    @FXML
    private Button btn_LogIn_Cancel;
    @FXML
    private Pane P_Check;
    @FXML
    private Pane P_Check_1;
    @FXML
    private TextField tf_Check_Password;
    @FXML
    private TextField tf_Check_PasswordConfirm;
    @FXML
    private Button btn_Check_Save;
    @FXML
    private TextField tf_Check_Mail;
    @FXML
    private TextField tf_Check_TempPass;
    @FXML
    private Button btn_Check_check;
    @FXML
    private Button btn_Check_send;
    @FXML
    private Pane p_Check_2;
    @FXML
    private Button btn_Check_Cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }    
    
    @FXML
    private void btn_SingIn(ActionEvent event) {
        P_selection.setVisible(false);
        P_LogIn.setVisible(true);
    }

    @FXML
    private void btn_LogIn(ActionEvent event) {
        P_selection.setVisible(false);
        P_SingIn.setVisible(true);
    }

    @FXML
    private void btn_SingIn_SingIn(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        DialogPane dp = a.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dp.getStyleClass().add("myDialog");
        if(!tf_SingIn_User.getText().isEmpty() && !tf_SingIn_Password.getText().isEmpty() && !tf_SingIn_PasswordComfirm.getText().isEmpty()){
            try {
                boolean register = false;
                for (int i = 1; i <= util.Utility.getUsers().size(); i++) {
                    Security s = (Security) util.Utility.getUsers().getNode(i).data;
                    if(tf_SingIn_User.getText().equals(s.getUser())){
                        register=true;
                        break;
                    }
                }
                if(register==false){
                    if(tf_SingIn_Password.getText().equals(tf_SingIn_PasswordComfirm.getText())){
                        if(util.Utility.checkPass(tf_SingIn_Password.getText())){
                            FileTXT txt = new FileTXT();
                            Security s = new Security(tf_SingIn_User.getText(), tf_SingIn_Password.getText());
                            txt.writeFile("Users.txt", s.toString());
                            util.Utility.getUsers().add(s);
                            
                            a.setAlertType(Alert.AlertType.INFORMATION);
                            a.setHeaderText("Se ha registrado de manera exitosa !!");
                            a.setContentText("Ahora puede ingresar a los servicios");
                            a.showAndWait();
                            P_SingIn.setVisible(false);
                            P_selection.setVisible(true);
                            cleanTFs();
                        }else{
                           a.setHeaderText("Contraseña invalida");
                            a.setContentText("La contraseña debe tener\n- Almenos 8 caracteres\n-Numeros"
                                            + "- Mayusculas [A-Z]\n-Minusculas [a-z]\n-Signos ! # $ . , * + -");
                            a.showAndWait(); 
                        }
                    }else{
                        a.setHeaderText("La contraseña no coincide");
                        a.setContentText("Verifique que la haya ingresado correctamente");
                        a.showAndWait();
                    }
                }else{
                    a.setHeaderText("El usuario ingesado ya exite");
                    a.setContentText("Debe de cambiarlo");
                    a.showAndWait();
                }
            } catch (ListException ex) {
                Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            a.setHeaderText("No debe dejar espacios en blanco");
             a.setContentText(" ");
            a.showAndWait();
        }
    }

    @FXML
    private void btn_SingIn_Cancel(ActionEvent event) {
        cleanTFs();
        P_SingIn.setVisible(false);
        P_selection.setVisible(true);
    }

    @FXML
    private void btn_LogIn_LogIn(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        DialogPane dp = a.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dp.getStyleClass().add("myDialog");
        if(!tf_LogIn_User.getText().isEmpty() && !tf_LogIn_Password.getText().isEmpty()){
            try {
                Security s=null;
                boolean register = false;
                for (int i = 1; i <= util.Utility.getUsers().size(); i++) {
                    s=(Security) util.Utility.getUsers().getNode(i).data;
                    if(tf_LogIn_User.getText().equals(s.getUser())){
                        register=true;
                        break;
                    }
                }
                if(register==true){
                    if(tf_LogIn_Password.getText().equals(s.getPassword())){
                        JOptionPane.showMessageDialog(null, "xd");
                        
                        //carga el otro
                        
                        
                        
                    }else{
                        a.setHeaderText("Contraseña incorrecta");
                        a.setContentText(" ");
                        a.showAndWait();
                        tf_LogIn_Password.clear();
                    }
                }else{
                    a.setHeaderText("El nombre de usuario ingresado\nno existe");
                    a.setContentText("Verfique que sea correcto o\nregistrelo");
                    a.showAndWait();  
                }
            } catch (ListException ex) {
                Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            a.setHeaderText("No debe dejar espacios en blanco");
            a.setContentText(" ");
            a.showAndWait();
        }
    }

    @FXML
    private void btn_LogIn_Cancel(ActionEvent event) {
        cleanTFs();
        P_LogIn.setVisible(false);
        P_selection.setVisible(true);
    }

    @FXML
    private void btn_Check_Save(ActionEvent event) {
    }

    @FXML
    private void btn_Check_send(ActionEvent event) {
    }

    @FXML
    private void btn_Check_check(ActionEvent event) {
    }

    @FXML
    private void btn_Check_Cancel(ActionEvent event) {
    }

    private void cleanTFs(){
        tf_Check_Mail.clear();
        tf_Check_Password.clear();
        tf_Check_PasswordConfirm.clear();
        tf_Check_TempPass.clear();
        tf_LogIn_Password.clear();
        tf_LogIn_User.clear();
        tf_SingIn_Password.clear();
        tf_SingIn_PasswordComfirm.clear();
        tf_SingIn_User.clear();
    }
//    private void btn_acept_newStudent(ActionEvent event) {
//        if(!tf_carnet_newStudent.getText().isEmpty() && !tf_passwordConfirm_newStudent.getText().isEmpty() && 
//                !tf_password_newSudent.getText().isEmpty()){// verificamos que los espacios no esten en blanco
//            try {
//                boolean register=true;
//                for (int i = 1; i <= util.Utility.getUsers().size(); i++) {  // revisamos que el estudiantes exista y que no tiene contraseña como usuario
//                    Security s = (Security)util.Utility.getUsers().getNode(i).data;
//                    if(tf_carnet_newStudent.getText().equals(s.getUser()) && s.getPassword().equals("-")){
//                        register=false;
//                    }
//                }
//                FileTXT file = new FileTXT();
//                if(register==false){ // si no esta registrado
//                    if(tf_password_newSudent.getText().equals(tf_passwordConfirm_newStudent.getText())){ //confirmamos que las contraseñas coinciden
//                        for (int i = 1; i <= util.Utility.getUsers().size(); i++) {
//                            Security s = (Security)util.Utility.getUsers().getNode(i).data;
//                            if(tf_carnet_newStudent.getText().equals(s.getUser())){ //bscamos el usuario y le agregamos la contraseña
//                                file.modifyFile("Users.txt", s, new Security(tf_carnet_newStudent.getText(),tf_password_newSudent.getText())) ;
//                                s.setPassword(tf_password_newSudent.getText());
//                                util.Utility.getUsers().getNode(i).data = s;
//                                
//                                P_NewSudent.setVisible(false);
//                                P_selection.setVisible(true);
//                                Alert a = new Alert(Alert.AlertType.INFORMATION);
//                                a.setHeaderText("Se a guardado con exito la contraseña");
//                                a.setContentText("ahora puede ingresar a hacer consultas");
//                                a.showAndWait();
//                            }
//                        }   
//                    }else{
//                        Alert a = new Alert(Alert.AlertType.ERROR);
//                        a.setHeaderText("La contrseña y su confirmación no coinciden");
//                        a.showAndWait();
//                    }
//                }else{
//                    Alert a = new Alert(Alert.AlertType.ERROR);
//                    a.setHeaderText("El estudiante indicado no se a podido encontrar\no ya tiene una contraseña registrada");
//                    a.showAndWait();
//                }  
//            } catch (ListException ex) {
//                Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
//            } 
//        }else{
//            Alert a = new Alert(Alert.AlertType.ERROR);
//            a.setHeaderText("No debe dejar espacios en blanco");
//            a.showAndWait();
//        }
//    }
//-----------------------------------------------Proceso cambio de contraseña
//    private void btn_Enviar_ChangePassword_1(ActionEvent event) {
//        if(!P_ChangePassword_1_carnet.getText().isEmpty()){
//            try {
//                boolean tf = false;
//                for (int i = 1; i <= util.Utility.getEstudiantes().size(); i++) {  // revisamos que el carnet exista
//                    Student s = (Student)util.Utility.getEstudiantes().getNode(i).data;
//                    if(s.getStudentID().equals(P_ChangePassword_1_carnet.getText())){
//                        stud=s;
//                        tf=true;
//                    }
//                }
//                if(tf==true){
//                    P_ChangePassword_1.setVisible(false);  // si existe pasamos al siguiente panel y enviamos la contraseña temporal
//                    P_ChangePassword_2.setVisible(true);
//                    temporalPass = util.Utility.randomPass();
//                    sendEmail();
//                    Alert a = new Alert(Alert.AlertType.INFORMATION);
//                    a.setHeaderText("Se envio su contraseña temporal");
//                    a.setContentText("revise su correo");
//                    a.showAndWait(); 
//                    P_ChangePassword_1_carnet.setText("");
//                }else{
//                    Alert a = new Alert(Alert.AlertType.ERROR);
//                    a.setHeaderText("El carnet ingresado no estaregistrado");
//                    a.setContentText("Verifique que sea correcto");
//                    a.showAndWait();  
//                }
//            } catch (ListException ex) {
//                Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }else{
//            Alert a = new Alert(Alert.AlertType.ERROR);
//            a.setHeaderText("No deje el espacio en blanco");
//            a.showAndWait();
//        }
//        
//    }

//    private void P_ChangePanel_2_btnSave(ActionEvent event) {
//        if(!P_ChangePassword_2_tfTemporalPass.getText().isEmpty() && !P_ChangePanel_2_tfNewPass.getText().isEmpty() && 
//                !P_ChangePassword_2_tfConformPass.getText().isEmpty()){ //revisamos qu los espacios no esten vacios
//            
//            if(P_ChangePassword_2_tfTemporalPass.getText().equals(temporalPass)){ // revisamos que la contraseña temporal coincide
//                if(P_ChangePanel_2_tfNewPass.getText().equals(P_ChangePassword_2_tfConformPass.getText())){ // confirmamos que las constraseñas coinciden
//                    try {
//                        Security s = null;
//                        Security s2 = new Security(stud.getStudentID(), P_ChangePanel_2_tfNewPass.getText());
//                        
//                        for (int i = 1; i <= util.Utility.getUsers().size(); i++) {
//                            s = (Security)util.Utility.getUsers().getNode(i).data;
//                            if(s.getUser().equals(stud.getStudentID())){ //actualizamos la lista de usuarios
//                                util.Utility.getUsers().getNode(i).data = s2;
//                            }
//                        }
//                        FileTXT txt = new FileTXT();             //actualizamos el txt y regresamos al inicio
//                        txt.modifyFile("Users.txt", s, s2);
//                        Alert a = new Alert(Alert.AlertType.ERROR);
//                        a.setHeaderText("Se a cambiado su contraseña");
//                        P_ChangePanel_2_tfNewPass.setText("");
//                        P_ChangePassword_2_tfConformPass.setText("");
//                        P_ChangePassword_2_tfTemporalPass.setText("");
//                        P_selection.setVisible(true);
//                        P_ChangePasswors.setVisible(false);
//                        P_ChangePassword_1.setVisible(true);
//                        P_ChangePassword_2.setVisible(false);
//                        a.showAndWait();
//                    } catch (ListException ex) {
//                        Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }else{
//                    Alert a = new Alert(Alert.AlertType.ERROR);
//                    a.setHeaderText("La confirmacion de contraseña no coincide");
//                    a.showAndWait(); 
//                }
//            }else{
//                Alert a = new Alert(Alert.AlertType.ERROR);
//                a.setHeaderText("La contraseña temporal no coincide");
//                a.setContentText("Verifique en su correo que sea correcta");
//                a.showAndWait(); 
//            }
//        }else{
//            Alert a = new Alert(Alert.AlertType.ERROR);
//            a.setHeaderText("No deje el espacio en blanco");
//            a.showAndWait(); 
//        }
//    }
//
//    private void P_ChangePanel_2_btnCancel(ActionEvent event) {
//        P_ChangePasswors.setVisible(false);
//        P_ChangePassword_1.setVisible(true);
//        P_ChangePassword_2.setVisible(false);
//        P_selection.setVisible(true);
//    }
//
//    private void lostPasword(MouseEvent event) {
//        P_ChangePasswors.setVisible(true);
//        P_student.setVisible(false);
//    }
    
//        public void sendEmail() throws ListException{
//    // Recipient's email ID needs to be mentioned.
//        String to = stud.getEmail();
//
//        // Sender's email ID needs to be mentioned
//        String from = "xx.ucrfake.xx@gmail.com";
//
//        // Assuming you are sending email from through gmails smtp
//        String host = "smtp.gmail.com";
//
//        // Get system properties
//        Properties properties = System.getProperties();
//
//        // Setup mail server
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//
//        // Get the Session object.// and pass 
//        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//
//            protected PasswordAuthentication getPasswordAuthentication() {
//
//                return new PasswordAuthentication("xx.ucrfake.xx@gmail.com", "UCRfake123");
//
//            }
//
//        });
//        //session.setDebug(true);
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(from));
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            // Set Subject: header field
//            message.setSubject("Cambio de contraseña de "+stud.getFirstname()+" "+stud.getLastname());
//
//            Multipart multipart = new MimeMultipart();
//
//            MimeBodyPart attachmentPart = new MimeBodyPart();
//
//            MimeBodyPart textPart = new MimeBodyPart();
//            
//            
//            try {
//
//                File f =new File("Escudo.png");
//
//                attachmentPart.attachFile(f);
//                textPart.setText("Su contraseña temporal para el cambio de contraseña es :\n"+temporalPass);
//                multipart.addBodyPart(textPart);
//                multipart.addBodyPart(attachmentPart);
//
//            } catch (IOException e) {
//
//                e.printStackTrace();
//
//            }
//
//            message.setContent(multipart);
//
//            System.out.println("sending...");
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
//
//    }

    @FXML
    private void txt_change(MouseEvent event) {
    }


}
