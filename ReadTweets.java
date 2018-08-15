
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadTweets {
	
	
	
	
	public static void listFilesForFolder(final File folder,ArrayList<Review> Data) throws IOException  {
		for (final File fileEntry : folder.listFiles()) {

	        if (fileEntry.isFile()) {
	          String fileName = fileEntry.getName();
	          
        	  BufferedReader bufferReader = new BufferedReader(new FileReader(folder.getAbsolutePath()+ "\\" +fileName));
        	    try {
        	        StringBuilder stringBuilder = new StringBuilder();
        	        String line = bufferReader.readLine();

        	        while (line != null) {
        	        	stringBuilder.append(line);
        	        	stringBuilder.append("\n");
        	            line = bufferReader.readLine();
        	        }
	        	    Review review = new Review(Integer.parseInt(fileName.substring(0,fileName.indexOf('_'))),stringBuilder.toString().toLowerCase(),Integer.parseInt(fileName.substring(fileName.length()-5,fileName.length()-4)));
	        	    
	        	    printReview(review.review);
	        	    
        	        Data.add(review);
        	    } finally {
        	    	bufferReader.close();
        	    }
        	    

		  }
		}
	}

	private static void printReview(String review) {
		// TODO Auto-generated method stub
		System.out.println(review);
	}
}
