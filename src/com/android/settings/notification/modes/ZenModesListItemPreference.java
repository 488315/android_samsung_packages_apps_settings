package com.android.settings.notification.modes;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.Utils;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModesListItemPreference extends RestrictedPreference {
    public final Context mContext;
    public ZenMode mZenMode;

    public ZenModesListItemPreference(Context context, ZenMode zenMode) {
        super(context);
        this.mContext = context;
        setZenMode(zenMode);
        setKey(zenMode.mId);
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        ZenSubSettingLauncher.forMode(this.mContext, this.mZenMode.mId).launch();
    }

    public final void setZenMode(ZenMode zenMode) {
        this.mZenMode = zenMode;
        setTitle(zenMode.mRule.getName());
        setSummary(this.mZenMode.mRule.getTriggerDescription());
        this.mIconSize = 2;
        ListenableFuture icon = this.mZenMode.getIcon(this.mContext, IconLoader.getInstance());
        Consumer consumer =
                new Consumer() { // from class:
                                 // com.android.settings.notification.modes.ZenModesListItemPreference$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ZenModesListItemPreference zenModesListItemPreference =
                                ZenModesListItemPreference.this;
                        Context context = zenModesListItemPreference.mContext;
                        Drawable mutate = ((Drawable) obj).mutate();
                        mutate.setTintList(Utils.getColorAttr(context, R.attr.colorControlNormal));
                        zenModesListItemPreference.setIcon(mutate);
                    }
                };
        icon.addListener(
                new Futures.CallbackListener(icon, new FutureUtil$1(consumer, new Object[0])),
                this.mContext.getMainExecutor());
    }
}
