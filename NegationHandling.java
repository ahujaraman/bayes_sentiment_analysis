
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class NegationHandling {

	public static void main(String[] args) throws FileNotFoundException, IOException {

        ArrayList<ArrayList<String>> posTrainSet = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> negTrainSet = new ArrayList<ArrayList<String>>();
        
        posTrainSet = StanfordLemmatizer.getTrainingDataP();
        negTrainSet = StanfordLemmatizer.getTrainingDataN();
        System.out.println("Pos Train Set");
        printTrainingSet(posTrainSet);
        
        
        
        System.out.println("Neg Train Set");
        printTrainingSet(negTrainSet);

        HashMap<String, Integer> posCounts = new HashMap<String, Integer>();
        HashMap<String, Integer> negCounts = new HashMap<String, Integer>();
        
        
        buildTrainingSet(posTrainSet,posCounts,negCounts);
        
        buildTrainingSet(negTrainSet,negCounts,posCounts);
        
       
		System.out.println("Pos Hashmap");
		printMap(posCounts);
		
        
		 System.out.println("Neg Hashmap");   
	    printMap(negCounts);
	    
	    //Saving in Pos File
	    String fileName = "posMap.properties";
	    saveMapToFile(posCounts,fileName);
	    
	    
	  //Saving in Neg File
	    fileName = "negMap.properties";
	    saveMapToFile(negCounts,fileName);
    
	}

	private static void printTrainingSet(ArrayList<ArrayList<String>> TrainSet) {
		// TODO Auto-generated method stub
		
		System.out.println("Map Size: "+ TrainSet.size());
        for(int i = 0 ; i < TrainSet.size() ; i++) {
            ArrayList<String> currentReview = TrainSet.get(i);
            
            //now iterate on the current list
            for (int j = 0; j < currentReview.size(); j++) {
                String s = currentReview.get(j);
                System.out.print(s+" ");
                
            }
            System.out.println();
        }
	}

	private static void buildTrainingSet(ArrayList<ArrayList<String>> posTrainSet, HashMap<String, Integer> hashMap1,
			HashMap<String, Integer> hashMap2)
	{
		// TODO Auto-generated method stub
	for (ArrayList<String> currentReview : posTrainSet) 
	{ 	
	        	
    	int negation = 0;
    	
    	for(String word : currentReview)
        {
    		//ADD OTHER NEGATIVE WORDS.ALSO CONSIDER NOT ONLY AND NEITHER NOR
        	if(word.equals("not") || word.equals("but") || word.equals("no") || word.equals("never"))
        	{
        		negation = 1- negation;
        	}
        	else if(word.equals("."))
        	{
        		negation =0;
        	}
        	else if(negation == 0)
        	{
        		Integer count = hashMap1.get(word);
    		    if (count == null) {
    		        count = 0;
    		    }
    		    hashMap1.put(word, count + 1);
    		    
    		    //ADD TO NEGATIVE NOT_WORD
    		    String negForm = "NOT_"+ word;
    		    count = hashMap2.get(negForm);
    		    if (count == null) {
    		        count = 0;
    		    }
    		    hashMap2.put(negForm, count + 1);
    		    
        	}
        	else
        	{
        		String negForm = "NOT_"+ word;
        		Integer count = hashMap1.get(negForm);
    		    if (count == null) {
    		        count = 0;
    		    }
    		    hashMap1.put(negForm, count + 1);
        	}
        	
        }
	
       }
	}

	private static void saveMapToFile(HashMap<String, Integer> posCounts, String fileName) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		Properties properties = new Properties();

	    for (Map.Entry<String,Integer> entry : posCounts.entrySet()) {
	        properties.put(entry.getKey(), entry.getValue().toString());
	    }
	    
	    properties.store(new FileOutputStream(fileName), null);
	}

	private static void printMap(HashMap<String, Integer> hashMap) {
		// TODO Auto-generated method stub
		for (String key : hashMap.keySet()) {
		    System.out.println("Key: " + key + " Value: " + hashMap.get(key));
        }
        System.out.println("Total features in Hashmap: "+ hashMap.size());
	}
}
