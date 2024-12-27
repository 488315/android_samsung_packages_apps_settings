package com.samsung.android.settings.uwb;

import android.content.Context;
import android.os.Handler;
import android.os.SystemProperties;

import androidx.preference.TwoStatePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UwbSelectablePreference {
    public final AnonymousClass1 mClick5OverEventRunnable;
    public final Context mContext;
    public final AnonymousClass1 mEnableDisableEventRunnable;
    public boolean mLabsEnabled;
    public final UwbPreferenceController mMainController;
    public TwoStatePreference mSelectablePreference;
    public final Handler mClickEventHandler = new Handler();
    public int mClickCount = 0;
    public boolean mIsUwbEnabled = false;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.uwb.UwbSelectablePreference$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.uwb.UwbSelectablePreference$1] */
    public UwbSelectablePreference(
            Context context, UwbPreferenceController uwbPreferenceController) {
        this.mLabsEnabled = false;
        final int i = 0;
        this.mEnableDisableEventRunnable =
                new Runnable(
                        this) { // from class:
                                // com.samsung.android.settings.uwb.UwbSelectablePreference.1
                    public final /* synthetic */ UwbSelectablePreference this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                UwbSelectablePreference uwbSelectablePreference = this.this$0;
                                uwbSelectablePreference.mMainController.setChecked(
                                        uwbSelectablePreference.mIsUwbEnabled);
                                this.this$0.mClickCount = 0;
                                break;
                            default:
                                this.this$0.mClickCount = 0;
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mClick5OverEventRunnable =
                new Runnable(
                        this) { // from class:
                                // com.samsung.android.settings.uwb.UwbSelectablePreference.1
                    public final /* synthetic */ UwbSelectablePreference this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                UwbSelectablePreference uwbSelectablePreference = this.this$0;
                                uwbSelectablePreference.mMainController.setChecked(
                                        uwbSelectablePreference.mIsUwbEnabled);
                                this.this$0.mClickCount = 0;
                                break;
                            default:
                                this.this$0.mClickCount = 0;
                                break;
                        }
                    }
                };
        this.mContext = context;
        this.mMainController = uwbPreferenceController;
        this.mLabsEnabled = SystemProperties.get("uwb.labs.enable").equals("true");
    }

    public final void setChecked(boolean z) {
        this.mSelectablePreference.setChecked(z);
    }

    public final void setEnabled(boolean z) {
        this.mSelectablePreference.setEnabled(z);
    }
}
