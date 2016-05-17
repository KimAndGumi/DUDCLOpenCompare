package org.opencompare;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;
import org.opencompare.api.java.Cell;
import org.opencompare.api.java.Product;
import org.opencompare.api.java.Value;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class AppTest{
	
	private String folderpath = "pcms/model/";
	//private String[] graphlibrary = ['Plot.ly','Nvd3'];
	private Random rg = new Random();
	private PCMLoader loader = new KMFJSONLoader();
	private PCMGraphConverter graph = null;
	private File[] pcmfiles = new File(folderpath).listFiles();
	/*@Before
	public void init(){
		File directory = new File(folderpath);
		File[] pcmfiles = directory.listFiles();			
		//PCMContainer pcmc = loader.load(pcmfiles[rg.nextInt(pcmfiles.length)])[0];
	}*/
	
	@Test
	public void fullTest(){
		FileWriter fw = new FileWriter("test/log.txt", false);
		BufferedWriter output = new BufferedWriter(fw);
		/*FileWriter fws = new FileWriter("test/success.txt", false);
		BufferedWriter outputs = new BufferedWriter(fw);*/
		FileWriter fwnc = new FileWriter("test/notcomp.txt", false);
		BufferedWriter outputnc = new BufferedWriter(fw);
		output.write("--- Log du test sur les 1400 PCMs---\n");
		int count = 1;
		int success = 0;
		int notdone = 0;
		int x,y,color,size,nombreColonnes;
		boolean noComp = false;
		try{			
			for (File pcmfile : pcmfiles){
				List<PCMContainer> pcmcs = loader.load(pcmfile);
				output.write("* "+pcmfile.getName()+" : "+pcmcs.size()+" PCM\n");
				
				//for (PCMContainer pcmc : pcmcs){
				PCMContainer pcmc = pcmcs.get(0);
					{
					//Extraction du PCM
					//PCM pcm = pcmContainer.getPcm();
					nombreColonnes = pcmc.getPcm().getConcreteFeatures().size();
					//Création de l'objet graph en fonction d'un tirage aléatoire
					output.write("PCM "+count);
					int rnlib = rg.nextInt(1);
					if (rnlib==1){
						graph = new PCMGraphPlotLy(pcmc);
						output.write(" : Plot.ly\n");
					}else{
						graph = new PCMGraphNvd3(pcmc);
						output.write(" : Nvd3.js\n");
					}
					output.write("La liste complète des paramètres est : "+graph.getNameList());
					output.write("Tirage aléatoire des 4 paramètres :\n");
					//Tirage aléatoire des 4 paramètres
					//Une seule vérification de "comparabilité"
					x = rg.nextInt(nombreColonnes);
					if (!graph.isComparable(x))
						x = rg.nextInt(nombreColonnes);
					output.write("x : "+pcmc.getPcm().getConcreteFeatures().get(x).getName()+"\n");
					y = rg.nextInt(nombreColonnes);
					if (!graph.isComparable(y))
						y = rg.nextInt(nombreColonnes);
					output.write("y : "+pcmc.getPcm().getConcreteFeatures().get(y).getName()+"\n");
					color = rg.nextInt(nombreColonnes);
					if (!graph.isComparable(color))
						color = rg.nextInt(nombreColonnes);
					output.write("color : "+pcmc.getPcm().getConcreteFeatures().get(color).getName()+"\n");
					size = rg.nextInt(nombreColonnes);
					if (!graph.isComparable(size))
						size = rg.nextInt(nombreColonnes);
					output.write("size : "+pcmc.getPcm().getConcreteFeatures().get(size).getName()+"\n");
					if (!graph.setParameters(x,y,color,size)){
						
						for (int i=0;i<nombreColonnes;i++){
							if (graph.isComparable(i)){
								noComp = true;
								break;
							}
						}
						if (!noComp){
							outputnc.write("* "+pcmfile.getName()+"\n");
							output.write("On ne peut pas tracer de graphes à partir de ce PCM.\n\n");
						}else{
							output.write("Ces paramètres ne permettent pas de générer un graphe.\n\n");
							notdone++;
						}
					}else{
						graph.generateHtmlFile("test/test.html");
						output.write("Un graphe a pu être généré.\n\n");
						success++;
					}
					/*
					Product pcmProd = pcm.getProducts().get(0);
					for (Cell pcmCell : pcmProd.getCells()){
						String nomCourant = pcmCell.getFeature().getName();
						nameLis
					}
					*/
					
					count++;
				}
			}
		}
		catch (Exception e){
			output.write("--- exception lors du traitement d'un PCM - STOP ---");
			output.flush();
			outputnc.flush();
		}finally{
		output.write("Sur les "+count+" PCMs, "+success+" ont pu être générés. "+notdone+" n'ont pas pu être générés.");
		output.flush();
		outputnc.flush();
		}
	}
	/*@Test
	public void testBoucle(){
		
		try{
		for (int i=0:i<10:i++){
			int num = rg.nextInt(pcmfiles.length);
			PCMContainer pcmc = loader.load(pcmfiles.get(num)).get(0);
			int rnlib = rg.nextInt(1);
			if (rnlib)
				graph = new PCMGraphPlotLy(pcmc);
			else
				graph = new PCMGraphNvd3(pcmc);
			
			int x = rg.nextInt(20);
			if (graph.isComparable(x))
				
			int y = rg.nextInt(20);
			int color = rg.nextInt(20);
			int size = rg.nextInt(20);
			
		}
		}catch (Exception e){
			System.out.println("Le test a échoué sur le fichier "+pcmfiles.get(num).getName());
		}
	*/
}