/*
 * Angel Garcia Camara
 * Adrian Sospedra Martinez
 * Grupo 2G
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
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
    @FXML
    private Button cambiarEjesBoton;
    @FXML
    private NumberAxis areaEjeX;
    @FXML
    private NumberAxis lineEjeX;
    
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
	
	// pieChart de las zonas de FC
	double[] valoresFC=valoresPieChart();
	ObservableList<PieChart.Data> zonasFC = FXCollections.observableArrayList(
		new PieChart.Data("Z1 Recuperación",valoresFC[0]),
		new PieChart.Data("Z2 Fondo",valoresFC[1]),
		new PieChart.Data("Z3 Tempo",valoresFC[2]),
		new PieChart.Data("Z4 Umbral",valoresFC[3]),
		new PieChart.Data("Z5 Anaeróbico",valoresFC[4])
	);
	pieChart.setData(zonasFC);
		
	
    }
    
    // Devuelve los valores de zonas de FC para el pieChart
    private double[] valoresPieChart(){
	Sesion s = ses.getValue();
	List<Double> valoresFC = s.getHeartrate(); //lista de FC
	int tam = valoresFC.size();
	double [] valoresFCporZonas = {0.0,0.0,0.0,0.0,0.0};
	int maxFC = s.getMaxHeartrate();

	for(int i=0; i<tam; i++){
	    if( valoresFC.get(i)<=0.6*maxFC ){ // Z1 recuperacion, <60% de la FC máxima
		valoresFCporZonas[0]+=100/(double)tam;
	    }else if( valoresFC.get(i)>0.6*maxFC && valoresFC.get(i)<=0.7*maxFC ){ // Z2 fondo, 60%-70%
		valoresFCporZonas[1]+=100/(double)tam;
	    }else if( valoresFC.get(i)>0.7*maxFC && valoresFC.get(i)<=0.8*maxFC ){ // Z3 tempo, 70%-80%
		valoresFCporZonas[2]+=100/(double)tam;
	    }else if( valoresFC.get(i)>0.8*maxFC && valoresFC.get(i)<=0.9*maxFC ){ // Z4 umbral, 80%-90%"
		valoresFCporZonas[3]+=100/(double)tam;
	    }else if( valoresFC.get(i)>0.9*maxFC){ // Z5 anaerobico, >90%-100%"
		valoresFCporZonas[4]+=100/(double)tam;
	    }
	}
	
	return(valoresFCporZonas);
    }


    @FXML
    private void cambiarEjes(ActionEvent event) {
	Sesion s = ses.getValue();
	areaChart.getData().clear();
	lineChart.getData().clear();
	if(cambiarEjesBoton.getText().equals("Cambiar eje horizontal a Tiempo")){
	    cambiarEjesBoton.setText("Cambiar eje horizontal a Distancia");
	    areaEjeX.setLabel("Tiempo (seg)");
	    lineEjeX.setLabel("Tiempo (seg)");
	    areaChart.getData().add(s.getHeightPerTime());
	    lineChart.getData().addAll(
		    s.getSpeedPerTime(),
		    s.getHeartratePerTime(),
		    s.getCadencePerTime()
	    );
	    // falta que de verdad cambie el grafico de acuerdo al nuevo eje
	}else{
	    cambiarEjesBoton.setText("Cambiar eje horizontal a Tiempo");
	    areaEjeX.setLabel("Distancia (m)");
	    lineEjeX.setLabel("Distancia (m)");
	    // falta que de verdad cambie el grafico de acuerdo al nuevo eje
	    areaChart.getData().add(s.getHeightPerDistance());
	    lineChart.getData().addAll(
		    s.getSpeedPerDistance(),
		    s.getHeartratePerDistance(),
		    s.getCadencePerDistance()
	    );
	}
    }
}
