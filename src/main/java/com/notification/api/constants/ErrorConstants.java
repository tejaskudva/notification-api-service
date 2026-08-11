package com.notification.api.constants;

public interface ErrorConstants {

    String TEMPLATE_ALREADY_EXISTS = "Template already exists with given name";
    String TEMPLATE_DOES_NOT_EXIST = "Template does not exist for given ID";
    String TEMPLATE_ID_REQUIRED = "Template ID is required";
    String CACHE_PUT_ERROR = "Error while caching data";
    String CACHE_GET_ERROR = "Error while fetching data from cache";

    String TEMPLATE_NAME_VALIDATION_BREACH = "Max template name length should be 100";
    String TEMPLATE_MSG_VALIDATION_BREACH = "Max message template length should be 10k";
    String TEMPLATE_VARS_VALIDATION_BREACH = "Max template variable is missing or length greater than 20";
    String TEMPLATE_UPDATE_EMPTY_VALIDATION_BREACH = "No fields provided for updating template";

}
