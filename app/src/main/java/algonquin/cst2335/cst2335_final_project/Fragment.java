package algonquin.cst2335.cst2335_final_project;

import java.util.Random;

public class Fragment {

    /**
     * Method to store Arrays from API response
     */
    public String display() {
        Random rand = new Random();
        String[] urls = {"https://mars.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00010/opgs/edr/ccam/CR0_398380890EDR_F0030000CCAM05010M_.JPG" , "https://mars.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00010/soas/rdr/ccam/CR0_398380645PRCLF0030000CCAM04010L1.PNG", "https://mars.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00065/opgs/edr/fcam/FRA_403252730EDR_F0050104FHAZ00311M_.JPG", "https://mars.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00187/opgs/edr/ccam/CR0_414094645EDR_F0060000CCAM02187M_.JPG", "https://mars.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00908/opgs/edr/fcam/FRB_478101124EDR_S0450450FHAZ00214M_.JPG", "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00657/opgs/edr/fcam/FLB_455821653EDR_F0350000FHAZ00304M_.JPG" };
        int index = rand.nextInt(urls.length); // get a random index between 0 and the length of the urls array
        return urls[index];
    }

}
