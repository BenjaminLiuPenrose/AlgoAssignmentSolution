package Percolatiion;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;




public class PercolationStats {
	private double[] threshold;
	
	public PercolationStats(int n, int trials) 
	{
		this.threshold = new double[trials];
		
		int cnt = 0;
		for (int i = 0; i < trials; i++) 
		{
			Percolation percolation = new Percolation(n);
			while (!percolation.percolates()) 
			{
				int row = StdRandom.uniform(1, n + 1);
				int col = StdRandom.uniform(1, n + 1);
				if (percolation.isOpen(row, col)) 
				{
					continue;
				}
				percolation.open(row, col);
				cnt += 1;
			}
			this.threshold[i] = (double) cnt / (n * n);
			cnt = 0;
		}
		
		
	}
	
	public double mean() 
	{
		return StdStats.mean(this.threshold);
	}
	
	public double stddev() 
	{
		return StdStats.stddev(this.threshold);
	}
	
	public double confidenceLo() 
	{
		return mean() + 1.96 * stddev() / Math.sqrt(this.threshold.length);
	}
	
	public double confidenceHi() 
	{
		return mean() - 1.96 * stddev() / Math.sqrt(this.threshold.length);
	}
	
	public static void main(String[] args) 
	{
//		PercolationStats pcs = new PercolationStats(
//				2, 
//				10000
//			);
		PercolationStats pcs = new PercolationStats(
					Integer.parseInt(args[0]), 
					Integer.parseInt(args[0])
				);
		
		pcs.mean();
		pcs.stddev();
		pcs.confidenceLo();
		pcs.confidenceHi();
		
	}
}
