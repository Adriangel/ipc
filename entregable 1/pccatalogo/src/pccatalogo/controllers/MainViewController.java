/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pccatalogo.controllers;

import es.upv.inf.Database;
import es.upv.inf.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import pccatalogo.models.Presupuesto;

/**
 * FXML Controller class
 *
 * @author angel
 */
public class MainViewController implements Initializable {
    
    static final String RUTA_PRESUPUESTOS = "";
    static final String RUTA_PREDETERMINADOS = "";
	
    public Presupuesto actual;
    
    @FXML
    private SplitMenuButton selectorCategorias;
    @FXML
    private Label productoCategoria;
    @FXML
    private Label productoDescripcion;
    @FXML
    private Label productoPrecio;
    @FXML
    private Label productoStock;
    @FXML
    private VBox productoSeleccionado;
    @FXML
    private VBox presupuestoVbox;
    @FXML
    private TextField buscarField;
    
    @FXML
    private TableView<Product> tablaProductos;
    @FXML
    private TableColumn<es.upv.inf.Product, String> nombreColumn;
    @FXML
    private TableColumn<es.upv.inf.Product, Double> precioColumn;
    @FXML
    private TableColumn<es.upv.inf.Product, Integer> stockColumn;
    @FXML
    private Label productoSeleccionadoLabel;
    @FXML
    private ImageView catImage;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectorCategorias.getItems().get(0).fire();
    }    
    @FXML
    private void acercaDe(ActionEvent event) {
    }

    @FXML
    private void nuevoPresupuesto(ActionEvent event) {
    }

    @FXML
    private void nuevoPresupuestoPredeterminado(ActionEvent event) {
    }

    @FXML
    private void abrirPresupuesto(ActionEvent event) {
    }

    @FXML
    private void guardarPresupuesto(ActionEvent event) {
    }

    @FXML
    private void guardarPresupuestoComo(ActionEvent event) {
    }

    @FXML
    private void cerrarPresupuestoActual(ActionEvent event) {
    }

    @FXML
    private void eliminarPresupuestoActual(ActionEvent event) {
    }

    @FXML
    private void imprimir(ActionEvent event) {
    }

    @FXML
    private void seleccionarCategoria(ActionEvent event) {
        MenuItem firer = (MenuItem) event.getSource();
        String firerText = firer.getText();
        selectorCategorias.setText("Categoria > " + firerText);
        Product.Category c = stringToCategory(firerText);
        
        tablaProductos.setItems(FXCollections.observableList(Database.getProductByCategory(c)));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockColumn.setStyle( "-fx-alignment: CENTER-RIGHT;");

    }

    @FXML
    private void buscar(ActionEvent event) {
    }

    @FXML
    private void tableClick(MouseEvent event) {
        
        Product p = tablaProductos.getSelectionModel().getSelectedItem();
        productoCategoria.setText(categoryToString(p.getCategory()));
        productoDescripcion.setText(p.getDescription());
        productoPrecio.setText("Precio: " + String.valueOf(p.getPrice()) + " eur.");
        productoStock.setText(String.valueOf(p.getStock()) + " uds. disponibles");
        productoSeleccionadoLabel.setVisible(false);
        productoSeleccionado.setVisible(true);
    }
    
    private static String categoryToString(Product.Category c) {
        String s;
        switch(c) {
            case SPEAKER:
                s = "Altavoces";
                break;
            case HDD:
                s = "Discos duros";
                break;
            case HDD_SSD:
                s = "Discos Duros SSD";
                break;
            case POWER_SUPPLY:
                s = "Fuentes de alimentación";
                break;
            case DVD_WRITER:
                s = "Grabadoras DVD";
                break;
            case RAM:
                s = "Memoria RAM";
                break;
            case SCREEN:
                s = "Monitores";
                break;
            case MULTIREADER:
                s = "Multilectores";
                break;
            case MOTHERBOARD:
                s = "Placas base";
                break;
            case CPU:
                s = "Procesadores";
                break;
            case MOUSE:
                s = "Ratones";
                break;
            case GPU:
                s = "Tarjetas gráficas";
                break;
            case KEYBOARD:
                s = "Teclados";
                break;
            case CASE:
                s = "Cajas";
                break;
            case FAN:
                s = "Ventiladores";
                break;
            default:
                s = "";
        }
        return s;
    }
    
    private static Product.Category stringToCategory(String s){
        Product.Category c;
        switch(s) {
            case "Altavoces":
                c = Product.Category.SPEAKER;
                break;
            case "Discos duros":
                c = Product.Category.HDD;
                break;
            case "Discos Duros SSD":
                c = Product.Category.HDD_SSD;
                break;
            case "Fuentes de alimentación":
                c = Product.Category.POWER_SUPPLY;
                break;
            case "Grabadoras DVD":
                c = Product.Category.DVD_WRITER;
                break;
            case "Memoria RAM":
                c = Product.Category.RAM;
                break;
            case "Monitores":
                c = Product.Category.SCREEN;
                break;
            case "Multilectores":
                c = Product.Category.MULTIREADER;
                break;
            case "Placas base":
                c = Product.Category.MOTHERBOARD;
                break;
            case "Procesadores":
                c = Product.Category.CPU;
                break;
            case "Ratones":
                c = Product.Category.MOUSE;
                break;
            case "Tarjetas gráficas":
                c = Product.Category.GPU;
                break;
            case "Teclados":
                c = Product.Category.KEYBOARD;
                break;
            case "Cajas":
                c = Product.Category.CASE;
                break;
            case "Ventiladores":
                c = Product.Category.FAN;
                break;
            default:
                c = null;
        }
        return c;
    }
}
