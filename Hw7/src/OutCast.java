
public class OutCast {
	private final WordNet wordnet;
	public OutCast(WordNet wordnet) 
	{
		this.wordnet = wordnet; // recall the wornet is immutable, which won't change data type
	}
	public String outcast(String[] nouns) // least related word
	{
		String outcast = null;
		int max = 0;
		for (String nounA : nouns) 
		{
			int dist = 0;
			for (String nounB : nouns) 
			{
				if ( !nounA.equals(nounB) ) 
				{
					dist += this.wordnet.distance(nounA, nounB);
				}
			}
			if( dist > max ) 
			{
				max = dist;
				outcast = nounA;
			}
		}
		return outcast;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 0;
	}
}
