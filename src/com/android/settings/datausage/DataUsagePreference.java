package com.android.settings.datausage;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.util.AttributeSet;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;

import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.net.DataUsageController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DataUsagePreference extends Preference implements TemplatePreference {
    public int mSubId;
    public NetworkTemplate mTemplate;
    public final int mTitleRes;

    public DataUsagePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet,
                        new int[] {R.attr.title},
                        TypedArrayUtils.getAttr(
                                context,
                                com.android.settings.R.attr.preferenceStyle,
                                R.attr.preferenceStyle),
                        0);
        this.mTitleRes = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
    }

    public DataUsageController getDataUsageController() {
        return new DataUsageController(getContext());
    }

    @Override // androidx.preference.Preference
    public final Intent getIntent() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("network_template", this.mTemplate);
        bundle.putInt("sub_id", this.mSubId);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mArguments = bundle;
        launchRequest.mDestinationName = DataUsageList.class.getName();
        launchRequest.mSourceMetricsCategory = 0;
        if (this.mTemplate.getMatchRule() == 1) {
            subSettingLauncher.setTitleRes(
                    com.android.settings.R.string.app_cellular_data_usage, null);
        } else {
            subSettingLauncher.setTitleRes(this.mTitleRes, null);
        }
        return subSettingLauncher.toIntent();
    }

    @Override // com.android.settings.datausage.TemplatePreference
    public final void setTemplate(NetworkTemplate networkTemplate, int i) {
        this.mTemplate = networkTemplate;
        this.mSubId = i;
        DataUsageController dataUsageController = getDataUsageController();
        if (this.mTemplate.getMatchRule() == 1) {
            setTitle(com.android.settings.R.string.app_cellular_data_usage);
        } else {
            DataUsageController.DataUsageInfo dataUsageInfo =
                    dataUsageController.getDataUsageInfo(this.mTemplate);
            setTitle(this.mTitleRes);
            setSummary(
                    getContext()
                            .getString(
                                    com.android.settings.R.string.data_usage_template,
                                    DataUsageUtils.formatDataUsage(
                                            getContext(), dataUsageInfo.usageLevel),
                                    dataUsageInfo.period));
        }
        dataUsageController.getClass();
        if (dataUsageController.getUsageLevel(networkTemplate, 0L, System.currentTimeMillis())
                > 0) {
            setIntent(getIntent());
        } else {
            setIntent(null);
            setEnabled(false);
        }
    }
}
