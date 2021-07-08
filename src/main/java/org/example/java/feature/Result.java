package org.example.java.feature;

public class Result {

    private final long id;

    public Result(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Result{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
