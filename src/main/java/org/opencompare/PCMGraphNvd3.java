package org.opencompare;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.Product;


public class PCMGraphNvd3 extends PCMGraphConverter{

	
	public PCMGraphNvd3(PCMContainer pcmContainer) {
		super(pcmContainer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getGraphData() {
		super.getGraphData();
		// TODO Auto-generated method stub
		PCM pcm = getPcmContainer().getPcm();
		
		try{
			ObjectMapper mapper = new ObjectMapper();
			PCMDataNvd3 dataNvd3 = new PCMDataNvd3( pcm.getName() );
			dataNvd3.label_x = this.getLabelElement(getX());
			dataNvd3.label_y = this.getLabelElement(getY());
			dataNvd3.features = this.getNameList();

			mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
			
	        for (Product product : pcm.getProducts()) {
	        	Object x = product.findCell(pcm.getConcreteFeatures().get(getX())).getContent();
	        	Object y = product.findCell(pcm.getConcreteFeatures().get(getY())).getContent();
	        	Object color = product.findCell(pcm.getConcreteFeatures().get(getColor())).getContent();
	        	Object size = product.findCell(pcm.getConcreteFeatures().get(getSize())).getContent();
	        	dataNvd3.addPoint(product.getKeyContent(),x,y,color,size);
	        }

			mapper.writeValue(new File("html/file.json"), dataNvd3 );		
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected String getHtmlScriptToAdd(){
		String s = "";
		PCM pcm = getPcmContainer().getPcm();
		
		s = "<script src=\"js/d3.v3.min.js\"></script> \n";
		s += "<link href=\"js/nv.d3.css\" rel=\"stylesheet\" type=\"text/css\"> \n";
		s += "<script src=\"js/nv.d3.js\"></script>\n";
		s += "<H3><center>"+pcm.getName()+"</center></H3>\n";
		s += "<div id=\"test1\" class='with-3d-shadow with-transitions'>\n";
		s += "<svg></svg>\n";
		s += "</div>\n";
		s += "<script src=\"functions.js\" type=\"text/javascript\"></script>\n";
		s += "<script src=\"mainNvd3.js\" type=\"text/javascript\"></script>\n";
		s += "<style> \n";
		s += "	div,#test1, svg {\n";
		s += "		margin: 0px;\n";
		s += "		padding: 0px;\n";
		s += "		height: 95%;\n";
		s += "		width: 100%;\n";
		s += "	}\n";
		s += "</style>\n";
		return s;
	}
	
	@Override
	public void generateHtmlFile(String file) throws IOException {
		super.generateHtmlFile("html/index.html");
		this.getGraphData();
	}

}
