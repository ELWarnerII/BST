package BSTDictionary;


/**
 * Node Class
 * 
 * This Node object class is an element of the Binary Search Tree 
 * implementation of the Dictionary ADT. It contains pointers to
 * its left and right children, and a key/value pair for the
 * Dictionary.
 * 
 * @author Louis Warner
 */

public class Node {
		
	/*Items contained in a given Node.*/
	public int key;
	public String value;
	public Node leftChild;
	public Node rightChild;
		
	/**
	 * This is the constructor class for a Node.
	 * 
	 * @param int key, String value
	 */
	public Node(int key, String value){
		this.key = key;
		this.value = value;
	}
		
	/**
	 * Displays an entry in the dictionary.
	 * 
	 * @return String display
	 */
	 public String display(){
		String display = key + "    " + value + "\n";
		return display;
	}
		
}
