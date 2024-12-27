package com.android.settings.deviceinfo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.storage.DiskInfo;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.HideNonSystemOverlayMixin;

import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.template.HeaderMixin;
import com.google.android.setupdesign.util.ThemeHelper;
import com.google.android.setupdesign.util.ThemeResolver;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class StorageWizardBase extends FragmentActivity {
    public FooterButton mBack;
    public DiskInfo mDisk;
    public FooterBarMixin mFooterBarMixin;
    public FooterButton mNext;
    public StorageManager mStorage;
    public final AnonymousClass1 mStorageListener =
            new StorageEventListener() { // from class:
                                         // com.android.settings.deviceinfo.StorageWizardBase.1
                public final void onDiskDestroyed(DiskInfo diskInfo) {
                    if (StorageWizardBase.this.mDisk.id.equals(diskInfo.id)) {
                        StorageWizardBase.this.finish();
                    }
                }
            };
    public VolumeInfo mVolume;

    public static void copyBooleanExtra(Intent intent, Intent intent2, String str) {
        if (!intent.hasExtra(str) || intent2.hasExtra(str)) {
            return;
        }
        intent2.putExtra(str, intent.getBooleanExtra(str, false));
    }

    public static void copyStringExtra(Intent intent, Intent intent2, String str) {
        if (!intent.hasExtra(str) || intent2.hasExtra(str)) {
            return;
        }
        intent2.putExtra(str, intent.getStringExtra(str));
    }

    public final VolumeInfo findFirstVolume(int i) {
        while (true) {
            List<VolumeInfo> volumes = this.mStorage.getVolumes();
            if (this.mDisk == null) {
                return null;
            }
            for (VolumeInfo volumeInfo : volumes) {
                if (Objects.equals(this.mDisk.getId(), volumeInfo.getDiskId())
                        && volumeInfo.getType() == 1
                        && volumeInfo.getState() == 2) {
                    return volumeInfo;
                }
            }
            i--;
            if (i <= 0) {
                return null;
            }
            Log.w(
                    "StorageWizardBase",
                    "Missing mounted volume of type 1 hosted by disk "
                            + this.mDisk.getId()
                            + "; trying again");
            SystemClock.sleep(250L);
        }
    }

    public final CharSequence getDiskDescription() {
        DiskInfo diskInfo = this.mDisk;
        if (diskInfo != null) {
            return diskInfo.getDescription();
        }
        VolumeInfo volumeInfo = this.mVolume;
        return volumeInfo != null ? volumeInfo.getDescription() : getText(R.string.unknown);
    }

    public final CharSequence getDiskShortDescription() {
        DiskInfo diskInfo = this.mDisk;
        if (diskInfo != null) {
            return diskInfo.getShortDescription();
        }
        VolumeInfo volumeInfo = this.mVolume;
        return volumeInfo != null ? volumeInfo.getDescription() : getText(R.string.unknown);
    }

    public final GlifLayout getGlifLayout() {
        return (GlifLayout) requireViewById(R.id.setup_wizard_layout);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(R.style.SetupWizardPartnerResource, true);
        super.onApplyThemeResource(theme, i, z);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        int i = 0;
        Logger logger = ThemeHelper.LOG;
        boolean isSetupWizardDayNightEnabled =
                PartnerConfigHelper.isSetupWizardDayNightEnabled(this);
        if (ThemeResolver.defaultResolver == null) {
            ThemeResolver.defaultResolver = new ThemeResolver(2132083859, true);
        }
        int i2 = ThemeResolver.defaultResolver.defaultTheme;
        Bundle bundle2 = PartnerConfigHelper.suwDefaultThemeBundle;
        String str = null;
        if (bundle2 == null || bundle2.isEmpty()) {
            try {
                PartnerConfigHelper.suwDefaultThemeBundle =
                        getContentResolver()
                                .call(
                                        PartnerConfigHelper.getContentUri(this),
                                        PartnerConfigHelper.GET_SUW_DEFAULT_THEME_STRING_METHOD,
                                        (String) null,
                                        (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w(
                        "PartnerConfigHelper",
                        "SetupWizard default theme status unknown; return as null.");
                PartnerConfigHelper.suwDefaultThemeBundle = null;
            }
        }
        Bundle bundle3 = PartnerConfigHelper.suwDefaultThemeBundle;
        if (bundle3 != null && !bundle3.isEmpty()) {
            str =
                    PartnerConfigHelper.suwDefaultThemeBundle.getString(
                            PartnerConfigHelper.GET_SUW_DEFAULT_THEME_STRING_METHOD);
        }
        int i3 = PartnerConfigHelper.isSetupWizardDayNightEnabled(this) ? 2132083868 : 2132083869;
        boolean isSetupWizardDayNightEnabled2 =
                PartnerConfigHelper.isSetupWizardDayNightEnabled(this);
        boolean z = !PartnerConfigHelper.isSetupWizardDayNightEnabled(this);
        if (isSetupWizardDayNightEnabled2 && !z) {
            if (str != null) {
                switch (str) {
                    case "glif_v2_light":
                    case "glif_v2":
                        i = 2132083862;
                        break;
                    case "material_light":
                    case "material":
                        i = 2132083871;
                        break;
                    case "glif_v3_light":
                    case "glif_v3":
                        i = 2132083865;
                        break;
                    case "glif_v4_light":
                    case "glif_v4":
                        i = 2132083868;
                        break;
                    case "glif":
                    case "glif_light":
                        i = 2132083859;
                        break;
                }
            }
        } else {
            i = ThemeResolver.getThemeRes(str);
        }
        if (i != 0 || i != 0) {
            i3 = i;
        }
        setTheme(
                new ThemeResolver(i3, true)
                        .resolve(ApnSettings.MVNO_NONE, true ^ isSetupWizardDayNightEnabled));
        ThemeHelper.trySetDynamicColor(this);
        super.onCreate(bundle);
        getLifecycle().addObserver(new HideNonSystemOverlayMixin(this));
        this.mStorage = (StorageManager) getSystemService(StorageManager.class);
        String stringExtra = getIntent().getStringExtra("android.os.storage.extra.VOLUME_ID");
        if (!TextUtils.isEmpty(stringExtra)) {
            this.mVolume = this.mStorage.findVolumeById(stringExtra);
        }
        String stringExtra2 = getIntent().getStringExtra("android.os.storage.extra.DISK_ID");
        if (TextUtils.isEmpty(stringExtra2)) {
            VolumeInfo volumeInfo = this.mVolume;
            if (volumeInfo != null) {
                this.mDisk = volumeInfo.getDisk();
            }
        } else {
            this.mDisk = this.mStorage.findDiskById(stringExtra2);
        }
        if (this.mDisk != null) {
            this.mStorage.registerListener(this.mStorageListener);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mStorage.unregisterListener(this.mStorageListener);
        super.onDestroy();
    }

    public void onNavigateBack(View view) {
        throw new UnsupportedOperationException();
    }

    public void onNavigateNext(View view) {
        throw new UnsupportedOperationException();
    }

    public final void setAuxChecklist() {
        FrameLayout frameLayout = (FrameLayout) requireViewById(R.id.storage_wizard_aux);
        frameLayout.addView(
                LayoutInflater.from(frameLayout.getContext())
                        .inflate(
                                R.layout.storage_wizard_checklist, (ViewGroup) frameLayout, false));
        frameLayout.setVisibility(0);
        ((TextView) frameLayout.requireViewById(R.id.storage_wizard_migrate_v2_checklist_media))
                .setText(
                        TextUtils.expandTemplate(
                                getText(R.string.storage_wizard_migrate_v2_checklist_media),
                                getDiskShortDescription()));
    }

    public final void setBodyText(int i, CharSequence... charSequenceArr) {
        TextView textView = (TextView) requireViewById(R.id.storage_wizard_body);
        textView.setText(TextUtils.expandTemplate(getText(i), charSequenceArr));
        textView.setVisibility(0);
    }

    public final void setBodyTextNoCaretStr(int i, CharSequence... charSequenceArr) {
        TextView textView = (TextView) requireViewById(R.id.storage_wizard_body);
        textView.setText(String.format(getString(i), charSequenceArr));
        textView.setVisibility(0);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void setContentView(int i) {
        super.setContentView(i);
        FooterBarMixin footerBarMixin =
                (FooterBarMixin) getGlifLayout().getMixin(FooterBarMixin.class);
        this.mFooterBarMixin = footerBarMixin;
        final int i2 = 0;
        footerBarMixin.setSecondaryButton(
                new FooterButton(
                        getString(R.string.wizard_back),
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.deviceinfo.StorageWizardBase$$ExternalSyntheticLambda0
                            public final /* synthetic */ StorageWizardBase f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i3 = i2;
                                StorageWizardBase storageWizardBase = this.f$0;
                                switch (i3) {
                                    case 0:
                                        storageWizardBase.onNavigateBack(view);
                                        break;
                                    default:
                                        storageWizardBase.onNavigateNext(view);
                                        break;
                                }
                            }
                        },
                        0,
                        2132083806),
                false);
        final int i3 = 1;
        this.mFooterBarMixin.setPrimaryButton(
                new FooterButton(
                        getString(R.string.wizard_next),
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.deviceinfo.StorageWizardBase$$ExternalSyntheticLambda0
                            public final /* synthetic */ StorageWizardBase f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i32 = i3;
                                StorageWizardBase storageWizardBase = this.f$0;
                                switch (i32) {
                                    case 0:
                                        storageWizardBase.onNavigateBack(view);
                                        break;
                                    default:
                                        storageWizardBase.onNavigateNext(view);
                                        break;
                                }
                            }
                        },
                        5,
                        2132083805));
        FooterBarMixin footerBarMixin2 = this.mFooterBarMixin;
        this.mBack = footerBarMixin2.secondaryButton;
        this.mNext = footerBarMixin2.primaryButton;
        setIcon(android.R.drawable.ic_thermostat);
    }

    public final void setCurrentProgress(int i) {
        ((ProgressBar) requireViewById(R.id.storage_wizard_progress)).setProgress(i);
        ((TextView) requireViewById(R.id.storage_wizard_progress_summary))
                .setText(NumberFormat.getPercentInstance().format(i / 100.0d));
    }

    public final void setHeaderText(int i, CharSequence... charSequenceArr) {
        CharSequence expandTemplate = TextUtils.expandTemplate(getText(i), charSequenceArr);
        GlifLayout glifLayout = getGlifLayout();
        glifLayout.setHeaderText(expandTemplate);
        ((HeaderMixin) glifLayout.getMixin(HeaderMixin.class)).setAutoTextSizeEnabled(false);
        setTitle(expandTemplate);
    }

    public final void setHeaderTextNoCaretStr(int i, CharSequence... charSequenceArr) {
        getGlifLayout()
                .setHeaderColor(
                        ColorStateList.valueOf(
                                getResources()
                                        .getColor(R.color.sec_storage_wizard_primary_text_color)));
        String format = String.format(getString(i), charSequenceArr);
        getGlifLayout().setHeaderText(format);
        setTitle(format);
    }

    public final void setIcon(int i) {
        getGlifLayout().setIcon(getDrawable(i).mutate());
    }

    public final void setNextButtonText(int i, CharSequence... charSequenceArr) {
        this.mNext.setText(TextUtils.expandTemplate(getText(i), charSequenceArr));
        this.mNext.setVisibility(0);
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public final void startActivity(Intent intent) {
        Intent intent2 = getIntent();
        copyStringExtra(intent2, intent, "android.os.storage.extra.DISK_ID");
        copyStringExtra(intent2, intent, "android.os.storage.extra.VOLUME_ID");
        copyStringExtra(intent2, intent, "format_forget_uuid");
        copyBooleanExtra(intent2, intent, "format_private");
        copyBooleanExtra(intent2, intent, "format_slow");
        copyBooleanExtra(intent2, intent, "migrate_skip");
        super.startActivity(intent);
    }
}
