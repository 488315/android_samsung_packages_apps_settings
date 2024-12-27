package com.samsung.android.sdk.command.template;

import android.os.Bundle;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class CommandTemplate {
    public static final AnonymousClass1 ERROR_TEMPLATE;
    public static final AnonymousClass1 NO_TEMPLATE;
    public final String mTemplateId;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.sdk.command.template.CommandTemplate$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.sdk.command.template.CommandTemplate$1] */
    static {
        final int i = 0;
        NO_TEMPLATE =
                new CommandTemplate() { // from class:
                                        // com.samsung.android.sdk.command.template.CommandTemplate.1
                    @Override // com.samsung.android.sdk.command.template.CommandTemplate
                    public final int getTemplateType() {
                        switch (i) {
                            case 0:
                                return 1;
                            default:
                                return 0;
                        }
                    }
                };
        final int i2 = 1;
        ERROR_TEMPLATE =
                new CommandTemplate() { // from class:
                                        // com.samsung.android.sdk.command.template.CommandTemplate.1
                    @Override // com.samsung.android.sdk.command.template.CommandTemplate
                    public final int getTemplateType() {
                        switch (i2) {
                            case 0:
                                return 1;
                            default:
                                return 0;
                        }
                    }
                };
    }

    public CommandTemplate() {
        this.mTemplateId = ApnSettings.MVNO_NONE;
    }

    public static CommandTemplate createTemplateFromBundle(Bundle bundle) {
        AnonymousClass1 anonymousClass1 = ERROR_TEMPLATE;
        if (bundle == null) {
            return anonymousClass1;
        }
        int i = bundle.getInt("key_template_type", 0);
        try {
            if (i == 1) {
                return NO_TEMPLATE;
            }
            if (i == 2) {
                ToggleTemplate toggleTemplate = new ToggleTemplate(bundle);
                toggleTemplate.mIsChecked = bundle.getBoolean("key_new_value");
                return toggleTemplate;
            }
            if (i == 3) {
                return new SliderTemplate(bundle);
            }
            if (i == 5) {
                return new SingleChoiceTemplate(bundle);
            }
            if (i == 6) {
                UnformattedTemplate unformattedTemplate = new UnformattedTemplate(bundle);
                unformattedTemplate.mJSONString = bundle.getString("key_new_value");
                return unformattedTemplate;
            }
            if (i != 7) {
                return anonymousClass1;
            }
            MediaControlTemplate mediaControlTemplate = new MediaControlTemplate(bundle);
            mediaControlTemplate.mCurrentActiveMode = bundle.getInt("key_current_active_mode");
            mediaControlTemplate.mModeFlags = bundle.getInt("key_mode_flags");
            mediaControlTemplate.mMediaInfo = bundle.getString("key_media_info");
            return mediaControlTemplate;
        } catch (Exception unused) {
            return anonymousClass1;
        }
    }

    public Bundle getDataBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("key_template_id", this.mTemplateId);
        bundle.putInt("key_template_type", getTemplateType());
        return bundle;
    }

    public abstract int getTemplateType();

    public CommandTemplate(Bundle bundle) {
        this.mTemplateId = bundle.getString("key_template_id", ApnSettings.MVNO_NONE);
    }

    public CommandTemplate(String str) {
        this.mTemplateId = str;
    }
}
