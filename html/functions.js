// Création d'un feature dans le bandeau de gauche
function addFeature(texte)
{
    var newLabel=document.createElement('Label');
    newLabel.innerHTML = texte;

    var newInput=document.createElement('input');
    var retour=document.createElement('br');

    newInput.setAttribute('type','checkbox');
    newInput.setAttribute('id','texte');

    document.getElementById('features').appendChild(newInput);
    document.getElementById('features').appendChild(newLabel);
    document.getElementById('features').appendChild(retour);
}

// Lecture d'un fichier
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

// Lecture du fichier .json
var jsonString = Fichier('file.json');
var jsonVariable = JSON.parse(jsonString);

// ajout des features dans la partie gauche
for (j = 0; j < jsonVariable.features.length; j++) {
	addFeature(jsonVariable.features[j]);
}
