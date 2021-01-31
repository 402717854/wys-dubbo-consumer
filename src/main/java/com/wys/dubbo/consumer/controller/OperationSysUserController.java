package com.wys.dubbo.consumer.controller;

import com.wys.dubbo.consumer.common.annotation.CheckToken;
import com.wys.dubbo.consumer.common.annotation.PermissionVerify;
import com.wys.dubbo.consumer.common.annotation.ResponseResult;
import com.wys.dubbo.consumer.util.ThreadLocalUtil;
import com.wys.dubbo.dto.request.OperationAssignRoleReq;
import com.wys.dubbo.dto.request.OperationLoginReq;
import com.wys.dubbo.dto.request.OperationUserReq;
import com.wys.dubbo.dto.response.OperationTokenRes;
import com.wys.dubbo.result.RpcExecuteResult;
import com.wys.dubbo.service.DubboUserServiceClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Classname LoginSystemController
 * @Description 管理员管理
 * @Date 2020/12/9 9:11
 */
@RestController
@RequestMapping("/operation/sysUserManage")
@Slf4j
@Api(tags = {"管理员管理(登录/注册)"})
@ResponseResult
public class OperationSysUserController {

    @Reference
    private DubboUserServiceClient userServiceClient;


    /**
     * 登录
     * @param request
     * @return
     */
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public RpcExecuteResult login(@RequestBody @Valid OperationLoginReq request){
        RpcExecuteResult<OperationTokenRes> result = userServiceClient.login(request);
        return result;
    }

    /**
     * 退出登录
     * @return
     */
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    @CheckToken
    public RpcExecuteResult logout() {
        String authToken = ThreadLocalUtil.getLocalAccessToken();
        RpcExecuteResult<Boolean> result = userServiceClient.logout(authToken);
        return result;
    }


    /**
     * @return ExecuteResult<String>
     * @Description 为多个管理员分配角色
     * @Param request
     **/
    @ApiOperation(value = "为多个管理员分配多个角色")
    @PostMapping("/assignRoleBatch")
    @CheckToken
    @PermissionVerify(permission = "operate:sysUserManage:assignRoleBatch")
    public RpcExecuteResult assignRoleBatch(@RequestBody @Valid OperationAssignRoleReq request){

        //过滤重复
        RpcExecuteResult<Boolean> result = userServiceClient.assignRoleBatch(request);
        return result;
    }

    /**
     * @return ExecuteResult
     * @Description 增加/修改运营端账户
     * @Param request
     **/
    @ApiOperation(value = "增加/修改运营端账户")
    @PostMapping("/addOrUpdateOperationUser")
    @ResponseBody
    @PermissionVerify(permission = "operate:sysUserManage:addOrUpdateOperationUser")
    public RpcExecuteResult addOrUpdateOperationUser(@Valid @RequestBody OperationUserReq request){
        RpcExecuteResult<Boolean> result = userServiceClient.addOrUpdateOperationUser(request);
        return result;
    }
}
