package tsl.campus_book_sharing_platform.uitl;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author TSL
 * token验证
 */
public class UserLoginInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //默认从请求头中获得token
        String token = (String) request.getSession().getAttribute("token");
        //如果header中不存在token，则从参数中获取token
//        if(token == null || token.equals("")){
//            token = request.getParameter("token");
//        }
        // 验证token
        int i = JwtUitls.verify(token,request);
        String result;
        if(i == 1){
            return true;
        }else {
            if(i == 2){
                result = JSON.toJSONString(ResultUtil.error(2, "登录已过期！"));
            }else if(i == 0){
                result = JSON.toJSONString(ResultUtil.error(0, "验证失败！"));
            }else{
                result = JSON.toJSONString(ResultUtil.error(-1, "未登录！"));
            }
            returnJson(response, result);
            return false;
        }
    }

    private void returnJson(HttpServletResponse response, String result) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(result);

        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

}
