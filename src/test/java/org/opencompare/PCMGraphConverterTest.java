package org.opencompare;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Ignore;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PCMGraphConverterTest {
	
	//Test sur un PCM donné
	protected PCMGraphConverter pcmgc;
	
	@Before
	public void init(){
		File pcmFile = new File("pcms/examples.pcm");
		PCMLoader loader = new KMFJSONLoader();
		List<PCMContainer> pcmc = loader.load(pcmFile);
		pcmgc = new PCMGraphPlotLy(pcmc[0]);
	}
	
	@Ignore
	@Test
	public void testSetParameters(){
		//paramètres négatifs
		pcmgc.setParameters(a,b,c,d)
	}
	
	@Ignore
	@Test
	public void testIsComparable() {
		//paramètre négatif
		//assert
		//Feature comparable --> entier ou réel
		assertTrue("Cette feature est normalement comparable",pcmgc.isComparable(14))
		//Feature non comparable --> autre
		//assertFalse()
	}
	
	@Ignore
	@Test
	public void testGenerateHtmlFile(){

	}
	
	@Ignore
	@Test
	public void testGetElements(){
		
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
		assert
		//
		assertEquals("Le minimum devrait être ...",pcmgc.getMinValue(x),minx);
	}
	
	@Ignore
	@Test
	public void testGetMaxValue(){
		assertEquals("Le minimum devrait être ...",pcmgc.getMinValue(x),maxx);
	}
	
	@Ignore
	@Test
	public void testGetListToString(){
		
	}
}