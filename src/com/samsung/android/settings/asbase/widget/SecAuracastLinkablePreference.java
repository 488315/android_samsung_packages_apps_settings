package com.samsung.android.settings.asbase.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAuracastLinkablePreference extends SecAudioLinkablePreference {
    public SecAuracastLinkablePreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference
    public final Bundle getBundle() {
        return AbsAdapter$1$$ExternalSyntheticOutline0.m(
                ":settings:fragment_args_key", "aura_cast_key");
    }

    @Override // com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference
    public final String getDestinationName() {
        return SecBluetoothLeBroadcastSourceSetting.class.getName();
    }

    @Override // com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference
    public final int getGuideTextResource() {
        return R.string.sec_auracast_enabled_feedback_link;
    }

    @Override // com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference
    public final int getTitleResource$1() {
        return R.string.sec_auracast_enabled_feedback_link;
    }

    @Override // com.samsung.android.settings.asbase.widget.SecAudioLinkablePreference
    public final int getTopMargin() {
        return 0;
    }

    public SecAuracastLinkablePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecAuracastLinkablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecAuracastLinkablePreference(Context context) {
        this(context, null);
    }
}
