import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Title:           Graph.java
//Description:     This program implements and undirected and unweighted 
//				   graph via a Vertex/Edge class combination.
//		
//Files:           Graph.java, WordProcessor.java, GraphProcessor.java
//	
//Course:          CS 400, Spring 2017
//
//Authors:          Chance Sanford, Mitchell Saulsberry, Anubhav Sanjeeva Prasad.
//				   Savannah Olson
//
//Email:           csanford4@wisc.edu, saulsberry@wisc.edu, sanjeevapras@wisc.edu
//				   seolson23@wisc.edu
//
//Lecturer's Name: Debra Deppler
//
///////////////////////////////// KNOWN BUGS //////////////////////////////////
//
// We are having a bug in Graph Processor file where the shortestPathPrecomputation
//    method will only generate paths of the form HAT -> CAT -> HAT or
//    ON -> HAT -> ON
// 
//    Currently we are unsure what is causing this bug, other than the fact it
//    is occurring in the shortestPathPrecomputation method
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
//Idea for a Vertex/Edge class combination was taken from: 
//   http://www.dreamincode.net/forums/
//		topic/377473-graph-data-structure-tutorial/ 
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


/**
 * Undirected and unweighted graph implementation using a generic VertexNode class which 
 * stores the data associated to each vertex of the graph, as well as the data of each 
 * adjacent vertex; and an EdgeNode class which stores a pair of VertexNode objects.
 * 
 * @param <E> type of a vertex
 * 
 * @author Chance Sanford (csanford4@wisc.edu), sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
	/**
	 * Class which stores the data associated with each vertex as well as
	 * an ArrayList of each neighbor's data.
	 * 
	 * @author Chance Sanford (csanford4@wisc.edu)
	 *
	 * @param <E> type of data associated to Vertex
	 */
	class VertexNode<E> {
		
		//ArrayList to store data of all adjacent vertices
		private ArrayList<E> neighbors;
		
		//Class field for the data associated to this VertexNode object
		final private E label;
		
		/**
		 * Vertex constructor
		 * @param label The data associated to this VertexNode object
		 */
		public VertexNode(E label) {
			
			this.label = label;
			this.neighbors = new ArrayList<E>();
		}
		
		/**
		 * Adds an element to the neighbor ArrayList
		 * @param newVertex data to be added
		 */
		public void addNeighbor(E newVertex) {
			
			if (this.neighbors.contains(newVertex)) {
				return;
			}
			
			neighbors.add(newVertex);
		}

		/**
		 * Determines whether neighbors ArrayList contains 
		 * the data newVertex
		 * @param newVertex Data to check if in neighbors 
		 * @return True if newVertex is in neighbors, else
		 * return false
		 */
		public boolean hasNeighbor(E newVertex) {

			return this.neighbors.contains(newVertex);
			
		}
		/**
		 * Removes an element from neighbors ArrayList
		 * @param vertex Data to be removed from neighbors
		 */
		public void removeNeighbor(E vertex) {
			
			this.neighbors.remove(vertex);
			
		}
		
		/**
		 * Returns the number of elements in neighbors
		 * ArrayList
		 * @return The size of neighbors
		 */
		public int getNeighborCount() {
			
			return this.neighbors.size();
			
		}
		
		/**
		 * Returns the data associated with this VertexNode object
		 * @return The field label 
		 */
		public E getLabel() {
			
			return this.label;
			
		}
		
		/**
		 * A toString method for VertexNode objects
		 * @return Returns "Vertex " + label
		 */
		public String toString() {
			
			return "Vertex " + label;
			
		}
		
		/**
		 * A hashCode method for VertexNode objects
		 * @return Returns label.hashCode()
		 */
		public int hashCode() {
			
			return this.label.hashCode();
			
		}
		
		/**
		 * An equals method for VertexNode objects.  Checks
		 * for equality between the data of two VertexNodes
		 * @param object The object which will be checked for
		 * equality against this Vertex object
		 * @return True if object is an instance of VertexNode and 
		 * their label fields are equal
		 */
		public boolean equals(Object object) {
			
			if (! (object instanceof VertexNode )) {
				
				return false;
			}
			
			else {
				
				@SuppressWarnings("unchecked")
				VertexNode<E> otherVertex = (VertexNode<E>) object;
				return this.label.equals(otherVertex.label);
			}
			
		}
		/**
		 * A method which returns the ArrayList neighbors
		 * @return neighbors
		 */
		public ArrayList<E> getNeighbors() {
			
			return new ArrayList<E>(this.neighbors);
			
		}

	}
	/**
	 * A class which stores two VertexNode objects.  The Graph data structure
	 * could have been implemented just with the Vertex class, but the addition
	 * of edge class provides flexibility for certain tasks, such as Dijkstra's
	 * algorithm.
	 * @author Chance Sanford (csanford4@wisc.edu)
	 *
	 */
	class EdgeNode {
		
		//Class field to store a VertexNode object
		final private VertexNode<E> source;
		
		//Class field to store a VertexNode object 
		final private VertexNode<E> target;
		
		/**
		 * Constructor for EdgeNode class
		 * @param source First VertexNode object
		 * @param target Second VertexNode object
		 */
		public EdgeNode(VertexNode<E> source, VertexNode<E> target) {
			
			this.source = source;
			this.target = target;
			
		}
		
		/**
		 * Method which returns the other vertexNode associated with this Edge object
		 * @param current The first VertexNode associated with this Edge object
		 * @return The target VertexNode if current equals source, and returns the
		 * source VertexNode if current equals target
		 */
		public VertexNode<E> getVertex(VertexNode<E> current) {

			if(!(current.equals(source) || current.equals(target))){

				return null;

			}


			return (current.equals(source)) ? target : source;

		}
		
		/**
		 * Retruns source field
		 * @return source
		 */
		public VertexNode<E> getSource() {
			
			return source;
			
		}
		
		/**
		 * Returns target field
		 * @return target
		 */
		public VertexNode<E> getTarget() {
			
			return target;
			
		}
		
		/**
		 * A hashCode method for EdgeNode objects.  Adds the hash codes of
		 * source and target field.
		 * @return The sum of source's and target's hash code
		 */
		public int hashCode() {
			
			return (source.hashCode() + target.hashCode());
			
		}
		
		/**
		 * An equals method for EdgeNode objects.  Checks equality between the source and 
		 * target fields of two EdgeNode objects.
		 * @param other The object to be checked for equality against this EdgeNode object
		 * @return Returns true if source and target fields of both edge objects are equal
		 */
		 @SuppressWarnings("unchecked")
		public boolean equals(Object other){
			 

			 EdgeNode edge = (EdgeNode)other;

			 return edge.source.equals(this.source) && edge.target.equals(this.target);

		 }  


	}
	
	
    
	//HashMap which stores the vertices of the graph
	private HashMap<Integer, VertexNode<E>> vertices;
	
	//HashMap which stores the edges of the graph
    private HashMap<Integer, EdgeNode> edges;
	
    /**
     * Constructor for Graph objects
     */
	public Graph() {
		this.vertices = new HashMap<Integer, VertexNode<E>>();
		this.edges = new HashMap<Integer, EdgeNode>();
		
	}
	
	/**
	 * Overloaded constructor for Graph objects
	 * @param vertices An ArrayList of vertices which
	 * are added to the graph
	 */
	public Graph(ArrayList<VertexNode<E>> vertices) {
		this.vertices = new HashMap<Integer, VertexNode<E>>();
		this.edges = new HashMap<Integer, EdgeNode>();
		
		for (VertexNode<E> v : vertices) {
			this.vertices.put(v.hashCode(), v);
		}
		
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
    	
    	
    	if (vertex == null) {
    		return null;
    	}
    	
    	// Checks that the data vertex doesn't already exist in the graph
    	VertexNode<E> current = vertices.get(vertex.hashCode());
    	
    	
    	if (current != null) {
    		return null;
    	}
    	
    	//If vertex doesn't already exist, a new VertexNode is added to graph
    	VertexNode<E> newVertex = new VertexNode<E>(vertex);
    	
    	vertices.put(newVertex.hashCode(), newVertex);

    	
		return vertex;
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
    	//Makes sure vertex is not null and vertices contains the VertexNode object 
    	//which has vertex as it's data
    	if (vertex == null || !vertices.containsKey(vertex.hashCode())) {
    		return null;
    	}
    	
    	VertexNode<E> v = vertices.get(vertex.hashCode());
    	
    	//Removes all edges associated with vertex
    	for (E neighbor : v.getNeighbors()) {
    		
    		if (!this.removeEdge(vertex, neighbor)) {
    			return null;
    		}
    	}
    	//Removes VertexNode object associated with vertex label
    	vertices.remove(vertex.hashCode());
    	
        return vertex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
    	//Makes sure vertec1 and vertex2 are assocaited with existing VertexNodes
    	//and that they are not equal
    	if (!vertices.containsKey(vertex1.hashCode()) || !vertices.containsKey(vertex2.hashCode())
    			|| vertex1.equals(vertex2)) {
    		
    		return false;
    	}
    	
    	VertexNode<E> v1 = vertices.get(vertex1.hashCode());
    	VertexNode<E> v2 = vertices.get(vertex2.hashCode());
    	EdgeNode newEdge = new EdgeNode(v1,v2);
    	
    	//Checks that an edge containing v1 and v2 does not already exist
    	if (edges.containsKey(newEdge.hashCode())) {
    		
    		return false;
    		
    	}
    	//Checks that v1 and v2 are not already neighbors
    	else if(v1.hasNeighbor(vertex1) || v2.hasNeighbor(vertex2)){
    		
    		return false;
    		
    	}

    	edges.put(newEdge.hashCode(), newEdge);
    	
    	
    	v1.addNeighbor(vertex2);
    	v2.addNeighbor(vertex1);
    	
    	
        return true;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
    	//Makes sure vertec1 and vertex2 are assocaited with existing VertexNodes
    	//and that they are not equal
    	if (!vertices.containsKey(vertex1.hashCode()) || !vertices.containsKey(vertex2.hashCode())
    			|| vertex1.equals(vertex2)) {
    		
    		return false;
    	}
    	
    	VertexNode<E> v1 = vertices.get(vertex1.hashCode());
    	VertexNode<E> v2 = vertices.get(vertex2.hashCode());
    	
    	//Creates new EdgeNode object to enable calling it's hashcode to remove
    	//it from edges HashMap
    	EdgeNode newEdge = new EdgeNode(v1,v2);
    	
    	v1.removeNeighbor(vertex2);
    	v2.removeNeighbor(vertex1);

    	edges.remove(newEdge.hashCode());
    	
    	return true;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
    	//Makes sure vertec1 and vertex2 are assocaited with existing VertexNodes
    	//and that they are not equal
    	if (!vertices.containsKey(vertex1.hashCode()) || !vertices.containsKey(vertex2.hashCode())
    			|| vertex1.equals(vertex2)) {
    		
    		return false;
    	}
    	
    
    	
    	VertexNode<E> v = vertices.get(vertex1.hashCode());
    	
    	if (v.hasNeighbor(vertex2)) {
    		return true;
    	}
    
    	
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
    	
    	if (!vertices.containsKey(vertex.hashCode())) {
    		return null;
    	}
    	
    	VertexNode<E> v = vertices.get(vertex.hashCode());
    	//Returns an ArrayList of neighbor vertices
        return v.getNeighbors();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
    	
    	//Creates a temporary ArrayList of VertexNodes
    	ArrayList<VertexNode<E>> vertList = new ArrayList<VertexNode<E>>();
    	
    	//For each loop which iterates through the entrySet of vertices 
    	//and adds the value of the key-value pair map to vertList
    	for (Map.Entry<Integer, VertexNode<E>> map : vertices.entrySet()) {
    		
    		vertList.add(map.getValue());
    	}
    	
    	ArrayList<E> valueList = new ArrayList<E>();
    	//For each loop which iterates through vertList and adds each VertexNode's
    	//label to the ArrayList valueList
    	for (VertexNode<E> v : vertList) {
    		valueList.add(v.getLabel());
    	}
    	
        return valueList;
    }
    
 

}
