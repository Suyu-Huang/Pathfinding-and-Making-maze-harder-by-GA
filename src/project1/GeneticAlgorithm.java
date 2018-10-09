package project1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {
	
	public void harderMaze(int dim,int mazeNum){
		int[] evaluate = new int[mazeNum];
		int[][][] mazes = new int[mazeNum][dim][dim];
		Astar_Euclid astartE ;
		// 1. generate mazes
		for(int i = 0; i < mazeNum; i++){
			int[][] tempMaze = createMaze(dim);
			astartE = new Astar_Euclid(dim, tempMaze);
			evaluate[i] = astartE.steps(new Point(0, 0), new Point(dim-1, dim-1));
			
			//evaluate[i] = dfsByStack_Node(dim, tempMaze);
			while(evaluate[i] == -1){
				tempMaze = createMaze(dim);
				//evaluate[i] = dfsByStack_Node(dim, tempMaze);
				astartE = new Astar_Euclid(dim, tempMaze);
				evaluate[i] = astartE.steps(new Point(0, 0), new Point(dim-1, dim-1));
			}
			mazes[i] = tempMaze;
		}
		

		
		for(int loopTime = 0; loopTime < 300; loopTime++){
			System.out.println("=======================================");
			System.out.println("This is " + loopTime + "th");

				
			
			// 2.Select
			double sum = 0;
			for(int e : evaluate)
				sum += e;	
			double[] selectRatio = new double[mazeNum];
			selectRatio[0] = evaluate[0] / sum;	
			for(int i = 1; i < mazeNum; i++){		
				selectRatio[i] = selectRatio[i-1] + evaluate[i] / sum;
			}
			
			// random number to select from 0 to 1
			double random;
			int[][][] newMazes = new int[mazeNum][dim][dim];
			for(int i = 0; i < mazeNum; i++){
				random = Math.random();
				for(int select = 0; select < mazeNum; select++){
					if(random <= selectRatio[select]){
						//System.out.println("select "+select);
						//newMazes[i] = mazes[select].clone();
						copyArray(mazes[i], newMazes[i]);
						break;
					}
				}
			}
			
			
			// 3.Cross Over		
			Random rand = new Random();
			int randRow = rand.nextInt(dim);
			
			int mazeIndex = 0;
			while(mazeIndex < mazeNum){
				for(int i = randRow; i < dim; i++){
					for(int j = 0; j < dim; j++){
						int temp = 0;
						temp = newMazes[mazeIndex][i][j];
						newMazes[mazeIndex][i][j] = newMazes[mazeIndex+1][i][j];
						newMazes[mazeIndex+1][i][j] = temp;
					}
				}
				mazeIndex += 2;
			}
			
			
			// 4. Mutation
			
			// random mutation times			
			Random randTime = new Random();
			int mutationTimes = dim/5;
			for(int i = 0; i < mazeNum; i++){
				for(int count = 0; count <= mutationTimes; count++){
					int row = randTime.nextInt(dim);
					int col = randTime.nextInt(dim);
					//System.out.println(i+"th maze: randome row:"+ row+ " col:"+col);
					while(row == 0 && col == 0){
						 row = randTime.nextInt(dim);
						 col = randTime.nextInt(dim);
					}
					
					while(row == dim-1 && col == dim-1){
						 row = randTime.nextInt(dim);
						 col = randTime.nextInt(dim);
					}
					if(newMazes[i][row][col] == 1)
						newMazes[i][row][col] = 0;
					if(newMazes[i][row][col] == 0)
						newMazes[i][row][col] = 1;
				}
				//System.out.println("after mutation maze 0:"+i+" eva:"+dfsByStack(dim, newMazes[i]));
				
			}
			

			

			// 5. Check if every maze has path from start to end.
			//System.out.println("after mutation 1 maze 0:" +" eva:"+dfsByStack(dim, newMazes[0]));
			int[] newEvaluate = new int[mazeNum];
			for(int i = 0; i < mazeNum; i++){
				//newEvaluate[i] = dfsByStack_Node(dim, newMazes[i]);
				astartE = new Astar_Euclid(dim, newMazes[i]);
				newEvaluate[i] = astartE.steps(new Point(0, 0), new Point(dim-1, dim-1));
//				System.out.println("Maze:" + i +" before:" + evaluate[i]);
//				System.out.println("Maze:" + i +" after:" + newEvaluate[i]);
				//System.out.println("after mutation 3 Mazes:"+i+" eva:"+newEvaluate[i]);
				if(newEvaluate[i] == -1){
					//System.out.println("Maze:" + i + " no path ");
					
					//newMazes[i] = mazes[i].clone();
				
					copyArray(mazes[i], newMazes[i]);
					//System.out.println("update eval:"+dfsByStack(dim, newMazes[i]));
					newEvaluate[i] = evaluate[i];
				}
				// 6. ready for iterate
				//mazes[i] = newMazes[i].clone();
				copyArray(newMazes[i], mazes[i]);
				evaluate[i] = newEvaluate[i];
					
				//System.out.println("Maze:" + i + "after: " + evaluate[i]);
				
				
			}
			int total = 0;
			for(int e : evaluate)
				total += e;
			System.out.println("average  " + "after: " + total);
			writeDoc(loopTime, evaluate);	
		}
		
		printMaze(mazes[evaluate.length/2]);
		
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
	
	public void writeDoc(int nth,int[] eval) {
		double average;
		double median;
		double maxVal = 0;
		double minVal = 10000000;
		int mazeNum = eval.length;
		double sum = 0;
		for(int e : eval){
			sum += e;
			maxVal = Math.max(e, maxVal);
			minVal = Math.min(e, minVal);
		}
		average = sum / mazeNum;
		Arrays.sort(eval);
		median = eval[mazeNum / 2];
		
		 try{
	            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("cs520text001.txt"),true));
	            writer.write("================= THIS IS THE " + nth + " ITERATION\n");
	            writer.write("  Total "+mazeNum+" of mazes\n");
	            writer.write("  Average: "+average+" Median: "+median +" MAX: " +maxVal+" MIN: "+minVal +"\n");
	            writer.close();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	}
	
	
	public void printMaze(int[][] printMaze){			
		try{
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File("finalMaze.txt"),true));
				for(int i = 0; i < printMaze.length; i++){
					writer.write("{");
					for(int j = 0; j < printMaze[0].length; j++){
						if(j == printMaze[0].length-1)
							writer.write(printMaze[i][j]+" ");
						else {
							writer.write(printMaze[i][j]+",");
						}
					}
					if(i == printMaze.length-1)
						writer.write("}");
					else {
						writer.write("},\n");
					}
					
				}
				writer.close();
		 }catch(Exception e){
		     e.printStackTrace();
		 }
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
}
