package jason.smythe.uct.csc2003.desktop.maps;

import java.util.PriorityQueue;
import java.util.Stack;

import jason.smythe.uct.csc2003.constants.Movement;
import jason.smythe.uct.csc2003.constants.SquareType;
import jason.smythe.uct.csc2003.path_finding.AStarNode;
import jason.smythe.uct.csc2003.states.AStarSquareResultState;
import jason.smythe.uct.csc2003.util.Square;

public class MapGrid {
	public AStarNode[][] mapGrid;
	public Square playerPosition;
	public Stack<AStarNode> listToClear;
	public Stack<AStarNode> clearOldUse;
	
	public MapGrid(Map map) {
		
		mapGrid = new AStarNode[17][13];
			
		System.out.println("creating the map grid.");
		for (int x = 0;x < 17; x++ ) {
			for (int y = 0; y < 13; y++ ) {
				Square square = new Square(x, y);
				mapGrid[x][y] = new AStarNode(square, map.getSquareType(square));
//				System.out.println(mapGrid[x][y].square + ":" + mapGrid[x][y].squareType + "; ");
			}
		}
		
		listToClear = new Stack<AStarNode>();
		clearOldUse = new Stack<AStarNode>();
//		printMapGrid();
	}
	
	public AStarSquareResultState[] findShortestPath(AStarNode enemy, AStarNode playerNode) {
		
//		System.out.println("Calculating shortest path");
		
		//clear previous changes so they don't effect results
		clearAllNodes();
		
		PriorityQueue<AStarNode> openList = new PriorityQueue<AStarNode>();
		enemy.setCostsAndPrevious(enemy, playerNode, Movement.UP); //note: the direction UP is arbitrary, it just cannot be STATIONARY
		addToLists(enemy, openList);
		enemy.g = 0;
		
		//THE CORE OF THE A* ALGORITHM
		AStarNode currentNode = null;
		while(!openList.isEmpty()) {
			currentNode = openList.remove();
//			System.out.println("still searching: current node: " + currentNode);
			
			if (currentNode == playerNode) { //here this kind of equality test is acceptable, as we are taking two nodes from the same mapGrid
//				System.out.println("path complete!!!!");
				break;
			} else {
				getConnectedNodesAndCosts(currentNode, playerNode, openList);
			}
		}
		
		return createResultArray(playerNode);
//		return null;
	}
	
	private AStarSquareResultState[] createResultArray(AStarNode currentNode) {
		int sizeOfResults = currentNode.g;
		
		AStarSquareResultState[] resultsArray = new AStarSquareResultState[sizeOfResults];
		
		while (currentNode != currentNode.previous) { //I set the flag for the root/first note as a node that references itself
			sizeOfResults--; //we count down as we go backwards through the previous nodes from the target
//			System.out.println("Creating results: index:" + sizeOfResults + " square:" + currentNode.square);
			resultsArray[sizeOfResults] = currentNode.returnResultForm();
			
			currentNode.incrementBreadCrumbWeight();

			currentNode = currentNode.previous;//iterate backwards through nodes
		}
		
		return resultsArray;
	}

	public void clearAllBreadCrumbs() {
		while(!clearOldUse.isEmpty()){
			clearOldUse.pop().resetBreadCrumbs();
		}
	}
	
	private void clearAllNodes() {
		while(!listToClear.isEmpty()){
			listToClear.pop().reset();
		}
	}

	private PriorityQueue<AStarNode> getConnectedNodesAndCosts(AStarNode node, AStarNode target, PriorityQueue<AStarNode> openList) {

		//above node available.
		if (node.squareType == SquareType.BLT || node.squareType == SquareType.BLTR) {
//			connectedNodes.
			AStarNode top = mapGrid[node.square.x][node.square.y + 1];
			if (top.moveDirectionTo == Movement.STATIONARY){ 
				
				//TODO : add direction too.
				top.setCostsAndPrevious(node, target, Movement.UP);
//				System.out.println("added top: " + top);
				addToLists(top, openList);
			}
		} 
		//right node available.
		if (node.squareType == SquareType.BLR || node.squareType == SquareType.BLTR) {
			AStarNode right = mapGrid[node.square.x + 1][node.square.y];
			if (right.moveDirectionTo == Movement.STATIONARY){ 
				right.setCostsAndPrevious(node, target, Movement.RIGHT);
//				System.out.println("added right: " + right);
				addToLists(right, openList);
			}
		} 
		//bottom node available
		if (node.square.y > 0 && 
				(mapGrid[node.square.x][node.square.y - 1].squareType == SquareType.BLT 
				|| mapGrid[node.square.x][node.square.y - 1].squareType == SquareType.BLTR) ) {
//			connectedNodes.
			AStarNode bottom = mapGrid[node.square.x][node.square.y - 1];
			if (bottom.moveDirectionTo == Movement.STATIONARY){ 
				bottom.setCostsAndPrevious(node, target, Movement.DOWN);
//				System.out.println("added bottom: " + bottom);
				addToLists(bottom, openList);
			}
		} 
		//left node available
		if (node.square.x > 0 && 
				(mapGrid[node.square.x - 1][node.square.y].squareType == SquareType.BLR 
				|| mapGrid[node.square.x - 1][node.square.y].squareType == SquareType.BLTR) ) {
			AStarNode left = mapGrid[node.square.x - 1][node.square.y];
			if (left.moveDirectionTo == Movement.STATIONARY){ 
				left.setCostsAndPrevious(node, target, Movement.LEFT);
//				System.out.println("added left: " + left);
				addToLists(left, openList);
			}
		} 
		
		return openList;
	}
	
	private void addToLists(AStarNode node, PriorityQueue<AStarNode> openList) {
		openList.add(node);
		listToClear.add(node);
		clearOldUse.add(node);
	}
	
	public void printMapGrid(){
		for (int x = 0;x < 17; x++ ) {
			for (int y = 0; y < 13; y++ ) {
				System.out.print(mapGrid[x][y].square + ":" + mapGrid[x][y].squareType + "; ");
			}
			System.out.println();
		}
	}
}