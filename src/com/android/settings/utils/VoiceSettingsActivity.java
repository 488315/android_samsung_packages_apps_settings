package com.android.settings.utils;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class VoiceSettingsActivity extends Activity {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.utils.VoiceSettingsActivity$1, reason: invalid class name */
    public final class AnonymousClass1 extends VoiceInteractor.CompleteVoiceRequest {
        public AnonymousClass1(CharSequence charSequence) {
            super(charSequence, (Bundle) null);
        }

        @Override // android.app.VoiceInteractor.CompleteVoiceRequest
        public final void onCompleteResult(Bundle bundle) {
            VoiceSettingsActivity.this.finish();
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!isVoiceInteractionRoot()) {
            Log.v("VoiceSettingsActivity", "Cannot modify settings without voice interaction");
            finish();
        } else if (onVoiceSettingInteraction(getIntent())) {
            finish();
        }
    }

    public abstract boolean onVoiceSettingInteraction(Intent intent);
}
