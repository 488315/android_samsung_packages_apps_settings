package com.android.settings.deviceinfo;

import android.app.usage.ExternalStorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.util.DataUnit;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockSettingsHelper;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageWizardMigrateConfirm extends StorageWizardBase {
    public AnonymousClass1 mEstimate;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.deviceinfo.StorageWizardMigrateConfirm$1, reason: invalid class name */
    public final class AnonymousClass1 extends AsyncTask {
        public static final long SPEED_ESTIMATE_BPS = DataUnit.MEBIBYTES.toBytes(10);
        public final Context mContext;
        public long mSizeBytes = -1;

        public AnonymousClass1(Context context) {
            this.mContext = context;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            long j = this.mSizeBytes;
            long j2 = -1L;
            if (j != -1) {
                return Long.valueOf(j);
            }
            UserManager userManager =
                    (UserManager) this.mContext.getSystemService(UserManager.class);
            StorageManager storageManager =
                    (StorageManager) this.mContext.getSystemService(StorageManager.class);
            StorageStatsManager storageStatsManager =
                    (StorageStatsManager) this.mContext.getSystemService(StorageStatsManager.class);
            VolumeInfo findEmulatedForPrivate =
                    storageManager.findEmulatedForPrivate(
                            this.mContext.getPackageManager().getPrimaryStorageCurrentVolume());
            if (findEmulatedForPrivate == null) {
                Log.w("MigrateEstimateTask", "Failed to find current primary storage");
                return j2;
            }
            try {
                UUID uuidForPath = storageManager.getUuidForPath(findEmulatedForPrivate.getPath());
                Log.d("MigrateEstimateTask", "Measuring size of " + uuidForPath);
                long j3 = 0;
                for (UserInfo userInfo : userManager.getUsers()) {
                    ExternalStorageStats queryExternalStatsForUser =
                            storageStatsManager.queryExternalStatsForUser(
                                    uuidForPath, UserHandle.of(userInfo.id));
                    j3 += queryExternalStatsForUser.getTotalBytes();
                    if (userInfo.id == 0) {
                        j3 += queryExternalStatsForUser.getObbBytes();
                    }
                }
                return Long.valueOf(j3);
            } catch (IOException e) {
                Log.w("MigrateEstimateTask", "Failed to measure", e);
                return j2;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            long longValue = ((Long) obj).longValue();
            this.mSizeBytes = longValue;
            long max = Math.max((longValue * 1000) / SPEED_ESTIMATE_BPS, 1000L);
            String formatFileSize = Formatter.formatFileSize(this.mContext, this.mSizeBytes);
            String charSequence = DateUtils.formatDuration(max).toString();
            StorageWizardMigrateConfirm storageWizardMigrateConfirm =
                    StorageWizardMigrateConfirm.this;
            storageWizardMigrateConfirm.setBodyText(
                    R.string.storage_wizard_migrate_v2_body,
                    storageWizardMigrateConfirm.getDiskDescription(),
                    formatFileSize,
                    charSequence);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i != 100) {
            super.onActivityResult(i, i2, intent);
        } else if (i2 == -1) {
            onNavigateNext(null);
        } else {
            Log.w("StorageWizardMigrateConfirm", "Failed to confirm credentials");
        }
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.storage_wizard_generic);
        if (this.mVolume == null) {
            this.mVolume = findFirstVolume(1);
        }
        if (getPackageManager().getPrimaryStorageCurrentVolume() == null || this.mVolume == null) {
            Log.d("StorageWizardMigrateConfirm", "Missing either source or target volume");
            finish();
            return;
        }
        setIcon(R.drawable.ic_swap_horiz);
        setHeaderText(R.string.storage_wizard_migrate_v2_title, getDiskShortDescription());
        setBodyText(R.string.memory_calculating_size, new CharSequence[0]);
        setAuxChecklist();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this);
        this.mEstimate = anonymousClass1;
        anonymousClass1.mSizeBytes = getIntent().getLongExtra("size_bytes", -1L);
        this.mEstimate.execute(new Void[0]);
        this.mBack.setText(
                TextUtils.expandTemplate(
                        getText(R.string.storage_wizard_migrate_v2_later), new CharSequence[0]));
        this.mBack.setVisibility(0);
        setNextButtonText(R.string.storage_wizard_migrate_v2_now, new CharSequence[0]);
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase
    public void onNavigateBack(View view) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(this, 1413, new Pair[0]);
        if (this.mDisk == null) {
            finishAffinity();
            return;
        }
        Intent intent = new Intent(this, (Class<?>) StorageWizardReady.class);
        intent.putExtra("migrate_skip", true);
        startActivity(intent);
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase
    public void onNavigateNext(View view) {
        LockPatternUtils lockPatternUtils = new LockPatternUtils(this);
        if (StorageManager.isFileEncrypted()) {
            for (UserInfo userInfo :
                    ((UserManager) getSystemService(UserManager.class)).getUsers()) {
                if (!StorageManager.isCeStorageUnlocked(userInfo.id)) {
                    if (lockPatternUtils.isSecure(userInfo.id)) {
                        RecyclerView$$ExternalSyntheticOutline0.m(
                                new StringBuilder("Secured user "),
                                userInfo.id,
                                " is currently locked; requesting manual unlock",
                                "StorageWizardMigrateConfirm");
                        CharSequence expandTemplate =
                                TextUtils.expandTemplate(
                                        getText(R.string.storage_wizard_move_unlock),
                                        userInfo.name);
                        ChooseLockSettingsHelper.Builder builder =
                                new ChooseLockSettingsHelper.Builder(this);
                        builder.mRequestCode = 100;
                        builder.mDescription = expandTemplate;
                        builder.mUserId = userInfo.id;
                        builder.mAllowAnyUserId = true;
                        builder.mForceVerifyPath = true;
                        builder.show();
                        return;
                    }
                    RecyclerView$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Unsecured user "),
                            userInfo.id,
                            " is currently locked; attempting automatic unlock",
                            "StorageWizardMigrateConfirm");
                    lockPatternUtils.unlockUserKeyIfUnsecured(userInfo.id);
                }
            }
        }
        try {
            int movePrimaryStorage = getPackageManager().movePrimaryStorage(this.mVolume);
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl.getMetricsFeatureProvider().action(this, 1412, new Pair[0]);
            Intent intent = new Intent(this, (Class<?>) StorageWizardMigrateProgress.class);
            intent.putExtra("android.os.storage.extra.VOLUME_ID", this.mVolume.getId());
            intent.putExtra("android.content.pm.extra.MOVE_ID", movePrimaryStorage);
            startActivity(intent);
            finishAffinity();
        } catch (IllegalArgumentException e) {
            if (!Objects.equals(
                    this.mVolume.getFsUuid(),
                    ((StorageManager) getSystemService("storage"))
                            .getPrimaryStorageVolume()
                            .getUuid())) {
                throw e;
            }
            Intent intent2 = new Intent(this, (Class<?>) StorageWizardReady.class);
            intent2.putExtra(
                    "android.os.storage.extra.DISK_ID",
                    getIntent().getStringExtra("android.os.storage.extra.DISK_ID"));
            startActivity(intent2);
            finishAffinity();
        } catch (IllegalStateException unused) {
            Toast.makeText(this, getString(R.string.another_migration_already_in_progress), 1)
                    .show();
            finishAffinity();
        }
    }
}
