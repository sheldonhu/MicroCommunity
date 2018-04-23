package com.java110.center.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.java110.center.dao.ICenterServiceDAO;
import com.java110.common.constant.ResponseConstant;
import com.java110.common.exception.DAOException;
import com.java110.common.log.LoggerEngine;
import com.java110.core.base.dao.BaseServiceDao;
import com.java110.entity.mapping.Mapping;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 中心服务 数据操作类
 * Created by wuxw on 2018/4/14.
 */
@Service("centerServiceDAOImpl")
@Transactional
public class CenterServiceDAOImpl extends BaseServiceDao implements ICenterServiceDAO {

    /**
     * 保存订单信息
     * @param order 订单信息
     * @return
     */
    @Override
    public void saveOrder(Map order) throws DAOException{

        LoggerEngine.debug("----【CenterServiceDAOImpl.saveOrder】保存数据入参 : " + JSONObject.toJSONString(order));

        int saveFlag = sqlSessionTemplate.insert("centerServiceDAOImpl.saveOrder",order);
        if(saveFlag < 1){
            throw new DAOException(ResponseConstant.RESULT_CODE_INNER_ERROR,"保存订单信息失败："+ JSONObject.toJSONString(order));
        }

    }

    /**
     * 保存属性信息
     * @param orderAttrs
     * @return
     */
    @Override
    public void saveOrderAttrs(List<Map> orderAttrs) throws DAOException {

        LoggerEngine.debug("----【CenterServiceDAOImpl.saveOrderAttrs】保存数据入参 : " + JSONObject.toJSONString(orderAttrs));

        for(Map orderAttr:orderAttrs){
            int saveFlag = sqlSessionTemplate.insert("centerServiceDAOImpl.saveOrderAttrs",orderAttr);
            if(saveFlag < 1){
                throw new DAOException(ResponseConstant.RESULT_CODE_INNER_ERROR,"保存订单属性信息失败："+ JSONObject.toJSONString(orderAttr));
            }
        }
    }

    /**
     * 保存订单项信息
     * @param businesses 订单项信息
     */
    @Override
    public void saveBusiness(List<Map> businesses) throws DAOException {

        LoggerEngine.debug("----【CenterServiceDAOImpl.saveBusiness】保存数据入参 : " + JSONObject.toJSONString(businesses));
        for(Map business:businesses) {
            int saveFlag = sqlSessionTemplate.insert("centerServiceDAOImpl.saveBusiness", business);
            if (saveFlag < 1) {
                throw new DAOException(ResponseConstant.RESULT_CODE_INNER_ERROR, "保存订单项信息失败：" + JSONObject.toJSONString(business));
            }
        }
    }

    /**
     * 保存属性信息
     * @param businessAttrs
     */
    @Override
    public void saveBusinessAttrs(List<Map> businessAttrs) throws DAOException {

        LoggerEngine.debug("----【CenterServiceDAOImpl.saveBusinessAttrs】保存数据入参 : " + JSONObject.toJSONString(businessAttrs));

        for(Map businessAttr:businessAttrs){
            int saveFlag = sqlSessionTemplate.insert("centerServiceDAOImpl.saveBusinessAttrs",businessAttr);
            if(saveFlag < 1){
                throw new DAOException(ResponseConstant.RESULT_CODE_INNER_ERROR,"保存订单项属性信息失败："+ JSONObject.toJSONString(businessAttr));
            }
        }
    }

    /**
     * 更新订单信息（一般就更新订单状态）
     * @param order
     * @throws DAOException
     */
    @Override
    public void updateOrder(Map order) throws DAOException {
        LoggerEngine.debug("----【CenterServiceDAOImpl.updateOrder】保存数据入参 : " + JSONObject.toJSONString(order));

        int saveFlag = sqlSessionTemplate.update("centerServiceDAOImpl.updateOrder",order);
        if(saveFlag < 1){
            throw new DAOException(ResponseConstant.RESULT_CODE_INNER_ERROR,"更新订单信息失败："+ JSONObject.toJSONString(order));
        }
    }

    /**
     * 更新订单项信息（一般就更新订单项状态）
     * @param order
     * @throws DAOException
     */
    @Override
    public void updateBusiness(Map order) throws DAOException {
        LoggerEngine.debug("----【CenterServiceDAOImpl.updateBusiness】保存数据入参 : " + JSONObject.toJSONString(order));

        int saveFlag = sqlSessionTemplate.update("centerServiceDAOImpl.updateBusiness",order);
        if(saveFlag < 1){
            throw new DAOException(ResponseConstant.RESULT_CODE_INNER_ERROR,"更新订单项信息失败："+ JSONObject.toJSONString(order));
        }
    }

    /**
     * 根据bId 修改业务项信息
     * @param business
     * @throws DAOException
     */
    public void updateBusinessByBId(Map business) throws DAOException{
        LoggerEngine.debug("----【CenterServiceDAOImpl.updateBusinessByBId】保存数据入参 : " + JSONObject.toJSONString(business));

        int saveFlag = sqlSessionTemplate.update("centerServiceDAOImpl.updateBusinessByBId",business);
        if(saveFlag < 1){
            throw new DAOException(ResponseConstant.RESULT_CODE_INNER_ERROR,"更新订单项信息失败："+ JSONObject.toJSONString(business));
        }
    }

    /**
     * 当所有业务动作是否都是C，将订单信息改为 C
     * @param bId
     * @return
     * @throws DAOException
     */
    public void completeOrderByBId(String bId) throws DAOException{
        LoggerEngine.debug("----【CenterServiceDAOImpl.completeOrderByBId】数据入参 : " + bId);

        int updateFlag = sqlSessionTemplate.update("centerServiceDAOImpl.completeOrderByBId",bId);

        if(updateFlag < 1){
            throw new DAOException(ResponseConstant.RESULT_CODE_INNER_ERROR,"当前业务还没有全完成（C）："+ bId);
        }
    }

    /**
     * 根据bId查询订单信息
     * @param bId
     * @return
     * @throws DAOException
     */
    public Map getOrderInfoByBId(String bId)throws DAOException{
        List<Map> orders = sqlSessionTemplate.selectList("centerServiceDAOImpl.getOrderInfoByBId",bId);
        if(orders !=null){
            return orders.get(0);
        }
        return null;
    }
    /**
     * 获取同个订单中已经完成的订单项
     * @param bId
     * @return
     * @throws DAOException
     */
    public List<Map> getCommonOrderCompledBusinessByBId(String bId) throws DAOException{
        LoggerEngine.debug("----【CenterServiceDAOImpl.getCommonOrderCompledBusinessByBId】数据入参 : " + bId);
        return sqlSessionTemplate.selectList("centerServiceDAOImpl.getCommonOrderCompledBusinessByBId",bId);
    }

    @Override
    public List<Map> getAppRouteAndServiceInfoAll() {
        return sqlSessionTemplate.selectList("centerServiceDAOImpl.getAppRouteAndServiceInfoAll");
    }


    /**
     * 查询映射表
     * @return
     */
    @Override
    public List<Mapping> getMappingInfoAll() {
        return sqlSessionTemplate.selectList("centerServiceDAOImpl.getMappingInfoAll");
    }


}