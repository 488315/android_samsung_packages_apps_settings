package com.samsung.android.settings.wifi.mobileap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.wifi.SemWifiApBleScanResult;
import com.samsung.android.wifi.SemWifiApSmartWhiteList;
import com.samsung.android.wifi.SemWifiManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApCustomPreference extends Preference
        implements Preference.OnPreferenceClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SemWifiApBleScanResult mBleScanAllowedDevice;
    public AutoHotspotCustomPreferenceListener mListener;
    public Drawable mSecondaryIcon;
    public AnonymousClass1 mSecondaryIconClickListener;
    public final int mSecondaryIconResId;
    public SemWifiApSmartWhiteList.SmartWhiteList mSmartWhiteList;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApCustomPreference$1, reason: invalid class name */
    public final class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {}

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            int i = WifiApCustomPreference.$r8$clinit;
            Log.i("WifiApCustomPreference", "mSecondaryIcon`s onClick() - Triggered");
            WifiApCustomPreference wifiApCustomPreference = WifiApCustomPreference.this;
            if (wifiApCustomPreference.mSmartWhiteList == null) {
                wifiApCustomPreference.mListener.onSecondaryIconClicked(null);
                return;
            }
            if (SemWifiManager.MHSDBG) {
                Log.d(
                        "WifiApCustomPreference",
                        "mSecondaryIcon`s onClick() - (Remove) Removing Device Name: "
                                + WifiApCustomPreference.this.mSmartWhiteList.getName()
                                + " mac: "
                                + WifiApCustomPreference.this.mSmartWhiteList.getMac());
            }
            WifiApCustomPreference wifiApCustomPreference2 = WifiApCustomPreference.this;
            wifiApCustomPreference2.mListener.onSecondaryIconClicked(
                    wifiApCustomPreference2.mSmartWhiteList);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface AutoHotspotCustomPreferenceListener {
        void onPreferenceClicked(Object obj);

        void onSecondaryIconClicked(Object obj);
    }

    public WifiApCustomPreference(
            Context context,
            SemWifiApBleScanResult semWifiApBleScanResult,
            AutoHotspotCustomPreferenceListener autoHotspotCustomPreferenceListener) {
        super(context);
        this.mSecondaryIconClickListener = new AnonymousClass1();
        Log.i(
                "WifiApCustomPreference",
                "WifiApCustomPreference() - SemWifiApBleScanResult device received.");
        this.mSmartWhiteList = null;
        this.mBleScanAllowedDevice = semWifiApBleScanResult;
        this.mListener = autoHotspotCustomPreferenceListener;
        setLayoutResource(R.layout.sec_wifi_ap_custom_preference_small);
        setKey(semWifiApBleScanResult.mWifiMac);
        setTitle(semWifiApBleScanResult.mSSID);
        if (semWifiApBleScanResult.mhidden == 1) {
            setIcon(R.drawable.ic_wifi_ap_tablet);
        } else {
            setIcon(R.drawable.ic_wifi_ap_mobile);
        }
        setOnPreferenceClickListener(this);
        if (SemWifiManager.MHSDBG) {
            StringBuilder sb =
                    new StringBuilder(
                            "WifiApCustomPreference() - SemWifiApBleScanResult Preference added: ");
            sb.append(semWifiApBleScanResult.mSSID);
            sb.append(", mac: ");
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    sb, semWifiApBleScanResult.mWifiMac, "WifiApCustomPreference");
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        Log.i("WifiApCustomPreference", "onBindViewHolder() - Triggered");
        super.onBindViewHolder(preferenceViewHolder);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.icon_secondary);
        if (imageView != null) {
            imageView.setOnClickListener(this.mSecondaryIconClickListener);
            if (this.mSecondaryIconResId != 0 || this.mSecondaryIcon != null) {
                if (this.mSecondaryIcon == null) {
                    this.mSecondaryIcon = getContext().getDrawable(this.mSecondaryIconResId);
                }
                Drawable drawable = this.mSecondaryIcon;
                if (drawable != null) {
                    imageView.setImageDrawable(drawable);
                }
            }
            imageView.setVisibility(this.mSecondaryIcon != null ? 0 : 8);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        if (SemWifiManager.MHSDBG) {
            Log.d("WifiApCustomPreference", "onPreferenceClick() - preference: " + preference);
        }
        if (this.mSmartWhiteList != null) {
            Log.i("WifiApCustomPreference", "onPreferenceClick() - mSmartWhiteList is clicked");
            this.mListener.onPreferenceClicked(this.mSmartWhiteList);
            return true;
        }
        if (this.mBleScanAllowedDevice != null) {
            Log.i(
                    "WifiApCustomPreference",
                    "onPreferenceClick() - mBleScanAllowedDevice is clicked");
            this.mListener.onPreferenceClicked(this.mBleScanAllowedDevice);
            return true;
        }
        Log.i(
                "WifiApCustomPreference",
                "onPreferenceClick() - other preference clicked is clicked");
        this.mListener.onPreferenceClicked(preference);
        return true;
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        Log.i("WifiApCustomPreference", "setEnabled() - Triggered");
        super.setEnabled(z);
    }

    public WifiApCustomPreference(
            Context context,
            SemWifiApSmartWhiteList.SmartWhiteList smartWhiteList,
            AutoHotspotCustomPreferenceListener autoHotspotCustomPreferenceListener) {
        super(context);
        this.mSecondaryIconClickListener = new AnonymousClass1();
        Log.i(
                "WifiApCustomPreference",
                "WifiApCustomPreference() - SmartWhiteList device received");
        this.mSmartWhiteList = smartWhiteList;
        this.mBleScanAllowedDevice = null;
        this.mListener = autoHotspotCustomPreferenceListener;
        setLayoutResource(R.layout.sec_wifi_ap_custom_preference_small);
        setKey(smartWhiteList.getMac());
        setTitle(smartWhiteList.getName());
        if (smartWhiteList.getDeviceType() == 1) {
            setIcon(R.drawable.ic_wifi_ap_tablet);
        } else {
            setIcon(R.drawable.ic_wifi_ap_mobile);
        }
        Drawable drawable = context.getDrawable(R.drawable.sec_wifi_ap_hotspot_list_ic_minus);
        if ((drawable == null && this.mSecondaryIcon != null)
                || (drawable != null && this.mSecondaryIcon != drawable)) {
            this.mSecondaryIcon = drawable;
            this.mSecondaryIconResId = 0;
            notifyChanged();
        }
        this.mSecondaryIconResId = R.drawable.sec_wifi_ap_hotspot_list_ic_minus;
        setOnPreferenceClickListener(this);
        if (SemWifiManager.MHSDBG) {
            Log.d(
                    "WifiApCustomPreference",
                    "WifiApCustomPreference() - SmartWhiteList Preference added: "
                            + smartWhiteList.getName()
                            + ", mac:"
                            + smartWhiteList.getMac());
        }
    }
}
