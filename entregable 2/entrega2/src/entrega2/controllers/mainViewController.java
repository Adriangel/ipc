/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega2.controllers;

import entrega2.Entrega2;
import java.io.File;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import jgpx.model.analysis.TrackData;
import jgpx.model.gpx.Track;
import jgpx.model.jaxb.GpxType;
import jgpx.model.jaxb.TrackPointExtensionT;

/**
 * FXML Controller class
 *
 * @author angel
 */
public class mainViewController implements Initializable {

    
    List<String> gpxFiles;
    @FXML
    private Button b0;
    @FXML
    private MenuItem abrir;

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
	    stage.setMinHeight(700);
	    stage.setMinWidth(840);
	    stage.show();
	    loader.<SesionActividadController>getController().openGpxFile(s);
	} catch (IOException ex) {
	    System.out.println(ex);
	}
	
    }

    @FXML
    private void acercaDe(ActionEvent event) {
        Alert alertaAcercaDe = new Alert(Alert.AlertType.INFORMATION);
        alertaAcercaDe.setTitle("Acerca de...");
        alertaAcercaDe.setHeaderText("Acerca de Diario de actividades 2016");
        alertaAcercaDe.setContentText("Programa creado por:\n- Angel Garcia Camara\n- Adrian Sospedra Martinez\n Grupo 2G\n\n 2016");
        alertaAcercaDe.showAndWait();
    }

    @FXML
    private void abrirGPX(ActionEvent event) throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos GPX", "*.gpx"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        fileChooser.setInitialDirectory(new File("C:\\Users\\angel\\Desktop\\Entrega 2 ipc\\Tracks\\"));
        File file = fileChooser.showOpenDialog(); // AQUI ADRIAN
        if (file == null) {
            return;
        }
        label.setText("Loading " + file.getName());
        JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class, TrackPointExtensionT.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<Object> root = (JAXBElement<Object>) unmarshaller.unmarshal(file);
        GpxType gpx = (GpxType) root.getValue();

        if (gpx != null) {
            trackData = new TrackData(new Track(gpx.getTrk().get(0)));
            showTrackInfo(trackData);
            label.setText("GPX successfully loaded");
        } else {
            label.setText("Error loading GPX from " + file.getName());
        }
    }

    
}
