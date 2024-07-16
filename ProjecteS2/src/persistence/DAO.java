package persistence;

import business.estructuraArbreBin.entities.Habitant;
import business.GlobalsBusiness;
import business.estructuraArbreR.entities.Bardissa;
import business.estructuraGraph.entities.InterestPoint;
import business.estructuraGraph.entities.KnownRoute;
import business.estructuraTaules.entities.Guilty;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that represents the DAO to read the files and create the objects
 */
public class DAO {
    GlobalsBusiness globals = new GlobalsBusiness();

    /**
     * Method that reads the file and return an arrayList with all the interestPoints read
     *
     * @return an ArrayList that contains an arrayList with every interest point already created.
     * @throws FileNotFoundException in case it cant find the file.
     */
    public ArrayList<InterestPoint> readDataFileInterestPoint() throws FileNotFoundException {
        File f = new File(GlobalsBusiness.graphFilePath);
        Scanner sn = new Scanner(f);

        int nInterestPoints = Integer.parseInt(sn.nextLine());

        String[] infoInterestPoints = new String[nInterestPoints];
        for (int i = 0; i < nInterestPoints; i++) {
            infoInterestPoints[i] = sn.nextLine();
        }

        return getInfoFromInterestPoints(infoInterestPoints);
    }

    /**
     * Method that splits, gets the information and creates the object of every interestPoint read on the file
     *
     * @param infoInterestPoints Array of Strings that contains every line of the file that contains
     *                           information about the interest points.
     * @return an ArrayList that contains every interestPoint already created.
     */
    private ArrayList<InterestPoint> getInfoFromInterestPoints(String[] infoInterestPoints) {
        ArrayList<InterestPoint> temp = new ArrayList<>();

        for (String s: infoInterestPoints) {
            String[] splitInfo = s.split(";");
            int id = Integer.parseInt(splitInfo[0]);
            String name = splitInfo[1];
            String reign = splitInfo[2];
            String weather = splitInfo[3];

            temp.add(new InterestPoint(id, name, reign, weather));
        }

        return temp;
    }

    /**
     * Method that reads the file and returns an ArrayList with all the KnownRoutes read.
     *
     * @return an ArrayList that contains every knowRoute already created.
     * @throws FileNotFoundException in case it cant find the file
     */
    public ArrayList<KnownRoute> readDataFileKnownRoute() throws FileNotFoundException {
        File f = new File(GlobalsBusiness.graphFilePath);
        Scanner sn = new Scanner(f);

        int nInterestPoints = Integer.parseInt(sn.nextLine());

        //Wait for the file to read the interest points
        for (int i = 0; i < nInterestPoints; i++) {
            sn.nextLine();
        }

        int nKnownRoutes = Integer.parseInt(sn.nextLine());
        String[] infoKnowRoutes = new String[nKnownRoutes];

        for (int i = 0; i < nKnownRoutes; i++) {
            infoKnowRoutes[i] = sn.nextLine();
        }

        return getInfoFromKnownRoutes(infoKnowRoutes);
    }

    /**
     * Method that splits, gets the information and creates the object of every knownRoute read on the file
     *
     * @param infoKnowRoutes Array of Strings that contains every line of the file that contains
     *                       information about the knownRoutes.
     * @return an ArrayList that contains every knownRoute already created.
     */
    private ArrayList<KnownRoute> getInfoFromKnownRoutes(String[] infoKnowRoutes) {
        ArrayList<KnownRoute> temp = new ArrayList<>();

        for (String s: infoKnowRoutes) {
            String[] splitInfo = s.split(";");
            int pointA = Integer.parseInt(splitInfo[0]);
            int pointB = Integer.parseInt(splitInfo[1]);
            float timeE = Float.parseFloat(splitInfo[2]);
            float timeA = Float.parseFloat(splitInfo[3]);
            float distance = Float.parseFloat(splitInfo[4]);
            temp.add(new KnownRoute(pointA, pointB, timeE, timeA, distance));
        }

        return temp;
    }

