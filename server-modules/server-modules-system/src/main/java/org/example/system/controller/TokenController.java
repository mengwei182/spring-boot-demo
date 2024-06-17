package org.example.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.system.service.TokenService;
import org.example.common.core.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Api(tags = "Token管理")
@RestController
@RequestMapping("/token")
public class TokenController {
    @Resource
    private TokenService tokenService;

    /**
     * 刷新token
     *
     * @return
     */
    @ApiOperation("刷新token")
    @GetMapping("/refresh")
    public CommonResult<String> refresh() {
        return CommonResult.success(tokenService.refresh());
    }
}