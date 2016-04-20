/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pccatalogo.controllers;

import es.upv.inf.Database;
import es.upv.inf.Product;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pccatalogo.data.logos.Logos;
import pccatalogo.models.Componente;
import pccatalogo.models.Presupuesto;


/**
 * FXML Controller class
 *
 * @author angel
 */
public class MainViewController implements Initializable {
    
    private int actual;
    private List<Presupuesto> presupuestos;
    
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
    @FXML
    private TextField anadirProductosQ;
    @FXML
    private TabPane tabPane;
    @FXML
    private HBox hboxProductoSeleccionado;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectorCategorias.getItems().get(0).fire();
        
        presupuestos = new ArrayList();
        Tab t = new Tab();
        Presupuesto p = new Presupuesto();
        
        p.setName("Nuevo Presupuesto");
        presupuestos.add(p);
        actual = 0;
        
        t.setText("Nuevo Presupuesto*");
        tabPane.getTabs().add(t);
        t.setContent(p.getNode());
    }    
    @FXML
    private void acercaDe(ActionEvent event) {
        Alert alertaAcercaDe = new Alert(AlertType.INFORMATION);
        alertaAcercaDe.setTitle("Acerca de...");
        alertaAcercaDe.setHeaderText("Acerca de PC Forge 2016");
        alertaAcercaDe.setContentText("Programa creado por:\n- Angel Garcia\n- Adrian Sospedra\n Grupo 2G1\n\n 2016");
        alertaAcercaDe.showAndWait();
    }

    @FXML
    private void nuevoPresupuesto(ActionEvent event) {
        Tab t = new Tab();
        Presupuesto p = new Presupuesto();
        
        p.setName("Nuevo Presupuesto");
        presupuestos.add(p);
        
        t.setText("Nuevo Presupuesto*");
        tabPane.getTabs().add(t);
        t.setContent(p.getNode());
        actual = tabPane.getSelectionModel().getSelectedIndex();

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
        
        // Ventana de alerta sobre cierre
        
        Alert alertaCierre = new Alert(AlertType.CONFIRMATION);
        alertaCierre.setTitle("Confirmar cierre de presupuesto");
        alertaCierre.setHeaderText("Va a cerrar el presupuesto actual");
        alertaCierre.setContentText("¿Seguro que quiere continuar?");
        Optional<ButtonType> resultado = alertaCierre.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK){
            // Código para cerrar el presupuesto
            
            
        } else {
            alertaCierre.close();
        }
    }

    @FXML
    private void eliminarPresupuestoActual(ActionEvent event) {
        
        // Ventana de alerta sobre eliminación
        
        Alert alertaCierre = new Alert(AlertType.CONFIRMATION);
        alertaCierre.setTitle("Confirmar borrado de presupuesto");
        alertaCierre.setHeaderText("Va a eliminar el presupuesto actual");
        alertaCierre.setContentText("¿Seguro que quiere continuar?");
        Optional<ButtonType> resultado = alertaCierre.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK){
            // Código para eliminar el presupuesto
            //actual-=1;
            
        } else {
            alertaCierre.close(); // si pulsa cancelar
        }
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
    }

    @FXML
    private void buscar(ActionEvent event) {
    }

    @FXML
    private void tableClick(MouseEvent event) {
        Product p = tablaProductos.getSelectionModel().getSelectedItem();
        String catStr = categoryToString(p.getCategory());
        productoCategoria.setText(catStr);
        productoDescripcion.setText(p.getDescription());
        productoPrecio.setText("Precio: " + String.valueOf(p.getPrice()) + " eur.");
        productoStock.setText(String.valueOf(p.getStock()) + " uds. disponibles");
        catImage.setImage(new Image(Logos.class.getResource(catStr + ".png").toString()));
        productoSeleccionadoLabel.setVisible(false);
        productoSeleccionado.setVisible(true);
        hboxProductoSeleccionado.setDisable(!presupuestos.get(actual).addable(new Componente(p, 1)));
    }
    
    public static String categoryToString(Product.Category c) {
        String s;
        switch(c) {
            case SPEAKER:
                s = "Altavoces";
                break;
            case HDD:
                s = "Discos duros";
                break;
            case HDD_SSD:
                s = "Discos duros SSD";
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
    
    public static Product.Category stringToCategory(String s){
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

    @FXML
    private void anadirProductos(ActionEvent event) {
        Componente c = new Componente(
                tablaProductos.getSelectionModel().getSelectedItem(),
                Integer.valueOf(anadirProductosQ.getText())
        );
        presupuestos.get(actual).addComponente(c);
        this.actualizarActual();
        hboxProductoSeleccionado.setDisable(!presupuestos.get(actual).addable(c));
    }

    @FXML
    private void cambiarNombre(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog(presupuestos.get(actual).getName());
        dialog.setHeaderText(null);
        dialog.setTitle("Cambio de nombre");
        dialog.setContentText("Elija un nuevo nombre:");
        Optional<String> result = dialog.showAndWait();
        String res = "";
        if (result.isPresent()){
            res = result.get();
        }
        presupuestos.get(actual).setName(res);
        tabPane.getTabs().get(actual).setText(res);
        this.actualizarActual();
    }

    private void actualizarActual(){
        tabPane.getTabs().get(actual).setContent(presupuestos.get(actual).getNode());
    }

    @FXML
    private void cambiarTab(MouseEvent event) {
        actual = tabPane.getSelectionModel().getSelectedIndex();
    }
}
