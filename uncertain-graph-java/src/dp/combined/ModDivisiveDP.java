/*
 * Sep 18, 2015
 * 	- divisive approach using exponential mechanism with modularity Q
 * 	- use NodeSetMod.java
 */

package dp.combined;

import grph.Grph;
import grph.io.EdgeListReader;
import toools.io.file.RegularFile;

public class ModDivisiveDP {

	//////////////////////////////////////////////////
	public static void main(String[] args) throws Exception {
		ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
		System.out.println("ModDivisiveDP");
		
		// load graph
//		String dataname = "polbooks";		// (105, 441)		eps = 50, final modularity = 0.442
											// recursiveLK		
//		String dataname = "polblogs";		// (1224,16715) 	eps = 50, final modularity = 0.399
											// recursiveLK		
//		String dataname = "as20graph";		// (6474,12572)		eps = 50, max_level = 4, final modularity = 0.189 (8s)
											// recursiveLK		
//		String dataname = "wiki-Vote";		// (7115,100762) 	
											// recursiveLK		
//		String dataname = "ca-HepPh";		// (12006,118489) 	eps = 50, max_level = 5, final modularity = 0.492 (58s) 
															
		String dataname = "ca-AstroPh";		// (18771,198050) 	eps = 50, max_level = 5, final modularity = 0.506 (92s) 
		// LARGE
//		String dataname = "com_amazon_ungraph"; 	// (334863,925872) 	eps = 50, max_level = 4, final modularity = ? (?s)
//		String dataname = "com_dblp_ungraph";  		// (317080,1049866) 
//		String dataname = "com_youtube_ungraph"; 	// (1134890,2987624) 
		
		
		// COMMAND-LINE <prefix> <dataname> <n_samples> <burn_factor>
		String prefix = "";
		int n_samples = 1;
		int burn_factor = 20;
		int limit_size = 1;
		int lower_size = 2;		// at least 2
		int max_level = 4;
		double eps1 = 10.0;	// 1, 10, 50, 100 for polbooks: interesting prob values and final results
		
		if(args.length >= 4){
			prefix = args[0];
			dataname = args[1];
			n_samples = Integer.parseInt(args[2]);
			burn_factor = Integer.parseInt(args[3]);
		}
		if(args.length >= 5)
			limit_size = Integer.parseInt(args[4]);
		
		System.out.println("dataname = " + dataname);
		System.out.println("burn_factor = " + burn_factor + " n_samples = " + n_samples);
		System.out.println("limit_size = " + limit_size);
		System.out.println("lower_size = " + lower_size);
		System.out.println("max_level = " + max_level);
		
		//
		String filename = prefix + "_data/" + dataname + ".gr";	// EdgeListReader
		
		//
		EdgeListReader reader = new EdgeListReader();
		
		Grph G;
		RegularFile f = new RegularFile(filename);
		
		G = reader.readGraph(f);
		
		System.out.println("#nodes = " + G.getNumberOfVertices());
		System.out.println("#edges = " + G.getNumberOfEdges());
		
		//
		NodeSetMod R = new NodeSetMod(G);
		System.out.println("mod = " + R.modularity(G.getNumberOfEdges()));
		
		// TEST recursiveMod()
		for (int i = 0; i < n_samples; i++){
			System.out.println("sample i = " + i);
			
			long start = System.currentTimeMillis();
			NodeSetMod root_set = NodeSetMod.recursiveMod(G, eps1, burn_factor, limit_size, lower_size, max_level);	
			System.out.println("recursiveMod - DONE, elapsed " + (System.currentTimeMillis() - start));
			
			
			NodeSetMod.printSetIds(root_set, G.getNumberOfEdges());
			System.out.println("final modularity = " + root_set.modularityAll(G.getNumberOfEdges()));
		}

	}

}
