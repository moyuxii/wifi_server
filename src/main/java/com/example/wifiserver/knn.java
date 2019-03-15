package com.example.wifiserver;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

public class knn {
    /*private double INF = 10000;
    public String position;//储存位置,用于返回最后位置。
    private Map<String,Integer> wifiinfo_sql;
    private List<String> map_sql;*/
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

    /*private Map<String,Integer> get_position_wifiinfo(String pos){
        Map<String,Integer> mmap = new HashMap<>();
        List<wifi_info> wifiInfos = rr.findAllByPosition_id(pos);
        for(wifi_info wifiInfos1 : wifiInfos){
            mmap.put(wifiInfos1.getBssid(),wifiInfos1.getLevel());
        }
        return mmap;
    }*/

    private String KNN_realize(){
        //knn实现算法
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
        /*position=null;
        double cost=INF;
        double level_sum=0;
        double level_sql,level_scan;//信号强度
        for(String pos:map_sql){
            level_sum=0;
            wifiinfo_sql = get_position_wifiinfo(pos);
            for(String key:wifiinfo_sql.keySet()){
                if(wifiinfo_scan.containsKey(key)){
                    level_sql=wifiinfo_sql.get(key);
                    level_scan=wifiinfo_scan.get(key);
                    level_sum += Math.pow(level_scan-level_sql,2)*(level_sql/100);
                }
            }
            if(cost>level_sum){
                cost=level_sum;
                position=pos;
            }
        }
        return position;*/
    }

