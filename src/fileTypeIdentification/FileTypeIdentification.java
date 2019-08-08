// Start of the main Program 
// File Type Identification
//
// Input: data/input0.csv
// Output: Console O/P
// 
// Developers:
//	Mohammed Ataaur Rahaman
// 	Siddharth Singh
// 	Shivani Bangalore
//



package fileTypeIdentification;

import java.util.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
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
	static HashMap<String, FileInfoData> fileInfoHM;	// Hashmap definition for Source 1
	static HashMap<String, TikaData> tikaHM;			// Hashmap definition for Source 2
	
	public static void main(String[] args) throws IOException {
		
		// Hash maps declaration for the sources
		fileInfoHM = new HashMap<String, FileInfoData>();
		tikaHM = new HashMap<String, TikaData>();
		
		
		// Reading filenames with Extensions from input file in data/*.csv to an arrayList
		BufferedReader inputFileNames = new BufferedReader(new FileReader("data/input2.csv"));
		List<String> inputList = new ArrayList<String>();
		String fileNameNExtension;
		while((fileNameNExtension = inputFileNames.readLine()) != null)
			inputList.add(fileNameNExtension);
//		System.out.println(inputList); 	// Debug line
		inputFileNames.close();
		
		
		// Reading Source 1 - SourcefileInfo.json and loading in HASH map
		JSONParser jsonParserS1 = new JSONParser();
		try(FileReader reader = new FileReader("data/sourceFileInfo1.json"))	{
			Object obj = jsonParserS1.parse(reader);
			
			JSONArray fileTypeList = (JSONArray) obj;
//            System.out.println(fileTypeList);	// Debug line
             
            //Iterate over fileTypesList 
            fileTypeList.forEach( fileExtension -> s1parseFileTypeObject( (JSONObject) fileExtension ) );
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(ParseException e) {
			e.printStackTrace();
		}

		
		// Reading Source 2 - SourceTika.json and loading in HASH map
		JSONParser jsonParserS2 = new JSONParser();
		try (FileReader s2Reader = new FileReader("data/sourceTika.json"))	{
			Object obj = jsonParserS2.parse(s2Reader);
			JSONArray fileTypeList = (JSONArray) obj;
//			System.out.println(fileTypeList);
			
			fileTypeList.forEach( fileExtension -> s2parseFileTypeObject( (JSONObject) fileExtension));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		
		
//		// Accessing the tikaHM
//		for (Map.Entry<String, TikaData> tikaEntry : tikaHM.entrySet()) {
//			String key = tikaEntry.getKey();
//			TikaData td = tikaEntry.getValue();
//			System.out.println(	"Extension: " + key + 
//					", TikaType: " + td.TikaType + 
//					", TikaComment: " + td.TikaComment);
//		}
//		
//		// Accessing the fileInfoHM
//		for (Map.Entry<String, FileInfoData> fIEntry : fileInfoHM.entrySet()) {
//			String key = fIEntry.getKey();
//			FileInfoData fd = fIEntry.getValue();
//			System.out.println("Extension: " + key + 
//					", Category: " + fd.fICategory);
//		}
//		System.out.println(); // Debug line
		
		// File Identification from Various sources
		for (String input : inputList) 
		{
			int indexOfDot = input.lastIndexOf(".");
//			String fileName = input.substring(0, indexOfDot);
			String fileExtension = input.substring(indexOfDot).toLowerCase();
			
			System.out.println("______________________________________________________________________________________________________________\n ");
			System.out.println("File: " + input);
			System.out.println("______________________________________________________________________________________________________________\n ");
			String category = "N/A";
			String type = "N/A";
			String description = "N/A";
			String programs = "N/A";
			
			if (fileInfoHM.containsKey(fileExtension))
			{
				FileInfoData fd = fileInfoHM.get(fileExtension);
				category = fd.fICategory;
				type = fd.fIType;
				description = fd.fIDescription;
				programs = fd.fIPrograms;
			}
			else if (tikaHM.containsKey(fileExtension))
			{
				TikaData td = tikaHM.get(fileExtension);
				category = td.TikaComment;
				type = td.TikaType;
			} 
			
			System.out.println("\tCategory\t: " + category + 
					"\n\tType\t\t: " + type + 
					"\n\tDescription\t: " + description + 
					"\n\tPrograms\t: " + programs + "\n");
		}

	}
	
	private static void s1parseFileTypeObject(JSONObject fileExtension)
    {      
//		String number = (String) fileExtension.get("No");
        String extension = (String) fileExtension.get("Extension");   
        String category = (String) fileExtension.get("Category"); 
        String type = (String) fileExtension.get("Type");   
        String description = (String) fileExtension.get("Description");   
        String programs = (String) fileExtension.get("Programs");
        
        // Updating fileInfoHM for each Extension
        FileInfoData fd = new FileInfoData();
        fd.fIType = type;
        fd.fICategory = category;
        fd.fIDescription = description;
        fd.fIPrograms = programs;
        fileInfoHM.put(extension.toLowerCase(), fd);
    }
	
	public static void s2parseFileTypeObject(JSONObject fileExtension)
	{
		for(Iterator iterator = fileExtension.keySet().iterator(); iterator.hasNext();) {
		    String key = (String) iterator.next();

		    JSONObject extensionObject = (JSONObject) fileExtension.get(key);
		    
		    String tikaType = (String) extensionObject.get("tikaType");
		    String tikaComment = (String) extensionObject.get("tikaComment");
		    
		    // Updating tikaHM for each key (extension)
		    TikaData td = new TikaData();
			td.TikaComment = tikaComment;
			td.TikaType = tikaType;
			tikaHM.put(key.toLowerCase(), td);
		}
	}
}
