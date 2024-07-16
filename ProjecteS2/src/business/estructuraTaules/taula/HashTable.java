package business.estructuraTaules.taula;

import business.estructuraTaules.entities.Guilty;

import java.util.ArrayList;

/**
 * Hash table class that represents a hash table
 */
public class HashTable {

    // Atributs
    private int arrayTotalLenght;
    private ArrayList<Guilty>[] guiltiesFromRabbits;
    private ArrayList<Guilty>[] guiltiesFromName;
    private int conflictsName = 0;
    private int conflictsEnter = 0;
    private GuiltyChart guiltyChart;

    /**
     * Constructor of the class
     *
     * @param mida size of the table
     * @param guiltyChart guilty chart
     */
    public HashTable (int mida, GuiltyChart guiltyChart) {
        guiltiesFromRabbits = new ArrayList[mida];
        guiltiesFromName = new ArrayList[mida];
        this.guiltyChart = guiltyChart;
    }

    /**
     * Getter of conflictsName
     *
     * @return number of conflictsName
     */
    public int getConflictsName() {
        return conflictsName;
    }

    /**
     * Getter of conflictsEnter
     *
     * @return number of conflictsEnter
     */
    public int getConflictsEnter() {
        return conflictsEnter;
    }

    /**
     * Insert a guilty in the table using a string as a keyValue
     *
     * @param guilty guilty to insert
     * @param keyValue key value
     * @return key
     */
    public int insert(Guilty guilty, String keyValue) {
        int key = hashFunction(keyValue);
        boolean isCollision = false;
        try {
            if (guiltiesFromName[key].isEmpty()) {
                guiltiesFromName[key] = new ArrayList<Guilty>();
                guiltiesFromName[key].add(guilty);
            } else {
                guiltiesFromName[key].add(guilty);
                isCollision = true;
                conflictsName++;
            }
        } catch (Exception e) {
            guiltiesFromName[key] = new ArrayList<Guilty>();
            guiltiesFromName[key].add(guilty);
        }

        return key;
    }

    /**
     * Insert a guilty in the table using an int as a keyValue
     *
     * @param guilty guilty to insert
     * @param KeyValue key value
     * @return key
     */
    public int insert (Guilty guilty, int KeyValue){
        int key = hashFunction(KeyValue);
        boolean isCollision = false;
        try {
            if (guiltiesFromRabbits[key].isEmpty()) {
                guiltiesFromRabbits[key] = new ArrayList<Guilty>();
                guiltiesFromRabbits[key].add(guilty);
            } else {
                guiltiesFromRabbits[key].add(guilty);
                isCollision = true;
                conflictsEnter++;
            }
        } catch (Exception e) {
            guiltiesFromRabbits[key] = new ArrayList<Guilty>();
            guiltiesFromRabbits[key].add(guilty);
        }

        return key;
    }

    /**
     * Hash function for a string
     *
     * @param keyValue key value
     * @return key
     */
    private int hashFunction(String keyValue) {
        double key = 0;
        int prime = 31;
        int prime2 = 89;
        char[] letters = keyValue.toCharArray();
        for (int i = 0; i < keyValue.length()/2; i++) {
            if (i % 2 == 0 && i != 0) {
                key += letters[i] * letters[i - 1];
            } else if (i % 3 == 0) {
                key *= letters[i] + prime2;
            } else {
                key += letters[i] * i - prime;
            }
        }

        return (int) key % guiltiesFromName.length;
    }


