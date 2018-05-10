import java.util.ArrayList;

public class ReservationSystem
{
    // Welcomes the user and begins to start the program
    public ReservationSystem()
    {
        System.out.println("Welcome to the seat reservation program!");
        System.out.println("");
        run();
    }
    
    // All of the varaibles needing to do with the files
    ArrayList<String> planesArray = new ArrayList<String>();
    FileUtility planesFile = new FileUtility();
    FileUtility resFile = new FileUtility();
    String resFileName = "reservations.txt";
    String planesFileName = "planes.txt";
    
    // Strings needed for later in the code
    String planes;
    String res;
    String holdPlaneNbr;
    
    // Used to hold all the names that change
    String resName;
    String resType;
    String resFlightNbr;
    String planeFlightNbr;
    String departAirport;
    String arriveAirport;
    
    // Used in counting for the diagrams and standy
    int businessCount;
    int firstCount;
    int economyCount;
    int standbyCount;
    
    // Used as the icon for the seats on the diagram
    String seatmarker;
    
    public void run()
    {
        startUp();

        int i = 0; // Used to find the size of the array
        
        while (i < planesArray.size())
        {
            processPlanes(i);
            i++;
            displayPlaneData(); // Displays the chart and plane information
            
            // Resets the data for the next plane
            businessCount = 0;
            firstCount = 0;
            economyCount = 0;
            standbyCount = 0;
          
        }
    }
    
    private void startUp()
    {
        planesFile.openReadFile(planesFileName);
        resFile.openReadFile(resFileName);
        loadPlanes();
        res = resFile.getNextLine();
    }
    
    // Adds all of the planes to the array
    private void loadPlanes()
    {
        planes = planesFile.getNextLine();
        
        while (planes != null)
        {
            planesArray.add(planes);
            planes = planesFile.getNextLine();  
        }
    }
    
    // Reads through the plane array and gets the data for each plane
    private void processPlanes(int x)
    {
        planes = planesArray.get(x);
        
        holdPlaneNbr = getPlaneFlightNumber(planes);
                
        planeFlightNbr = getPlaneFlightNumber(planes);
        departAirport = getPlaneDepartureAirport(planes);
        arriveAirport = getPlaneArrivalAirport(planes);
        
        // Runs the program until there are no more reservations
        while ((res != null) && (getReservationFlightNumber(res).equals(holdPlaneNbr)))
        {
            readRes();
            res = resFile.getNextLine();
        }
        
    }
    
    private boolean readRes()
    {
        boolean validSeat = true;
        
        // Pulls the letter from the file and assigns it its real name
        // Also adds to the count in each class
        switch (getReservationType(res))
        {
            case 'F': // First Class
            {
                if (firstCount <= 5)
                {
                    resType = "First Class";
                    firstCount++;
                }
                
                else
                {
                    resType = "Standby";
                    standbyCount++;
                }
            }
            break;
            
            case 'B': // Business Class
            {
                if (businessCount <= 11)
                {
                    resType = "Business Class";
                    businessCount++;
                }
                
                else
                {
                    resType = "Standby";
                    standbyCount++;
                }
            }
            break;
            
            case 'E': // Economy Class
            {
                if (economyCount <= 29)
                {
                    resType = "Economy Class";
                    economyCount++;
                }
                
                else
                {
                    resType = "Standby";
                    standbyCount++;
                }
            }
            break;
            
            // Error handling to catch something put in incorrectly
            default:
            {
                System.out.println("There has been an error within our system,"
                        + " sorry for the inconvenience");
                resType = "Invalid";
                validSeat = false;
            }
            break;
            
        }
        // Displays all the data of the reservation
        displayResData();
            
        return validSeat;
    }
    
    // Displays reservation data for each flyer
    private void displayResData()
    {
        // Prints out the information of the reservations
        resName = getReservationName(res);
        resFlightNbr = getReservationFlightNumber(res);
        System.out.println("*************************************");
        System.out.println("Flight Number:     " + resFlightNbr);
        System.out.println("Reservation Type:  " + resType);
        System.out.println("Customer:          " + resName);
        System.out.println("*************************************");
        System.out.println("");
    }
    
    // Displays data for each plane
    private void displayPlaneData()
    {
        // Prints out formatted and nice looking details of the plane details and the reservations on the plane
        System.out.println("Plane Reservation Details");
        System.out.println("-------------------------------");
        System.out.println("Flight Number:   " + planeFlightNbr);
        System.out.println("Departure Airport:   " + departAirport);
        System.out.println("Arrival Airport:   " + arriveAirport);
        System.out.println("Standby Reservations:   " + standbyCount);
        System.out.println("-------------------------------");
        System.out.println("Reservations:");
        buildFirstDiagram();
        buildBusinessDiagram();
        buildEconomyDiagram();
        System.out.println("-------------------------------");
        System.out.println("");
    }
    
