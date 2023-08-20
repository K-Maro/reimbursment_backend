package org.example.utils;

public enum HttpMethod {

    POST, PUT, DELETE, GET, PATCH, OPTIONS;

    public static boolean isPost(String requestMethod) {
        return POST.name().equals(requestMethod);
    }

    public static boolean isDelete(String requestMethod) {
        return DELETE.name().equals(requestMethod);
    }

    public static boolean isGet(String requestMethod) {
        return GET.name().equals(requestMethod);
    }

    public static boolean isPut(String requestMethod) {
        return PUT.name().equals(requestMethod);
    }

    public static boolean isOptions(String requestMethod) {
        return OPTIONS.name().equals(requestMethod);
    }
}