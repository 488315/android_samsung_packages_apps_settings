package com.samsung.android.settings.bixby.target.actions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.samsung.android.settings.bixby.utils.BixbyUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class Action {
    public final Bundle mBundle;
    public final Context mContext;

    public Action(Context context, Bundle bundle) {
        this.mContext = context;
        this.mBundle = bundle;
    }

    public static Bundle createResult(String str) {
        return AbsAdapter$1$$ExternalSyntheticOutline0.m("result", str);
    }

    public Bundle doChangeAction() {
        return createResult("fail");
    }

    public Bundle doConnectAction() {
        return createResult("fail");
    }

    public Bundle doDisableAction() {
        return createResult("fail");
    }

    public Bundle doGetAction() {
        return createResult("fail");
    }

    public Bundle doGetEntryListAction() {
        return createResult("fail");
    }

    public Bundle doGotoAction() {
        return createResult("fail");
    }

    public Bundle doSetAction() {
        return createResult("fail");
    }

    public Bundle doSupportAction() {
        return createResult("true");
    }

    public Bundle doTryToConnectAction() {
        return createResult("fail");
    }

    public Bundle doTryToDisconnectAction() {
        return createResult("fail");
    }

    public final Integer getTaskId() {
        return Integer.valueOf(this.mBundle.getInt("targetTaskId"));
    }

    public final String getValue() {
        return this.mBundle.getString("value");
    }

    public final void launchSettings(Intent intent, Bundle bundle) {
        if (BixbyUtils.isLargeSubDisplayScreen() && this.mBundle.getBoolean("isFolded", false)) {
            BixbyUtils.setPendingIntentAfterUnlock(this.mContext, intent);
        } else {
            this.mContext.startActivity(intent, bundle);
        }
    }
}
