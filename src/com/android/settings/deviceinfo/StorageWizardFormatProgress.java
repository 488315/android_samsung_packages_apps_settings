package com.android.settings.deviceinfo;

import android.content.Intent;
import android.content.pm.IPackageMoveObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IVoldTaskListener;
import android.os.PersistableBundle;
import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.android.settings.R;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageWizardFormatProgress extends StorageWizardBase {
    public boolean mFormatPrivate;
    public boolean mFromFactoryReset;
    public PartitionTask mTask;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PartitionTask extends AsyncTask {
        public StorageWizardFormatProgress mActivity;
        public volatile long mPrivateBench;
        public volatile int mProgress;

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            StorageWizardFormatProgress storageWizardFormatProgress = this.mActivity;
            StorageManager storageManager = storageWizardFormatProgress.mStorage;
            try {
                if (!storageWizardFormatProgress.mFormatPrivate) {
                    storageManager.partitionPublic(storageWizardFormatProgress.mDisk.getId());
                    publishProgress(60);
                    try {
                        Thread.sleep(500L);
                    } catch (Exception e) {
                        Log.e("StorageWizardFormatProgress", "Failed to Thread.sleep", e);
                    }
                    return null;
                }
                storageManager.partitionPrivate(storageWizardFormatProgress.mDisk.getId());
                publishProgress(40);
                try {
                    Thread.sleep(500L);
                } catch (Exception e2) {
                    Log.e("StorageWizardFormatProgress", "Failed to Thread.sleep", e2);
                }
                VolumeInfo findFirstVolume = storageWizardFormatProgress.findFirstVolume(25);
                final CompletableFuture completableFuture = new CompletableFuture();
                storageManager.benchmark(
                        findFirstVolume.getId(),
                        new IVoldTaskListener
                                .Stub() { // from class:
                                          // com.android.settings.deviceinfo.StorageWizardFormatProgress.PartitionTask.1
                            public final void onFinished(
                                    int i, PersistableBundle persistableBundle) {
                                completableFuture.complete(persistableBundle);
                            }

                            public final void onStatus(int i, PersistableBundle persistableBundle) {
                                PartitionTask.this.publishProgress(
                                        Integer.valueOf(((i * 40) / 100) + 40));
                            }
                        });
                this.mPrivateBench =
                        ((PersistableBundle) completableFuture.get(60L, TimeUnit.SECONDS))
                                .getLong("run", Long.MAX_VALUE);
                if (storageWizardFormatProgress.mDisk.isDefaultPrimary()
                        && Objects.equals(
                                storageManager.getPrimaryStorageUuid(), "primary_physical")) {
                    Log.d(
                            "StorageWizardFormatProgress",
                            "Just formatted primary physical; silently moving storage to new"
                                + " emulated volume");
                    storageManager.setPrimaryStorageUuid(
                            findFirstVolume.getFsUuid(), new SilentObserver());
                }
                return null;
            } catch (Exception e3) {
                return e3;
            }
            return e3;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Exception exc = (Exception) obj;
            StorageWizardFormatProgress storageWizardFormatProgress = this.mActivity;
            if (storageWizardFormatProgress.isDestroyed()) {
                return;
            }
            if (exc != null) {
                Log.e("StorageWizardFormatProgress", "Failed to partition", exc);
                Toast.makeText(storageWizardFormatProgress, exc.getMessage(), 1).show();
                storageWizardFormatProgress.finishAffinity();
                return;
            }
            publishProgress(100);
            try {
                Thread.sleep(500L);
            } catch (Exception e) {
                Log.e("StorageWizardFormatProgress", "Failed to Thread.sleep", e);
            }
            if (!storageWizardFormatProgress.mFormatPrivate) {
                this.mActivity.onFormatFinished();
                return;
            }
            Log.d(
                    "StorageWizardFormatProgress",
                    "New volume took " + this.mPrivateBench + "ms to run benchmark");
            if (this.mPrivateBench <= 2000
                    && !SystemProperties.getBoolean("sys.debug.storage_slow", false)) {
                this.mActivity.onFormatFinished();
                return;
            }
            StorageWizardFormatProgress storageWizardFormatProgress2 = this.mActivity;
            storageWizardFormatProgress2.getClass();
            Intent intent =
                    new Intent(
                            storageWizardFormatProgress2, (Class<?>) StorageWizardFormatSlow.class);
            intent.putExtra("format_slow", true);
            if (storageWizardFormatProgress2.mFromFactoryReset) {
                intent.putExtra("from_factory_reset", true);
                storageWizardFormatProgress2.startActivityForResult(intent, 111);
            } else {
                storageWizardFormatProgress2.startActivity(intent);
                storageWizardFormatProgress2.finishAffinity();
            }
        }

        @Override // android.os.AsyncTask
        public final void onProgressUpdate(Object[] objArr) {
            this.mProgress = ((Integer[]) objArr)[0].intValue();
            this.mActivity.setCurrentProgress(this.mProgress);
            try {
                Thread.sleep(500L);
            } catch (Exception e) {
                Log.e("StorageWizardFormatProgress", "Failed to Thread.sleep", e);
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 111) {
            setResult(-1, new Intent());
            finish();
        }
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.mDisk == null) {
            finish();
            return;
        }
        setContentView(R.layout.storage_wizard_progress);
        getWindow().getDecorView().setSystemUiVisibility(2);
        getWindow().setFlags(16, 16);
        getGlifLayout().setKeepScreenOn(true);
        this.mFromFactoryReset = getIntent().getBooleanExtra("from_factory_reset", false);
        this.mFormatPrivate = getIntent().getBooleanExtra("format_private", false);
        setHeaderTextNoCaretStr(
                R.string.storage_wizard_format_progress_title, getDiskShortDescription());
        setBodyTextNoCaretStr(R.string.storage_wizard_format_progress_body, getDiskDescription());
        this.mBack.setVisibility(4);
        this.mNext.setVisibility(4);
        ComponentActivity.NonConfigurationInstances nonConfigurationInstances =
                (ComponentActivity.NonConfigurationInstances) getLastNonConfigurationInstance();
        PartitionTask partitionTask =
                (PartitionTask)
                        (nonConfigurationInstances != null
                                ? nonConfigurationInstances.custom
                                : null);
        this.mTask = partitionTask;
        if (partitionTask != null) {
            partitionTask.mActivity = this;
            setCurrentProgress(partitionTask.mProgress);
            return;
        }
        PartitionTask partitionTask2 = new PartitionTask();
        partitionTask2.mProgress = 20;
        this.mTask = partitionTask2;
        partitionTask2.mActivity = this;
        setCurrentProgress(partitionTask2.mProgress);
        this.mTask.execute(new Void[0]);
    }

    public final void onFormatFinished() {
        Intent intent = new Intent(this, (Class<?>) StorageWizardFormatSlow.class);
        intent.putExtra("format_slow", false);
        if (this.mFromFactoryReset) {
            intent.putExtra("from_factory_reset", true);
            startActivityForResult(intent, 111);
        } else {
            startActivity(intent);
            finishAffinity();
        }
    }

    @Override // androidx.activity.ComponentActivity
    public final Object onRetainCustomNonConfigurationInstance() {
        return this.mTask;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SilentObserver extends IPackageMoveObserver.Stub {
        public final void onCreated(int i, Bundle bundle) {}

        public final void onStatusChanged(int i, int i2, long j) {}
    }
}
