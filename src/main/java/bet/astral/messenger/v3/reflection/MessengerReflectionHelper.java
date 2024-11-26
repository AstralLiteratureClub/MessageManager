package bet.astral.messenger.v3.reflection;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.translation.TranslationKey;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public interface MessengerReflectionHelper {
    /**
     * Returns the messenger used for this reflection helper
     * @return helper
     */
    Messenger getMessenger();

    /**
     * Loads translation key fields from given class and loads them in the messenger.
     * @param clazz clazz to load from
     */
    default void loadTranslations(Class<?> clazz){
        getMessenger().loadTranslations(fetchTranslations(clazz));
    }
    /**
     * Loads translation key fields from given class and loads them in the messenger.
     * @param locale locale to load using
     * @param clazz clazz to load from
     */
    default void loadTranslations(Locale locale, Class<?> clazz){
        getMessenger().loadTranslations(locale, fetchTranslations(clazz));
    }


    /**
     * Fetches all translation keys from given class.
     * @param clazz clazz to fetch from
     * @return clazz to fetch from
     */
    default List<TranslationKey> fetchTranslations(Class<?> clazz){
        List<TranslationKey> translationKeys = new LinkedList<>();
        for (Field field : clazz.getFields()){
            field.setAccessible(true);
            try {
                Object value = field.get(null);
                if (value instanceof TranslationKey translation){
                    translationKeys.add(translation);
                }
            } catch (IllegalAccessException ignored) {
            }
        }
        for (Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);
            try {
                Object value = field.get(null);
                if (value instanceof TranslationKey translation){
                    translationKeys.add(translation);
                }
            } catch (IllegalAccessException ignored) {
            }
        }
        return translationKeys;
    }
}
