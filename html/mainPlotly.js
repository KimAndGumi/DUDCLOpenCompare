// Traitement des données pour le graphe Plotly
var data = [{
x:JSON.parse(jsonVariable.x),
y:JSON.parse(jsonVariable.y),
text:jsonVariable.text,
mode: 'markers',
marker: {
size:JSON.parse(jsonVariable.size),
cmin: JSON.parse(jsonVariable.minColor),
cmax: JSON.parse(jsonVariable.maxColor),
colorscale: [[0,'rgb(0,0,255)'],[0.5,'rgb(255,255,0)'],[1,'rgb(255,0,0)']],
showscale : true,
color:JSON.parse(jsonVariable.color),
} }]
var layout = {
//	title: 'Comparison_of_Nikon_DSLR_cameras',
	xaxis: {
		title: jsonVariable.label_x
	},
	yaxis: {
		title: jsonVariable.label_y
	},
}
TESTER = document.getElementById('tester');
Plotly.plot( TESTER, data,layout);
{margin: { t:0 }};
