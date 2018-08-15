
import java.io.File;
import java.io.IOException;
import java.util.*; 

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.*; 
import edu.stanford.nlp.ling.CoreAnnotations.*; 
import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class StanfordLemmatizer
{
	
    public  static ArrayList<ArrayList<String>> getTrainingDataPos() throws IOException
    {
    	String posTrainPath = "C:\\Users\\Anand\\git\\Project\\Sentiment Test\\aclImdb\\train\\pos";
    	
    	File folder = new File(posTrainPath);
		
		ArrayList<Review> posData = new ArrayList<Review>();
	    
		
		ReadTweets.listFilesForFolder(folder, posData);
		   	
		Properties props = new Properties(); 
        props.put("annotators", "tokenize, ssplit, pos, lemma"); 
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props, false);
        
        
        ArrayList<ArrayList<String>> collectionLemma = new ArrayList<ArrayList<String>>();
        
        int posSize = posData.size();
  
         for(int i=0;i<posSize;i++)
         {
         	Annotation document = pipeline.process(posData.get(i).review);  
         	ArrayList<String> currentReview = new ArrayList<String>();

             for(CoreMap sentence: document.get(SentencesAnnotation.class))
             {    
                 for(CoreLabel token: sentence.get(TokensAnnotation.class))
                 {       
                         
                     String lemma = token.get(LemmaAnnotation.class); 
                     lemma = lemma.replaceAll("\\s+","");
                     String[] tempLemma = lemma.split("\\.|[-,!?/]");
                     for(String word : tempLemma)
                     {
                     	currentReview.add(word);
                     }
                    
                 }
             }
             collectionLemma.add(currentReview);
             System.out.println("Pos Lemmatization completed for "+(i+1)+" reviews");
         }
         
        /* 
         System.out.println("Check the stop words:");
         for(int i = 0 ; i < collectionFinalPos.size() ; i++) {
           ArrayList<String> printList = collectionFinalPos.get(i);
           
           //now iterate on the current list
           for (int j = 0; j < printList.size(); j++) {
               String s = printList.get(j);
               System.out.println(s);
               
           }
       }
         */
         
         return collectionLemma;
         
            }
    
    
    
    public static ArrayList<ArrayList<String>> getTrainingDataNeg() throws IOException
    {
    	
    	
    	String negTrainPath = "C:\\Users\\Anand\\git\\Project\\Sentiment Test\\aclImdb\\train\\neg";
    	File negFolder = new File(negTrainPath);
		
		
		ArrayList<Review> negData = new ArrayList<Review>();
	    

		ReadTweets.listFilesForFolder(negFolder, negData);
	
    	
        Properties props = new Properties(); 
        props.put("annotators", "tokenize, ssplit, pos, lemma"); 
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props, false);

              
        ArrayList<ArrayList<String>> collectionLemmaNeg = new ArrayList<ArrayList<String>>();
        
        int negSize = negData.size();
  
         for(int i=0;i<negSize;i++)
         {
        	 
         	Annotation document = pipeline.process(negData.get(i).review);  
         	ArrayList<String> currentList = new ArrayList<String>();

             for(CoreMap sentence: document.get(SentencesAnnotation.class))
             {    
                 for(CoreLabel token: sentence.get(TokensAnnotation.class))
                 {       
                     
                     String lemma = token.get(LemmaAnnotation.class); 
                     lemma = lemma.replaceAll("\\s+","");
                     String[] tempLemma = lemma.split("\\.|[-,!?/]");
                     for(String word : tempLemma)
                     {
                     	currentList.add(word);
                     }
                 }
             }
             collectionLemmaNeg.add(currentList);
             System.out.println("Neg Lemmatization completed for "+(i+1)+" reviews");
         }
       
         return collectionLemmaNeg;
            }
    
       
    
    public  static ArrayList<ArrayList<String>> getPosTagging(ArrayList<ArrayList<String>> collectionLemma) 
    {
    	
    	MaxentTagger tagger = new MaxentTagger(
                "taggers/english-left3words-distsim.tagger");
    	
    	ArrayList<ArrayList<String>> collectionPos = new ArrayList<ArrayList<String>>();
        
        
        //System.out.println("Check the stop words other than adverbs and adjectives:");
        for(int i = 0 ; i < collectionLemma.size() ; i++) {
        	
            ArrayList<String> LemmatizedReview = collectionLemma.get(i);
            ArrayList<String> currentReviewPOS = new ArrayList<String>();
            
            //now iterate on the current list
            for (int j = 0; j < LemmatizedReview.size(); j++)
            {
                String s = LemmatizedReview.get(j);
                String taggedWord = tagger.tagString(s);
                currentReviewPOS.add(taggedWord);    
            }
            collectionPos.add(currentReviewPOS);
            System.out.println("POS completed for "+(i+1)+" reviews");
        }
    	
          return collectionPos;
    }
    
    
    
    public  static ArrayList<ArrayList<String>> getFilteredList(ArrayList<ArrayList<String>> collectionPos,ArrayList<ArrayList<String>> collectionLemma) 
    		throws java.lang.StringIndexOutOfBoundsException
    {
    	
    	ArrayList<ArrayList<String>> collectionFinalPos = new ArrayList<ArrayList<String>>();
        
        
        for(int i = 0 ; i < collectionPos.size() ; i++) {
            System.out.println("Filtering started for review " +i);
       	 
       	 ArrayList<String> printList = collectionPos.get(i);
            ArrayList<String> currentListLemma = new ArrayList<String>();
            ArrayList<String> currentListFinal = new ArrayList<String>();
            
            currentListLemma=collectionLemma.get(i);
            
            for (int j = 0; j < printList.size(); j++) {
                String s = printList.get(j);
                String curstr;
                String no="no";
                //int pos = s.indexOf("_");
                
                if((curstr=currentListLemma.get(j)).equals(no))
                {
               	 curstr=currentListLemma.get(j);
             		currentListFinal.add(curstr);
             		
                }
                
                else if( (s.substring(s.indexOf('_')+1,s.length())!="NNP") ||(s.substring(s.indexOf('_')+1,s.length())=="NNS"))
                
                //else if((s.charAt(pos+1) == 'N' && (s.charAt(pos+2) == 'N') && (s.charAt(pos+3) != 'P'))|| ((s.charAt(pos+1) == 'N') &&(s.charAt(pos+3)=='S')) )
            	{
            		curstr=currentListLemma.get(j);
            		currentListFinal.add(curstr);
            		
            		
            	}
                else if( s.substring(s.indexOf('_')+1,s.length())=="JJ")
            	//else if((s.charAt(pos+1) == 'J' && (s.charAt(pos+2) == 'J')))
            	{
            		curstr=currentListLemma.get(j);
            		currentListFinal.add(curstr);
            		
            		
            	}
                else if( (s.substring(s.indexOf('_')+1,s.indexOf('_')+2)=="V"))
                 //else if((s.charAt(pos+1) == 'V' ))
            	{
               	curstr=currentListLemma.get(j);
              		currentListFinal.add(curstr);
              		
            	}
                
                else if( (s.substring(s.indexOf('_')+1,s.indexOf('_')+3)=="RB"))
                // else if((s.charAt(pos+1) == 'R' && (s.charAt(pos+2) == 'B')))
             	{
               	curstr=currentListLemma.get(j);
              		currentListFinal.add(curstr);
              		
             		
             	}           
            }
            collectionFinalPos.add(currentListFinal);
        }
        
        return collectionFinalPos;
    	
    }
    
    public  static ArrayList<ArrayList<String>> getTrainingDataP() throws IOException
    {
    	ArrayList<ArrayList<String>> collectionLemma = new ArrayList<ArrayList<String>>();
    	ArrayList<ArrayList<String>> collectionPos = new ArrayList<ArrayList<String>>();
    	ArrayList<ArrayList<String>> collectionFinalPos = new ArrayList<ArrayList<String>>();
    	
    	 collectionLemma=StanfordLemmatizer.getTrainingDataPos();
    	 collectionPos = StanfordLemmatizer.getPosTagging(collectionLemma);
    	 collectionFinalPos=StanfordLemmatizer.getFilteredList(collectionPos, collectionLemma);
    	 
    	 return collectionFinalPos;
    	  
    }
    
    
    public  static ArrayList<ArrayList<String>> getTrainingDataN() throws IOException
    {
    	ArrayList<ArrayList<String>> collectionLemma = new ArrayList<ArrayList<String>>();
    	ArrayList<ArrayList<String>> collectionPos = new ArrayList<ArrayList<String>>();
    	ArrayList<ArrayList<String>> collectionFinalPos = new ArrayList<ArrayList<String>>();
    	
    	 collectionLemma=StanfordLemmatizer.getTrainingDataNeg();
    	 collectionPos = StanfordLemmatizer.getPosTagging(collectionLemma);
    	 collectionFinalPos=StanfordLemmatizer.getFilteredList(collectionPos, collectionLemma);
    	 
    	 
    	 return collectionFinalPos;
    	  
    }
    
    
    
    
    
}