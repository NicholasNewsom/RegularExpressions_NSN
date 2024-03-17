import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileProcessor 
{
    static HashMap<String, Integer> ipAddresses = new HashMap<>();
    static HashMap<String, Integer> Users = new HashMap<>();
    /**
     * scans each line of log file and adds total found regex numbers to HashMap
     * @param regex regex used in our search
     * @param file file being scanned
     * @param Type type for which hash key is used
     * @param map HashMap of data
    */
    public static void scanFile(File file, String regex, int Type, HashMap<String, Integer> map) throws IOException 
    {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        String line;
	    while((line = fileReader.readLine())!= null) 
        {
            Matcher matcher = pattern.matcher(line);
            while(matcher.find()) 
            {
                String place = matcher.group(Type);
                map.putIfAbsent(place, 0);
                map.put(place, map.get(place)+1);
            }
	    }
        fileReader.close();
    }
    /**
     * prints HashMap
     * @param HashMap HashMap being printed
    */
    public static void printHashMap(HashMap<String, Integer> HashMap) 
    {
        for (HashMap.Entry<String, Integer> entry : HashMap.entrySet()) 
        {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
    /**
     * counts total lines in file
     * @param file file whose lines are counted
     * @throws IOException
     * @return the amount of lines
    */
    public static int lines(File file) throws IOException
    {
        int Count = 0;
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
	    while((fileReader.readLine()) != null) 
        {
            Count++;
	    }
        fileReader.close();
        return Count;
    }
    /**
     * gets HashMap size
     * @param HashMap HashMap being sized
     * @return size of HashMap
    */
    public static int Size(HashMap<String, Integer> HashMap) 
    {
        return HashMap.size();
    }
    public static void main(String[] args) throws IOException
    {	
        try (Scanner scan = new Scanner(System.in)) 
        {
            System.out.println("Enter file: ");
            File file = new File(scan.next());
            scanFile(file, "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}", 0, ipAddresses);
            scanFile(file, "user\\s(\\w+)\\s", 1, Users);
   
            System.out.println("Enter Command of 0 (default), 1 (IPs), or 2 (users): ");
            String Request = (scan.next());
            switch (Request) 
            {
                case "1":
                printHashMap(ipAddresses);
                    break;

                case "2":
                printHashMap(Users);
                    break;

                default:
                System.out.println(lines(file) + " lines parsed.");
                System.out.println(Size(ipAddresses) +  " unique IP addresses.");
                System.out.println(Size(Users) + " unique users.");    
                    break;
            }
        }
    } 
}