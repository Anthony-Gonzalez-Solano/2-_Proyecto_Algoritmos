/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Anthony G.S
 */
public class FileTXT {
    
      //Crea el archivo y la ubicación a escribir
  FileWriter file;
  //Objeto que escribe en el archivo creado
  PrintWriter pw;
   
  //Es la ubicación con el archivo a leer
  File fileAddress;
  //Objeto que permite leer el archivo seleccionado
  FileReader fr;
  //Objeto que almacena el archivo leido
  BufferedReader br;
 
  public void writeFile(String address, String data){
    
    try{
      //Se crea el archivo y se la dan las propiedades de escritura (boolean)
      file = new FileWriter(address,true);
      //Se crea el objeto que tiene la propiedad de escribir en el archivo
      pw = new PrintWriter(file);
             
      pw.println(data);
      
    } catch (IOException ioe) {
      
      JOptionPane.showMessageDialog(null, ioe.getMessage());
      ioe.printStackTrace();
    } finally {
      try {
        file.close();//Cerrar el archivo de texto
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
        e.printStackTrace();
      }
    } 
  }
 
 public ArrayList<String> readFile(String address){
   
    ArrayList<String> information  = new ArrayList<>();
   String line="";
   
   try {
     fileAddress = new File(address);
     fr = new FileReader(fileAddress);
     br = new BufferedReader(fr);
     
     //Leo una línea y si tiene un valor, la almaceno
     while((line=br.readLine())!=null){      
       information.add(line); 
     }
   }catch(IOException e){
     e.printStackTrace();
   }finally{
     try{                    
       if( null != fr ){   
         fr.close();     
       }                  
     }catch (IOException e2){ 
       e2.printStackTrace();
     }
   }
   return information;
  }
 
 public boolean removeElement(String address,Object o){
    ArrayList<String> elements = readFile(address);
    ArrayList<String> elementsOK = new ArrayList<>();
    boolean tf = false;
    
    int count = 0;
    while (count < elements.size()) {
        if(elements.get(count).equals(o.toString())){
            tf=true;
            count++;
        }else{
            elementsOK.add(elements.get(count));
            count++;
        }
    }
    new File(address).delete();
     for (int i = 0; i < elementsOK.size(); i++) {
         writeFile(address, elementsOK.get(i));
    }
    
    return tf;
 }
 
 public boolean modifyFile(String address, Object o,Object o2){
    ArrayList<String> elements = readFile(address);
    ArrayList<String> elementsOK = new ArrayList<>();
    boolean tf = false;
    
    int count = 0;
    while (count < elements.size()) {
        if(elements.get(count).equals(o.toString())){
            elementsOK.add(o2.toString());
            tf=true;
            count++;
        }else{
            elementsOK.add(elements.get(count));
            count++;
        }
    }
    new File(address).delete();
     for (int i = 0; i < elementsOK.size(); i++) {
         writeFile(address, elementsOK.get(i));
    }
    
    return tf;
 }
 
 public boolean existFile(String address){
    File file = new File(address);
   return file.exists();
 }
    
}
