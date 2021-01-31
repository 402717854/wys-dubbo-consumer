package com.wys.dubbo.consumer.controller;

import com.wys.dubbo.consumer.common.annotation.CheckToken;
import com.wys.dubbo.consumer.common.annotation.PermissionVerify;
import com.wys.dubbo.consumer.common.annotation.ResponseResult;
import com.wys.dubbo.dto.OperationMenuDto;
import com.wys.dubbo.dto.request.OperationMenuPageReq;
import com.wys.dubbo.dto.request.OperationMenuReq;
import com.wys.dubbo.dto.response.OperationMenuPageRes;
import com.wys.dubbo.dto.response.OperationValidMenuTreeRes;
import com.wys.dubbo.result.RpcExecuteResult;
import com.wys.dubbo.service.DubboMenuServiceClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Classname OperationMenuController
 * @Description 菜单管理
 * @Date 2020/12/11 16:17
 * @Created by 20113370
 */
@RestController
@RequestMapping("/operation/menuManage")
@Slf4j
@Api(tags = {"菜单管理"})
@ResponseResult
public class OperationMenuController {

    @Reference
    private DubboMenuServiceClient menuServiceClient;

    /**
     * @return ExecuteResult<OperationMenuPageRes>
     * @Description 分页查询全部菜单
     * @Param request
     **/
    @ApiOperation(value = "分页查询全部菜单")
    @PostMapping("/queryMenuPage")
    @CheckToken
    @PermissionVerify(permission = "operate:menuManage:query")
    public RpcExecuteResult<OperationMenuPageRes> queryMenuPage(@RequestBody @Valid OperationMenuPageReq request) {
        RpcExecuteResult<OperationMenuPageRes> result = menuServiceClient.queryMenuPage(request);
        return result;
    }

    /**
     * @return ExecuteResult<OperationMenuVO>
     * @Description 获取菜单详情
     * @Param request
     **/
    @ApiOperation(value = "获取菜单详情")
    @PostMapping("/queryMenuDetails")
    @CheckToken
    @PermissionVerify(permission = "operate:menuManage:query")
    public RpcExecuteResult<OperationMenuDto> queryMenuDetails(Integer menuId) {
        RpcExecuteResult<OperationMenuDto> result = menuServiceClient.queryMenuDetails(menuId);
        return result;
    }

    /**
     * @return ExecuteResult
     * @Description 获取可用菜单树
     * @Param request
     **/
    @ApiOperation(value = "获取可用菜单树")
    @PostMapping("/queryValidMenuTree")
    @CheckToken
    @PermissionVerify(permission = "operate:menuManage:query")
    public RpcExecuteResult queryValidMenuTree() {
        RpcExecuteResult<OperationValidMenuTreeRes> result = menuServiceClient.queryValidMenuTree();
        return result;
    }

    /**
     * @return ExecuteResult
     * @Description 添加或修改菜单
     * @Param request
     **/
    @ApiOperation(value = "添加或修改菜单")
    @PostMapping("/addOrEditMenu")
    @CheckToken
    @PermissionVerify(permission = "operate:menuManage:edit")
    public RpcExecuteResult addOrEditMenu(@RequestBody @Valid OperationMenuReq request) {
        RpcExecuteResult<Boolean> result = menuServiceClient.addOrEditMenu(request);
        return result;
    }
}
