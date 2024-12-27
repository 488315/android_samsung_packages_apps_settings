package com.samsung.android.settings.deviceinfo.legalinfo;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SamsungEula extends SettingsPreferenceFragment {
    public Context mContext;
    public TextView mLegalTextView;
    public ProgressBar mLoadingProgress;
    public String mLegalText = ApnSettings.MVNO_NONE;
    public boolean mIsShowAAOnly = false;
    public final Handler mHandlerLoading =
            new Handler(Looper.getMainLooper(), new AnonymousClass1());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.legalinfo.SamsungEula$1, reason: invalid class name */
    public final class AnonymousClass1 implements Handler.Callback {
        public AnonymousClass1() {}

        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            ThreadUtils.postOnMainThread(new SamsungEula$$ExternalSyntheticLambda0(1, this));
            return true;
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4808;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        this.mLegalTextView.setText(ApnSettings.MVNO_NONE);
        this.mLoadingProgress.setVisibility(0);
        super.onConfigurationChanged(configuration);
        this.mHandlerLoading.sendEmptyMessage(0);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("SamsungEula", "Eula onCreate");
        this.mContext = getContext();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mIsShowAAOnly = arguments.getBoolean("ShowAAOnly", false);
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("mIsShowAAOnly : "), this.mIsShowAAOnly, "SamsungEula");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.sec_eula, (ViewGroup) null);
        View findViewById = inflate.findViewById(R.id.container);
        if (findViewById != null) {
            findViewById.semSetRoundedCorners(15);
            findViewById.semSetRoundedCornerColor(
                    15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        this.mLegalTextView = (TextView) inflate.findViewById(R.id.legal_text);
        this.mLoadingProgress = (ProgressBar) inflate.findViewById(R.id.loading_progress);
        Log.d("SamsungEula", "URI_EULA_GET as Latest  : " + Rune.isUSA());
        ThreadUtils.postOnBackgroundThread(new SamsungEula$$ExternalSyntheticLambda0(0, this));
        return inflate;
    }
}
