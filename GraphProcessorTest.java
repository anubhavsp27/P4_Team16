import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class GraphProcessorTest {
	
	GraphProcessor graphPrc = new GraphProcessor();
	
	

	@Test
	public void populateGraphWithTwentyWords() {
		
		int testInt = graphPrc.populateGraph("twentyWords.txt");
		
		assertEquals(20, testInt);
		
	
	}
	
	@Test 
	public void populateGraphShortestPath() {
		
		int testInt = graphPrc.populateGraph("shortestPathTest.txt");
		assertEquals(3, testInt);
	}
	
	@Test
	public void getShortestPath() {
		
		graphPrc.populateGraph("shortestPathTest.txt");
		graphPrc.shortestPathPrecomputation();
		int shortestDistance = graphPrc.getShortestDistance("cat", "hath");
		assertEquals(2, shortestDistance);
		
	}

}
