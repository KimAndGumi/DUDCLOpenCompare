package org.opencompare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencompare.api.java.Cell;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.Product;

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
            int index = 0;
            for (Feature feature : pcm.getConcreteFeatures()) {

                // Find the cell corresponding to the current feature and product
                Cell cell = product.findCell(feature);

                // Get information contained in the cell
                String content = cell.getContent();
                //String rawContent = cell.getRawContent();
                //Value interpretation = cell.getInterpretation();

                //Sélection des paramètres
                if (index == column){
                	myList.add(content);
                	break;
                }

                // Print the content of the cell
                //System.out.println("(" + product.getKeyContent() + ", " + feature.getName() + ") = " + content);
                index++;
            }
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
	
	@SuppressWarnings("resource")
	@Override
	public void generateHtmlFile(String file) throws IOException{
        // Génération du fichier Html
		//------------------------------------
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
			
			output.write("mode: 'markers',\n");
			output.write("marker: {\n");
			// Liste Size
			output.write("size:" + this.getListToString(getSize()) + "\n");
			
			output.write("} }]\n");
			output.write("TESTER = document.getElementById('tester');\n");
			output.write("Plotly.plot( TESTER, data,{\n");
			output.write("margin: { t:0 }});\n");
			output.write("</script>	</body>	</html>\n");
			//------------------------------------
	        
	        output.flush();
	}


}
