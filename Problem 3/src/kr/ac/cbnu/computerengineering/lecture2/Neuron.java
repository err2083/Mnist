package kr.ac.cbnu.computerengineering.lecture2;

public class Neuron implements IActivateFunction{
	private double bias;
	private double lastV;
	private double lastD;
	private double[] lastX;
	private Weight weight;
	
	@Override
	public double sigmoid(double x) { //활성화 함수
        return 1. / (1. + Math.pow(Math.E, -x));
    }
	
	@Override
	public double sigmoid_derivative(double x) {	//활성화함수 미분
		double y = sigmoid(x);
		return y * (1-y);
	}
	public Neuron(int size) {	
		weight = new Weight(size);
		bias = -1;
	}
	public Weight getWeight() {
		return weight;
	}
	
	public double compute(double[] x) {
		if(x.length != weight.getW().length);
		
		double wx = 0.;
		for(int i=0;i<weight.getW().length;i++) {
			wx += weight.getWidx(i) * x[i];
		}
		lastV = wx + bias;
		lastX = x;
		return sigmoid(lastV);
	}
	
	public void train(double a,double[] data,int lavel) {
		int datalen = data.length;
		if(weight.getW().length != datalen);
		
		for(int i=0;i<datalen;i++) {
			double o = compute(data);
			double t = lavel;
			
			for(int j=0;j<datalen;j++) {
				weight.setplusW(j,a*sigmoid_derivative(lastV) * (t-o) * data[j]);
			}
			bias += a * sigmoid_derivative(lastV) * (t-o);
			
			lastD = sigmoid_derivative(lastV) * (t-o);
		}
	}
	
	public void train(double a,double e,double[] data) {
		int datalen = data.length;
		if(weight.getW().length != datalen) {
			System.out.println(datalen);
			System.out.println(weight.getW().length);
		}
		
		for(int i=0;i<datalen;i++) {
			weight.setplusW(i,a*sigmoid_derivative(lastV) * e * data[i]);
		}
		bias += a * sigmoid_derivative(lastV) * e;
		
		lastD = sigmoid_derivative(lastV) * e;
	}
	
	public double getlastV() {
		return lastV;
	}
	
	public double getlastD() {
		return lastD;
	}
	
	public double getbias() {
		return bias;
	}
	
	public double[] getlastX() {
		return lastX;
	}
}
