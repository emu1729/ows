package Count;

import java.util.*;

public class FrequentWords {
	
	public FrequentWords(){
	}
	
	/**
	 * 
	 * @param text
	 * @param pattern
	 * @return number of pattern in text
	 */
	public static int FrequentWordCount(String text, String pattern) {
		// TODO Auto-generated constructor stub
		int count = 0;
		for(int i = 0; i <= (text.length() - pattern.length()); i++){
			if(text.substring(i, i+pattern.length()).equals(pattern)){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * 
	 * @param text
	 * @param k
	 * @return most k-pattern in text
	 */
	public static ArrayList<String> FrequentKCount(String text, int k){
		ArrayList<String> list = new ArrayList<String>();
		int[] count = new int[text.length()-k];
		String pattern = "";
		for(int i = 0; i < (text.length() - k); i++){
			pattern = text.substring(i, i+k);
			count[i] = FrequentWordCount(text, pattern);
		}
		int maxCount = 1;
		for(int i = 0; i < text.length()-k; i++){
			if(count[i] >= maxCount){
				maxCount = count[i];
			}
		}
		for(int i = 0; i < (text.length()-k); i++){
			if(count[i] == maxCount){
				list.add(text.substring(i, i+k));
			}
		}
		return list;
	}

}
