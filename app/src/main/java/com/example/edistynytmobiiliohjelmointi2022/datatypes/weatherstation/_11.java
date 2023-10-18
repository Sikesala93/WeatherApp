
package com.example.edistynytmobiiliohjelmointi2022.datatypes.weatherstation;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class _11 {

    @SerializedName("v")
    @Expose
    private double v;

    /**
     * No args constructor for use in serialization
     * 
     */
    public _11() {
    }

    /**
     * 
     * @param v
     */
    public _11(double v) {
        super();
        this.v = v;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public _11 withV(double v) {
        this.v = v;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(_11 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("v");
        sb.append('=');
        sb.append(this.v);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
