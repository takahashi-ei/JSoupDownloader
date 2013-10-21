package StaticField;

import actionWithDifferentRanking.ActionWithDifferentRankingImp;
import actionWithDifferentRanking.WriteDifferentRankHtml;

public class StaticFieldForDifferentRanking {
	public static String ONE_DATE = "20131020";
	public static String OTHER_DATE = "20131021";
	public static String ONE_FOLDER = ONE_DATE + "SEOチェックdata";
	public static String OTHER_FOLDER = OTHER_DATE + "SEOチェックdata";
	public static String SAVE_DERECTORY = ONE_DATE + "_" + OTHER_DATE;
	
	public static String QUERY = "アジサイ";
	
	public static boolean G_FLAG = true;
	
	public static ActionWithDifferentRankingImp[] actionWithDiferentRanking = 
		{
			new WriteDifferentRankHtml()
		};
}
