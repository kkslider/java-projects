/* ListNode.java */

package list;


/**
 *  A ListNode is a mutable node in a list.  No implementation is provided.
 *
 *  DO NOT CHANGE THIS FILE.
 **/

public abstract class ListNode {

  /**
   *  myList references the List that contains this node.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected List myList;

  /**
   *  isValidNode returns true if this node is valid; false otherwise.
   *  By default, an invalid node is one that doesn't belong to a list (myList
   *  is null), but subclasses can override this definition.
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
   */
  public abstract ListNode next() throws InvalidNodeException;

  /**
   *  prev() returns the node preceding this node.  If this node is invalid,
   *  throws an exception.
   *
   *  @param node the node whose predecessor is sought.
   *  @return the node preceding this node.
   *  @exception InvalidNodeException if this node is not valid.
   */
  public abstract ListNode prev() throws InvalidNodeException;

  /**
   *  insertAfter() inserts an item immediately following this node.  If this
   *  node is invalid, throws an exception.
   *
   *  @param item the item to be inserted.
   *  @exception InvalidNodeException if this node is not valid.
   */
  public abstract void insertAfter(Object item) throws InvalidNodeException;

  /**
   *  insertBefore() inserts an item immediately preceding this node.  If this
   *  node is invalid, throws an exception.
   *
   *  @param item the item to be inserted.
   *  @exception InvalidNodeException if this node is not valid.
   */
  public abstract void insertBefore(Object item) throws InvalidNodeException;

  /**
   *  remove() removes this node from its List.  If this node is invalid,
   *  throws an exception.
   *
   *  @exception InvalidNodeException if this node is not valid.
   */
  public abstract void remove() throws InvalidNodeException;

}
