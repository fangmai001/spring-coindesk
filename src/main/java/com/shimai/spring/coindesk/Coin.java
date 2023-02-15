package com.shimai.spring.coindesk;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

@Entity

public class Coin {

    @Id
    private String id;
    private String code;
    private String nameEng;
    private String nameChi;
    private Float rate;
    private Instant createTime;
    private Instant  updateTime;

    public Coin(){
        this(UUID.randomUUID().toString(), null);
    }
    public Coin(String code){
        this(UUID.randomUUID().toString(), code);
    }
    public Coin(String id, String code){
        this.id = id;
        this.code = code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNameChi(String nameChi) {
        this.nameChi = nameChi;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public Float getRate() {
        return rate;
    }

    public String getCode() {
        return code;
    }

    public String getNameChi() {
        return nameChi;
    }

    public String getNameEng() {
        return nameEng;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }
}
