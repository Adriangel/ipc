/*
 * Angel Garcia Camara
 * Adrian Sospedra Martinez
 * Grupo 2G
 */
package entrega2.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.scene.chart.XYChart;

public class Sesion {
    private LocalDateTime start;
    private Duration duration, movingTime;
    private double totalD, tA, tD, maxSpeed, avgSpeed;
    private int minHR, avgHR, maxHR, avgCad, maxCad;
    private XYChart.Series hxd, vxd, fcxd, cxd, hxt, vxt, fcxt, cxt;
    private List<Double> fc;
    
    public Sesion(){
	
    }
    
    public String getFecha(){
	return start.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    
    public String getHora(){
	return start.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
    
    public String getCadencias() {
	return String.valueOf(avgCad) + " / " +String.valueOf(maxCad);
    }
    
    public String getDesnivel(){
	return String.format( "%.2f", (tA - tD) ) + " m";
    }
    
    public String getFrecuenciasCardiacas(){
	return String.valueOf(minHR) + " / " + String.valueOf(avgHR) + " / " + String.valueOf(maxHR);
    }
    
    public String _getDuration(){
	String ret = "";
	ret += (duration.toDays() == 0L)? "": String.valueOf(duration.toDays()) + " días ";
	ret += (duration.toHours() == 0L)? "": String.valueOf(duration.toHours() % 24) + "h ";
	ret += (duration.toMinutes() == 0L)? "": String.valueOf(duration.toMinutes() % 60) + "min ";
	ret += (duration.getSeconds() == 0L)? "": String.valueOf(duration.getSeconds() % 60) + "seg ";
	return   ret;
    }
    
    public String _getMovingTime(){
	String ret = "";
	ret += (movingTime.toDays() == 0L)? "": String.valueOf(movingTime.toDays()) + " días ";
	ret += (movingTime.toHours() == 0L)? "": String.valueOf(movingTime.toHours() % 24) + "h ";
	ret += (movingTime.toMinutes() == 0L)? "": String.valueOf(movingTime.toMinutes() % 60) + "min ";
	ret += (movingTime.getSeconds() == 0L)? "": String.valueOf(movingTime.getSeconds() % 60) + "seg ";
	return   ret;
    }
    
    public String getVelocidades(){
	return String.format( "%.2f", avgSpeed) + " / " + String.format( "%.2f",maxSpeed);
    }
    
    public LocalDateTime getStartTime(){return start;}
    public void setStartTime(LocalDateTime t){
	t.minusNanos(t.getNano());//set nanos to 0
	start = t;
    }

    public Duration getDuration(){return duration;}
    public void setDuration(Duration d){duration = d;}

    public Duration getMovingTime(){return movingTime;}
    public void setMovingTime(Duration mt){movingTime = mt;}

    public double getTotalDistance(){return totalD;}
    public void setTotalDistance(double d) {totalD = d;}

    public double getTotalAscent(){return tA;}
    public void setTotalAscent(double d){tA = d;}

    public double getTotalDescent(){return tD;}
    public void setTotalDescent(double d){tD = d;}

    public double getMaxSpeed(){return maxSpeed;}
    public void setMaxSpeed(double q){maxSpeed = q;}

    public double getAvgSpeed(){return avgSpeed;}
    public void setAvgSpeed(double q){avgSpeed = q;}

    public int getMinHeartrate(){return minHR;}
    public void setMinHeartrate(int q){minHR = q;}

    public int getAvgHeartrate(){return avgHR;}
    public void setAvgHeartrate(int q){avgHR = q;}

    public int getMaxHeartrate(){return maxHR;}
    public void setMaxHeartrate(int q){maxHR = q;}

    public int getAvgCadence(){return avgCad;}
    public void setAvgCadence(int q){avgCad = q;}

    public int getMaxCadence(){return maxCad;}
    public void setMaxCadence(int q){maxCad = q;}

    public XYChart.Series getHeightPerDistance(){return hxd;}
    public void setHeightPerDistance(XYChart.Series s){hxd = s;}

    public XYChart.Series getSpeedPerDistance(){return vxd;}
    public void setSpeedPerDistance(XYChart.Series s){vxd = s;}

    public XYChart.Series getHeartratePerDistance(){return fcxd;}
    public void setHeartratePerDistance(XYChart.Series s){fcxd = s;}

    public XYChart.Series getCadencePerDistance(){return cxd;}
    public void setCadencePerDistance(XYChart.Series s){cxd = s;}

    public XYChart.Series getHeightPerTime(){return hxt;}
    public void setHeightPerTime(XYChart.Series s){hxt = s;}

    public XYChart.Series getSpeedPerTime(){return vxt;}
    public void setSpeedperTime(XYChart.Series s){vxt = s;}

    public XYChart.Series getHeartratePerTime(){return fcxt;}
    public void setHeartratePerTime(XYChart.Series s){fcxt = s;}

    public XYChart.Series getCadencePerTime(){return cxt;}
    public void setCadencePerTime(XYChart.Series s){cxt = s;}
    
    public List<Double> getHeartrate(){return fc;}
    public void setHeartrate(List<Double> fclist){fc = fclist;}
}