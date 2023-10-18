
package com.example.edistynytmobiiliohjelmointi2022.datatypes.weatherstation;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class _4 {

    @SerializedName("v")
    @Expose
    private double v;

    /**
     * No args constructor for use in serialization
     * 
     */
    public _4() {
    }

    /**
     * 
     * @param v
     */
    public _4(double v) {
        super();
        this.v = v;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public _4 withV(double v) {
        this.v = v;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(_4 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
