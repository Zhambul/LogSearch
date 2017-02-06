package ru.zhambul.logsearch.type.converter;

/**
 * Created by zhambyl on 26/01/2017.
 */
public interface Converter<I, O> {

    O convert(I input);

    default <T> Converter<I, T> chain(Converter<? super O, ? extends T> next) {
        return input -> next.convert(this.convert(input));
    }
}
