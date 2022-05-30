package org.example;

import java.util.Objects;

/**
 * Needed utility class
 */
class Pair {
    protected String key;
    protected Object value;

    protected Pair(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }

    /**
     * Generated from a template
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return key.equals(pair.key) && value.equals(pair.value);
    }

    /**
     * Generated from a template
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
