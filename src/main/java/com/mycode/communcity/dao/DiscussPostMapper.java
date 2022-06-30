package com.mycode.communcity.dao;

import com.mycode.communcity.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    /**
     * 可以使用动态SQL来直接查所有，或者是某用户所属的帖子
     * @param userId 帖子所属的用户id,没有(=0)的话就是查所有
     * @param offset 分页查询的行号
     * @param limit 每页存储的数量
     */
    List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit);

    /**
     * 计算表中数据，用于计算页数
     *          param用于起别名。
     *          如果一个方法只用一个参数，且用到了动态sql中的if,那么必须加别名。
     */
    int selectDiscussPostRows(@Param("userId") int userId);
}
