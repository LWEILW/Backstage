package com.backstage.dao.commodity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.admin.User;
import com.backstage.entity.commodity.Commodity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品管理
 *
 * @author Liu wei
 * @date 2020-07-22 16:00
 **/
@Mapper
public interface CommodityMapper extends BaseMapper<JSONObject> {


    /**
     * 商品台账
     *
     * @param page
     * @return
     */
    List<Commodity> commodityList(Pagination page);


    /**
     * 商品详情
     *
     * @param commodityId
     * @return
     */
    Commodity commodityDetails(@Param("commodityId") int commodityId);


    /**
     * 商品创建
     *
     * @param commodity
     * @return
     */
    int commodityCreate(@Param("total") Commodity commodity);


    /**
     * 获取商品创建ID
     *
     * @return
     */
    List<Commodity> getCommodityId();


    /**
     * 商品编辑
     *
     * @param commodity
     * @return
     */
    int commodityUpdate(@Param("total") Commodity commodity);


    /**
     * 商品批量删除
     *
     * @param list
     * @return
     */
    int commodityDeleteAll(@Param("list") List<Integer> list);


    /**
     * 商品图片添加
     *
     * @param commodityId
     * @param url
     * @return
     */
    int insertCommodityFile(@Param("commodityId") int commodityId, @Param("url") String url);

    /**
     * 商品图片列表
     *
     * @param commodityId
     * @return
     */
    List<JSONObject> commodityFileListById(@Param("commodityId") int commodityId);
}
