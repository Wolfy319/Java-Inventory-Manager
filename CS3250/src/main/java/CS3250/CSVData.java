package CS3250;

import java.util.HashMap;
import java.util.Vector;

public class CSVData implements DataInterface {
    /*  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        this is where we need to create our methods
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        One note is that we already have a list of Entry type objects, so when dealing with the list 
        we will be getting or setting entry objects which holds all our individual datas.
    */

    private Vector<Entry>  Data = new Vector<Entry>();
    private int NumEntries;
    private HashMap<String, Entry> initialData = new HashMap<String, Entry>();
    


    @Override
    public void initializeDatabase(String filename) {
    	// Initialize a HashMap that stores all inventory data
    	HashMap<String, Entry> initData = new HashMap<String, Entry>();
    	// Initialize a new parser
    	CSVParser parse = new CSVParser();
    	
    	// Load the HashMap with entries
        parse.readCSV(filename, this);
       
        // Track how many entries were added
        this.NumEntries = initialData.size();
       
    }

    @Override
    public void createEntry(String ID, Entry e) {
        // Put into hashmap and creates a new entry
        initialData.put(ID, e);
            
    }

    @Override
    public Entry readEntry(String ID) {
        // What entry is to be read from the hashmap
        return initialData.get(ID);

    }

    @Override
    public void updateEntry(String ID, Entry e) {
        // we will just replace the entry with a new one that 
        // auto fills the information that the user did not want to 
        // change.
        deleteEntry(ID);
        createEntry(ID, e);
    }

    @Override
    public void deleteEntry(String id) {
        // deletes entry using id provided
        initialData.remove(id);
    }

    @Override
    public void saveEntry(Entry e) {
        createEntry(e.getProductID(), e);
    }
    
    @Override
    public int retSize() {
        // returns size of HashMap to test delete function
        return initialData.size();
    }
}
