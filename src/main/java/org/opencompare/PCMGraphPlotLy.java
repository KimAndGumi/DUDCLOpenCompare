package org.opencompare;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.opencompare.api.java.Cell;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.Product;

import com.fasterxml.jackson.databind.ObjectMapper;


public class PCMGraphPlotLy extends PCMGraphConverter{
	
	public PCMGraphPlotLy(PCMContainer pcmContainer) {
		super(pcmContainer);
	}
	
	private List<String> getElements(int column){
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

	private String getLabelElement(int column){
		String label = "";
		PCM pcm = this.getPcmContainer().getPcm();
		label = pcm.getConcreteFeatures().get(column).getName();
		return label;
	}
	
	private List<String> getProductTitles(){
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
	
 	private String getMinValue(int column){
		String min = "";
		int i =0;

        PCM pcm = this.getPcmContainer().getPcm();
        
        // Browse the cells of the PCM
        for (Product product : pcm.getProducts()) {
            // Find the cell corresponding to the current feature and product
            Cell cell = product.findCell(pcm.getConcreteFeatures().get(column));

            String content = cell.getContent();
            if (StringUtils.isNumericSpace(content))
            	if ((i==0) || Double.parseDouble(content)<Double.parseDouble(min))
            		min = content;
            i++;
        }
 		return min;
 	}
	
	private String getMaxValue(int column){
		String max = "";
		int i =0;

        PCM pcm = this.getPcmContainer().getPcm();
        
        // Browse the cells of the PCM
        for (Product product : pcm.getProducts()) {
            // Find the cell corresponding to the current feature and product
            Cell cell = product.findCell(pcm.getConcreteFeatures().get(column));

            String content = cell.getContent();
            //if (StringUtils.isNumericSpace(content))
        	if ((i==0) || Double.parseDouble(content)>Double.parseDouble(max))
        		max = content;
            i++;
        }
 		return max;
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
	
	@SuppressWarnings("resource")
	@Override
	public void generateHtmlFile(String file) throws IOException{
        
		// Génération du fichier Html
		//------------------------------------
/*
			PCM pcm = getPcmContainer().getPcm();
			
	        FileWriter fw = new FileWriter("html/monHtml.html", false);
			BufferedWriter output = new BufferedWriter(fw);

			output.write("<!DOCTYPE html>\n");
			output.write("<html>\n");
			output.write("<head></head>\n");
			output.write("<body>\n");
			output.write("<H1>"+pcm.getName()+"</H1>");
	      	output.write("<H2>"+pcm.getProducts().size()+" éléments</H2>");
			output.write("<div id=\"tester\" style=\"width:800px;height:500px;\"></div>\n");
			output.write("<!-- plot.ly -->\n");
			output.write("<script src=\"js/plotly-latest.min.js\"></script>\n");
			output.write("<script>\n");
			output.write("var data = [{\n");
			// Liste X
			output.write("x:" + this.getListToString(getX()) + ",\n");
			// Liste Y
			output.write("y:" + this.getListToString(getY()) + ",\n");
			// informations
			output.write("text:" + this.getTextTitles() + ",\n");
			
			output.write("mode: 'markers',\n");
			output.write("marker: {\n");
			// Liste Size
			output.write("size:" + this.getListToString(getSize()) + ",\n");
			output.write("cmin: " + this.getMinValue(this.getColor()) + ",\n");//valeur numÃ©rique minimum-->min(z?)
			output.write("cmax: " + this.getMaxValue(this.getColor()) + ",\n");//valeur numÃ©rique maximum-->max(z?)
			output.write("colorscale: [[0,'rgb(100,50,24)'],[1,'rgb(56,100,33)']],\n");
			output.write("showscale : true,\n");
			output.write("color:" + this.getListToString(getColor()) + ",\n");
			output.write("} }]\n");
			
			// Affichage de la légende
			output.write("var layout = {\n");
			output.write("title: '" + pcm.getName() + "',\n");
			output.write("xaxis: {	\n	title: '" + this.getLabelElement(this.getX()) + "'\n},\n");
			output.write("yaxis: {	\n	title: '" + this.getLabelElement(this.getY()) + "'\n},\n");
			output.write("}\n");

			output.write("TESTER = document.getElementById('tester');\n");
			output.write("Plotly.plot( TESTER, data,layout);\n");
			//output.write("{margin: { t:0 }});\n");
			output.write("</script>	</body>	</html>\n");
			//------------------------------------
*/
			PCM pcm = getPcmContainer().getPcm();

			// ******************************* Generation du fichier Html ************************
	        FileWriter fw = new FileWriter("html/monHtml.html", false);
			BufferedWriter output = new BufferedWriter(fw);
			output.write("<!DOCTYPE html>\n");
			output.write("<html>\n");
			output.write("<head></head>\n");
			output.write("<body>\n");
			output.write("<H1>"+pcm.getName()+"</H1>");
	      	output.write("<H2>"+pcm.getProducts().size()+" éléments</H2>");
			output.write("<div id=\"tester\" style=\"width:800px;height:500px;\"></div>\n");
			output.write("<!-- plot.ly -->\n");
			output.write("<script src=\"js/plotly-latest.min.js\"></script>\n");
			output.write("<script src=\"main.js\" type=\"text/javascript\"></script>\n");
			output.write("</body>\n	</html>\n");
	        output.flush();

			// ******************************* Génération du JSON ******************************
			this.getGraphData();
	        
	        // ******************************* Generation du fichier JS ************************
	        
	        fw = new FileWriter("html/main.js", false);
			output = new BufferedWriter(fw);
			
			output.write("var Fichier = function Fichier(fichier)\n");
			output.write("{\n");
			output.write("if(window.XMLHttpRequest) obj = new XMLHttpRequest(); //Pour Firefox, Opera,...\n");
			output.write("else if(window.ActiveXObject) obj = new ActiveXObject(\"Microsoft.XMLHTTP\"); //Pour Internet Explorer\n");
			output.write("else return(false);\n");
			output.write("if (obj.overrideMimeType) obj.overrideMimeType(\"text/xml\"); //Évite un bug de Safari\n");

			output.write("obj.open(\"GET\", fichier, false);\n");
			output.write("obj.send(null);\n");

			output.write("if(obj.readyState == 4) return(obj.responseText);\n");
			output.write("else return(false);\n");
			output.write("}\n");

			output.write("var jsonString = Fichier('file.json');\n");
			output.write("var jsonVariable = JSON.parse(jsonString);\n");
			
			output.write("var data = [{\n");
			// Liste X
			output.write("x:JSON.parse(jsonVariable.x),\n");
			// Liste Y
			output.write("y:JSON.parse(jsonVariable.y),\n");
			// informations
			output.write("text:jsonVariable.text,\n");
			
			output.write("mode: 'markers',\n");
			output.write("marker: {\n");
			// Liste Size
			output.write("size:JSON.parse(jsonVariable.size),\n");
			output.write("cmin: JSON.parse(jsonVariable.minColor),\n");//valeur numÃ©rique minimum-->min(z?)
			output.write("cmax: JSON.parse(jsonVariable.maxColor),\n");//valeur numÃ©rique maximum-->max(z?)
			output.write("colorscale: [[0,'rgb(100,50,24)'],[1,'rgb(56,100,33)']],\n");
			output.write("showscale : true,\n");
			output.write("color:JSON.parse(jsonVariable.color),\n");
			output.write("} }]\n");
			
			// Affichage de la légende
			output.write("var layout = {\n");
			output.write("title: '" + pcm.getName() + "',\n");
			output.write("xaxis: {	\n	title: jsonVariable.label_x\n},\n");
			output.write("yaxis: {	\n	title: jsonVariable.label_y\n},\n");
			output.write("}\n");

			output.write("TESTER = document.getElementById('tester');\n");
			output.write("Plotly.plot( TESTER, data,layout);\n");
			output.write("{margin: { t:0 }};\n");

	        output.flush();
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
