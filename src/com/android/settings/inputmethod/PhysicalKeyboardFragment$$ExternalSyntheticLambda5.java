package com.android.settings.inputmethod;

import android.content.Context;
import android.net.Uri;

import com.android.settingslib.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class PhysicalKeyboardFragment$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PhysicalKeyboardFragment f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PhysicalKeyboardFragment$$ExternalSyntheticLambda5(
            PhysicalKeyboardFragment physicalKeyboardFragment, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = physicalKeyboardFragment;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PhysicalKeyboardFragment physicalKeyboardFragment = this.f$0;
                Context context = (Context) this.f$1;
                Uri uri = PhysicalKeyboardFragment.sVirtualKeyboardSettingsUri;
                physicalKeyboardFragment.getClass();
                List hardKeyboards = PhysicalKeyboardFragment.getHardKeyboards(context);
                if (!((ArrayList) hardKeyboards).isEmpty()) {
                    ThreadUtils.postOnMainThread(
                            new PhysicalKeyboardFragment$$ExternalSyntheticLambda5(
                                    physicalKeyboardFragment, hardKeyboards, 1));
                    break;
                } else {
                    physicalKeyboardFragment.getActivity().finish();
                    break;
                }
            default:
                PhysicalKeyboardFragment.$r8$lambda$J4XxqnIbWDLeFXC5A2o7uVF1_0s(
                        this.f$0, (List) this.f$1);
                break;
        }
    }
}
