/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega2.controllers;

import entrega2.models.Sesion;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import jgpx.model.analysis.Chunk;
import jgpx.model.analysis.TrackData;
import jgpx.model.gpx.Track;
import jgpx.model.jaxb.GpxType;
import jgpx.model.jaxb.TrackPointExtensionT;

/**
 * FXML Controller class
 *
 * @author angel
 */
public class SesionActividadController implements Initializable {
    public SimpleObjectProperty<Sesion> ses;
    
    @FXML
    private GridPane load;
    @FXML
    private GridPane main;
    @FXML
    private Label loadingLabel;
    @FXML
    private Label fecha;
    @FXML
    private Label hora;
    @FXML
    private Label duracion;
    @FXML
    private Label tiempoMovimiento;
    @FXML
    private Label distanciaRecorrida;
    @FXML
    private Label desnivel;
    @FXML
    private Label velocidades;
    @FXML
    private Label frecuenciasCardiacas;
    @FXML
    private Label cadencias;
    @FXML
    private AreaChart areaChart;
    @FXML
    private LineChart lineChart;
    @FXML
    private PieChart pieChart;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	ses = new SimpleObjectProperty();
    }    
    
    public void echo(String s){System.out.println(s);}
    
    public void openGpxFile(String s) {
	
	Task task = null;
	task = new Task<Sesion>() {
	    @Override
	    public Sesion call() {
		TrackData t;
		Sesion ses;
		try {
		    updateMessage("Leyendo fichero GPX...");
		    File f = new File(s);
		    JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class, TrackPointExtensionT.class);
		    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		    JAXBElement<Object> root = (JAXBElement<Object>)
			    unmarshaller.unmarshal(f);
		    GpxType gpx = (GpxType) root.getValue();
		    
		    
		    t = new TrackData(new Track(gpx.getTrk().get(0)));
		    
		    
		    updateMessage("Procesando datos...");
		    ObservableList<Chunk> cList = t.getChunks();
		    XYChart.Series hxd, sxd, fcxd, cxd, hxt, sxt, fcxt, cxt;
		    List<Double> fc;
		    hxd = new XYChart.Series();
		    hxd.setName("Altura (m)");
		    sxd = new XYChart.Series();
		    sxd.setName("Velocidad (km/h)");
		    fcxd = new XYChart.Series();
		    fcxd.setName("Frecuencia Cardiaca (bpm)");
		    cxd = new XYChart.Series();
		    cxd.setName("Cadencia (rmp)");
		    hxt = new XYChart.Series();
		    hxt.setName("Altura (m)");
		    sxt = new XYChart.Series();
		    sxt.setName("Velocidad (km/h)");
		    fcxt = new XYChart.Series();
		    fcxt.setName("Frecuencia Cardiaca (bpm)");
		    cxt = new XYChart.Series();
		    cxt.setName("Cadencia (rmp)");
		    fc = new ArrayList<>();
		    double dist = 0;
		    double time = 0;
		    for (Chunk c : cList) {
			dist += c.getDistance();
			time += c.getDuration().getSeconds();
			hxd.getData().add(new XYChart.Data(dist, c.getAvgHeight()));
			hxt.getData().add(new XYChart.Data(time, c.getAvgHeight()));
			sxd.getData().add(new XYChart.Data(dist, c.getSpeed()));
			sxt.getData().add(new XYChart.Data(time, c.getSpeed()));
			fcxd.getData().add(new XYChart.Data(dist, c.getAvgHeartRate()));
			fcxt.getData().add(new XYChart.Data(time, c.getAvgHeartRate()));
			cxd.getData().add(new XYChart.Data(dist, c.getAvgCadence()));
			cxt.getData().add(new XYChart.Data(time, c.getAvgCadence()));
			fc.add(c.getAvgHeartRate());
		    }
		    ses = new Sesion();
		    ses.setStartTime(t.getStartTime());
		    ses.setDuration(t.getTotalDuration());
		    ses.setMovingTime(t.getMovingTime());
		    ses.setTotalDistance(t.getTotalDistance());
		    ses.setTotalAscent(t.getTotalAscent());
		    ses.setTotalDescent(t.getTotalDescend());
		    ses.setMaxSpeed(t.getMaxSpeed());
		    ses.setAvgSpeed(t.getAverageSpeed());
		    ses.setMinHeartrate(t.getMinHeartRate());
		    ses.setAvgHeartrate(t.getAverageHeartrate());
		    ses.setMaxHeartrate(t.getMaxHeartrate());
		    ses.setAvgCadence(t.getAverageCadence());
		    ses.setMaxCadence(t.getMaxCadence());
		    ses.setHeightPerDistance(hxd);
		    ses.setSpeedPerDistance(sxd);
		    ses.setHeartratePerDistance(fcxd);
		    ses.setCadencePerDistance(cxd);
		    ses.setHeightPerTime(hxt);
		    ses.setSpeedperTime(sxt);
		    ses.setHeartratePerTime(fcxt);
		    ses.setCadencePerTime(cxt);
		    ses.setHeartrate(fc);
		    
		    updateMessage("Dibujando gráficas...");
		}
		catch (Exception e){
		    System.out.println(e);
		    ses = null;
		}
		
		return ses;
	    }
	    
	    @Override protected void succeeded() {
		super.succeeded();
		Platform.runLater(new Runnable() {
		    @Override public void run() {
			try {
			    representSes();
			    load.setVisible(false);
			    main.setVisible(true);
			}
			catch(Exception e){}
		    }

		    
		});
	    }
	};
	
	loadingLabel.textProperty().bind(task.messageProperty());
	ses.bind(task.valueProperty());
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
    
    private void representSes() {
	Sesion s = ses.getValue();
	
	//seteamos los labels
	cadencias.setText(s.getCadencias());
	desnivel.setText(s.getDesnivel());
	distanciaRecorrida.setText(String.format( "%.2f", s.getTotalDistance()) + " metros");
	duracion.setText(s._getDuration());
	fecha.setText(s.getFecha());
	hora.setText(s.getHora());
	frecuenciasCardiacas.setText(s.getFrecuenciasCardiacas());
	tiempoMovimiento.setText(s._getMovingTime());
	velocidades.setText(s.getVelocidades());
	
	//creamos las gráficas
	areaChart.getData().add(s.getHeightPerDistance());
	areaChart.setCreateSymbols(false);
	lineChart.getData().addAll(
		s.getSpeedPerDistance(),
		s.getHeartratePerDistance(),
		s.getCadencePerDistance()
	);
	lineChart.setCreateSymbols(false);

    }

    @FXML
    private void cambiarEdad(ActionEvent event) {
    }

    @FXML
    private void cambiarEjes(ActionEvent event) {
    }
}
