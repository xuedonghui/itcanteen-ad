package entity;

import java.io.Serializable;
import java.util.Collection;

/**
 * 返回统一结果集
 * Created by progr on 2020/4/7.
 */
public class Result{

    private boolean flag;
    private Integer code;
    private String message;
    private Object date;//返回数据

    public Result() {
    }

    public Result(boolean flag,Integer code, String message) {
        this.flag = flag;
        this.code=code;
        this.message = message;
    }
    public Result(boolean flag,Integer code,Object date) {
        this.flag = flag;
        this.code=code;
        this.date = date;
    }

    public Result(boolean flag,Integer code, String message, Object date) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.date = date;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }
}
