package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoFriendDao extends JpaRepository<NoFriend,String> {
    /**
     * 判断是否有相同数据
     * @param userid
     * @param friendid
     * @return
     */
    public NoFriend findByUseridAndFriendid(String userid, String friendid);



}
