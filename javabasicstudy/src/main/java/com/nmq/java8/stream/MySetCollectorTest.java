package com.nmq.java8.stream;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author niemengquan
 * @create 2019/5/9
 * @modifyUser
 * @modifyDate
 */
public class MySetCollectorTest<T> implements Collector<T,Set<T>,Map<T,T>> {
    @Override
    public Supplier<Set<T>> supplier() {
        return null;
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        return null;
    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
        return null;
    }

    @Override
    public Function<Set<T>, Map<T, T>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
