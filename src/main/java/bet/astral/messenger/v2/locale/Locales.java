package bet.astral.messenger.v2.locale;

import org.jetbrains.annotations.Contract;

import java.util.Locale;

public class Locales {
	@Contract(value = " -> new", pure = true)
	public static Locale[] getAvailableLocales(){
		return Locale.getAvailableLocales();
	}
	public final static Locale FINNISH = new Locale("fi", "FI");
}
