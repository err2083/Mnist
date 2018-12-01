package kr.ac.cbnu.computerengineering.lecture2;

import java.util.Random;

public class Weight {
	private double[] w;
	
	public int getWsize() {
		return w.length;
	}
	
	public double[] getW() {
		return w;
	}
	
	public void setW(double[] w) {
		this.w = w;
	}
	
	public double getWidx(int idx) {
		return w[idx];
	}
	public void setplusW(int idx,double set) {
		w[idx] += set;
	}
	
	public Weight(int size) {
		w = new double[size];
		for(int i=0;i<size;i++) {
			Random random = new Random();
			int sign = random.nextInt();
			if(sign % 2 == 0)
				w[i] = -1*random.nextDouble(); //·£´ýÀ¸·Î w ÃÊ±«È­
			else
				w[i] = random.nextDouble();
		}
	}
}
