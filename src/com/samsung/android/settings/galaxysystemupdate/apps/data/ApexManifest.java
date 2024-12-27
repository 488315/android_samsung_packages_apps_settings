package com.samsung.android.settings.galaxysystemupdate.apps.data;

import com.samsung.android.util.SemLog;

import java.util.Queue;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ApexManifest {
    public Queue queue_;
    public long version_;

    public final long getVersion() {
        int i;
        long j = this.version_;
        long j2 = -1;
        if (j != -1) {
            return j;
        }
        while (!this.queue_.isEmpty()) {
            int intValue = ((Integer) this.queue_.poll()).intValue();
            int i2 = intValue >> 3;
            int i3 = intValue & 7;
            int i4 = 0;
            if (i2 == 2) {
                if (i3 != 0) {
                    SemLog.e("ApexManifest", "Field 2 is not varint type");
                } else {
                    long j3 = 0;
                    while (!this.queue_.isEmpty()) {
                        j3 += (r2 & 127) << i4;
                        i4 += 7;
                        if ((((Integer) this.queue_.poll()).intValue() & 128) == 0) {
                            break;
                        }
                    }
                    j2 = j3;
                }
                this.version_ = j2;
                return j2;
            }
            if (i3 == 0) {
                while ((((Integer) this.queue_.poll()).intValue() & 128) != 128) {}
                i = 0;
            } else if (i3 == 1) {
                i = 8;
            } else if (i3 == 2) {
                i = ((Integer) this.queue_.poll()).intValue();
            } else if (i3 != 5) {
                SemLog.e("ApexManifest", "Wire type is wrong");
            } else {
                i = 4;
            }
            while (i4 < i) {
                this.queue_.poll();
                i4++;
            }
        }
        return -1L;
    }
}
