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
import org.opencompare.api.java.impl.PCMFactoryImpl;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.extractor.CellContentInterpreter;
import org.opencompare.api.java.io.CSVLoader;
import org.opencompare.api.java.io.PCMLoader;


public class App {

	public static void main(String[] args) {
		try
		{
			// arguments, on attends les paramètres suivants:
			// 0 : f:chemin du fichier
			// 1 : x:numéro de colonne du x
			// 2 : y:numéro de colonne du y
			// 3 : color:numéro de colonne du color
			// 4 : size:numéro de colonne du size
			// 5 : l:librairie à utiliser Plot.ly ou Nvd3
			//
			// exemple : f=pcms/example.pcm x=1 y=2 color=2 size=14 l=Plot.ly
			// exemple : f=pcms/example.pcm x=1 y=2 color=2 size=14 l=Nvd3	
			// f=c:/model/starwars/films.json x=1 y=2 color=5 size=14 l=Nvd3
			
			String[] listeParametres = scanParametres(args);
			
			String file = listeParametres[0];
			int x = Integer.parseInt(listeParametres[1]);
			int y = Integer.parseInt(listeParametres[2]);
			int color = Integer.parseInt(listeParametres[3]);
			int size = Integer.parseInt(listeParametres[4]);
			String librairie = listeParametres[5];
			
	        // Define a file representing a PCM to load
	        File pcmFile = new File(file);
	        PCMLoader loader = null;
	        List<PCMContainer> pcmContainers = null;
	        
			if (file.endsWith("json"))
			{
				loader = new KMFJSONLoader();
		        pcmContainers = loader.load(JsonToCsv(pcmFile));
			}else if (file.endsWith("pcm")){
				loader = new KMFJSONLoader();
		        pcmContainers = loader.load(pcmFile);
			}else{
				loader = new CSVLoader(new PCMFactoryImpl(), new CellContentInterpreter(new PCMFactoryImpl())); 

		        pcmContainers = loader.load(pcmFile);
			}
				
	        for (PCMContainer pcmContainer : pcmContainers) {
	        	PCMGraphConverter graph = null;
	        	if (librairie.equals("Plot.ly"))
	        	   	graph = new PCMGraphPlotLy(pcmContainer);
		        else if (librairie.equals("Nvd3"))
	        		graph = new PCMGraphNvd3(pcmContainer);
		        	
	        	if ((graph != null) && ( graph.setParameters(x, y, color, size)))
	        		graph.generateHtmlFile("html/monHtml.html");
	        	else
	        		System.err.println("ERROR in parameters : check for numerical values in the PCM choosen columns.");
		        
	        }
		}catch (IOException ioe){
			System.err.println(ioe.getMessage());
			ioe.printStackTrace();
		}
		

	}
	public static String[] scanParametres(String[] liste){
		String[] returnList = new String[6];
		for(String s: liste){
			s = s.trim();
			String[] line = s.split("=");
			switch(line[0]){
				case "f" : returnList[0] = line[1];break;
				case "x" : returnList[1] = line[1];break;
				case "y" : returnList[2] = line[1];break;
				case "color" : returnList[3] = line[1];break;
				case "size" : returnList[4] = line[1];break;
				case "l" : returnList[5] = line[1];break;
			}					
		}
		return returnList;
	}
	
	public static File JsonToCsv(File f){
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
				        System.out.println( e.getKey() + " , " + e.getValue().toString());
				        List<String> liste = new ArrayList<String>();
				        if (listeCsv.containsKey(key)){
				        	liste = listeCsv.get(key);
				        }
				        liste.add(value);
				        listeCsv.put(key, liste);
				    }
			    	nb_entry++;
				}
			}
			
			String path = "html/" +f.getName()+".csv";
			FileWriter newFile = new FileWriter(path);
			Set<String> l = listeCsv.keySet();

			for (String s: l)
				newFile.write(s + "\t");
			newFile.write("\n");
			int i=0;
			//System.out.println("Affichage taille : "+listeCsv.get(0).size());
			while( i < nb_entry){
				for (String s: l){
					newFile.write(listeCsv.get(s).get(i)+ "\t");
				}
				i++;
				newFile.write("\n");

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
