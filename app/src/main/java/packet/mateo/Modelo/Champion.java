package packet.mateo.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Champion {
    @SerializedName("championId")
    @Expose
    private Integer championId;
    @SerializedName("championLevel")
    @Expose
    private Integer championLevel;
    @SerializedName("championPoints")
    @Expose
    private Integer championPoints;
    @SerializedName("lastPlayTime")
    @Expose
    private Integer lastPlayTime;
    @SerializedName("championPointsSinceLastLevel")
    @Expose
    private Integer championPointsSinceLastLevel;
    @SerializedName("championPointsUntilNextLevel")
    @Expose
    private Integer championPointsUntilNextLevel;
    @SerializedName("chestGranted")
    @Expose
    private Boolean chestGranted;
    @SerializedName("tokensEarned")
    @Expose
    private Integer tokensEarned;
    @SerializedName("summonerId")
    @Expose
    private String summonerId;

    public Integer getChampionId() {
        return championId;
    }

    public void setChampionId(Integer championId) {
        this.championId = championId;
    }

    public Integer getChampionLevel() {
        return championLevel;
    }

    public void setChampionLevel(Integer championLevel) {
        this.championLevel = championLevel;
    }

    public Integer getChampionPoints() {
        return championPoints;
    }

    public void setChampionPoints(Integer championPoints) {
        this.championPoints = championPoints;
    }

    public Integer getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(Integer lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    public Integer getChampionPointsSinceLastLevel() {
        return championPointsSinceLastLevel;
    }

    public void setChampionPointsSinceLastLevel(Integer championPointsSinceLastLevel) {
        this.championPointsSinceLastLevel = championPointsSinceLastLevel;
    }

    public Integer getChampionPointsUntilNextLevel() {
        return championPointsUntilNextLevel;
    }

    public void setChampionPointsUntilNextLevel(Integer championPointsUntilNextLevel) {
        this.championPointsUntilNextLevel = championPointsUntilNextLevel;
    }

    public Boolean getChestGranted() {
        return chestGranted;
    }

    public void setChestGranted(Boolean chestGranted) {
        this.chestGranted = chestGranted;
    }

    public Integer getTokensEarned() {
        return tokensEarned;
    }

    public void setTokensEarned(Integer tokensEarned) {
        this.tokensEarned = tokensEarned;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

}
