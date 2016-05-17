package org.opencompare;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class AppTest{
	
	private String folder='pcms/model/';
	private String[] graphlibrary = ['Plot.ly','Nvd3'];
	private Random rg = new Random();
	private PCMLoader loader = new KMFJSONLoader();
	private PCMGraphConverter graph = null;
	@Before
	public void init(){
		File directory = new File(folder);
		List<File> pcmfiles = directory.listFiles();			
		//PCMContainer pcmc = loader.load(pcmfiles[rg.nextInt(pcmfiles.length)])[0];
	}
	
	@Test
	public void fullTest(){
		FileWriter fw = new FileWriter("test/log.txt", false);
		BufferedWriter output = new BufferedWriter(fw);
		output.write("--- Log du test sur les 1400 PCMs---\n");
		try{			
			for (File pcmfile:pcmfiles){
				List<PCMContainer> pcmcs = loader.load(pcmfile);
				output.write("* "+pcmfile.getName()+" : "+pcmcs.length+" PCM\n");
				int count = 1;
				int success = 0;
				int notdone = 0;
				//for (PCMContainer pcmc : pcmcs){
				PCMContainer pcmc = pcmcs.get(0);
					{
					//Extraction du PCM
					//PCM pcm = pcmContainer.getPcm();
					//Création de l'objet graph en fonction d'un tirage aléatoire
					output.write("PCM "+count);
					int rnlib = rg.nextInt(1);
					if (rnlib)
						graph = new PCMGraphPlotLy(pcmc);
					else
						graph = new PCMGraphNvd3(pcmc);
					output.write(" : "+graphlibrary[rnlib]+"\n");
					output.write("La liste complète des paramètres est : "+graph.getNameList());
					output.write("Tirage aléatoire des 4 paramètres :\n")
					//Tirage aléatoire des 4 paramètres
					//Une seule vérification de "comparabilité"
					int x = rg.nextInt(pcmContainer.getPcm().getConcreteFeatures().length);
					if (!(graph.isComparable(x)))
						x = rg.nextInt(pcmContainer.getPcm().getConcreteFeatures().length);
					output.write("x : "+pcmContaine.getPcm().getConcreteFeatures().get(x).getName()+"\n");
					int y = rg.nextInt(pcmContainer.getPcm().getConcreteFeatures().length);
					if (!graph.isComparable(y))
						y = rg.nextInt(pcmContainer.getPcm().getConcreteFeatures().length);
					output.write("y : "+pcmContaine.getPcm().getConcreteFeatures().get(y).getName()+"\n");
					int color = rg.nextInt(pcmContainer.getPcm().getConcreteFeatures().length);
					if (!(graph.isComparable(color)))
						color = rg.nextInt(pcmContainer.getPcm().getConcreteFeatures().length);
					output.write("color : "+pcmContaine.getPcm().getConcreteFeatures().get(color).getName()+"\n");
					int size = rg.nextInt(pcmContainer.getPcm().getConcreteFeatures().length);
					if (!(graph.isComparable(size)))
						size = rg.nextInt(pcmContainer.getPcm().getConcreteFeatures().length);
					output.write("size : "+pcmContaine.getPcm().getConcreteFeatures().get(size).getName()+"\n");
					if (!graph.setParameters(x,y,color,size)){
						output.write("Ces paramètres ne permettent pas de générer un graphe.\n\n");
						notdone++;
					}else{
						graph.generateHtmlFile("test/test.html");
						output.write("Un graphe a pu être généré.\n\n")
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
		catch{
			
		}
		output.flush();
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