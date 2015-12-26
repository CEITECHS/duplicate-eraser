package om.ceitechs.duperaser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by iddymagohe on 10/28/15.
 */
public class PatternExtractorUtility {
    private static Pattern constituencyNamePattern = Pattern.compile("Jimbo [a-zA-Z ]+| Urais[ ,a-zA-Z ]+"); //DEAL WITH @RT
    private static  Pattern totalPattern=Pattern.compile("Kura[0-9 :,]{1,10}");
    private static Pattern ccmVotesPattern= Pattern.compile("\\(CCM\\)[0-9 :,]{1,10}");
    private static Pattern cmdVotesPattern= Pattern.compile("\\(Chadema\\)[0-9 :,]{1,10}");
    private static Pattern numericPattern = Pattern.compile("[0-9,]{1,10}");

    public static String extractConstituencyName(String tweet){

        Matcher matcher = constituencyNamePattern.matcher(tweet);
        String name =  matcher.find() ? matcher.group(0) : "";
        if(name.contains("Jimbo ")){
            String replaceText = name.replace("Jimbo la ","");
            String replaceText1 = replaceText.replace("Jimbo ","");
            String replaceTex2 = replaceText1.replace(" Kura","");
            String replaceTex3 = replaceTex2.replace(" Urais","");
            String replaceTex4 = replaceTex3.replace(",","");
            return  replaceTex4.replace(" ","").toLowerCase();
        }else{
            String replaceText = name.replace("Urais, ","");
            String replaceText1 = replaceText.replace("Urais ","");
            String replaceTex2 = replaceText1.replace(" Kura","");
            String replaceTex3 = replaceTex2.replace(",","");
            return  replaceTex3.replace(" ","").toLowerCase();
        }
    }

    public static long extractTotalVotes(String tweet){
       return numericExtractor(totalPattern, tweet);
    }

    public static long extractCCMTotalVotes(String tweet){
        return numericExtractor(ccmVotesPattern, tweet);
    }

    public static long extractCMDTotalVotes(String tweet){
        return numericExtractor(cmdVotesPattern, tweet);
    }

    private static long numericExtractor(Pattern strPattern, String txt){
        Matcher matcher = strPattern.matcher(txt);
        String numericAndText =  matcher.find() ? matcher.group(0) : "";
        Matcher matcherNumber = numericPattern.matcher(numericAndText);
        String numericOnly = matcherNumber.find() ? matcherNumber.group(0):"0";
        String numericNow = numericOnly.replace(",","");

        return Long.valueOf(numericNow);
    }

    public static void printExtracts(String tweet){
        System.out.println(extractConstituencyName(tweet) + " >>>> " +extractTotalVotes(tweet)+ " CCM - "+ extractCCMTotalVotes(tweet) + " CMD - " + extractCMDTotalVotes(tweet));
    }

    public static void main(String[] args) {
        printExtracts("Jaji Lubuva Matokeo ya Urais Jimbo la Lindi Mjini Kura: 38,992 @MagufuliJP (CCM): 21,088 @edwardlowassatz (Chadema): 17,607 #tanzaniadecides");
        printExtracts("Jimbo Hanang, Manyara Kura: 99,464 @MagufuliJP (CCM):  63,205 @edwardlowassatz (Chadema): 32,367 #tanzaniadecides");
        printExtracts("Jimbo la Mwanga, Kilimanjaro Kura: 41,136 @MagufuliJP (CCM):  25,738 @edwardlowassatz (Chadema): 15,148 #tanzaniadecides");
        printExtracts("Tume ya Uchaguzi Matokeo ya Urais, Kondoa Mjini Kura: 22,584 @MagufuliJP (CCM): 15,113 @edwardlowassatz (Chadema): 7,102 #tanzaniadecides");

    }
}
