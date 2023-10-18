
package com.example.edistynytmobiiliohjelmointi2022.datatypes.weatherstation;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class WeatherStation {

    @SerializedName("d")
    @Expose
    private D d;
    @SerializedName("ts")
    @Expose
    private String ts;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WeatherStation() {
    }

    /**
     * 
     * @param d
     * @param ts
     */
    public WeatherStation(D d, String ts) {
        super();
        this.d = d;
        this.ts = ts;
    }

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

    public WeatherStation withD(D d) {
        this.d = d;
        return this;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public WeatherStation withTs(String ts) {
        this.ts = ts;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(WeatherStation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("d");
        sb.append('=');
        sb.append(((this.d == null)?"<null>":this.d));
        sb.append(',');
        sb.append("ts");
        sb.append('=');
        sb.append(((this.ts == null)?"<null>":this.ts));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
