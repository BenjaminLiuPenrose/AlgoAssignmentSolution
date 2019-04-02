import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

public class BaseballElimination {
	private int[] wins;
	private int[] losses;
	private int[] remaining;
	private int[][] games;
	private Map<String, Integer> teamToId;
	private int maxWins = Integer.MIN_VALUE;
	private String leadTeam;
	
	private class Graph {
		FordFulkerson ff;
		FlowNetwork network;
		int source;
		int sink;
		public Graph(FordFulkerson ff, FlowNetwork network, int source, int sink)  
		{
			super();
			this.ff = ff;
			this.network = network;
			this.source = source;
			this.sink = sink;
		}
	}
	private Graph buildGraph(int id) 
	{
		int n = numberOfTeams();
		int source = n;
		int sink = n + 1;
		int gameNode = n + 2;
		int currentMaxWins = this.wins[id] + this.remaining[id];
		Set<FlowEdge> edges = new HashSet<>();
		for ( int i = 0; i < n; i++ ) 
		{
			if ( i == id || this.wins[i] + this.remaining[i] < currentMaxWins ) { continue; }
			for ( int j = 0; j < i; j ++) 
			{
				if ( j == id || this.wins[j] + this.remaining[j] < currentMaxWins ) { continue; }
				edges.add(new FlowEdge(source, gameNode, games[i][j]));
				edges.add(new FlowEdge(gameNode, i, Double.POSITIVE_INFINITY));
				edges.add(new FlowEdge(gameNode, j, Double.POSITIVE_INFINITY));
				gameNode ++;
			}
			edges.add(new FlowEdge(i, sink, currentMaxWins - this.wins[i]));
		}
		
		// return the sink, source, ff, and network
		FlowNetwork network = new FlowNetwork(gameNode);
		for (FlowEdge edge: edges) 
		{
			network.addEdge(edge);
		}
		FordFulkerson ff = new FordFulkerson(network, source, sink);
		return new Graph(ff, network, source, sink);
	}
	public BaseballElimination(String filename) 
	{
		In in = new In(filename);
		int teams = in.readInt();
		this.wins = new int[teams];
		this.losses = new int[teams];
		this.remaining = new int[teams];
		this.games = new int[teams][teams];
		
		this.teamToId = new HashMap<String, Integer>();
		for (int id = 0; id < teams; id ++) 
		{
			String team = in.readString();
			this.teamToId.put(team, id);
			this.wins[id] = in.readInt();
			this.losses[id] = in.readInt();
			this.remaining[id] = in.readInt();
			for (int j = 0; j < teams; j++) 
			{
				this.games[id][j] = in.readInt();
			}
			if (this.wins[id] > maxWins) 
			{
				maxWins = this.wins[id];
				this.leadTeam = team;
			}
		}
		
	}
	public int numberOfTeams() 
	{
		return teamToId.size();
	}
	public Iterable<String> teams() 
	{
		return teamToId.keySet();
	}
	public int wins(String team) 
	{
		if (!teamToId.containsKey(team)) 
		{
			throw new IllegalArgumentException("The team is not known!");
		}
		return wins[teamToId.get(team)];
	}
	public int losses(String team) 
	{
		if (!teamToId.containsKey(team)) 
		{
			throw new IllegalArgumentException("The team is not known!");
		}
		return losses[teamToId.get(team)];
	}
	public int remaining(String team) 
	{
		if (!teamToId.containsKey(team)) 
		{
			throw new IllegalArgumentException("The team is not known!");
		}
		return remaining[teamToId.get(team)];
	}
	public int against(String team1, String team2) 
	{
		if ( !teamToId.containsKey(team1) || !teamToId.containsKey(team2)) 
		{
			throw new IllegalArgumentException("The team is not known!");
		}
		return games[teamToId.get(team1)][teamToId(team2)];
	}
	public boolean isEliminated(String team) 
	{
		if (!teamToId.containsKey(team)) 
		{
			throw new IllegalArgumentException("The team is not known!");
		}
		int id = teamToId.get(team);
		if (trivallyEliminated(id)) { return true; }
		// translate original problem to graph maxflow problem
		Graph graph = buildGraph(id);
		for (FlowEdge edge: graph.network.adj(graph.source)) 
		{
			if (edge.flow() < edge.capacity()) 
			{
				return true;
			}
		}
		return false;
		
	}
	private boolean trivallyEliminated(int id) 
	{
		for (int i = 0; i < this.wins.length; i++) 
		{
			if (i != id) 
			{
				if( this.wins[id] + this.remaining[id] < this.wins[i] ) { return true; }
			}
		}
		return false;
	}
	public Iterable<String> certificateOfElimination(String team) 
	{
		if (!teamToId.containsKey(team)) 
		{
			throw new IllegalArgumentException("The team is not known!");
		}
		int id = teamToId.get(team);
		Set<String> set = new HashSet<>();
		if ( trivallyEliminated(id) ) 
		{
			set.add(this.leadTeam);
			return set;
		}
		Graph graph = buildGraph(id);
		for (FlowEdge edge: graph.network.adj(graph.source)) 
		{
			if (edge.flow() < edge.capacity()) 
			{
				for (String t : teams()) 
				{
					int idx = teamToId.get(t);
					if (graph.ff.inCut(idx)) 
					{
						set.add(t);
					}
				}
			}
		}
		graph = null;
		if (set.isEmpty()) { return null; }
		return set;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 0;
	}

}
