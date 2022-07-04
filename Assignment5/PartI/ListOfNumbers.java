import java.io.*;
import java.util.List;
import java.util.ArrayList;
 
public class ListOfNumbers {
    private List<Integer> list;
    private String inFile;
 
    public ListOfNumbers () {
        list = new ArrayList<Integer>();
    }
    

    public ListOfNumbers (String inFile) {
    	this();
    	this.inFile = inFile;	
    }
    public void readList() {
    	FileReader f = null;
    	BufferedReader in ;
    	int i =0;
    	try{
    	    f = new FileReader(this.inFile);
    	    in = new BufferedReader(f);
    	    String Line  = in.readLine();
    	    while(Line!=null){
    	        try {
                    this.list.add(Integer.parseInt(Line));
                    i++;
                }
    	        catch (NumberFormatException nfe){
    	            System.err.println("Parsing problem:"+nfe.toString());
                }
    	        Line = in.readLine();
                }
            }
        catch (FileNotFoundException fne){
            System.err.println("IO Exception Not Found this file:"+fne.toString());
        }
    	catch (IOException ioe){
    	    System.err.println("IO Exception reading file:"+ioe.toString());
        }
    	finally {
            try {
                f.close();
                System.out.println("Closing FileReader");
            } catch (IOException e) {
                System.err.println("IO Exception closing file");
            }
        }
    }
    
    public void writeList() {
        PrintWriter out = null;
        try {
            System.out.println("Entering try statement");
            out = new PrintWriter(new FileWriter("outFile.txt"));
            for (int i = 0; i < list.size(); i++)
                out.println("Value at: " + i + " = " + list.get(i));
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Caught IndexOutOfBoundsException: " +
                                 e.getMessage());
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        } finally {
            if (out != null) {
                System.out.println("Closing PrintWriter");
                out.close();
            } else {
                System.out.println("PrintWriter not open");
            }
        }
    }
    
    public static void cat(String fileName) {
        RandomAccessFile input = null;
        String line = null;
        File file = new File(fileName);
        try {
            input = new RandomAccessFile(file, "r");
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            return;
        } catch (FileNotFoundException fne) {
            System.err.println("IO Exception Not Found this file:"+fne.toString());
        } catch (IOException ioe) {
            System.err.println("IO Exception reading file:"+ioe.toString());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.err.println("IO Exception closing file");
                }
            }
        }
    }
    
    public static void main(String[] args) {
    	ListOfNumbers listOfNumbers = new ListOfNumbers("numberfile.txt");
    	ListOfNumbers.cat("numberfile.txt");
    	listOfNumbers.readList();
        listOfNumbers.writeList();
    }

}
