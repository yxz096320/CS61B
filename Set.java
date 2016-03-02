/* Set.java */

import list.*;
import java.lang.Comparable;

/**
 *  A Set is a collection of Comparable elements stored in sorted order.
 *  Duplicate elements are not permitted in a Set.
 **/
public class Set<T> {
  /* Fill in the data fields here. */
  private DList elements;
  private int count;
  /**
   * Set ADT invariants:
   *  1)  The Set's elements must be precisely the elements of the List.
   *  2)  The List must always contain Comparable elements, and those elements 
   *      must always be sorted in ascending order.
   *  3)  No two elements in the List may be equal according to compareTo().
   **/

  /**
   *  Constructs an empty Set. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public Set() { 
    // Your solution here.
    elements = new DList();
    count = 0;
  }

  /**
   *  cardinality() returns the number of elements in this Set.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int cardinality() {
    // Replace the following line with your solution.
    return count;
  }
  
  public ListNode first(){
	  return elements.front();
  }
  /**
   *  insert() inserts a Comparable element into this Set.
   *
   *  Sets are maintained in sorted order.  The ordering is specified by the
   *  compareTo() method of the java.lang.Comparable interface.
   *
   *  Performance:  runs in O(this.cardinality()) time.
   **/
  public void insert(Comparable<T> c) {
    // Your solution here.
    if (count == 0) {
      elements.insertFront(c);
      count = 1;
    }
    else{
      ListNode node = elements.front();
      while(node.isValidNode() ){
    	  try{
	    	  if(c.compareTo((T) node.item()) == 0){
	    		//  System.out.println("inserted duplicate item :"+ c);
	    		  return;
	    	  }
	    	  else if(c.compareTo((T) node.item()) < 0){
	    		  node.insertBefore(c);
	    		  count ++;
	    		  return; 
	    	  }
	    	  else{
	    		  node = node.next();  
	    	  }
    	  }
    	  catch(InvalidNodeException e){
    		  System.out.println(e);
    	  }
      }
      elements.insertBack(c);
      count ++;
    }
  }

  /**
   *  union() modifies this Set so that it contains all the elements it
   *  started with, plus all the elements of s.  The Set s is NOT modified.
   *  Make sure that duplicate elements are not created.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Your implementation should NOT copy elements of s or "this", though it
   *  will copy _references_ to the elements of s.  Your implementation will
   *  create new _nodes_ for the elements of s that are added to "this", but
   *  you should reuse the nodes that are already part of "this".
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
   **/
  public void union(Set<T> s) {
    // Your solution here.
	  ListNode nodeS = s.first();
	  ListNode node = elements.front();
	  if(s.cardinality() == 0){
		  return;
	  }
	  try{
		  T i1 = (T) nodeS.item();
		  T i2 = (T) node.item();
		  while(nodeS.isValidNode() && node.isValidNode()){
			  if(((Comparable<T>) i1).compareTo(i2) == 0){ 
				  if(nodeS.next().isValidNode() && node.next().isValidNode() ){
					  nodeS = nodeS.next();
					  node = node.next();
					  i1 = (T) nodeS.item();
					  i2 = (T) node.item(); 
				  }
				  else if(node.next().isValidNode()){
					  return;
				  }
				  else{
					  while(nodeS.next().isValidNode()){
						  nodeS = nodeS.next();
						  i1 = (T)nodeS.item();
						  node.insertAfter(i1);
						  node = node.next();
						  count++;
						  if(!node.next().isValidNode()){
							  return;
						  }
					  }
				  }
			  }
			  else if(((Comparable<T>) i1).compareTo(i2) < 0){
				  node.insertBefore(i1);
				  count ++;
				  node = node.next();
				  nodeS = nodeS.next();
				  i1 = (T) nodeS.item();
				  i2 = (T) node.item();
			  }
			  else {
				  node = node.next();
				  i2 = (T) node.item();
			  }
		  }
		  
	  }
	  catch(InvalidNodeException e){
		  System.out.println("union an invalid Node");
	  }
	
  }

