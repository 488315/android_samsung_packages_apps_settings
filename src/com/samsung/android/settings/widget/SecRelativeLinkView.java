package com.samsung.android.settings.widget;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.SettingsApplication;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.homepage.SettingsHomepageActivity;

import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SecRelativeLinkView extends LinearLayout {
    public final Context mContext;
    public long mLastClickTime;
    public final LinearLayout mLinkContainer;
    public final View mParentView;
    public SettingsPreferenceFragment mTargetFragment;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.widget.SecRelativeLinkView$1, reason: invalid class name */
    public final class AnonymousClass1 implements View.OnClickListener {
        public final /* synthetic */ SettingsPreferenceFragmentLinkData val$linkData;
        public final /* synthetic */ TextView val$tv;

        public AnonymousClass1(
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData,
                TextView textView) {
            this.val$linkData = settingsPreferenceFragmentLinkData;
            this.val$tv = textView;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            SettingsPreferenceFragment settingsPreferenceFragment =
                    SecRelativeLinkView.this.mTargetFragment;
            if (settingsPreferenceFragment == null || settingsPreferenceFragment.isResumed()) {
                long uptimeMillis = SystemClock.uptimeMillis();
                SecRelativeLinkView secRelativeLinkView = SecRelativeLinkView.this;
                long j = uptimeMillis - secRelativeLinkView.mLastClickTime;
                final SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                        this.val$linkData;
                if (j > settingsPreferenceFragmentLinkData.clickInterval) {
                    secRelativeLinkView.mLastClickTime = uptimeMillis;
                    this.val$tv.post(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.widget.SecRelativeLinkView$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SecRelativeLinkView.AnonymousClass1 anonymousClass1 =
                                            SecRelativeLinkView.AnonymousClass1.this;
                                    SecRelativeLinkView.this.startFooterViewLink(
                                            settingsPreferenceFragmentLinkData);
                                }
                            });
                } else {
                    Log.i("RelativeLinkView", "Ignore click : " + ((Object) this.val$tv.getText()));
                }
            }
        }
    }

    public SecRelativeLinkView(Context context) {
        super(context, null, 0);
        this.mContext = context;
        this.mParentView =
                LayoutInflater.from(context)
                        .inflate(R.layout.sec_widget_relative_link_footerview, this);
        this.mParentView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.mLinkContainer = (LinearLayout) this.mParentView.findViewById(R.id.link_container);
    }

    public final void create(Object obj) {
        LinearLayout linearLayout = this.mLinkContainer;
        if (linearLayout != null && linearLayout.getChildCount() <= 0) {
            Log.d("RelativeLinkView", "The current screen doesn't have link data.");
            return;
        }
        if (KnoxUtils.isApplicationRestricted(
                this.mContext, "Settings_Relative_Link_View", "hide")) {
            return;
        }
        if (obj instanceof SettingsPreferenceFragment) {
            SettingsPreferenceFragment settingsPreferenceFragment =
                    (SettingsPreferenceFragment) obj;
            settingsPreferenceFragment.setFooterView(this.mParentView, true);
            this.mTargetFragment = settingsPreferenceFragment;
        } else {
            Log.d("RelativeLinkView", "Failed to attach relative view. " + obj.getClass());
        }
    }

    public final View pushLinkData(
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData) {
        if (settingsPreferenceFragmentLinkData == null) {
            return null;
        }
        TextView textView =
                new TextView(
                        new ContextThemeWrapper(this.mContext, R.style.sec_relative_link_link));
        SecPreferenceUtils.applyTitleLargerTextIfNeeded(textView);
        SecPreferenceUtils.applyTitleLargerTextIfNeeded(
                (TextView) this.mParentView.findViewById(R.id.link_title));
        textView.setFocusable(true);
        textView.setBackgroundResource(R.drawable.sec_widget_relative_link_item_background);
        textView.setText(this.mContext.getString(settingsPreferenceFragmentLinkData.titleRes));
        textView.setOnClickListener(
                new AnonymousClass1(settingsPreferenceFragmentLinkData, textView));
        this.mLinkContainer.addView(textView, new LinearLayout.LayoutParams(-2, -2));
        return textView;
    }

    public final void startFooterViewLink(
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData) {
        SettingsHomepageActivity homeActivity;
        if (settingsPreferenceFragmentLinkData.callerMetric > 0
                && !TextUtils.isEmpty(settingsPreferenceFragmentLinkData.flowId)) {
            LoggingHelper.insertEventLogging(
                    settingsPreferenceFragmentLinkData.callerMetric,
                    settingsPreferenceFragmentLinkData.flowId);
        }
        try {
            if (ActivityEmbeddingUtils.isEmbeddingActivityEnabled(this.mContext)
                    && !TextUtils.isEmpty(settingsPreferenceFragmentLinkData.topLevelKey)
                    && (homeActivity =
                                    ((SettingsApplication) this.mContext.getApplicationContext())
                                            .getHomeActivity())
                            != null) {
                Log.i(
                        "RelativeLinkView",
                        "setHighlightMenuKey : " + settingsPreferenceFragmentLinkData.topLevelKey);
                homeActivity.mMainFragment.setHighlightMenuKey(
                        settingsPreferenceFragmentLinkData.topLevelKey);
            }
        } catch (NullPointerException e) {
            Log.e("RelativeLinkView", e.getMessage());
        }
        String str = settingsPreferenceFragmentLinkData.runType;
        if (str != null && str.equals("Broadcast")) {
            this.mContext.sendBroadcast(settingsPreferenceFragmentLinkData.intent);
            return;
        }
        if (settingsPreferenceFragmentLinkData.fragment != null) {
            int i = settingsPreferenceFragmentLinkData.linkedTitleRes;
            if (i == 0) {
                i = settingsPreferenceFragmentLinkData.titleRes;
            }
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
            String str2 = settingsPreferenceFragmentLinkData.fragment;
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = str2;
            subSettingLauncher.setTitleRes(i, null);
            launchRequest.mArguments = settingsPreferenceFragmentLinkData.extras;
            int i2 = settingsPreferenceFragmentLinkData.callerMetric;
            launchRequest.mSourceMetricsCategory = i2 > 0 ? i2 : 0;
            subSettingLauncher.launch();
            return;
        }
        if (settingsPreferenceFragmentLinkData.intent != null) {
            if (settingsPreferenceFragmentLinkData.callerMetric > 0
                    && !TextUtils.isEmpty(settingsPreferenceFragmentLinkData.flowId)) {
                settingsPreferenceFragmentLinkData.intent.putExtra(
                        "callerMetric", settingsPreferenceFragmentLinkData.callerMetric);
                settingsPreferenceFragmentLinkData.intent.putExtra(
                        "flowId", settingsPreferenceFragmentLinkData.flowId);
            }
            if (!Utils.isIntentAvailable(
                    this.mContext, settingsPreferenceFragmentLinkData.intent)) {
                Log.d("RelativeLinkView", "intent is not available");
                return;
            }
            Context context = this.mContext;
            Intent intent = settingsPreferenceFragmentLinkData.intent;
            context.startActivityForResult(
                    null, intent, Utils.isLaunchModeSingleInstance(context, intent) ? -1 : 0, null);
        }
    }
}
