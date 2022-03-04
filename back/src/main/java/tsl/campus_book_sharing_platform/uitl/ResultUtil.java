package tsl.campus_book_sharing_platform.uitl;

/**
 * @author tsl
 * @version 1.0
 * 统一返回工具类
 */
public class ResultUtil {


    public static ResultFormat success(Object object) {
        ResultFormat result =new ResultFormat();
        result.setCode(200);
        result.setMessage("成功");
        result.setData(object);
        return result;
    }

    public static ResultFormat success() {
        return success(null);
    }

    public static ResultFormat error(Integer code, String msg) {
        ResultFormat result =new ResultFormat();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

}
