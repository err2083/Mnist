package kr.ac.cbnu.computerengineering.lecture2;

public class Data {
	private double[] train;
	private double[] vlavel; //���� ��
	private int lavel;	//�׽�Ʈ�뵵 ��
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
