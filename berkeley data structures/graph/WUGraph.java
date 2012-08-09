/* WUGraph.java */

package graph;
import hash.*;
import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
   
   private HashTableChained vertices;
   private HashTableChained edges;
   private DList vertexList;

   /**
    * WUGraph() constructs a graph having no vertices or edges. The WUGraph
    * constructor calls constructors for its three fields: Vertices (a hash
    * table that stores the references to the application vertices), 
    * Edges (a hash table that stores the references to the application edges),
    * and vertexList (a DList that stores the internal vertex representation
    * as well as the adjacency lists for each vertex).
    *
    * Running time:  O(1).
    */
   public WUGraph()
   {
      vertices = new HashTableChained();
      edges = new HashTableChained();
      vertexList = new DList();
   }

   /**
    * vertexCount() returns the number of vertices in the graph. The method
    * calls the length() method for a DList and returns the size of that DList;
    * in this case, the DList is vertexList, which stores each vertex.
    * 
    * @return the number of vertices in the graph.
    *
    * Running time:  O(1).
    */
   public int vertexCount()
   {
      return vertexList.length();
   }

   /**
    * edgeCount() returns the number of edges in the graph. The method calls 
    * the size() method on the edges hash table, and returns the number
    * of entries in the hash table.
    * 
    * @return the number of edges in the graph (pairs are not counted twice).
    *
    * Running time:  O(1).
    */
   public int edgeCount()
   {
      return edges.size();
   }

   /**
    * getVertices() returns an array containing all the objects that serve
    * as vertices of the graph.  The array's length is exactly equal to the
    * number of vertices. The method first declares and initializes the Object
    * array, called allVertices, that is to hold the vertices of the graph and 
    * be returned at the end. Then a DListNode pointer is created that points to 
    * the front of the DList of vertices, vertexList. The DListNode pointer 
    * looks at the key field of the Entry contained in its DListNode, and adds 
    * it to allVertices. Then the pointer moves on to the next DListNode in the 
    * vertexList. If the graph has no vertices, the array has length zero.
    *
    * (NOTE:  Do not return any internal data structure you use to represent
    * vertices!  Return only the same objects that were provided by the
    * calling application in calls to addVertex().)
    * 
    * @return an Object array that contains the references to the application
    *    vertices.
    *
    * Running time:  O(|V|).
    */
   public Object[] getVertices(){

      Object[] allVertices = new Object[vertexList.length()];
      DListNode currVertexNode = (DListNode) vertexList.front();

      // Traverse through the vertexList. Setting each element in the array 
      // 'allVertices' to reference an vertex object as given by the outside 
      // application.
      for (int i = 0; i < vertexList.length(); i++)
      {
         // Looks in each DListNode's item field(Assumed to be an 'Entry') for 
         // the key. The entry key is where the actual vertex object is located.
         allVertices[i] = ((Entry) currVertexNode.item()).key();

         try
         {
            currVertexNode = (DListNode)currVertexNode.next();
         }
         catch (InvalidNodeException e)
         {
            System.out.println(e);
         }
      }

      return allVertices;
   }

   /**
    * addVertex() adds a vertex (with no incident edges) to the graph.  The
    * vertex's "name" is the object provided as the parameter "vertex". An 
    * Entry object is created that stores the inputed Object vertex as its key, 
    * and is inserted to the DList vertexList. The Object vertex is inserted 
    * into the Vertices hash table, with the Entry object that the hash table
    * creates having the vertex as the key and the DListNode in vertexList 
    * that was created before as the value. If the load factor of the Vertices
    * hash table exceeds 0.85, the hash table is resized to have double the 
    * buckets. If this object is already a vertex of the graph, the graph is 
    * unchanged.
    *
    * @param an Object vertex from the application.
    * 
    * Running time:  O(1).
    */
   public void addVertex(Object vertex){
      if (vertices.find(vertex) != null)
      {
         return;
      }
      
      Entry vertexHolder = new Entry();
      vertexHolder.setKey(vertex);
      vertexList.insertBack(vertexHolder); 
      
      // Value of 'Entry' inserted into the hash table is the DListNode we 
      // just added into vertexList
      vertices.insert(vertex, vertexList.back()); 

      // Will only resize the table if loadFactor exceeds 0.85
      vertices.resizeTable();
   }

   /**
    * removeVertex() removes a vertex from the graph.  All edges incident on the
    * deleted vertex are removed as well. The method first finds the vertex in 
    * the Vertices hash table, goes to the Entry's value reference, and goes to 
    * the DListNode's adjList field. From there, if adjList is not empty (the 
    * vertex has at least one edge), the partner edges are removed by going 
    * through each node's partner reference (also accounting for self-edges). 
    * Then the edge itself of the current node is removed. The proper edge 
    * references are removed from the Edge hash table as well.
    * Finally, the vertex is removed from vertexList and the vertices hash 
    * table. If the parameter "vertex" does not represent a vertex of the graph, 
    * the graph is unchanged.
    * 
    * @param the Object vertex from the application.
    *
    * Running time:  O(d), where d is the degree of "vertex".
    */
   public void removeVertex(Object vertex)
   {
      if (isVertex(vertex))
      {
         try
         {
            Entry targetVertex = vertices.find(vertex); 
            DListNode targetVertexNode = (DListNode) targetVertex.value(); 
            DList targetAdjList = targetVertexNode.adjList(); 

            if (!targetAdjList.isEmpty())
            {
               // Used to access each individual node in adjList
               DListNode currNeighbor = (DListNode) targetAdjList.front(); 
               DListNode deleteCurrNode;
               
               // Traverse through the adjList of the vertex that is to be 
               // removed. Remove all edges that exist in other vertices by 
               // using the 'partner' fields in each adjList node as the 
               // reference.
               while (currNeighbor.isValidNode())
               {
                  // Checking to make sure the current node does not store an
                  // Entry object with a self-edge
                  if (currNeighbor != currNeighbor.partner())
                  {
                     if (((Entry) currNeighbor.partner().item()).value() != null)
                     {
                        edges.remove(((Entry)currNeighbor.partner().item()).key());
                     }

                     currNeighbor.partner().remove();
                  }
                  
                  if (((Entry) currNeighbor.item()).value() != null)
                  {
                     edges.remove(((Entry) currNeighbor.item()).key());
                  }
                  
                  deleteCurrNode = currNeighbor;

                  currNeighbor = (DListNode) currNeighbor.next();
                  deleteCurrNode.remove();
               }
            }

            // Once all edges relating to vertex we want to remove are dealt 
            // with, remove the vertex from the Vertices hash table and 
            // vertexList.
            vertices.remove(vertex);
            targetVertexNode.remove();
         }
         catch(InvalidNodeException e)
         {
            System.out.println(e);
         }
      }
   }

   /**
    * isVertex() returns true if the parameter "vertex" represents a vertex of
    * the graph. The method checks whether the vertices hash table contains
    * the vertex.
    * 
    * @param the Object vertex to be checked for existence in WUGraph.
    * @return true if vertex exists in WUGraph, false otherwise.
    *
    * Running time:  O(1).
    */
   public boolean isVertex(Object vertex){

      if (vertices.find(vertex) != null)
      {
         return true;
      }
      return false; 
   }
   
   /**
    * degree() returns the degree of a vertex.  Self-edges add only one to the
    * degree of a vertex. The method finds the vertex by finding the Entry
    * holding it in the vertices hash table, going to that Entry's value, which 
    * is a reference to the DListNode holding the vertex in vertexList, and 
    * returns the size of the node's adjacency list. If the parameter "vertex" 
    * doesn't represent a vertex of the graph, zero is returned.
    * 
    * @param the Object vertex from the application.
    * @return the number of edges that the vertex has (self-edges count as one).
    *
    * Running time:  O(1).
    */
   public int degree(Object vertex){
      
      if (vertices.find(vertex) != null)
      {
         Entry foundVertex = ((Entry) vertices.find(vertex));
         DList vertexNeighbors = ((DListNode) foundVertex.value()).adjList();
         
         if (vertexNeighbors != null) 
         {
            return (vertexNeighbors.length());
         }
      }

      return 0;
   }

   /**
    * getNeighbors() returns a new Neighbors object referencing two arrays.  The
    * Neighbors.neighborList array contains each object that is connected to the
    * input object by an edge.  The Neighbors.weightList array contains the
    * weights of the corresponding edges.  The length of both arrays is equal to
    * the number of edges incident on the input vertex. The method creates a 
    * Neighbors object, finds the proper adjacency list of the inputed vertex, 
    * and goes through each node in the adjacency list and adds the neighbor 
    * vertex and corresponding edge weight to the neighborList and weightList 
    * fields, respectively. If the vertex has degree zero, or if the parameter 
    * "vertex" does not represent a vertex of the graph, null is returned 
    * (instead of a Neighbors object).
    *
    * The returned Neighbors object, and the two arrays, are both newly created.
    * No previously existing Neighbors object or array is changed.
    *
    * (NOTE:  In the neighborList array, do not return any internal data
    * structure you use to represent vertices!  Return only the same objects
    * that were provided by the calling application in calls to addVertex().)
    * 
    * @param the Object vertex from the application.
    * @return a Neighbors object, with the neighborList field being an Object
    *    array of references to the application vertices that are connected to
    *    the vertex passed in as the argument; the weightList field stores an
    *    int array of the weights of each corresponding edge.
    *
    * Running time:  O(d), where d is the degree of "vertex".
    */
   public Neighbors getNeighbors(Object vertex) {
      
      if (degree(vertex) != 0 && this.isVertex(vertex))
      {
         
         Neighbors neighborDetails = new Neighbors();
         // currVertexNode is the DListNode in the DList of vertices that 
         // contains the reference to the application vertex inputed
         DListNode currVertexNode = (DListNode) 
               ((Entry) vertices.find(vertex)).value(); 
         neighborDetails.neighborList = new 
               Object[currVertexNode.adjList().length()];
         neighborDetails.weightList = new 
               int[currVertexNode.adjList().length()];
         
         if (!currVertexNode.adjList().isEmpty())
         {  
            try
            {
               // currAdjListNode is the first node in the adjacency list of 
               // the specified vertex
               DListNode currAdjListNode = 
                     (DListNode) currVertexNode.adjList().front();
               
               // Traverses through the adjacency list and adds the neighbor
               // vertex (application) reference and corresponding edge weight
               // to the Neighbors object fields.
               for (int i = 0; i < currVertexNode.adjList().length(); i++)
               {
                  neighborDetails.neighborList[i] = 
                        ((Entry) currAdjListNode.vertexTwo().item()).key();
                  neighborDetails.weightList[i] = 
                        ((Entry) currAdjListNode.item()).weight();

                  // Move onto the next neighbor in the adjList.
                  currAdjListNode = (DListNode) currAdjListNode.next();
               }

            }
            catch (InvalidNodeException e)
            {
               System.out.println(e);
            }
         }
         return neighborDetails;
      }
      return null; 
   }

   /**
    * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
    * u and v does not represent a vertex of the graph, the graph is unchanged.
    * The edge is assigned a weight of "weight".  If the edge is already
    * contained in the graph, the weight is updated to reflect the new value.
    * Self-edges (where u == v) are allowed. If a new edge is being added to 
    * the graph, the edge is added to the adjacency list of both vertices 
    * (unless it's a self-edge), then added to the edges hash table. At the
    * end, the DListNodes in their adjacency lists have their fields set to 
    * the proper objects (such as the node containing the partner edge, the 
    * DListNodes containing the vertices in vertexList, etc.). 
    * 
    * @param Object u and Object v are the Object vertices from the application.
    *    weight is the specified int value of the weight for the edge; if the 
    *    edge already exists, the existing weight is replaced by the inputed
    *    weight. If neither Objects are vertices of the graph, the graph is 
    *    unaltered.
    *
    * Running time:  O(1).
    */
   public void addEdge(Object u, Object v, int weight)
   {
      VertexPair edge = new VertexPair(u, v);
      Entry edgeEntry = edges.find(edge);

      // edge is already in the graph, finds the proper edge and updates its 
      // weight; goes to partner node, updates the partner edge's weight
      if (edgeEntry != null)
      {
         ((Entry) ((DListNode) edgeEntry.value()).item()).setWeight(weight);
         ((Entry) ((DListNode) 
               edgeEntry.value()).partner().item()).setWeight(weight);
      }
      else
      {

         // finds the Entry holding Vertex u, goes to its value reference to the
         // DListNode holding the vertex in the DList of vertices, and goes to
         // that vertex
         DListNode uVertex = ((DListNode) vertices.find(u).value());
         
         // uVertex is not a valid node if u is not a vertex in the graph
         if (!uVertex.isValidNode())
         {
            return;
         }
         DListNode vVertex = ((DListNode) vertices.find(v).value());
         
         // vVertex is not a valid node if v is not a vertex in the graph
         if (!vVertex.isValidNode())
         {
            return;
         }
         
         edgeEntry = new Entry();
         edgeEntry.setKey(edge);
         edgeEntry.setWeight(weight);

         uVertex.adjList().insertBack(edgeEntry);

         // key is the VertexPair, value is the node in the adjacency list 
         // holding the Entry object
         edges.insert(edge, uVertex.adjList().back());
         Entry edgeHash = edges.find(edge);
         
         // adjList node Entry's value is the hash table Entry
         edgeEntry.setValue(edgeHash); 
         
         if (u == v)  // if u and v are the same vertex
         {
            DListNode uEdgeNode = (DListNode) uVertex.adjList().back();
            uEdgeNode.setPartner(uEdgeNode);
            uEdgeNode.setVertexOne(uVertex);
            uEdgeNode.setVertexTwo(uVertex);
            return;
         }

         edgeEntry = new Entry();
         edge = new VertexPair(v, u);   // Changing the order of the vertices
         edgeEntry.setKey(edge);
         edgeEntry.setWeight(weight);

         vVertex.adjList().insertBack(edgeEntry);
         
         DListNode uEdgeNode = (DListNode) uVertex.adjList().back();
         DListNode vEdgeNode = (DListNode) vVertex.adjList().back();

         uEdgeNode.setPartner(vEdgeNode);
         vEdgeNode.setPartner(uEdgeNode);

         uEdgeNode.setVertexOne(uVertex);
         uEdgeNode.setVertexTwo(vVertex);
         vEdgeNode.setVertexOne(vVertex);
         vEdgeNode.setVertexTwo(uVertex);
      }
   }

   /**
    * removeEdge() removes an edge (u, v) from the graph. A VertexPair object
    * is first created to hold the two inputed Object vertices. Then the edges 
    * hash table checks to see if it holds that edge already, and if it does, 
    * removes the proper edges from the adjacency list(s) in the DListNodes of 
    * vertexList. Then the edge is removed from the edges hash table. If either 
    * of the parameters u and v does not represent a vertex of the graph, the 
    * graph is unchanged. If (u, v) is not an edge of the graph, the graph is
    * unchanged.
    *
    * @param Object u and Object v are the Object vertices from the application.
    * 
    * Running time:  O(1).
    */
   public void removeEdge(Object u, Object v)
   {
      VertexPair edge = new VertexPair(u, v);
      Entry edgeEntry = edges.find(edge);
      
      if (edgeEntry != null)
      {
         try
         {
            // If the edge is not a self-edge.
            if (((DListNode) edgeEntry.value()).partner() != 
                  ((DListNode) edgeEntry.value()))
            {
               ((DListNode) edgeEntry.value()).partner().remove();
            }

            ((DListNode) edgeEntry.value()).remove();
            edges.remove(edge);
         }
         catch (InvalidNodeException e)
         {
            e.printStackTrace();
         }
      }

   }

   /**
    * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
    * if (u, v) is not an edge (including the case where either of the
    * parameters u and v does not represent a vertex of the graph). The method
    * creates a VertexPair object to hold both Object vertices, and hashes it
    * to check whether the edge exists in the edges hash table.
    * 
    * @param Object u and Object v are the Object vertices from the application.
    * @return true if the two vertices are connected by an edge, false if
    *    they are not, or are not vertices in the graph.
    *
    * Running time:  O(1).
    */
   public boolean isEdge(Object u, Object v)
   {
      VertexPair edge = new VertexPair(u, v);
      Entry edgeEntry = edges.find(edge);

      if (edgeEntry == null)
      {
         return false;
      }
      else
      {
         return true;
      }
   }

   /**
    * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
    * an edge (including the case where either of the parameters u and v does
    * not represent a vertex of the graph). The method first finds the Entry
    * holding the edge in the edges hash table, goes to the value field which
    * is a reference to the DListNode holding the edge in the adjacency list 
    * of a vertex, and returns the DListNode's Entry object's weight field.
    *
    * (NOTE:  A well-behaved application should try to avoid calling this
    * method for an edge that is not in the graph, and should certainly not
    * treat the result as if it actually represents an edge with weight zero.
    * However, some sort of default response is necessary for missing edges,
    * so we return zero.  An exception would be more appropriate, but
    * also more annoying.)
    * 
    * @param Object u and Object v are the Object vertices from the application.
    * @return the int weight of the edge connecting the two vertices; 0 if 
    *    there is not an edge between the two vertices or if they are not 
    *    vertices in the graph.
    *
    * Running time:  O(1).
    */
   public int weight(Object u, Object v)
   {
      VertexPair edge = new VertexPair(u, v);
      Entry edgeEntry = edges.find(edge);

      if (edgeEntry == null)
      {
         return 0;
      }
      else
      {
         return ((Entry) ((DListNode) edgeEntry.value()).item()).weight();
      }
   }

}
