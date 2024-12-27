package com.android.settings.deviceinfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.internal.util.Preconditions;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.deviceinfo.storage.StorageUtils;
import com.android.settingslib.widget.UsageProgressBarPreference;

import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecButtonPreference;

import java.io.File;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PublicVolumeSettings extends SettingsPreferenceFragment {
    public Preference mFormatPublic;
    public Preference mMount;
    public StorageManager mStorageManager;
    public UsageProgressBarPreference mSummary;
    public SecButtonPreference mUnmount;
    public VolumeInfo mVolume;
    public String mVolumeId;
    public final AnonymousClass1 mUnmountListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.android.settings.deviceinfo.PublicVolumeSettings.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    StorageUtils.checkEncryptionAndUnmount(
                            PublicVolumeSettings.this.getActivity(),
                            PublicVolumeSettings.this.mVolume,
                            false);
                }
            };
    public final AnonymousClass2 mStorageListener =
            new StorageEventListener() { // from class:
                                         // com.android.settings.deviceinfo.PublicVolumeSettings.2
                public final void onVolumeRecordChanged(VolumeRecord volumeRecord) {
                    if (Objects.equals(
                            PublicVolumeSettings.this.mVolume.getFsUuid(),
                            volumeRecord.getFsUuid())) {
                        PublicVolumeSettings publicVolumeSettings = PublicVolumeSettings.this;
                        publicVolumeSettings.mVolume =
                                publicVolumeSettings.mStorageManager.findVolumeById(
                                        publicVolumeSettings.mVolumeId);
                        PublicVolumeSettings.this.update();
                    }
                }

                public final void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) {
                    if (Objects.equals(
                            PublicVolumeSettings.this.mVolume.getId(), volumeInfo.getId())) {
                        PublicVolumeSettings publicVolumeSettings = PublicVolumeSettings.this;
                        publicVolumeSettings.mVolume = volumeInfo;
                        publicVolumeSettings.update();
                    }
                }
            };

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1983;
    }

    public final boolean isVolumeValid() {
        VolumeInfo volumeInfo = this.mVolume;
        return volumeInfo != null && volumeInfo.getType() == 0 && this.mVolume.isMountedReadable();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        isVolumeValid();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mStorageManager = (StorageManager) activity.getSystemService(StorageManager.class);
        if ("android.provider.action.DOCUMENT_ROOT_SETTINGS"
                .equals(getActivity().getIntent().getAction())) {
            this.mVolume =
                    this.mStorageManager.findVolumeByUuid(
                            DocumentsContract.getRootId(getActivity().getIntent().getData()));
        } else {
            String string = getArguments().getString("android.os.storage.extra.VOLUME_ID");
            if (string != null) {
                this.mVolume = this.mStorageManager.findVolumeById(string);
            }
        }
        if (!isVolumeValid()) {
            getActivity().finish();
            return;
        }
        Preconditions.checkNotNull(this.mStorageManager.findDiskById(this.mVolume.getDiskId()));
        this.mVolumeId = this.mVolume.getId();
        addPreferencesFromResource(R.xml.device_info_storage_volume);
        getPreferenceScreen().mOrderingAsAdded = true;
        this.mSummary = new UsageProgressBarPreference(getPrefContext());
        Preference preference = new Preference(getPrefContext());
        preference.setTitle(R.string.storage_menu_mount);
        this.mMount = preference;
        SecButtonPreference secButtonPreference = new SecButtonPreference(activity);
        this.mUnmount = secButtonPreference;
        secButtonPreference.setOrder(1000);
        this.mUnmount.mTitle = getResources().getString(R.string.storage_menu_unmount);
        SecButtonPreference secButtonPreference2 = this.mUnmount;
        secButtonPreference2.mButtonBackGroundRes =
                R.drawable.sec_widget_color_round_button_background;
        secButtonPreference2.mButtonTextColor =
                activity.getColor(R.color.sec_widget_color_round_button_text_color);
        this.mUnmount.mOnClickListener = this.mUnmountListener;
        Preference preference2 = new Preference(getPrefContext());
        preference2.setTitle(R.string.storage_menu_format_option);
        this.mFormatPublic = preference2;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mStorageManager.unregisterListener(this.mStorageListener);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        Intent intent = new Intent(getActivity(), (Class<?>) StorageWizardInit.class);
        intent.putExtra("android.os.storage.extra.VOLUME_ID", this.mVolume.getId());
        if (preference == this.mMount) {
            new StorageUtils.MountTask(getActivity(), this.mVolume).execute(new Void[0]);
        } else if (preference == this.mFormatPublic) {
            LoggingHelper.insertEventLogging(1983, 4712);
            startActivity(intent);
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        String str = this.mVolumeId;
        if (str == null) {
            getActivity().finish();
            return;
        }
        this.mVolume = this.mStorageManager.findVolumeById(str);
        if (!isVolumeValid()) {
            getActivity().finish();
        } else {
            this.mStorageManager.registerListener(this.mStorageListener);
            update();
        }
    }

    public final void update() {
        if (!isVolumeValid()) {
            getActivity().finish();
            return;
        }
        getActivity().setTitle(this.mStorageManager.getBestVolumeDescription(this.mVolume));
        FragmentActivity activity = getActivity();
        getPreferenceScreen().removeAll();
        if (this.mVolume.isMountedReadable()) {
            UsageProgressBarPreference usageProgressBarPreference = this.mSummary;
            usageProgressBarPreference.setOrder(Preference.DEFAULT_ORDER);
            getPreferenceScreen().addPreference(usageProgressBarPreference);
            File path = this.mVolume.getPath();
            long totalSpace = path.getTotalSpace();
            long freeSpace = totalSpace - path.getFreeSpace();
            this.mSummary.setUsageSummary(
                    StorageUtils.getStorageSummary(
                            R.string.storage_usage_summary, freeSpace, activity));
            UsageProgressBarPreference usageProgressBarPreference2 = this.mSummary;
            String storageSummary =
                    StorageUtils.getStorageSummary(
                            R.string.storage_total_summary, totalSpace, activity);
            if (!TextUtils.equals(usageProgressBarPreference2.mTotalSummary, storageSummary)) {
                usageProgressBarPreference2.mTotalSummary = storageSummary;
                usageProgressBarPreference2.notifyChanged();
            }
            this.mSummary.setPercent(freeSpace, totalSpace);
        }
        if (this.mVolume.getState() == 0) {
            Preference preference = this.mMount;
            preference.setOrder(Preference.DEFAULT_ORDER);
            getPreferenceScreen().addPreference(preference);
        }
        Preference preference2 = this.mFormatPublic;
        preference2.setOrder(Preference.DEFAULT_ORDER);
        getPreferenceScreen().addPreference(preference2);
        if (this.mVolume.isMountedReadable()) {
            SecButtonPreference secButtonPreference = this.mUnmount;
            secButtonPreference.setOrder(Preference.DEFAULT_ORDER);
            getPreferenceScreen().addPreference(secButtonPreference);
        }
    }
}
