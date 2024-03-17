import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NovelProcessor 
{
    //default constructor
    public NovelProcessor()
    {

    }

    public static HashMap <String, Integer> regexMap(String novel, String regex) throws IOException
    {
        String line = "";
        HashMap <String, Integer> count = new HashMap <String,Integer>();
        try (BufferedReader novelReader = new BufferedReader(new FileReader(novel))) {
            while((line = novelReader.readLine()) != null)
            {
                for(String regexString : count.keySet())
                {
                    Pattern pattern = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher= pattern.matcher(line);

                    while(matcher.find())
                    {
                        count.put(regexString, count.get(regexString)+1);
                    }
                }
            }
        }

            return count;
    }

    public static void outputFile( HashMap<String,Integer> appearanceMap, String fileName) throws IOException
    {
        PrintWriter outputWriter = new PrintWriter(new FileWriter(fileName.replaceAll(".txt","_wc.txt")));
            
        for( HashMap.Entry<String, Integer> entry : appearanceMap.entrySet())
        {           
            outputWriter.println(entry.getKey() + "| " + entry.getValue());
        }
          
         outputWriter.close();    
    }

    public static void main(String[]args) throws IOException
    {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.println("Enter novel: ");
            String novel = (scan.next());
            System.out.println("Enter regex: ");
            String regex = (scan.next());

            HashMap <String, Integer> count = regexMap(novel, regex);
            outputFile(count, novel);
        }
        System.out.println("File created.");
    }
}
