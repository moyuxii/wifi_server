package com.example.wifiserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/get_pixel")
    public @ResponseBody String get_pixel(String position){
        String pop = position;
        pixel_point p_pt ;
        p_pt = p_pR.findByPosition(pop);
        return p_pt.getX_p()+","+p_pt.getY_p();
    }

    @PostMapping("/Login")
    public @ResponseBody Responsess add_user(@RequestBody Uuser uuser){
        Responsess resopnses =null;
        if(!uu.existsByName(uuser.getName())){
            uu.save(uuser);
            resopnses = new Responsess("ok","注册成功");
        }else{
            resopnses = new Responsess("error","用户名已存在");
        }
        return resopnses;
    }

    @PostMapping("/Loading")
    public @ResponseBody Responsess user_load(@RequestBody Uuser uuser){
        Responsess resopnses =null;
        if(uu.existsByName(uuser.getName())){

        }else{
            resopnses = new Responsess("error","用户名不存在");
        }
        return resopnses;
    }

   
}
