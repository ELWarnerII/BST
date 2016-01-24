package BSTDictionary;
import java.util.*;
import java.io.*;

/**
 * BST Class
 * 
 * This class is an implementation of the Dictionary ADT.
 * It uses a binary search tree design to store, manipulate,
 * and look-up the entries of the dictionary.
 * 
 * @author Louis Warner
 */
public class BST{

	public static Node rootNode;
	
	/*Counter to determine number of comparisons used in find*/
	public static int opCounter = 0;
	
	/*Index is solely used as an indicator in building the initial tree from the preorder array.*/
	public int index = 0;
	
		
	/**
	* Constructor for the BST dictionary class.
	*/
	BST(){
		rootNode = null;
	}
		
	 
	/**
	 * Returns the minimum Node in a subtree given a root.
	 * 
	 * @param Node root
	 * @return Node minimum
	 */
	public Node minValue(Node root) {
	  Node minimum = root;
	 
	  while (minimum.leftChild != null){
		  minimum = minimum.leftChild;
	  }
	  return minimum;
	}
	
	
	/**
	* Makes sure the tree being built has at least one value.
	* Begins the build process for the tree
	* 
	* @param Node[] array
	* @param int length
	* @return Node rootNode
	*/
	public Node buildTree(Node[] array, int length) {
	    if (length <= 0) {
	        return null;
	    }
	    return buildTree(array, length, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	
	/**
	* Recursively converts an array of nodes in preorder to a binary tree.
	* 
	* @param Node[] array
	* @param int length
	* @param int min - current minimum value
	* @param int max - current maximum value
	* @return Node root
	*/
	public Node buildTree(Node[] array, int length, int min, int max) {
		
		if (index >= length) {
	        return null;
	    }
	    
	    Node root = null;
	    Node current = array[index];
	    
	    if (current.key > min && current.key < max) {
	        root = current;
	        index++;
	        if (index < length) {
	            root.leftChild = buildTree(array, length, min, current.key);
	        } 
	        if (index < length) {
	            root.rightChild = buildTree(array, length, current.key, max);
	        }
	    } 
	    return root;
	}
	
	
	/**
	 * Searches for a key in a binary tree starting at the given root.
	 * 
	 * @param int key
	 * @param Node root
	 * @return Node result
	 */
	public Node find(int key, Node root) {
		Node result = null;
		opCounter++;
		if (root == null){
			return null;
		}
		if (key == root.key){
			return root;
		}
		if (root.leftChild != null){
			result = find(key, root.leftChild);
		}
		if (result == null && root.rightChild != null){
		    result = find(key, root.rightChild);
		}
		return result;
	}
	
	
	/**
	 * Inserts a node into the binary search tree if the given
	 * node is not already present in the tree.
	 * 
	 * @param int key
	 * @param String value
	 * @param Node root
	 */
	public Node insert(int key, String value, Node root) {
		if(root == null){
			root = new Node(key, value);
		} else if(key < root.key){
			root.leftChild = insert(key, value, root.leftChild);
		} else if(key > root.key){
			root.rightChild = insert(key, value, root.rightChild);
		}
		return root;
	}
	
	
	/**
	 * Removes a node from the binary search tree if the given
	 * node is present in the tree.
	 * 
	 * @param int key
	 * @param Node root
	 */
	public Node remove(int key, Node root) {
		if(root != null && root.key == key){
			// found it
			if (root.leftChild == null || root.rightChild == null){ // at most one child
				if(root.leftChild == null){
					root = root.rightChild;
				}
				else{
					root = root.leftChild;
				}
			}
			else {
				// has two children – replace it by its inorder successor
				int newKey = minValue(root.rightChild).key;
				root.value = minValue(root.rightChild).value;
				root.key = newKey;
				root.rightChild = remove(root.key, root.rightChild);
			}
		}
		else if(root != null && root.key > key){
			root.leftChild = remove(key, root.leftChild);
		}
		else if(root != null){
			root.rightChild = remove(key, root.rightChild);
		}
		return root;
	}
	
	
	/**
	 * Display uses an inorder traversal to print each
	 * key-value pair in the BST.
	 * 
	 * @param Node root
	 * @return String display
	 */
	public String display(Node root, String display){
		String returnable = display;
		if(root.leftChild != null){
			returnable += display(root.leftChild, display);
		}
		returnable += root.display();
		if(root.rightChild != null){
			returnable += display(root.rightChild, display);
		}
		return returnable;
	}
	

	/**
	 *	ProcessFile takes a file input and processes builds a binary search tree
	 *	from the first set of inputs in the file, inserts nodes into the tree with
	 *	the second set of inputs in the file, and removes nodes from the tree corresponding
	 *	to the third set of inputs in the file. It outputs a file with an inorder traversal
	 *	of the final tree.
	 *
	 * @param Scanner input, PrintStream output, BST theBST
	 * @throws FileNotFoundException if file doesn't exist
	 */
	@SuppressWarnings("static-access")
	public static void processFile(Scanner input, PrintStream output, BST theBST) throws FileNotFoundException {
		int key;
		Node[] preOrder = new Node[100000];
		int buildSize = 0;
		String value;
		
		System.out.println();
		System.out.println("Reading binary tree preorder values from file...");
		System.out.println();
		
		while(input.hasNextInt()){
			key = input.nextInt();
			if(key == -1){
				break;
			}
			value = input.next();
			preOrder[buildSize] = new Node(key, value);
			buildSize ++;
		}
		
		System.out.println();
		System.out.println("Creating dictionary...");
		
		theBST.rootNode = theBST.buildTree(preOrder, buildSize);
		
		System.out.println("Dictionary created.");
		System.out.println();
		System.out.println("Beginning insert operations...");
		
		while(input.hasNextInt()){
			key = input.nextInt();
			if(key == -1){
				break;
			}
			value = input.next();
			theBST.insert(key, value, rootNode);
		}
		
		System.out.println("Inserts successful.");
		System.out.println();
		System.out.println("Beginning remove operations...");
		
		while(input.hasNextInt()){
			key = input.nextInt();
			if(key == -1){
				break;
			}
			theBST.remove(key, rootNode);
		}
		
		System.out.println("Removals successful.");
		System.out.println();
		System.out.println("Program complete.");
		
		String display = "";
		output.print(theBST.display(rootNode, display));
		System.out.println(rootNode.key);
		
		return;
	}


	/**
	 *	ProcessFile2 takes a second file input and finds all of keys
	 *	in the given file. It generates an output file with the 
	 *	number of comparisons for every 1000th key.
	 *
	 * @param Scanner input, PrintStream output, BST theBST
	 * @throws FileNotFoundException if file doesn't exist
	 */
	@SuppressWarnings("static-access")
	public static void processFile2(Scanner input, PrintStream output, BST theBST) throws FileNotFoundException {
		int key;
		int[] findList = new int[100000];
		int count = 0;
		
		System.out.println();
		System.out.println("Creating find list...");
		
		while(input.hasNextInt()){
			key = input.nextInt();
			input.next();
			findList[count] = key;
			count ++;
		}
		
		System.out.println();
		System.out.println("Beginning find operations...");
		
		int checker = 0;
		for(int i = 0; i < count; i++){
			theBST.find(findList[i], theBST.rootNode);
			checker ++;
			if(checker % 1000 == 0){
				output.println(opCounter);
			}
		}
		
		System.out.println();
		System.out.println("Number of find operations performed: " + count);
		System.out.println("Total Comparisons used in find operations: " + opCounter);
		System.out.println();
		
		return;
	}
	/**
	 * Calls the userInterface method.
	 * 
	 * @param args An array of command line arguments
	 * @throws FileNotFoundException if file doesn't exist
	 */
	public static void main(String [] args) throws FileNotFoundException{
		userInterface();
	}

	
	/**
	 * Welcomes the user and prompts the user for an input file.
     * Calls methods for obtaining input and output file names.
     * Calls the method to process the input file.
     *
	 * @throws FileNotFoundException if file doesn't exist
	 */
	public static void userInterface() throws FileNotFoundException{
		BST theBST = new BST();
		
		//Create an input Scanner to interact with user
		Scanner input = new Scanner(System.in);
	      
		System.out.println();
		System.out.println();
		System.out.println("Welcome to the Binary Search Tree Dictionary!");
		System.out.println();
		  
		//Get a Scanner that will read the input
		Scanner fileReader = getInputScanner(input);

		//Create a PrintStream based on the valid file
		//passed in by the user
		PrintStream fileWriter = getOutputPrintStream(input);

		processFile(fileReader, fileWriter, theBST);
		System.out.println("Output file successfully generated.");
		System.out.println();
		
		//Get a Scanner that will read the input
		Scanner fileReader2 = getInputScanner(input);

		//Create a PrintStream based on the valid file
		//passed in by the user
		PrintStream fileWriter2 = getOutputPrintStream(input);
		processFile2(fileReader2, fileWriter2, theBST);
		
		System.out.println("Output file successfully generated.");
		System.out.println("Goodbye!");
	}

	
	/**
	 * This method prompts the user for a file name,
	 * and re-prompts the user until a valid file name is entered.
	 *
	 * @param Scanner console
	 * @return fileScanner a new scanner associated with the file
	 * @throws FileNotFoundException if file doesn't exist
	 */
	public static Scanner getInputScanner(Scanner console) throws FileNotFoundException{
		Scanner fileScanner = null;
		while (fileScanner == null) {
			System.out.print("Please enter the location and name of the file to be processed: ");
			String name = console.nextLine();
			try {
				fileScanner = new Scanner(new File(name));
				}
			catch (FileNotFoundException e) {
				System.out.println();
				System.out.println("File: " + name + " not found. Try again.");
			}
		}
		return fileScanner;
	}

	
	/**
	 * Returns a PrintStream for the specified file.  If the
	 * file specified by the user already exists, it will ask
	 * the user whether or not it can overwrite the file.
	 * 
	 * @param Scanner console to process user's input
	 * @return a PrintStream to print to the file.
     * @throws FileNotFoundException if file doesn't exist
	 */
	public static PrintStream getOutputPrintStream(Scanner console) throws FileNotFoundException{
		PrintStream output = null;
		while (output == null) {
			System.out.println();
			System.out.print("Please enter the location and name of the output file: ");
			String outputName = console.nextLine();
			try {
				File fileName = new File(outputName);
				if (fileName.exists()) {
					@SuppressWarnings("resource")
					Scanner verify = new Scanner(System.in);
					System.out.print("Okay to overwrite file? (y/n): ");
					if (verify.next().charAt(0) == 'y') {
						output = new PrintStream(fileName);
					} else {
						System.out.println();
						System.out.println("You have elected not to overwrite the file. Please try again.");
					} 
				} else {
					output = new PrintStream(fileName);
				} 
					
			} catch (FileNotFoundException e) {
				System.out.println();
				System.out.println("Problem creating file:" + e + " Please try again.");
			}
		}
			return output;
	}
		
}
