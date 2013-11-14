package StaticField;

import actionWithDifferentRanking.ActionWithDifferentRankingImp;
import actionWithDifferentRanking.WriteDifferentRankHtml;

public class StaticFieldForDifferentRanking {
	public static String ONE_DATE = "2013_10_20";
	public static String OTHER_DATE = "2013_10_21";
	public static String ONE_FOLDER = ONE_DATE;
	public static String OTHER_FOLDER = OTHER_DATE;
	public static String SAVE_DERECTORY = ONE_DATE + "_" + OTHER_DATE;
	
	public static String DATA_DIRECTORY = "data";

	
	public static boolean G_FLAG = true;
	
	public static ActionWithDifferentRankingImp[] actionWithDiferentRanking = 
		{
			new WriteDifferentRankHtml()
		};
}
