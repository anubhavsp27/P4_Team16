import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import Graph.Graphnode;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
	
	class Vertex<E> {

		private ArrayList<Edge> neighborhood;
		private ArrayList<E> neighbors;

		private E label;
		
		public Vertex(E label) {
			
			this.label = label;
			this.neighborhood = new ArrayList<Edge>();
			this.neighbors = new ArrayList<E>();
		}
		
		public void addNeighbor(E newVertex) {
			
			if (this.neighbors.contains(newVertex)) {
				return;
			}
			
			neighbors.add(newVertex);
		}
		
		public void addNeighbor(Edge edge) {
			
			if (this.neighborhood.contains(edge)) {
				return;
			}
			
			neighborhood.add(edge);
		}
		
		public boolean hasNeighbor(E newVertex) {
			
			
			
			return this.neighbors.contains(newVertex);
			
		}
		
		public boolean hasNeighbor(Edge edge) {
			
			return this.neighborhood.contains(edge);
			
		}
		
		
		public Edge getNeighbor(int index) {
			
			return this.neighborhood.get(index);
			
		}
		
		public void removeNeighbor(E vertex) {
			
			this.neighbors.remove(vertex);
			
		}
		
		public Edge removeNeighbor(int index) {
			
			return this.neighborhood.remove(index);
			
		}
		
		public void removeNeighbor(Edge edge) {
			
			this.neighborhood.remove(edge);
			
		}
		
		public int getNeighborCount() {
			
			return this.neighbors.size();
			
		}
		
		public E getLabel() {
			
			return this.label;
			
		}
		
		public String toString() {
			
			return "Vertex " + label;
			
		}
		
		public int hashCode() {
			
			return this.label.hashCode();
			
		}
		
		public boolean equals(Object object) {
			
			if (! (object instanceof Vertex )) {
				
				return false;
			}
			
			else {
				
				@SuppressWarnings("unchecked")
				Vertex<E> otherVertex = (Vertex<E>) object;
				return this.label.equals(otherVertex.label);
			}
			
		}
		
		public ArrayList<Edge> getNeighborhood() {
			
			return new ArrayList<Edge>(this.neighborhood);
			
		}
		
		public ArrayList<E> getNeighbors() {
			
			return new ArrayList<E>(this.neighbors);
			
		}

	}

	class Edge {
		
		Vertex<E> source;
		Vertex<E> target;
		
		public Edge(Vertex<E> source, Vertex<E> target) {
			
			this.source = source;
			this.target = target;
			
		}
		
		public Vertex<E> getVertex(Vertex<E> current) {

			if(!(current.equals(source) || current.equals(target))){

				return null;

			}


			return (current.equals(source)) ? target : source;

		}
		
		public Vertex<E> getSource() {
			
			return source;
			
		}
		
		public Vertex<E> getTarget() {
			
			return target;
			
		}

		public int hashCode() {
			
			return (source.getLabel().hashCode() + target.getLabel().hashCode());
			
		}
		
		 @SuppressWarnings("unchecked")
		public boolean equals(Object other){
			 

			 Edge edge = (Edge)other;

			 return edge.source.equals(this.source) && edge.target.equals(this.target);

		 }  


	}
	
	
    /**
     * Instance variables and constructors
     */
	
	private HashMap<Integer, Vertex<E>> vertices;
    private HashMap<Integer, Edge> edges;
	
	public Graph() {
		this.vertices = new HashMap<Integer, Vertex<E>>();
		this.edges = new HashMap<Integer, Edge>();
		
	}
	
	public Graph(ArrayList<Vertex<E>> vertices) {
		this.vertices = new HashMap<Integer, Vertex<E>>();
		this.edges = new HashMap<Integer, Edge>();
		
		for (Vertex<E> v : vertices) {
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
    	
    	Vertex<E> current = vertices.get(vertex.hashCode());
    	
    	if (current != null) {
    		return null;
    	}
    	
    	Vertex<E> newVertex = new Vertex<E>(vertex);
    	
    	vertices.put(newVertex.hashCode(), newVertex);

    	
		return vertex;
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
    	
    	if (vertex == null || !vertices.containsKey(vertex.hashCode())) {
    		return null;
    	}
    	
    	Vertex<E> v = vertices.get(vertex.hashCode());
    	
    	for (E neighbor : v.getNeighbors()) {
    		
    		if (!this.removeEdge(vertex, neighbor)) {
    			return null;
    		}
    	}
    	
    	vertices.remove(vertex.hashCode());
    	
        return vertex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
    	
    	if (!vertices.containsKey(vertex1.hashCode()) || !vertices.containsKey(vertex2.hashCode())
    			|| vertex1.equals(vertex2)) {
    		
    		return false;
    	}
    	
    	Vertex<E> v1 = vertices.get(vertex1.hashCode());
    	Vertex<E> v2 = vertices.get(vertex2.hashCode());
    	Edge newEdge = new Edge(v1,v2);
    	
    	
    	if (edges.containsKey(newEdge.hashCode())) {
    		
    		return false;
    		
    	}
    	
    	
    	else if(v1.hasNeighbor(newEdge) || v2.hasNeighbor(newEdge)){
    		
    		return false;
    		
    	}

    	edges.put(newEdge.hashCode(), newEdge);
    	
    	//This is original code but may be deleted to use code below instead, currently this is not 
    	//being used
    	
    	//v1.addNeighbor(newEdge);
    	//v2.addNeighbor(newEdge);
    	
    	/////////////////////////////////////////////////////////////////
    	
    	//Trying out new code with E arguments for Vertex class methods
    	
    	v1.addNeighbor(vertex2);
    	v2.addNeighbor(vertex1);
    	
    	/////////////////////////////////////////////////////////////////////
    	
        return true;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
    	
    	if (!vertices.containsKey(vertex1.hashCode()) || !vertices.containsKey(vertex2.hashCode())
    			|| vertex1.equals(vertex2)) {
    		
    		return false;
    	}
    	
    	Vertex<E> v1 = vertices.get(vertex1.hashCode());
    	Vertex<E> v2 = vertices.get(vertex2.hashCode());
    	Edge newEdge = new Edge(v1,v2);
    	
    	//TODO: Need to write if statement to catch case where v1 and v2 exist but they are not adjacent, i.e. 
    	// they do not share an edge.
    	
    	//This code is not being used at the moment

    	//v1.removeNeighbor(newEdge);
    	//v2.removeNeighbor(newEdge);
    	
    	////////////////////////////////////////////////
    	
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
    	
    	if (!vertices.containsKey(vertex1.hashCode()) || !vertices.containsKey(vertex2.hashCode())
    			|| vertex1.equals(vertex2)) {
    		
    		return false;
    	}
    	
    	//System.out.println(vertices.containsKey(vertex1.hashCode()));
    	
    	Vertex<E> v = vertices.get(vertex1.hashCode());
    	
    	if (v.hasNeighbor(vertex2)) {
    		return true;
    	}
    	
    	//Edge e = edges.get(vertex1.hashCode() + vertex2.hashCode());
    	//Vertex<E> v = vertices.get(vertex1.hashCode());
    	//Vertex<E> v2 = vertices.get(vertex2.hashCode());
    
    	
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
    	
    	Vertex<E> v = vertices.get(vertex.hashCode());
    	
        return v.getNeighbors();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
    	
    	ArrayList<Vertex<E>> vertList = new ArrayList<Vertex<E>>();
    	
    	for (Map.Entry<Integer, Vertex<E>> map : vertices.entrySet()) {
    		
    		vertList.add(map.getValue());
    	}
    	
    	ArrayList<E> valueList = new ArrayList<E>();
    	
    	for (Vertex<E> v : vertList) {
    		valueList.add(v.getLabel());
    	}
    	
        return valueList;
    }
    
 

}
