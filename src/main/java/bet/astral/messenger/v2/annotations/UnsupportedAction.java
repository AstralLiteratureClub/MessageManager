package bet.astral.messenger.v2.annotations;

import java.lang.annotation.*;

/**
 * Used on methods, constructors or classes which are not supported by the developers.
 */
@Retention(RetentionPolicy.SOURCE)
@Documented
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR})
public @interface UnsupportedAction {
}
