package com.example.ullar.golf.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by UllaR on 09-07-2017.
 */

public class Order {
    public long beer;
    public long hole;
    public long sandwich;
    public String uid;

    public Order() {

    }

    public Order(long beer,  long sandwich, long hole, String uid) {
        this.beer = beer;
        this.hole = hole;
        this.sandwich = sandwich;
        this.uid = uid;
    }

    public long getBeer() {
        return beer;
    }

    public void setBeer(long beer) {
        this.beer = beer;
    }

    public long getHole() {
        return hole;
    }

    public void setHole(long hole) {
        this.hole = hole;
    }

    public long getSandwich() {
        return sandwich;
    }

    public void setSandwich(long sandwich) {
        this.sandwich = sandwich;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("beer", beer);
        result.put("sandwich", sandwich);
        result.put("hole", hole);
        result.put("uid", uid);
        return result;
    }

    public static Order mapToOrder(Map<String, Object> map) {
        Order result = new Order();
        result.setBeer((long)map.get("beer"));
        result.setSandwich((long)map.get("sandwich"));
        result.setHole((long)map.get("hole"));
        result.setUid((String)map.get("uid"));
        return result;
    }
}
