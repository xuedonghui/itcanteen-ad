package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    /**
     * 添加好友或者添加非好友
     * @return
     */
    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){
            //验证是否登录 并且拿到当前登录用户id
          Claims claims = (Claims) request.getAttribute("user_claims");
          System.out.println(claims);
          if(claims==null){
              return new Result(false, StatusCode.ERROR,"无权访问");
            }
            //判断是添加好友还是非好友
            if(type!=null){
                if(type.equals("1")){
                    //添加好友
                        int flag = friendService.addFriend(claims.getId(),friendid);
                        if (flag==0){
                            return new Result(false, StatusCode.REPERROR,"不能重复添加好友");
                        }
                        if(flag==1){
                            //调用user模块 使添加好友后 好友粉丝数+1 用户关注数+1
                            userClient.updateFanscountAndFollowcount(claims.getId(),friendid,1);
                            return new Result(false, StatusCode.REPERROR,"已经添加此好友");
                        }
                }else if(type.equals("2")){
                    //添加非好友
                    int flag = friendService.addNofriend(claims.getId(),friendid);
                    if (flag==0){
                        return new Result(false, StatusCode.REPERROR,"不能重复添加非好友");
                    }
                    if(flag==1){
                        return new Result(false, StatusCode.REPERROR,"已经添加此为非好友");
                    }
                }
                return new Result(false, StatusCode.ERROR,"参数异常");
            }else{
                return new Result(false, StatusCode.ERROR,"参数异常");
            }
    }

    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid){
        //验证是否登录 并且拿到当前登录用户id
        Claims claims = (Claims) request.getAttribute("user_claims");
        System.out.println(claims);
        if(claims==null){
            return new Result(false, StatusCode.ERROR,"无权访问");
        }
        friendService.deleteFriend(claims.getId(),friendid);
        userClient.updateFanscountAndFollowcount(claims.getId(),friendid,-1);
        return new Result(true,StatusCode.OK,"删除成功");
    }


}
