package channelpopularity.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Results class to implement witetofile and writetostdout
 */
public class Results implements FileDisplayInterface, StdoutDisplayInterface {
	
	private static String outputString="";
	private String outputFileName;
	
	/**
	 * Sets OutputFileName
	 *
	 * @param  String  filename
	 * @return NULL	   
	 */
	public void setOutputFileName(String outputFileName1) {
		outputFileName = outputFileName1;
	}
	
	
	/**
	 * Get Output
	 *
	 * @param  NULL
	 * @return String  outputstring	   
	 */
	public static String getoutputString() {
		return outputString;
	}
	
	
	/**
	 * appends result
	 *
	 * @param  String	Output of line processor
	 * @return NULL   
	 */
	public void store(String lineToAdd) {
		outputString += lineToAdd;
	}
	
	
	/**
	 * Writes generated output on console.
	 *
	 * @param  null
	 * @return null
	 */
	@Override
	public void writeToStdout() {
		// TODO Auto-generated method stub
		System.out.println(outputString);
	}

	
	/**
	 * Writes generated output to output.txt
	 *
	 * @param  null
	 * @return null
	 */
	@Override
	public void writeToFile() {
		// TODO Auto-generated method stub
		BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFileName, false));
            writer.write(outputString);
            
        } catch(IOException e) {
            System.err.println("IO Exception: Filename was not a proper name.");
            System.exit(1);
        } finally {
            try {
                writer.close();
            } catch(IOException e) {
                System.err.println("BufferedWriter not found.");
                System.exit(1);
            }
        }
	}
	
	
	/**
     * Default toString, needed for debugging here.
     * @return String with values that will be stored by Result
     */
    public String toString() {
        return ("Output String: " + outputString + "\nOutput Filename: " + outputFileName + "\n");
    }
	
}
