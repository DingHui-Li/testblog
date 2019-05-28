package top.lidh.blog.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import top.lidh.blog.Entity.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class jwtUtil  {
    private String secret="sf23rit35952-flsadlkWR=WJF;LQW2RWELF;";
        public String createToken(admin admin){
            Date nowDate=new Date();
            Calendar nowTime=Calendar.getInstance();
            nowTime.add(Calendar.HOUR,1);
            Date expires=nowTime.getTime();
            //header
            Map<String,Object> header=new HashMap<>();
            header.put("alg","HS256");
            header.put("typ","JWT");

            String token= JWT.create().withHeader(header)
                            .withClaim("name",admin.getName())
                            .withIssuedAt(nowDate)
                            .withExpiresAt(expires)
                            .sign(Algorithm.HMAC256(secret));
            return token;
        }
        public boolean verify(String token){
            try{
                JWTVerifier verifier=JWT.require(Algorithm.HMAC256(secret)).build();
                verifier.verify(token);
                return true;
            }catch (Exception e){
                System.out.println(e.getMessage());
                return false;
            }
        }
}
