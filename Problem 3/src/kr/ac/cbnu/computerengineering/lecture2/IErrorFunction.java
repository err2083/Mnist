package kr.ac.cbnu.computerengineering.lecture2;

public interface IErrorFunction {
	abstract void Matrix(int iter,int[] predict,int[] reality,int n); //가로줄 예측, 세로줄 실제값 을 나타내는 매트릭스
	abstract int Maxpredict(double[] compute);	//벡터로 그 값의 확률을 나타내므로 가장 큰값을 추출
}
