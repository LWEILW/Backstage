package com.backstage.dao;//package com.blogger.dao;
//
//
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.io.Serializable;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//public interface BaseMapper<T> extends Mapper<T> {
//    int insert(T entity);
//
//    int deleteById(Serializable id);
//
//    int deleteByMap(@Param("cm") Map<String, Object> columnMap);
//
//    int delete(@Param("ew") Wrapper<T> wrapper);
//
//    int deleteBatchIds(@Param("coll") Collection<? extends Serializable> idList);
//
//    int updateById(@Param("et") T entity);
//
//    int update(@Param("et") T entity, @Param("ew") Wrapper<T> updateWrapper);
//
//    T selectById(Serializable id);
//
//    List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> idList);
//
//    List<T> selectByMap(@Param("cm") Map<String, Object> columnMap);
//
//    T selectOne(@Param("ew") Wrapper<T> queryWrapper);
//
//    Integer selectCount(@Param("ew") Wrapper<T> queryWrapper);
//
//    List<T> selectList(@Param("ew") Wrapper<T> queryWrapper);
//
//    List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<T> queryWrapper);
//
//    List<Object> selectObjs(@Param("ew") Wrapper<T> queryWrapper);
//
//    IPage<T> selectPage(IPage<T> page, @Param("ew") Wrapper<T> queryWrapper);
//
//    IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param("ew") Wrapper<T> queryWrapper);
//}