    /**
     * Hash function for rabbits (int)
     *
     * @param keyValue key value
     * @return key
     */
    private int hashFunction(int keyValue) {
        int minims = 20;
        String binaryString = Integer.toBinaryString(keyValue);
        int key = 0;
        int prime = 31;
        char[] binaryNumber = binaryString.toCharArray();
        char[] auxBinaryNumber = new char[minims - binaryNumber.length];
        char[] charArray = new char[minims];
        for (int i = 0; i < minims; i++) {
            if (i < minims - binaryNumber.length) {
                charArray[i] = '0';
            } else {
                charArray[i] = binaryNumber[i - (minims - binaryNumber.length)];
            }
        }

        int ones = 0;
        int zeros = 0;
        for (char c : charArray) {
            if (c == '1') {
                ones++;
            } else {
                zeros++;
            }
        }
        for (char c : charArray) {
            if (c == '1') {
                key += keyValue * zeros + prime;
            } else {
                key += keyValue * ones;
            }
        }

        //Operacions bit a bit amb numeros primers
        int primer = 167;
        int position = 31;

        for (int i = 0; i < 4; i++) {
            int value = (keyValue >> (i * 8));
            position = (position * primer) ^ value;
        }

        return Math.abs(position % guiltiesFromRabbits.length);
    }


    /**
     * Method that deletes a guilty from the table
     *
     * @param name name of the guilty
     * @return message if it has been deleted or not
     */
    public String deleteGuilty(String name) {
        boolean ok = false;
        int rabbits = 0;
        for (Guilty guilty: guiltiesFromName[hashFunction(name)]) {
            if (guilty.getName().equals(name)) {
                rabbits = guilty.getRabbits();
                guiltiesFromName[hashFunction(name)].remove(guilty);
                ok = true;
                break;
            }
        }
        if (ok) {
            for (Guilty guilty: guiltiesFromRabbits[hashFunction(rabbits)]) {
                if (guilty.getName().equals(name)) {
                    guiltiesFromRabbits[hashFunction(rabbits)].remove(guilty);
                    return "\nL’execució pública de "+ name +" ha estat un èxit.";
                }
            }
        }
        return "L'acusat " + name + " no ha estat trobat a la llista de culpables.";
    }

    /**
     * Method that adds a guilty to the table
     *
     * @param guilty guilty to add
     */
    public void addGuilty(Guilty guilty) {
        if(guiltiesFromName[hashFunction(guilty.getName())] == null) guiltiesFromName[hashFunction(guilty.getName())] = new ArrayList<Guilty>();
        guiltiesFromName[hashFunction(guilty.getName())].add(guilty);
        if(guiltiesFromRabbits[hashFunction(guilty.getRabbits())] == null) guiltiesFromRabbits[hashFunction(guilty.getName())] = new ArrayList<Guilty>();
        guiltiesFromRabbits[hashFunction(guilty.getRabbits())].add(guilty);
    }

    /**
     * Method that applies "edicte de gracia" to a guilty
     *
     * @param name name of the guilty
     * @param isHeretge true if the guilty is heretge
     * @return message whether it has been applied or not
     */
    public String edicteGracia(String name, boolean isHeretge) {
        ArrayList<Guilty> guilties = guiltiesFromName[hashFunction(name)];
        if (guilties == null) {
            return "L'acusat " + name + " no ha estat trobat a la llista de culpables.";
        }

        for (Guilty guilty: guilties) {
            if (guilty.getName().equals(name)) {
                if(!edicteGraciaRabbits(guilty.getRabbits(), isHeretge)) {
                    return "L'acusat " + name + " no ha estat trobat a la llista de culpables.";
                }
                if(guilty.getProfesion().equals("KING")){
                    return "No es pot aplicar l'edicte de gracia ja que l'acusat és un KING";
                } else if (guilty.getProfesion().equals("QUEEN")) {
                    return "No es pot aplicar l'edicte de gracia ja que l'acusat és un QUEEN";
                } else if (guilty.getProfesion().equals("CLERGYMAN")) {
                    return "No es pot aplicar l'edicte de gracia ja que l'acusat és un CLERGYMAN";
                }

                if(isHeretge){
                    guilty.setHeretge(true);
                    return "La Inquisició Espanyola ha conclòs que "+ name +" és un heretge.";
                } else {
                    guilty.setHeretge(false);
                    return "La Inquisició Espanyola ha conclòs que "+ name +" NO és un heretge.";
                }

            }

        }

        return "No s'ha trobat l'acusat";
    }

