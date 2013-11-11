package com.example.modernhome;

public class Similarity {
	/**
	 * Return the candidate closest to the specified key.
	 * @param key	Key to match.
	 * @param candidates	Candidates to match against.
	 * @return
	 */
	public static String getClosest(String key, String[] candidates) {
		int len = candidates.length;
		int distance[] = new int[len];
		for(int i = 0; i < len; i++) {
			distance[i] = Levenshtein.distance(key, candidates[i]);
		}
		
		int min = 9999;
		int pos = 0;
		for(int i = 0; i < len; i++) {
			if(distance[i] < min) {
				min = distance[i];
				pos = i;
			}
		}
		
		return candidates[pos];
	}
}
