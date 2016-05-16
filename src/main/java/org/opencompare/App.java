package org.opencompare;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
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
			
			String[] listeParametres = scanParametres(args);
			
			String file = listeParametres[0];
			int x = Integer.parseInt(listeParametres[1]);
			int y = Integer.parseInt(listeParametres[2]);
			int color = Integer.parseInt(listeParametres[3]);
			int size = Integer.parseInt(listeParametres[4]);
			String librairie = listeParametres[5];
			
	        // Define a file representing a PCM to load
	        File pcmFile = new File(file);
	        PCMLoader loader = new KMFJSONLoader();
	        List<PCMContainer> pcmContainers = loader.load(pcmFile);
	        
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
}
