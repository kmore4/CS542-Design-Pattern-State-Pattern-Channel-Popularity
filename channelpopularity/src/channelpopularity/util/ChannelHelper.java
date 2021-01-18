package channelpopularity.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import channelpopularity.context.ChannelContext;
import channelpopularity.context.ContextI;
import channelpopularity.operation.Operation;
import channelpopularity.state.StateName;
import channelpopularity.state.factory.SimpleStateFactory;
import channelpopularity.state.factory.SimpleStateFactoryI;
import channelpopularity.util.FileProcessor;
import channelpopularity.validations.EmptyFileException;
import channelpopularity.validations.EmptyLineException;
import channelpopularity.validations.FileNotExistException;
import channelpopularity.validations.InvalidInputFormatException;
import channelpopularity.validations.InvalidInputValueException;
import channelpopularity.validations.videoAlreadyExistsException;
import channelpopularity.validations.videoDoesNotExistsException;

/**
 * @author Kasturi Tarachand More
 *
 */

/**
 * The ChannelHelper processes each line of the file
 */
public class ChannelHelper {
	
	/**
	 * Line parsing
	 *
	 * @param	fp		file processor object
	 * @return 	NULL
	 * @exception	EmptyLineException, IOException, EmptyFileException, 
	 * InvalidInputFormatException, videoAlreadyExistsException, 
	 * videoDoesNotExistsException, InvalidInputValueException
	 */
	public void lineProcessor(FileProcessor fp) throws EmptyLineException, IOException, EmptyFileException, InvalidInputFormatException, videoAlreadyExistsException, videoDoesNotExistsException, InvalidInputValueException {
		try {
			String line = "";
			
			List<StateName> stateNames = new ArrayList<>();
			stateNames.add(StateName.UNPOPULAR);
			stateNames.add(StateName.MILDLY_POPULAR);
			stateNames.add(StateName.HIGHLY_POPULAR);
			stateNames.add(StateName.ULTRA_POPULAR);
			
			SimpleStateFactoryI stateFactoryIn = new SimpleStateFactory();
			
			ContextI CC = new ChannelContext(stateFactoryIn, stateNames);
			
			int count = 0;
			while ((line = fp.poll()) != null)
			{
				count++;
				if (line.isEmpty()){
					throw new EmptyLineException("Line is NULL...!!!");
			    }
				else {
					if(line.contains("::")) {
						String[] output = line.split("::");
						if(output[0].equals(Operation.ADD_VIDEO.toString())) {
							CC.addVideo(output[1]);
						}
						else if(output[0].equals(Operation.REMOVE_VIDEO.toString())) {
							CC.removeVideo(output[1]);
						}
						else if(output[0].contains(Operation.METRICS__.toString()) && output[1].contains("[") && output[1].contains("]")) {
							
							int len = output[0].length();
							String v = output[0].substring(9, len);
							String z = output[1].substring(1, line.length()-len-3);
							CC.updateMetric(v,z);
						}
						else if(output[0].contains(Operation.AD_REQUEST__.toString()) && output[1].contains("=")) {
							int len = output[0].length();
							String v = output[0].substring(12, len);
							CC.adRequest(v,output[1]);
						}
						else {
							throw new InvalidInputFormatException("Invalid Input Format..!!");
						}
					}
					else {
						throw new InvalidInputFormatException("Invalid Input Format..!!");
					}
					
				}
			}
			if (count == 0) {
				throw new EmptyFileException("File is Empty...!!!");
			}
		} catch(InvalidInputFormatException e) {
			e.printStackTrace();
			System.exit(1);
		} catch(EmptyFileException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	/**
	 * check if given file exist or not
	 *
	 * @param	String		filename
	 * @return 	Boolean
	 * 
	 */
	private static boolean checkIfFileExists(String inputfile) {
		if (inputfile == null)
			return false;
		File file = new File(inputfile);
		return file.exists();
	}
	
	
	/**
	 * File existence checking
	 *
	 * @param	String		filename
	 * @return 	NULL
	 * @exception FileNotExistException
	 */
	public void checkFile(String string) throws FileNotExistException {
		try {
			if(!checkIfFileExists(string)){
				throw new FileNotExistException(string + " does not exist.!");
			}
		} catch (FileNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}	
	}
	
	
	/**
     * Default toString, needed for debugging here.
     * @return null
     */
    public String toString() {
        return null;
    }
}
