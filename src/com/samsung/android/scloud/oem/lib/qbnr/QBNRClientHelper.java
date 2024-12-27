package com.samsung.android.scloud.oem.lib.qbnr;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.samsung.android.scloud.oem.lib.LOG;
import com.samsung.android.scloud.oem.lib.common.IClientHelper;
import com.samsung.android.scloud.oem.lib.common.IServiceHandler;
import com.sec.ims.configuration.DATA;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class QBNRClientHelper extends IClientHelper {
    public final ISCloudQBNRClient backupClient;
    public boolean mIsFinished;
    public boolean mIsSuccess;
    public long mProcNow;
    public long mProcTotal;
    public final Map serviceHandlerMap;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper$1, reason: invalid class name */
    public final class AnonymousClass1 implements IServiceHandler {
        @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
        public final Bundle handleServiceAction(
                Context context, Object obj, String str, Bundle bundle) {
            LOG.i("QBNRClientHelper", "[" + str + "] GET_CLIENT_INFO , " + str);
            ISCloudQBNRClient iSCloudQBNRClient = (ISCloudQBNRClient) obj;
            iSCloudQBNRClient.getClass();
            String label = iSCloudQBNRClient.getLabel(context);
            String description = iSCloudQBNRClient.getDescription(context);
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("support_backup", true);
            bundle2.putString("name", str);
            bundle2.putBoolean("is_enable_backup", true);
            bundle2.putString("label", label);
            bundle2.putString("description", description);
            StringBuilder m =
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            "[", str, "] GET_CLIENT_INFO, ", str, ", ");
            m.append(label);
            LOG.d("QBNRClientHelper", m.toString());
            return bundle2;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper$2, reason: invalid class name */
    public final class AnonymousClass2 implements IServiceHandler {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ QBNRClientHelper this$0;

        public /* synthetic */ AnonymousClass2(QBNRClientHelper qBNRClientHelper, int i) {
            this.$r8$classId = i;
            this.this$0 = qBNRClientHelper;
        }

        @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
        public final Bundle handleServiceAction(
                final Context context, final Object obj, final String str, Bundle bundle) {
            switch (this.$r8$classId) {
                case 0:
                    final Uri parse = Uri.parse(bundle.getString("observing_uri"));
                    final ParcelFileDescriptor parcelFileDescriptor =
                            (ParcelFileDescriptor) bundle.getParcelable("file");
                    QBNRClientHelper qBNRClientHelper = this.this$0;
                    qBNRClientHelper.mProcNow = 0L;
                    qBNRClientHelper.mProcTotal = 0L;
                    qBNRClientHelper.mIsFinished = false;
                    qBNRClientHelper.mIsSuccess = false;
                    final int i = 0;
                    new Thread(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper.2.1

                                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                        /* renamed from: com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper$2$1$1, reason: invalid class name and collision with other inner class name */
                                        public final class C00531 {
                                            public final /* synthetic */ int $r8$classId = 0;
                                            public final /* synthetic */ Object this$2;

                                            public C00531(AnonymousClass1 anonymousClass1) {
                                                this.this$2 = anonymousClass1;
                                            }

                                            public final void complete(boolean z) {
                                                switch (this.$r8$classId) {
                                                    case 0:
                                                        StringBuilder sb = new StringBuilder("[");
                                                        AnonymousClass1 anonymousClass1 =
                                                                (AnonymousClass1) this.this$2;
                                                        sb.append(str);
                                                        sb.append(
                                                                "] backup: complete: isSuccess: ");
                                                        sb.append(z);
                                                        LOG.i("QBNRClientHelper", sb.toString());
                                                        QBNRClientHelper qBNRClientHelper =
                                                                ((AnonymousClass2) this).this$0;
                                                        qBNRClientHelper.mIsFinished = true;
                                                        qBNRClientHelper.mIsSuccess = z;
                                                        context.getContentResolver()
                                                                .notifyChange(parse, null);
                                                        ParcelFileDescriptor parcelFileDescriptor =
                                                                parcelFileDescriptor;
                                                        if (parcelFileDescriptor != null) {
                                                            try {
                                                                parcelFileDescriptor.close();
                                                                break;
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                                return;
                                                            }
                                                        }
                                                        break;
                                                    default:
                                                        StringBuilder sb2 = new StringBuilder("[");
                                                        AnonymousClass1 anonymousClass12 =
                                                                (AnonymousClass1) this.this$2;
                                                        sb2.append(str);
                                                        sb2.append(
                                                                "] restore: complete: isSuccess: ");
                                                        sb2.append(z);
                                                        LOG.i("QBNRClientHelper", sb2.toString());
                                                        QBNRClientHelper qBNRClientHelper2 =
                                                                ((AnonymousClass2) this).this$0;
                                                        qBNRClientHelper2.mIsFinished = true;
                                                        qBNRClientHelper2.mIsSuccess = z;
                                                        Uri.Builder buildUpon = parse.buildUpon();
                                                        buildUpon.appendQueryParameter(
                                                                "is_success",
                                                                ((AnonymousClass2) this)
                                                                                .this$0
                                                                                .mIsSuccess
                                                                        ? "1"
                                                                        : DATA.DM_FIELD_INDEX
                                                                                .PCSCF_DOMAIN);
                                                        context.getContentResolver()
                                                                .notifyChange(
                                                                        buildUpon.build(), null);
                                                        ParcelFileDescriptor parcelFileDescriptor2 =
                                                                parcelFileDescriptor;
                                                        if (parcelFileDescriptor2 != null) {
                                                            try {
                                                                parcelFileDescriptor2.close();
                                                                break;
                                                            } catch (IOException e2) {
                                                                e2.printStackTrace();
                                                            }
                                                        }
                                                        break;
                                                }
                                            }

                                            public final void onProgress(long j, long j2) {
                                                switch (this.$r8$classId) {
                                                    case 0:
                                                        StringBuilder sb = new StringBuilder("[");
                                                        AnonymousClass1 anonymousClass1 =
                                                                (AnonymousClass1) this.this$2;
                                                        sb.append(str);
                                                        sb.append("] backup: onProgress: ");
                                                        sb.append(j);
                                                        sb.append(" / ");
                                                        sb.append(j2);
                                                        LOG.d("QBNRClientHelper", sb.toString());
                                                        QBNRClientHelper qBNRClientHelper =
                                                                ((AnonymousClass2) this).this$0;
                                                        qBNRClientHelper.mProcNow = j;
                                                        qBNRClientHelper.mProcTotal = j2;
                                                        context.getContentResolver()
                                                                .notifyChange(parse, null);
                                                        break;
                                                    default:
                                                        StringBuilder sb2 = new StringBuilder("[");
                                                        AnonymousClass1 anonymousClass12 =
                                                                (AnonymousClass1) this.this$2;
                                                        sb2.append(str);
                                                        sb2.append("] restore: onProgress: ");
                                                        sb2.append(j);
                                                        sb2.append(" / ");
                                                        sb2.append(j2);
                                                        LOG.d("QBNRClientHelper", sb2.toString());
                                                        QBNRClientHelper qBNRClientHelper2 =
                                                                ((AnonymousClass2) this).this$0;
                                                        qBNRClientHelper2.mProcNow = j;
                                                        qBNRClientHelper2.mProcTotal = j2;
                                                        break;
                                                }
                                            }

                                            public C00531(AnonymousClass1 anonymousClass1, byte b) {
                                                this.this$2 = anonymousClass1;
                                            }
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i) {
                                                case 0:
                                                    ((ISCloudQBNRClient) obj)
                                                            .backup(
                                                                    context,
                                                                    parcelFileDescriptor,
                                                                    new C00531(this));
                                                    break;
                                                default:
                                                    ((ISCloudQBNRClient) obj)
                                                            .restore(
                                                                    context,
                                                                    parcelFileDescriptor,
                                                                    new C00531(this, (byte) 0));
                                                    break;
                                            }
                                        }
                                    },
                                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                            "BACKUP_", str))
                            .start();
                    return null;
                case 1:
                    final Uri parse2 = Uri.parse(bundle.getString("observing_uri"));
                    final ParcelFileDescriptor parcelFileDescriptor2 =
                            (ParcelFileDescriptor) bundle.getParcelable("file");
                    QBNRClientHelper qBNRClientHelper2 = this.this$0;
                    qBNRClientHelper2.mProcNow = 0L;
                    qBNRClientHelper2.mProcTotal = 0L;
                    qBNRClientHelper2.mIsFinished = false;
                    qBNRClientHelper2.mIsSuccess = false;
                    final int i2 = 1;
                    new Thread(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper.2.1

                                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                        /* renamed from: com.samsung.android.scloud.oem.lib.qbnr.QBNRClientHelper$2$1$1, reason: invalid class name and collision with other inner class name */
                                        public final class C00531 {
                                            public final /* synthetic */ int $r8$classId = 0;
                                            public final /* synthetic */ Object this$2;

                                            public C00531(AnonymousClass1 anonymousClass1) {
                                                this.this$2 = anonymousClass1;
                                            }

                                            public final void complete(boolean z) {
                                                switch (this.$r8$classId) {
                                                    case 0:
                                                        StringBuilder sb = new StringBuilder("[");
                                                        AnonymousClass1 anonymousClass1 =
                                                                (AnonymousClass1) this.this$2;
                                                        sb.append(str);
                                                        sb.append(
                                                                "] backup: complete: isSuccess: ");
                                                        sb.append(z);
                                                        LOG.i("QBNRClientHelper", sb.toString());
                                                        QBNRClientHelper qBNRClientHelper =
                                                                ((AnonymousClass2) this).this$0;
                                                        qBNRClientHelper.mIsFinished = true;
                                                        qBNRClientHelper.mIsSuccess = z;
                                                        context.getContentResolver()
                                                                .notifyChange(parse2, null);
                                                        ParcelFileDescriptor parcelFileDescriptor =
                                                                parcelFileDescriptor2;
                                                        if (parcelFileDescriptor != null) {
                                                            try {
                                                                parcelFileDescriptor.close();
                                                                break;
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                                return;
                                                            }
                                                        }
                                                        break;
                                                    default:
                                                        StringBuilder sb2 = new StringBuilder("[");
                                                        AnonymousClass1 anonymousClass12 =
                                                                (AnonymousClass1) this.this$2;
                                                        sb2.append(str);
                                                        sb2.append(
                                                                "] restore: complete: isSuccess: ");
                                                        sb2.append(z);
                                                        LOG.i("QBNRClientHelper", sb2.toString());
                                                        QBNRClientHelper qBNRClientHelper2 =
                                                                ((AnonymousClass2) this).this$0;
                                                        qBNRClientHelper2.mIsFinished = true;
                                                        qBNRClientHelper2.mIsSuccess = z;
                                                        Uri.Builder buildUpon = parse2.buildUpon();
                                                        buildUpon.appendQueryParameter(
                                                                "is_success",
                                                                ((AnonymousClass2) this)
                                                                                .this$0
                                                                                .mIsSuccess
                                                                        ? "1"
                                                                        : DATA.DM_FIELD_INDEX
                                                                                .PCSCF_DOMAIN);
                                                        context.getContentResolver()
                                                                .notifyChange(
                                                                        buildUpon.build(), null);
                                                        ParcelFileDescriptor parcelFileDescriptor2 =
                                                                parcelFileDescriptor2;
                                                        if (parcelFileDescriptor2 != null) {
                                                            try {
                                                                parcelFileDescriptor2.close();
                                                                break;
                                                            } catch (IOException e2) {
                                                                e2.printStackTrace();
                                                            }
                                                        }
                                                        break;
                                                }
                                            }

                                            public final void onProgress(long j, long j2) {
                                                switch (this.$r8$classId) {
                                                    case 0:
                                                        StringBuilder sb = new StringBuilder("[");
                                                        AnonymousClass1 anonymousClass1 =
                                                                (AnonymousClass1) this.this$2;
                                                        sb.append(str);
                                                        sb.append("] backup: onProgress: ");
                                                        sb.append(j);
                                                        sb.append(" / ");
                                                        sb.append(j2);
                                                        LOG.d("QBNRClientHelper", sb.toString());
                                                        QBNRClientHelper qBNRClientHelper =
                                                                ((AnonymousClass2) this).this$0;
                                                        qBNRClientHelper.mProcNow = j;
                                                        qBNRClientHelper.mProcTotal = j2;
                                                        context.getContentResolver()
                                                                .notifyChange(parse2, null);
                                                        break;
                                                    default:
                                                        StringBuilder sb2 = new StringBuilder("[");
                                                        AnonymousClass1 anonymousClass12 =
                                                                (AnonymousClass1) this.this$2;
                                                        sb2.append(str);
                                                        sb2.append("] restore: onProgress: ");
                                                        sb2.append(j);
                                                        sb2.append(" / ");
                                                        sb2.append(j2);
                                                        LOG.d("QBNRClientHelper", sb2.toString());
                                                        QBNRClientHelper qBNRClientHelper2 =
                                                                ((AnonymousClass2) this).this$0;
                                                        qBNRClientHelper2.mProcNow = j;
                                                        qBNRClientHelper2.mProcTotal = j2;
                                                        break;
                                                }
                                            }

                                            public C00531(AnonymousClass1 anonymousClass1, byte b) {
                                                this.this$2 = anonymousClass1;
                                            }
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            switch (i2) {
                                                case 0:
                                                    ((ISCloudQBNRClient) obj)
                                                            .backup(
                                                                    context,
                                                                    parcelFileDescriptor2,
                                                                    new C00531(this));
                                                    break;
                                                default:
                                                    ((ISCloudQBNRClient) obj)
                                                            .restore(
                                                                    context,
                                                                    parcelFileDescriptor2,
                                                                    new C00531(this, (byte) 0));
                                                    break;
                                            }
                                        }
                                    },
                                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                            "RESTORE_", str))
                            .start();
                    return null;
                default:
                    StringBuilder m =
                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                    "[", str, "] GET_STATUS: is_finished: ");
                    QBNRClientHelper qBNRClientHelper3 = this.this$0;
                    m.append(qBNRClientHelper3.mIsFinished);
                    m.append(", is_success: ");
                    m.append(qBNRClientHelper3.mIsSuccess);
                    m.append(", proc: ");
                    m.append(qBNRClientHelper3.mProcNow);
                    m.append(", total: ");
                    m.append(qBNRClientHelper3.mProcTotal);
                    LOG.i("QBNRClientHelper", m.toString());
                    Bundle bundle2 = new Bundle();
                    bundle2.putBoolean("is_finished", qBNRClientHelper3.mIsFinished);
                    bundle2.putBoolean("is_success", qBNRClientHelper3.mIsSuccess);
                    if (!qBNRClientHelper3.mIsFinished) {
                        long j = qBNRClientHelper3.mProcTotal;
                        bundle2.putInt(
                                "progress",
                                (int) (j != 0 ? (qBNRClientHelper3.mProcNow * 100) / j : 0L));
                    }
                    return bundle2;
            }
        }
    }

    public QBNRClientHelper(ISCloudQBNRClient iSCloudQBNRClient) {
        HashMap hashMap = new HashMap();
        this.serviceHandlerMap = hashMap;
        this.backupClient = iSCloudQBNRClient;
        hashMap.put("getClientInfo", new AnonymousClass1());
        hashMap.put("backup", new AnonymousClass2(this, 0));
        hashMap.put("restore", new AnonymousClass2(this, 1));
        hashMap.put("get_status", new AnonymousClass2(this, 2));
    }

    @Override // com.samsung.android.scloud.oem.lib.common.IClientHelper
    public final Object getClient() {
        return this.backupClient;
    }

    @Override // com.samsung.android.scloud.oem.lib.common.IClientHelper
    public final IServiceHandler getServiceHandler(String str) {
        return (IServiceHandler) ((HashMap) this.serviceHandlerMap).get(str);
    }
}
