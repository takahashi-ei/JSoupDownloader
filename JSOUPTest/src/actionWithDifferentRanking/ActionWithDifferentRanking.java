package actionWithDifferentRanking;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import StaticField.StaticFieldForDifferentRanking;


public class ActionWithDifferentRanking {
	static List<String> urls = new ArrayList<String>();
	static List<Integer> beforeRanking = new ArrayList<Integer>();
	static List<Integer> afterRanking = new ArrayList<Integer>();
	public static void main(String[] args){
		try {
			getRankings();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		for(int i = 0; i < urls.size(); i++){
			System.out.print(beforeRanking.get(i));
			System.out.println(":" +  afterRanking.get(i));
		}

		for(int i = 0; i < urls.size(); i++){
			for(int j = 0; j < StaticFieldForDifferentRanking.actionWithDiferentRanking.length; j++){
				int beforeRank = beforeRanking.get(i);
				int afterRank = afterRanking.get(i);
				String query = StaticFieldForDifferentRanking.QUERY;
				StaticFieldForDifferentRanking.actionWithDiferentRanking[j].init(beforeRank, afterRank, query);
				try{
					StaticFieldForDifferentRanking.actionWithDiferentRanking[j].output();
				}catch(Exception e){
					continue;
				}
			}
			System.out.println(i + "is finish");
		}

	}
	
	public static void getRankings() throws IOException{
		File differentRankings = new File(StaticFieldForDifferentRanking.SAVE_DERECTORY + File.separatorChar +
				                          StaticFieldForDifferentRanking.QUERY + "_different.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(differentRankings));
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		String line = br.readLine();
		while((line = br.readLine()) != null){
			String[] letters = line.split(",",0);
			urls.add(letters[0]);
			beforeRanking.add(Integer.valueOf(letters[1]));
			afterRanking.add(Integer.valueOf(letters[2]));
			System.out.print(Integer.valueOf(letters[1]));
			System.out.println(":" +  Integer.valueOf(letters[2]));
		}

	}
}
