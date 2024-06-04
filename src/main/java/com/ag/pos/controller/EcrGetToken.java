package com.ag.pos.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.impl.AuditLogServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.JwtTokenUtil;

@RestController
public class EcrGetToken {

    @Autowired
    UserLoginService userLoginService;

    @Autowired
    AuditLogServiceImpl auditLogService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @SuppressWarnings("unchecked")
    @PostMapping("/generateToken") 
    public JSONObject retrieveDiscountRate(@RequestBody JSONObject requestJson, HttpServletRequest request)
            throws Exception {
        String uid = (String) requestJson.get("uid");
        String password = (String) requestJson.get("password");
        String ipAddress = request.getHeader("IP");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        JSONObject job = new JSONObject();
        try {
            AgLogger.logInfo("@@@@@@@@@@@ " + uid + " @@@@@@@@@@@ " + password);
            UserLogin user = userLoginService.validateUserPassword(uid, password);
            if (!Objects.isNull(user)) {
                String token = jwtTokenUtil.generateToken(user.getUserCode(), user.getUserId());
                job.put("code", "0000");
                job.put("msg", "Success");
                job.put("userCode", uid);
                job.put("id", user.getUserId());
                job.put("token", token);
                updateLoginInformation(user, token);
            } else {
                job.put("code", "0001");
                job.put("msg", "Invalid User Name or Password."); 
            }

        } catch (Exception e) {
            AgLogger.logerror(AckDiscountAvailed.class, " EXCEPTION  ", e);
            job.put("code", "9999");
            job.put("msg", "Failed");
        } finally {
            Date date = new Date();
            Timestamp time = new Timestamp(date.getTime());
            AuditLog adt = new AuditLog();
            adt.setUserId("0");
            adt.setEntryDate(time);
            adt.setRequest(uid + "|" + password);
            adt.setResponse(job.toString());
            adt.setRequestMode("POS");
            adt.setRequestIp(ipAddress);
            adt.setTxnType("GENERATE_TOKEN");
            adt.setMid("N/A");
            adt.setTid("N/A");
            adt.setSerialNum("N/A");
            adt.setCorpId("N/A");
            auditLogService.insertAuditLog(adt);
        }

        return job;

    }

    public void updateLoginInformation(UserLogin user, String token) {
        user.setToken(token);
        user.setTokenGenerationTime(new Timestamp(jwtTokenUtil.getIssuedAtDateFromToken(token).getTime()));
        user.setLastLogin(new Timestamp(new Date().getTime()));
        userLoginService.updateUser(user);
    }
}
