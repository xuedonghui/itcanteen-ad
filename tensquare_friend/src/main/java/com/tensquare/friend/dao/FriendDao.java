package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {
    /**
     * 判断是否有相同数据
     * @param userid
     * @param friendid
     * @return
     */
    public Friend findByUseridAndFriendid(String userid,String friendid);


    @Query(value="update tb_friend set islike=?1 where userid =?2 and friendid =?3",nativeQuery = true)
    @Modifying
    public void updateIslike(String islike,String userid,String friendid);

    @Query(value="delete from tb_friend  where userid =?1 and friendid =?2",nativeQuery = true)
    @Modifying
    void deleteFriend(String userid, String friendid);
}
