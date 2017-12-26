# AboutYou web pages parser

The application allows to get offers by keyword for search from [About You web site](www.aboutyou.de) and write all offers to xml file.

## Getting Started

Download java archive by path:
      
   ```target\aboutyou-parser-1.0.jar```

## Running

1. Run console and type the following command, where **"keyword"** is a word for search:

      ``` java -jar aboutyou-parser-1.0.jar <keyword>```

2. As a result, you can find out xml file in the folder with java archive.
3. In console window you can find useful information such 
as: 
      * amount of triggered HTTP request, 
      * run-time, 
      * memory footprint and 
      * amount of extracted products.