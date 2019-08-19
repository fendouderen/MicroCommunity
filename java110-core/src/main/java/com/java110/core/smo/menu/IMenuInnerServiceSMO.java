package com.java110.core.smo.menu;

import com.java110.core.feign.FeignConfiguration;
import com.java110.dto.menuGroup.MenuGroupDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @ClassName IMenuGroupInnerServiceSMO
 * @Description 路由接口类
 * @Author wuxw
 * @Date 2019/4/24 9:04
 * @Version 1.0
 * add by wuxw 2019/4/24
 **/
@FeignClient(name = "community-service", configuration = {FeignConfiguration.class})
@RequestMapping("/menuApi")
public interface IMenuInnerServiceSMO {

    /**
     * <p>查询小区楼信息</p>
     *
     * @param routeDto 数据对象分享
     * @return MenuGroupDto 对象数据
     */
    @RequestMapping(value = "/queryMenuGroups", method = RequestMethod.POST)
    List<MenuGroupDto> queryMenuGroups(@RequestBody MenuGroupDto routeDto);

    /**
     * 查询<p>小区楼</p>总记录数
     *
     * @param routeDto 数据对象分享
     * @return 小区下的小区楼记录数
     */
    @RequestMapping(value = "/queryMenuGroupsCount", method = RequestMethod.POST)
    int queryMenuGroupsCount(@RequestBody MenuGroupDto routeDto);

    /**
     * <p>修改APP信息</p>
     *
     * @param routeDto 数据对象分享
     * @return ServiceDto 对象数据
     */
    @RequestMapping(value = "/updateMenuGroup", method = RequestMethod.POST)
    int updateMenuGroup(@RequestBody MenuGroupDto routeDto);


    /**
     * <p>添加APP信息</p>
     *
     * @param routeDto 数据对象分享
     * @return MenuGroupDto 对象数据
     */
    @RequestMapping(value = "/saveMenuGroup", method = RequestMethod.POST)
    int saveMenuGroup(@RequestBody MenuGroupDto routeDto);

    /**
     * <p>删除APP信息</p>
     *
     * @param routeDto 数据对象分享
     * @return MenuGroupDto 对象数据
     */
    @RequestMapping(value = "/deleteMenuGroup", method = RequestMethod.POST)
    int deleteMenuGroup(@RequestBody MenuGroupDto routeDto);
}