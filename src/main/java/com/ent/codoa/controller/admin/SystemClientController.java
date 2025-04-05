package com.ent.codoa.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.PlatformAuthCheck;
import com.ent.codoa.common.constant.SystemConstant;
import com.ent.codoa.common.constant.enums.RoleEnum;
import com.ent.codoa.common.exception.AccountOrPasswordException;
import com.ent.codoa.common.tools.CodeTools;
import com.ent.codoa.common.tools.GenerateTools;
import com.ent.codoa.common.tools.HttpTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.Customer;
import com.ent.codoa.entity.Staff;
import com.ent.codoa.entity.SystemClient;
import com.ent.codoa.pojo.req.systemclient.AdminLogin;
import com.ent.codoa.pojo.req.systemclient.SystemClientAdd;
import com.ent.codoa.pojo.req.systemclient.SystemClientBaseUpdate;
import com.ent.codoa.pojo.req.systemclient.SystemClientPage;
import com.ent.codoa.pojo.resp.systemclient.CaptchaCode;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.CustomerService;
import com.ent.codoa.service.EhcacheService;
import com.ent.codoa.service.StaffService;
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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import java.time.LocalDateTime;
import java.util.Set;

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
    private CustomerService customerService;//客户管理接口
    @Autowired
    private StaffService staffService;//人事管理接口

    @PlatformAuthCheck
    @PostMapping("/page")
    @ApiOperation(value = "分页系统用户", notes = "分页系统用户")
    public R<IPage<SystemClient>> page(@RequestBody SystemClientPage req) {
        IPage<SystemClient> iPage = systemClientService.queryPage(req);
        return R.ok(iPage);
    }

    @PlatformAuthCheck
    @PostMapping("/add")
    @ApiOperation(value = "添加系统用户", notes = "添加系统用户")
    public R add(@RequestBody @Valid SystemClientAdd req) {
        SystemClient systemClient = BeanUtil.toBean(req, SystemClient.class);
        systemClientService.add(systemClient);
        return R.ok(null);
    }


    @PlatformAuthCheck
    @PostMapping("/updateBaseInfo")
    @ApiOperation(value = "修改系统用户基础信息", notes = "修改系统用户基础信息")
    public R updateBaseInfo(@RequestBody @Valid SystemClientBaseUpdate req) {
        systemClientService.editBaseInfo(req);
        return R.ok(null);
    }


    //对比密码正确与否
    private void checkAccountAndPassword(String actualPassword, String passwordReq,String lang) {
        if (!actualPassword.equals(passwordReq)) {
            if("cn".equals(lang)){
                throw new AccountOrPasswordException();
            }
            if("jp".equals(lang)){
                throw new AccountOrPasswordException("アカウント情報が間違っています");
            }
            throw new AccountOrPasswordException("参数不正确");
        }
    }


    //判断登录账号与密码是否存在 并正确
    private LoginToken checkLogin(String account, String password,String lang) {
        LoginToken loginToken;

        //判断登录账号是否存在系统用户中
        SystemClient systemClient = systemClientService.findByAccount(account);
        if (systemClient != null) {
            loginToken = new LoginToken();
            loginToken.setName(systemClient.getName());
            loginToken.setRole(systemClient.getRole());
            loginToken.setCustomerMenu(systemClient.getCustomerMenu());
            loginToken.setHrMenu(systemClient.getHrMenu());
            loginToken.setInventoryMenu(systemClient.getInventoryMenu());
            loginToken.setPaymentMenu(systemClient.getPaymentMenu());
            loginToken.setPlatformMenu(systemClient.getPlatformMenu());
            loginToken.setStatus(systemClient.getStatus());
            //对比登录密码和正确密码
            checkAccountAndPassword(systemClient.getPassword(), CodeTools.md5AndSalt(password, systemClient.getSalt()),lang);
            return loginToken;
        }

        //判断登录账号是否存在客户管理中
        Customer customer = customerService.findByAccount(account);
        if (customer != null) {
            loginToken = new LoginToken();
            loginToken.setName(customer.getName());
            loginToken.setRole(RoleEnum.CUSTOMER);
            loginToken.setSystemClientAccount(customer.getSystemClientAccount());
            //对比登录密码和正确密码
            checkAccountAndPassword(customer.getPassword(), CodeTools.md5AndSalt(password, customer.getSalt()),lang);
            //获取所属系统用户的权限赋予自己
            systemClient = systemClientService.findByAccount(loginToken.getSystemClientAccount());
            loginToken.setCustomerMenu(systemClient.getCustomerMenu());
            loginToken.setHrMenu(systemClient.getHrMenu());
            loginToken.setInventoryMenu(systemClient.getInventoryMenu());
            loginToken.setPaymentMenu(systemClient.getPaymentMenu());
            loginToken.setPlatformMenu(systemClient.getPlatformMenu());
            loginToken.setStatus(systemClient.getStatus());
            return loginToken;
        }

        //判断账号是否在人事管理中
        Staff staff = staffService.findByAccount(account);
        if (staff != null){
            loginToken = new LoginToken();
            loginToken.setName(staff.getName());
            loginToken.setRole(RoleEnum.HUMAN_RESOURCE_MANAGEMENT);
            loginToken.setSystemClientAccount(staff.getSystemClientAccount());
            //对比登录密码和正确密码
            checkAccountAndPassword(staff.getPassword(), CodeTools.md5AndSalt(password, staff.getSalt()),lang);
            //获取所属系统用户的权限赋予自己
            systemClient = systemClientService.findByAccount(loginToken.getSystemClientAccount());
            loginToken.setCustomerMenu(systemClient.getCustomerMenu());
            loginToken.setHrMenu(systemClient.getHrMenu());
            loginToken.setInventoryMenu(systemClient.getInventoryMenu());
            loginToken.setPaymentMenu(systemClient.getPaymentMenu());
            loginToken.setPlatformMenu(systemClient.getPlatformMenu());
            loginToken.setStatus(systemClient.getStatus());
            return loginToken;
        }

        //校验是否已经登录,如果已经登陆过删除之前的tokenId和缓存
        //checkLoginCache(administrators.getAccount());

        if("cn".equals(lang)){
            throw new AccountOrPasswordException();
        }
        if("jp".equals(lang)){
            throw new AccountOrPasswordException("アカウント情報が間違っています");
        }
        throw new AccountOrPasswordException("语言参数不正确");
    }


    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public R<LoginToken> login(@RequestBody @Valid AdminLogin req) {
        log.info("登录接口入参:{}====>ip:{}", JSONUtil.toJsonStr(req), HttpTools.getIp());
        //校验验证码
        Set<String> captchaCodeSet = ehcacheService.captchaCodeCache().get(SystemConstant.CAPTCHA_CODE);
        if (CollectionUtils.isEmpty(captchaCodeSet)) {
            if("cn".equals(req.getLang())){
                return R.failed("验证码有误或已过期");
            }if("jp".equals(req.getLang())){
                return R.failed("確認コードが間違っているか、期限切れです");
            }
            return R.failed("参数不正确");
        }

        boolean confirm = false;
        for(String key: captchaCodeSet){
            if (key.equalsIgnoreCase(req.getVerificationCode())){
                confirm = true;
                captchaCodeSet.remove(key);
                break;
            }
        }

        if (!confirm) {
            if("cn".equals(req.getLang())){
                return R.failed("验证码错误");
            }
            if("jp".equals(req.getLang())){
                return R.failed("認証コードが正しくありません");
            }
            return R.failed("参数不正确");
        }

        LoginToken loginToken = checkLogin(req.getAccount(), req.getPassword(),req.getLang());

        //生成token并返回
        loginToken.setAccount(req.getAccount());
        loginToken.setLang(req.getLang());
        loginToken.setLoginTime(LocalDateTime.now());
        loginToken.setTokenId(GenerateTools.createTokenId());

        ehcacheService.adminTokenCache().put(loginToken.getTokenId(), loginToken);

        //删除使用过的验证码缓存
        captchaCodeSet.remove(req.getVerificationCode());
        ehcacheService.captchaCodeCache().put(SystemConstant.CAPTCHA_CODE, captchaCodeSet);
        return R.ok(loginToken);
    }


    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    public R logout() {
        LoginToken loginToken = TokenTools.getLoginToken(false);
        if (loginToken == null){
            return R.ok(null);
        }
        String tokenId = loginToken.getTokenId();
        if (StringUtils.isBlank(tokenId)) {
            return R.ok(null);
        }
        ehcacheService.adminTokenCache().remove(tokenId);
        return R.ok(null);
    }


    @PostMapping("/getCaptchaCode")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    public synchronized R<CaptchaCode> get() {
        String codeImageStream = ehcacheService.getVC( 30, "每3秒超过30次点击验证码");

        CaptchaCode captchaCode = new CaptchaCode();
        captchaCode.setCaptchaImage("data:image/png;base64," + codeImageStream);
        return R.ok(captchaCode);
    }


}
