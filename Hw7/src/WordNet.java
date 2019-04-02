import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {
	private Map<Integer, String> idToNoun;
	private Map<String, Set<Integer>> nounToId;
	private SAP sap;
	public WordNet(String synsets, String hypernyms) 
	{
		this.idToNoun = new HashMap<>();
		this.nounToId = new HashMap<>();
		initSynsets(synsets);
		Digraph graph = initHypernyms(hypernyms);
		
		DirectedCycle cycle = new DirectedCycle(graph);
		if (cycle.hasCycle() || !rootedDAG(graph) ) 
		{
			throw new IllegalArgumentException("The input doesn't correspond to a rooted DAG!");
		}
		this.sap = new SAP(graph);
		
	}
	
	private boolean rootedDAG(Digraph graph) {
		int roots = 0;
		for (int i = 0; i < graph.V(); i++) 
		{
			if (! graph.adj(i).iterator().hasNext()) 
			{
				roots ++;
				if ( roots > 1) { return false; }
			}
		}
		return roots == 1;
	}
	private void initSynsets(String synsets) 
	{
		In file = new In(synsets);
		while (file.hasNextLine()) 
		{
			String[] line = file.readLine().split(",");
			Integer id = Integer.valueOf(line[0]);
			String val = line[1];
			this.idToNoun.put(id, val);
			
			String[] nouns = val.split(" ");
			for (String noun : nouns ) 
			{
				Set<Integer> ids = this.nounToId.get(noun);
				if (ids == null ) 
				{
					ids = new HashSet<>();
				}
				ids.add(id);
				this.nounToId.put(noun, ids);
			}
		}
	}
	private Digraph initHypernyms(String hypernyms) 
	{
		Digraph graph = new Digraph(this.idToNoun.size());
		
		In file = new In(hypernyms);
		while (file.hasNextLine()) 
		{
			String[] line = file.readLine().split(",");
			Integer synsetId = Integer.valueOf(line[0]);
			for (int i = 1; i < line.length; i++) 
			{
				Integer id = Integer.valueOf(line[i]);
				graph.addEdge(synsetId, id);
			}
		}
		return graph;
	}
	
	public Iterable<String> nouns() 
	{
		return this.nounToId.keySet();
	}
	public boolean isNoun(String word) 
	{
		return this.nounToId.containsKey(word);
	}
	public int distance(String nounA, String nounB) 
	{
		if ( isNoun(nounA) != true || isNoun(nounB) != true ) 
		{
			return -1; // -1 means bad method
		}
		return this.sap.length(this.nounToId.get(nounA), this.nounToId.get(nounB));
	}
	public String sap(String nounA, String nounB) 
	{
		return this.idToNoun.get(
				this.sap.ancestor(
						this.nounToId.get(nounA), 
						this.nounToId.get(nounB)
						)
				);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 0;
	}

}
