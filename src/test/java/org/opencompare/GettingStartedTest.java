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
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Néréide, Cédric, Jérémie, Nirina & Soleil
 * commit > pull > push !
 */
public class GettingStartedTest {

    @Test
    public void testGettingStarted() throws IOException {

    	
    	
        // Define a file representing a PCM to load
        File pcmFile = new File("pcms/example.pcm");


        // Create a loader that can handle the file format
        PCMLoader loader = new KMFJSONLoader();

        
        // Load the file
        // A loader may return multiple PCM containers depending on the input format
        // A PCM container encapsulates a PCM and its associated metadata
        List<PCMContainer> pcmContainers = loader.load(pcmFile);

        FileWriter fw = new FileWriter("html/monHtml.html", false);
		BufferedWriter output = new BufferedWriter(fw);
 
		//------------------------------------
		output.write("<!DOCTYPE html>");
		output.write("<html>");
		output.write("<head></head>");
		output.write("<body>");
		output.write("<div id=\"tester\" style=\"width:600px;height:250px;\"></div>");
		output.write("<!-- plot.ly -->");
		output.write("<script src=\"js/plotly-latest.min.js\"></script>");
		output.write("<script>");
		output.write("var data = [{");
		output.write("x: [1, 2, 3, 4, 5],");
		output.write("y: [1, 2, 4, 8, 16],");
		output.write("mode: 'markers',");
		output.write("marker: {");
		output.write("size: [40, 60, 80, 100, 50]");
		output.write("} }]");
		output.write("TESTER = document.getElementById('tester');");
		output.write("Plotly.plot( TESTER, data,{");
		output.write("margin: { t:0 }});");
		output.write("</script>	</body>	</html>");
		//------------------------------------
		
		
        for (PCMContainer pcmContainer : pcmContainers) {

            // Get the PCM
            PCM pcm = pcmContainer.getPcm();
            
//            output.write("<H1>"+pcm.getName()+"</H1>");
//            output.write("<H2>"+pcm.getProducts().size()+" éléments</H2>");
            
//            output.write("<style>#chart svg {height: 400px;}</style>");
//            output.write("<div id=\"chart\"><svg></svg></div>");


            
            output.write("<ul>");
            // Browse the cells of the PCM
            for (Product product : pcm.getProducts()) {
//                output.write("<li>"+product.getKeyContent()+"</li>");
                
                for (Feature feature : pcm.getConcreteFeatures()) {

                    // Find the cell corresponding to the current feature and product
                    Cell cell = product.findCell(feature);

                    // Get information contained in the cell
                    String content = cell.getContent();
                    String rawContent = cell.getRawContent();
                    Value interpretation = cell.getInterpretation();

                    // Print the content of the cell
                    System.out.println("(" + product.getKeyContent() + ", " + feature.getName() + ") = " + content);
                }
            }
//            output.write("</ul>");
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
