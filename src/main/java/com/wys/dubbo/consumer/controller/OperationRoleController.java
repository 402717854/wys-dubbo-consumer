package com.wys.dubbo.consumer.controller;

import com.wys.dubbo.consumer.common.annotation.CheckToken;
import com.wys.dubbo.consumer.common.annotation.PermissionVerify;
import com.wys.dubbo.consumer.common.annotation.ResponseResult;
import com.wys.dubbo.consumer.util.ThreadLocalUtil;
import com.wys.dubbo.dto.request.OperationRolePageReq;
import com.wys.dubbo.dto.request.OperationRoleReq;
import com.wys.dubbo.dto.response.OperationRoleDetailsRes;
import com.wys.dubbo.dto.response.OperationRoleEasyRes;
import com.wys.dubbo.dto.response.OperationRolePageRes;
import com.wys.dubbo.result.RpcExecuteResult;
import com.wys.dubbo.service.DubboRoleServiceClient;
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
 * @Classname OperationRoleController
 * @Description 角色管理
 * @Date 2020/12/14 10:33
 * @Created by 20113370
 */
@RestController
@RequestMapping("/operation/roleManage")
@Slf4j
@Api(tags = {"角色管理"})
@ResponseResult
public class OperationRoleController {

    @Reference
    DubboRoleServiceClient dubboRoleServiceClient;

    /**
     * @return ExecuteResult<OperationRolePageRes>
     * @Description 分页查询全部角色
     * @Param request
     **/
    @ApiOperation(value = "分页查询全部角色")
    @PostMapping("/queryRolePage")
    @CheckToken
    @PermissionVerify(permission = "operate:roleManage:query")
    public RpcExecuteResult queryRolePage(@RequestBody @Valid OperationRolePageReq request) {
        RpcExecuteResult<OperationRolePageRes> result = dubboRoleServiceClient.queryRolePage(request);

        return result;
    }

    /**
     * @return ExecuteResult<OperationRoleDetailsRes>
     * @Description 根据id查询单个角色详情
     * @Param request
     **/
    @ApiOperation(value = "根据id查询单个角色详情")
    @PostMapping("/queryRoleDetails")
    @CheckToken
    @PermissionVerify(permission = "operate:roleManage:query")
    public RpcExecuteResult queryRoleDetails(Integer roleId) {
        RpcExecuteResult<OperationRoleDetailsRes> result = dubboRoleServiceClient.queryRoleDetails(roleId);
        return result;
    }

    /**
     * @return ExecuteResult
     * @Description 添加或修改角色
     * @Param request
     **/
    @ApiOperation(value = "添加或修改角色")
    @PostMapping("/addOrEditRole")
    @CheckToken
    @PermissionVerify(permission = "operate:roleManage:edit")
    public RpcExecuteResult addOrEditRole(@RequestBody @Valid OperationRoleReq request) {
        RpcExecuteResult<Boolean> result = dubboRoleServiceClient.addOrEditRole(request);
        return result;
    }

    /**
     * @return ExecuteResult<OperationRolePageRes>
     * @Description 查询全部角色简易信息
     * @Param request
     **/
    @ApiOperation(value = "查询全部角色简易信息")
    @PostMapping("/queryRoleEasyAllList")
    @PermissionVerify(permission = "operate:roleManage:query")
    public RpcExecuteResult queryRoleEasyAllList() {
        Integer userId = ThreadLocalUtil.getLocalUserId();
        RpcExecuteResult<OperationRoleEasyRes> result = dubboRoleServiceClient.queryRoleEasyAllList(userId);
        return result;
    }
}
