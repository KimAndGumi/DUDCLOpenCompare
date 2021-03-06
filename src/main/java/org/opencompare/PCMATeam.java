package org.opencompare;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.extractor.CellContentInterpreter;
import org.opencompare.api.java.impl.PCMFactoryImpl;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.CSVLoader;
import org.opencompare.api.java.io.PCMDirection;
import org.opencompare.api.java.io.PCMLoader;

import com.opencsv.CSVWriter;

public class PCMATeam {

	PCMGraphConverter graph;
	
	public PCMATeam(String file, String library){
		try
		{
			List<PCMContainer> pcmContainers = null;
			pcmContainers = getContainers(file);
			// Default Case, we use Plotly graph library
			for (PCMContainer pcmContainer : pcmContainers)
				graph = new PCMGraphPlotLy(pcmContainer);
			
		}catch(Exception e){
			System.err.println("ERROR when creating the PCM with : " + file + ".");
		}
	}
	
	public PCMATeam( String file, int x, int y, int size, int color, String lib ) throws Exception{
		try
		{
			List<PCMContainer> pcmContainers = null;
			pcmContainers = getContainers(file);
			// Default Case, we use Plotly graph library and we only keep the last pcmContainer
			for (PCMContainer pcmContainer : pcmContainers)
				graph = getGraphConverter(lib,pcmContainer);
			// Verify all columns
			this.setParameters(x, y, color, size);
			
		}catch(Exception e){
			System.err.println("ERROR when creating the PCM with : " + file + ".");
			e.printStackTrace();
		}	
	}
	
	private List<PCMContainer> getContainers( String file ) throws IOException{
		// Define a file representing a PCM to load
        File pcmFile = new File(file);
        PCMLoader loader = null;
        List<PCMContainer> pcmContainers = null;
        
		if (file.endsWith("json")){
	        loader = new CSVLoader(new PCMFactoryImpl(), new CellContentInterpreter(new PCMFactoryImpl()),PCMDirection.PRODUCTS_AS_LINES);
	        pcmContainers = loader.load(JsonToCsv(pcmFile));       
		}else if (file.endsWith("pcm")){
			loader = new KMFJSONLoader();
	        pcmContainers = loader.load(pcmFile);
		}else if (file.endsWith("csv")){
			loader = new CSVLoader(new PCMFactoryImpl(), new CellContentInterpreter(new PCMFactoryImpl()),PCMDirection.PRODUCTS_AS_LINES); 
	        pcmContainers = loader.load(pcmFile);
		}else
			System.err.println("ERROR in filename : " + file + " is not a recognized format.");
		return pcmContainers;
	}
		
	private PCMGraphConverter getGraphConverter( String library , PCMContainer pcmContainer ) throws Exception{

        PCMGraphConverter g = null;
        if (library.equals("Nvd3"))
    		g = new PCMGraphNvd3(pcmContainer);     
        else //if (library.equals("Plot.ly"))
    	   	g = new PCMGraphPlotLy(pcmContainer);

		return g;

	}
	
	private void setParameters(int x, int y, int color, int size) throws Exception{
    	// Set all column index parameters and generate the html file.
		if ((graph != null) && ( graph.setParameters(x, y, color, size)))
    		graph.generateHtmlFile("html/index.html");
    	else
    		System.err.println("ERROR in parameters : check for numerical values in the PCM choosen columns.");
	}
	
	public List<String> getFeaturesList(){
		// return all Features labels for the wizard
		// All numerical Feature has <num> prefix
		List<String> l = new ArrayList<String>();
		if (graph != null)
			l = graph.getNameList();
			for (int i =0; i<l.size(); i++){
				if (graph.isComparable(i))
					l.set(i, "<num>" + l.get(i));
			}
				
		return l;
	}
	
	public File JsonToCsv(File f){
		File fReturn = null;
		try{
			HashMap<String,List<String>> listeCsv = new HashMap<String,List<String>>();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(f);
			int nb_entry = 0;
			if (node.isArray()){
				
				for (JsonNode n : node){
					JsonNode s = n.findValue("fields");
				    Iterator<Map.Entry<String, JsonNode>> iter = s.getFields();
				    while (iter.hasNext()) {
				        Map.Entry<String, JsonNode> e = iter.next();
				        String key = e.getKey();
				        String value = e.getValue().toString();
				        //System.out.println( e.getKey() + " , " + e.getValue().toString());
				        List<String> liste = new ArrayList<String>();
				        if (listeCsv.containsKey(key)){
				        	liste = listeCsv.get(key);
				        }
				        liste.add(value.replace(",", "")); // suppression de la virgule en cas de champs multiple ou 1000 format US
				        listeCsv.put(key, liste);
				    }
			    	nb_entry++;
				}
			}
					
			String path = "html/" +f.getName()+".csv";
			FileWriter newFile = new FileWriter(path);
			Set<String> l = listeCsv.keySet();
			int i=0;
			for (String s: l){
				if (i==0)
					newFile.write(s);
				else
					newFile.write("," + s);
				i++;
			}
			newFile.write("\n");
			i=0;
			while( i < nb_entry){
				int j=0;
				for (String s: l){
					String str = listeCsv.get(s).get(i).replaceAll("\"", "");
					if (listeCsv.get(s).get(i).indexOf("unknown")>=0 || listeCsv.get(s).get(i).indexOf("null")>=0 || listeCsv.get(s).get(i).indexOf("indefinite")>=0 || listeCsv.get(s).get(i).indexOf("n/a")>=0)
						str = "0";
					if (j==0)
						newFile.write(str);
					else
						newFile.write("," + str);
					j++;
				}
				newFile.write("\n");
				i++;
			}
			newFile.close();
			fReturn = new File(path);
		}catch (Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return fReturn;
	}
}
