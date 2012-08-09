/* Kruskal.java */

import queue.*;
import graph.*;
import set.*;
import hash.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 * 
 */

public class Kruskal {

   /**
    * minSpanTree() returns a WUGraph that represents the minimum spanning tree
    * of the WUGraph g.  The original WUGraph g is NOT changed.
    * 
    * @param g is a WUGraph object.
    * @return minTree, a WUGraph object representing the minimum spanning tree
    * of the passed in WUGraph 'g'. 
    * 
    * (Note): Objects of the KruskalEdge class will represent edges for this 
    * portion of the project.
    * 
    * ____________________
    * :::MAIN VARIABLES:::
    * 
    * 'minTree': Newly created WUGraph object. Starts off as an empty graph. 
    * Will represent minimum spanning tree and returned by method when done.
    * 
    * 'minimumSet': DisjointSets object to keep track of which vertices are 
    * already connected and which ones are not. Each element in DisjointSets's 
    * 'array' field represents a vertex of the graph.
    * 
    * 'allEdges': Is a LinkedQueue object from hw8. It will be used to store all 
    * of the graph's edges (two vertices and a weight) in the form of 
    * KruskalEdge objects.
    * 
    * 'vertices': HashTableChained object used to quickly find out a vertex's 
    * index position in the disjoint set 'minimumSet'. The Hash Table will 
    * consist of Entry objects. Each Entry object's key will be the vertex 
    * object. The value of each Entry is the index position (int) for this 
    * vertex in the disjoint set.
    * 
    * 'gVertices': An array that simply holds all the vertices (actual 
    * application representations) of the graph.
    * 
    * ________________
    * ::: PROCEDURE:::
    * 
    * 1) Traverse through 'gVertices' and add each vertex of graph 'g' into the 
    *    new graph 'minTree'. (There are no edges yet in 'minTree').
    * 
    * 2) For each vertex in 'gVertices', insert an Entry object into 'vertices' 
    *    Hash Table. The key being the vertex object itself. The value is the 
    *    current integer of the loop counter (which will be the index position
    *    of the vertex in the disjoint set).
    *
    * 3) Using g.getNeighbors, we can get all the details about the vertex's 
    *    edges (which vertex each edge is connected to. The weight between the 
    *    two vertices.) For each neighboring vertex we find, along with the 
    *    weight, we create a KruskalEdge object with the following parameters - 
    *    -(our current vertex, 
    *    -the neighboring vertex, 
    *    -the weight of the edge formed between the two.)
    * 
    *    This newly constructed edge representation is enqueued into our 
    *    LinkedQueue 'allEdges'.
    * 
    * 4) After the above has been done for all vertices that exist in graph 'g',
    *    'allEdges' contains a KruskalEdge object for every edge that exists in 
    *    'g'. 
    * 
    *    Using quicksort, 'allEdges' is sorted from least to greatest by the 
    *    weight field, located in each KruskalEdge object. We essentially now 
    *    have a sorted list of edges from graph 'g'.
    * 
    * 5) Traversing through 'allEdges', for each KruskalEdge, determine whether 
    *    there is already a path connecting the two vertices of the edge 
    *    (Whether the two vertices are already in the same set in the
    *    disjoint set 'minimumSet'). 
    * 
    * 6) If there isn't a path between the two vertices, use the union operation 
    *    to put them in the same set. Call the addEdge method on 'minTree' with 
    *    the two vertices and weight of the edge, inserting this new
    *    "minimum" edge into the graph we will return.
    * 
    * 7) Return 'minTree', which will represent the minimum spanning tree of 
    *    'g'.
    */
   
   public static WUGraph minSpanTree(WUGraph g){

      KruskalEdge currEdge;
      int gVertexCount = g.vertexCount();
      int vertexAPosition;
      int vertexBPosition;
      int weightAB;

      WUGraph minTree = new WUGraph();
      LinkedQueue allEdges = new LinkedQueue();
      HashTableChained vertices = new HashTableChained();
      Object[] gVertices = g.getVertices();
      DisjointSets minimumSet;

      // Inserting all vertices of 'g' (with no edges) into 'minTree'.
      for (int i = 0; i < gVertices.length; i++)
      {
         minTree.addVertex(gVertices[i]);
      }


      for (int i = 0; i < gVertexCount; i++)
      {
         Neighbors eachVertexNeighbors = g.getNeighbors(gVertices[i]);
         
         // Insert current vertex into hash table with disjoint set index as 
         // value
         vertices.insert(gVertices[i], i);
                                           
         // Traverse through each vertex's neighborList and insert a new 
         // KruskalEdge object based on the current vertex, current neighbor, 
         // and the weight on the edge between the two.
         for (int j = 0; j < eachVertexNeighbors.neighborList.length; j++)
         {
            currEdge = new KruskalEdge(gVertices[i], 
                  eachVertexNeighbors.neighborList[j], 
                  eachVertexNeighbors.weightList[j]);

            allEdges.enqueue(currEdge);
         }
      }

      // Use quicksort to sort the Edges based on weight.
      ListSorts.quickSort(allEdges); 

      // Create DisjointSet for finding minimum spanning tree.
      minimumSet = new DisjointSets(allEdges.size());

      // Look through each KruskalEdge object in the list of sorted edges.
      for (int k = 1; k < allEdges.size() + 1; k++)
      { 
         currEdge = ((KruskalEdge) allEdges.nth(k));
         weightAB = currEdge.weight;
         vertexAPosition = (Integer) vertices.find(currEdge.object1).value();
         vertexBPosition = (Integer) vertices.find(currEdge.object2).value();

         // Determine if the two vertices in each KruskalEdge are in the same 
         // set. If they're not, use the union method to put them in the same 
         // set. Add a new edge, using the addEdge method, into minTree with 
         // the two vertices and weight as the parameters.
         if (minimumSet.find(vertexAPosition) != 
               minimumSet.find(vertexBPosition))
         {
            minimumSet.union(minimumSet.find(vertexAPosition), 
                  minimumSet.find(vertexBPosition));
            minTree.addEdge(currEdge.object1, currEdge.object2, weightAB);
         }
      }

      return minTree;
   }

}
