package org.opencompare;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencompare.api.java.Cell;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.Product;
import org.opencompare.api.java.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PCMGraphNvd3 extends PCMGraphConverter{

	public PCMGraphNvd3(PCMContainer pcmContainer) {
		super(pcmContainer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getGraphData() {
		// TODO Auto-generated method stub
		PCM pcm = getPcmContainer().getPcm();
		
		try{
			ObjectMapper mapper = new ObjectMapper();
			//mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
			PCMDataNvd3 dataNvd3 = new PCMDataNvd3( pcm.getName() );
		
		
	        for (Product product : pcm.getProducts()) {
	        	Object x = product.findCell(pcm.getConcreteFeatures().get(getX())).getContent();
	        	Object y = product.findCell(pcm.getConcreteFeatures().get(getY())).getContent();
	        	Object color = product.findCell(pcm.getConcreteFeatures().get(getColor())).getContent();
	        	Object size = product.findCell(pcm.getConcreteFeatures().get(getSize())).getContent();
	        	dataNvd3.addPoint(x,y,color,size);
	        }

			//Object to JSON in file
			mapper.writeValue(new File("html/file.json"), dataNvd3.listData );
	
			//Object to JSON in String
			//String jsonInString = mapper.writeValueAsString(obj);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void generateHtmlFile(String file) throws IOException {
		// TODO Auto-generated method stub
		super.generateHtmlFile(file);
		
        FileWriter fw = new FileWriter("html/monHtml.html", false);
		BufferedWriter output = new BufferedWriter(fw);
		
		// ******************************* Generation du fichier HTML **********************
		output.write("<!DOCTYPE html>\n");
		output.write("<html>\n");
		output.write("<head>\n");
		output.write("<meta charset=\"utf-8\">\n");
		output.write("<script src=\"js/d3.v3.min.js\"></script>\n");
		output.write("<link href=\"js/nv.d3.css\" rel=\"stylesheet\" type=\"text/css\">\n");
		output.write("<script src=\"js/nv.d3.js\"></script>\n");
		output.write("<style>\n");
		output.write("	text {\n");
		output.write("		font: 12px sans-serif;\n");
		output.write("	}\n");
		output.write("	svg {\n");
		output.write("		display: block;\n");
		output.write("	}\n");
		output.write("	html, body, #test1, svg {\n");
		output.write("		margin: 0px;\n");
		output.write("		padding: 0px;\n");
		output.write("		height: 100%;\n");
		output.write("		width: 100%;\n");
		output.write("	}\n");
		output.write("</style>\n");
		output.write("</head>\n");
		output.write("<body>\n");


		output.write("	<div id=\"test1\" class='with-3d-shadow with-transitions'>\n");
		output.write("		<svg></svg>\n");
		output.write("	</div>\n");
		
		output.write("<script src=\"main.js\" type=\"text/javascript\"></script>\n");
		output.write("</body>\n	</html>\n");
		
		output.flush();
		
		// ******************************* Generation du fichier JSON **********************
		this.getGraphData();
        // ******************************* Generation du fichier JS ************************
        
        fw = new FileWriter("html/main.js", false);
		output = new BufferedWriter(fw);
		
//		output.write("<script>\n");

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
		output.write("console.log(jsonVariable);");
		
		   // register our custom symbols to nvd3
		   // make sure your path is valid given any size because size scales if the chart scales.
		output.write("	nv.utils.symbolMap.set('thin-x', function(size) {	\n");
		output.write("		size = Math.sqrt(size);							\n");
		output.write("		return 'M' + (-size/2) + ',' + (-size/2) +		\n");
		output.write("		'l' + size + ',' + size +						\n");
		output.write("		'm0,' + -(size) +								\n");
		output.write("		'l' + (-size) + ',' + size;						\n");
		output.write("	});													\n");

		   // create the chart
		output.write("	var chart;									\n");
		output.write("	nv.addGraph(function() {					\n");
		output.write("		chart = nv.models.scatterChart()		\n");
		output.write("		.showDistX(true)						\n");
		output.write("		.showDistY(true)						\n");
		output.write("		.useVoronoi(true)						\n");
		output.write("		.color(d3.scale.category10().range())	\n");
		output.write("		.duration(300)							\n");
		output.write("	;											\n");
		output.write("	chart.dispatch.on('renderEnd', function(){	\n");
		output.write("	console.log('render complete');				\n");
		output.write("	});											\n");

		output.write("	chart.xAxis.tickFormat(d3.format('.02f'));	\n");
		output.write("	chart.yAxis.tickFormat(d3.format('.02f'));	\n");

		output.write("	d3.select('#test1 svg')						\n");
		output.write("	.datum(randomData(4,40))					\n");
		output.write("	.call(chart);								\n");

		output.write("	nv.utils.windowResize(chart.update);		\n");

		output.write("	chart.dispatch.on('stateChange', function(e) { ('New State:', JSON.stringify(e)); });	\n");
		output.write("		return chart;							\n");
		output.write("	});											\n");


		output.write("	function randomData(groups, points) { //# groups,# points per group	\n");
		       // smiley and thin-x are our custom symbols!
		output.write("		var data = [],	\n");
		output.write("		shapes = ['thin-x', 'circle', 'cross', 'triangle-up', 'triangle-down', 'diamond', 'square'],	\n");
		output.write("		random = d3.random.normal();	\n");
/*
		output.write("		for (i = 0; i < groups; i++) {	\n");
		output.write("			data.push({					\n");
		output.write("				key: 'Group ' + i,		\n");
		output.write("				values: []				\n");
		output.write("			});							\n");

		output.write("			for (j = 0; j < points; j++) {	\n");
		output.write("				data[i].values.push({		\n");
		output.write("					x: random(),			\n");
		output.write("					y: random(),			\n");
		output.write("					size: Math.round(Math.random() * 100) / 100,	\n");
		output.write("					shape: shapes[j % shapes.length]				\n");
		output.write("				});													\n");
		output.write("			}														\n");
		output.write("		}															\n");
 */
		output.write("			data.push({					\n");
		output.write("				key: 'Group ' + 1,		\n");
		output.write("				values: []				\n");
		output.write("			});							\n");
		output.write("			for (j = 0; j < jsonVariable.A.length; j++) {		\n");
		output.write("				data[0].values.push({							\n");
		output.write("					x: JSON.parse(jsonVariable.A[j].x),			\n");
		output.write("					y: JSON.parse(jsonVariable.A[j].y),			\n");
		output.write("					size: JSON.parse(jsonVariable.A[j].size),	\n");
		output.write("					shape: shapes[1]							\n");
		output.write("				});													\n");
		output.write("			}														\n");
						
//		output.write("			values = jsonVariable.A;");

		output.write("		console.log(jsonVariable.A[0]);												\n");
		output.write("		console.log(data);												\n");
		output.write("		return data;												\n");
		output.write("	}																\n");

//		output.write("</script>\n");
//		output.write("</body>\n");
//		output.write("</html>\n");
		// -------------------------------------------------------------------
		output.flush();
	}

}
