package com.samsung.android.settings.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SecTipLinkView extends LinearLayout {
    public Context mContext;
    public View mParentView;
    public LinearLayout mTipContainer;

    public final void pushLinkData(
            final SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData) {
        TextView textView;
        if (settingsPreferenceFragmentLinkData.intent == null) {
            textView = new TextView(new ContextThemeWrapper(this.mContext, R.style.sec_tip_desc));
        } else {
            textView = new TextView(new ContextThemeWrapper(this.mContext, R.style.sec_tip_link));
            textView.setPaintFlags(textView.getPaintFlags() | 8);
            textView.setFocusable(true);
            textView.setBackgroundResource(R.drawable.sec_widget_tip_link_item_background);
            textView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.widget.SecTipLinkView.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            SecTipLinkView secTipLinkView = SecTipLinkView.this;
                            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData2 =
                                    settingsPreferenceFragmentLinkData;
                            secTipLinkView.getClass();
                            if (settingsPreferenceFragmentLinkData2.callerMetric > 0
                                    && !TextUtils.isEmpty(
                                            settingsPreferenceFragmentLinkData2.flowId)) {
                                LoggingHelper.insertEventLogging(
                                        settingsPreferenceFragmentLinkData2.callerMetric,
                                        settingsPreferenceFragmentLinkData2.flowId);
                            }
                            String str = settingsPreferenceFragmentLinkData2.runType;
                            if (str != null && str.equals("Broadcast")) {
                                secTipLinkView.mContext.sendBroadcast(
                                        settingsPreferenceFragmentLinkData2.intent);
                                return;
                            }
                            if (settingsPreferenceFragmentLinkData2.fragment == null) {
                                if (settingsPreferenceFragmentLinkData2.intent != null) {
                                    if (settingsPreferenceFragmentLinkData2.callerMetric > 0
                                            && !TextUtils.isEmpty(
                                                    settingsPreferenceFragmentLinkData2.flowId)) {
                                        settingsPreferenceFragmentLinkData2.intent.putExtra(
                                                "callerMetric",
                                                settingsPreferenceFragmentLinkData2.callerMetric);
                                        settingsPreferenceFragmentLinkData2.intent.putExtra(
                                                "flowId",
                                                settingsPreferenceFragmentLinkData2.flowId);
                                    }
                                    if (Utils.isIntentAvailable(
                                            secTipLinkView.mContext,
                                            settingsPreferenceFragmentLinkData2.intent)) {
                                        secTipLinkView.mContext.startActivity(
                                                settingsPreferenceFragmentLinkData2.intent);
                                        return;
                                    } else {
                                        Log.d("SecTipLinkView", "intent is not available");
                                        return;
                                    }
                                }
                                return;
                            }
                            int i = settingsPreferenceFragmentLinkData2.linkedTitleRes;
                            if (i == 0) {
                                i = settingsPreferenceFragmentLinkData2.titleRes;
                            }
                            SubSettingLauncher subSettingLauncher =
                                    new SubSettingLauncher(secTipLinkView.mContext);
                            String str2 = settingsPreferenceFragmentLinkData2.fragment;
                            SubSettingLauncher.LaunchRequest launchRequest =
                                    subSettingLauncher.mLaunchRequest;
                            launchRequest.mDestinationName = str2;
                            subSettingLauncher.setTitleRes(i, null);
                            launchRequest.mArguments = settingsPreferenceFragmentLinkData2.extras;
                            int i2 = settingsPreferenceFragmentLinkData2.callerMetric;
                            if (i2 <= 0) {
                                i2 = 0;
                            }
                            launchRequest.mSourceMetricsCategory = i2;
                            subSettingLauncher.launch();
                        }
                    });
        }
        textView.setText(this.mContext.getString(settingsPreferenceFragmentLinkData.titleRes));
        this.mTipContainer.addView(textView, new LinearLayout.LayoutParams(-2, -2));
    }
}
