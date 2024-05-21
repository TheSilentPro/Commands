package tsp.commands.command.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @see java.util.Optional
 *
 * @author Java, TheSilentPro (Silent)
 * Type parameters: <T> – the type of value, <C> - the context.
 */
public class BiOptional<T, C> {

    /**
     * If non-null, the value; if null, indicates no value is present
     */
    private final T value;
    private final C context;

    /**
     * Returns an empty {@code BiOptional} instance.  No value is present for this
     * {@code BiOptional}.
     *
     * @apiNote
     * Though it may be tempting to do so, avoid testing if an object is empty
     * by comparing with {@code ==} or {@code !=} against instances returned by
     * {@code BiOptional.empty()}.  There is no guarantee that it is a singleton.
     * Instead, use {@link #isEmpty()} or {@link #isPresent()}.
     *
     * @param <T> The type of the non-existent value
     * @return an empty {@code BiOptional}
     */
    public static <T, C> BiOptional<T, C> empty(C context) {
        return new BiOptional<>(null, context);
    }

    /**
     * Returns an empty {@code BiOptional} instance.  No value, nor context, is present for this
     * {@code BiOptional}.
     *
     * @return Completely empty.
     * @param <T> The type of the non-existent value
     * @param <C> The type of the non-existent context value
     * @deprecated The point of this class is to have context, without it an {@link java.util.Optional} should suffice.
     */
    @Deprecated
    public static <T, C> BiOptional<T, C> empty() {
        return empty(null);
    }

    /**
     * Constructs an instance with the described value.
     *
     * @param value the value to describe; it's the caller's responsibility to
     *        ensure the value is non-{@code null} unless creating the singleton
     *        instance returned by {@code empty()}.
     * @param context the context for this optional.
     */
    public BiOptional(T value, C context) {
        this.value = value;
        this.context = context;
    }

    /**
     * Returns an {@code BiOptional} describing the given non-{@code null}
     * value.
     *
     * @param value the value to describe, which must be non-{@code null}
     * @param <T> the type of the value
     * @return an {@code BiOptional} with the value present
     * @throws NullPointerException if value is {@code null}
     */
    public static <T, C> BiOptional<T, C> of(T value, C context) {
        return new BiOptional<>(Objects.requireNonNull(value), context);
    }

    /**
     * Returns an {@code BiOptional} describing the given value, if
     * non-{@code null}, otherwise returns an empty {@code BiOptional}.
     *
     * @param value the possibly-{@code null} value to describe
     * @param <T> the type of the value
     * @return an {@code BiOptional} with a present value if the specified value
     *         is non-{@code null}, otherwise an empty {@code BiOptional}
     */
    public static <T, C> BiOptional<T, C> ofNullable(T value, C context) {
        return value == null ? new BiOptional<>(null, context) : new BiOptional<>(value, context);
    }

    /**
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     *
     * @apiNote
     * The preferred alternative to this method is {@link #orElseThrow()}.
     *
     * @return the non-{@code null} value described by this {@code BiOptional}
     * @throws NoSuchElementException if no value is present
     */
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * Returns the possibly null context.
     *
     * @return Possibly null context.
     */
    public C getContext() {
        return context;
    }

    /**
     * If a value is present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if a value is present, otherwise {@code false}
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * If a value is  not present, returns {@code true}, otherwise
     * {@code false}.
     *
     * @return  {@code true} if a value is not present, otherwise {@code false}
     * @since   11
     */
    public boolean isEmpty() {
        return value == null;
    }

    /**
     * If a value is absent, performs the given action with the context.
     *
     * @param action The action to be performed, if a value is absent.
     * @return Context
     */
    public C ifAbsent(Consumer<C> action) {
        if (value == null) {
            action.accept(context);
        }
        return context;
    }

    /**
     * If a value is present, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param action the action to be performed, if a value is present
     * @throws NullPointerException if value is present and the given action is
     *         {@code null}
     */
    public C ifPresent(Consumer<? super T> action) {
        if (value != null) {
            action.accept(value);
        }
        return context;
    }

