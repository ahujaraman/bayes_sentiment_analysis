import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
 

public class FeatureSelection {

	
	/*
	def MI(word):
	    """
	    Compute the weighted mutual information of a term.
	    """
	    T = totals[0] + totals[1]
	    W = pos[word] + neg[word]
	    I = 0
	    if W==0:
	        return 0
	    if neg[word] > 0:
	        # doesn't occur in -ve
	        I += (totals[1] - neg[word]) / T * log ((totals[1] - neg[word]) * T / (T - W) / totals[1])
	        # occurs in -ve
	        I += neg[word] / T * log (neg[word] * T / W / totals[1])
	    if pos[word] > 0:
	        # doesn't occur in +ve
	        I += (totals[0] - pos[word]) / T * log ((totals[0] - pos[word]) * T / (T - W) / totals[0])
	        # occurs in +ve
	        I += pos[word] / T * log (pos[word] * T / W / totals[0])
	    return I
	    
	    totals[0] = sum(pos.values())
        totals[1] = sum(neg.values())
	*/
	
	public static double MutualInfo(String word,int postotal,int negtotal,int posvalue,int negvalue)
	{
		double i=0;
		int t= postotal + negtotal;
		int w = posvalue + negvalue;
		if(w==0)
		{
			return 0;
		}
		if(negvalue > 0)
		{
			//# doesn't occur in -ve
			//# I += (totals[1] - neg[word]) / T * log ((totals[1] - neg[word]) * T / (T - W) / totals[1])
			i += (negtotal - negvalue)/ t * Math.log((negtotal - negvalue) * t / ( t - w) / negtotal );
			
			//# occurs in -ve
	        //I += neg[word] / T * log (neg[word] * T / W / totals[1])
			
			i += negvalue / t * Math.log( negvalue * t / w /negtotal );
			
		}
		if(posvalue > 0)
		{
			
			//# doesn't occur in +ve
	       // I += (totals[0] - pos[word]) / T * log ((totals[0] - pos[word]) * T / (T - W) / totals[0])
			i += (postotal - posvalue)/ t * Math.log((postotal - posvalue) * t / ( t - w) / postotal );
			
			
			//# occurs in +ve
	        //I += pos[word] / T * log (pos[word] * T / W / totals[0])
			
			i += posvalue / t * Math.log( posvalue * t / w /postotal );
		}
		
		return i;
	}
	
	/*
	 * Select top k features using mutual_information
	 */
	
	public static void  FeatureSelection(HashMap<String, Integer> posCounts,HashMap<String, Integer> negCounts) throws FileNotFoundException, IOException
	{
		   HashMap<String, Integer> CombinedMap = new HashMap<String, Integer>();
		   HashMap<String, Double> FeaturedSet = new HashMap<String, Double>();
		   Double mi_val=0.0;
		   String fileName = "FeaturedSet.properties";	
		   int postotal=0;
		   int negtotal=0;
		   int posvalue=0;
		   int negvalue=0;
		   
		   for (int val : posCounts.values()) {
			   postotal += val;
		    }
		  //  System.out.println(posSum);
		    
		    for (int val : negCounts.values()) {
		    	negtotal += val;
		    }
		   
		   CombinedMap.putAll(posCounts);
		   CombinedMap.putAll(negCounts);
		   
		   for (String key : CombinedMap.keySet()) {
			   posvalue = posCounts.get(key);
			   negvalue = negCounts.get(key);
			   mi_val=FeatureSelection.MutualInfo(key,postotal, negtotal, posvalue, negvalue);
			   FeaturedSet.put(key, mi_val);
	        }
		
	        /*
	         * Write this to file
	         */
		  FeatureSelection.saveMapToFile(FeaturedSet, fileName);
		   
	}
	
	
	
	private static void saveMapToFile(HashMap<String, Double> posCounts, String fileName) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		Properties properties = new Properties();

	    for (Map.Entry<String,Double> entry : posCounts.entrySet()) {
	        properties.put(entry.getKey(), entry.getValue().toString());
	    }
	    
	    properties.store(new FileOutputStream(fileName), null);
	}
	
	
	
	
	
	
}
