package top.lidh.blog.Util;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class interceptor implements HandlerInterceptor {
    private jwtUtil jwt=new jwtUtil();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");
        String token=request.getHeader("token");
        if(token!=null&&token!=""){
            boolean result=jwt.verify(token);
            if(result){
                return true;
            }
        }
        response.getWriter().write("认证失败");
        return false;
    }
}
