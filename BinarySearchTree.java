import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;
@SuppressWarnings("unchecked")

class BinarySearchTree<T extends Comparable<T>> {
	
	Node<T> rootNode;
	int index = 0;
	int level = 0;
	int searchTime;
	ArrayList<T> preOrder = new ArrayList();
	ArrayList<T> postOrder = new ArrayList();
	ArrayList<T> inOrder = new ArrayList();
	
	public void insertElement(T element) {
		Node<T> newNode = new Node<T>();
		newNode.setElement(element);
		if (rootNode == null) {
			rootNode = newNode;
			updateLevel(level);		
		} else {
			Node<T> traverseNode = rootNode;
			Node<T> root;
			while (true) {
				root = traverseNode;
				if (traverseNode.getElement().compareTo(newNode.getElement()) > 0) {
					traverseNode = traverseNode.getLeftNode();
					if (traverseNode == null) {
				 		root.setLeftNode(newNode);
				 		break;
				 	}
				} else {
			 		traverseNode = traverseNode.getRightNode();
			 		if (traverseNode == null) {
			 			root.setRightNode(newNode);
			 			traverseNode = root.getLeftNode();
			 			break;
				 	}
			 	}
			}
		}
		index++; 
	}

	public void updateLevel(int presentLevel) {
		if (index == ((2^level) - 1)) {
			level++;
		}
	}

	public void preOrderTraversel(Node<T> root) {
		if (root != null) {
			preOrder.add(root.getElement());
			preOrderTraversel(root.getLeftNode());
			preOrderTraversel(root.getRightNode());
		}
	}
	
	public void postOrderTraversel(Node<T> root) {
		if (root != null) {
			postOrderTraversel(root.getLeftNode());
			postOrderTraversel(root.getRightNode());
			postOrder.add(root.getElement());
		}		
	}

	public void inOrderTraversel(Node<T> root) {
		if (root != null) {
			inOrderTraversel(root.getLeftNode());
			inOrder.add(root.getElement());
			inOrderTraversel(root.getRightNode());
		}
	}
	public void printAllNodes(ArrayList<T> a) {
		for (int i = 0; i < a.size() - 1; i++) {
			System.out.print(a.get(i) + ",");
		}
		if (a.size() != 0) {
			System.out.println(a.get(a.size() - 1));
		} else {
			System.out.println("0");
		}
		preOrder = new ArrayList<T>();
		postOrder = new ArrayList<T>();
		inOrder = new ArrayList<T>();
	}
	public boolean searchElement(T element) {
		searchTime = 1;
		Node<T> node = rootNode;
		if (rootNode == null) {
			return false;
		}
		while (node != null && !node.getElement().equals(element)){
			if (node.getElement().compareTo(element) > 0) {
				node = node.getLeftNode();
				searchTime++;
			} else {
				node = node.getRightNode();
				searchTime++;
			}
			if (node == null) {
				return false;
			}
		}
		return true;
	}

	public T getNextHighest(T element) {
		this.inOrderTraversel(rootNode);
		int i = 0;
		T nextHighest = null;
		for (i = 0; i < inOrder.size(); i++) {
			if (inOrder.get(i).compareTo(element) > 0) {
				nextHighest = inOrder.get(i);
				break;
			}
		}
		inOrder = new ArrayList<T>();
		return nextHighest;
	}

	public Node<T> getParent(Node<T> node1) {
		Node<T> node = rootNode;
		Node<T> refNode = null;
		if (rootNode == null) {
			return null;
		} else {
			while (!node.getElement().equals(node1.getElement())) {
				if (node.getElement().compareTo(node1.getElement()) > 0) {
					refNode = node;
					node = node.getLeftNode();
				} else {
					refNode = node;
					node = node.getRightNode();
				}
				if (node == null) {
					break;
				}
			}
		}
		return refNode;
	}
	
	public Node<T> getPurticularNode(T element) {
		Node<T> node = rootNode;
		Node<T> refNode = null;
		while (!node.getElement().equals(element)){
			if (node.getElement().compareTo(element) > 0) {
				refNode = node;
				node = node.getLeftNode();
			} else {
				refNode = node;
				node = node.getRightNode();
			}
			if (node == null) {
				break;
			}
		}
		return node;
	}

	public boolean isLeafNode(Node<T> node) {
		if (node.getRightNode() != null || node.getLeftNode() != null) {
			return false;
		}
		return true;
	}