    /**
     * Method that applies the edicte de gracia to a guilty
     *
     * @param rabbits number of rabbits of the guilty
     * @param isHeretge true if the guilty is heretge
     * @return message if it has been applied or not
     */
    private boolean edicteGraciaRabbits(int rabbits, boolean isHeretge) {
        ArrayList<Guilty> guilties = guiltiesFromRabbits[hashFunction(rabbits)];
        if (guilties == null) {
            return false;
        }

        for (Guilty guilty: guilties) {
            if (guilty.getRabbits() == rabbits) {
                if(guilty.getProfesion().equals("KING")){
                    return true;
                } else if (guilty.getProfesion().equals("QUEEN")) {
                    return true;
                } else if (guilty.getProfesion().equals("CLERGYMAN")) {
                    return true;
                }

                if(isHeretge){
                    guilty.setHeretge(true);
                    return true;
                } else {
                    guilty.setHeretge(false);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method that applies the final judgement to a guilty
     *
     * @param name name of the guilty
     * @return Guilty found or null whether it has been found or not
     */
    public Guilty judiciFinal(String name) {
        for(Guilty guilty: guiltiesFromName[hashFunction(name)]) {
            if(guilty.getName().equals(name)) {
                return guilty;
            }
        }
        return null;
    }

    /**
     * Method that applies the final judgement to a guilty given the number of rabbits
     *
     * @param minRabbits minimum number of rabbits
     * @param maxRabbits maximum number of rabbits
     * @return ArrayList of guilties found with the number of rabbits between minRabbits and maxRabbits
     */
    public ArrayList<Guilty> judiciFinal(int minRabbits, int maxRabbits) {
        ArrayList<Guilty> guilties = new ArrayList<>();

        for (ArrayList<Guilty> guilties1 : guiltiesFromRabbits) {
            if(guilties1 != null) {
                for (Guilty guilty : guilties1) {
                    if (guilty.getRabbits() >= minRabbits && guilty.getRabbits() <= maxRabbits) guilties.add(guilty);
                }
            }
        }
        return guilties;
    }

    /**
     * Method that shows a histogram of the guilties based on their professions
     */
    public void histograma() {
        int kings = 0, knights = 0, queens = 0, minstrels = 0, peasants = 0, shrubbers = 0, clergymans = 0, enchanters = 0;

        for (ArrayList<Guilty> guilties: guiltiesFromName) {
            if(guilties == null) continue;
            for (Guilty guilty: guilties) {
                switch (guilty.getProfesion()) {
                    case "KING" -> kings++;
                    case "KNIGHT" -> knights++;
                    case "QUEEN" -> queens++;
                    case "MINSTREL" -> minstrels++;
                    case "PEASANT" -> peasants++;
                    case "SHRUBBER" -> shrubbers++;
                    case "CLERGYMAN" -> clergymans++;
                    case "ENCHANTER" -> enchanters++;
                }
            }
        }

        generateChart(kings, knights, queens, minstrels, peasants, shrubbers, clergymans, enchanters);
    }

    /**
     * Method that generates the chart of the histogram using the number of guilties of each profession
     *
     * @param kings number of kings
     * @param knights number of knights
     * @param queens number of queens
     * @param minstrels number of minstrels
     * @param peasants number of peasants
     * @param shrubbers number of shrubbers
     * @param clergymans number of clergymans
     * @param enchanters number of enchanters
     */
    private void generateChart(int kings, int knights, int queens, int minstrels, int peasants, int shrubbers, int clergymans, int enchanters) {
       guiltyChart.generateChart(kings, knights, queens, minstrels, peasants, shrubbers, clergymans, enchanters);
    }
}