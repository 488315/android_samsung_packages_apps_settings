package com.android.settings.deviceinfo.storage;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.deviceinfo.PrivateVolumeForget;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.security.SemSdCardEncryption;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class StorageUtils {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SystemInfoFragment extends InstrumentedDialogFragment {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 565;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.P.mMessage = getContext().getString(R.string.storage_os_detail_dialog_system);
            builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
            return builder.create();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TemporaryFilesInfoFragment extends InstrumentedDialogFragment {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 2079;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.P.mMessage =
                    getContext().getString(R.string.storage_other_files_detail_dialog_system);
            builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
            return builder.create();
        }
    }

    public static void checkEncryptionAndUnmount(
            Context context, VolumeInfo volumeInfo, boolean z) {
        SemSdCardEncryption semSdCardEncryption =
                new SemSdCardEncryption(context.getApplicationContext());
        if (!semSdCardEncryption.isEncryptionSupported()
                || !semSdCardEncryption.isSdCardEncryped()
                || !volumeInfo.disk.isSd()) {
            new MountTask(context, volumeInfo).execute(new Void[0]);
            return;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.samsung.android.settings.deviceinfo.StorageSDCardEncryptionWarnDialog");
        if (z) {
            intent.setFlags(268435456);
        }
        intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
        context.startActivity(intent);
    }

    public static List getAllStorageEntries(
            final Context context, final StorageManager storageManager) {
        ArrayList arrayList = new ArrayList();
        final int i = 0;
        arrayList.addAll(
                (Collection)
                        storageManager.getVolumes().stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.settings.deviceinfo.storage.StorageUtils$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                switch (i) {
                                                    case 0:
                                                        int type = ((VolumeInfo) obj).getType();
                                                        return type == 0 || type == 1 || type == 5;
                                                    default:
                                                        return StorageUtils.isDiskUnsupported(
                                                                (DiskInfo) obj);
                                                }
                                            }
                                        })
                                .map(
                                        new Function() { // from class:
                                                         // com.android.settings.deviceinfo.storage.StorageUtils$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return new StorageEntry(context, (VolumeInfo) obj);
                                            }
                                        })
                                .collect(Collectors.toList()));
        final int i2 = 1;
        Stream filter =
                storageManager.getDisks().stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.android.settings.deviceinfo.storage.StorageUtils$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        switch (i2) {
                                            case 0:
                                                int type = ((VolumeInfo) obj).getType();
                                                return type == 0 || type == 1 || type == 5;
                                            default:
                                                return StorageUtils.isDiskUnsupported(
                                                        (DiskInfo) obj);
                                        }
                                    }
                                });
        final int i3 = 0;
        arrayList.addAll(
                (Collection)
                        filter.map(
                                        new Function() { // from class:
                                                         // com.android.settings.deviceinfo.storage.StorageUtils$$ExternalSyntheticLambda3
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                switch (i3) {
                                                    case 0:
                                                        return new StorageEntry((DiskInfo) obj);
                                                    default:
                                                        return new StorageEntry((VolumeRecord) obj);
                                                }
                                            }
                                        })
                                .collect(Collectors.toList()));
        final int i4 = 1;
        arrayList.addAll(
                (Collection)
                        storageManager.getVolumeRecords().stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.settings.deviceinfo.storage.StorageUtils$$ExternalSyntheticLambda4
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return StorageUtils.isVolumeRecordMissed(
                                                        storageManager, (VolumeRecord) obj);
                                            }
                                        })
                                .map(
                                        new Function() { // from class:
                                                         // com.android.settings.deviceinfo.storage.StorageUtils$$ExternalSyntheticLambda3
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                switch (i4) {
                                                    case 0:
                                                        return new StorageEntry((DiskInfo) obj);
                                                    default:
                                                        return new StorageEntry((VolumeRecord) obj);
                                                }
                                            }
                                        })
                                .collect(Collectors.toList()));
        return arrayList;
    }

    public static String getStorageSizeLabel(Context context, long j) {
        Formatter.BytesResult formatBytes = Formatter.formatBytes(context.getResources(), j, 1);
        return TextUtils.expandTemplate(
                        context.getText(R.string.storage_size_large),
                        formatBytes.value,
                        formatBytes.units)
                .toString();
    }

    public static String getStorageSummary(int i, long j, Context context) {
        Formatter.BytesResult formatBytes = Formatter.formatBytes(context.getResources(), j, 1);
        return context.getString(i, formatBytes.value, formatBytes.units);
    }

    public static boolean isDiskUnsupported(DiskInfo diskInfo) {
        return diskInfo.volumeCount == 0 && diskInfo.size > 0;
    }

    public static boolean isVolumeRecordMissed(
            StorageManager storageManager, VolumeRecord volumeRecord) {
        return volumeRecord.getType() == 1
                && storageManager.findVolumeByUuid(volumeRecord.getFsUuid()) == null;
    }

    public static void launchForgetMissingVolumeRecordFragment(
            Context context, StorageEntry storageEntry) {
        if (storageEntry == null || !storageEntry.isVolumeRecordMissed()) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("android.os.storage.extra.FS_UUID", storageEntry.getFsUuid());
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String canonicalName = PrivateVolumeForget.class.getCanonicalName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = canonicalName;
        subSettingLauncher.setTitleRes(R.string.storage_menu_forget, null);
        launchRequest.mSourceMetricsCategory = 745;
        launchRequest.mArguments = bundle;
        subSettingLauncher.launch();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MountTask extends AsyncTask {
        public final /* synthetic */ int $r8$classId = 1;
        public final Context mContext;
        public final String mDescription;
        public final StorageManager mStorageManager;
        public final String mVolumeId;

        public MountTask(Context context, VolumeInfo volumeInfo) {
            Context applicationContext = context.getApplicationContext();
            this.mContext = applicationContext;
            StorageManager storageManager =
                    (StorageManager) applicationContext.getSystemService(StorageManager.class);
            this.mStorageManager = storageManager;
            this.mVolumeId = volumeInfo.getId();
            this.mDescription = storageManager.getBestVolumeDescription(volumeInfo);
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            switch (this.$r8$classId) {
                case 0:
                    try {
                        this.mStorageManager.mount(this.mVolumeId);
                        return null;
                    } catch (Exception e) {
                        return e;
                    }
                default:
                    try {
                        this.mStorageManager.unmount(this.mVolumeId);
                        return null;
                    } catch (Exception e2) {
                        return e2;
                    }
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    Exception exc = (Exception) obj;
                    if (exc != null) {
                        Log.e("StorageUtils", "Failed to mount " + this.mVolumeId, exc);
                        Context context = this.mContext;
                        Preference$$ExternalSyntheticOutline0.m(
                                context,
                                R.string.storage_mount_failure,
                                new Object[] {this.mDescription},
                                context,
                                0);
                        break;
                    } else {
                        Context context2 = this.mContext;
                        Preference$$ExternalSyntheticOutline0.m(
                                context2,
                                R.string.storage_mount_success,
                                new Object[] {this.mDescription},
                                context2,
                                0);
                        break;
                    }
                default:
                    Exception exc2 = (Exception) obj;
                    if (exc2 != null) {
                        Log.e("StorageUtils", "Failed to unmount " + this.mVolumeId, exc2);
                        Context context3 = this.mContext;
                        Preference$$ExternalSyntheticOutline0.m(
                                context3,
                                R.string.storage_unmount_failure,
                                new Object[] {this.mDescription},
                                context3,
                                0);
                        break;
                    } else {
                        Context context4 = this.mContext;
                        Preference$$ExternalSyntheticOutline0.m(
                                context4,
                                R.string.storage_unmount_success,
                                new Object[] {this.mDescription},
                                context4,
                                0);
                        break;
                    }
            }
        }

        public MountTask(FragmentActivity fragmentActivity, VolumeInfo volumeInfo) {
            Context applicationContext = fragmentActivity.getApplicationContext();
            this.mContext = applicationContext;
            StorageManager storageManager =
                    (StorageManager) applicationContext.getSystemService(StorageManager.class);
            this.mStorageManager = storageManager;
            this.mVolumeId = volumeInfo.getId();
            this.mDescription = storageManager.getBestVolumeDescription(volumeInfo);
        }
    }
}
