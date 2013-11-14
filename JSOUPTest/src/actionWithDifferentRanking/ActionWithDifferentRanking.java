package actionWithDifferentRanking;
import getdifferentRanking.GetQuery;

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
		List<String> queryLists = null;
		try {
			queryLists = GetQuery.getQuery();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		for(int k = 0; k < queryLists.size(); k++){
			if(k <= 12){continue;}
			String query = "SEO";
		try {
			getRankings(query);
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
				StaticFieldForDifferentRanking.actionWithDiferentRanking[j].init(beforeRank, afterRank, query);
				try{
					StaticFieldForDifferentRanking.actionWithDiferentRanking[j].output();
				}catch(Exception e){
					continue;
				}
			}
			System.out.println(i + "is finish");
		}
		if(k <= 14){break;}
		}

	}
	
	public static void getRankings(String query) throws IOException{
		System.out.println("query=" + query);
		File differentRankings = new File("data" + File.separatorChar +
				                          StaticFieldForDifferentRanking.SAVE_DERECTORY + File.separatorChar +
				                          query + "_different.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(differentRankings));
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		String line = br.readLine();
		while((line = br.readLine()) != null){
			String[] letters = line.split(" ",0);
			urls.add(letters[0]);
			try{
			beforeRanking.add(Integer.valueOf(letters[1]));
			afterRanking.add(Integer.valueOf(letters[2]));
			System.out.print(Integer.valueOf(letters[1]));
			System.out.println(":" +  Integer.valueOf(letters[2]));
			}catch(NumberFormatException e){
				continue;
			}
		}

	}
}
