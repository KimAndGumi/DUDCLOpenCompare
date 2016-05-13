package org.opencompare;

import java.io.IOException;

import org.opencompare.api.java.Cell;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.Product;
import org.opencompare.api.java.Value;
import org.opencompare.api.java.impl.value.IntegerValueImpl;
import org.opencompare.api.java.impl.value.RealValueImpl;

public abstract class PCMGraphConverter {

	private PCMContainer pcmContainer;
	private int x;
	private int y;
	private int size;
	private int color;
	
	public PCMGraphConverter(PCMContainer pcmContainer){
		//pcmContainer : PCM à traiter
		this.pcmContainer = pcmContainer;
	}
	
	protected PCMContainer getPcmContainer() {
		return pcmContainer;
	}

	protected int getX() {
		return x;
	}

	protected int getY() {
		return y;
	}

	protected int getSize() {
		return size;
	}

	protected int getColor() {
		return color;
	}

	public boolean setParameters(int x, int y, int color, int size){
		// x, y, color, size : numero des colonnes du PCM.
		// alimente les listes en fonction
		this.x =x;
		this.y = y;
		this.size = size;
		this.color = color;
		
		boolean returnVal = false;
		
		// On vérifie que les paramètres correspondent à des valeurs comparables
		if (isComparable(x) && isComparable(y) && isComparable(color) && isComparable(size) ){
			returnVal = true;
		}
		
		return returnVal;
	}
	
	private boolean isComparable(int column){
		boolean bool = false;
		PCM pcm = this.getPcmContainer().getPcm();

        // Find the cell corresponding to the current feature and product
        Product product = pcm.getProducts().get(0);
        Cell cell = product.findCell(pcm.getConcreteFeatures().get(column));
		Value interpretation = cell.getInterpretation();
        String classValue = interpretation.getClass().getName();
        //System.out.println(classValue);
        if ((classValue.equals(IntegerValueImpl.class.getName())) || (classValue.equals(RealValueImpl.class.getName())))
        	bool = true;
        return bool;
	}
	
	public String getGraphData(){
		
		//String json = new Gson().toJson(foo );
		// retourne les données pour affichage dans un graphe : au format JSON
		return null;
	}
	
	public void generateHtmlFile(String file) throws IOException{
		// génération sauvage du fichier Html
	}
	// ajoute par jeremie
	// rend la liste de parametres des produits d'un .pcm
	public List<String> getNameList(File pcmFile) throws IOException	{
		
		//ancien appel dans gettingStartedTest.java
		//File pcmFile = new File("pcms/example.pcm");

		// Create a loader that can handle the file format
		PCMLoader loader = new KMFJSONLoader();

		// Load the file
		// A loader may return multiple PCM containers depending on the input format
		// A PCM container encapsulates a PCM and its associated metadata
		List<PCMContainer> pcmContainers = loader.load(pcmFile);
	
		//resultat dans product > feature > getname
		List<String> nameList = new ArrayList<String>() ;
		
		for (PCMContainer pcmContainer : pcmContainers) {
        	
			// Get the PCM
			PCM pcm = pcmContainer.getPcm();	
            
			Product pcmProd = pcm.getProducts().get(0); 
			for (Cell pcmCell : pcmProd.getCells()){
				String nomCourant = pcmCell.getFeature().getName();
				nameList.add(nomCourant);
				//System.out.println(pcmCell.getFeature().getName());
			}
		}
		//essai affichage 
		//System.out.println(nameList.toString());
		return nameList;
	
	} //getNameList - fin
}
