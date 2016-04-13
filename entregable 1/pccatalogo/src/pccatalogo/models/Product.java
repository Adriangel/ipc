/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pccatalogo.models;

/**
 *
 * @author Adrian
 */
public class Product {
    public enum Category {
        SPEAKER, HDD, HDD_SSD, POWER_SUPPLY, DVD_WRITER, RAM, SCREEN,
        MULTIREADER, MOTHERBOARD, CPU, MOUSE, GPU,
        KEYBOARD, CASE, FAN
    }
public Product(String description, double price, int stock, Category category) {
    this.description = description;
    this.price = price;
    this.stock = stock;
    this.category = category;
}
public Category getCategory() {
    return category;
}
public String getDescription() {
    return description;
}
public double getPrice() {
    return price;
}
public int getStock() {
    return stock;
}
private final String description;
private final double price;
private final int stock;
private final Category category;
}
