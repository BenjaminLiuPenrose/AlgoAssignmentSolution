import java.awt.Color;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
	private Picture picture;
	private double[][] energy;
	private int[][] parent;
	private static final double MAX_ENERGY = Double.MAX_VALUE;
	public SeamCarver(Picture picture) 
	{
		this.picture = picture;
		this.energy = new double[picture.width()][picture.height()];
		this.parent = new int[picture.width()][picture.height()];
		for (int i = 0; i < picture.width(); i++) 
		{
			for (int j = 0; j < picture.height(); j++) 
			{
				this.energy[i][j] = energy(i, j);
			}
		}
	}
	public Picture picture() 
	{
		return this.picture;
	}
	public int width() 
	{
		return picture.width();
	}
	public int height() 
	{
		return picture.height();
	}
	public double energy(int x, int y) 
	{
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) 
        {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) 
        {
            return this.MAX_ENERGY;
        }
		return Math.sqrt(
				gradient(picture.get(x+1, y), picture.get(x-1, y)) + gradient(picture.get(x, y+1), picture.get(x, y-1))
				);
	}
	private double gradient(Color a, Color b) 
	{
		return (a.getBlue() - b.getBlue())*(a.getBlue() - b.getBlue()) + (a.getGreen() - b.getGreen())*(a.getGreen() - b.getGreen()) + (a.getRed() - b.getRed())*(a.getRed() - b.getRed());
	}
	public int[] findHorizontalSeam() 
	{
		transpose();
		int[] seam = findVerticalSeam();
		transpose(); // to have efficient system arraycopy
		return seam;
	}
	public int[] findVerticalSeam()  // key method
	{
		int[] seam = new int[height()];
		double[] distTo = new double[width()];
		double[] oldDistTo = new double[width()];
		for (int y = 0; y < height(); y++) 
		{
			for (int x = 0; x < width(); y++) 
			{
				relaxVertically(x, y, distTo, oldDistTo);
			}
			System.arraycopy(distTo, 0, oldDistTo, 0, width());
		}
		double min = oldDistTo[0];
		int best = 0;
		for (int idx = 0; idx < oldDistTo.length; idx++) 
		{
			if( oldDistTo[idx] < min ) 
			{
				best = idx;
				min = oldDistTo[idx];
			}
		}
		seam[height()-1] = best;
		for (int i = height()-2; i >= 0; i--) 
		{
			seam[i] = parent[best][i+1];
			best = parent[best][i+1];
		}
		return seam;
	}
	public void removeHorizontalSeam(int[] seam) 
	{
		checkValidity(seam);
		if (seam.length > width() ) 
		{
			throw new IllegalArgumentException("The seam is greater than image width");
		}
		this.picture = removeSeam(seam, false);
		double[][] oldEnergy = this.energy;
		for (int y = 0; y < height(); y++) 
		{
			for (int x = 0; x < width(); x++) 
			{
				this.energy[x][y] = energy(x, y);
			}
		}
	}
	public void removeVerticalSeam(int[] seam) 
	{
		checkValidity(seam);
		if (seam.length > width() ) 
		{
			throw new IllegalArgumentException("The seam is greater than image width");
		}
		this.picture = removeSeam(seam, true);
		double[][] oldEnergy = this.energy;
		for (int y = 0; y < height(); y++) 
		{
			for (int x = 0; x < width(); x++) 
			{
				this.energy[x][y] = energy(x, y);
			}
		}
	}
	private void checkValidity(int[] seam) 
	{
		if ( width() <= 1 || height() <= 1 ) 
		{
			throw new IllegalArgumentException("The hight and width is smaller than 1");
		}
		if ( seam.length <= 1 ) 
		{
			throw new IllegalArgumentException("The seam size is smaller than 1");
		}
		for ( int i = 0; i < seam.length - 1; i++ ) 
		{
			if (Math.abs(seam[i] - seam[i+1]) > 1) 
			{
				throw new IllegalArgumentException("The seam is invalid");
			}
		}
	}
	private Picture removeSeam(int[] seam, boolean vertical) 
	{
		if (vertical) 
		{
			Picture p = new Picture(width() - 1, height());
			for (int y = 0; y < height(); y++) 
			{
				int k = 0;
				for (int x = 0; x < width(); x++) 
				{
					if (x != seam[y]) 
					{
						p.set(k, y, picture.get(x, y));
						k++;
					}
				}
			}
			return p;
		} else {
			Picture p = new Picture(width(), height() - 1);
			for(int y = 0; y < width(); y++) 
			{
				int k = 0;
				for (int x = 0; x < height(); x++) 
				{
					if(x != seam[y]) 
					{
						p.set(y, k, picture.get(y, x));
						k++;
					}
				}
			}
			return p;
		}
	}
	private void relaxVertically(int col, int row, double[] distTo, double[] oldDistTo) // key method
	{
		if (row == 0 ) 
		{
			distTo[col] = this.MAX_ENERGY;
			parent[col][row] = -1;
			return ;
		}
		
		if ( col == 0 ) 
		{
			double a = oldDistTo[col];
			double b = oldDistTo[col + 1];
			double min = Math.min(a, b);
			distTo[col] = min + energy[col][row];
			if ( a < min ) 
			{
				parent[col][row] = col;
			} else {
				parent[col][row] = col + 1; 
			}
			return ;
		}
		
		if ( col == width()-1 ) 
		{
			double a = oldDistTo[col];
			double b = oldDistTo[col - 1];
			double min = Math.min(a, b);
			distTo[col] = min + energy[col][row];
		}
		
		double left = oldDistTo[col - 1];
		double mid = oldDistTo[col];
		double right = oldDistTo[col + 1];
		
		double min = Math.min(Math.min(left, mid), right);
		
		distTo[col] = min + energy[col][row];
		if (min == left ) 
		{
			parent[col][row] = col - 1;
		} else if (min == mid) {
			parent[col][row] = col;
		} else {
			parent[col][row] = col + 1;
		}
	}
	private void transpose() 
	{
		Picture transposePicture = new Picture(picture.height(), picture.width());
		double[][] newEnergy = new double[picture.height()][picture.width()];
		for (int i = 0; i < picture.width(); i++)  
		{
			for (int j = 0; j < picture.height(); j++) 
			{
				transposePicture.set(j, i, picture.get(i, j));
				newEnergy[j][i] = energy[i][j];
			}
		}
		picture = transposePicture;
		energy = newEnergy;
		parent = new int[picture.width()][picture.height()];
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 0;
	}

}
