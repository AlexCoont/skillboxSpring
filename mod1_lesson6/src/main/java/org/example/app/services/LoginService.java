package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger(LoginService.class);
    private final Map<String, String> users = new TreeMap<>();
    private final String emptyVar = "";

    public boolean authenticate(LoginForm loginFrom) {
        logger.info("try auth with user-form: " + loginFrom);
        if (users.containsKey(loginFrom.getUsername())){
            return users.get(loginFrom.getUsername()).equals(loginFrom.getPassword());
        }
        return false;
        //return loginFrom.getUsername().equals("root") && loginFrom.getPassword().equals("123");
    }

    public boolean registration(LoginForm loginFrom){
        if (emptyVar.equals(loginFrom.getUsername()) || emptyVar.equals(loginFrom.getPassword())){
            logger.info("registration FAIL - empty password or username " + loginFrom);
            return false;
        }
        logger.info("try registration with user-form: " + loginFrom);
        if (!users.containsKey(loginFrom.getUsername())){
            users.put(loginFrom.getUsername(), loginFrom.getPassword());
            return true;
        } else {
            return false;
        }
    }
}
