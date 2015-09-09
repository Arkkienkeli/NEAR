import java.util.*;

public class NearLogicClass {
	public static LinkedList<String> iam; //List of WhoAmI hashtags
	public static LinkedList<String> ilookfor; //List of WhomINeed hashtags
	public static int mode; //0 for DoNotDisturb, 1 for Passive, 2 for Active
	public static float threshold;
	/**
	 * @param args
	 */
	
	//Constructs from LinkedLists of hashtags
	public NearLogicClass(
			LinkedList<String> whomi, LinkedList<String> whomineed,
			int searchmode, float t
			) {
		iam = new LinkedList<String>(whomi);
		ilookfor = new LinkedList<String>(whomineed);
		mode = searchmode;
		threshold = t;
	}
	
	//Constructs from Strings
	public NearLogicClass(String whomi, String whomineed, int searchmode, float t) {
		NearLogicClass.iam = readHashtagList(whomi);;
		NearLogicClass.ilookfor = readHashtagList(whomineed);
		NearLogicClass.mode = searchmode;
		NearLogicClass.threshold = t;
	}
	
	//Constructs with empty linked lists
	public NearLogicClass(int searchmode, float t) {
		NearLogicClass.iam = new LinkedList<String> ();
		NearLogicClass.ilookfor = new LinkedList<String> ();
		NearLogicClass.mode = searchmode;
		NearLogicClass.threshold = t;
	}
	
	//getImportance("#BIGBOOBS!!!") = 8
	private static int getImportance (String ht) {
		String hashtag = new String(ht);
		//System.out.println(ht);
		int lbefore = hashtag.length();
		//System.out.println(hashtag);
		hashtag = hashtag.replaceAll("!", "");
		int lafter = hashtag.length();
		//System.out.println("lbefore");
		//System.out.println(lbefore);
		//System.out.println("lafter");
		//System.out.println(lafter);
		int importance = (int) Math.pow(2, (lbefore-lafter));
		//System.out.println(importance);
		return(importance);
	}
	

	public static String packHashtags (LinkedList<String> hashtags) {
		String result = "";
		for(String tag : hashtags) {
			result += tag + " ";
		}
		return(result);
	}

	//getHashTag("#BIGBOOBS!!!") = "#BIGBOOBS"
	private static String getHashTag (String ht) {
		String hashtag = new String(ht);
		hashtag = hashtag.replaceAll("!", "");
		//System.out.println(hashtag);
		return(hashtag);
	}
	
	//Computes score in percents.
	public static float computeScore(
			LinkedList<String> needs, LinkedList<String> propositions
			) {
		int score = 0;
		int maximp = 0;
		for(String ht : needs) {
			String noimp = getHashTag(ht);
			int imp = getImportance(ht);
			if (propositions.contains(noimp)) {
				//System.out.println(noimp);
				//System.out.println(imp);
				score += imp;
			}
			maximp += imp;
		}
		float normalizedImportance = (float) score / (float) maximp * 100;
		return(normalizedImportance);
	}
	
	//Returns true if it is possible to match.
	public static boolean match(LinkedList<String> needs, LinkedList<String> props) {
		float score = computeScore(needs, props);
		boolean matchP = false;
		if (score > threshold) {
			if (mode == 2) {
				matchP = true;
			}
		}
		else if (score == threshold) {
			if (mode == 1 || mode == 2) {
				matchP = true;
			}
		}
		return(matchP);
	}
	
	public static boolean match(String needs, String props) {
		LinkedList<String> a = readHashtagList(needs);
		LinkedList<String> b = readHashtagList(props);
		return(match(a,b));
	}
	
	public static boolean matchForMe(LinkedList<String> props) {
		return(match(ilookfor, props));
	}
	
	public static boolean matchForMe(String props) {
		return(matchForMe(readHashtagList(props)));
	}
	
	public static boolean matchMe(LinkedList<String> needs) {
		return(match(needs, iam));
	}
	
	public static boolean matchMe(String needs) {
		return(matchMe(readHashtagList(needs)));
	}
	
	public static LinkedList<String> readHashtagList(String data) {
		LinkedList<String> result = new LinkedList<String>();
		String[] splat = data.split(" ");
		for(String s : splat) {
			if (s.startsWith("#")) {
				result.add(s);
			}
		}
		return(result);
	}
	/*
	public static void main(String[] args) {
		String testneeds = "Ебать как я хочу #ламповая_няша! с #большие_сиськи!!!!";
		String testmeprops = "Мне #двадцать_лет и я #бородатый #тролль #лжец ношу #маска_гая_фокса";
		String testtyanprops = "Я #ламповая_няша с #первый_размер #рыженькая #художница люблю #построк пеку #оладушки";
		String testtyanneeds = "Хочу #высокий #бородатый #много_бабок #альфач #большой_хуец";
		NearLogicClass NLC = new NearLogicClass(testmeprops, testneeds, 2, 10);
		LinkedList<String> tn = NearLogicClass.readHashtagList(testtyanprops);
		boolean i = NearLogicClass.matchMe(testtyanneeds);
		//for(String o : NearLogicClass.iam) {
			//System.out.println(o);
		//}
		System.out.println(i);
		//System.out.println(NLC.getHashTag("#bigtits!!!"));
		//System.out.println(NLC.getImportance("#bigtits!!!"));
	}*/
}
