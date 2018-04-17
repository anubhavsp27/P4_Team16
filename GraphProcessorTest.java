import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.*;
	
public class GraphProcessorTest {
	


		
		GraphProcessor graphPrc;
		
		

		@Before
		public void setUp() throws Exception {
			this.graphPrc= new GraphProcessor();	
		}
		
		@After
		public void tearDown() throws Exception{
			this.graphPrc=null;
		}
		
		@Test
		public void test01_populateGraphWithTwentyWords() {
			
			int testInt = graphPrc.populateGraph("twentyWords.txt");
			
			assertEquals(20, testInt);
			
		
		}
		
		@Test 
		public void test02_getShortestPathTest() {
			
			graphPrc.populateGraph("shortestPath.txt");
			List<String> actualList=graphPrc.getShortestPath("cat", "wheat");
			List<String> expectedList= new ArrayList<String>();
			expectedList.add("cat");
			expectedList.add("hat");
			expectedList.add("heat");
			expectedList.add("wheat");
			assertEquals(actualList,expectedList);
		}
		
		@Test
		public void test03_getShortestDistanceTest() {
			
			graphPrc.populateGraph("shortestPathTest.txt");
			graphPrc.shortestPathPrecomputation();
			int shortestDistance = graphPrc.getShortestDistance("cat", "wheat");
			assertEquals(4, shortestDistance);
			
		}
		
		@Test
		public void test04_populateGraphWith441Words() {
            int testInt = graphPrc.populateGraph("words_list.txt");
			
			assertEquals(441, testInt);
			
		}
		
		@Test
		public void test05_shortestPathPrecomputationError() {
			graphPrc.populateGraph("twentyWords.txt");
			try {
				graphPrc.shortestPathPrecomputation();
			}
			catch(Exception e) {
				fail();
			}
		}
		
		@Test
		public void test06_getWordStreamTest() {
			Stream a = null;
			try {
				a = WordProcessor.getWordStream("textWithBlanks.txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
			}
			
			ArrayList<String> actualList = (ArrayList<String>) a.collect(Collectors.toList());
			
			ArrayList<String> expectedList = new ArrayList<String>();
			
			expectedList.add("cat");
			expectedList.add("rat");
			expectedList.add("hat");
			expectedList.add("neat");
			expectedList.add("wheat");
			expectedList.add("kit");
			
			assertEquals(expectedList, actualList);
		}
		
		
		@Test
		public void test07_isAdjacentTest() {
			
			boolean actual = WordProcessor.isAdjacent("on", "hone");
			
			assertEquals(false, actual);
			
			
		}
		
        @Test
        public void test08_pathFromNodeToItselfLength() {
    
                graphPrc.populateGraph("shortestPathTest.txt");
                graphPrc.shortestPathPrecomputation();
    
                int expectedPathLength = 0;
                int actualPathLength = graphPrc.getShortestDistance("cat", "cat");
    
                assertEquals(expectedPathLength, actualPathLength);
    
        }

        @Test
        public void test09_selectsShortestDistance() {

    
                graphPrc.populateGraph("similarWords.txt");
                graphPrc.shortestPathPrecomputation();
    
                int expectedPathLength = 7;
                int actualPathLength = graphPrc.getShortestDistance("on", "hat");
    
                assertEquals(expectedPathLength, actualPathLength);
        }



	}
