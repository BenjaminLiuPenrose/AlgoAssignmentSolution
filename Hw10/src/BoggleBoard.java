import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class BoggleBoard {
	private final int M;
	private final int N;
	private char[][] board;
	
	private static final String[] boggle1992 = {"LRYTTE", "VTHRWE", "EGHWNE", "SEOTIS", "ANAEEG", "IDSYTT", "OATTOW", "MTOICU", "AFPKFS", "XLDERI",
	            "HCPOAS", "ENSIEU", "YLDEVR", "ZNRNHL", "NMIQHU", "OBBAOJ"};
	private static final String[] boggle1983 = {"AACIOT", "ABILTY", "ABJMOQ", "ACDEMP", "ACELRS", "ADENVZ", "AHMORS", "BIFORX", "DENOSW", "DKNOTU",
            "EEFHIY", "EGINTV", "EGKLUY", "EHINPS", "ELPSTU", "GILRUW",};
	private static final String[] boggleMaster = {"AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM", "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCNSTW",
            "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DHHLOR", "DHHNOT", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU", "FIPRSY", "GORRVW", "HIPRRY",
            "NOOTUW", "OOOTTU"};
	private static final String[] boggleBig = {"AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM", "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCENST",
            "CEIILT", "CEILPT", "CEIPST", "DDHNOT", "DHHLOR", "DHLNOR", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU", "FIPRSY", "GORRVW", "IPRRRY",
            "NOOTUW", "OOOTTU"};
	private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final double[] frequencies = {0.08167, 0.01492, 0.02782, 0.04253, 0.12703, 0.02228, 0.02015, 0.06094, 0.06966, 0.00153, 0.00772,
            0.04025, 0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987, 0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150, 0.01974, 0.00074};
	
	public BoggleBoard() 
	{
		this.M = 4;
		this.N = 4;
		StdRandom.shuffle(this.boggle1992);
		this.board = new char[M][N];
		for (int i = 0; i < M; i++) 
		{
			for (int j = 0; j < N; j++) 
			{
				String letters = this.boggle1992[N * i + j];
				int r = StdRandom.uniform(letters.length());
				this.board[i][j] = letters.charAt(r);
			}
		}
	}
	
	public BoggleBoard(int m, int n) 
	{
		this.M = m;
		this.N = n;
		this.board = new char[M][N];
		for (int i = 0; i < M; i++) 
		{
			for (int j = 0; j < N; j++) 
			{
				int r = StdRandom.discrete(this.frequencies);
				this.board[i][j] = this.alphabet.charAt(r);
			}
		}
	}
	
	public BoggleBoard(String filename) 
	{
		In in = new In(filename);
		this.M = in.readInt();
		this.N = in.readInt();
		this.board = new char[M][N];
		for (int i = 0; i < M; i++) 
		{
			for (int j = 0; j < N; j++) 
			{
				String letter = in.readString().toUpperCase();
				if (letter.equals("QU")) 
				{
					this.board[i][j] = 'Q';
				} else if (letter.length()!=1) 
				{
					throw new IllegalArgumentException("Invalid char " + letter);
				} else if (alphabet.indexOf(letter) == -1) 
				{
					throw new IllegalArgumentException("Invalud char " + letter);
				} else {
					this.board[i][j] = letter.charAt(0);
				}
			}
		}
	}
	
	public BoggleBoard(char[][] a) 
	{
		this.M = a.length;
		this.N = a[0].length;
		this.board = a.clone();
	}
	
	public int rows() 
	{
		return this.M;
	}
	
	public int cols() 
	{
		return this.N;
	}
	
	public char getLetter(int i, int j) 
	{
		return this.board[i][j];
	}
	
	public String toString() 
	{
		StringBuilder sb = new StringBuilder(this.M + " " + this.N + "\n");
		for (int i = 0; i < M; i ++) 
		{
			for (int j = 0; j < N; j++) 
			{
				sb.append(board[i][j]);
				if (board[i][j] == 'Q') 
				{
					sb.append("u ");
				} else {
					sb.append("  ");
				}
			}
			sb.append("\n");
		}
		return sb.toString().trim();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StdOut.println("Hasbro board:");
		BoggleBoard board1 = new BoggleBoard();
		StdOut.println(board1);
		StdOut.println();
		
		StdOut.println("Random 4-by-4 board:");
		BoggleBoard board2 = new BoggleBoard(4, 4);
		StdOut.println(board2);
		StdOut.println();
		
		StdOut.println("4-by-4 board from 2D character array:");
		char[][] a = { {'D', 'O', 'T', 'Y'}, {'T', 'R', 'S', 'F'}, {'M', 'X', 'M', 'O'}, {'Z', 'A', 'B', 'W'}};
		BoggleBoard board3 = new BoggleBoard(a);
		StdOut.println(board3);
		StdOut.println();
		
		String filename = "board-quinquevalencies.txt";
		StdOut.println("4-by-4 board from file " + filename + ":");
		BoggleBoard board4 = new BoggleBoard(filename);
		StdOut.println(board4);
		StdOut.println();
	}

}
