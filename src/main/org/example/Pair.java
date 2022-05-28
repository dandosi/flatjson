package org.example;

/**
 * Not generating equals or hashcode because they are not yet needed in this small project.
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
}
