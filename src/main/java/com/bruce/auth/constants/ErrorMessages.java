package com.bruce.auth.constants;

public final class ErrorMessages {
    public static class Client {
        public static final String UNAUTHORIZED_ACCESS = "error.client.unauthorized_access";
        public static final String ONLY_SELF_ACCESS = "error.client.only_self_access";
        public static final String ONLY_SELF_UPDATE = "error.client.only_self_update";
        public static final String ONLY_SELF_DELETE = "error.client.only_self_delete";
        public static final String NOT_FOUND = "error.client.not_found";
        public static final String REGISTRATION_FAILED = "error.client.registration_failed";
        public static final String LOGIN_FAILED = "error.client.login_failed";
    }

    public static class General {
        public static final String INTERNAL_SERVER_ERROR = "error.general.internal_server_error";
        public static final String BAD_REQUEST = "error.general.bad_request";
        public static final String VALIDATION_ERROR = "error.general.validation_error";
    }

    public static class Email{
       public static final String CODE_NOT_FOUND = "error.email.verification_code_not_found";
       public static final String CODE_INVALID = " error.email.verification_code_invalid";
       public static final String CODE_EXPIRED = "error.email.verification_code_expired";
       public static final String EMAIL_IN_USE = "error.email.in_use";
       public static final String EMAIL_NOT_FOUND = "error.email.not_found";
       public static final String SENDING_FAILED = "error.email.sending_failed";
    }

    private ErrorMessages() {} // Prevent instantiation
}