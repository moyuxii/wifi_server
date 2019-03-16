package com.example.wifiserver;

import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

class knn {
    private String [] position;//储存前四个最佳位置。
    private List  [] position_wifiinfo;//储存上述四个房间的WiFi信号
    private double [] costFunction = new double[4];//代价函数
    private Map<String,Integer> wifiinfo_scan;
    private wifi_infoResp rr;
    String k;
    knn(Map<String, Integer> data_scanning, Map<String, Integer> map, wifi_infoResp rr) throws SQLException {
        this.rr = rr;
        this.wifiinfo_scan=data_scanning;
        position_wifiinfo = new  List[4];
        initizlize(map);
        for(int i=0;i<position.length;i++)
        {
            position_wifiinfo[i]=get_position_wifiinfo(position[i].trim());
        }
        k = KNN_realize();
    }

    private void initizlize(Map<String,Integer> map) throws SQLException{
        position = new String[4];
        Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,Integer>>() {
            @Override
            public int compare(Entry<String, Integer> o1,
                               Entry<String, Integer> o2) {
                return o2.getValue()-o1.getValue();
            }
        };
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        list.sort(valueComparator);
        int index=0;
        for (Map.Entry<String, Integer> entry : list) {
            if(index>=position.length){
                break;
            }
            position[index++]=entry.getKey();
        }
    }

    private List<wifi_get> get_position_wifiinfo(String pos){
        List<wifi_get> www = new ArrayList<>();
        List<wifi_info> wifiInfos = rr.findAllByPosition_id(pos);
        for(wifi_info ss : wifiInfos){
            wifi_get aaa = new wifi_get();
            aaa.setBssid(ss.getBssid());
            aaa.setLevel(ss.getLevel());
            www.add(aaa);
        }
        return www;
    }

    private String KNN_realize(){
        wifi_get data;
        String bssid;
        int level,level_temp;
        for(int i=0;i<position_wifiinfo.length;i++){
            int sum=0;
            for(int j=0;j<position_wifiinfo[i].size();j++){
                data=(wifi_get) position_wifiinfo[i].get(j);
                bssid=data.getBssid();
                if(wifiinfo_scan.containsKey(bssid)){
                    level=data.getLevel();
                    level_temp=wifiinfo_scan.get(bssid);
                    sum+=Math.pow(level-level_temp, 2);
                }
            }
            costFunction[i]= Math.sqrt(sum);
        }
        double cost=100;
        int cost_id=100;
        for(int j=0;j<costFunction.length;j++){
            if(costFunction[j]<cost){
                cost_id=j;
                cost=costFunction[j];
            }
        }
        return position[cost_id];
    }
}

