package tsl.campus_book_sharing_platform.uitl;

import lombok.*;

/**
 * @author tsl
 * @version 1.0
 * 统一返回格式
 */
@Getter
@Setter
@ToString
public class ResultFormat<T> {
    /**
     * 错误码(-1：失败;200:成功)
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 内容
     */
    private T data;

}
