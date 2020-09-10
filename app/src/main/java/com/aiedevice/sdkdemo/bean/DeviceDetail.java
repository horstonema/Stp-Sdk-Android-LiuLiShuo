package com.aiedevice.sdkdemo.bean;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.aiedevice.sdkdemo.bean.state.MsgInfoData;
import com.aiedevice.sdkdemo.bean.state.PlayInfoData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeviceDetail implements Serializable, Cloneable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final String PUDDING_S_DEVICE_TYPE = "pudding1s";
    public static final String PUDDING_PLUS_DEVICE_TYPE = "pudding-plus";

    @SerializedName("mcid")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("autodefense")
    private boolean autoDenfense;

    @SerializedName("isdefense")
    private boolean isDefense;

    private boolean online;

    @SerializedName("users")
    private List<User> users;

    @SerializedName("devices")
    private List<Device> devices;

    @SerializedName("volume")
    private double volume;

    @SerializedName("mtdetect")
    private int moveDetection;

    @SerializedName("rvnotify")
    private int userEnterRemind;

    @Expose(serialize = false, deserialize = false)
    private int memeryExtends;

    @Expose(serialize = false, deserialize = false)
    private boolean used = false;

    @Expose(serialize = false, deserialize = false)
    private boolean enabled = true;

    @SerializedName("guard_times")
    private ArrayList<GuardTime> GuardTimes;

    private int chatlevel;

    private String wifissid;

    private int insurance;
    private int hall;

    @SerializedName("index_config")
    private String homepageConfigFileName;

    private PlayInfoData playinfo;

    private MsgInfoData msginfo;

    @SerializedName("face_track")
    private FamilyDynamicsTrackEntity faceTrack;

    private FamilyDynamicsMomentEntity moment;

    private boolean power;//false未充电 true充电
    @SerializedName("power_supply")
    private boolean powerSupply;//false未插电 true插电
    private int battery;//电量 0 - 100

    private String timbre;//设备音效

    //baby的成长建议
    private String tips;

    private GrowplanInfo growplan;

    @SerializedName("device_type")//pudding1s  pudding-plus
    private String deviceType;

    public DeviceDetail getDetail() {
        return detail;
    }

    public void setDetail(DeviceDetail detail) {
        this.detail = detail;
    }

    private DeviceDetail detail;

    @Override
    public boolean equals(Object obj) {
        try {
            return id.equals(((DeviceDetail) obj).id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getMemeryExtends() {
        return memeryExtends;
    }

    public void setMemeryExtends(int memeryExtends) {
        this.memeryExtends = memeryExtends;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getMoveDetection() {
        return moveDetection;
    }

    public void setMoveDetection(int moveDetection) {
        this.moveDetection = moveDetection;
    }

    public int getUserEnterRemind() {
        return userEnterRemind;
    }

    public void setUserEnterRemind(int userEnterRemind) {
        this.userEnterRemind = userEnterRemind;
    }

    public boolean isAutoDenfense() {
        return autoDenfense;
    }

    public void setAutoDenfense(boolean autoDenfense) {
        this.autoDenfense = autoDenfense;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        if (TextUtils.isEmpty(name)) {
            return id;
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDefense() {
        return isDefense;
    }

    public void setDefense(boolean isDefense) {
        this.isDefense = isDefense;
    }

    public ArrayList<GuardTime> getGuardTimes() {
        return GuardTimes;
    }

    public void setGuardTimes(ArrayList<GuardTime> guardTimes) {
        GuardTimes = guardTimes;
    }

    public int getChatLevel() {
        return chatlevel;
    }

    public void setChatLevel(int level) {
        this.chatlevel = level;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getWifissid() {
        return wifissid;
    }

    public void setWifissid(String wifissid) {
        this.wifissid = wifissid;
    }

    public int getInsurance() {
        return insurance;
    }

    public void setInsurance(int insurance) {
        this.insurance = insurance;
    }

    public int getHall() {
        return hall;
    }

    public void setHall(int hall) {
        this.hall = hall;
    }

    public String getHomepageConfigFileName() {
        return homepageConfigFileName;
    }

    public void setHomepageConfigFileName(String homepageConfigFileName) {
        this.homepageConfigFileName = homepageConfigFileName;
    }

    public MsgInfoData getMsginfo() {
        return msginfo;
    }

    public void setMsginfo(MsgInfoData msginfo) {
        this.msginfo = msginfo;
    }

    public PlayInfoData getPlayinfo() {
        return playinfo;
    }

    public void setPlayinfo(PlayInfoData playinfo) {
        this.playinfo = playinfo;
    }

    public FamilyDynamicsTrackEntity getFaceTrack() {
        return faceTrack;
    }

    public void setFaceTrack(FamilyDynamicsTrackEntity faceTrack) {
        this.faceTrack = faceTrack;
    }

    public FamilyDynamicsMomentEntity getMoment() {
        return moment;
    }

    public void setMoment(FamilyDynamicsMomentEntity moment) {
        this.moment = moment;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public boolean isPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(boolean powerSupply) {
        this.powerSupply = powerSupply;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public String getTimbre() {
        return timbre;
    }

    public void setTimbre(String timbre) {
        this.timbre = timbre;
    }

    public int getChatlevel() {
        return chatlevel;
    }

    public void setChatlevel(int chatlevel) {
        this.chatlevel = chatlevel;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public GrowplanInfo getBabyinfo() {
        return growplan;
    }

    public void setBabyinfo(GrowplanInfo babyinfo) {
        this.growplan = babyinfo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    //年龄目前放在 homepageConfigFileName 字符串里
    public int getAge() {
        return getBabyAgeFromMasterDetail(homepageConfigFileName);
    }

    /**
     * 从主控详情获取 baby年龄
     * 0-1岁（id=1）
     * 1-2岁（id=2）
     * 2-3岁（id=3）
     * 3-4岁（id=4）
     * 4-5岁（id=5）
     * 5-6岁（id=6）
     * 6岁以上（id=7）
     *
     * @return
     */
    public static int getBabyAgeFromMasterDetail(String ageString) {
        int age = 1;
        if (!TextUtils.isEmpty(ageString)) {
            int length = ageString.length();
            if (length > 0) {
                try {
                    age = Integer.parseInt(String.valueOf(ageString.charAt(length - 1)));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return age;
    }

    public void setHomepageConfigFileNameByAge(int age) {
        try {
            if (TextUtils.isEmpty(homepageConfigFileName)) {
                return;
            }
            int length = homepageConfigFileName.length();
            if (length > 0) {
                homepageConfigFileName = homepageConfigFileName.replaceFirst(homepageConfigFileName.charAt(length - 1) + "", age + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPuddingS() {
        return PUDDING_S_DEVICE_TYPE.equalsIgnoreCase(deviceType);
    }

    public boolean isPuddingPLUS() {
        return PUDDING_PLUS_DEVICE_TYPE.equalsIgnoreCase(deviceType);
    }

    public static class GrowplanInfo implements Serializable {
        public static final String BOY = "boy";
        public static final String GIRL = "girl";

        private String babyId;
        private String mcid;//此字段不是主控id，目前没有意义
        private String nickname = "";
        private String birthday;
        private String sex;
        @SerializedName("updated_at")
        private String updatedTime;

        private String img;

        private String tips;//儿童成长的发育期
        private String age;//三岁三个月
        private List<String> tags;//标签 "亲子" "儿童歌谣"

        public String getBabyId() {
            return babyId;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(ArrayList<String> tags) {
            this.tags = tags;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMcid() {
            return mcid;
        }

        public void setMcid(String mcid) {
            this.mcid = mcid;
        }

        public String getUpdatedTime() {
            return updatedTime;
        }

        public void setUpdatedTime(String updatedTime) {
            this.updatedTime = updatedTime;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public boolean isNOtSetBabyInfo() {
            return TextUtils.isEmpty(age);
        }
    }

    @Override
    public String toString() {
        return "MasterDetail{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", autoDenfense=" + autoDenfense +
                ", isDefense=" + isDefense +
                ", online=" + online +
                ", users=" + users +
                ", devices=" + devices +
                ", volume=" + volume +
                ", moveDetection=" + moveDetection +
                ", userEnterRemind=" + userEnterRemind +
                ", memeryExtends=" + memeryExtends +
                ", used=" + used +
                ", enabled=" + enabled +
                ", GuardTimes=" + GuardTimes +
                ", chatlevel=" + chatlevel +
                ", wifissid='" + wifissid + '\'' +
                ", insurance=" + insurance +
                ", hall=" + hall +
                ", homepageConfigFileName='" + homepageConfigFileName + '\'' +
                ", playinfo=" + playinfo +
                ", msginfo=" + msginfo +
                ", faceTrack=" + faceTrack +
                ", moment=" + moment +
                ", power=" + power +
                ", powerSupply=" + powerSupply +
                ", battery=" + battery +
                ", timbre='" + timbre + '\'' +
                ", tips='" + tips + '\'' +
                ", babyinfo=" + growplan +
                ", deviceType='" + deviceType + '\'' +
                ", detail=" + detail +
                '}';
    }
}
