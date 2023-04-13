package algonquin.cst2335.cst2335_final_project.ui;

import java.util.Random;

public class Fragment {

    /**
     * Method to store Arrays from API response
     */
    public String display() {
        Random rand = new Random();
        String[] urls = {"https://mars.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00010/opgs/edr/ccam/CR0_398380890EDR_F0030000CCAM05010M_.JPG" };
        int index = rand.nextInt(urls.length); // get a random index between 0 and the length of the urls array
        return urls[index];
    }

}
