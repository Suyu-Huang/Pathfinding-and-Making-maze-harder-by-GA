package project1;





import java.util.ArrayList;
import java.util.Collections;

import javax.xml.ws.Endpoint;

public class Astar_Euclid {
	private ArrayList<Point> openList = new ArrayList<>();
    private ArrayList<Point> closeList = new ArrayList<>();
    private int dim;
    private int[][] maze;
    private int[][] moveVector = {{0,-1},{-1,0},{0,1},{1,0}};
    private int maxNode;
    private int nodeExpand;
    
    public Astar_Euclid(int dim,int[][] mazeO){
    	this.dim = dim;
    	maze = new int[dim][dim];
    	this.maze = copyArray(mazeO, this.maze);
    	this.maxNode = 0;
    	this.nodeExpand = 0;
    }
    
    public int steps(Point startPoint,Point endPoint){
    	Point end = findPath(startPoint, endPoint);
    	int step = 0;
   
    	while(end != null){
    		step++;
    		end = end.getPoint();
    	}
    	
    	if(step == 0)
    		return -1;
    	return step;
    }
    
    
    public int nodesExpand(Point startPoint,Point endPoint) {
    	Point end = findPath(startPoint, endPoint);
    	if(end == null)
    		return -1;
    	
    	return nodeExpand;
	}
    
    public int maxNodesInList(Point startPoint,Point endPoint){
    	Point end = findPath(startPoint, endPoint);
    	if(end == null)
    		return -1;
    	
    	return maxNode;
    	
    }
    
    public int[][] drawMaze(Point startPoint,Point endPoint){
    	Point end = findPath(startPoint, endPoint);
    	
    	int[][] result =new int[dim][dim];
    	copyArray(maze, result);
    	while(end != null){
    		result[end.getI()][end.getJ()] = 2;
    		end = end.getPoint();
    	}
    	
    	return result;
    }
    
    public Point findPath(Point startPoint,Point endPoint){
    	openList.add(startPoint);
    	
    	
    	while(!openList.isEmpty()){
    		Point currentPoint = openList.get(0);
    		openList.remove(0);
    		closeList.add(currentPoint);
    		
    		ArrayList<Point> nextMove = nextMove(currentPoint);
    		for(Point nextPoint : nextMove){
    			if(exist(openList, nextPoint)){
    				for(Point foundPoint : openList){
    					if(foundPoint.getI() == nextPoint.getI() && foundPoint.getJ() == nextPoint.getJ()){
    						if(foundPoint.getG() > nextPoint.getG()){
    							foundPoint.setG(nextPoint.getG());
    							foundPoint.setPre(currentPoint);
    							foundPoint.Euclid(dim, currentPoint.getG());
    						}
    					}
    				}
    			}
    			else {
    				openList.add(nextPoint);
    			}
    		}
    		
    		maxNode = Math.max(openList.size(), maxNode);
    		
    		if(findEnd(openList, endPoint) != null){
    			return findEnd(openList, endPoint);
    		}
    		
    		Collections.sort(openList);
    	}
    	
    	return findEnd(openList, endPoint);
    }
    
    public Point findEnd(ArrayList<Point> list, Point p){
    	for(Point pointInList : list){
    		if(pointInList.getI() == p.getI() && pointInList.getJ() == p.getJ()){
    			return pointInList;
    		}
    	}
    	
    	return null;
    }
    
    public ArrayList<Point> nextMove(Point currentPoint){
    	ArrayList<Point> nextMoves = new ArrayList<>();
    	int i = currentPoint.getI();
    	int j = currentPoint.getJ();
    	
    	for(int nth=0;nth<4;nth++){
			Point nextChoice = new Point(i+moveVector[nth][0], j+moveVector[nth][1],currentPoint);
			if(boundaryCheck(nextChoice) && !exist(closeList, nextChoice)){
				nextChoice.Euclid(dim, currentPoint.getG());
				nextMoves.add(nextChoice);
				nodeExpand++;
			}
    	}
    	
    	return nextMoves;
    }
    
    public boolean boundaryCheck(Point currentPoint){
    	int i = currentPoint.getI();
    	int j = currentPoint.getJ();
    	
    	if(i < 0 || j < 0 || i > dim-1 || j > dim -1 )
    		return false;
    	
    	if(maze[i][j] == 1)
    		return false;
    	
    	return true;
    }
    
    public int[][] copyArray(int[][] origin,int[][] target){
		int len = origin.length;
		for(int row = 0; row < len;row++){
			for(int col = 0; col < len; col++){
				target[row][col] = origin[row][col];
			}
		}
		
		return target;
	}
    
    public boolean exist(ArrayList<Point> list,Point p){
    	
    	for(Point pointInList : list){
    		if(pointInList.getI() == p.getI() && pointInList.getJ() == p.getJ()){
    			return true;
    		}
    	}
    	
    	return false;
    }
}
