/*
 * Angel Garcia Camara
 * Adrian Sospedra Martinez
 * Grupo 2G
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.bind.JAXBException;

public class mainViewController implements Initializable {
    List<String> gpxFiles;
    int lastIndex = -1;
    @FXML
    private MenuItem abrir;
    @FXML
    private VBox vbox;
    @FXML

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	gpxFiles = new ArrayList<>();
	//gpxFiles.add("C:\\Users\\angel\\Desktop\\Entrega 2 ipc\\Tracks\\20160113-120434-VirtualRide.gpx");
    }

    private void ver(int index) {
	Parent root;
	String s = gpxFiles.get(index);
	try {
	    FXMLLoader loader = new FXMLLoader(
		Entrega2.class.getResource("views/SesionActividad.fxml")
	      );
	    root = loader.load();
	    Stage stage = new Stage();
	    stage.setTitle(new File(s).getName());//Aquí irá el nombre del archivo
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
    private void abrirGPX(ActionEvent event) throws JAXBException  {
	lastIndex++;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos GPX", "*.gpx"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        //fileChooser.setInitialDirectory(new File("C:\\Users\\angel\\Desktop\\Entrega 2 ipc\\Tracks\\"));
        File file = fileChooser.showOpenDialog(vbox.getScene().getWindow());
        if (file == null) {
            return;
        }
        //label.setText("Loading " + file.getName());
        
	gpxFiles.add(file.getAbsolutePath());
	
	HBox hbox = new HBox();
	hbox.getChildren().add(new Label(file.getName()));
	hbox.setSpacing(16);
	hbox.setAlignment(Pos.CENTER);
	Button verBoton = new Button("Ver");
	
	Button quitarBoton = new Button("Quitar");
	verBoton.setId("b" + String.valueOf(lastIndex));
	hbox.getChildren().addAll(verBoton, quitarBoton);
	vbox.getChildren().add(hbox);
	verBoton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent actionEvent) {
		ver(Integer.valueOf(verBoton.getId().substring(1)));
	    }
	});
	
	quitarBoton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent actionEvent) {
		gpxFiles.remove(lastIndex);
		vbox.getChildren().remove(lastIndex);
		lastIndex--;
	    }
	});
	
    }

}
