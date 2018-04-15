import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class contains some utility helper methods
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class WordProcessor {
	
	/**
	 * Gets a Stream of words from the filepath.
	 * 
	 * The Stream should only contain trimmed, non-empty and UPPERCASE words.
	 * 
	 * @see <a href="http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8 stream blog</a>
	 * 
	 * @param filepath file path to the dictionary file
	 * @return Stream<String> stream of words read from the filepath
	 * @throws IOException exception resulting from accessing the filepath
	 */
	public static Stream<String> getWordStream(String filepath) throws IOException {
		/**
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html">java.nio.file.Files</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Paths.html">java.nio.file.Paths</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html">java.nio.file.Path</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html">java.util.stream.Stream</a>
		 * 
		 * class Files has a method lines() which accepts an interface Path object and 
		 * produces a Stream<String> object via which one can read all the lines from a file as a Stream.
		 * 
		 * class Paths has a method get() which accepts one or more strings (filepath),  
		 * joins them if required and produces a interface Path object
		 * 
		 * Combining these two methods:
		 *     Files.lines(Paths.get(<string filepath>))
		 *     produces
		 *         a Stream of lines read from the filepath
		 * 
		 * Once this Stream of lines is available, you can use the powerful operations available for Stream objects to combine 
		 * multiple pre-processing operations of each line in a single statement.
		 * 
		 * Few of these features:
		 * 		1. map( )      [changes a line to the result of the applied function. Mathematically, line = operation(line)]
		 * 			-  trim all the lines
		 * 			-  convert all the lines to UpperCase
		 * 			-  example takes each of the lines one by one and apply the function toString on them as line.toString() 
		 * 			   and returns the Stream:
		 * 			        streamOfLines = streamOfLines.map(String::toString) 
		 * 
		 * 		2. filter( )   [keeps only lines which satisfy the provided condition]  
		 *      	-  can be used to only keep non-empty lines and drop empty lines
		 *      	-  example below removes all the lines from the Stream which do not equal the string "apple" 
		 *                 and returns the Stream:
		 *      			streamOfLines = streamOfLines.filter(x -> x != "apple");
		 *      			 
		 * 		3. collect( )  [collects all the lines into a java.util.List object]
		 * 			-  can be used in the function which will invoke this method to convert Stream<String> of lines to List<String> of lines
		 * 			-  example below collects all the elements of the Stream into a List and returns the List:
		 * 				List<String> listOfLines = streamOfLines.collect(Collectors::toList); 
		 * 
		 * Note: since map and filter return the updated Stream objects, they can chained together as:
		 * 		streamOfLines.map(...).filter(a -> ...).map(...) and so on
		 */
		Stream<String> streamofLines = Files.lines(Paths.get(filepath));
		streamofLines.map(String::trim).filter(x -> ! x.equals(""));
		streamofLines.map(String::toUpperCase);
		return streamofLines;
	}
	
	/**
	 * Adjacency between word1 and word2 is defined by:
	 * if the difference between word1 and word2 is of
	 * 	1 char replacement
	 *  1 char addition
	 *  1 char deletion
	 * then 
	 *  word1 and word2 are adjacent
	 * else
	 *  word1 and word2 are not adjacent
	 *  
	 * Note: if word1 is equal to word2, they are not adjacent
	 * 
	 * @param word1 first word
	 * @param word2 second word
	 * @return true if word1 and word2 are adjacent else false
	 */
	public static boolean isAdjacent(String word1, String word2) {
		// wordLengthDiff stores the absolute difference between
		// in the length of the two words
		
		int wordLengthDiff = Math.abs(word2.length()-word1.length());
		
		// For the two words to be adjacent, the should either have the
		// same number of characters or differ by at most one character:
		
		if (wordLengthDiff > 1)
		    return false;
		
		else if (wordLengthDiff == 0){
			// Since in this case, both words have the same number of character 
			// (i.e. wordLengthDiff=0),for them to be adjacent, there must 
			// be a difference of only one character
			
		    int count = 0;  // counts the number of characters that differ
		                   // between the two words.
		    for (int i = 0; i < word1.length(); i++ ) {
		    	    if(word1.charAt(i) != word2.charAt(i)) {
		    	    	    count++;
		    	    	    
		    	    	    // If the number of characters at the same position
		    	    	    // of the two words that do not match (as tracked by
		    	    	    // count) exceeds 1, the words are not adjacent:
		    	    	    
		    	    	    if(count > 1)
		    	    	    	    return false;
		          }   
		    } 
		    return true;    
		}
		
		else if (wordLengthDiff == 1) {
			// The words will be adjacent in this case only if there is
			// an addition/removal of one character while the relative 
			// position of all other characters remain the same.
			
			int count = 0;  // counts the number of characters at the same
			              // position that differ between the two words
			
			String longerWord = word1;  // Word with greater length 
			                            // initialized to word1
			String shorterWord = word2;
			
			// Assigning longer and shorter words:
			if (word2.length() > word1.length()) {
				longerWord=word2;
				shorterWord=word1;
			}
			int j = 0;  // used to access characters of longerWord
			            // in the following for loop
			for (int i = 0; i < shorterWord.length(); i++) {
				if (shorterWord.charAt(i) != longerWord.charAt(j)) {
					// If the characters at a position do not match:
					// First, count is incremented
					count++;
					// For words to be adjacent, no more than one character
					// at the same position should differ. Thus:
					if (count > 1)
						return false;
					// Next, 'i' is decremented so that the current character
					// of shorterWord is compared to the next character of 
					// longerWord during the next iteration.
					i--;
				}
				j++;
			}
			// The above for-loop iterates until the last character
			// of shorterWord is reached. Thus the last character of
			// longerWord may not be compared with any characters of
			// shorterWord in some cases. In these cases,the two words
			// must be adjacent and the additional character added to
			// the shorter word must be at the end since the difference 
			// the length of the two words is 1.
			return true;
		}
		
		return false;	
	}
	
}
