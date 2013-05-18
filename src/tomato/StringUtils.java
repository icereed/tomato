package tomato;

public class StringUtils {

	public static String formatSecondsToMinutes(double seconds){
		return (int)(seconds/60) + ":" + (Math.round((double) (seconds%60)*1000))/ 1000D;
		}
	
}
