package com.mycode.communcity.dao;

import com.mycode.communcity.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

//@Repository
@Mapper
public interface UserMapper {
    /** 根据id查询 */
    User selectById(int id);
    /** 根据姓名查询 */
    User selectByName(String username);
    /** 根据Email查询 */
    User selectByEmail(String email);
    /** 插入user数据 */
    int insertUser(User user);
    /** 更新status状态 */
    int updateStatus(int id,int status);
    /** 更新头像网址 */
    int updateHeaderUrl(int id,String headerUrl);
    /** 更新密码 */
    int updatePassword(int id,String password);
    /** */
}
