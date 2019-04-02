/*
Name: Beier (Benjamin) Liu
Date: 5/17/2018

Remark:
*/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.logging.Logger;

/*===================================================================================================
File content:
A Percolation Class
===================================================================================================*/ 

public class Percolation {
	private static final Logger LOGGER = Logger.getLogger( Percolation.class.getName() );

	private WeightedQuickUnionUF grid;
	private WeightedQuickUnionUF auxGrid; 
	private boolean[] state; 
	private int n;


  // create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		// Preparation Phrase
		if (n <= 0) {
			throw new IllegalArgumentException("N must be at least 1");
		}
		int siteCount=n*n;
		this.n=n;

		grid=new WeightedQuickUnionUF(siteCount+2);
		auxGrid=new WeightedQuickUnionUF(siteCount+1);
		state=new boolean[siteCount+2];

		// Handling Phrase
		state[0]=true;
		state[siteCount+1]=true;
		for (int i=1; i<=siteCount; i++){
			state[i]=false;
		}  
		// Checking Phrase
 }  

	public int gridToArray(int row, int col){
		// Preparation Phrase
		if (row<1 || row>n){
			throw new IllegalArgumentException("row must be between 1 and N (included)");
		}
		if (col<1 || col>n){
			throw new IllegalArgumentException("col must be between 1 and N (included)");
		}  

		// Handling Phrase
		return (row-1)*n+col;

		// Checking Phrase
	}

	private boolean isTopSite(int index) {
		// Handling Phrase
		return index<=n; 
	}

	private boolean isBottomSite(int index){
		// Handling Phrase
		return index>=(n-1)*n+1;
	}

	// open site (row, col) if it is not open already      
	public    void open(int row, int col){
		// Preparation Phrase
		int idx=gridToArray(row, col);
		state[idx]=true;

		// Handling Phrase
		if (row != 1 && isOpen(row-1, col)) {
			grid.union(idx, gridToArray(row-1, col));
			auxGrid.union(idx, gridToArray(row-1, col));
		}
		if (row != n && isOpen(row+1, col)) {
			grid.union(idx, gridToArray(row+1, col));
			auxGrid.union(idx, gridToArray(row+1, col));
		}
		if (row != 1 && isOpen(row, col-1)) {
			grid.union(idx, gridToArray(row, col-1));
			auxGrid.union(idx, gridToArray(row, col-1));
		}
		if (row != n && isOpen(row, col+1)) {
			grid.union(idx, gridToArray(row, col+1));
			auxGrid.union(idx, gridToArray(row, col+1));
		}
		// if site is on top or bottom, connect to corresponding virtual site.
		if (isTopSite(idx)) {
			grid.union(0, idx);
			auxGrid.union(0, idx);
		}
		if (isBottomSite(idx)){
			grid.union(state.length-1, idx);
		}

		// Checking Phrase
 }

	// is site (row, col) open? 
	public boolean isOpen(int row, int col) {
		// Preparation Phrase
		int idx=gridToArray(row, col);
		// Handling Phrase
		return state[idx];
	} 

	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		// Preparation Phrase
		int idx=gridToArray(row, col);
		// Handling Phrase
		return grid.connected(0, idx) && auxGrid.connected(0, idx);
	} 
	// number of open sites
	public int numberOfOpenSites() {
		// Preparation Phrase
		int numOpen=0;

		// Handling Phrase
		for (int i=1; i<=n*n; i++){
			if (state[i]==true) {
				numOpen=numOpen+1;
			}
		}

		// Checking Phrase
		return numOpen;
	} 

	// does the system percolate?     
	public boolean percolates() {
		// Handling Phrase
		return grid.connected(0, state.length-1);
	}
 
//    public static void main(String[] args) // test client (optional)
}
