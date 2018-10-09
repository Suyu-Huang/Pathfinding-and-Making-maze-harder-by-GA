package project1;

public class Point implements Comparable{
	int x;
	int y;
	double f;
	double g;
	double h;
	Point pre;
	
	public Point(int x,int y,Point pre){
		this.x = x;
		this.y = y;
		this.g = 0;
		this.h = 0;
		this.pre = pre;
	}
	
	public Point(int x,int y){
		this.x = x;
		this.y = y;
		this.g = 0;
		this.h = 0;
		this.pre = null;
	}
	
	public void Manhaton(int dim,double prevG){
		g = prevG+1;
		h = Math.abs(dim-1-x) + Math.abs(dim - 1-y);
		f = g + h;
	}
	
	public void Euclid(int dim,double prevG){
		g = prevG+1;
		h = Math.sqrt(Math.pow((dim-1-x), 2) + Math.pow((dim - 1-y), 2));
		f = g + h;
	}
	
	public double getF(){
		return this.f;
	}
	
	public Point getPoint(){
		return this.pre;
	}
	public double getG(){
		return this.g;
	}
	
	public double getH(){
		return this.h;
	}
	
	public int getI(){
		return this.x;
	}
	
	public int getJ(){
		return this.y;
	}
	
	public void setG(double g){
		this.g = g;
		this.f = this.g + this.h;
	}
	
	public void setPre(Point pre){
		this.pre = pre;
	}
	
	

	public int compareTo(Object object) {
		Point point = (Point) object;
		return (this.getF() < point.getF() ? -1 :
		 (this.getF() == point.getF() ? 0 : 1));		
	}
}

