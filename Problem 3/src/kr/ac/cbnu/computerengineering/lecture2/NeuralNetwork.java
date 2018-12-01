package kr.ac.cbnu.computerengineering.lecture2;

import java.math.BigDecimal;

/*
 * main 아래에 있습니다.
 */

public class NeuralNetwork implements IErrorFunction{
	private Layer[] layers;
	
	public NeuralNetwork(int[] layer){
		int len = layer.length;
		layers = new Layer[layer.length-1];
		for(int i=0;i<len-1;i++) {
			layers[i] = new Layer(layer[i+1],layer[i]); //layer[i] 의 w 를 가지는 layer[i+1] 개의 뉴런 layer생성
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
			//은닉 -
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
	 * 주어지 과제에 보면 특징추출이나 합성곱 개념이 없기에 다층퍼셉트론으로 구현하였습니다.
	 * 코드를 작성하기전에 공부를 해야해서 단일퍼셉트론 코드가 중간에 있습니다. 그 부분은 표시를 해두었습니다.
	 * 그리고 재귀를 이용해서 하라는 부분은 어디부분에서 써야할지 몰라서 하지 못하였고(RNN 개념?)
	 * BigDecimal 을 이용해서 하는부분은 상당히 쉽다고 생각했는데 활성화 함수에서 1/1+e^-x 부분을
	 * BigDecimal 로 반환해줘야하는데 BigDecimal.pow 에서 int 형만 인자로 받아서 이부분때문에 못하였습니다.
	 * 인터넷을 찾아보니까 BigDecimalMath 가 있다고는 하는데 외부라리브러리인지는 몰라도 여기에는 없어서 하지 못하였습니다.
	 * 그리고 초기에 랜덤함수로 값을 어떻게 정하느냐에따라 값이 상당히 변화가 많은 단점이 있습니다.
	 * 조교님이 주신 매트랩코드를 똑같이 {784,500,3} 으로 구성한 30개의 데이터를 넣고 돌리면 조교님과 같은 결과가 나오고
	 * {784,500,400,10} 으로 돌리면 시간적으로 오래걸려서 100번학습만 했지만 점점 인식률이 증가하는것을 볼수있는데, 90번째 와 95번째 학습에서
	 * 변화율이 없는 부분이 있습니다. 그 부분만 그런건지 아니면 내주신 과제에 없는 드랍아웃 개념이나 컨벌루션 개념을 써서 했어야하는 과제였는지는 잘 모르겠습니다.
	 * (같이 첨부한 result.txt 에 결과첨부하였습니다.)
	 * 
	 */
    public static void main(String[] args) {
    	int[] layer = {784,500,400,10};
		NeuralNetwork nn = new NeuralNetwork(layer);
		DataSet my_data = new DataSet("C:\\Users\\user\\eclipse-workspace\\Problem 3\\mnistdataset.txt",layer[0],layer[layer.length-1]);
		System.out.println("파일 불러오기 완료");
		int[] reality = new int[my_data.getData().size()];	//실제값
		for(int i=0;i<my_data.getData().size();i++) {
			reality[i] = my_data.getData().get(i).gettest();
		}
		System.out.println("학습 시작");
		for(int i=1;i<=100;i++) {
			nn.train(0.01, my_data);
			System.out.println(i+"번쨰 학습을 완료했습니다.");
			if(i % 5 == 0) {
				int[] predict = new int[my_data.getData().size()];	//예측값
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
