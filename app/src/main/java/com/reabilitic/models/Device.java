package com.reabilitic.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.reabilitic.R;
import com.reabilitic.helpers.NetworkInfoHelper;
import com.reabilitic.helpers.VendorHelper;
import com.stealthcopter.networktools.ARPInfo;


public class Device implements Parcelable {
    public static final Creator<Device> CREATOR = new Creator<Device>() {
        public Device createFromParcel(Parcel parcel) {
            return new Device(parcel);
        }

        public Device[] newArray(int i) {
            return new Device[i];
        }
    };
    public String mDescription = null;
    public int mIconTypeResources;
    public String mLocalIP;
    public String mMacAddress;
    public String mVendor;
    public boolean needBlock = true;

    public Device(Parcel parcel) {
        this.mLocalIP = parcel.readString();
        this.mMacAddress = parcel.readString();
        this.mIconTypeResources = parcel.readInt();
        this.mVendor = parcel.readString();
    }

    public Device(String str, Context context) {
        this.mLocalIP = str;
        this.mMacAddress = setMacAddressAndDescription(str, context);
        this.mVendor = VendorHelper.getVendorFromDevice(context, this);
        if (this.mDescription == null) {
            this.mDescription = this.mVendor;
        }
        this.mIconTypeResources = VendorHelper.getIconFromDevice(context, this);
    }

    public static String getMacAddress(String str, Context context) {
        String mACFromIPAddress = ARPInfo.getMACFromIPAddress(str);
        if (mACFromIPAddress != null) {
            return mACFromIPAddress.length() < 16 ? "unknown_mac_address" : mACFromIPAddress;
        } else {
            NetworkInfoHelper networkInfoHelper = new NetworkInfoHelper(context);
            return str.equals(networkInfoHelper.s_ipAddress) ? networkInfoHelper.getInterfaceMacAddress(context) : "unknown_mac_address";
        }
    }

    public int describeContents() {
        return 0;
    }

    public String setMacAddressAndDescription(String str, Context context) {
        String mACFromIPAddress = ARPInfo.getMACFromIPAddress(str);
        NetworkInfoHelper networkInfoHelper = new NetworkInfoHelper(context);
        if (str.equals(networkInfoHelper.s_gateway)) {
            this.mDescription = "your_router";
            this.mIconTypeResources = R.mipmap.ic_launcher;
            this.needBlock = false;
        }
        if (mACFromIPAddress != null) {
            return mACFromIPAddress.length() < 16 ? "unknown_mac_address" : mACFromIPAddress;
        } else {
            if (!str.equals(networkInfoHelper.s_ipAddress)) {
                return "unknown_mac_address";
            }
            this.mDescription = "your_device";
            this.mIconTypeResources = R.mipmap.ic_launcher;
            mACFromIPAddress = networkInfoHelper.getInterfaceMacAddress(context);
            this.needBlock = false;
            return mACFromIPAddress;
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mLocalIP);
        parcel.writeString(this.mMacAddress);
        parcel.writeInt(this.mIconTypeResources);
        parcel.writeString(this.mVendor);
    }
}
