package com.shenjing.dengyuejinfu.entity;
import com.shenjing.dengyuejinfu.common.JsonServiceImpl;
import java.util.List;

/**
 * author : Leehor
 * date   : 2019/11/711:15
 * version: 1.0
 * desc   :
 */
public class CustomerBean extends JsonServiceImpl {
    private int id;
    private String levelType;
    private String levelName;
    private String logo;
    private List<Integer> direct;
    private List<Integer> inDirect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Integer> getDirect() {
        return direct;
    }

    public void setDirect(List<Integer> direct) {
        this.direct = direct;
    }

    public List<Integer> getInDirect() {
        return inDirect;
    }

    public void setInDirect(List<Integer> inDirect) {
        this.inDirect = inDirect;
    }

}
