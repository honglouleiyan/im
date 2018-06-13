package com.jihuiduo.msgserver.protocol.http;

/**
 * Type safe enumeration representing HTTP protocol version
 */
public enum HttpVersion {
    /**
     * HTTP 1/1
     */
    HTTP_1_1("HTTP/1.1"),

    /**
     * HTTP 1/0
     */
    HTTP_1_0("HTTP/1.0");

    private final String value;

    private HttpVersion(String value) {
        this.value = value;
    }

    /**
     * Returns the {@link HttpVersion} instance from the specified string.
     * 
     * @return The version, or <code>null</code> if no version is found
     */
    public static HttpVersion fromString(String string) {
        if (HTTP_1_1.toString().equalsIgnoreCase(string)) {
            return HTTP_1_1;
        }

        if (HTTP_1_0.toString().equalsIgnoreCase(string)) {
            return HTTP_1_0;
        }

        return null;
    }

    /**
     * @return A String representation of this version
     */
    @Override
    public String toString() {
        return value;
    }

}

