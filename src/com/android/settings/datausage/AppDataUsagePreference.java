package com.android.settings.datausage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.AppItem;
import com.android.settingslib.net.UidDetail;
import com.android.settingslib.net.UidDetailProvider;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.AppPreference;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.datausage.DataUsageUtilsCHN;

import java.text.NumberFormat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppDataUsagePreference extends AppPreference {
    public UidDetail mDetail;
    public final AppItem mItem;
    public final int mPercent;

    public AppDataUsagePreference(
            Context context, AppItem appItem, int i, final UidDetailProvider uidDetailProvider) {
        super(context);
        setLayoutResource(R.layout.sec_preference_app_icon);
        setKey("app_data_usage_" + appItem.key);
        this.mItem = appItem;
        this.mPercent = i;
        if (appItem.restricted && appItem.total <= 0) {
            setSummary(R.string.data_usage_app_restricted);
        } else if (Rune.SUPPORT_SMARTMANAGER_CN) {
            setSummary(DataUsageUtilsCHN.formatDataUsage(context, appItem.total));
        } else {
            setSummary(DataUsageUtils.formatDataUsage(context, appItem.total));
        }
        UidDetail uidDetail = uidDetailProvider.getUidDetail(appItem.key, false);
        this.mDetail = uidDetail;
        if (uidDetail == null) {
            setTitle(R.string.summary_placeholder);
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                                     // com.android.settings.datausage.AppDataUsagePreference$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            final AppDataUsagePreference appDataUsagePreference =
                                    AppDataUsagePreference.this;
                            appDataUsagePreference.mDetail =
                                    uidDetailProvider.getUidDetail(
                                            appDataUsagePreference.mItem.key, true);
                            ThreadUtils.postOnMainThread(
                                    new Runnable() { // from class:
                                                     // com.android.settings.datausage.AppDataUsagePreference$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            AppDataUsagePreference appDataUsagePreference2 =
                                                    AppDataUsagePreference.this;
                                            UidDetail uidDetail2 = appDataUsagePreference2.mDetail;
                                            if (uidDetail2 != null) {
                                                appDataUsagePreference2.setIcon(uidDetail2.icon);
                                                appDataUsagePreference2.setTitle(
                                                        appDataUsagePreference2.mDetail.label);
                                            } else {
                                                appDataUsagePreference2.setIcon((Drawable) null);
                                                appDataUsagePreference2.setTitle(
                                                        (CharSequence) null);
                                            }
                                        }
                                    });
                        }
                    });
        } else if (uidDetail != null) {
            setIcon(uidDetail.icon);
            setTitle(this.mDetail.label);
        } else {
            setIcon((Drawable) null);
            setTitle((CharSequence) null);
        }
        Log.i("AppDataUsagePreference", appItem.key + " : " + appItem.total + " : " + i);
    }

    @Override // com.android.settingslib.widget.AppPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ProgressBar progressBar =
                (ProgressBar) preferenceViewHolder.findViewById(android.R.id.progress);
        AppItem appItem = this.mItem;
        if (!appItem.restricted || appItem.total > 0) {
            progressBar.setVisibility(0);
        } else {
            progressBar.setVisibility(8);
        }
        progressBar.setProgress(this.mPercent);
        progressBar.setContentDescription(
                NumberFormat.getPercentInstance().format(this.mPercent / 100.0d));
    }
}
