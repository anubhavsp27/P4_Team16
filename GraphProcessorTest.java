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
	public void getShortestPath() {
		
		graphPrc.populateGraph("shortestPath.txt");
		
	}

}
