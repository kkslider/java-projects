package hash;
/* Entry.java */

/**
 *  A class for dictionary entries.
 *
 *  DO NOT CHANGE THIS FILE.  It is part of the interface of the
 *  Dictionary ADT.
 **/

public class Entry {

   protected Object key;    // an Object reference to the application vertex or 
                            // VertexPair (edge) object
   protected Object value;  // a reference to a DListNode
   protected int weight;    // only for edges


   public Object key() {
      return key;
   }

   public Object value() {
      return value;
   }

   public int weight()
   {
      return weight;
   }

   public void setKey(Object key)
   {
      this.key = key;
   }

   public void setValue(Object value)
   {
      this.value = value;
   }

   public void setWeight(int weight)
   {
      this.weight = weight;
   }
}
