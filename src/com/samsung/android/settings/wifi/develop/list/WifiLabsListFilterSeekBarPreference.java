package com.samsung.android.settings.wifi.develop.list;

import android.content.Context;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.AttributeSet;
import android.widget.SeekBar;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.wifitrackerlib.SemWifiUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiLabsListFilterSeekBarPreference extends Preference {
    public final Context mContext;
    public SeekBar mSeekBar;
    public int mSelectedLevel;

    public WifiLabsListFilterSeekBarPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.sec_wifi_labs_list_filter_seekbar);
        this.mContext = context;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mSeekBar == null) {
            SeekBar seekBar = (SeekBar) preferenceViewHolder.findViewById(R.id.filter_seek_bar);
            this.mSeekBar = seekBar;
            seekBar.setMax(2);
            Context context = this.mContext;
            SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
            final int i =
                    Settings.Global.getInt(
                            context.getContentResolver(), "sec_wifi_developer_rssi_level", 1);
            this.mSeekBar.setProgress(i);
            this.mSeekBar.setOnSeekBarChangeListener(
                    new SeekBar
                            .OnSeekBarChangeListener() { // from class:
                                                         // com.samsung.android.settings.wifi.develop.list.WifiLabsListFilterSeekBarPreference.1
                        @Override // android.widget.SeekBar.OnSeekBarChangeListener
                        public final void onProgressChanged(SeekBar seekBar2, int i2, boolean z) {
                            WifiLabsListFilterSeekBarPreference.this.mSelectedLevel = i2;
                        }

                        @Override // android.widget.SeekBar.OnSeekBarChangeListener
                        public final void onStartTrackingTouch(SeekBar seekBar2) {
                            WifiLabsListFilterSeekBarPreference.this.mSelectedLevel = i;
                        }

                        @Override // android.widget.SeekBar.OnSeekBarChangeListener
                        public final void onStopTrackingTouch(SeekBar seekBar2) {
                            WifiLabsListFilterSeekBarPreference
                                    wifiLabsListFilterSeekBarPreference =
                                            WifiLabsListFilterSeekBarPreference.this;
                            Context context2 = wifiLabsListFilterSeekBarPreference.mContext;
                            int i2 = wifiLabsListFilterSeekBarPreference.mSelectedLevel;
                            SubscriptionManager subscriptionManager2 =
                                    SemWifiUtils.mSubscriptionManager;
                            Settings.Global.putInt(
                                    context2.getContentResolver(),
                                    "sec_wifi_developer_rssi_level",
                                    i2);
                        }
                    });
        }
    }

    public WifiLabsListFilterSeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiLabsListFilterSeekBarPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiLabsListFilterSeekBarPreference(Context context) {
        this(context, null);
    }
}
