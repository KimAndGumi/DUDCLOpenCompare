package org.opencompare;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gbecan on 02/02/15.
 */
public class JsonTest {

    @Test
    public void testMyJsonFeatures() throws IOException {
    	
        // Watcher Json file
    	try {
    		ObjectMapper objectMapper = new ObjectMapper();
    		
    		//Test file.json qui contient pas de meme nbres d'éléments en x et y
    		//File file = new File("html/file.json");
    		
    		//Test fichierFaux.json qui contient pas de meme nbres d'éléments en x et y
    		File file = new File("html/fileFaux.json");
    		PCMDataPlotLy pcmData = objectMapper.readValue(file, PCMDataPlotLy.class);
    		
    		String[] xData =  ((String) pcmData.x).split(",");
    		System.out.println(Arrays.toString(xData));
    		String[] yData =  ((String) pcmData.y).split(",");
    		System.out.println(Arrays.toString(yData));
    		
    		assertEquals(xData.length , yData.length) ;
    		
         } 
         catch (JsonParseException e) { e.printStackTrace(); } 
         catch (JsonMappingException e) { e.printStackTrace(); }
         catch (IOException e) { e.printStackTrace(); }
      }
    

    }
