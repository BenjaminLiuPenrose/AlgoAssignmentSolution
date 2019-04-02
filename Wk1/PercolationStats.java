/*
Name: Beier (Benjamin) Liu
Date: 5/17/2018

Remark:
*/
package Percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.text.MessageFormat;

/*===================================================================================================
File content:
A PercolationStats Class
===================================================================================================*/ 

public class PercolationStats {
	private static final Logger LOGGER = Logger.getLogger(PercolationStats.class.getName());
	private double[] threshold;

	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {

		// Preparation Phrase
		int openCount, row, col;
		if(n<=0||trials<=0){
		LOGGER.severe("Arguement out of bound");
		throw new IllegalArgumentException("Arguement out of bound");
			}

		threshold=new double[trials];

		openCount=0;

		// Handling Phrase
		for (int i=0; i<trials; i++){
			Percolation pc = new Percolation(n);
			do{
				row=StdRandom.uniform(1, n+1);
				col=StdRandom.uniform(1, n+1);
				if (pc.isOpen(row, col)){
					continue;
				}
 				pc.open(row, col);
 				openCount++;
			} while (!pc.percolates());

		threshold[i]=(double) openCount/(n*n);
		openCount=0;
		LOGGER.finest(MessageFormat.format("threshold[{}]={}\n", i, threshold[i]));
		}

	// Checking Phrase
	} 

	// sample mean of percolation threshold  
	public double mean() {
 	// Handling Phrase
 	return StdStats.mean(threshold);
	}      
	// sample standard deviation of percolation threshold                    
	public double stddev() {
 		// Handling Phrase
 		return StdStats.stddev(threshold);
	} 
	// low  endpoint of 95% confidence interval                
	public double confidenceLo(){
 		// Handling Phrase
 		return mean()-1.96*stddev()/Math.sqrt(threshold.length);
	}   
	// high endpoint of 95% confidence interval               
	public double confidenceHi(){
		// Handling Phrase
		return mean()+1.96*stddev()/Math.sqrt(threshold.length);
	}                  
	// test client (described below)
	public static void main(String[] args){
		// Preparation phrase
		PercolationStats pcs=new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

		// Handling Phrase
		LOGGER.setLevel(Level.INFO);
		LOGGER.info(MessageFormat.format("mean:  ={}\n", pcs.mean()));
		LOGGER.info(MessageFormat.format("stddev: ={}\n", pcs.stddev()));
		LOGGER.info(MessageFormat.format("95%% confidence inteval: ({}, {})", pcs.confidenceLo(), pcs.confidenceHi()));	
	}    
}
