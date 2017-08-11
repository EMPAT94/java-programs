/*
Class Map_Reduce is a classic example of mapper and reducer approach. Here, you supply
a text file (via command line arg) as input and the program shoots out an output file that
consists of all the words in your input file along with the count of each.
 */

//package map_reduce;

/*Enable package if using in Netbeans.

Note that to supply commandline arguments in netbeans, go to :
File -> Project Properties -> Run -> Arguments (Input your file name here)

In Case of FIleNotFound Exception, try to change your Working Directory in same box
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 * @author Pritesh
 */
public class Map_Reduce {

    /**
     * @param args = File Name of the Input Text File.
     */
    public static void main(String[] args) {

        Map_Reduce mr = new Map_Reduce();
        mr.run(args[0]); // Run the Program with argument of Inputfile

        System.exit(0); //Return a Success Status and exit.s
    }

    void run(String fileName) {

        try {
            File f = new File(fileName);    //Create a new File object with "fileName" parameter.

            if (f.exists()) {

                //If file exists, run the mapper method.
                mapper(f);
            } else {
                System.err.print("File Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void mapper(File f) {
        try {

            Scanner fent = new Scanner(f);  //Create a new Scanner object and attach it to File object.
            int words = 0;  //Number of words in input file.

            PrintStream w = new PrintStream("tmp.txt");     //New Printstream pointing to the destination file.

            File o = new File("tmp.txt");   //File Object fole file handling 

            while (fent.hasNext()) {

                //While next input sequence is present,
                w.print(fent.next() + "\n");
                //fetch it and buffer it to print stream then
                w.flush();
                //Flush it to the output file
                words++;
            }

            if (o != null) {

                //if the file is not empty, then call the reducer method.
                reducer(o, words);
            } else {
                System.err.print("Problem in tmp file");
            }

            w.close();  //Close the printstream (IMPORTANT)
            fent.close();   //Close the file object handle (IMPORTANT)
            
            //Files.delete(Paths.get("tmp.txt"));  //Delete the tmp file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void reducer(File o, int words) {
        try {

            Scanner fent = new Scanner(o);
            PrintStream w = new PrintStream("output.txt");  //new printstream also Creates or Overwrites the file.

            List<String> check = new ArrayList();   //List for ALL the words
            List<String> checked = new ArrayList(); //List for processed words

            while (fent.hasNext()) {

                //Add keys from file to list
                check.add(fent.next());
            }

            for (String k : check) {

                if (checked.contains(k)) {
                    continue;   //if the key is already processed, continue with the loop
                }

                // else check for occurences of the key in "check" list
                w.print(k + ", " + Collections.frequency(check, k) + "\n");
                w.flush();
                checked.add(k);
            }
            w.close();
            fent.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
