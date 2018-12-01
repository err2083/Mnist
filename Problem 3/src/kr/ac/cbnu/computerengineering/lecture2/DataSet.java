package kr.ac.cbnu.computerengineering.lecture2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSet {
	private List<Data> data = new ArrayList<>();
	private File my_file;
	
	public DataSet(String file_name,int in,int out) {
		double[] stream = new double[in];
		my_file = new File(file_name);
		FileInputStream input;
		try {
			input = new FileInputStream(my_file);
			int now=0;
			String stemp="";
			int end;
			char ctemp;
			boolean islavel = false;
			while((end = input.read()) != -1) {
				ctemp = (char)end;
				if(islavel == true) {
					islavel = false;
					int lavel = ctemp-'0';
					double[] vlavel = new double[out];
					vlavel[ctemp-'0'] = 1;
					double[] temparr = new double[in];
					for(int i=0;i<in;i++)
						temparr[i] = stream[i];
					Data t = new Data(temparr,vlavel,lavel);
					data.add(t);
					now = 0;
				}
				else if(ctemp >= '0' && ctemp <= '9') {
					stemp = stemp + ctemp;
				}
				else if(ctemp == ' ' || ctemp == ']') {
					if(stemp == "") continue;
					stream[now++] = (double)Integer.parseInt(stemp);
					stemp = "";
				}
				else if(ctemp == ',') {
					islavel = true;
					continue;
				}
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public List<Data> getData(){
		return data;
	}
}
