package ida.XML;

import java.util.LinkedList;

public class validCheckerTest
{
	public static void main(String[] args)
	{
		LinkedList<String> keywords = new LinkedList<String>();
		
		keywords.add("WHAT");
		keywords.add("HOW");
		keywords.add("ARE");
		
		for(String keyword: keywords)
		{
			System.out.println("Testing the word: "+keyword);
			if (keyword != "WHAT" && keyword != "HOW" && keyword != "ARE"
					&& keyword != "WHY" && keyword != "YOU" && keyword != "WHO" )
			{
				System.out.println("Test Passed!");
			}
			else
			{
				System.out.println("Test failed!");
			}
		}
		
	}

}
