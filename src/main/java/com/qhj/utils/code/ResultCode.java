package com.qhj.utils.code;

/**
 * 返回code
 */
public interface ResultCode {

    int SC_OK = 200;

    int SC_NOT_FOUND = 404;

    int SC_INTERNAL_SERVER_ERROR = 500;

    /**
     * 数据绑定错误
     */
    int SC_PARAMETER_ERROR = 600;

    /**
     * 业务错误
     */
    int SC_SERVICE_ERROR = 601;

    int SC_THROW_ERROR = 602;

    int SC_NO_LOGIN = 603;

    int SC_FORM_ERROR = 604;

}
