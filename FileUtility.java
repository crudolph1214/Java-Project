import java.io.*;


/**
 * The FileUtility class can be used to open, read from, and close simple text files, including .txt and .csv file types. The file
 * is automatically closed when the end of file is encountered.
 * 
 * @author IS Training
 *
 */
public class FileUtility
{
    private BufferedReader reader = null;

    /**
     * Opens the specified file. Logs an message to the console if an error is encountered opening the file.
     * 
     * @param fileName
     *            - the relative or fully-qualified file path and file name to open.
     */
    public void openReadFile( String fileName )
    {
        try
        {
            reader = new BufferedReader( new FileReader( fileName ) );
        }
        catch ( FileNotFoundException e )
        {
            System.err.println( "OPEN OF FILE FAILED - " + fileName );
            e.printStackTrace();
        }
    }

    /**
     * Reads from file specified in the openReadFile operation. One line will be read and returned at a time, and extra spaces
     * will be trimmed from the start and end of the input line. At end of file, a null is returned. This method will close the
     * read file when it encounters the end-of-file marker.
     * 
     * @return the next line of data, or null at end of file.
     */
    public String getNextLine()
    {
        String result = null;

        if ( reader != null )
        {
            try
            {
                result = reader.readLine();

                if ( result != null )
                {
                    result = result.trim();
                }
                else
                {
                    reader.close();
                }
            }
            catch ( IOException e )
            {
                System.err.println( "ERROR OCCURRED READING NEXT LINE IN FILE" );
                e.printStackTrace();
            }
        }

        return result;
    }

}
