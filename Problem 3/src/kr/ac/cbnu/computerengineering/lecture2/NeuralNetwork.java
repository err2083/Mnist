package kr.ac.cbnu.computerengineering.lecture2;

import java.math.BigDecimal;

/*
 * main �Ʒ��� �ֽ��ϴ�.
 */

public class NeuralNetwork implements IErrorFunction{
	private Layer[] layers;
	
	public NeuralNetwork(int[] layer){
		int len = layer.length;
		layers = new Layer[layer.length-1];
		for(int i=0;i<len-1;i++) {
			layers[i] = new Layer(layer[i+1],layer[i]); //layer[i] �� w �� ������ layer[i+1] ���� ���� layer����
		}
	}
	
	public double[] compute(double[] x) {
		if(x.length != layers[0].getneuron(0).getWeight().getWsize());
		double[] result = null;
		double[] nextlayer = x;
		
		for(int i=0;i<layers.length;i++) {
			result = new double[layers[i].getsize()];
			for(int j=0;j<layers[i].getsize();j++) {
				result[j] = layers[i].getneuron(j).compute(nextlayer);
			}
			nextlayer = result;
		}
		return result;
	}
	public void train(double a,DataSet dataset) {
		int len = dataset.getData().size();
		for(int i=0;i<len;i++) {
			double[] o = compute(dataset.getData().get(i).getTrain());
			double[] e = new double[o.length];
			
			if(o.length != dataset.getData().get(i).getlavel().length) {
				System.out.println("out");
			}
			
			for(int j=0;j<o.length;j++) {
				e[j] = dataset.getData().get(i).getlavel()[j] - o[j];
			}
			int len2 = layers[layers.length-1].getsize(); //last layers neuron size
			double[] d = new double[len2];
			for(int j=0;j<len2;j++) {
				layers[layers.length-1].getneuron(j).train(a, e[j],layers[layers.length-1].getneuron(j).getlastX());
				d[j] = layers[layers.length-1].getneuron(j).getlastD();
			}
			
			if(layers.length == 1) continue;
			//���� -
			int len3 = layers.length-2; //hidden layer size
			for(int j = len3;j>=0;j--) {
				int len4 = layers[j].getsize(); //layer j neuron size
				double[] new_d = new double[len4];
				for(int k=0;k<len4;k++) {
					int len5 = layers[j+1].getsize(); //next later neuron size
					double[] link_w = new double[len5];
					for(int n=0;n<len5;n++) {
						link_w[n] = layers[j+1].getneuron(n).getWeight().getWidx(k);
					}
					if(len5 != len2); //d and w size
					
					double e_hidden = 0.;
					for(int n=0;n<len5;n++) {
						e_hidden += link_w[n] * d[n];
					}
					
					layers[j].getneuron(k).train(a, e_hidden,layers[j].getneuron(k).getlastX());
					new_d[k] = layers[j].getneuron(k).getlastD();
				}
				
				if(j == 0) break;
				
				d = new_d;
			}
		}
	}
	

	/*
	 * �־��� ������ ���� Ư¡�����̳� �ռ��� ������ ���⿡ �����ۼ�Ʈ������ �����Ͽ����ϴ�.
	 * �ڵ带 �ۼ��ϱ����� ���θ� �ؾ��ؼ� �����ۼ�Ʈ�� �ڵ尡 �߰��� �ֽ��ϴ�. �� �κ��� ǥ�ø� �صξ����ϴ�.
	 * �׸��� ��͸� �̿��ؼ� �϶�� �κ��� ���κп��� ������� ���� ���� ���Ͽ���(RNN ����?)
	 * BigDecimal �� �̿��ؼ� �ϴºκ��� ����� ���ٰ� �����ߴµ� Ȱ��ȭ �Լ����� 1/1+e^-x �κ���
	 * BigDecimal �� ��ȯ������ϴµ� BigDecimal.pow ���� int ���� ���ڷ� �޾Ƽ� �̺κж����� ���Ͽ����ϴ�.
	 * ���ͳ��� ã�ƺ��ϱ� BigDecimalMath �� �ִٰ�� �ϴµ� �ܺζ󸮺귯�������� ���� ���⿡�� ��� ���� ���Ͽ����ϴ�.
	 * �׸��� �ʱ⿡ �����Լ��� ���� ��� ���ϴ��Ŀ����� ���� ����� ��ȭ�� ���� ������ �ֽ��ϴ�.
	 * �������� �ֽ� ��Ʈ���ڵ带 �Ȱ��� {784,500,3} ���� ������ 30���� �����͸� �ְ� ������ �����԰� ���� ����� ������
	 * {784,500,400,10} ���� ������ �ð������� �����ɷ��� 100���н��� ������ ���� �νķ��� �����ϴ°��� �����ִµ�, 90��° �� 95��° �н�����
	 * ��ȭ���� ���� �κ��� �ֽ��ϴ�. �� �κи� �׷����� �ƴϸ� ���ֽ� ������ ���� ����ƿ� �����̳� ������� ������ �Ἥ �߾���ϴ� ������������ �� �𸣰ڽ��ϴ�.
	 * (���� ÷���� result.txt �� ���÷���Ͽ����ϴ�.)
	 * 
	 */
    public static void main(String[] args) {
    	int[] layer = {784,500,400,10};
		NeuralNetwork nn = new NeuralNetwork(layer);
		DataSet my_data = new DataSet("C:\\Users\\user\\eclipse-workspace\\Problem 3\\mnistdataset.txt",layer[0],layer[layer.length-1]);
		System.out.println("���� �ҷ����� �Ϸ�");
		int[] reality = new int[my_data.getData().size()];	//������
		for(int i=0;i<my_data.getData().size();i++) {
			reality[i] = my_data.getData().get(i).gettest();
		}
		System.out.println("�н� ����");
		for(int i=1;i<=100;i++) {
			nn.train(0.01, my_data);
			System.out.println(i+"���� �н��� �Ϸ��߽��ϴ�.");
			if(i % 5 == 0) {
				int[] predict = new int[my_data.getData().size()];	//������
				for(int j=0;j<my_data.getData().size();j++) {
					int maxlavel = nn.Maxpredict(nn.compute(my_data.getData().get(j).getTrain()));
					predict[j] = maxlavel;
				}
				nn.Matrix(i,predict,reality,layer[layer.length-1]);
			}
		}
    }
	
	
	@Override
	public void Matrix(int iter,int[] predict,int[] reality,int n) {
		int matrix[][] = new int[n][n];
		
		int len = predict.length;
		for(int i=0;i<len;i++) {
			matrix[predict[i]][reality[i]]++;
		}
		
		int good = 0;
		for(int i=0;i<n;i++) {
			good += matrix[i][i];
		}
		int bad = len-good;
		
		BigDecimal correct = new BigDecimal(good);
		BigDecimal incorrect = new BigDecimal(bad);
		BigDecimal recognition = correct.divide(new BigDecimal(len),50,BigDecimal.ROUND_UP);
		BigDecimal error = incorrect.divide(new BigDecimal(len),50,BigDecimal.ROUND_UP);
		
		System.out.printf("iter:%d,error:",iter);
		System.out.print(error);
		System.out.printf(",recognition:");
		System.out.print(recognition);
		System.out.println();
	}
    
	@Override
	public int Maxpredict(double[] compute) {
		int len = compute.length;
		double maxvalue = 0.;
		int maxidx = 0;
		for(int i=0;i<len;i++) {
			if(compute[i] > maxvalue) {
				maxvalue = compute[i];
				maxidx = i;
			}
		}
		return maxidx;
	}	
}