    /**
     * If a value is present, performs the given action with the value,
     * otherwise performs the given empty-based action.
     *
     * @param action the action to be performed, if a value is present
     * @param emptyAction the empty-based action to be performed, if no value is
     *        present
     * @throws NullPointerException if a value is present and the given action
     *         is {@code null}, or no value is present and the given empty-based
     *         action is {@code null}.
     * @since 9
     */
    public C ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (value != null) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
        return context;
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * returns an {@code BiOptional} describing the value, otherwise returns an
     * empty {@code BiOptional}.
     *
     * @param predicate the predicate to apply to a value, if present
     * @return an {@code BiOptional} describing the value of this
     *         {@code BiOptional}, if a value is present and the value matches the
     *         given predicate, otherwise an empty {@code BiOptional}
     * @throws NullPointerException if the predicate is {@code null}
     */
    public BiOptional<T, C> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : empty(context);
        }
    }

    /**
     * If a value is present, returns an {@code BiOptional} describing (as if by
     * {@link #ofNullable}) the result of applying the given mapping function to
     * the value, otherwise returns an empty {@code BiOptional}.
     *
     * <p>If the mapping function returns a {@code null} result then this method
     * returns an empty {@code BiOptional}.
     *
     * @apiNote
     * This method supports post-processing on {@code BiOptional} values, without
     * the need to explicitly check for a return status.  For example, the
     * following code traverses a stream of URIs, selects one that has not
     * yet been processed, and creates a path from that URI, returning
     * an {@code BiOptional<Path>}:
     *
     * <pre>{@code
     *     BiOptional<Path> p =
     *         uris.stream().filter(uri -> !isProcessedYet(uri))
     *                       .findFirst()
     *                       .map(Paths::get);
     * }</pre>
     *
     * Here, {@code findFirst} returns an {@code BiOptional<URI>}, and then
     * {@code map} returns an {@code BiOptional<Path>} for the desired
     * URI if one exists.
     *
     * @param mapper the mapping function to apply to a value, if present
     * @param <U> The type of the value returned from the mapping function
     * @return an {@code BiOptional} describing the result of applying a mapping
     *         function to the value of this {@code BiOptional}, if a value is
     *         present, otherwise an empty {@code BiOptional}
     * @throws NullPointerException if the mapping function is {@code null}
     */
    public <U> BiOptional<U, C> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty(context);
        } else {
            return BiOptional.ofNullable(mapper.apply(value), context);
        }
    }

    /**
     * If a value is present, returns the result of applying the given
     * {@code BiOptional}-bearing mapping function to the value, otherwise returns
     * an empty {@code BiOptional}.
     *
     * <p>This method is similar to {@link #map(Function)}, but the mapping
     * function is one whose result is already an {@code BiOptional}, and if
     * invoked, {@code flatMap} does not wrap it within an additional
     * {@code BiOptional}.
     *
     * @param <U> The type of value of the {@code BiOptional} returned by the
     *            mapping function
     * @param mapper the mapping function to apply to a value, if present
     * @return the result of applying an {@code BiOptional}-bearing mapping
     *         function to the value of this {@code BiOptional}, if a value is
     *         present, otherwise an empty {@code BiOptional}
     * @throws NullPointerException if the mapping function is {@code null} or
     *         returns a {@code null} result
     */
    public <U> BiOptional<U, C> flatMap(Function<? super T, ? extends BiOptional<? extends U, C>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty(context);
        } else {
            @SuppressWarnings("unchecked")
            BiOptional<U, C> r = (BiOptional<U, C>) mapper.apply(value);
            return Objects.requireNonNull(r);
        }
    }

    /**
     * If a value is present, returns an {@code BiOptional} describing the value,
     * otherwise returns an {@code BiOptional} produced by the supplying function.
     *
     * @param supplier the supplying function that produces an {@code BiOptional}
     *        to be returned
     * @return returns an {@code BiOptional} describing the value of this
     *         {@code BiOptional}, if a value is present, otherwise an
     *         {@code BiOptional} produced by the supplying function.
     * @throws NullPointerException if the supplying function is {@code null} or
     *         produces a {@code null} result
     * @since 9
     */
    public BiOptional<T, C> or(Supplier<? extends BiOptional<? extends T, C>> supplier) {
        Objects.requireNonNull(supplier);
        if (isPresent()) {
            return this;
        } else {
            @SuppressWarnings("unchecked")
            BiOptional<T, C> r = (BiOptional<T, C>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }

    /**
     * If a value is present, returns a sequential {@link Stream} containing
     * only that value, otherwise returns an empty {@code Stream}.
     *
     * @apiNote
     * This method can be used to transform a {@code Stream} of BiOptional
     * elements to a {@code Stream} of present value elements:
     * <pre>{@code
     *     Stream<BiOptional<T>> os = ..
     *     Stream<T> s = os.flatMap(BiOptional::stream)
     * }</pre>
     *
     * @return the BiOptional value as a {@code Stream}
     * @since 9
     */
    public Stream<T> stream() {
        if (!isPresent()) {
            return Stream.empty();
        } else {
            return Stream.of(value);
        }
    }

    /**
     * If a value is present, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if no value is present.
     *        May be {@code null}.
     * @return the value, if present, otherwise {@code other}
     */
    public T orElse(T other) {
        return value != null ? value : other;
    }

    /**
     * If a value is present, returns the value, otherwise returns the result
     * produced by the supplying function.
     *
     * @param supplier the supplying function that produces a value to be returned
     * @return the value, if present, otherwise the result produced by the
     *         supplying function
     * @throws NullPointerException if no value is present and the supplying
     *         function is {@code null}
     */
    public T orElseGet(Supplier<? extends T> supplier) {
        return value != null ? value : supplier.get();
    }

    /**
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     *
     * @return the non-{@code null} value described by this {@code BiOptional}
     * @throws NoSuchElementException if no value is present
     * @since 10
     */
    public T orElseThrow() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * If a value is present, returns the value, otherwise throws an exception
     * produced by the exception supplying function.
     *
     * @apiNote
     * A method reference to the exception constructor with an empty argument
     * list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     *
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier the supplying function that produces an
     *        exception to be thrown
     * @return the value, if present
     * @throws X if no value is present
     * @throws NullPointerException if no value is present and the exception
     *          supplying function is {@code null}
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * Indicates whether some other object is "equal to" this {@code BiOptional}.
     * The other object is considered equal if:
     * <ul>
     * <li>it is also an {@code BiOptional} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code equals()}.
     * </ul>
     *
     * @param obj an object to be tested for equality
     * @return {@code true} if the other object is "equal to" this object
     *         otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return obj instanceof BiOptional<?, ?> other
                && Objects.equals(value, other.value);
    }

    /**
     * Returns the hash code of the value, if present, otherwise {@code 0}
     * (zero) if no value is present.
     *
     * @return hash code value of the present value or {@code 0} if no value is
     *         present
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "BiOptional{" +
                "value=" + value +
                ", context=" + context +
                '}';
    }

}