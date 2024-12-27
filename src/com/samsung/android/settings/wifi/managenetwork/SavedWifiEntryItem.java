package com.samsung.android.settings.wifi.managenetwork;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.WifiDevicePolicyManager;
import com.samsung.android.settings.wifi.details.WifiNetworkDetailsFragment;

import java.util.StringJoiner;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SavedWifiEntryItem extends LinearLayout {
    public CheckBox mCheckbox;
    public final Context mContext;
    public boolean mIsChecked;
    public boolean mIsRemovable;
    public SavedWifiEntrySettings mListener;
    public int mMode;
    public int mPosition;
    public SavedWifiEntry mSavedWifiEntry;
    public TextView mSsid;
    public TextView mSummary;
    public WifiEntry mWifiEntry;

    public SavedWifiEntryItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsRemovable = true;
        this.mContext = context;
    }

    public final String getDescription() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (this.mMode == 0) {
            stringJoiner
                    .add(
                            this.mSavedWifiEntry.mIsChecked
                                    ? this.mContext.getString(R.string.sec_wifi_checked_tss)
                                    : this.mContext.getString(R.string.sec_wifi_unchecked_tss))
                    .add(this.mContext.getString(R.string.sec_wifi_checkbox_tss));
        }
        stringJoiner.add(this.mSsid.getText());
        if (this.mSummary.getVisibility() == 0) {
            stringJoiner.add(this.mSummary.getText());
        }
        return stringJoiner.toString();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSsid = (TextView) findViewById(R.id.title);
        this.mSummary = (TextView) findViewById(R.id.summary);
        this.mCheckbox = (CheckBox) findViewById(R.id.checkbox);
    }

    public final void setChecked(boolean z) {
        boolean z2 = this.mIsRemovable && z;
        this.mCheckbox.setChecked(z2);
        this.mSavedWifiEntry.mIsChecked = z2;
        this.mIsChecked = z2;
        setContentDescription(getDescription());
    }

    public final void setSummary(boolean z) {
        if (z) {
            this.mWifiEntry.getSummary(true);
            this.mSummary.setText(this.mWifiEntry.getSummary(true));
            this.mSummary.setVisibility(0);
            this.mSsid.setTextColor(this.mContext.getColor(R.color.sec_highlight_text_color));
        } else {
            this.mSummary.setVisibility(8);
            this.mSsid.setTextColor(this.mContext.getColor(R.color.wifi_preference_title_color));
        }
        setContentDescription(getDescription());
    }

    public final void setWifiEntry(
            SavedWifiEntry savedWifiEntry, int i, SavedWifiEntrySettings savedWifiEntrySettings) {
        this.mSavedWifiEntry = savedWifiEntry;
        this.mWifiEntry = savedWifiEntry.mWifiEntry;
        this.mIsChecked = savedWifiEntry.mIsChecked;
        this.mIsRemovable = savedWifiEntry.isRemovableNetwork().booleanValue();
        this.mPosition = i;
        this.mListener = savedWifiEntrySettings;
        this.mSsid.setText(this.mWifiEntry.getSsid());
        this.mSummary.setVisibility(8);
        this.mCheckbox.setOnCheckedChangeListener(null);
        this.mCheckbox.setChecked(this.mIsChecked);
        setLongClickable(true);
        if (!this.mIsRemovable) {
            this.mCheckbox.setClickable(false);
        }
        if (this.mListener == null) {
            return;
        }
        this.mCheckbox.setOnCheckedChangeListener(
                new CompoundButton
                        .OnCheckedChangeListener() { // from class:
                                                     // com.samsung.android.settings.wifi.managenetwork.SavedWifiEntryItem$$ExternalSyntheticLambda0
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        SavedWifiEntryItem savedWifiEntryItem = SavedWifiEntryItem.this;
                        savedWifiEntryItem.setChecked(!savedWifiEntryItem.mIsChecked);
                        SavedWifiEntrySettings savedWifiEntrySettings2 =
                                savedWifiEntryItem.mListener;
                        savedWifiEntrySettings2.updateComponentsStatus(
                                savedWifiEntrySettings2.mMode);
                    }
                });
        setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.managenetwork.SavedWifiEntryItem$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SavedWifiEntryItem savedWifiEntryItem = SavedWifiEntryItem.this;
                        if (savedWifiEntryItem.mMode != 1) {
                            savedWifiEntryItem.setChecked(!savedWifiEntryItem.mIsChecked);
                            SavedWifiEntrySettings savedWifiEntrySettings2 =
                                    savedWifiEntryItem.mListener;
                            savedWifiEntrySettings2.updateComponentsStatus(
                                    savedWifiEntrySettings2.mMode);
                            return;
                        }
                        WifiEntry wifiEntry = savedWifiEntryItem.mWifiEntry;
                        SALogging.insertSALog(
                                wifiEntry.getConnectedState() == 2 ? 0L : 1L,
                                "WIFI_220",
                                "1266",
                                (String) null);
                        WifiConfiguration wifiConfiguration = wifiEntry.getWifiConfiguration();
                        if (!savedWifiEntryItem.mIsRemovable
                                && wifiEntry.getConnectedState() == 2) {
                            Context context = savedWifiEntryItem.mContext;
                            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                    context, RestrictedLockUtilsInternal.getDeviceOwner(context));
                            return;
                        }
                        if (WifiDevicePolicyManager.canModifyNetwork(
                                savedWifiEntryItem.mContext, wifiConfiguration)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("key_chosen_wifientry_key", wifiEntry.getKey());
                            bundle.putParcelable("key_chosen_wifientry_config", wifiConfiguration);
                            bundle.putBoolean("manage_network", true);
                            SubSettingLauncher subSettingLauncher =
                                    new SubSettingLauncher(savedWifiEntryItem.mContext);
                            String name = WifiNetworkDetailsFragment.class.getName();
                            SubSettingLauncher.LaunchRequest launchRequest =
                                    subSettingLauncher.mLaunchRequest;
                            launchRequest.mDestinationName = name;
                            launchRequest.mSourceMetricsCategory = 103;
                            launchRequest.mArguments = bundle;
                            subSettingLauncher.launch();
                        }
                    }
                });
        setOnLongClickListener(
                new View
                        .OnLongClickListener() { // from class:
                                                 // com.samsung.android.settings.wifi.managenetwork.SavedWifiEntryItem$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        SavedWifiEntryItem savedWifiEntryItem = SavedWifiEntryItem.this;
                        SavedWifiEntrySettings savedWifiEntrySettings2 =
                                savedWifiEntryItem.mListener;
                        int i2 = savedWifiEntryItem.mPosition;
                        if (!savedWifiEntrySettings2.mAdapter.isAutoSelectAllEnabled()) {
                            savedWifiEntrySettings2.mAdapter.setChecked(i2);
                        }
                        savedWifiEntrySettings2.setMode$2(0);
                        savedWifiEntrySettings2.mDragStartPosition = i2;
                        savedWifiEntrySettings2.mRecyclerView.mIsLongPressMultiSelection = true;
                        return true;
                    }
                });
    }
}
