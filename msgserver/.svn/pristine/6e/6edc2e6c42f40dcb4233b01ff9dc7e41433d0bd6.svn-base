package com.jihuiduo.msgserver.protocol.http;

/**
 * An <code>Enumeration</code> of all known HTTP status codes.
 */
public enum HttpStatus {
    /**
     * 100 - Continue
     */
    STATUS_100(100, "100 Continue"),
    /**
     * 101 - Switching Protocols
     */
    STATUS_101(101, "101 Swtiching Protocols"),
    /**
     * 200 - OK
     */
    STATUS_200(200, "200 OK"),
    /**
     * 201 - Created
     */
    STATUS_201(201, "201 Created"),
    /**
     * 202 - Accepted
     */
    STATUS_202(202, "202 Accepted"),
    /**
     * 203 - Non-Authoritative Information
     */
    STATUS_203(203, "203 Non-Authoritative Information"),
    /**
     * 204 - No Content
     */
    STATUS_204(204, "204 No Content"),
    /**
     * 205 - Reset Content
     */
    STATUS_205(205, "205 Reset Content"),
    /**
     * 206 - Created
     */
    STATUS_206(206, "206 Partial Content"),

    /**
     * 300 - Multiple Choices
     */
    STATUS_300(300, "300 Multiple Choices"),
    /**
     * 301 - Moved Permanently
     */
    STATUS_301(301, "301 Moved Permanently"),
    /**
     * 302 - Found / Moved Temporarily
     */
    STATUS_302_FOUND(302, "302 Found"),
    STATUS_302_MOVED_TEMPORARILY(302, "302 Moved Temporarily"),
    /**
     * 303 - See Others
     */
    STATUS_303(303, "303 See Other"),
    /**
     * 304 - Not Modified
     */
    STATUS_304(304, "304 Not Modified"),
    /**
     * 305 - Use Proxy
     */
    STATUS_305(305, "305 Use Proxy"),
    /**
     * 307 - Temporary Redirect
     */
    STATUS_307(307, "307 Temporary Redirect"),

    /**
     * 400 - Bad Request
     */
    STATUS_400(400, "400 Bad Request"),
    /**
     * 401 - Unauthorized
     */
    STATUS_401(401, "401 Unauthorized"),
    /**
     * 403 - Forbidden
     */
    STATUS_403(403, "403 Forbidden"),
    /**
     * 404 - Not Found
     */
    STATUS_404(404, "404 Not Found"),
    /**
     * 405 - Method Not Allowed
     */
    STATUS_405(405, "405 Method Not Allowed"),
    /**
     * 406 - Not Acceptable
     */
    STATUS_406(406, "406 Not Acceptable"),
    /**
     * 407 - Proxy Authentication Required
     */
    STATUS_407(407, "407 Proxy Authentication Required"),
    /**
     * 408 - Request Timeout
     */
    STATUS_408(408, "408 Request Timeout"),
    /**
     * 409 - Conflict
     */
    STATUS_409(409, "409 Conflict"),
    /**
     * 410 - Gone
     */
    STATUS_410(410, "410 Gone"),
    /**
     * 411 - Length Required
     */
    STATUS_411(411, "411 Length Required"),
    /**
     * 412 - Precondition Failed
     */
    STATUS_412(412, "412 Precondition Failed"),
    /**
     * 413 - Request Entity Too Large
     */
    STATUS_413(413, "413 Request Entity Too Large"),
    /**
     * 414 - Bad Request
     */
    STATUS_414(414, "414 Request-URI Too Long"),
    /**
     * 415 - Unsupported Media Type
     */
    STATUS_415(415, "415 Unsupported Media Type"),
    /**
     * 416 - Requested Range Not Satisfiable
     */
    STATUS_416(416, "416 Requested Range Not Satisfiable"),
    /**
     * 417 - Expectation Failed
     */
    STATUS_417(417, "417 Expectation Failed"),

    /**
     * 500 - Internal Server Error
     */
    STATUS_500(500, "500 Internal Server Error"),
    /**
     * 501 - Not Implemented
     */
    STATUS_501(501, "501 Not Implemented"),
    /**
     * 502 - Bad Gateway
     */
    STATUS_502(502, "502 Bad Gateway"),
    /**
     * 503 - Service Unavailable
     */
    STATUS_503(503, "503 Service Unavailable"),
    /**
     * 504 - Gateway Timeout
     */
    STATUS_504(504, "504 Gateway Timeout"),
    /**
     * 505 - HTTP Version Not Supported
     */
    STATUS_505(505, "505 HTTP Version Not Supported");

    /** The code associated with this status, for example "404" for "Not Found". */
    private int code;

    /**
     * The line associated with this status, "HTTP/1.1 501 Not Implemented".
     */
    private String line;

    /**
     * Create an instance of this type.
     * 
     * @param code the status code.
     * @param phrase the associated phrase.
     */
    private HttpStatus(int code, String phrase) {
        this.code = code;
        line = phrase;
    }

    /**
     * Retrieve the status code for this instance.
     * 
     * @return the status code.
     */
    public int code() {
        return code;
    }

    /**
     * Retrieve the status line for this instance.
     * 
     * @return the status line.
     */
    public String line() {
        return line;
    }
}
