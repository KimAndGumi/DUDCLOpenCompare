package org.opencompare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.opencompare.api.java.Cell;
import org.opencompare.api.java.Feature;
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
	/*
	 * 
	 */
	public PCMGraphConverter(PCMContainer pcmContainer){
		//pcmContainer : PCM � traiter
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
		
		// On v�rifie que les param�tres correspondent � des valeurs comparables
		if (isComparable(x) && isComparable(y) && isComparable(color) && isComparable(size) ){
			returnVal = true;
		}
		
		return returnVal;
	}
	
	public boolean isComparable(int column){
		boolean bool = false;
		PCM pcm = this.getPcmContainer().getPcm();
		
		Feature feat = pcm.getConcreteFeatures().get(column);
		List<Cell> listCell = feat.getCells();
		if (listCell != null)
		{
			String classValue = listCell.get(0).getInterpretation().getClass().getName();
		    //System.out.println(classValue);
		    if ((classValue.equals(IntegerValueImpl.class.getName())) || (classValue.equals(RealValueImpl.class.getName())))
		    	bool = true;
		}

        return bool;
	}
	
	public String getGraphData(){
		// retourne les donn�es pour affichage dans un graphe : au format JSON
		return null;
	}
	
	public void generateHtmlFile(String file) throws IOException{
		// g�n�ration sauvage du fichier Html + JS
	}
	
	// ajoute par jeremie
	// rend la liste de parametres des produits d'un .pcm
	protected List<String> getNameList(){
		
		//resultat dans product > feature > getname
		List<String> nameList = new ArrayList<String>() ;
		
			// Get the PCM
			PCM pcm = pcmContainer.getPcm();	
            
			Product pcmProd = pcm.getProducts().get(0); 
			for (Cell pcmCell : pcmProd.getCells()){
				String nomCourant = pcmCell.getFeature().getName();
				nameList.add(nomCourant);
				//System.out.println(pcmCell.getFeature().getName());
			}
		
		//essai affichage 
		//System.out.println(nameList.toString());
		return nameList;
	
	}; //getNameList - fin
	
	protected String getMinValue(int column){
		String min = "";
		int i =0;

        PCM pcm = this.getPcmContainer().getPcm();
        
        // Browse the cells of the PCM
        for (Product product : pcm.getProducts()) {
            // Find the cell corresponding to the current feature and product
            Cell cell = product.findCell(pcm.getConcreteFeatures().get(column));

            String content = cell.getContent();
            if (StringUtils.isNumericSpace(content))
            	if ((i==0) || Double.parseDouble(content)<Double.parseDouble(min))
            		min = content;
            i++;
        }
 		return min;
 	}
	
 	protected String getMaxValue(int column){
		String max = "";
		int i =0;

        PCM pcm = this.getPcmContainer().getPcm();
        
        // Browse the cells of the PCM
        for (Product product : pcm.getProducts()) {
            // Find the cell corresponding to the current feature and product
            Cell cell = product.findCell(pcm.getConcreteFeatures().get(column));

            String content = cell.getContent();
            //if (StringUtils.isNumericSpace(content))
        	if ((i==0) || Double.parseDouble(content)>Double.parseDouble(max))
        		max = content;
            i++;
        }
 		return max;
 	}
	
	protected String getLabelElement(int column){
		String label = "";
		PCM pcm = this.getPcmContainer().getPcm();
		label = pcm.getConcreteFeatures().get(column).getName();
		return label;
	}

}