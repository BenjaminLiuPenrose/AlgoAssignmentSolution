import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
	private Digraph graph;
	private Map<String, SAPProcessor> cache;
	private class SAPProcessor {
		int ancestor;
		int distance;
		public SAPProcessor(int v, int w) 
		{
			BreadthFirstDirectedPaths a = new BreadthFirstDirectedPaths(graph, v);
			BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(graph, w);
			process(a, b);
		}
		public SAPProcessor(Iterable<Integer> v, Iterable<Integer> w) 
		{
			BreadthFirstDirectedPaths a = new BreadthFirstDirectedPaths(graph, v);
			BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(graph, w);
			process(a, b);
		}
		private void process(BreadthFirstDirectedPaths a, BreadthFirstDirectedPaths b) 
		{
			List<Integer> ancestors = new ArrayList<>();
			for (int i = 0; i < graph.V(); i++) 
			{
				// traversal all the points to find ancester
				if( a.hasPathTo(i) && b.hasPathTo(i)) 
				{
					ancestors.add(i);
				}
			}
			int shortestAncestor = -1;
			int minDistance = Integer.MAX_VALUE;
			for (int ancestor: ancestors) 
			{
				int dist = a.distTo(ancestor) + b.distTo(ancestor);
				if( dist < minDistance) 
				{
					shortestAncestor = ancestor;
					minDistance = dist;
				}
			}
			if (minDistance == Integer.MAX_VALUE) 
			{
				this.distance = -1;
			} else {
				this.distance = minDistance; 
			}
			this.ancestor = shortestAncestor;
		}	
	}
	private SAPProcessor cachedResult(int v, int w) 
	{
		String key = v + "_" + w;
		if (this.cache.containsKey(key)) 
		{
			SAPProcessor p = this.cache.get(key);
			return p;
		}
		SAPProcessor p = new SAPProcessor(v, w);
		this.cache.put(key,  p);
		return p;
	}
	private SAPProcessor cachedResult(Iterable<Integer> v, Iterable<Integer> w) 
	{
		String key = v.toString() + "_" + w.toString();
		if ( this.cache.containsKey(key) ) 
		{
			SAPProcessor p = this.cache.get(key);
			return p;
		}
		SAPProcessor p = new SAPProcessor(v, w);
		cache.put(key, p);
		return p;
	}
	
	
	public SAP(Digraph G) 
	{
		this.graph = new Digraph(G);
		this.cache = new HashMap<>();
	}
	public int length(int v, int w) 
	{
		if (!validIndex(v) || !validIndex(w) ) 
		{
			throw new ArrayIndexOutOfBoundsException("Index out of bound");
		}
		return cachedResult(v, w).distance;
	}
	public int ancestor(int v, int w) 
	{
		if(!validIndex(v) || !validIndex(w) ) 
		{
			throw new ArrayIndexOutOfBoundsException("Index out of bound");
		}
		return cachedResult(v, w).ancestor;
	}
	public int length(Iterable<Integer> v, Iterable<Integer> w) 
	{
		if (!validIndex(v) || !validIndex(w) ) 
		{
			throw new ArrayIndexOutOfBoundsException("Index out of bound");
		}
		return cachedResult(v, w).distance;
	}
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) 
	{
		if (!validIndex(v) || !validIndex(w) ) 
		{
			throw new ArrayIndexOutOfBoundsException("Index out of bound");
		}
		return cachedResult(v, w).ancestor;
	}
	
	private boolean validIndex(int v) 
	{
		if( v < 0 || v >= this.graph.V() ) { return false; }
		return true;
	}
	private boolean validIndex(Iterable<Integer> v) 
	{
		for (Integer vertex : v) 
		{
			if( !validIndex(vertex) ) 
			{
				return false;
			}
		} 
		return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 0;
	}

}
