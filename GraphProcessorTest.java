	import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.BeforeClass;
	import org.junit.Test;
	import org.junit.*;
	
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
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
//Idea for a Vertex/Edge class combination was taken from: 
//   http://www.dreamincode.net/forums/
//		topic/377473-graph-data-structure-tutorial/ 
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

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
		public void test04_shortestPathPrecomputationError() {
			graphPrc.populateGraph("twentyWords.txt");
			try {
				graphPrc.shortestPathPrecomputation();
			}
			catch(Exception e) {
				fail();
			}
		}

	}


