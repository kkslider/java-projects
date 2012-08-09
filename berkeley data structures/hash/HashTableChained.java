package hash;
/* HashTableChained.java */


import graph.Graph;
import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Graph {

   /**
    *  Place any data fields here.
    **/

   private DList[] hashTable;

   private int numEntries = 0;
   private int numBuckets;
   private double loadFactor = 0.85;


   public void resizeTable(){

      if (((double) numEntries/(double) numBuckets) > 0.85)
      {

         DList[] tempTable = hashTable;
         DListNode currNode;
         Entry currEntry;
         numBuckets = numBuckets * 2;
         hashTable = new DList[numBuckets];

         try
         {
            for (int i=0; i<hashTable.length; i++)
            {
               hashTable[i] = new DList();
            }

            for (int i=0;i<tempTable.length; i++)
            {
               currNode = (DListNode)tempTable[i].front();

               for (int j = 0;j < tempTable[i].length(); j++)
               {
                  currEntry = (Entry)currNode.item();
                  insert(currEntry.key, currEntry.value); 
                  currNode = ((DListNode)currNode.next());
               }
            }
         }
         catch(InvalidNodeException e)
         {
            System.out.println(e);
         }

      }
   }

   /** 
    *  Construct a new empty hash table intended to hold roughly sizeEstimate
    *  entries.  (The precise number of buckets is up to you, but we recommend
    *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
    **/

   public HashTableChained(int sizeEstimate) 
   {
      numBuckets = (int)(sizeEstimate/loadFactor);
      hashTable = new DList[numBuckets];

      for (int i = 0; i < hashTable.length; i++)
      {
         hashTable[i] = new DList();
      }
   }

   /** 
    *  Construct a new empty hash table with a default size.  Say, a prime in
    *  the neighborhood of 100.
    **/

   public HashTableChained() 
   {
      hashTable = new DList[5];
      numBuckets = 5;

      for (int i = 0; i < hashTable.length; i++)
      {
         hashTable[i] = new DList();
      }
   }

   /**
    *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
    *  to a value in the range 0...(size of hash table) - 1.
    *
    *  This function should have package protection (so we can test it), and
    *  should be used by insert, find, and remove.
    **/

   int compFunction(int code) 
   {
      int usedCode = code;

      if (code < 0)
      {
         usedCode = usedCode * -1;
      }
      int binNum = (((65324*(usedCode%5000) + (usedCode%9856) + 576546)% 3169) 
            % numBuckets);

      return binNum;
   }

   /** 
    *  Returns the number of entries stored in the dictionary.  Entries with
    *  the same key (or even the same key and value) each still count as
    *  a separate entry.
    *  @return number of entries in the dictionary.
    **/

   public int size() 
   {
      return numEntries;
   }

   public int numBuckets()
   {
      return numBuckets;
   }


   public boolean isEmpty() 
   {
      if (numEntries == 0)
      {
         return true;
      }
      return false;
   }

   /**
    *  Create a new Entry object referencing the input key and associated value,
    *  and insert the entry into the dictionary.  Return a reference to the new
    *  entry.  Multiple entries with the same key (or even the same key and
    *  value) can coexist in the dictionary.
    *
    *  This method should run in O(1) time if the number of collisions is small.
    *
    *  @param key the key by which the entry can be retrieved.
    *  @param value an arbitrary object.
    *  @return an entry containing the key and value.
    **/

   public Entry insert(Object key, Object value) 
   {
      int hashCode;
      Entry newEntry = new Entry();

      newEntry.key = key;
      newEntry.value = value;

      hashCode = key.hashCode();

      hashCode = compFunction(hashCode);

      hashTable[hashCode].insertFront(newEntry);
      numEntries++;

      return newEntry;
   }

   /** 
    *  Search for an entry with the specified key.  If such an entry is found,
    *  return it; otherwise return null.  If several entries have the specified
    *  key, choose one arbitrarily and return it.
    *
    *  This method should run in O(1) time if the number of collisions is small.
    *
    *  @param key the search key.
    *  @return an entry containing the key and an associated value, or null if
    *          no entry contains the specified key.
    **/

   public DListNode findNode(Object key) 
   {
      int hashCode = key.hashCode();
      hashCode = compFunction(hashCode);

      DListNode iter = (DListNode)hashTable[hashCode].front();
      try
      {
         while (iter != null)
         {
            if (key.equals(((Entry)iter.item()).key()))
            {
               return (iter);
            }          
            iter = (DListNode)iter.next();
         }
      }
      catch (InvalidNodeException e)
      {
         System.out.println(e);
      }

      return null;
   }

   public Entry find(Object key) 
   {
      int hashCode = key.hashCode();
      hashCode = compFunction(hashCode);

      DListNode iter = (DListNode)hashTable[hashCode].front();

      while (iter.isValidNode())
      {
         try 
         {
            if (key.equals(((Entry) iter.item()).key()))
            {
               return ((Entry) iter.item());
            }


            iter = (DListNode) iter.next();
         } 
         catch (InvalidNodeException e) 
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      return null;
   }

   /** 
    *  Remove an entry with the specified key.  If such an entry is found,
    *  remove it from the table and return it; otherwise return null.
    *  If several entries have the specified key, choose one arbitrarily, then
    *  remove and return it.
    *
    *  This method should run in O(1) time if the number of collisions is small.
    *
    *  @param key the search key.
    *  @return an entry containing the key and an associated value, or null if
    *          no entry contains the specified key.
    */

   public Entry remove(Object key) 
   {

      int hashCode = key.hashCode();
      hashCode = compFunction(hashCode);

      DListNode iter = (DListNode)hashTable[hashCode].front();

      while (iter.isValidNode())
      {
         try
         {
            if (key.equals(((Entry)iter.item()).key()))
            {
               iter.remove();
               numEntries--;
               return ((Entry)iter.item());
            }      

            iter = (DListNode) iter.next();
         }
         catch (InvalidNodeException e)
         {
            System.out.println(e);
         }
      }

      return null;
   }

   /**
    *  Remove all entries from the dictionary.
    */
   public void makeEmpty() 
   {
      for (int i = 0; i < hashTable.length; i++)
      {
         hashTable[i] = new DList();
      }

      numEntries = 0;
   }

   public DList getEntry(int entryNum)
   {
      return (hashTable[entryNum]);
   }
}
