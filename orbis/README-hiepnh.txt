
==== dkTopoGen1k_new ====
-n <number of nodes>

dkTopoGen1k_new -i as20graph.seq -n 6474 > as20graph.1.gen

dkTopoGen1k_new -i com_amazon_ungraph.seq -n 334863 > com_amazon_ungraph.1.gen

dkTopoGen1k_new -i com_dblp_ungraph.seq -n 317080 > com_dblp_ungraph.1.gen

dkTopoGen1k_new -i com_youtube_ungraph.seq -n 1134890 > com_youtube_ungraph.1.gen


==== dkTopoGen1k_stub ====
-n <number of nodes>

dkTopoGen1k_stub -i polbooks.1.stub -n 105 > polbooks.stub.1.gen

dkTopoGen1k_stub -i com_amazon_ungraph.1.stub -n 334863 > com_amazon_ungraph.stub.1.gen


==== shuffle1k ====
shuffle1k -i com_amazon_ungraph.seq -n 334863 > com_amazon_ungraph.cpp.1.stub

shuffle1k -i com_dblp_ungraph.seq -n 317080 > com_dblp_ungraph.cpp.1.stub