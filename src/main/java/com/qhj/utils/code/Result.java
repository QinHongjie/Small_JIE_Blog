package com.qhj.utils.code;

/**
 * 封装返回参数
 */
public class Result {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    private int code;
    private String message;
    private Object data;

    public Result() {
    }
    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result success() {

        return Result.builder().code(ResultCode.SC_OK).message(DEFAULT_SUCCESS_MESSAGE).build();
    }

    public static Result success(Object data) {
        return Result.builder().code(ResultCode.SC_OK).message(DEFAULT_SUCCESS_MESSAGE).data(data).build();
    }

    public static Result fail(String message) {
        return Result.builder().code(ResultCode.SC_SERVICE_ERROR).message(message).build();
    }

    public static Result exception(String message) {
        return Result.builder().code(ResultCode.SC_THROW_ERROR).message(message).build();
    }

    public static Result.ResultBuilder builder() {
        return new Result.ResultBuilder();
    }

    public static class ResultBuilder {
        private int code;
        private String message;
        private Object data;
        ResultBuilder() {}

        public Result.ResultBuilder code(int code) {
            this.code = code;
            return this;
        }
        public Result.ResultBuilder message(String message) {
            this.message = message;
            return this;
        }
        public Result.ResultBuilder data(Object data) {
            this.data = data;
            return this;
        }
        public Result build() {
            return new Result(this.code, this.message, this.data);
        }
        public String toString() {
            return "Result.ResultBuilder(code=" + this.code + ", message=" + this.message + ", data=" + this.data +")";
        }
    }
}
