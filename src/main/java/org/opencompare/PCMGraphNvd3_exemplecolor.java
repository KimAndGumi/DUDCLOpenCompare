///code color
//******************************* Generation du fichier JS ************************

fw = new FileWriter("html/main.js", false);
output = new BufferedWriter(fw);

output.write("var Fichier = function Fichier(fichier)\n");
output.write("{\n");
output.write("if(window.XMLHttpRequest) obj = new XMLHttpRequest(); //Pour Firefox, Opera,...\n");
output.write("else if(window.ActiveXObject) obj = new ActiveXObject(\"Microsoft.XMLHTTP\"); //Pour Internet Explorer\n");
output.write("else return(false);\n");
output.write("if (obj.overrideMimeType) obj.overrideMimeType(\"text/xml\"); //�vite un bug de Safari\n");

output.write("obj.open(\"GET\", fichier, false);							\n");
output.write("obj.send(null);												\n");

output.write("if(obj.readyState == 4) return(obj.responseText);				\n");
output.write("else return(false);											\n");
output.write("}																\n");

output.write("var jsonString = Fichier('file.json');						\n");
output.write("var jsonVariable = JSON.parse(jsonString);					\n");
output.write("console.log(jsonVariable);");

   // register our custom symbols to nvd3
   // make sure your path is valid given any size because size scales if the chart scales.
output.write("	nv.utils.symbolMap.set('thin-x', function(size) {			\n");
output.write("		size = Math.sqrt(size);									\n");
output.write("		return 'M' + (-size/2) + ',' + (-size/2) +				\n");
output.write("		'l' + size + ',' + size +								\n");
output.write("		'm0,' + -(size) +										\n");
output.write("		'l' + (-size) + ',' + size;								\n");
output.write("	});															\n");

   // create the chart
output.write("	var chart;													\n");
output.write("	nv.addGraph(function() {									\n");
output.write("		chart = nv.models.scatterChart()						\n");
output.write("		.showDistX(true)										\n");
output.write("		.showDistY(true)										\n");
output.write("		.useVoronoi(true)										\n");
output.write("		.color(d3.scale.category10().range())					\n");
output.write("		.duration(350)											\n");

output.write("		chart.tooltipContent(function(key) {					\n");
output.write("    		return '<h3>' + key + '</h3>';						\n");
output.write("		});														\n");

output.write("		chart.xAxis.tickFormat(d3.format('.02f'));				\n");
output.write("		chart.yAxis.tickFormat(d3.format('.02f'));				\n");
output.write("		chart.xAxis.axisLabel(jsonVariable.label_x);			\n");
output.write("		chart.yAxis.axisLabel(jsonVariable.label_y);			\n");

output.write("		d3.select('#test1 svg')									\n");
output.write("			.datum(randomData(4,40))							\n");
output.write("			.call(chart);										\n");

output.write("		nv.utils.windowResize(chart.update);					\n");

//output.write("	chart.dispatch.on('renderEnd', function(){					\n");
//output.write("	console.log('render complete');								\n");
//output.write("	});															\n");

//output.write("	chart.dispatch.on('stateChange', function(e) { ('New State:', JSON.stringify(e)); });	\n");
output.write("		return chart;											\n");
output.write("	});															\n");

output.write("	function randomData(groups, points) { //# groups,# points per group	\n");
       // smiley and thin-x are our custom symbols!
output.write("		var data = [],											\n");
output.write("		shapes = ['circle', 'cross', 'triangle-up', 'triangle-down', 'diamond', 'square'],	\n");
output.write("		random = d3.random.normal();							\n");

output.write("		for (i = 0; i < groups; i++) 							\n");
output.write("		{														\n");

output.write("			data.push({											\n");
output.write("				key: 'Group ' + i,								\n");
output.write("				values: []										\n");
output.write("			});													\n");

output.write("			for (j = 0; j < points; j++) {						\n");
output.write("				data[i].values.push({							\n");
output.write("					x: JSON.parse(jsonVariable.data[j].x),		\n");
output.write("					y: JSON.parse(jsonVariable.data[j].y),		\n");
output.write("					size: JSON.parse(jsonVariable.data[j].size),\n");
output.write("					shape: (Math.random() > 0.95) ? shapes[j % 6] : 'circle'	\n");
//output.write("					shape: shapes[1]							\n");

output.write("				});												\n");
output.write("			}													\n");
output.write("		}														\n");
		
output.write("		return data;											\n");
output.write("	}															\n");
//output.write("	}														\n");

output.flush();
}
