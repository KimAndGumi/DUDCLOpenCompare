package org.opencompare;

import java.io.IOException;

import org.opencompare.api.java.Cell;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.Product;
import org.opencompare.api.java.Value;

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
		if (isComparable(x) && isComparable(x) && isComparable(x) && isComparable(x) ){
			returnVal = true;
		}
		
		return returnVal;
	}
	
	private boolean isComparable(int column){
		// teste si la colonne peut etre utilisable pour un graphe
		// Get the PCM
        PCM pcm = this.getPcmContainer().getPcm();

        // Find the cell corresponding to the current feature and product
        Product product = pcm.getProducts().get(0);
        Cell cell = product.findCell(pcm.getConcreteFeatures().get(column));
        Value interpretation = cell.getInterpretation();
        
        // A TERMINER !!!!!!!!!!

        return true;
	}
	
	public String getGraphData(){
		// retourne les données pour affichage dans un graphe : au format JSON
		return null;
	}
	
	public void generateHtmlFile(String file) throws IOException{
		// génération sauvage du fichier Html
	}
	
}
