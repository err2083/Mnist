package kr.ac.cbnu.computerengineering.lecture2;

public class Layer {
	Neuron[] neuron;
	public Layer(int nsize,int nw) {
		neuron = new Neuron[nsize];
		for(int i=0;i<nsize;i++) {
			neuron[i] = new Neuron(nw);
		}
	}
	public int getsize() {
		return neuron.length;
	}
	public Neuron getneuron(int idx) {
		return neuron[idx];
	}
}
