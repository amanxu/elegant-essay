package com.elegant.essay.mapper;

import com.elegant.essay.model.ElegantUser;
import com.elegant.essay.model.dto.UserQueryDto;
import com.elegant.essay.model.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserSelfMapper {

    List<UserVo> pageList(UserQueryDto queryDto);

    List<UserVo> findUsersByRealName(@Param("realName") String realName);

    ElegantUser findUserByAccount(@Param("userName") String userName);

    List<ElegantUser> batchUsersByIds(@Param("userIds")Set<Long> userIds);
}