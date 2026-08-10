package com.notification.api.constants;

public interface ErrorConstants {

    String TEMPLATE_ALREADY_EXISTS = "Template already exists with given name";

    String TEMPLATE_DOES_NOT_EXIST = "Template does not exist for given ID";

    String TEMPLATE_ID_REQUIRED = "Template ID is required";

    String CACHE_PUT_ERROR = "Error while caching data";

    String CACHE_GET_ERROR = "Error while fetching data from cache";

}
