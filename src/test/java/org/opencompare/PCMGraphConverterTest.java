package org.opencompare;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import static org.junit.Assert.*;
//import java.lang.ArrayIndexOutOFBoundsException;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PCMGraphConverterTest {
	/*
	//Test sur un PCM donné
	protected PCMGraphConverter graph;
	
	@Before
	public void init(){
		try{
		File pcmFile = new File("pcms/examples.pcm");
		PCMLoader loader = new KMFJSONLoader();
		List<PCMContainer> pcmc = loader.load(pcmFile);
		graph = new PCMGraphPlotLy(pcmc.get(pcmc.size()-1));
		}catch (IOException ioe){
			System.err.println(ioe.getMessage());
		}
	}
	
	@Ignore
	@Test
	public void testInit(){
		//il faut donner une valeur à chaque paramètre
		graph.generateHtmlFile("html/testInit.html",false);
	}
	
	//@Ignore
	@Test
	public void testSetParameters(){
		//paramètres hors liste
		//graph.setParameters(-1,0,1,2);
	}
	
	@Ignore
	@Test
	//@Test(expected=IndexOutOFBoundsException.class)
	public void testIsComparable() {
		//paramètre négatif
		try{
			//Feature comparable --> entier ou réel
			assertTrue("Cette feature est normalement comparable",graph.isComparable(14));
			//Feature non comparable --> autre
			assertFalse("Cette feature n'est normalement pas comparable",graph.isComparable(10));
			graph.isComparable(-1);
			fail("isComparable devrait lancer une exception d'indices hors plage");
		}catch (IndexOutOfBoundsException ioobe){
			
			//Feature comparable --> entier ou réel
			assertTrue("Cette feature est normalement comparable",graph.isComparable(14));
			//Feature non comparable --> autre
			assertFalse("Cette feature n'est normalement pas comparable",graph.isComparable(10));
		}
		}finally{
			
		}
	}
	
	@Ignore
	@Test
	public void testGenerateHtmlFile(){

	}
	
	@Ignore
	@Test
	public void testGetElements(){
		try{
			graph.getElements(-1);
		}catch (IndexOutOFBoundsException ioobe){
			
		}
	}
	
	@Ignore
	@Test
	public void testGetLabelElement(){
		
	}
	
	@Ignore
	@Test
	public void testGetTextTitles(){
		
	}
	
	@Ignore
	@Test
	public void testGetMinValue(){
		//sortie String
		//
		//assertEquals("Le minimum devrait être ...",graph.getMinValue(x),<minx>);
	}
	
	@Ignore
	@Test
	public void testGetMaxValue(){
		//assertEquals("Le minimum devrait être ...",graph.getMinValue(x),<maxx>);
	}
	
	@Ignore
	@Test
	public void testGetListToString(){
		
	}
	*/
}