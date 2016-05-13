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
console.log(JSON.parse(jsonString));
var data = [{
x:JSON.parse(jsonVariable.x),
y:JSON.parse(jsonVariable.y),
text:JSON.parse(jsonVariable.x),
mode: 'markers',
marker: {
size:JSON.parse(jsonVariable.size),
cmin: 800,
cmax: 25600,
colorscale: [[0,'rgb(100,50,24)'],[1,'rgb(56,100,33)']],
showscale : true,
color:JSON.parse(jsonVariable.color),
} }]
var layout = {
title: 'Comparison_of_Nikon_DSLR_cameras',
xaxis: {
	title: 'Weight (g)'
},
yaxis: {
	title: 'Focus points'
},
}
TESTER = document.getElementById('tester');
Plotly.plot( TESTER, data,layout);
{margin: { t:0 }};
