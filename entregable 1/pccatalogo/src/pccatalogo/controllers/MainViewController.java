/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pccatalogo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        selectorCategorias.setText("Categoria > " + firer.getText());
    }
    
}
