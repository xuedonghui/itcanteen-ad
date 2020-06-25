package com.tensquare.friend.service;

import com.netflix.discovery.converters.Auto;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;


    public int addFriend(String userid,String friendid){
        //先判断userid到friendid是否有数据 有就是重复添加好友 返回0
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if(friend!=null){
            return 0;
        }
        //直接添加好友 让好友表中userid到friendid方向(单向喜欢)的islike为0
        Friend friend1 = new Friend();
        friend1.setUserid(userid);
        friend1.setFriendid(friendid);
        friend1.setIslike("0");
        friendDao.save(friend1);
        //判断从friendid到userid是否有数据 (对方是否也喜欢你 )如果有双方状态 islike为1
        if(friendDao.findByUseridAndFriendid(friendid, userid)!=null){
            //把双方的islike改为 1
            friendDao.updateIslike("1",userid,friendid);
            friendDao.updateIslike("1",friendid,userid);
        }

        return 1;
    }

    public int addNofriend(String id, String friendid) {
        //先判断是否已经是非好友
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(id, friendid);
        if(noFriend!=null){
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(id);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;
    }

    public void deleteFriend(String userid, String friendid) {
        //删除表中userid到friendid的数据
        friendDao.deleteFriend(userid,friendid);
        //更新friendid到userid的islike为0
        friendDao.updateIslike("0",friendid,userid);
        //非好友表中添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
