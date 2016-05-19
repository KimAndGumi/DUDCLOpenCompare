
// Traitement des données pour le graphe Nvd3
nv.utils.symbolMap.set('thin-x', function(size) {
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
		.color(d3.scale.category10().range())
		.duration(300)
	;
	chart.dispatch.on('renderEnd', function(){
	});
	chart.xAxis.tickFormat(d3.format('.02f'));
	chart.yAxis.tickFormat(d3.format('.02f'));
	chart.xAxis.axisLabel(jsonVariable.label_x);
	chart.yAxis.axisLabel(jsonVariable.label_y);
	d3.select('#test1 svg')
	.datum(addData())
	.call(chart);
	nv.utils.windowResize(chart.update);
	chart.dispatch.on('stateChange', function(e) { ('New State:', JSON.stringify(e)); });
		return chart;
	});

	// Recherche des points à charger dans le graphe
	function addData() {
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
