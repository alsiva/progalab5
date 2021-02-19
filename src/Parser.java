import java.text.ParseException;

@FunctionalInterface
public interface Parser<T> {
    T parse(String str) throws FailedToParseException;
}
