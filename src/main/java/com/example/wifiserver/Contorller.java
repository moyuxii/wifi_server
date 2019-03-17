package com.example.wifiserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@Controller
public class Contorller {
    @Autowired
    private pixel_pointResp p_pR;
    @Autowired
    private wifi_infoResp ww;
    @Autowired
    private UserResp uu;
    @Autowired
    private friendshipResp ff;

    @PostMapping("/pos")
    public @ResponseBody position_info getpos(@RequestBody List<wifi_get> wg) throws SQLException {
       String location;
        position_info position_info = new position_info(0,0,0);
        if(wg.size()==0){
            return position_info;
        }else {
            List<String> wifi_bssid = new ArrayList<String>();    /*仅存地址的表*/
            Map<String, Integer> waftinfo = new HashMap<String, Integer>();   /*存地址与强度的表*/
            for (wifi_get aWg : wg) {    /*将收到的数据存入链表中*/
                wifi_get dd = new wifi_get();
                dd.setBssid(aWg.getBssid());
                dd.setLevel(aWg.getLevel());
                wifi_bssid.add(dd.getBssid());
                waftinfo.put(aWg.getBssid(), dd.getLevel());
            }
            List<wifi_info> wiArray = null;
            Map<String,Integer> map =new HashMap<>();
            for(String s:wifi_bssid){
                if (wiArray != null) {
                    wiArray.clear();
                }
                wiArray = ww.findAllByBssid(s);
                if(!wiArray.isEmpty()){
                for(wifi_info wifiInfo:wiArray){
                    int num = map.getOrDefault(wifiInfo.getPosition_id(),0);
                    map.put(wifiInfo.getPosition_id(),++num);
                }
                }
            }
            try {
                location = new knn(waftinfo,map,ww).k;
                position_info = new position_info(p_pR.findByPosition(location).getFloor(),p_pR.findByPosition(location).getX_p(),p_pR.findByPosition(location).getY_p());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.gc();
        return position_info;
    }

    @GetMapping("/get_pixel")  /*获取像素点*/
    public @ResponseBody position_info get_pixel(String position){
       return new position_info(p_pR.findByPosition(position).getFloor(),p_pR.findByPosition(position).getX_p(),p_pR.findByPosition(position).getY_p());
    }

    @PostMapping("/Login")  /*注册*/
    public @ResponseBody Responsess add_user(@RequestBody Uuser uuser){
        Responsess resopnses =null;
        if(!uu.existsByName(uuser.getName())){
            uuser.setX_p(0);
            uuser.setY_p(0);
            uuser.setFloor(0);
            uuser.setShare_position(false);
            uu.save(uuser);
            resopnses = new Responsess("ok","注册成功");
        }else{
            resopnses = new Responsess("error","用户名已存在");
        }
        return resopnses;
    }

    @PostMapping("/Loading")   /*登陆*/
    public @ResponseBody Responsess user_load(@RequestBody Uuser uuser){
        Responsess resopnses =null;
        if(uu.existsByName(uuser.getName())&&(uu.existsByNameAndPassword(uuser.getName(),uuser.getPassword()))){
            resopnses = new Responsess("ok",uu.findByName(uuser.getName()).getId()+"");
        }else{
            resopnses = new Responsess("error","用户名不存在");
        }
        return resopnses;
    }

    @PostMapping("/Change_Password")     /*更改密码*/
    @Transactional
    public @ResponseBody Responsess change_passsword(@RequestBody Uuser[] u){
        Uuser u1 = new Uuser(u[0].getName(),u[0].getPassword());
        Uuser u2 = new Uuser(u[1].getName(),u[1].getPassword());
        Responsess resopnses =null;
        if((u1.getName().equals(u2.getName()))&&(uu.existsByNameAndPassword(u1.getName(),u1.getPassword()))){
            uu.updateOne(u2.getPassword(),u2.getName());
            resopnses = new Responsess("ok","密码更新成功");
        }else{
            resopnses = new Responsess("error","更新失败");
        }
        System.gc();
        return resopnses;
    }

    @PostMapping("/user_destroy")    /*销毁用户*/
    @Transactional
    public @ResponseBody Responsess user_destroy(@RequestBody Uuser uuser){
        Responsess responsess = null;
        if(uu.existsByName(uuser.getName())&&uu.existsByNameAndPassword(uuser.getName(),uuser.getPassword())){
            uu.deleteByName(uuser.getName());
            responsess = new Responsess("ok","用户已注销");
        }else{
            responsess = new Responsess("error","注销失败");
        }
        return responsess;
    }

    @PostMapping("/open_share")      /*打开位置分享*/
    @Transactional
    public @ResponseBody Responsess open_share(@RequestBody  Uuser uuser){
        Responsess responsess = null;
        if(uu.existsByName(uuser.getName())&&uu.existsByNameAndPassword(uuser.getName(),uuser.getPassword())){
            uu.op_share(uuser.getName());
            responsess = new Responsess("ok","位置共享已打开");
        }else{
            responsess = new Responsess("error","操作失败");
        }
        return responsess;
    }

    @PostMapping("/close_share")    /*关闭位置分享*/
    @Transactional
    public @ResponseBody Responsess close_share(@RequestBody  Uuser uuser){
        Responsess responsess = null;
        if(uu.existsByName(uuser.getName())&&uu.existsByNameAndPassword(uuser.getName(),uuser.getPassword())){
            uu.clo_share(uuser.getName());
            responsess = new Responsess("ok","位置共享已关闭");
        }else{
            responsess = new Responsess("error","操作失败");
        }
        return responsess;
    }

    @PostMapping("/update_position")     /*更新位置信息*/
    @Transactional
    public @ResponseBody Responsess update_position(@RequestBody Uuser uuser){
        Responsess responsess = null;
        if(uu.existsByName(uuser.getName())&&uu.existsByNameAndPassword(uuser.getName(),uuser.getPassword())){
            if((uuser.getX_p() != 0) && (uuser.getY_p() != 0)){
                uu.update_position(uuser.getX_p(),uuser.getY_p(),uuser.getFloor(),uuser.getName());
                responsess = new Responsess("ok","位置信息更新成功");
            }else{
                responsess = new Responsess("error","位置信息更新失败");
            }
        }else{
            responsess = new Responsess("error","操作失败");
        }
        return responsess;
    }

    @GetMapping("/add_friend")   /*加好友*/
    public @ResponseBody Responsess add_friend(String uuser,int friend_id,String friend){
        Responsess responsess = null;
        if(uu.existsByName(uuser)&&uu.existsByName(friend)&&uu.existsByIdAndName(friend_id,friend)){
            if(ff.existsByUuserAndFriend(uuser,friend)){
                responsess = new Responsess("error","该用户已是您的好友");
            }else{
                ff.save(new friendship(uuser,friend));
                ff.save(new friendship(friend,uuser));
                responsess = new Responsess("ok","成功与"+friend+"成为好友");
            }
        }else{
            responsess = new Responsess("error","操作失败");
        }
        return  responsess;
    }

    @GetMapping("/del_friend")   /*删好友*/
    @Transactional
    public @ResponseBody Responsess del_friend(String uuser,String friend){
        Responsess responsess = null;
        if(ff.existsByUuserAndFriend(uuser,friend)){
            ff.deleteByUuserAndFriend(uuser,friend);
            ff.deleteByUuserAndFriend(friend,uuser);
            responsess = new Responsess("ok","好友删除成功");
        }else{
            responsess = new Responsess("error","操作失败");
        }
        return  responsess;
    }

    @GetMapping("/get_friend_position")   /*获取好友位置*/
    public @ResponseBody position_info get_friend_position(String uuser,String friend){
        position_info pos_info = new position_info(0,0,0);
        if(ff.existsByUuserAndFriend(uuser,friend)&&uu.findByName(friend).getShare_position()){
            Uuser frid = uu.findByName(friend);
            pos_info = new position_info(frid.getX_p(),frid.getY_p(),frid.getFloor());
        }
        return  pos_info;
    }
}
