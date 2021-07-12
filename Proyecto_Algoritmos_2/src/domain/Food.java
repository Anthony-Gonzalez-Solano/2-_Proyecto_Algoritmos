/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Dell 7470
 */
public class Food {

    private static int autoId;
    private String name;
    private double price;
    private int restaurantID;

    public Food(String name, double price, int restaurantID) {
        this.name = name;
        this.price = price;
        this.restaurantID = restaurantID;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    @Override
    public String toString() {
        return name;
    }

    public String secondToString() {
        return name + "," + price + "," + restaurantID;
    }

}
