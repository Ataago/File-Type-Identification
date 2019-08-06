package fileextraction;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import com.opencsv.CSVWriter;
 
public class test {
 
    public static void main(String[] args) throws IOException {
    	//taking input csv
        CSVWriter inputwrite = new CSVWriter(new FileWriter("input.csv"));
        inputwrite.writeNext(new String[]{"y.CPP"});
        inputwrite.close();
        //creating output csv
        CSVWriter outputwrite = new CSVWriter(new FileWriter("output.csv"));
        //reading input file and data extension file
        BufferedReader dataread = new BufferedReader(new FileReader("ExtensionData.csv"));
        BufferedReader inputread = new BufferedReader(new FileReader("input.csv"));
        //splitting input file
        String[] s=inputread.readLine().split(",");
        //checking for files extensions
        while (dataread.readLine() != null) {
            String[] data = dataread.readLine().split(",");
            for(String str:s) {    
            	str=str.replaceAll("\"","");
            	if(str.endsWith(data[0])) {
            		outputwrite.writeNext(data);break;
        }}}
        outputwrite.close();
        inputread.close();
        dataread.close();
    }
}
    