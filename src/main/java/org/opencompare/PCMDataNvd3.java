package org.opencompare;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PCMDataNvd3 implements Serializable{
	@XmlElement	
	Map<String,List<PCMDataNvd3Point>> listData;
	@XmlElement	
	String title;
	
	public PCMDataNvd3( String title ){
		//on part du principe que nous aurons un groupe de données à traiter.
		//this.title = title;
		this.title = "A";
		List<PCMDataNvd3Point> listePoint = new ArrayList<PCMDataNvd3Point>();
		listData = new HashMap<String,List<PCMDataNvd3Point>>();
		listData.put(this.title, listePoint);
	}
	
	public void addPoint(Object x, Object y, Object color, Object size){
		//System.out.println("Ajout de : " + x + ";" + y + ";" + color + ";" + size);
		List<PCMDataNvd3Point> liste = listData.get(title);
		liste.add(new PCMDataNvd3Point(x, y, color, size));
	}
	
}
