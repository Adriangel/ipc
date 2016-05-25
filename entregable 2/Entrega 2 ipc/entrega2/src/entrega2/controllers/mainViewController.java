/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega2.controllers;

import entrega2.Entrega2;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author angel
 */
public class mainViewController implements Initializable {

    
    List<String> gpxFiles;
    @FXML
    private Button b0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	gpxFiles = new ArrayList<>();
	gpxFiles.add("C:\\Users\\angel\\Desktop\\Entrega 2 ipc\\Tracks\\20160113-120434-VirtualRide.gpx");
    }

    @FXML
    private void ver(ActionEvent event) {
	Parent root;
	Button origin = (Button) event.getSource();
	String s = gpxFiles.get(Integer.valueOf(origin.getId().substring(1)));
	try {
	    FXMLLoader loader = new FXMLLoader(
		Entrega2.class.getResource("views/SesionActividad.fxml")
	      );
	    root = loader.load();
	    Stage stage = new Stage();
	    stage.setTitle("SesionActividad.fxml");//Aquí irá el nombre del archivo
	    stage.setScene(new Scene(root));
	    stage.setMinHeight(640);
	    stage.setMinWidth(840);
	    stage.show();
	    loader.<SesionActividadController>getController().openGpxFile(s);
	} catch (IOException ex) {
	    System.out.println(ex);
	}
	
    }

    
}
