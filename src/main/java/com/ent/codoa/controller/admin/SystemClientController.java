package com.ent.codoa.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.exception.AccountOrPasswordException;
import com.ent.codoa.common.tools.CodeTools;
import com.ent.codoa.common.tools.GenerateTools;
import com.ent.codoa.common.tools.HttpTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.SystemClient;
import com.ent.codoa.pojo.req.systemclient.*;
import com.ent.codoa.pojo.resp.systemclient.CaptchaCode;
import com.ent.codoa.pojo.resp.token.AdminToken;
import com.ent.codoa.service.EhcacheService;
import com.ent.codoa.service.SystemClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@RestController
@Api(tags = "系统用户")
@RequestMapping("/admin/systemClient")
public class SystemClientController {
    @Autowired
    private EhcacheService ehcacheService;
    @Autowired
    private SystemClientService systemClientService;

    @PostMapping("/page")
    @ApiOperation(value = "分页系统用户", notes = "分页系统用户")
    public R<IPage<SystemClient>> page(@RequestBody SystemClientPage req) {
        IPage<SystemClient> iPage = systemClientService.queryPage(req);
        return R.ok(iPage);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加系统用户", notes = "添加系统用户")
    public R add(@RequestBody @Valid SystemClientAdd req) {
        SystemClient systemClient = BeanUtil.toBean(req, SystemClient.class);
        systemClientService.add(systemClient);
        return R.ok(null);
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改系统用户状态", notes = "修改系统用户状态")
    public R updateStatus(@RequestBody @Valid SystemClientUpdateStatus req) {
        systemClientService.updateStatus(req.getId(), req.getStatus());
        return R.ok(null);
    }

    @PostMapping("/updateBaseInfo")
    @ApiOperation(value = "修改系统用户基础信息", notes = "修改系统用户基础信息")
    public R updateBaseInfo(@RequestBody @Valid SystemClientUpdateBaseInfo req) {
        systemClientService.editBaseInfo(req);
        return R.ok(null);
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public R<AdminToken> login(@RequestBody @Valid AdminLogin req) {
        log.info("登录接口入参:{}", JSONUtil.toJsonStr(req));
        //校验验证码
        String captchaCode = ehcacheService.captchaCodeCache().get(HttpTools.getIp());
        if (captchaCode == null) {
            return R.failed("验证码有误或已过期");
        }
        if (!captchaCode.equals(req.getVerificationCode())){
            return R.failed("验证码错误");
        }

        //判断账号密码是否正确
        SystemClient systemClient = systemClientService.findByAccount(req.getAccount());
        if (systemClient == null) {
            throw new AccountOrPasswordException();
        }

        //对比登录密码和正确密码
        String password = systemClient.getPassword();
        String passwordReq = CodeTools.md5AndSalt(req.getPassword(), systemClient.getSalt());

        if (!password.equals(passwordReq)) {
            throw new AccountOrPasswordException();
        }

        //校验是否已经登录,如果已经登陆过删除之前的tokenId和缓存
        //checkLoginCache(administrators.getAccount());

        String tokenId = GenerateTools.createTokenId();
        //生成token并返回
        AdminToken adminToken = new AdminToken();
        adminToken.setAccount(req.getAccount());
        adminToken.setName(systemClient.getName());
        adminToken.setRole(systemClient.getRole());
        adminToken.setLoginTime(LocalDateTime.now());
        adminToken.setTokenId(tokenId);

        ehcacheService.adminTokenCache().put(tokenId, adminToken);

        //删除使用过的验证码缓存
        ehcacheService.captchaCodeCache().remove(HttpTools.getIp());
        return R.ok(adminToken);
    }


    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    public R logout() {
        String tokenId = TokenTools.getAdminToken(false).getTokenId();
        if (StringUtils.isBlank(tokenId)){
            return R.ok(null);
        }
        ehcacheService.adminTokenCache().remove(tokenId);
        return R.ok(null);
    }


    @PostMapping("/getCaptchaCode")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    public synchronized R<CaptchaCode> get() {
        String ip = HttpTools.getIp();
        log.info("ip:{}请求图片验证码", ip);

        String codeImageStream =  ehcacheService.getVC(ip,30,"每3秒超过30次点击验证码");

        CaptchaCode captchaCode = new CaptchaCode();
        captchaCode.setCaptchaImage("data:image/png;base64," + codeImageStream);
        return R.ok(captchaCode);
    }



}