    // Buils the First Class row of the diagram, the first row
    // Each while loop is a different row of the diagram 
    private void buildFirstDiagram()
    {
        // Maximum amount of passangers in First Class, used to decide if someone is in a seat or not
        int maxFirst = 6;
        System.out.print("-");
        while (maxFirst != 0)
        {
            if (firstCount > 0)
            {
                seatmarker = "XX";
                firstCount--;
                maxFirst--;
            }
            
            else
            {
                seatmarker = "__";
                maxFirst--;
            }
            System.out.print(seatmarker + "-");
        }
    }
    
    // Buils the Business Class row of the diagram , the two after first class
    // Each while loop is a different row of the diagram 
    private void buildBusinessDiagram()
    {
        // Maximum amount of passangers in Business Class, used to decide if someone is in a seat or not
        int maxBusiness = 12;
        System.out.println("");
        System.out.print("-");
        while (maxBusiness >= 7)
        {
            if (businessCount > 0)
            {
                seatmarker = "XX";
                businessCount--;
                maxBusiness--;
            }
            
            else
            {
                seatmarker = "__";
                maxBusiness--;
            }
            
            System.out.print(seatmarker + "-");
            
        }
        
        // Print lines to format it and make it look clean 
        System.out.println("");
        System.out.print("-");
        
        while (maxBusiness >= 1)
        {
            if (businessCount > 0)
            {
                seatmarker = "XX";
                businessCount--;
                maxBusiness--;
            }
            
            else
            {
                seatmarker = "__";
                maxBusiness--;
            }
            
            System.out.print(seatmarker + "-");
        }
    }
    
    // Buils the Economy Class row of the diagram, the rest of the diagram
    // Each while loop is a different row of the diagram   
    private void buildEconomyDiagram()
    {
        // Maximum amount of passangers in Economy Class, used to decide if someone is in a seat or not
        int maxEconomy = 30;
        System.out.println("");
        System.out.print("-");
        
        while (maxEconomy > 24)
        {
            if (economyCount > 0)
            {
                seatmarker = "XX";
                economyCount--;
                maxEconomy--;
            }
            
            else
            {
                seatmarker = "__";
                maxEconomy--;
            }
            
            System.out.print(seatmarker + "-");
        }
        
        // Print lines to format it and make it look clean
        System.out.println("");
        System.out.print("-");
        
        while (maxEconomy > 18)
        {
            if (economyCount > 0)
            {
                seatmarker = "XX";
                economyCount--;
                maxEconomy--;
            }
            
            else
            {
                seatmarker = "__";
                maxEconomy--;
            }
            
            System.out.print(seatmarker + "-");
        }
        
        // Print lines to format it and make it look clean
        System.out.println("");
        System.out.print("-");
        
        while (maxEconomy > 12)
        {
            if (economyCount > 0)
            {
                seatmarker = "XX";
                economyCount--;
                maxEconomy--;
            }
            
            else
            {
                seatmarker = "__";
                maxEconomy--;
            }
            
            System.out.print(seatmarker + "-");
        }
        
        // Print lines to format it and make it look clean
        System.out.println("");
        System.out.print("-");
        
        while (maxEconomy > 6)
        {
            if (economyCount > 0)
            {
                seatmarker = "XX";
                economyCount--;
                maxEconomy--;
            }
            
            else
            {
                seatmarker = "__";
                maxEconomy--;
            }
            
            System.out.print(seatmarker + "-");
        }
        
        // Print lines to format it and make it look clean
        System.out.println("");
        System.out.print("-");
        
        while (maxEconomy > 0)
        {
            if (economyCount > 0)
            {
                seatmarker = "XX";
                economyCount--;
                maxEconomy--;
            }
            
            else
            {
                seatmarker = "__";
                maxEconomy--;
            }
            
            System.out.print(seatmarker + "-");
        }
        System.out.println("");
    }
    
    // BEGIN IMPORT METHODS
    /**
     * Extracts the reservation's customer name from the reservation record.
     * 
     * @param record
     * @return String - the customer name.
     */
    private String getReservationName( String record )
    {
        return record.substring( 5 );
    }

    /**
     * Extracts the reservation type from a reservation record.
     * 
     * @param record
     * @return char - the character reservation type code.
     */
    private char getReservationType( String record )
    {
        return record.charAt( 0 );
    }

    /**
     * Extracts the 4-character flight number from a reservation number.
     * 
     * @param record
     * @return String - the flight number.
     */
    private String getReservationFlightNumber( String record )
    {
        return record.substring( 1, 5 );
    }

    /**
     * Extracts the 4-character flight number from a plane record.
     * 
     * @param record
     * @return String - the flight number.
     */
    private String getPlaneFlightNumber( String record )
    {
        return record.substring( 0, 4 );
    }

    /**
     * Extracts the plane departure airport 3-character code from the plane
     * record.
     * 
     * @param record
     * @return String - the departure airport code.
     */
    private String getPlaneDepartureAirport( String record )
    {
        return record.substring( 4, 7 );
    }

    /**
     * Extracts the plane arrival airport 3-character code from the plane
     * record.
     * 
     * @param record
     * @return String - the arrival airport code.
     */
    private String getPlaneArrivalAirport( String record )
    {
        return record.substring( 7, 10 );
    }
}
    // END IMPORT METHODS
