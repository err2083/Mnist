package kr.ac.cbnu.computerengineering.lecture2;

public interface IErrorFunction {
	abstract void Matrix(int iter,int[] predict,int[] reality,int n); //������ ����, ������ ������ �� ��Ÿ���� ��Ʈ����
	abstract int Maxpredict(double[] compute);	//���ͷ� �� ���� Ȯ���� ��Ÿ���Ƿ� ���� ū���� ����
}