	public void removeElement(T element) {
		Node<T> node = getPurticularNode(element);
		if (node != null) {
		Node<T> refNode = null;
		if (getParent(node) != null) {
			refNode = getParent(node);
		}

		if (isLeafNode(node)) {
			if (node == rootNode) {
				rootNode = null;
			} else {
				if (refNode.getLeftNode() == node && refNode.getLeftNode() != null) {
					refNode.setLeftNode(null);
				} else {
					refNode.setRightNode(null);
				}
			}
		} else {
			Node<T> precedingNode;
			Node<T> leftNode =node.getLeftNode();
			
			if (leftNode != null) {
				precedingNode = getPrecedingRightNode(leftNode);		
			} else {
				precedingNode = getPrecedingLeftNode(node.getRightNode());		
			}

			Node<T> parentNode = getParent(precedingNode);
			
			if (precedingNode.getLeftNode() != null ) {
				parentNode.setRightNode(precedingNode.getLeftNode());
				precedingNode.setLeftNode(null);		
			}
			if (precedingNode == parentNode.getLeftNode()) {
				parentNode.setLeftNode(null);	
			} else {
				parentNode.setRightNode(null);
			}
			
			if (node == rootNode) {
				precedingNode.setLeftNode(rootNode.getLeftNode());
				precedingNode.setRightNode(rootNode.getRightNode());
				rootNode = precedingNode;
			} else {
				
				if (refNode == rootNode) {
					if (rootNode.getLeftNode() == node && rootNode.getLeftNode() != null) {
						rootNode.setLeftNode(precedingNode);
					} else {
						rootNode.setRightNode(precedingNode);
					}
					
					if (node.getLeftNode() != null && node.getLeftNode() != precedingNode) {
						precedingNode.setLeftNode(node.getLeftNode());
					}
					if (node.getRightNode() != null && node.getRightNode() != precedingNode) {
						precedingNode.setRightNode(node.getRightNode());
					}					
					node = precedingNode;
						System.out.println(node.getRightNode().getLeftNode());
				} else {
					if (refNode.getRightNode() != null && refNode.getRightNode() == node) {						
						refNode.setRightNode(precedingNode);					
					} else {					
						refNode.setLeftNode(precedingNode);					
					}
				} 

				if (node.getLeftNode() != null && node.getLeftNode() != precedingNode) {
					precedingNode.setLeftNode(node.getLeftNode());
				} 
				if (node.getRightNode() != null && node.getRightNode() != precedingNode) {
					precedingNode.setRightNode(node.getRightNode());
				}
				node.setRightNode(null);
				node.setLeftNode(null);
				node = precedingNode;
			}
		}
	}

	}
	
	public Node<T> getPrecedingLeftNode(Node<T> node) {
		if (node.getRightNode() != null) {
			node = getPrecedingLeftNode(node.getRightNode());
		}
		return node;
	}

	public Node<T> getPrecedingRightNode(Node<T> node) {
		if(node != null) {
			if (node.getRightNode() != null) {
				node = getPrecedingRightNode(node.getRightNode());
			}
		}
		return node;
	}

	public void setParent(Node<T> node1, Node<T> node) {
		Node<T> parentNode = getParent(node1);
		parentNode = node;
	}

	public static void main(String[] args) {
		BinarySearchTree<Integer> bt = new BinarySearchTree<Integer>();
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		StringTokenizer st = new StringTokenizer(s, ",");
		while(st.hasMoreTokens()) {
			String s1 = st.nextToken();
			String[] s2 = s1.split(" ");
			if (s2[0].equalsIgnoreCase("I")) {
				bt.insertElement(Integer.parseInt(s2[1]));
				bt.inOrderTraversel(bt.rootNode);
				bt.printAllNodes(bt.inOrder);
			}
			if (s2[0].equalsIgnoreCase("S")) {
				if (bt.searchElement(Integer.parseInt(s2[1]))) {
					System.out.print(bt.searchElement(Integer.parseInt(s2[1])) + ",");
					System.out.println(bt.searchTime);
				} else {
					System.out.print(bt.searchElement(Integer.parseInt(s2[1])) + ",");
					if (bt.getNextHighest(Integer.parseInt(s2[1])) != null) {
						System.out.println(bt.getNextHighest(Integer.parseInt(s2[1])));						
					} else {
						System.out.println("0");
					}
				}
			}
			if (s2[0].equalsIgnoreCase("R")) {
				bt.removeElement(Integer.parseInt(s2[1]));
				bt.postOrderTraversel(bt.rootNode);
				bt.printAllNodes(bt.postOrder);
			}
			if (s2[0].equalsIgnoreCase("end")) {
				break;
			}
		}
	// 	bt.insertElement(18);
	// 	// bt.inOrderTraversel(bt.rootNode);
	// 	// bt.printAllNodes(bt.inOrder);
	// 	bt.insertElement(16);
	// 	// bt.inOrderTraversel(bt.rootNode);
	// 	// bt.printAllNodes(bt.inOrder);
	// 	bt.insertElement(17);
	// 	// bt.inOrderTraversel(bt.rootNode);
	// 	// bt.printAllNodes(bt.inOrder);
	// 	bt.insertElement(13);
	// 	// bt.inOrderTraversel(bt.rootNode);
	// 	// bt.printAllNodes(bt.inOrder);
	// 	bt.insertElement(10);
	// 	// bt.inOrderTraversel(bt.rootNode);
	// 	// bt.printAllNodes(bt.inOrder);
	// 	bt.insertElement(14);
	// 	bt.insertElement(15);
	// 	bt.insertElement(12);
	// 	bt.insertElement(9);
	// 	bt.inOrderTraversel(bt.rootNode);
	// 	bt.printAllNodes(bt.inOrder);
	// 	bt.removeElement(12);
	// 	bt.inOrderTraversel(bt.rootNode);
	// 	bt.printAllNodes(bt.inOrder);
	// 	System.out.println();
	// 	System.out.print(bt.searchElement(15) + ",");
	// 	System.out.println(bt.searchTime);
	}
}