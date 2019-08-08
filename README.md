# File Type Identification
To identify file type using various sources by using files name and extensions.


# Problem​ ​Statement: 
With​ ​the​ ​enormous​ ​number​ ​of​ ​languages​ ​and​ ​file​ ​types​ ​used​ ​for​ ​writing​ ​logical​ ​source​ ​or​ ​for​ ​data​ ​purposes,​ ​it is​ ​very​ ​important​ ​for​ ​a​ ​product​ ​like​ ​BlueOptima​ ​to​ ​effectively​ ​identify​ ​and​ ​categorize​ ​a​ ​file​ ​into​ ​its​ ​type.​ ​And this​ ​has​ ​to​ ​be​ ​done​ ​solely​ ​based​ ​on​ ​Extension​ ​and​ ​Name​ ​of​ ​the​ ​file​ ​itself. This​ ​work​ ​sample​ ​requires​ ​you​ ​to​ ​identify​ ​different​ ​sources​ ​that​ ​could​ ​be​ ​used​ ​to​ ​identify​ ​details​ ​of​ ​a​ ​file​ ​type like​ ​following​ ​(but​ ​not​ ​limited​ ​to) 

  - Short​ ​Description​ ​(explaining​ ​the​ ​usage​ ​of​ ​the​ ​file​ ​type)
  - Category​ ​(i.e.​ ​Logical​ ​Source,​ ​Configuration,​ ​Data,​ ​etc.) 
  -  Language​ ​Family​ ​(Java,​ ​Python,​ ​Perl,​ ​etc.) 
  -  Programming​ ​Paradigm​ ​(Procedural,​ ​OOP,​ ​Dynamic,​ ​etc) 
  -  Associated​ ​applications 
  
# Solution (Execution Flow)

- [x] **Deliverable 1 - Identification and Analysis of Data Sources.**
    - [x] Identify at least 5 different Data sources.
    - [x] Expand on the rationale for using the Data source.
    
- [x] **Deliverable 2 - Implementation and Presentation of information about the given input file types.**
    - [x] Extract (Web scraping) data from Fileinfo.com using python script and store in **sourceFileInfo.json** file.
    - [x] Extract tika.xml using java parser and store in **sourceTika.json** file.
    - [ ] Extract (web scraping) data from IANA source using python script and store in .json file.
    - [x] Create an input **input.csv** file for passing all the inputs.
    - [x] Implement the main Program - **fileTypeIdentification.java**
      - [x] Store all the input filenames in a list.
      - [x] Access various data sources (extracted previously in .json files) and load each data source in the main memory (hash maps) `fileInfoHM, tikaHM`.
      - [x] For each file Extension input, parse it in the hash maps to search for required data in a priority.
      - [x] Print the information about each file input on the Console.

## Input

The input file is found in the 'data' directory of the `File-Type-Identification`. We have taken filenames with its extension in a `csv file` as shown below.

> **/data/input.csv**
```
      binarySort.CPP
      linkList.cpp
      Readme.pdf
      fibonacci.XCODEPROJ
      about.txt
      scrape.py
      xmlParser.java
```

## Output

The output for the program is written on a text file `output.txt` in the main directory. Given below is a sample output.

> **output.txt**

```
      ______________________________________________________________________________________________________________
 
      File: binarySort.CPP
      ______________________________________________________________________________________________________________
 
	        Category	: Developer File
	        Type		: C++ Source Code File
	        Description	: A CPP file is a source code file written in C++, a popular programming language that adds features such as object-oriented programming to C.  It may be a standalone program, containing all the code or one of many files referenced in a development project.  CPP files must be compiled by a C++ compiler for the target platform before the code can be run.
	        Programs	: File Viewer Plus, Microsoft Visual Studio 2017, Microsoft Visual Studio Code, Eclipse CDT, Code::Blocks, Embarcadero Technologies C++ Builder, ES-Computing EditPlus, BloodshedSoftware Dev-C++, Apple Xcode, GNU Compiler Collection (GCC), MacroMates TextMate, Freescale CodeWarrior Development Tools, File Viewer for Android
```

## Steps to Run the Program

1. In `/data/` Create your input file in csv as given in the above input format or just use the pre built one.
2. Execute the main program from `/src/fileTypeIdentification/FileTypeIdentification.java`
3. Enter the input file name example: `input1.csv` in the console or else it will take the default `input0.csv`.
4. You can check the output in `output.txt` file in the main directory.


## Developers

- Mohammed Ataaur Rahaman
- Siddharth Singh
- Shivani Bangalore
