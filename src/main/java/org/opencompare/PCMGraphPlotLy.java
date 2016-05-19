package org.opencompare;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencompare.api.java.Cell;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.Product;

import com.fasterxml.jackson.databind.ObjectMapper;


public class PCMGraphPlotLy extends PCMGraphConverter{
	
	public PCMGraphPlotLy(PCMContainer pcmContainer) {
		super(pcmContainer);
	}
	
	protected List<String> getProductTitles(){
		// Retourne les textes à afficher lors du survol d'un rond du graphique
		List<String> titles = new ArrayList<String>();
		int i=0;
		PCM pcm = this.getPcmContainer().getPcm();
		for (Product product : pcm.getProducts())
			titles.add(product.getKeyContent());

		return titles;
	}
	
	private String getTextTitles(){
		// Retourne les textes à afficher lors du survol d'un rond du graphique
		String titles = "['";
		int i=0;
		PCM pcm = this.getPcmContainer().getPcm();
		for (Product product : pcm.getProducts()) {
			if (i==0)
				titles += product.getKeyContent();
			else
				titles += "','" + product.getKeyContent();
			i++;
		}
		titles += "']";
		return titles;
	}
	
	protected List<String> getElements(int column){
	    List<String> myList = new ArrayList<String>();
		
		// Get the PCM
        PCM pcm = this.getPcmContainer().getPcm();

        // Browse the cells of the PCM
        for (Product product : pcm.getProducts()) {

	        // Find the cell corresponding to the current feature and product
	        Cell cell = product.findCell(pcm.getConcreteFeatures().get(column));
	
	        // Get information contained in the cell
	        String content = cell.getContent();
	        //String rawContent = cell.getRawContent();
	        //Value interpretation = cell.getInterpretation();
	
	        //Sélection des paramètres
	       	myList.add(content);
	
	
	        // Print the content of the cell
	        //System.out.println("(" + product.getKeyContent() + ", " + feature.getName() + ") = " + content);
        }
		return myList;
	}

	private String getListToString( int column ){
		int i=0;
		String line = "[";
		for(String s: getElements(column)){ 
			if (i == 0) 
				line += ""+ s;
			else 
				line += ", " + s;
			i++;
		}
		line += " ]";
		return line;
	}
	
	@Override
	protected String getHtmlScriptToAdd(){
		String s = "";
		PCM pcm = getPcmContainer().getPcm();
		
		s = "<H3><center>"+pcm.getName()+"</center></H3>\n";
      	s += "<div id=\"tester\" style=\"height:800px;\"></div>\n";
		s += "<script src=\"js/plotly-latest.min.js\"></script>\n";
		s += "<script src=\"functions.js\" type=\"text/javascript\"></script>\n";
		s += "<script src=\"mainPlotly.js\" type=\"text/javascript\"></script>\n";
		return s;
	}
	
	
	@Override
	public void generateHtmlFile(String file) throws IOException{
        super.generateHtmlFile("html/index.html");
		this.getGraphData();
	}

	@Override
	public String getGraphData() {
		super.getGraphData();
		PCM pcm = getPcmContainer().getPcm();
		
		try{
		ObjectMapper mapper = new ObjectMapper();
		
		PCMDataPlotLy data = new PCMDataPlotLy();
		data.text = this.getProductTitles();
		data.x = this.getListToString(getX());
		data.y = this.getListToString(getY());
		data.size = this.getListToString(getSize());
		data.color = this.getListToString(getColor());
		
		data.label_x = this.getLabelElement(this.getX());
		data.label_y = this.getLabelElement(this.getY());
		data.title = pcm.getName();
		
		data.features = this.getNameList();
		
		data.minColor = this.getMinValue(this.getColor());
		data.maxColor = this.getMaxValue(this.getColor());
		
		//Object to JSON in file
		mapper.writeValue(new File("html/file.json"), data );

		//Object to JSON in String
		//String jsonInString = mapper.writeValueAsString(obj);
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return null;
	}

}
