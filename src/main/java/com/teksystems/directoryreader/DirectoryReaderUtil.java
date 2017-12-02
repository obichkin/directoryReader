package com.teksystems.directoryreader;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

/**
 * The purpose of this utility is to read and print all the file names under
 * parent directory and all it's subdirectories recursively. The project is
 * working code based on Maven and Java 8. The candidates are supposed to
 * re-factor this utility using good OO principles e.g. Single responsibility
 * principle, interface segregation and code readability. Besides this proper
 * exception handling should also be implemented when make sense. All the code
 * should be and must be backed by unit testing. Note: the code can be imported
 * and run in any ide, but when candidates submit the code, they are supposed to
 * take all the ide specific code out and submit the project as a zip file with
 * candidate first name and last name as the file name. To run: mvn package and
 * then mvn exec:java
 *
 * @author michael.obichkin
 */
public class DirectoryReaderUtil {

	private static final String defaultDir = "./";
	private static int indent;

    /**
     * This methods list and print all files in default directory recursively
     *
     */
    public void listAllFilesRecursively() {
        listAllFilesRecursively(defaultDir);
    }


	/**
	 * This methods list and print all files recursively
	 *
	 * @param root represents the root directory path.
	 */
	public void listAllFilesRecursively(String root){

	    try {

	        if(root!=null){
                File dir = new File(root);
                if (dir.exists()) {

                    indent = dir.getAbsolutePath().length() - dir.getName().length();
                    printFileOnConsole(dir);
                    listDirectory(dir, 0);

                } else {
                    System.out.println("Directory " + dir + " does not exist.\n" +
                            "Please specify correct directory path.");
                }

            }

        }catch (Exception e){
            System.err.println("Caught Exception" + e.getMessage());
        }
	}

	/**
     * This method prints all files in the directory recursively.
     *
     * @param dir represents the directory to be printed.
     * @param tabs represents the indentation for the printed string.
     */
	private void listDirectory(File dir, int tabs) {

        File[] files = dir.listFiles();
        for(File file: files) {
            printFileOnConsole(file, tabs+1);
            if(file.isDirectory()) {
                listDirectory(file, tabs+1);
            }

        }

    }


    /**
     * This method prints directory or file on console
     *
     * @param file File object to print.
     */
	private void printFileOnConsole(File file) {
	    printFileOnConsole(file, 0);
    }

	/**
	 * This method prints directory or file on console
	 *
	 * @param file File object to print.
	 * @param tabs Tabulation for the printed string.
	 */
	private void printFileOnConsole(File file, int tabs) {

        for(int i=0; i<tabs; i++){
            System.out.print("  ");
        }

        if(file.isDirectory()) {
            System.out.println(
                    "- Project: "   + file.getName() +
                    " - URL: "      + file.getAbsolutePath().substring(indent)
            );
        }else{
            System.out.println(
                    "- Document: "      + file.getName() +
                    " - Extension: ."   + FilenameUtils.getExtension(file.getName()) +
                    " - URL: "          + file.getAbsolutePath().substring(indent)
            );

        }


    }

	/**
	 * Main method, reading the directory and recursively printing its output.
	 *
	 * @param args args[0] represent the directory path.
     *         If arg[0] is not set up, the default directory "./testdirectory/Main Project" is used.
	 */
	public static void main(String[] args) {

	    String dir = (args.length>0) ? args[0] : defaultDir;
        new DirectoryReaderUtil().listAllFilesRecursively(dir);

	}

}
