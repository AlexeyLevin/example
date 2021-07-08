package org.example.java.feature;

public class Execute {
    private final long id;
    private final long timeout;
    private final boolean successful;

    public Execute(long id, int timeout, boolean successful) {
        this.id = id;
        this.timeout = timeout;
        this.successful = successful;
    }

    public long getId() {
        return id;
    }

    public long getTimeout() {
        return timeout;
    }

    public boolean isSuccessful() {
        return successful;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Execute{");
        sb.append("id=").append(id);
        sb.append(", timeout=").append(timeout);
        sb.append(", successful=").append(successful);
        sb.append('}');
        return sb.toString();
    }
}
