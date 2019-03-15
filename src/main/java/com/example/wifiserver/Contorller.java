package com.example.wifiserver;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/pos")
    public @ResponseBody String getpos(@RequestBody List<wifi_get> wg) throws SQLException {
       String pppp = "请求无效";
       String p_pt = "无此点";
        if(wg.size()==0){
            pppp = "未接收到数据，等待重试";
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
            /*List<String> map = new ArrayList<>();
            for (String s : wifi_bssid){
                if (wiArray != null) {
                    wiArray.clear();
                }
                wiArray = ww.findAllByBssid(s);
                if(wiArray != null && !wiArray.isEmpty()){
                    for(wifi_info wifiInfo:wiArray){
                        if(!map.contains(wifiInfo.getPosition_id())){
                            map.add(wifiInfo.getPosition_id());
                        }
                    }
                }
            }*/
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
                pppp = new knn(waftinfo,map,ww).k;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        p_pt = p_pR.findByPosition(pppp).getX_p()+","+p_pR.findByPosition(pppp).getY_p();
        return  p_pt;
    }

    @GetMapping("/get_pixel")
    public @ResponseBody String get_pixel(String position){
        String pop = position;
        pixel_point p_pt = new pixel_point();
        p_pt = p_pR.findByPosition(pop);
        return p_pt.getX_p()+","+p_pt.getY_p();
    }

   
}
