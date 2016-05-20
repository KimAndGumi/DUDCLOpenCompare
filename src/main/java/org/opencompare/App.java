package org.opencompare;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.PCMFactoryImpl;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.extractor.CellContentInterpreter;
import org.opencompare.api.java.io.CSVLoader;
import org.opencompare.api.java.io.PCMDirection;
import org.opencompare.api.java.io.PCMLoader;


public class App {

	public static void main(String[] args) {
		try
		{
			// arguments, on attends les paramètres suivants:
			// 0 : f:chemin du fichier
			// 1 : x:numéro de colonne du x
			// 2 : y:numéro de colonne du y
			// 3 : color:numéro de colonne du color
			// 4 : size:numéro de colonne du size
			// 5 : l:librairie à utiliser Plot.ly ou Nvd3
			// 6 : Wizard
			//
			// exemple : f=pcms/example.pcm x=1 y=2 color=2 size=14 l=Plot.ly
			// exemple : f=pcms/example.pcm x=1 y=2 color=2 size=14 l=Nvd3	
			// f=c:/model/starwars/films.json x=1 y=2 color=5 size=14 l=Nvd3
			
			String[] listeParametres = scanParametres(args);
			
			if (("wizard").equals(listeParametres[6]))
				launchWizard();
			else
				if (("?").equals(listeParametres[6])){
					System.err.println("Help:\n\tAccepted parameters \n"
							+ "\texpert mode : f=filename x,y,size,color=column_index l=(Plot.ly|Nvd3)"
							+ "\tquestion mode : wizard");
				}
				else
				{
					String file = listeParametres[0];
					int x = Integer.parseInt(listeParametres[1]);
					int y = Integer.parseInt(listeParametres[2]);
					int color = Integer.parseInt(listeParametres[3]);
					int size = Integer.parseInt(listeParametres[4]);
					String library = listeParametres[5];
					
					new PCMATeam(file,x,y,size,color,library);
				}
		}catch (Exception ioe){
			System.err.println(ioe.getMessage());
			ioe.printStackTrace();
		}
	}
		
	public static String[] scanParametres(String[] liste){
		String[] returnList = new String[7];
		for(String s: liste){
			s = s.trim();
			String[] line = s.split("=");
			switch(line[0]){
				case "f" : returnList[0] = line[1];break;
				case "x" : returnList[1] = line[1];break;
				case "y" : returnList[2] = line[1];break;
				case "color" : returnList[3] = line[1];break;
				case "size" : returnList[4] = line[1];break;
				case "l" : returnList[5] = line[1];break;
				case "?" :	returnList[6] = line[0];break;
				case "wizard" :  returnList[6] = line[0];break;
			}					
		}
		return returnList;
	}
	
	public static PCMATeam launchWizard() throws Exception{
		Scanner sc = new Scanner(System.in);
		String file = "";
		File f;
		PCMATeam pcmATeam ;
		
		boolean isExist = false;
		do{
			System.out.print("\n<Wizard Mode>\nGive us a path/filename to graph : ");
			file = sc.nextLine();
			f = new File(file);
			
			if (!f.exists())
				System.out.println("\n!! The file " + file + " doesn't exist.\n");
			else
				isExist = true;
		}while(!isExist);
		
		int i = 0;
		System.out.println("List of the features :\n");
		pcmATeam = new PCMATeam(file,"Plot.ly");
		for(String s : pcmATeam.getFeaturesList()){
			System.out.println(i + ":" + s);
			i++;
		}
		System.out.println("Choose parameters (x, y, size, color) you want to display according to the previous features list.");
		System.out.print("x=");
		int x = sc.nextInt();
		System.out.print("\ny=");
		int y = sc.nextInt();
		System.out.print("\nsize=");
		int size = sc.nextInt();
		System.out.print("\ncolor=");
		int color = sc.nextInt();
		String lib = "";
		System.out.print("\nWhich graphic library do you want to use, Plot.ly or Nvd3 ? ");
		do{
			lib = sc.nextLine();
		}while( !(lib.equals("Plot.ly") || lib.equals("Nvd3")) );
		System.out.println( "Selected parameters : " + file + ","+x+","+y+","+size+","+color+","+lib );
		System.out.println("\nEnd of Wizard Mode");
		return new PCMATeam(file,x,y,size,color,lib);
		
	}
	
	
	

}
