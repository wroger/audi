
package com.example.demo.user;
/**
 * @Author: wroger
 * @time: 2022/05/10
 * @Discription: 用户服务增删改查
 */

 
import java.util.List;
import java.util.Map;

import com.example.demo.comman.Md5;
import com.example.demo.limit.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //注入JdbcTemplate
    @Autowired
    JdbcTemplate jdbcTemplate;
    //日志
    Logger logger = LoggerFactory.getLogger(getClass());
    //获取ftp地址
    @Value("${ftp_ip}")
    private String ftpIp;
    
    public UserService(JdbcTemplate jdbc) {
        jdbcTemplate = jdbc;
    }

    public Map<String, Object> searchId(String id) {

        String sql = "select * from coop_users where coop_user_id=? and rownum=1";
        Map<String, Object> newMap = jdbcTemplate.queryForMap(sql, id);
        return newMap;
    }

    public List<UserDao> searchAll(String order, int page, int limit) {
        String sql = "select * from Interested ";
        if (null != order && order.length() != 0)
            sql += " order by " + order;
        sql += " limit " + page * limit + "," + (page + 1) * limit;

        logger.debug("searcheAll SQL=" + sql);
        
        BeanPropertyRowMapper<UserDao> rowMapper = new BeanPropertyRowMapper<>(UserDao.class);
        List<UserDao> userList = jdbcTemplate.query(sql, rowMapper);
        return userList;
    }

    public List<UserDao> searchName(String name, int page, int limit) {
        String sql = "select * from Interested where name like '%" + name + "%'";
        sql += " limit " + page * limit + "," + (page + 1) * limit;
        BeanPropertyRowMapper<UserDao> rowMapper = new BeanPropertyRowMapper<>(UserDao.class);
        List<UserDao> userList = jdbcTemplate.query(sql, rowMapper);
        return userList;
    }

    public int add(UserDao user) {

        String addsql = "insert into Interested(name,phone,interested) values(?,?,?)";
        Object[] args = { user.getName(), user.getPhone(), user.getInterested() };
        int count = jdbcTemplate.update(addsql, args);
        System.out.println(count);
        return count;
    }

    public int update(UserDao user) {
        String updatesql = "update Interested set name=?,phone=?,interested=? where id=?";
        Object[] args = {user.getName(), user.getPhone(), user.getInterested(),user.getId()};//注意参数顺序
        int count = jdbcTemplate.update(updatesql,args);
        System.out.println(count);
        return count;
    }

    public int delete(String id) {
        String deletesql = "delete from Interested where id=?";
        int count = jdbcTemplate.update(deletesql, id);
        System.out.println(count);
        return count;
    }

    public Object setpassword(Map<String, Object> map) {
        String name = map.get("name").toString();
        String password = map.get("password").toString();
        password=Md5.encryptToMD5(password);
        String deletesql = "update users set password=? where name=?";
        int count = jdbcTemplate.update(deletesql, password, name);
        System.out.println(count);
        return count;
    }

    public Map<String, Object> login(Map<String, Object> map) {

        String name = map.get("name").toString();
        String password = map.get("password").toString();
        password = Md5.encryptToMD5(password);
        String sql = "select name from users where name=? and password=?";
        try {
            Map<String, Object> newMap = jdbcTemplate.queryForMap(sql, name, password);
            return newMap;
        } catch (Exception e) {
            throw new LoginException("无效的用户名或者密码！");
        }
    }
}