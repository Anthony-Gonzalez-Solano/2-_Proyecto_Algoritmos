/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.list.ListException;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
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
                        Parent root = null;            // cargamos el XFMLVentanaPrincipal
                        try {
                            root = FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipal.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) btn_LogIn_LogIn.getScene().getWindow();
                        stage.setScene(scene);
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






    private void cleanTFs(){
        tf_LogIn_Password.clear();
        tf_LogIn_User.clear();
        tf_SingIn_Password.clear();
        tf_SingIn_PasswordComfirm.clear();
        tf_SingIn_User.clear();
    }
    
}
