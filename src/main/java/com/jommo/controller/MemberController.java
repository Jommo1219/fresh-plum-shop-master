package com.jommo.controller;


import cn.hutool.jwt.Claims;
import com.jommo.common.Result;
import com.jommo.domain.Member;
import com.jommo.dto.MemberDTO;
import com.jommo.service.MemberService;
import com.jommo.util.JwtUtil;
import com.jommo.util.Md5Util;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 不会开发的小虾米
 */
@RestController
@RequestMapping("/member")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //注册
    @PostMapping("/register")
    public Result Register(@RequestBody @Validated(MemberDTO.Register.class) MemberDTO memberDTO) {
        Member existedMember = memberService.getByUsername(memberDTO.getUsername());
        if (existedMember != null) {
            return Result.error("该用户名已存在");
        }
        Member member = new Member();
        BeanUtils.copyProperties(memberDTO, member);
        member.setPassword(Md5Util.getMD5String(memberDTO.getPassword()));
        memberService.save(member);
        return Result.success();
    }

    //登录
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody @Validated MemberDTO memberDTO) {
        Member loginMember = memberService.getByUsername(memberDTO.getUsername());
        if (loginMember == null) {
            return Result.error("用户名或密码错误");
        }
        //判断密码是否正确
        if (Md5Util.checkPassword(memberDTO.getPassword(), loginMember.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", loginMember.getUsername());
            claims.put("id", loginMember.getId());
            String token = JwtUtil.genToken(claims);
            //把token保存到redis中
            stringRedisTemplate.opsForValue().set(token, token, 7, TimeUnit.DAYS);
            Map<String, Object> res = new HashMap<>();
            res.put("token", token);
            res.put("memberInfo", loginMember);
            return Result.success(res);
        }
        return Result.error("用户名或密码错误");
    }

    @PostMapping("/update/avatar")
    @Validated
    public Result updateAvatar(@RequestParam("avatarUrl") @URL String avatarUrl) {
        memberService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PutMapping("/update/nickname")
    public Result updateNickname(@RequestParam("nickname") String nickname) {
        memberService.updateNickname(nickname);
        return Result.success();
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return Result.error("无效的认证信息");
        }
        String token = authHeader.substring(7);
        stringRedisTemplate.delete(token);
        return Result.success();
    }
}
