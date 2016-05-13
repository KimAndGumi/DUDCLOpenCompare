var data = [{
	// Liste X
	x:[1, 2, 3, 4, 5],
	// Liste Y
	y:[1, 2, 4, 8, 16],
	// informations
	text:['a', 'b', 'c', 'd', 'e'],
	mode: 'markers',
	marker: {
		// Liste Size
		size:[40, 60, 80, 100, 50],
		cmin: 40,
		cmax: 100,
		colorscale: [[0,'rgb(100,50,24)'],[1,'rgb(56,100,33)']],
		showscale : true,
		color:[40, 60, 80, 100, 50],
	}}]

var layout = {
	title: 'Mon graphe',
	xaxis: {
		title: 'Text1'},
	yaxis: {
		title: 'Text2'},
	}
TESTER = document.getElementById('tester');
Plotly.plot( TESTER, data,layout);

{margin: { t:0 }};
