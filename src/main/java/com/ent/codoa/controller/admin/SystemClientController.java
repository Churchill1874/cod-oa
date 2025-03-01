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
import com.ent.codoa.entity.BusinessClient;
import com.ent.codoa.entity.SystemClient;
import com.ent.codoa.pojo.req.systemclient.*;
import com.ent.codoa.pojo.resp.systemclient.CaptchaCode;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.BusinessClientService;
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
    @Autowired
    private BusinessClientService businessClientService;

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


    //对比密码正确与否
    private void checkAccountAndPassword(String actualPassword, String passwordReq){
        if (!actualPassword.equals(passwordReq)) {
            throw new AccountOrPasswordException();
        }
    }


    //判断登录账号与密码是否存在 并正确
    private LoginToken checkLogin(String account, String password) {
        LoginToken loginToken;

        //判断登录账号是否存在系统用户中
        SystemClient systemClient = systemClientService.findByAccount(account);
        if (systemClient != null) {
            loginToken = new LoginToken();
            loginToken.setName(systemClient.getName());
            loginToken.setRole(systemClient.getRole());
            loginToken.setIsSystemClient(true);
            //对比登录密码和正确密码
            checkAccountAndPassword(systemClient.getPassword(), CodeTools.md5AndSalt(password, systemClient.getSalt()));
            return loginToken;
        }

        //判断登录账号是否存在业务管理中
        BusinessClient businessClient = businessClientService.findByAccount(account);
        if (businessClient != null){
            loginToken = new LoginToken();
            loginToken.setName(businessClient.getName());
            loginToken.setIsSystemClient(false);
            loginToken.setSystemClientAccount(businessClient.getSystemClientAccount());
            //对比登录密码和正确密码
            checkAccountAndPassword(systemClient.getPassword(), CodeTools.md5AndSalt(password, systemClient.getSalt()));

            //获取所属系统用户的权限赋予自己
            systemClient = systemClientService.findByAccount(loginToken.getSystemClientAccount());
            loginToken.setBusinessMenu(systemClient.getBusinessMenu());
            loginToken.setCustomerMenu(systemClient.getCustomerMenu());
            loginToken.setHrMenu(systemClient.getHrMenu());
            loginToken.setInventoryMenu(systemClient.getInventoryMenu());
            loginToken.setPaymentMenu(systemClient.getPaymentMenu());
            loginToken.setPlatformMenu(systemClient.getPlatformMenu());
            return loginToken;
        }

        //校验是否已经登录,如果已经登陆过删除之前的tokenId和缓存
        //checkLoginCache(administrators.getAccount());

        throw new AccountOrPasswordException();
    }


    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public R<LoginToken> login(@RequestBody @Valid AdminLogin req) {
        log.info("登录接口入参:{}", JSONUtil.toJsonStr(req));
        //校验验证码
        String captchaCode = ehcacheService.captchaCodeCache().get(HttpTools.getIp());
        if (captchaCode == null) {
            return R.failed("验证码有误或已过期");
        }
        if (!captchaCode.equals(req.getVerificationCode())) {
            return R.failed("验证码错误");
        }

        LoginToken loginToken = checkLogin(req.getAccount(), req.getPassword());

        //生成token并返回
        loginToken.setAccount(req.getAccount());
        loginToken.setLoginTime(LocalDateTime.now());
        loginToken.setTokenId(GenerateTools.createTokenId());
        ehcacheService.adminTokenCache().put(loginToken.getTokenId(), loginToken);

        //删除使用过的验证码缓存
        ehcacheService.captchaCodeCache().remove(HttpTools.getIp());
        return R.ok(loginToken);
    }


    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    public R logout() {
        String tokenId = TokenTools.getAdminToken(false).getTokenId();
        if (StringUtils.isBlank(tokenId)) {
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

        String codeImageStream = ehcacheService.getVC(ip, 30, "每3秒超过30次点击验证码");

        CaptchaCode captchaCode = new CaptchaCode();
        captchaCode.setCaptchaImage("data:image/png;base64," + codeImageStream);
        return R.ok(captchaCode);
    }


}
