package project1;

import java.util.Stack;

public class DFS {
	private int[][] maze;
	
	
	public DFS(int dim){
		this.maze = createMaze(dim);
	}
	
	public int dfsByStack(int dim){
		Stack<Point> stack = new Stack<>();
		stack.push(new Point(0, 0));
		boolean[][] visited = new boolean[dim][dim];
		boolean[][] openList = new boolean[dim][dim];
		int steps = 0;
		int[][] moveVector = {{0,-1},{-1,0},{0,1},{1,0}};
		boolean found = false;
		// stack size
		//int maxSize = 1;
		
		
		openList[0][0] = true;
		for(;!stack.isEmpty();){
			//maxSize = Math.max(maxSize, stack.size());
			Point current = stack.peek();
			int i = current.getI();
			int j = current.getJ();
			
			if(!visited[i][j]){
				visited[i][j] = true;
				if(i > maze.length-1 || j > maze.length-1 || i < 0 || j < 0 || maze[i][j] == 1){
					stack.pop();
					continue;
				}
				else {				
					//maze[i][j] = 2;
					steps++;
					// check destination
					if(i == dim-1 && j == dim - 1){
						found = true;
						break;
					}
				}
			}		
			
			boolean doBackTrack = true;
			for(int count=0; count<4; count++){
				int nexti = i+moveVector[count][0];
				int nextj =  j+moveVector[count][1];
				Point nextPoint = new Point(nexti,nextj);
				if(stack.contains(nextPoint))
					System.out.println("contains i: "+nexti +" j:"+nextj);
				if(checkBoundary(nexti, nextj, maze) 
						&& !visited[nexti][nextj] && !openList[nexti][nextj])
				{
					doBackTrack = false;
					if(maze[nexti][nextj] == 3)
						System.out.println("Obeject found i:"+ nexti +" j:"+nextj);
					stack.push(new Point(nexti,nextj));
					openList[nexti][nextj] = true;
				}
			}
			
			if(doBackTrack){
				//maze[i][j] = 3;
				steps--;
//				System.out.println("push i:"+i +"  j:"+j);
				stack.pop();
			}		
		}
		if(!found)
			return -1;
		else {
			return steps;
		}
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
	
	public int[][] drawMaze(int dim){
		Stack<Point> stack = new Stack<>();
		stack.push(new Point(0, 0));
		boolean[][] visited = new boolean[dim][dim];
		boolean[][] openList = new boolean[dim][dim];
		int steps = 0;
		int[][] moveVector = {{0,-1},{-1,0},{0,1},{1,0}};
		boolean found = false;
		// stack size
		//int maxSize = 1;
		
		int[][] resultMaze  = new int[dim][dim];
		resultMaze = copyArray(maze, resultMaze);
		openList[0][0] = true;
		for(;!stack.isEmpty();){
			//maxSize = Math.max(maxSize, stack.size());
			Point current = stack.peek();
			int i = current.getI();
			int j = current.getJ();
			
			if(!visited[i][j]){
				visited[i][j] = true;
				if(i > maze.length-1 || j > maze.length-1 || i < 0 || j < 0 || maze[i][j] == 1){
					stack.pop();
					continue;
				}
				else {				
					resultMaze[i][j] = 2;
					steps++;
					// check destination
					if(i == dim-1 && j == dim - 1){
						found = true;
						break;
					}
				}
			}		
			
			boolean doBackTrack = true;
			for(int count=0; count<4; count++){
				int nexti = i+moveVector[count][0];
				int nextj =  j+moveVector[count][1];
				Point nextPoint = new Point(nexti,nextj);
				if(stack.contains(nextPoint))
					System.out.println("contains i: "+nexti +" j:"+nextj);
				if(checkBoundary(nexti, nextj, maze) 
						&& !visited[nexti][nextj] && !openList[nexti][nextj])
				{
					doBackTrack = false;
					if(maze[nexti][nextj] == 3)
						System.out.println("Obeject found i:"+ nexti +" j:"+nextj);
					stack.push(new Point(nexti,nextj));
					openList[nexti][nextj] = true;
				}
			}
			
			if(doBackTrack){
				resultMaze[i][j] = 3;
				steps--;
//				System.out.println("push i:"+i +"  j:"+j);
				stack.pop();
			}		
		}
		 return resultMaze;
	}
	
	public int[][] createMaze(int n){
		int[][] maze = new int[n][n];
//		visited = new boolean[n][n];
		
		double rand = Math.random();
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				rand = Math.random();
				if(rand<0.1)
					maze[i][j] = 1;
			}
		}
		maze[0][0] = 0;
		maze[n-1][n-1] = 0;
		
		return maze;
	}
	
	public boolean checkBoundary(int i,int j,int[][] maze){	
		if(i > maze.length-1 || j > maze.length-1 || i < 0 || j < 0)
			return false;
		
		return true;
	}
}
