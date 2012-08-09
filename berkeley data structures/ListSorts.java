/* ListSorts.java */

import queue.LinkedQueue;
import queue.QueueEmptyException;
import list.*;

public class ListSorts {

  private final static int SORTSIZE = 100000;
  
  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
     LinkedQueue overall = new LinkedQueue();
     while (!q.isEmpty())
     {
        try 
        {
           LinkedQueue single = new LinkedQueue();
           single.enqueue(q.dequeue());
           overall.enqueue(single);
        } 
        catch (QueueEmptyException e) 
        {
           // TODO Auto-generated catch block
           e.printStackTrace();
        }   
     }
     
     return overall;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
     LinkedQueue bigQueue = new LinkedQueue();
     while (!q1.isEmpty() && !q2.isEmpty())
     {
        try
        {
           Object qOneItem = q1.front();
           Object qTwoItem = q2.front();
           if (((Comparable) qOneItem).compareTo(qTwoItem) > 0)
           {
              bigQueue.enqueue(q2.dequeue());
           }
           else if (((Comparable) qOneItem).compareTo(qTwoItem) < 0)
           {
              bigQueue.enqueue(q1.dequeue());      
           }
           else
           {
              bigQueue.enqueue(q1.dequeue());  
              bigQueue.enqueue(q2.dequeue());
           }
        }
        catch (QueueEmptyException e)
        {
           e.printStackTrace();        
        }
     }
     
     if (!q1.isEmpty())
     {
        bigQueue.append(q1);
     }
     if (!q2.isEmpty())
     {
        bigQueue.append(q2);
     }
     
     return bigQueue;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
     // Your solution here.
     while (!qIn.isEmpty())
     {
        try
        {
           Object qItem = qIn.dequeue();
           if (((Comparable) qItem).compareTo(pivot) > 0)
           {
              qLarge.enqueue(qItem);
           }
           else if (((Comparable) qItem).compareTo(pivot) < 0)
           {
              qSmall.enqueue(qItem);
           }
           else
           {
              qEquals.enqueue(qItem);
           }
        }
        catch (QueueEmptyException e)
        {
           e.printStackTrace(); 
        }
     }
     
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
     LinkedQueue temp = ListSorts.makeQueueOfQueues(q);
     if (temp.isEmpty())
     {
        return;
     }
     while (temp.size() != 1)
     {
        try
        {
           Object queueOne = temp.dequeue();
           Object queueTwo = temp.dequeue();
           LinkedQueue temp2 = ListSorts.mergeSortedQueues((LinkedQueue) 
                 queueOne, (LinkedQueue) queueTwo);
           temp.enqueue(temp2);
        }
        catch (QueueEmptyException e)
        {
           e.printStackTrace();              
        }
     }
     
     try 
     {
        q.append((LinkedQueue) temp.dequeue());
     } 
     catch (QueueEmptyException e) 
     {
        // TODO Auto-generated catch block
        e.printStackTrace();
     } 
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
     if (q.isEmpty())
     {
        return;
     }
     int pivotInt = (int) (1 + Math.random() * q.size());
     Object pivot = q.nth(pivotInt);
     LinkedQueue qSmall = new LinkedQueue();
     LinkedQueue qEquals = new LinkedQueue();
     LinkedQueue qLarge = new LinkedQueue();
     
     ListSorts.partition(q, (Comparable) pivot, qSmall, qEquals, qLarge);
     
     ListSorts.quickSort(qSmall);
     ListSorts.quickSort(qLarge);
     
     q.append(qSmall);
     q.append(qEquals);
     q.append(qLarge);
     

  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }


}
