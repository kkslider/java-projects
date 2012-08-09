
/**
 * The KruskalEdge class is essentially the same as VertexPair. However, 
 * there is an additional field named 'weight' that stores the weight of the 
 * edge this object is supposed to represent. 
 * 
 * In addition, the class implements Comparable and has a compareTo() method 
 * that compares two KruskalEdge objects based on their weights. 
 */

public class KruskalEdge implements Comparable{

   protected Object object1;
   protected Object object2;
   protected int weight; //Additional field: weight of the edge.

   protected KruskalEdge(Object o1, Object o2, int w) {
      object1 = o1;
      object2 = o2;
      weight = w;
   }


   /**
    * hashCode() returns a hashCode equal to the sum of the hashCodes of each
    * of the two objects of the pair, so that the order of the objects will
    * not affect the hashCode.  Self-edges are treated differently:  we don't
    * add an object's hashCode to itself, since the result would always be even.
    * We add one to the hashCode so that a self-edge will not collide with the
    * object itself if vertices and edges are stored in the same hash table.
    */
   public int hashCode() {
      if (object1.equals(object2)) {
         return object1.hashCode() + 1;
      } else {
         return object1.hashCode() + object2.hashCode();
      }
   }

   /**
    * equals() returns true if this VertexPair represents the same unordered
    * pair of objects as the parameter "o".  The order of the pair does not
    * affect the equality test, so (u, v) is found to be equal to (v, u).
    */
   public boolean equals(Object o) {
      if (o instanceof KruskalEdge) {
         return ((object1.equals(((KruskalEdge) o).object1)) &&
               (object2.equals(((KruskalEdge) o).object2))) ||
               ((object1.equals(((KruskalEdge) o).object2)) &&
                     (object2.equals(((KruskalEdge) o).object1)));
      } else {
         return false;
      }
   }

   /**
    * compareTo() allows KruskalEdge objects to be compared to each other
    * based on their weights.
    * ...returns 0 if both vertices have equal weight.
    * ...returns 1 if this.weight > weight of compared to vertex.
    * ...returns -1 if this.weight < weight of compared to vertex.
    * 
    */
   public int compareTo(Object o2){
      if(weight == ((KruskalEdge)o2).weight){
         return 0;
      }
      else if(weight > ((KruskalEdge)o2).weight){
         return 1;
      }
      else{
         return -1;
      }
   }

}
