import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;


public class TestFeatureSelection {
	
	
	private static void loadMap(Map<String, Double> hashMap, String fileName) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		 Properties properties = new Properties();
		    properties.load(new FileInputStream(fileName));

		    for (String key : properties.stringPropertyNames()) {
		    	hashMap.put(key, Double.parseDouble(properties.get(key).toString()));
		    }
		    
	}
	
	
	private static void loadToMap(Map<String, Integer> hashMap, String fileName) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		 Properties properties = new Properties();
		    properties.load(new FileInputStream(fileName));

		    for (String key : properties.stringPropertyNames()) {
		    	hashMap.put(key, Integer.parseInt(properties.get(key).toString()));
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
	
	
	public static void CreateFeaturedSet_limit(int limit) throws FileNotFoundException, IOException
	{
		
		 HashMap<String, Integer> posCounts = new HashMap<String, Integer>();
	     HashMap<String, Integer> negCounts = new HashMap<String, Integer>();
	     HashMap<String, Double> FeaturedSet = new HashMap<String, Double>();
	     
		//Loadin in Pos Map
	    String fileName = "posMap.properties";
	    TestFeatureSelection.loadToMap(posCounts, fileName); 
	    
	    
	  //Loading in Neg Map
	    fileName = "negMap.properties";
	    TestFeatureSelection.loadToMap(negCounts, fileName); 
	    
	    //Loading FeaturedSet
	    fileName = "FeaturedSet.properties";
	    TestFeatureSelection.loadMap(FeaturedSet, fileName);
	    
	    
	    /*
		    * Now sort featured set on mi_val(value) in desecending order
		    */
	         
		   Set<Entry<String, Double>> set = FeaturedSet.entrySet();
	        List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(set);
	        Collections.sort( list, new Comparator<Map.Entry<String, Double>>()
	        {
	            public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 )
	            {
	                return (o2.getValue()).compareTo( o1.getValue() );
	            }
	        } );
	        
	        
	        String filePath="C:\\Users\\waheguruji\\javaprograms\\Sentiment Test\\FeaturedSet\\posFeatuedMap.properties";
	        TestFeatureSelection.CreateFinalSet(limit, posCounts, FeaturedSet, filePath);
	        filePath="C:\\Users\\waheguruji\\javaprograms\\Sentiment Test\\FeaturedSet\\negFeatuedMap.properties";
	        TestFeatureSelection.CreateFinalSet(limit, negCounts, FeaturedSet, filePath);
	        
	    
	}
	
	public static void CreateFinalSet(int limit,HashMap<String, Integer> posCounts,HashMap<String, Double> FeaturedSet,String filePath) throws FileNotFoundException, IOException
	{
		 HashMap<String, Integer> posFeatured = new HashMap<String, Integer>();
	     HashMap<String, Integer> negFeatured = new HashMap<String, Integer>();
		int i=0;
		int val=0;
		
		for (String key : FeaturedSet.keySet()) {
			i++;
			if(i<=limit)
			{
				if(posCounts.get(key)!=null)
				{
					val=posCounts.get(key);
					posFeatured.put(key, val);
				}
			}
			else
			{
				break;
			}   
        }
		TestFeatureSelection.saveMapToFile(posCounts, filePath);
		
	}
	

}
