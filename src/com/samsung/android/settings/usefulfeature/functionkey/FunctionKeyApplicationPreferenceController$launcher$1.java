package com.samsung.android.settings.usefulfeature.functionkey;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

import com.samsung.android.settings.widget.SecRadioButtonGearPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FunctionKeyApplicationPreferenceController$launcher$1
        implements SecRadioButtonGearPreference.OnGearClickListener, ActivityResultCallback {
    public final /* synthetic */ FunctionKeyApplicationPreferenceController this$0;

    @Override // androidx.activity.result.ActivityResultCallback
    public void onActivityResult(Object obj) {
        ActivityResult activityResult = (ActivityResult) obj;
        this.this$0.onActivityResult(activityResult.mResultCode, activityResult.mData);
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreference.OnGearClickListener
    public void onGearClick() {
        this.this$0.launch();
    }
}
