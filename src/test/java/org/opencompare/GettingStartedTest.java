package org.opencompare;

import org.junit.Test;
import org.opencompare.api.java.*;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.CSVExporter;
import org.opencompare.api.java.io.PCMLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Néréide, Cédric, Jérémie, Nirina & Soleil
 * commit > pull > push !
 */
public class GettingStartedTest {
	

    @Test
    public void testGettingStarted() throws IOException {

    	List<String> listX = new ArrayList<String>();
    	List<String> listY = new ArrayList<String>();
    	List<String> listSize = new ArrayList<String>();
    	List<String> listColor = new ArrayList<String>();
    	
        // Define a file representing a PCM to load
        File pcmFile = new File("c:\\model\\Comparison_of_PSA_systems_0.pcm");


        // Create a loader that can handle the file format
        PCMLoader loader = new KMFJSONLoader();

        
        // Load the file
        // A loader may return multiple PCM containers depending on the input format
        // A PCM container encapsulates a PCM and its associated metadata
        List<PCMContainer> pcmContainers = loader.load(pcmFile);

        FileWriter fw = new FileWriter("html/monHtml.html", false);
		BufferedWriter output = new BufferedWriter(fw);
		
        for (PCMContainer pcmContainer : pcmContainers) {

            // Get the PCM
            PCM pcm = pcmContainer.getPcm();
            //System.out.println("Affichage d'un PcmContainer\n " + pcmContainer.getMetadata().toString());
            // Browse the cells of the PCM
            for (Product product : pcm.getProducts()) {
                int index = 0;
                for (Feature feature : pcm.getConcreteFeatures()) {

                    // Find the cell corresponding to the current feature and product
                    Cell cell = product.findCell(feature);

                    // Get information contained in the cell
                    String content = cell.getContent();
                    String rawContent = cell.getRawContent();
                    Value interpretation = cell.getInterpretation();

                    //Sélection des paramètres
                    if (index == 1)
                    	listX.add(content);
                    if (index == 2)
                    	listY.add(content);
                    if (index == 3)
                    	listColor.add(content);
                    if (index == 14)
                    	listSize.add(content);

                    
                    // Print the content of the cell
                    System.out.println("(" + product.getKeyContent() + ", " + feature.getName() + ") = " + content);
                    System.out.println(interpretation.getClass().getName());
                    index++;
                }
            }
            
            // Génération du fichier Html
    		//------------------------------------
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
    		String line = "x: [";
    		int i=0;
    		for(String s: listX){ if (i == 0) line += ""+ s; else line += ", " + s; i++;}
    		line += " ],\n";
    		output.write(line);
    		// Liste Y
    		i=0;
    		line = "y: [";
    		for(String s: listY){ if (i == 0) line += ""+ s; else line += ", " + s; i++;}
    		line += " ],\n";
    		output.write(line);
    		
    		output.write("mode: 'markers',\n");
    		output.write("marker: {\n");
    		// Liste Size
    		i=0;
    		line = "size: [";
    		for(String s: listSize){ if (i == 0) line += ""+ s; else line += ", " + s; i++;}
    		line += " ]";
    		output.write(line);
    		
    		output.write("} }]\n");
    		output.write("TESTER = document.getElementById('tester');\n");
    		output.write("Plotly.plot( TESTER, data,{\n");
    		output.write("margin: { t:0 }});\n");
    		output.write("</script>	</body>	</html>\n");
    		//------------------------------------
            
            output.flush();
            
            
            
            // Export the PCM container to CSV
            CSVExporter csvExporter = new CSVExporter();
            String csv = csvExporter.export(pcmContainer);

            // Write CSV content to file
            Path outputFile = Files.createTempFile("oc-", ".csv");
            Files.write(outputFile, csv.getBytes());
            System.out.println("PCM exported to " + outputFile);
        }

    }
}
