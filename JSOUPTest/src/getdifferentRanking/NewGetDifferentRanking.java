package getdifferentRanking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import StaticField.StaticFieldForDifferentRanking;

/**
 * 複数日時の検索順位の変動を調べる
 * 調べる方法は,URLが検索順位の高い順に入ったファイルを見ていく（その中にURLが無かった場合,順位は-1とする）

 * @author kakelab
 *
 */
public class NewGetDifferentRanking {
	public static void main(String[] args) throws IOException{
		String oneFileName;
		String compareFileName;
		List<UrlAndSearchRankings> rankings = new ArrayList<UrlAndSearchRankings>();
		List<String> queryLists = GetQuery.getQuery();
		List<String> directoryName = GetDirectory.getDirectoryName();
		int directoryCount = GetDirectory.getDirectoryCount();
		for(int i = 0; i < queryLists.size();i++){
			String query = queryLists.get(i);
			for(int j = 0; j < directoryCount; j++){				
				File file = getFile(query,directoryName.get(i));
				changeRankings(rankings,file,i);
			}
			createDifferentFiles(rankings,query);
		}
		

		
	/*
		for(int j = 0; j < queryLists.size(); j++){
			String query = queryLists.get(j);
			if(StaticFieldForDifferentRanking.G_FLAG){
				oneFileName = "data" + File.separatorChar + 
							  StaticFieldForDifferentRanking.ONE_FOLDER + File.separatorChar + 
						      "google" + File.separatorChar +
					          query+ ".txt";
				compareFileName = "data" + File.separatorChar + 
					              StaticFieldForDifferentRanking.OTHER_FOLDER + File.separatorChar + 
					              "google" + File.separatorChar +
					              query + ".txt";
			}else{
				oneFileName = "data" + File.separatorChar +
			   		          StaticFieldForDifferentRanking.ONE_FOLDER + File.separatorChar + 
					          "yahoo" + File.separatorChar +
					          query + ".txt";
				compareFileName = "data" + File.separatorChar + 
					              StaticFieldForDifferentRanking.OTHER_FOLDER + File.separatorChar + 
					              "yahoo" + File.separatorChar +
					              query + ".txt";			
			}
		
			try {
				oneUrlLists = getUrlLists(oneFileName);
				compareUrlLists = getUrlLists(compareFileName);
				System.out.println(oneUrlLists.size());
				System.out.println(compareUrlLists.size());
			} catch (IOException e) {
			// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		
			findDifferent(oneUrlLists,compareUrlLists,query);
		}
	*/
		
	}
	
	private static void changeRankings(List<UrlAndSearchRankings> rankings,File file,int dayCount) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
		String line = null;
		System.out.println(file);
		int rank = 1;
		while((line = br.readLine()) != null){
			boolean isContain = false;
			for(int i = 0; i < rankings.size(); i++){
				System.out.println(line);
				if(rankings.get(i).getUrl().equals(line)){
					rankings.get(i).addRanking(rank);
					isContain = true;
				}
			}
			if(!isContain){
				rankings.add(new UrlAndSearchRankings(line));
				for(int i = 0; i < dayCount; i++){
					rankings.get(rankings.size() - 1).addRanking(-1);
				}
				rankings.get(rankings.size() - 1).addRanking(rank);
			}
			
			rank += 1;
		}
		
		
	}

	/**
	 * queryとfileNameからその引数に対応するfileを作る
	 * 具体的には,directoryName // google // query + .txt
	 * になる
	 * @param query
	 * @param directoryName
	 * @return
	 */
	private static File getFile(String query,String directoryName){
		String engine = StaticFieldForDifferentRanking.G_FLAG ? "google" : "yahoo";
		String fileName = directoryName + File.separatorChar +
				  engine + File.separatorChar +
				  query + ".txt";
		return new File(fileName);
	}
	
	private static void createDifferentFiles(List<UrlAndSearchRankings> rankings,String query){
		String fileName = StaticFieldForDifferentRanking.DATA_DIRECTORY + File.separatorChar +
				          "different" + File.separatorChar +
				          query + ".txt";

		File differentWriteFile = new File(fileName);
		differentWriteFile.getParentFile().mkdirs();
		PrintWriter pageBak = null;
		try {
			pageBak = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(differentWriteFile),"utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		for(int i = 0; i < rankings.size(); i++){
			pageBak.print(rankings.get(i).output());
		}
		pageBak.close();
	}
	/**
	 * 引数のファイルから,ファイルに書かれた内容を取り出したリストを作る
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static List<String> getUrlLists(String fileName) throws IOException{
		List<String> returnValue = new ArrayList<String>();	
		BufferedReader br = null;
		String line = "";
		

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		while((line = br.readLine()) != null){
			returnValue.add(line);
		}
		br.close();
		return returnValue;
	}
	
	private static void findDifferent(List<String> oneUrlLists,List<String> compareUrlLists,String query){
		List<String> differentUrl = new ArrayList<String>();
		List<String> outputEndUrl = new ArrayList<String>();
		List<Integer> beforeRanking = new ArrayList<Integer>();
		List<Integer> afterRanking = new ArrayList<Integer>();
		//jの値を保存するために使う
		int temp;
		for(int i = 0;i < oneUrlLists.size(); i++){
			temp = -1;
			if(outputEndUrl.contains(oneUrlLists.get(i))){
				continue;
			}
			for(int j = 0; j < compareUrlLists.size(); j++){

				if(oneUrlLists.get(i).equals(compareUrlLists.get(j))){
					temp = j;
					break;
				}
				
			}
			if(i != temp){
				if(temp != -1){
					outputEndUrl.add(oneUrlLists.get(i));
					differentUrl.add(oneUrlLists.get(i));
					beforeRanking.add(i+1);
					afterRanking.add(temp+1);
				}else{
					differentUrl.add(oneUrlLists.get(i));
					outputEndUrl.add(oneUrlLists.get(i));
					beforeRanking.add(i+1);
					afterRanking.add(temp);					
				}
			}
		}
		File differentWriteFile = new File("data" + File.separatorChar + StaticFieldForDifferentRanking.ONE_DATE + "_" + StaticFieldForDifferentRanking.OTHER_DATE + File.separatorChar +query+ "_different.txt");
		differentWriteFile.getParentFile().mkdirs();
		PrintWriter pageBak = null;
		try {
			pageBak = new PrintWriter( new BufferedWriter(new OutputStreamWriter(new FileOutputStream(differentWriteFile),"utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		pageBak.print("URL" + "　");
		pageBak.print(StaticFieldForDifferentRanking.ONE_DATE + "　");
		pageBak.print(StaticFieldForDifferentRanking.OTHER_DATE);
		pageBak.print("\n");
		for(int i = 0;i < differentUrl.size(); i++){
			pageBak.print(differentUrl.get(i) + " ");
			pageBak.print(beforeRanking.get(i) + " ");
			pageBak.print(afterRanking.get(i));
			pageBak.print("\n");
		}
		pageBak.close();

	}
}
