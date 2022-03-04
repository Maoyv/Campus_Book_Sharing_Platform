package tsl.campus_book_sharing_platform.uitl;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TSL
 * token生成和验证
 */
@Component
public class JwtUitls {
    /**
     * 过期时间 2小时
     */
    private static final long EXPIRE_TIME=2*60*60*1000;
    /**
     * 加密密钥
     */
    private static final String KEY = "token";
    private static final String ID = "token";
    /**
     * 生成token
     * @param userId  用户ID
     * @return
     */
    public static String createToken(String userId){
        Map<String,Object> header = new HashMap();
        header.put("typ","JWT");
        header.put("alg","HS256");
        //setID:用户ID
        //setExpiration:token过期时间  当前时间+有效时间
        //setSubject:用户名
        //setIssuedAt:token创建时间
        //signWith:加密方式
        JwtBuilder builder = Jwts.builder().setHeader(header)
                .setId(ID)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                .setSubject(userId)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,KEY);
        return builder.compact();
    }

    /**
     * 验证token是否有效
     * @param token  请求头中携带的token
     * @return  token验证结果  2-token过期；1-token认证通过；0-token错误；-1-无token
     */
    public static int verify(String token,HttpServletRequest request){
        if(token == null || token.equals("")){
            return -1;
        }
        Claims claims = null;
        try {
            //token过期后，会抛出ExpiredJwtException 异常，通过这个来判定token过期，
            claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return 2;
        }
        //从token中获取ID,验证ID是否正确
        String userIds = (String) request.getSession().getAttribute("userId");
        if(userIds == null || userIds.equals("")){
            return -1;
        }
        String id = claims.getId();
        String userId = claims.getSubject();
        if(ID.equals(id) && userId.equals(userIds)){
            return 1;
        }else{
            return 0;
        }
    }

}
