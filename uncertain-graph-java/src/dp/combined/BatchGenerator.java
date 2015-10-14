/*
 * Oct 11, 2015
 * 	- generate batch files (.cmd)
 * Oct 12
 * 	- generateLouvain() for "_ef_", "_tmf_", "_ldp_"
 * Oct 13
 * 	- measureABC,... (the same parameter list as generateABC functions)
 */

package dp.combined;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class BatchGenerator {
	
	//// EdgeFlip
	public static void generateEdgeFlip(String batch_file, String prefix, String dataname, int n_samples, double[] epsArr) 
			throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(batch_file));
		for (double eps : epsArr){
			String cmd = "java dp.combined.EdgeFlip " + prefix + " " + dataname + " " + n_samples + " " + String.format("%.2f", eps) + 
					" > ../_console/" + dataname + "_ef_" + String.format("%.1f", eps) + "-CONSOLE.txt";
			bw.write(cmd + "\n");
		}
    	bw.close();
	}
	
	//// TmF
	public static void generateTmF(String batch_file, String prefix, String dataname, int n_samples, double[] epsArr) 
			throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(batch_file));
		for (double eps : epsArr){
			String cmd = "java naive.GreedyReconstruct " + prefix + " " + dataname + " " + n_samples + " " + String.format("%.2f", eps) + 
					" > ../_console/" + dataname + "_tmf_" + String.format("%.1f", eps) + "-CONSOLE.txt";
			bw.write(cmd + "\n");
		}
		bw.close();
	}
	
	//// LouvainDP
	public static void generateLouvainDP(String batch_file, String prefix, String dataname, int n_samples, double[] epsArr, int[] kArr) 
			throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(batch_file));
		for (double eps : epsArr){
			for (int k : kArr){
				String cmd = "java dp.combined.LouvainDP " + prefix + " " + dataname + " " + n_samples + " " + 
						String.format("%.2f", eps) + " " + k + 
						" > ../_console/" + dataname + "_ldp_" + String.format("%.1f", eps) + "_" + k + "-CONSOLE.txt";
				bw.write(cmd + "\n");
			}
			bw.write("\n");
		}
		bw.close();
	}
	
	////LouvainOpt
	public static void generateLouvainOpt(String batch_file, String prefix, String dataname, int n_samples, 
			int burn_factor, int[] maxLevelArr, int[] kArr) throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(batch_file));
		for (int i = 0; i < kArr.length; i++){
			int k = kArr[i];
			int max_level = maxLevelArr[i];
			String cmd = "java dp.combined.LouvainOpt " + prefix + " " + dataname + " " + n_samples + " " + 
					burn_factor + " " + max_level + " " + k + 
					" > ../_console/" + dataname + "_nmd_" + burn_factor + "_" + max_level + "_" + k + "-CONSOLE.txt";
			bw.write(cmd + "\n");
		}
		
		bw.close();
	}
	
	////LouvainModDiv
	public static void generateLouvainModDiv(String batch_file, String prefix, String dataname, int n_samples, 
			int burn_factor, int[] maxLevelArr, int[] kArr, double[] epsArr, double ratio) throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(batch_file));
		for (double eps : epsArr){
			for (int i = 0; i < kArr.length; i++){
				int k = kArr[i];
				int max_level = maxLevelArr[i];
				String cmd = "java dp.combined.LouvainModDiv " + prefix + " " + dataname + " " + n_samples + " " + 
						burn_factor + " " + max_level + " " + k + " " + String.format("%.1f", eps) + " " + String.format("%.2f", ratio) + 
						" > ../_console/" + dataname + "_md_" + burn_factor + "_" + 
						max_level + "_" + k + "_" + String.format("%.1f", eps) + "_" + String.format("%.2f", ratio) + "-CONSOLE.txt";
				bw.write(cmd + "\n");
			}
			bw.write("\n");
		}
		
		bw.close();
	}
	
	////HRGDivisiveGreedy
	public static void generateHRGDiv(String batch_file, String prefix, String dataname, int n_samples, 
			int burn_factor, int max_level, double[] epsArr, double[] ratioArr) throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(batch_file));
		
		for (double ratio : ratioArr){
			for (double eps : epsArr){
				String cmd = "java dp.combined.HRGDivisiveGreedy " + prefix + " " + dataname + " " + n_samples + " " + 
						burn_factor + " " + max_level + " " + String.format("%.1f", eps) + " " + String.format("%.2f", ratio) + 
						" > ../_console/" + dataname + "_hd_" + burn_factor + "_" + 
						max_level + "_" + String.format("%.1f", eps) + "_" + String.format("%.2f", ratio) + "-CONSOLE.txt";
				bw.write(cmd + "\n");
			}
			bw.write("\n");
		}
		
		bw.close();
	}
	
	/////////
	// algo = "_ef_", "_tmf_", "_ldp_"
	public static void generateLouvain(String batch_file, String prefix, String dataname, int n_samples, double[] epsArr, String algo, int type) 
			throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(batch_file));
		for (double eps : epsArr){
			String sample_name = dataname + algo + String.format("%.1f", eps);
			
			String cmd = "java dp.combined.Louvain " + prefix + " " + n_samples + " " + type + " " + sample_name + 
					" > ../_console/" + dataname + "_ef_" + String.format("%.1f", eps) + "-LOUVAIN.txt";
			bw.write(cmd + "\n");
		}
    	bw.close();
	}
	
	//////to automate CommunityMeasure.computeAndExport()
	public static void measureEdgeFlip(String batch_file, String prefix, String dataname, int n_samples, double[] epsArr) 
			throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(batch_file));
		for (double eps : epsArr){
			String sample_file = dataname + "_ef_" + String.format("%.1f", eps);
			String cmd = "java dp.combined.CommunityMeasure " + prefix + " " + dataname + " " + n_samples + " " + sample_file;
					
			bw.write(cmd + "\n");
		}
 	bw.close();
		
	}
	
	public static void measureTmF(String batch_file, String prefix, String dataname, int n_samples, double[] epsArr) 
			throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(batch_file));
		for (double eps : epsArr){
			String sample_file = dataname + "_tmf_" + String.format("%.1f", eps);
			String cmd = "java dp.combined.CommunityMeasure " + prefix + " " + dataname + " " + n_samples + " " + sample_file;
			
			bw.write(cmd + "\n");
		}
		bw.close();
	}
	
	public static void measureLouvainDP(String batch_file, String prefix, String dataname, int n_samples, double[] epsArr, int[] kArr) 
			throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(batch_file));
		for (double eps : epsArr){
			for (int k : kArr){
				String sample_file = dataname + "_ldp_" + String.format("%.1f", eps) + "_" + k;
				String cmd = "java dp.combined.CommunityMeasure " + prefix + " " + dataname + " " + n_samples + " " + sample_file;
				
				bw.write(cmd + "\n");
			}
			bw.write("\n");
		}
		bw.close();
	}
	
	public static void measureLouvainOpt(String batch_file, String prefix, String dataname, int n_samples, 
			int burn_factor, int[] maxLevelArr, int[] kArr) throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(batch_file));
		for (int i = 0; i < kArr.length; i++){
			int k = kArr[i];
			int max_level = maxLevelArr[i];
			
			String sample_file = dataname + "_nmd_" + burn_factor + "_" + max_level + "_" + k;
			String cmd = "java dp.combined.CommunityMeasure " + prefix + " " + dataname + " " + n_samples + " " + sample_file;
			
			bw.write(cmd + "\n");
		}
		
		bw.close();
	}
	
	public static void measureLouvainModDiv(String batch_file, String prefix, String dataname, int n_samples, 
			int burn_factor, int[] maxLevelArr, int[] kArr, double[] epsArr, double ratio) throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(batch_file));
		for (double eps : epsArr){
			for (int i = 0; i < kArr.length; i++){
				int k = kArr[i];
				int max_level = maxLevelArr[i];
				
				String sample_file = dataname + "_md_" + burn_factor + "_" + 
						max_level + "_" + k + "_" + String.format("%.1f", eps) + "_" + String.format("%.2f", ratio);
				String cmd = "java dp.combined.CommunityMeasure " + prefix + " " + dataname + " " + n_samples + " " + sample_file;
				
				bw.write(cmd + "\n");
			}
			bw.write("\n");
		}
		
		bw.close();
	}
	
	////////////////////////////////////////////////
	public static void main(String[] args) throws Exception{
		
		String prefix = "../";		// run in D:/git/itce2011/uncertain-graph-java/_cmd
		
		String[] dataname_list = new String[]{"com_amazon_ungraph", "com_dblp_ungraph", "com_youtube_ungraph"};
		int[] n_list = new int[]{334863, 317080, 1134890};
		// for TEST
//		String[] dataname_list = new String[]{"karate"};
//		int[] n_list = new int[]{34};
		
		int n_samples = 20;
	
		
		
		
		// EdgeFlip
//		for (int i = 0; i < n_list.length; i++){
//			String dataname = dataname_list[i];
//			int n = n_list[i];
//			
//			//
//			String batch_file = "_cmd/EdgeFlip_" + dataname + ".cmd";
//			double log_n = Math.log(n);
//			double[] epsArr = new double[]{2.0, 0.25*log_n, 0.5*log_n, log_n, 1.5*log_n, 2*log_n, 3*log_n};
//			
//			
//			generateEdgeFlip(batch_file, prefix, dataname, n_samples, epsArr);
//			System.out.println("DONE.");
//		}
		
		// TmF
//		for (int i = 0; i < n_list.length; i++){
//			String dataname = dataname_list[i];
//			int n = n_list[i];
//			
//			//
//			String batch_file = "_cmd/TmF_" + dataname + ".cmd";
//			double log_n = Math.log(n);
//			double[] epsArr = new double[]{2.0, 0.25*log_n, 0.5*log_n, log_n, 1.5*log_n, 2*log_n, 3*log_n};
//			
//			
//			generateTmF(batch_file, prefix, dataname, n_samples, epsArr);
//			System.out.println("DONE.");
//		}
		
		// LouvainDP
//		for (int i = 0; i < n_list.length; i++){
//			String dataname = dataname_list[i];
//			int n = n_list[i];
//			
//			//
//			String batch_file = "_cmd/LouvainDP_" + dataname + ".cmd";
//			double log_n = Math.log(n);
//			double[] epsArr = new double[]{2.0, 0.25*log_n, 0.5*log_n, log_n, 1.5*log_n, 2*log_n, 3*log_n};
//			int[] kArr = new int[]{2,4,8,16,32,64,128};
//			
//			
//			
//			generateLouvainDP(batch_file, prefix, dataname, n_samples, epsArr, kArr);
//			System.out.println("DONE.");
//		}
		
		// LouvainOpt
//		for (int i = 0; i < n_list.length; i++){
//			String dataname = dataname_list[i];
//			int n = n_list[i];
//			
//			//
//			String batch_file = "_cmd/LouvainOpt_" + dataname + ".cmd";
//			double log_n = Math.log(n);
//			int[] kArr = new int[]{2,3,4,5,6,10};
//			int[] maxLevelArr = new int[]{10,7,5,4,4,3};
//			int burn_factor = 20;
//			
//			generateLouvainOpt(batch_file, prefix, dataname, n_samples, burn_factor, maxLevelArr, kArr);
//			System.out.println("DONE.");
//		}
		
		// LouvainModDiv
//		for (int i = 0; i < n_list.length; i++){
//			String dataname = dataname_list[i];
//			int n = n_list[i];
//			
//			//
//			String batch_file = "_cmd/LouvainModDiv_" + dataname + ".cmd";
//			double log_n = Math.log(n);
//			int[] kArr = new int[]{2,3,4,5,6,10};
//			int[] maxLevelArr = new int[]{10,7,5,4,4,3};
//			double[] epsArr = new double[]{2.0, 0.25*log_n, 0.5*log_n, log_n, 1.5*log_n, 2*log_n, 3*log_n};
//			int burn_factor = 20;
//			double ratio = 2.0;
//			
//			generateLouvainModDiv(batch_file, prefix, dataname, n_samples, burn_factor, maxLevelArr, kArr, epsArr, ratio);
//			System.out.println("DONE.");
//		}
		
		// HRGDivisiveGreedy
//		for (int i = 0; i < n_list.length; i++){
//			String dataname = dataname_list[i];
//			int n = n_list[i];
//			
//			//
//			String batch_file = "_cmd/HRGDivisiveGreedy_" + dataname + ".cmd";
//			double log_n = Math.log(n);
//			int max_level = 10;
//			double[] epsArr = new double[]{2.0, 0.25*log_n, 0.5*log_n, log_n, 1.5*log_n, 2*log_n, 3*log_n};
//			int burn_factor = 20;
//			double[] ratioArr = new double[]{2.0, 1.5, 1.0};
//			
//			generateHRGDiv(batch_file, prefix, dataname, n_samples, burn_factor, max_level, epsArr, ratioArr);
//			System.out.println("DONE.");
//		}
		
		
		//////////////////////////////// LOUVAIN
//		for (int i = 0; i < n_list.length; i++){
//			String dataname = dataname_list[i];
//			int n = n_list[i];
//			double log_n = Math.log(n);
//			double[] epsArr = new double[]{2.0, 0.25*log_n, 0.5*log_n, log_n, 1.5*log_n, 2*log_n, 3*log_n};
//			
//			String batch_file = "_cmd/Louvain_" + dataname + ".cmd";
//			generateLouvain(batch_file, prefix, dataname, n_samples, epsArr, "_ef_", 1);
//			
//			System.out.println("DONE.");
//		}
//		
//		for (int i = 0; i < n_list.length; i++){
//			String dataname = dataname_list[i];
//			int n = n_list[i];
//			double log_n = Math.log(n);
//			double[] epsArr = new double[]{2.0, 0.25*log_n, 0.5*log_n, log_n, 1.5*log_n, 2*log_n, 3*log_n};
//			
//			String batch_file = "_cmd/Louvain_" + dataname + ".cmd";
//			generateLouvain(batch_file, prefix, dataname, n_samples, epsArr, "_tmf_", 1);
//			
//			System.out.println("DONE.");
//		}
//		
//		for (int i = 0; i < n_list.length; i++){
//			String dataname = dataname_list[i];
//			int n = n_list[i];
//			double log_n = Math.log(n);
//			double[] epsArr = new double[]{2.0, 0.25*log_n, 0.5*log_n, log_n, 1.5*log_n, 2*log_n, 3*log_n};
//			int[] kArr = new int[]{2,4,8,16,32,64,128};
//			
//			String batch_file = "_cmd/Louvain_" + dataname + ".cmd";
//			generateLouvain(batch_file, prefix, dataname, n_samples, epsArr, "_ldp_", 1);
//			
//			System.out.println("DONE.");
//		}
		
		
		//////////////////////////////// COMMUNITY METRICS
		for (int i = 0; i < n_list.length; i++){
			String dataname = dataname_list[i];
			int n = n_list[i];
			
			//
			double log_n = Math.log(n);
			double[] epsArr = new double[]{2.0, 0.25*log_n, 0.5*log_n, log_n, 1.5*log_n, 2*log_n, 3*log_n};

//			//
//			String batch_file = "_cmd/Metric_EdgeFlip_" + dataname + ".cmd";
//			measureEdgeFlip(batch_file, prefix, dataname, n_samples, epsArr);
//			System.out.println("measureEdgeFlip - DONE.");
//			
//			//
//			batch_file = "_cmd/Metric_TmF_" + dataname + ".cmd";
//			measureTmF(batch_file, prefix, dataname, n_samples, epsArr);
//			System.out.println("measureTmF - DONE.");
//			
//			//
//			batch_file = "_cmd/Metric_LouvainDP_" + dataname + ".cmd";
//			int[] kArr = new int[]{2,4,8,16,32,64,128};
//			measureLouvainDP(batch_file, prefix, dataname, n_samples, epsArr, kArr);
//			System.out.println("measureLouvainDP - DONE.");
			
			
			//
			String batch_file = "_cmd/Metric_LouvainOpt_" + dataname + ".cmd";
			int[] kArr = new int[]{2,3,4,5,6,10};
			int[] maxLevelArr = new int[]{10,7,5,4,4,3};
			int burn_factor = 20;
			measureLouvainOpt(batch_file, prefix, dataname, n_samples, burn_factor, maxLevelArr, kArr);
			System.out.println("measureLouvainOpt - DONE.");
			
//			//
//			String batch_file = "_cmd/Metric_LouvainModDiv_" + dataname + ".cmd";
//			int[] kArr = new int[]{2,3,4,5,6,10};
//			int[] maxLevelArr = new int[]{10,7,5,4,4,3};
//			int burn_factor = 20;
//			double ratio = 2.0;
//			measureLouvainModDiv(batch_file, prefix, dataname, n_samples, burn_factor, maxLevelArr, kArr, epsArr, ratio);
//			System.out.println("measureLouvainModDiv - DONE.");
		}
		
	}

}
