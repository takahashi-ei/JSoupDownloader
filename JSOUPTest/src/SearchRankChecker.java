import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;


public abstract class SearchRankChecker {
	final String checkUrl = "http://kouren1.web.fc2.com/";
	final String userAgent = "Mozilla/5.0 (Windows NT 6.1; rv:23.0) Gecko/20100101 Firefox/23.0";
	LinkedHashMap<String, ArrayList<Integer>> ranks = new LinkedHashMap<String, ArrayList<Integer>>();


	protected void rankCheck(String filename){
		readResultFile(filename);
		Set<String> keywords = ranks.keySet();

		BufferedWriter log = null;
		try {
			log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename.replace(".csv", ".log"), true), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for( String keyword : keywords ){
			//int rank = getSearchRank(keyword);
			getUrl(keyword);
			//ranks.get(keyword).add(rank);
			//System.out.println(keyword+":"+rank);
			//try {
			//	Date date = new Date();
			//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//	sdf.setTimeZone(TimeZone.getTimeZone("JST"));

			//	log.write(sdf.format(date)+"\t"+keyword);
			//	log.newLine();
			//} catch (IOException e) {
			//	e.printStackTrace();
			//}
		}

		//writeResultFile(filename);

		//try {
		//	log.close();
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}


	protected void readResultFile(String filename){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
			String line = "";
			while ( ( line = br.readLine() ) != null ){
				String[] data = line.split(",");
				ArrayList<Integer> rank = new ArrayList<Integer>();
				for( int i = 1 ; i<data.length ; i++ ){
					rank.add(Integer.parseInt(data[i]));
				}
				ranks.put(data[0], rank);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	protected String getHtml(String openUrl){
		String result = "";
		BufferedReader br = null;
		try {
			URL url = new URL(openUrl);
			if( openUrl.matches("https") ){
				HttpsURLConnection  http = (HttpsURLConnection)url.openConnection();
				http.setRequestMethod("GET");
				http.setRequestProperty("User-Agent", userAgent);
				http.connect();
				br = new BufferedReader(new InputStreamReader(new BufferedInputStream(http.getInputStream()), "utf-8"));
			}else{
				HttpURLConnection  http = (HttpURLConnection) url.openConnection();
				http.setRequestMethod("GET");
				http.setRequestProperty("User-Agent", userAgent);
				http.connect();
				br = new BufferedReader(new InputStreamReader(new BufferedInputStream(http.getInputStream()), "utf-8"));
			}

            String line;
			while ( (line = br.readLine()) != null ){
				result = result + line;
			}
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}


	protected int getUrlRank(String patternText, String result){
		int rank = 1;
		Pattern pattern = Pattern.compile(patternText);
		Matcher matcher = pattern.matcher(result);

		while(matcher.find()) {
			String urlText = matcher.group(2);
			if( urlText.matches(".*"+checkUrl+".*") ){
				return rank;
			}else{
				rank++;
			}
		}

		return -1;
	}


	abstract int getSearchRank(String keyword);
	
	
	abstract void getUrl(String keyword);


	protected void writeResultFile(String filename){
		Set<String> keywords = ranks.keySet();

		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"));

			for( String keyword : keywords ){
				bw.write(keyword);

				ArrayList<Integer> data = ranks.get(keyword);
				for( int rank : data ){
					bw.write(","+rank);
				}

				bw.newLine();
			}

			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
