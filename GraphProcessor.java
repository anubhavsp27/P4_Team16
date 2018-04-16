import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for all the vertices and edges.
 * @see #populateGraph(String)
 *  - loads a dictionary of words as vertices in the graph.
 *  - finds possible edges between all pairs of vertices and adds these edges in the graph.
 *  - returns number of vertices added as Integer.
 *  - every call to this method will add to the existing graph.
 *  - this method needs to be invoked first for other methods on shortest path computation to work.
 * @see #shortestPathPrecomputation()
 *  - applies a shortest path algorithm to precompute data structures (that store shortest path data)
 *  - the shortest path data structures are used later to 
 *    to quickly find the shortest path and distance between two vertices.
 *  - this method is called after any call to populateGraph.
 *  - It is not called again unless new graph information is added via populateGraph().
 * @see #getShortestPath(String, String)
 *  - returns a list of vertices that constitute the shortest path between two given vertices, 
 *    computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *  - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 * @see #getShortestDistance(String, String)
 *  - returns distance (number of edges) as an Integer for the shortest path between two given vertices
 *  - this is computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *  - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 *  
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class GraphProcessor {

    /**
     * Graph which stores the dictionary words and their associated connections
     */
    private GraphADT<String> graph;
    
    /**
     * HashMap which stores the shortest path from all combinations of words
     */
    private Map<String, Map<String, ArrayList<String>>> shortestPathHash;
    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
    }
        
    /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the dictionary as vertices
     * and finding and adding the corresponding connections (edges) between 
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph.
     * Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent {@link WordProcessor#isAdjacent(String, String)}
     * If a pair is adjacent, adds an undirected and unweighted edge between the pair of vertices in the graph.
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added
     */
    public Integer populateGraph(String filepath) {
    	
        Stream<String> wordStream = null;
        
        //Get stream
        try {
            wordStream = getWordStream(filepath);
        } catch (IOException e) {
            return -1;
        }
        
        //Add each string in the stream into the graph
        wordStream.forEach(graph::addVertex);
        
        //populate edges 
        Iterable<String> vertecies = graph.getAllVertices();
        int size = 0;
        for (String vertex1 : vertecies) {
            for (String vertex2 : vertecies) {
            	    if(WordProcessor.isAdjacent(vertex1, vertex2));
                    graph.addEdge(vertex1, vertex2);
            }
            size++;
        }
        
        return size;
    
    }
    
    /**
     * This method retrieves a stream of the words in the file
     * 
     * @param filepath path to the file
     * @return a word stream containing non null, non empty values
     * @throws IOException
     */
    private Stream<String> getWordStream(String filepath) throws IOException {
        
        Stream<String> wordStream = WordProcessor.getWordStream(filepath);
        
        return wordStream;
    }
    

    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  shortest path between cat and wheat is the following list of words:
     *     [cat, hat, heat, wheat]
     * 
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {
    	//access nested HashMap
        ArrayList<String> shortestPath = new ArrayList<String>();
        shortestPath = shortestPathHash.get(word1).get(word2);
        return shortestPath;
    }
    
    /**
     * Gets the distance of the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  distance of the shortest path between cat and wheat, [cat, hat, heat, wheat]
     *   = 3 (the number of edges in the shortest path)
     * 
     * @param word1 first word
     * @param word2 second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {
    	//access nested HashMap
    	int shortestDistance = 0;
    	ArrayList<String> shortestPath = new ArrayList<String>();
        shortestPath = shortestPathHash.get(word1).get(word2);
        shortestDistance = shortestPath.size() - 1;
        return shortestDistance;
    }
    
    /**
     * Innner class for encapsulating data for each node in the graph, and 
     * making it comparable to use within a priority queue.
     */
    class DijkstraNode implements Comparable<DijkstraNode> {
    	
    	private DijkstraNode predecessor;
    	private String associatedString;
    	private int cost;
    	private boolean visited;
    	
    	/**
    	 * Constructs a DijkstraNode object
    	 * @param predecessor
    	 * @param string
    	 * @param cost
    	 * @param visited
    	 */
    	DijkstraNode(DijkstraNode predecessor, String string, int cost, boolean visited) {
    		
    		this.visited = false;
    		this.predecessor = predecessor;
    		this.associatedString = string;
    		this.cost = cost;
    	}
    	
    	//link to parent node, used for constructing shortest path
		DijkstraNode getPred() {
			return predecessor;
		}

		@Override
		public int compareTo(DijkstraNode other) {
			return Integer.compare(other.cost, cost);
		}
    }
    /**
     * Computes shortest paths and distances between all possible pairs of vertices.
     * This method is called after every set of updates in the graph to recompute the path information.
     * Any shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() { 
    	//Nested HashMap to calculate shortest distances between all combinations
    	shortestPathHash = new HashMap<String, Map<String, ArrayList<String>>>();
    	
    	//Graph Processor only needs to iterate through String vertices
    	for(String i : graph.getAllVertices()) { 
    		Map<String, ArrayList<String>> innerHash = new HashMap<String, ArrayList<String>>();
    		DijkstraNode newNode = new DijkstraNode(null, i, 0, false);
    		
			PriorityQueue<DijkstraNode> priorityQ = new PriorityQueue<DijkstraNode>();
			priorityQ.add(newNode);
			
			//keep track of the strings in the queue
			ArrayList<String> expandedStringNodes = new ArrayList<String>(); 
			while (!priorityQ.isEmpty()) {
				//pop lowest cost node off of the stack
				DijkstraNode node = priorityQ.poll();
				node.visited = true;
				
				for(String s : graph.getNeighbors(i)) {
					//check if node is already expanded, by reference
					if (!expandedStringNodes.contains(s)) {
						expandedStringNodes.add(s);
						DijkstraNode successor = new DijkstraNode(node, s, node.cost + 1, false);
						ArrayList<String> shortestPathList = new ArrayList<String>();
						//add string to visted strings and associated dijkstra node to PQ
						shortestPathList.add(i);
						priorityQ.add(successor);
						
						//computing shortest path for these two nodes with strings i & s
						while (successor != null) { 
							shortestPathList.add(successor.associatedString);
							successor = successor.predecessor;
						}
						
						innerHash.put(s, shortestPathList);
						
					}
				}
			}
			shortestPathHash.put(i, innerHash); // store shortest path in hashmap
    	}
    }
}
