// Start of the main Program 
// File Type Identification
//
// Input: data/input0.csv
// Output: Text file 
// 
// Developers:
//	Mohammed Ataaur Rahaman
// 	Siddharth Singh
// 	Shivani Bangalore
//



package fileTypeIdentification;

import java.io.*; 
import java.util.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.util.HashMap;
import java.util.Map;

// Source 1 - fileinfo.com
class FileInfoData
{
	String fIType; 
	String fICategory;
	String fIDescription;
	String fIPrograms;
	
}

// Source 2 - tika.xml
class TikaData 
{
	String TikaComment;
	String TikaType;
}

public class FileTypeIdentification {
	static HashMap<String, FileInfoData> fileInfoHM;	// Hash map definition for Source 1
	static HashMap<String, TikaData> tikaHM;			// Hash map definition for Source 2
	
	public static void main(String[] args) throws IOException {
		
		// User input for input file .csv
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the .csv file present in /data/");
		String inputFile = br.readLine();
		if (inputFile.isEmpty() || !inputFile.endsWith(".csv")) {
			System.out.println("\nDefault input file taken.");
			inputFile = "input0.csv";	
		}
		System.out.println("\nInput File: " + inputFile);
		
		// Hash maps declaration for the sources
		fileInfoHM = new HashMap<String, FileInfoData>();
		tikaHM = new HashMap<String, TikaData>();
		
		
		// Reading filenames with Extensions from input file in data/*.csv to an arrayList
		BufferedReader inputFileNames = new BufferedReader(new FileReader("data/" + inputFile));
		List<String> inputList = new ArrayList<String>();	// list containing all the inputs
		String fileNameNExtension;
		while((fileNameNExtension = inputFileNames.readLine()) != null)
			inputList.add(fileNameNExtension);
		inputFileNames.close();
		
		
		// Reading Source 1 - SourcefileInfo.json and loading in HASH map - fileInfoHM
		JSONParser jsonParserS1 = new JSONParser();
		try(FileReader reader = new FileReader("data/sourceFileInfo2.json"))	{
			Object obj = jsonParserS1.parse(reader);	
			JSONArray fileTypeList = (JSONArray) obj;

            // Iterate over fileTypesList - JSON Array to get the extensions and its details
            fileTypeList.forEach( fileExtension -> s1parseFileTypeObject( (JSONObject) fileExtension ) );
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(ParseException e) {
			e.printStackTrace();
		}

		
		// Reading Source 2 - SourceTika.json and loading in HASH map - tikaHM
		JSONParser jsonParserS2 = new JSONParser();
		try (FileReader s2Reader = new FileReader("data/sourceTika1.json"))	{
			Object obj = jsonParserS2.parse(s2Reader);
			JSONArray fileTypeList = (JSONArray) obj;
			
			// Iterate over fileTypesList - JSON Array to get the extensions and its details
			fileTypeList.forEach( fileExtension -> s2parseFileTypeObject( (JSONObject) fileExtension));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		
		// Writing output on a ouput.txt file
		PrintStream o = new PrintStream(new File("output.txt")); 
		PrintStream console = System.out; // for printing on the console
		System.setOut(o);	 // Assign o to output stream 
	
		
		// File Identification from Various sources for each item in inputList
		for (String input : inputList) 
		{
			int indexOfDot = input.lastIndexOf(".");
			String fileExtension = input.substring(indexOfDot).toLowerCase();	// Extracting file Extension from each input
			
			// Initializing each category for a particular fileExtension with N/A as default
			String category = "N/A";
			String type = "N/A";
			String description = "N/A";
			String programs = "N/A";
			
			if (fileInfoHM.containsKey(fileExtension))	// Searching in File Info Hash Map
			{
				FileInfoData fd = fileInfoHM.get(fileExtension);
				category = fd.fICategory;
				type = fd.fIType;
				description = fd.fIDescription;
				programs = fd.fIPrograms;
			}
			else if (tikaHM.containsKey(fileExtension))	// Searching in Tika Hash Map
			{
				TikaData td = tikaHM.get(fileExtension);
				category = td.TikaComment;
				type = td.TikaType;
			} 
			
 
			// Outputting the Category list 
			System.out.println("______________________________________________________________________________________________________________\n " + 
					"\nFile: " + input +
					"\n______________________________________________________________________________________________________________\n " +
					"\n\tCategory\t: " + category + 
					"\n\tType\t\t: " + type + 
					"\n\tDescription\t: " + description + 
					"\n\tPrograms\t: " + programs + "\n\n");
		}
		

		System.setOut(console);
		System.out.println("\n\nSuccess!!\nYou can check output in output.txt file in the main directory.");
	}
	
	private static void s1parseFileTypeObject(JSONObject fileExtension)
    {      
		// Getting each value for the respective keys from JSON Object
        String extension = (String) fileExtension.get("Extension");   
        String category = (String) fileExtension.get("Category"); 
        String type = (String) fileExtension.get("Type");   
        String description = (String) fileExtension.get("Description");   
        String programs = (String) fileExtension.get("Programs");
        
        // Updating Hash map - fileInfoHM for each Extension
        FileInfoData fd = new FileInfoData();
        fd.fIType = type;
        fd.fICategory = category;
        fd.fIDescription = description;
        fd.fIPrograms = programs;
        fileInfoHM.put(extension.toLowerCase(), fd);	// Handling case 
    }
	
	public static void s2parseFileTypeObject(JSONObject fileExtension)
	{
		// Iterating over each file Extension in the JSON Object of Tika file
		for(Iterator iterator = fileExtension.keySet().iterator(); iterator.hasNext();) {
		    String key = (String) iterator.next();	// Get the key - fileExtension
		    JSONObject extensionObject = (JSONObject) fileExtension.get(key);
		    
		    // Getting values for each fileExtension
		    String tikaType = (String) extensionObject.get("tikaType");
		    String tikaComment = (String) extensionObject.get("tikaComment");
		    
		    // Updating Hash map - tikaHM for each key (extension)
		    TikaData td = new TikaData();
			td.TikaComment = tikaComment;
			td.TikaType = tikaType;
			tikaHM.put(key.toLowerCase(), td);	// Handling case
		}
	}
}
