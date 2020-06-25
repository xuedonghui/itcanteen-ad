package com.tensquare.manager.filter;

import com.netflix.discovery.converters.Auto;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 在请求前pre或者后post执行
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器的执行顺序,数字越小,表示越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启true表示开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作 return 任何ojbect的值都表示继续执行
     * setsendzuulrespponse(false)表示不再继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台过滤器了");
        RequestContext context = RequestContext.getCurrentContext();
        //request域
        HttpServletRequest request = context.getRequest();

        if(request.getMethod().equals("OPTIONS")){
            return null;
        }

        if(request.getRequestURI().indexOf("login")>0){
            return null;
        }

        //得到头信息
        String header = request.getHeader("Authorization");
        if(header!=null && !"".equals(header)){
            if(header.startsWith("Bearer ")){
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    if("admin".equals(claims.get("roles"))){
                        //转发头信息 并且放行
                        context.addZuulRequestHeader("context",header);
                        return null;
                    }
                }catch (Exception e){
                    context.setSendZuulResponse(false);//终止运行
                }
            }
        }
        context.setSendZuulResponse(false);//终止运行
        context.setResponseStatusCode(403);//http状态码
        context.setResponseBody("无权访问");
        context.getResponse().setContentType("text/html;;charset=UTF-8");
        return null;
    }
}
