package org.opencompare;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
	
	final String htmlTemplateFileName = "html/bootStrap/boot2.html";
	final String scriptFileName = "";
	final String jsonGeneratedFile = "html/file.json";
		
	/*
	 * 
	 */
	public PCMGraphConverter(PCMContainer pcmContainer){
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
		
		Feature feat = this.getFeatures().get(column);
		List<Cell> listCell = feat.getCells();
		if (listCell != null && listCell.size()>0)
		{
			if (listCell.get(0).getInterpretation() != null){
				String classValue = listCell.get(0).getInterpretation().getClass().getName();
			    //System.out.println(classValue);
			    if ((classValue.equals(IntegerValueImpl.class.getName())) || (classValue.equals(RealValueImpl.class.getName())))
			    	bool = true;
			}
		}

        return bool;
	}
	
	public void getGraphData() throws JsonGenerationException, JsonMappingException, IOException{
		// retourne les donn�es pour affichage dans un graphe : au format JSON
	}
	
	public void generateHtmlFile(String file) throws Exception{
		// g�n�ration sauvage du fichier Html + JS
		
		// Lecture et stockage du fichier HTML original dans une liste
		List<String> tabFichier = new ArrayList<String>();
		@SuppressWarnings("resource")
		BufferedReader inputFile = new BufferedReader(new FileReader(htmlTemplateFileName));
		String myLine = inputFile.readLine();
		while (myLine != null ){
			tabFichier.add(myLine);
			myLine = inputFile.readLine();
		}
		
		// Parcours de la liste pour retrouver nos Balises pour insertion de notre code
	    // <!--GENERATED PART BODY-->
	    // <!--END GENERATED PART BODY-->
		int indexBody = tabFichier.indexOf("<!--GENERATED PART BODY-->");

		tabFichier.add(indexBody + 1, this.getHtmlScriptToAdd());

		FileWriter fr = new FileWriter(file);
		BufferedWriter output = new BufferedWriter(fr);
		for(String s : tabFichier){
			output.write(s + "\n");
		}
		output.close();
	}
	
	protected String getHtmlScriptToAdd(){
		return "";
	}
	
	// ajoute par jeremie - Modifi� par C�dric
	// rend la liste des parametres des produits d'un .pcm
	protected List<String> getNameList(){
		
		List<String> nameList = new ArrayList<String>() ;
		List<Feature> listFeat = this.getFeatures();

		for (Feature feat: listFeat)
			nameList.add(feat.getName());
		
			/*
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
			 */
		//essai affichage 
		//System.out.println(nameList.toString());
		return nameList;
	
	}; //getNameList - fin
	
	
	// R�cup�re les Features et les classe suivant le GetName()
	@SuppressWarnings("unchecked")
	protected List<Feature> getFeatures(){
		PCM pcm = pcmContainer.getPcm();
		
		Comparator comparator = new Comparator(){
				@Override
				public int compare(Object o1, Object o2) {
					// TODO Auto-generated method stub
					String s1 = ((Feature) o1).getName();
					String s2 = ((Feature) o2).getName();
					return s1.compareTo(s2);
				}
		};
		List<Feature> listFeat = pcm.getConcreteFeatures();
		Collections.sort( listFeat ,comparator);
		return listFeat;
	}
	
	protected String getMinValue(int column){
		String min = "";
		int i =0;

        PCM pcm = this.getPcmContainer().getPcm();
        
        // Browse the cells of the PCM
        for (Product product : pcm.getProducts()) {
            // Find the cell corresponding to the current feature and product
            Cell cell = product.findCell(this.getFeatures().get(column));

            String content = cell.getContent();
            if (NumberUtils.isNumber(content))//     		StringUtils.isNumericSpace(content))
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
            Cell cell = product.findCell(this.getFeatures().get(column));

            String content = cell.getContent();
            if (NumberUtils.isNumber(content))//     		StringUtils.isNumericSpace(content))            if (StringUtils.isNumericSpace(content))
            	if ((i==0) || Double.parseDouble(content)>Double.parseDouble(max))
            		max = content;
            i++;
        }
 		return max;
 	}
	
	protected String getLabelElement(int column){
		String label = "";
		label = this.getFeatures().get(column).getName();
		return label;
	}

}