package channelpopularity.driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

import channelpopularity.util.ChannelHelper;
import channelpopularity.util.FileProcessor;
import channelpopularity.util.Results;
import channelpopularity.validations.EmptyFileException;
import channelpopularity.validations.EmptyLineException;
import channelpopularity.validations.FileNotExistException;
import channelpopularity.validations.InvalidInputFormatException;
import channelpopularity.validations.InvalidInputValueException;
import channelpopularity.validations.videoAlreadyExistsException;
import channelpopularity.validations.videoDoesNotExistsException;

/**
 * @author Kasturi T. More
 *
 */

/**
 * The Driver class
 */
public class Driver {
	/**
	 * An int variable to acceptable number of command line arguments
	 */
	private static final int REQUIRED_NUMBER_OF_CMDLINE_ARGS = 2;

	public static void main(String[] args) throws EmptyLineException, EmptyFileException, InvalidInputFormatException, videoAlreadyExistsException, videoDoesNotExistsException, InvalidInputValueException, FileNotExistException, InvalidPathException, SecurityException, FileNotFoundException, IOException {

		/*
		 * As the build.xml specifies the arguments as input,output or metrics, in case the
		 * argument value is not given java takes the default value specified in
		 * build.xml. To avoid that, below condition is used
		 */
		if ((args.length != 2) || (args[0].equals("${input}")) || (args[1].equals("${output}"))) {
			System.err.printf("Error: Incorrect number of arguments. Program accepts %d arguments.", REQUIRED_NUMBER_OF_CMDLINE_ARGS);
			System.exit(0);
		}
		//System.out.println("Hello World! Lets get started with the assignment");
			ChannelHelper CH = new ChannelHelper();
			CH.checkFile(args[0]);
			
			FileProcessor myProcessor = new FileProcessor(args[0]);
			Results res1 = new Results();
			res1.setOutputFileName(args[1]);
			
			CH.lineProcessor(myProcessor);
			
			res1.writeToFile();
			res1.writeToStdout();
	}
}
