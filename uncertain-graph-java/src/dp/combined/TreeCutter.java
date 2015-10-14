/*
 * Oct 14, 2015
 * 	- 
 */

package dp.combined;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TreeCutter {

	////
	public static void fixTreeFile(String prefix, String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(prefix + "_sample2/" + filename));
		BufferedWriter bw = new BufferedWriter(new FileWriter(prefix + "_sample/" + filename));
		
		int count = 0;
		while (true){
        	String str = br.readLine();
        	if (str == null)
        		break;
        	
        	ArrayList<Integer> pos = new ArrayList<Integer>();
    		for(int i = 0; i < str.length(); i++){
    		    if(str.charAt(i) == ':'){
    		       pos.add(i);
    		    }
    		}
    		
        	if (pos.size() > 1){
        		int last_minus = 0;
        		for (int i = 1; i < pos.size(); i++){
        			int minus_pos = str.substring(0, pos.get(i)).lastIndexOf("-");
        			
        			bw.write(str.substring(last_minus, minus_pos) + "\n");
        			last_minus = minus_pos;
        		}
        		bw.write(str.substring(last_minus) + "\n");
        		
        		//
        		count += 1;
        	}else{
        		bw.write(str + "\n");
        	}
		}
		
		br.close();
		bw.close();
	}
	
	////
	public static void fixHRGDivisiveTreeFiles() throws IOException{
		
		String[] dataname_list = new String[]{"com_amazon_ungraph", "com_dblp_ungraph", "com_youtube_ungraph"};
		int[] n_list = new int[]{334863, 317080, 1134890};
		int n_samples = 20;
		
		for (int i = 0; i < n_list.length; i++){
			String dataname = dataname_list[i];
			int n = n_list[i];
			
			System.out.println("dataname = " + dataname);
			//
			double log_n = Math.log(n);
			int max_level = 10;
			double[] epsArr = new double[]{2.0, 0.25*log_n, 0.5*log_n, log_n, 1.5*log_n, 2*log_n, 3*log_n};
			int burn_factor = 20;
			double[] ratioArr = new double[]{2.0, 1.5, 1.0};
			
			for (double ratio : ratioArr){
				System.out.println("ratio = " + ratio);
				for (double eps : epsArr){
					System.out.println("eps = " + eps);
					String filename = dataname + "_hd_" + burn_factor + "_" + max_level + "_" + 
							String.format("%.1f", eps) + "_" + String.format("%.2f", ratio) + "_tree";
					
					for (int s = 0; s < n_samples; s++){
						System.out.println("sample " + s);
						fixTreeFile("", filename + "." + i);
					}
					
				}
			}
		}
		
		
		
	}
	
	//////////////////////////////////////////////////
	public static void main(String[] args) throws Exception{
		

		fixHRGDivisiveTreeFiles();

	}

}
