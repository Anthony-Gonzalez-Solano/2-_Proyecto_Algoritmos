/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class Product {
    private int ID;
    private String Name;
    private Double Price;
    private int SupermarketID;
    private static int autoID;// variable para aumentar el id de los estudiantes
    public Product(String Name, Double Price, int SupermarketID){
        this.Name=Name;
        this.Price=Price;
        this.SupermarketID=SupermarketID;
        this.ID=autoID;
        autoID++;
    }

    public Product(String Name, Double Price, int SupermarketID,int id){
        this.Name=Name;
        this.Price=Price;
        this.SupermarketID=SupermarketID;
        this.ID=id;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double Price) {
        this.Price = Price;
    }

    public int getSupermarketID() {
        return SupermarketID;
    }

    public void setSupermarketID(int SupermarketID) {
        this.SupermarketID = SupermarketID;
    }

    public static int getAutoID() {
        return autoID;
    }

    public static void setAutoID(int autoID) {
        Product.autoID = autoID;
    }

    @Override
    public String toString() {
        return Name;
    }
    
    public String secondToSting() {
        return ID + "," + Name + "," + Price + "," + SupermarketID;
    }
    
}
