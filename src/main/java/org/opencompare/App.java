package org.opencompare;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
	        // Define a file representing a PCM to load
	        File pcmFile = new File("pcms/example.pcm");
	
	        // Create a loader that can handle the file format
	        PCMLoader loader = new KMFJSONLoader();
	
	        // Load the file
	        // A loader may return multiple PCM containers depending on the input format
	        // A PCM container encapsulates a PCM and its associated metadata
	        List<PCMContainer> pcmContainers = loader.load(pcmFile);
	        for (PCMContainer pcmContainer : pcmContainers) {
	        	
	        	PCMGraphConverter graph = new PCMGraphPlotLy(pcmContainer);
	        	if (graph.setParameters(1, 2, 3, 14))
	        		graph.generateHtmlFile("html/monHtml.html");
	        	else
	        		System.err.println("ERROR in parameters : check for numerical values in the PCM choosen columns.");
	        }
		}catch (IOException ioe){
			System.err.println(ioe.getMessage());
		}
		
	}
}