    /**
     * Method that reads the file and returns an ArrayList with all the Guilty read.
     *
     * @return an ArrayList that contains every guilty already created.
     * @throws FileNotFoundException in case it cant find the file
     */
    public ArrayList<Habitant> readDataFileBruixest() throws FileNotFoundException {
        File f = new File(GlobalsBusiness.treesFilePath);
        Scanner sn = new Scanner(f);

        int nHabitants = Integer.parseInt(sn.nextLine());

        String[] infoHabitants = new String[nHabitants];
        for (int i = 0; i < nHabitants; i++) {
            infoHabitants[i] = sn.nextLine();
        }

        return getInfoFromHabitants(infoHabitants);
    }

    /**
     * Method that splits, gets the information and creates the object of every guilty read on the file
     *
     * @param infoHabitants Array of Strings that contains every line of the file that contains
     *                      information about the habitants.
     * @return an ArrayList that contains every guilty already created.
     */
    private ArrayList<Habitant> getInfoFromHabitants(String[] infoHabitants) {
        ArrayList<Habitant> temp = new ArrayList<>();

        for (String s: infoHabitants) {
            String[] splitInfo = s.split(";");
            int id = Integer.parseInt(splitInfo[0]);
            String name = splitInfo[1];
            float wheight = Float.parseFloat(splitInfo[2]);
            String realm = splitInfo[3];

            temp.add(new Habitant(id, name, wheight, realm));
        }

        return temp;
    }

    /**
     * Method that reads the file and returns an ArrayList with all the Guilty read.
     *
     * @return an ArrayList that contains every guilty already created.
     * @throws FileNotFoundException in case it cant find the file
     */
    public ArrayList<Bardissa> readDataFileBardisses() throws FileNotFoundException {
        File file = new File(GlobalsBusiness.rTreeFilePath);
        Scanner scanner = new Scanner(file);

        int numBardisses = Integer.parseInt(scanner.nextLine());

        String[] infoBardisses = new String[numBardisses];
        for (int i = 0; i < numBardisses; i++) {
            infoBardisses[i] = scanner.nextLine();
        }

        return getInfoFromBardisses(infoBardisses);
    }

    /**
     * Method that parses the information of the file and creates the objects of every bardissa read.
     *
     * @param infoBardisses Array of Strings that contains every line of the file that contains
     * @return an ArrayList that contains every guilty already created.
     * @throws FileNotFoundException in case it cant find the file
     */
    private ArrayList<Bardissa> getInfoFromBardisses(String[] infoBardisses) {
        ArrayList<Bardissa> temp = new ArrayList<>();

        for (int i = 0; i < infoBardisses.length; i++) {
            String[] splitInfo = infoBardisses[i].split(";");
            String type = splitInfo[0];
            double size = Float.parseFloat(splitInfo[1]);
            double latitude = Float.parseFloat(splitInfo[2]);
            double longitude = Float.parseFloat(splitInfo[3]);
            String color = splitInfo[4];

            temp.add(new Bardissa(type, size, latitude, longitude, color));
        }

        return temp;
    }

    /**
     * Method that reads the file and returns an ArrayList with all the Guilty read.
     *
     * @return an ArrayList that contains every guilty already created.
     * @throws FileNotFoundException in case it cant find the file
     */
    public ArrayList<Guilty> readDataFileTaules() throws FileNotFoundException {
        File file = new File(GlobalsBusiness.hashTableFilePath);
        Scanner scanner = new Scanner(file);

        int numBardisses = Integer.parseInt(scanner.nextLine());

        String[] infoAcusats = new String[numBardisses];
        for (int i = 0; i < numBardisses; i++) {
            infoAcusats[i] = scanner.nextLine();
        }

        return getInfoFromGuilties(infoAcusats);
    }

    /**
     * Method that parses the information of the file and creates the objects of every guilty read.
     *
     * @param infoAcusats Array of Strings that contains every line of the file that contains
     * @return an ArrayList that contains every guilty already created.
     * @throws FileNotFoundException in case it cant find the file
     */
    private ArrayList<Guilty> getInfoFromGuilties(String[] infoAcusats) {
        ArrayList<Guilty> temp = new ArrayList<>();

        for (int i = 0; i < infoAcusats.length; i++) {
            String[] splitInfo = infoAcusats[i].split(";");
            String name = splitInfo[0];
            int rabbits = Integer.parseInt(splitInfo[1]);
            String profesion = splitInfo[2];

            temp.add(new Guilty(name, profesion, rabbits));
        }

        return temp;
    }
}
