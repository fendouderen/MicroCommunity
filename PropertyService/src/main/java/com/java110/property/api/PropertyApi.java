package com.java110.property.api;

import com.alibaba.fastjson.JSONObject;
import com.java110.property.smo.IPropertyServiceSMO;
import com.java110.common.constant.ResponseConstant;
import com.java110.common.exception.InitConfigDataException;
import com.java110.common.exception.InitDataFlowContextException;
import com.java110.core.base.controller.BaseController;
import com.java110.core.context.BusinessServiceDataFlow;
import com.java110.core.factory.DataTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务类
 * Created by wuxw on 2018/5/14.
 */
@RestController
public class PropertyApi extends BaseController {

    @Autowired
    IPropertyServiceSMO propertyServiceSMOImpl;

    @RequestMapping(path = "/propertyApi/service",method= RequestMethod.GET)
    public String serviceGet(HttpServletRequest request) {
        return DataTransactionFactory.createBusinessResponseJson(ResponseConstant.RESULT_CODE_ERROR,"不支持Get方法请求").toJSONString();
    }

    /**
     * 用户服务统一处理接口
     * @param orderInfo
     * @param request
     * @return
     */
    @RequestMapping(path = "/propertyApi/service",method= RequestMethod.POST)
    public String servicePost(@RequestBody String orderInfo, HttpServletRequest request) {
        BusinessServiceDataFlow businessServiceDataFlow = null;
        JSONObject responseJson = null;
        try {
            Map<String, String> headers = new HashMap<String, String>();
            getRequestInfo(request, headers);
            //预校验
            preValiateOrderInfo(orderInfo);
            businessServiceDataFlow = this.writeDataToDataFlowContext(orderInfo, headers);
            responseJson = propertyServiceSMOImpl.service(businessServiceDataFlow);
        }catch (InitDataFlowContextException e){
            logger.error("请求报文错误,初始化 BusinessServiceDataFlow失败"+orderInfo,e);
            responseJson = DataTransactionFactory.createNoBusinessTypeBusinessResponseJson(orderInfo,ResponseConstant.RESULT_PARAM_ERROR,e.getMessage(),null);
        }catch (InitConfigDataException e){
            logger.error("请求报文错误,加载配置信息失败"+orderInfo,e);
            responseJson = DataTransactionFactory.createNoBusinessTypeBusinessResponseJson(orderInfo,ResponseConstant.RESULT_PARAM_ERROR,e.getMessage(),null);
        }catch (Exception e){
            logger.error("请求订单异常",e);
            responseJson = DataTransactionFactory.createBusinessResponseJson(businessServiceDataFlow,ResponseConstant.RESULT_CODE_ERROR,e.getMessage()+e,
                    null);
        }finally {
            return responseJson.toJSONString();
        }
    }

    /**
     * 这里预校验，请求报文中不能有 dataFlowId
     * @param orderInfo
     */
    private void preValiateOrderInfo(String orderInfo) {
       /* if(JSONObject.parseObject(orderInfo).getJSONObject("orders").containsKey("dataFlowId")){
            throw new BusinessException(ResponseConstant.RESULT_CODE_ERROR,"报文中不能存在dataFlowId节点");
        }*/
    }

    /**
     * 获取请求信息
     * @param request
     * @param headers
     * @throws RuntimeException
     */
    private void getRequestInfo(HttpServletRequest request,Map headers) throws Exception{
        try{
            super.initHeadParam(request,headers);
            super.initUrlParam(request,headers);
        }catch (Exception e){
            logger.error("加载头信息失败",e);
            throw new InitConfigDataException(ResponseConstant.RESULT_PARAM_ERROR,"加载头信息失败");
        }
    }

    public IPropertyServiceSMO getPropertyServiceSMOImpl() {
        return propertyServiceSMOImpl;
    }

    public void setPropertyServiceSMOImpl(IPropertyServiceSMO propertyServiceSMOImpl) {
        this.propertyServiceSMOImpl = propertyServiceSMOImpl;
    }
}