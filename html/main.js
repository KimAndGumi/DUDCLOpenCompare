var Fichier = function Fichier(fichier)
{
if(window.XMLHttpRequest) obj = new XMLHttpRequest(); //Pour Firefox, Opera,...
else if(window.ActiveXObject) obj = new ActiveXObject("Microsoft.XMLHTTP"); //Pour Internet Explorer
else return(false);
if (obj.overrideMimeType) obj.overrideMimeType("text/xml"); //�vite un bug de Safari
obj.open("GET", fichier, false);
obj.send(null);
if(obj.readyState == 4) return(obj.responseText);
else return(false);
}
var jsonString = Fichier('file.json');
var jsonVariable = JSON.parse(jsonString);
console.log(jsonVariable);	nv.utils.symbolMap.set('thin-x', function(size) {	
		size = Math.sqrt(size);							
		return 'M' + (-size/2) + ',' + (-size/2) +		
		'l' + size + ',' + size +						
		'm0,' + -(size) +								
		'l' + (-size) + ',' + size;						
	});													
	var chart;									
	nv.addGraph(function() {					
		chart = nv.models.scatterChart()		
		.showDistX(true)						
		.showDistY(true)						
		.useVoronoi(true)						
		.style({fill: randomColor});			
	chart.dispatch.on('renderEnd', function(){	
	console.log('render complete');				
	});											
	chart.xAxis.tickFormat(d3.format('.02f'));	
	chart.yAxis.tickFormat(d3.format('.02f'));	
	chart.xAxis.axisLabel(jsonVariable.label_x);					
	chart.yAxis.axisLabel(jsonVariable.label_y);					
	d3.select('#test1 svg')						
	.datum(randomData(4,40))					
	.call(chart);								
	nv.utils.windowResize(chart.update);		
	chart.dispatch.on('stateChange', function(e) { ('New State:', JSON.stringify(e)); });	
		return chart;							
	});											
	function randomData(groups, points) { //# groups,# points per group	
		var data = [],	
		shapes = ['thin-x', 'circle', 'cross', 'triangle-up', 'triangle-down', 'diamond', 'square'],	
		random = d3.random.normal();	
			data.push({					
				key: jsonVariable.title,		
				values: []				
			});							
			for (j = 0; j < jsonVariable.data.length; j++) {		
				data[0].values.push({								
					x: JSON.parse(jsonVariable.data[j].x),			
					y: JSON.parse(jsonVariable.data[j].y),			
					size: JSON.parse(jsonVariable.data[j].size),	
					shape: shapes[1]								
				});													
			}														
		return data;												
	}																
	var randomColor = (function(){							
  	var golden_ratio_conjugate = 0.618033988749895;		
 		var h = Math.random();								
 		var hslToRgb = function (h, s, l){					
    	var r, g, b;										
   	if(s == 0){											
			r = g = b = l; 									
		}else{												
			function hue2rgb(p, q, t){						
			if(t < 0) t += 1;								
			if(t > 1) t -= 1;								
			if(t < 1/6) return p + (q - p) * 6 * t;			
			if(t < 1/2) return q;							
			if(t < 2/3) return p + (q - p) * (2/3 - t) * 6;	
			return p;										
		}													
		var q = l < 0.5 ? l * (1 + s) : l + s - l * s;		
		var p = 2 * l - q;									
		r = hue2rgb(p, q, h + 1/3);							
		g = hue2rgb(p, q, h);								
		b = hue2rgb(p, q, h - 1/3);							
		}													
		return '#'+Math.round(r * 255).toString(16)+Math.round(g * 255).toString(16)+Math.round(b * 255).toString(16);
	};														
	return function(){										
			h += golden_ratio_conjugate;					
			h %= 1;											
			return hslToRgb(h, 0.5, 0.60);					
		};													
	})();													
