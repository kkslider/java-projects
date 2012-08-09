/* DListNode.java */

package list;


/**
 *  A DListNode is a mutable node in a DList (doubly-linked list).
 **/

public class DListNode extends ListNode {

  /**
   *  (inherited)  item references the item stored in the current node.
   *  (inherited)  myList references the List that contains this node.
   *  prev references the previous node in the DList.
   *  next references the next node in the DList.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   **/

   protected Object item;         // Object would be an Entry object
   protected DListNode prev;   
   protected DListNode next;
   protected DListNode partner;   // reference to the DListNode holding the 
                                  // Entry with the partner VertexPair; (u, g) 
                                  // --> (g, u);
                                  // could also be itself (a self-edge)
   protected DListNode vertexOne; // reference to the first vertex of VertexPair
                                  // in the DList of vertices
   protected DListNode vertexTwo; // reference to the second vertex of VertexPair
                                  // in the DList of vertices
   protected DList adjList;       // this DList is a field of the DListNodes
                                  // in the DList of vertices; contains the
                                  // DListNodes that contain Entry objects 
                                  // that contain the VertexPair objects

  /**
   *  DListNode() constructor.
   *  @param i the item to store in the node.
   *  @param l the list this node is in.
   *  @param p the node previous to this node.
   *  @param n the node following this node.
   */
  DListNode(Object i, DList l, DListNode p, DListNode n) {
    item = i;
    myList = l;
    prev = p;
    next = n;
  }

  /**
   *  item() returns this node's item.
   *  
   *  @return the item stored in this node.
   *
   *  Performance:  runs in O(1) time.
   */
  
  public Object item() 
  {
     return item;
  }
  
  /**
   *  isValidNode returns true if this node is valid; false otherwise.
   *  An invalid node is represented by a `myList' field with the value null.
   *  Sentinel nodes are invalid, and nodes that don't belong to a list are
   *  also invalid.
   *
   *  @return true if this node is valid; false otherwise.
   *
   *  Performance:  runs in O(1) time.
   */
  public boolean isValidNode() {
    return myList != null;
  }

  /**
   *  next() returns the node following this node.  If this node is invalid,
   *  throws an exception.
   *
   *  @return the node following this node.
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public ListNode next() throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException("next() called on invalid node");
    }
    return next;
  }

  /**
   *  prev() returns the node preceding this node.  If this node is invalid,
   *  throws an exception.
   *
   *  @param node the node whose predecessor is sought.
   *  @return the node preceding this node.
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public ListNode prev() throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException("prev() called on invalid node");
    }
    return prev;
  }

  /**
   *  insertAfter() inserts an item immediately following this node.  If this
   *  node is invalid, throws an exception.
   *
   *  @param item the item to be inserted.
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public void insertAfter(Object item) throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException("insertAfter() called on invalid node");
    }
    
    DListNode tracker = this.next;
    this.next =  ((DList) this.myList).newNode(item, (DList) this.myList, this, tracker);
    tracker.prev = this.next;
    this.myList.size++;
    // Your solution here.  Will look something like your Homework 4 solution,
    //   but changes are necessary.  For instance, there is no need to check if
    //   "this" is null.  Remember that this node's "myList" field tells you
    //   what DList it's in.  You should use myList.newNode() to create the
    //   new node.
  }

  /**
   *  insertBefore() inserts an item immediately preceding this node.  If this
   *  node is invalid, throws an exception.
   *
   *  @param item the item to be inserted.
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public void insertBefore(Object item) throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException("insertBefore() called on invalid node");
    }
    
    DListNode tracker = this.prev;
    this.prev = ((DList) this.myList).newNode(item, (DList) this.myList, tracker, this);
    tracker.next = this.prev;
    this.myList.size++;
    // Your solution here.  Will look something like your Homework 4 solution,
    //   but changes are necessary.  For instance, there is no need to check if
    //   "this" is null.  Remember that this node's "myList" field tells you
    //   what DList it's in.  You should use myList.newNode() to create the
    //   new node.
  }

  /**
   *  remove() removes this node from its DList.  If this node is invalid,
   *  throws an exception.
   *
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public void remove() throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException("remove() called on invalid node");
    }
    
    this.prev.next = this.next;
    this.next.prev = this.prev;
    this.myList.size--;
    // Your solution here.  Will look something like your Homework 4 solution,
    //   but changes are necessary.  For instance, there is no need to check if
    //   "this" is null.  Remember that this node's "myList" field tells you
    //   what DList it's in.

    this.partner = null;
    this.vertexOne = null;
    this.vertexTwo = null;
    this.adjList = null;

    // Make this node an invalid node, so it cannot be used to corrupt myList.
    myList = null;
    // Set other references to null to improve garbage collection.
    next = null;
    prev = null;
  }
  
  public void setPartner(DListNode partner)
  {
     this.partner = partner;
  }
  
  public void setVertexOne(DListNode vertexOne)
  {
     this.vertexOne = vertexOne;
  }
  
  public void setVertexTwo(DListNode vertexTwo)
  {
     this.vertexTwo = vertexTwo;
  }
  
  public void setAdjList(DList adjList)
  {
     this.adjList = adjList;
  }
  
  public DListNode partner()
  {
     return this.partner;
  }
  
  public DListNode vertexOne()
  {
     return this.vertexOne;
  }
  
  public DListNode vertexTwo()
  {
     return this.vertexTwo;
  }
  
  public DList adjList()
  {
     return this.adjList;
  }

  public void initializeAdjList()
  {
     adjList = new DList();
  }
}
