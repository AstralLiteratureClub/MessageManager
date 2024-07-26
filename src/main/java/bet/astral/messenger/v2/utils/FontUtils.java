package bet.astral.messenger.v2.utils;

import net.kyori.adventure.text.Component;
import org.intellij.lang.annotations.RegExp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FontUtils {
	public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final char[] ALPHABET_SPLIT = ALPHABET.toCharArray();
	private static final Map<String, Pattern> compiledPatterns = new HashMap<>();
	public static final String SMALL_ALPHABET = "ᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢ";
	public static final char[] SMALL_ALPHABET_SPLIT = SMALL_ALPHABET.toCharArray();
	public static void main(String[] args){
		System.out.println(asSmallLetters("AAAAAAAA"));
		System.out.println(asSmallLetters("AAAAAAogasdkAAf"));
		System.out.println(asSmallLetters("AAAAAdaaAAdaA"));
		System.out.println(asSmallLetters("Abd2321k31kpö3n.sd,AAAAAAA"));
	}
	public static String asSmallLetters(String s){
		for (int i = 0; i < ALPHABET_SPLIT.length; i++){
			char letter = ALPHABET_SPLIT[i];
			char letterLower = SMALL_ALPHABET_SPLIT[i];
			@RegExp String match = "(?i)"+letter;
			String replace = String.valueOf(letterLower);
			s = s.replaceAll(match, replace);
		}
		return s;
	}
	public static Component asSmallLetters(Component component){
		for (int i = 0; i < ALPHABET_SPLIT.length; i++){
			char letter = ALPHABET_SPLIT[i];
			char letterLower = SMALL_ALPHABET_SPLIT[i];
			@RegExp String match = "(?i)"+letter;
			String replace = String.valueOf(letterLower);
			Pattern pattern = compiledPatterns.get(match);
			if (pattern == null){
				pattern = Pattern.compile(match);
				compiledPatterns.put(match, pattern);
			}
			final Pattern finalPattern = pattern;
			component = component.replaceText((builder)->{
				builder.match(finalPattern)
						.replacement(replace);
			});
		}
		return component;
	}
}
