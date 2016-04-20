/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pccatalogo.models;

import es.upv.inf.Product;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import pccatalogo.controllers.MainViewController;
import pccatalogo.data.Data;

/**
 *
 * @author angel
 */
@XmlRootElement(name = "Presupuesto")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Presupuesto {
    protected boolean modified;
    protected boolean pred = false;
    private String name;
    private Componente mobo;
    private Componente cpu;
    private Componente torre;
    private Componente ram;
    private List<Componente> hdd;
    private Componente gpu;
    private Componente psu;
    private List<Componente> optional;
    
    public Presupuesto(){
        name = "Presupuesto Nuevo";
        hdd = new ArrayList();
        optional = new ArrayList();
    }
    
    public void addComponente(Componente c){
        int aux;
        boolean added = false;
	switch(c.getProduct().getCategory()) {
            case MOTHERBOARD:
                if (mobo == null) {
                    mobo = c;
                    mobo.setQuantity(1);
                    modified = true;
                }
                break;
            case CPU:
                if (cpu == null) {
                    cpu = c;
                    cpu.setQuantity(1);
                    modified = true;
                }
                break;
            case CASE:
                if (torre == null) {
                    torre = c;
                    torre.setQuantity(1);
                    modified = true;
                }
                break;
            case RAM:
                if (c.equals(ram)) {
                    modified = true;
                    ram.setQuantity(ram.getQuantity() + c.getQuantity());
                }
                else if (ram == null){
                    ram = c;
                    modified = true;
                }
                
                break;
            case HDD:
            case HDD_SSD:
                for(aux = 0; aux < hdd.size(); aux++) {
                    if (hdd.get(aux).equals(c)){
                        hdd.get(aux).setQuantity(hdd.get(aux).getQuantity() + c.getQuantity());
                        added = true;
                        modified = true;
                        break;
                    }
                }
                if(!added){
                    modified = true;
                    hdd.add(c);
                }
                    
                break;
            case GPU:
                if (c.equals(gpu)){
                    gpu.setQuantity(gpu.getQuantity() + c.getQuantity());
                    modified = true;
                }
                else if (gpu == null){
                    gpu = c;
                    modified = true;
                }
                break;
            case POWER_SUPPLY:
                if (psu == null){
                    psu = c;
                    psu.setQuantity(1);
                    modified = true;
                }
                break;
            default:
                for(aux = 0; aux < optional.size(); aux++) {
                    if (optional.get(aux).equals(c)){
                        optional.get(aux).setQuantity(optional.get(aux).getQuantity() + c.getQuantity());
                        added = true;
                        modified = true;
                        break;
                    }
                }
                if(!added){
                    optional.add(c);
                    modified = true;
                }
                    
	}
    }
    
    public boolean addable(Componente c){
        int q;
        switch(c.getProduct().getCategory()) {
            case CPU:
                if (cpu != null)
                    return false;
                break;
            case MOTHERBOARD:
                if (mobo != null)
                    return false;
                break;
            case CASE:
                if (torre != null)
                    return false;
                break;
            case RAM:
                q = this.getProductQuantity(c.getProduct());
                if (q == -1)
                    return false;
                break;
            case GPU:
                q = this.getProductQuantity(c.getProduct());
                if (q == -1)
                    return false;
                break;
            case POWER_SUPPLY:
                if (psu != null)
                    return false;
                break;
            case HDD:
            case HDD_SSD:
            default:
                return true;
        }
        return true;
    }
    
    public void deleteComponente(Componente c) {
        this.deleteComponente(c, Integer.MAX_VALUE);
    }
    
    public void deleteComponente(Componente c, int q){
        int i;
        switch(c.getProduct().getCategory()) {
            case MOTHERBOARD:
                mobo = null;
                break;
            case CPU:
                cpu = null;
                break;
            case CASE:
                torre = null;
                break;
            case RAM:
                if (ram.getQuantity() <= q) {
                    ram = null;
                }
                else {
                    ram.setQuantity(ram.getQuantity()-q);
                }
                break;
            case HDD:
            case HDD_SSD:
                i = hdd.indexOf(c);
                if(hdd.get(i).getQuantity() <= q) {
                    hdd.remove(i);
                }
                else {
                    hdd.get(i).setQuantity(hdd.get(i).getQuantity() - q);
                }
                break;
            case GPU:
                if (gpu.getQuantity() <= q) {
                    gpu = null;
                }
                else {
                    gpu.setQuantity(gpu.getQuantity()-q);
                }
                break;
            case POWER_SUPPLY:
                psu = null;
                break;
            default:
                i = optional.indexOf(c);
                if(optional.get(i).getQuantity() <= q) {
                    optional.remove(i);
                }
                else {
                    optional.get(i).setQuantity(optional.get(i).getQuantity() - q);
                }
        }
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    public void borrar(){
        try {
            File f = new File(Data.class.getResource("presupuestosUsuario/" + name).toString());
            if (f.exists())
                f.delete();
        }
        catch(NullPointerException e){}
    }
    
    public Node getNode(){
        ScrollPane sp = new ScrollPane();
        GridPane gp = new GridPane();
        VBox vb = new VBox();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        GridPane.setConstraints(vb, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.SOMETIMES, Priority.SOMETIMES, new Insets(20));
        sp.setContent(vb);
        gp.add(sp,0,0);
        gp.setAlignment(Pos.CENTER);
        
        vb.setStyle("-fx-background-color:#EEE;-fx-background-radius:7;");
        vb.setMaxWidth(500);
        vb.setPrefHeight(500);
        vb.setPadding(new Insets(28,24,20,24));
        vb.getStyleClass().add("presupuestoBVox");
        
        vb.getChildren().addAll(new Label(name), new Label(""));
        vb.getChildren().addAll(new Label("Placa base"), getHBox(this.getMobo(), false), new Label(""));
        vb.getChildren().addAll(new Label("Procesador"), getHBox(this.getCpu(), false), new Label(""));
        vb.getChildren().addAll(new Label("Caja"), getHBox(this.getTorre(), false), new Label(""));
        vb.getChildren().addAll(new Label("Memoria RAM"), getHBox(this.getRam(), false), new Label(""));
        vb.getChildren().add(new Label("Disco Duro"));
        for(int i=0; i<hdd.size(); i++){
            vb.getChildren().add(getHBox(hdd.get(i), false));
        }
        vb.getChildren().addAll(new Label(""), new Label("Tarjeta gráfica"), getHBox(this.getGpu(), false), new Label(""));
        vb.getChildren().addAll(new Label("Fuente de alimentación"), getHBox(this.getPsu(), false), new Label(""));
        vb.getChildren().add(new Label("Otros Componentes"));
        for(int i=0; i<optional.size(); i++){
            vb.getChildren().add(getHBox(optional.get(i), true));
        }
        double precio =  this.getTotalPrice();
        DecimalFormat df = new DecimalFormat("#.##");
        vb.getChildren().addAll(new Label(""), new Label("Precio total del presupuesto:  " + df.format(precio) + "€\tIVA:" + df.format(precio*0.21) + "€\tTOTAL:" + df.format(precio*1.21) + "€"));
        
        return gp;
    }

    private static HBox getHBox(Componente c, boolean showCat){
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setSpacing(10);
        if (c != null) {
            if (showCat == true) {
                hbox.getChildren().add(new Label(MainViewController.categoryToString(c.getProduct().getCategory())));
            }
            
            Label desc = new Label(c.getProduct().getDescription());
            Label precio = new Label(String.valueOf(c.getProduct().getPrice()) + "€");
            precio.setMaxWidth(50);
            precio.setMinWidth(50);
            Label q = new Label(String.valueOf(c.getQuantity()) + "uds.");
            q.setMaxWidth(50);
            q.setMinWidth(50);
            Label importe = new Label(String.valueOf(c.getPrice()) + "€");
            importe.setMaxWidth(50);
            importe.setMinWidth(50);
            hbox.getChildren().addAll(desc, precio, q, importe);
        }
        else {
            hbox.getChildren().add(new Label("Selecciona un producto para insertar aquí"));
        }
        return hbox;
    }
    
    private double getTotalPrice(){
        double p = 0;
        int i;
        
       	p += (mobo == null)? 0 : mobo.getPrice();
        p += (cpu== null)? 0 : cpu.getPrice();
        p += ( torre== null)? 0 : torre.getPrice();
        p += ( psu== null)? 0 : psu.getPrice();
        p += ( gpu== null)? 0 : gpu.getPrice();
        p += ( ram== null)? 0 : ram.getPrice();
        for(i=0; i<hdd.size(); i++) {
            p += hdd.get(i).getPrice();
        }
        for(i=0; i<optional.size(); i++) {
            p += optional.get(i).getPrice();
        }
        
        
        return p;
    }
    
    
    public String getName(){return name;}
    public Componente getMobo(){return mobo;}
    public Componente getCpu(){return cpu;}
    public Componente getTorre(){return torre;}
    public Componente getRam(){return ram;}
    public List<Componente> getHdd(){return hdd;}
    public Componente getGpu(){return gpu;}
    public Componente getPsu(){return psu;}
    public List<Componente> getOptional(){return optional;}
    
    /**
     * @param p     Producto a buscar
     * @return      -1 si el producto no se encuentra, 0 si null
     */
    public int getProductQuantity(Product p) {
        int r = -1;
        Componente c = new Componente(p, 1);
        try{
            switch(p.getCategory()) {
                case MOTHERBOARD:
                    if (p.getDescription().equals(mobo.getProduct().getDescription())) {
                        r = mobo.getQuantity();
                    }
                    break;
                case CPU:
                    if (p.getDescription().equals(cpu.getProduct().getDescription())) {
                        r = cpu.getQuantity();
                    }
                    break;
                case CASE:
                    if (p.getDescription().equals(torre.getProduct().getDescription())) {
                        r = torre.getQuantity();
                    }
                    break;
                case RAM:
                    if (p.getDescription().equals(ram.getProduct().getDescription())) {
                        r = ram.getQuantity();
                    }
                    break;
                case HDD:
                case HDD_SSD:
                    r = (hdd.contains(c))? hdd.get(hdd.indexOf(c)).getQuantity() : -1;
                    break;
                case GPU:
                    if (p.getDescription().equals(gpu.getProduct().getDescription())) {
                        r = gpu.getQuantity();
                    }
                    break;
                case POWER_SUPPLY:
                    if (p.getDescription().equals(psu.getProduct().getDescription())) {
                        r = psu.getQuantity();
                    }
                    break;
                default:
                    r = (optional.contains(c))? optional.get(optional.indexOf(c)).getQuantity() : -1;
                    break;
            }
        }
        catch(NullPointerException e) {
                return 0;
        }
        return r;
    }
    
    public File getProductFile(){
        String fileStr = "presupuestos";
        if (pred) {
            fileStr += "Predeterminados/";
        }
        else {
            fileStr += "Usuario/";
        }
        fileStr += this.getName();
        File f = new File(fileStr);
        return f;
    }
}
