package kr.ac.cbnu.computerengineering.lecture2;

public class Data {
	private double[] train;
	private double[] vlavel; //벡터 라벨
	private int lavel;	//테스트용도 라벨
	public Data(double[] train,double[] vlavel,int lavel) {
		this.train = train;
		this.vlavel = vlavel;
		this.lavel = lavel;
	}
	public double[] getTrain() {
		return train;
	}
	public double[] getlavel() {
		return vlavel;
	}
	public int gettest() {
		return lavel;
	}
	
}
