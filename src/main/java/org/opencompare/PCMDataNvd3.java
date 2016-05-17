package org.opencompare;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

public class PCMDataNvd3 implements Serializable{

	@JsonProperty("data")
	public List<PCMDataNvd3Point> listData;
	public String title;
	public String label_x;
	public String label_y;
	public List<String> features;
	
	public PCMDataNvd3( String title ){
		//on part du principe que nous aurons un groupe de données à traiter.
		this.title = title;
		listData = new ArrayList<PCMDataNvd3Point>();
		
	}
	
	public void addPoint(String key, Object x, Object y, Object color, Object size){
		listData.add(new PCMDataNvd3Point(x, y, color, size));
	}
}
