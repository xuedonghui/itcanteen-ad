package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    User findByMobile(String mobile);

    @Query(value = "update tb_user set fanscount = fanscount+?1 where id = ?2",nativeQuery = true)
    @Modifying
    void updateFanscount(int x, String friendid);

    @Query(value = "update tb_user set followcount = followcount+?1 where id = ?2",nativeQuery = true)
    @Modifying
    void updateFollowcount(int x, String userid);
}
