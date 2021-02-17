@FunctionalInterface
public interface Parser<T> {
    T parse(String str);
}
