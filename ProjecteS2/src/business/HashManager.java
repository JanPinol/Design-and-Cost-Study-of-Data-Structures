package business;

import business.estructuraTaules.entities.Guilty;
import business.estructuraTaules.taula.GuiltyChart;
import business.estructuraTaules.taula.HashTable;
import persistence.DAO;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HashManager {
    private HashTable hashTable;
    GuiltyChart guiltyChart;

    public HashManager(GuiltyChart guiltyChart) {
        this.guiltyChart = guiltyChart;
    }

    public void createTable() {
        DAO dao = new DAO();
        ArrayList<Guilty> guilties;
        try {
            guilties = dao.readDataFileTaules();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }

        this.hashTable = new HashTable(guilties.size(), guiltyChart);

        for (Guilty g: guilties) {
            hashTable.insert(g, g.getName());
            hashTable.insert(g, g.getRabbits());
        }
    }
    public void insert(String guiltyName, int guiltyRabbits, String profesion) {
        Guilty guilty = new Guilty(guiltyName, profesion, guiltyRabbits);

        hashTable.addGuilty(guilty);
    }

    public String remove(String guiltyName) {
        return hashTable.deleteGuilty(guiltyName);
    }

    public String edicteGracia(String name, boolean heretic) {
        return hashTable.edicteGracia(name, heretic);
    }

    public String judiciFinal(String guiltyName) {
        Guilty guilty = hashTable.judiciFinal(guiltyName);
        if (guilty == null) { return "No s'ha trobat el culpable"; }
        else {
            return guilty.getRabbits() + " " + guilty.getProfesion() + " " + guilty.isHeretge();
        }
    }

    public ArrayList<Guilty> judiciFinalRang(int minRabbits, int maxRabbits) {
        return hashTable.judiciFinal(minRabbits, maxRabbits);
    }

    public void histograma() {
        hashTable.histograma();
    }

}
