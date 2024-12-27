package com.android.settings.connecteddevice.audiosharing.audiostreams;

import com.google.common.base.Platform;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamsHelper$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                if (((Long) obj).longValue() != 0) {}
                break;
        }
        return !Platform.stringIsNullOrEmpty((String) obj);
    }
}