  /**
   *  intersect() modifies this Set so that it contains the intersection of
   *  its own elements and the elements of s.  The Set s is NOT modified.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Do not construct any new ListNodes during the execution of intersect.
   *  Reuse the nodes of "this" that will be in the intersection.
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT CONSTRUCT ANY NEW NODES.
   *  DO NOT ATTEMPT TO COPY ELEMENTS.
   **/
  public void intersect(Set s) {
    // Your solution here.
	  if(count == 0){
		  return;
	  }
	  if(s.cardinality() == 0){
		  try{
			  ListNode node = elements.front();
			  while(node.next().isValidNode()){
				  node = node.next();
				  node.prev().remove();
				  count --;
				  }
			  if(count == 1){
				  elements.front().remove();
			  }
			  if(count != 0){
				System.out.println("intersect with an empty set should be empty too");  
			  }
			  }
		  catch(InvalidNodeException e){
			  System.out.println("intersect with an empty set shrows exception");
			  }
	  }
	  ListNode nodeS = s.first();
	  ListNode node = elements.front();
	  try{
		  T i1 = (T) nodeS.item();
		  T i2 = (T) node.item();
		  while(nodeS.isValidNode() && node.isValidNode()){
			  if(((Comparable<T>) i1).compareTo(i2) == 0){
				  if(nodeS.next().isValidNode() && node.next().isValidNode() ){
					  nodeS = nodeS.next();
					  node = node.next();
					  i1 = (T) nodeS.item();
					  i2 = (T) node.item(); 
				  }
				  else if(nodeS.next().isValidNode()){
					  return;
				  }
				  else{
					  while(node.next().isValidNode()){
						  node = node.next();
						  node.prev().remove();
						  count--;
						  if(!node.next().isValidNode()){
							  return;
						  }
					  }
				  nodeS = nodeS.next();
				  node = node.next();
				  i1 = (T) nodeS.item();
				  i2 = (T) node.item();
				  }
			  }
			  else if(((Comparable<T>) i1).compareTo(i2) < 0){
				  nodeS = nodeS.next();
				  i2 = (T) node.item();
			  }
			  else {
				  node = node.next();
				  i2 = (T)node.item();
				  node.prev().remove();
				  count--;
			  }
		  }
		  if(node.isValidNode()){
			  while(node.isValidNode()){
				  node = node.next();
				  node.prev().remove();;
				  count--;
			  }
		  }
	  }
	  catch(InvalidNodeException e){
		  System.out.println("insersect found an invalid Node");
		  }
  }

  /**
   *  toString() returns a String representation of this Set.  The String must
   *  have the following format:
   *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
   *            between them.
   *    {  1  2  3  } for a Set of three Integer elements.  No spaces before
   *            "{" or after "}"; two spaces before and after each element.
   *            Elements are printed with their own toString method, whatever
   *            that may be.  The elements must appear in sorted order, from
   *            lowest to highest according to the compareTo() method.
   *
   *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
   *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
   *            DEVIATIONS WILL LOSE POINTS.
   **/
  public String toString() {
    // Replace the following line with your solution.
	  String result = "[ ";
	 
	  if(count ==0){
		  return result + "]";
	  }
	  try{
		  ListNode node= elements.front();
		  while(node.isValidNode()){
			  result = result + node.item() + " ";
			  node = node.next();
		  }
	  }
	  catch(InvalidNodeException e){
		  System.out.println("hahaha");
	  }
	  return result + "]";
  }

  public static void main(String[] argv) {
    Set s = new Set();
    s.insert(new Integer(3));
    s.insert(new Integer(4));
    s.insert(new Integer(3));
    System.out.println("Set s = " + s);

    Set s2 = new Set();
    s2.insert(new Integer(4));
    s2.insert(new Integer(5));
    s2.insert(new Integer(5));
    System.out.println("Set s2 = " + s2);

    Set s3 = new Set();
    s3.insert(new Integer(5));
    s3.insert(new Integer(3));
    s3.insert(new Integer(8));
    System.out.println("Set s3 = " + s3);

    s.union(s2);
    System.out.println("After s.union(s2), s should be[3 4 5], s = " + s);

    s.intersect(s3);
    System.out.println("After s.intersect(s3), s should be[3 5], s = " + s);
    
    System.out.println("s.cardinality() = " + s.cardinality());
    
    s.insert(1);
    System.out.println("After insert 1, s should be[1 3 5] = " + s);
    
    s.union(s3);
    System.out.println("After s.union(s3), s should be[1 3 5 8], s = " + s);

    // You may want to add more (ungraded) test code here.
    //test empty set
    Set s4 = new Set();
    System.out.println("Empty Set s4 = " + s4);
    
    System.out.println("s4.cardinality() = " + s4.cardinality());
    
    s4.union(s4);
    System.out.println("After s4.union(s4), s4 = " + s4);

    s4.intersect(s4);
    System.out.println("After s4.intersect(s4), s4 = " + s4);

    s3.union(s4);
    System.out.println("After s3.union(s4), s3 = " + s3);

    s3.intersect(s4);
    System.out.println("After s3.intersect(s4), s3 = " + s3);
  }
}
