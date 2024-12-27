package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.PreferenceScreen;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyItem;
import com.samsung.android.settings.widget.SecRadioButtonGearPreference;
import com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u00002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\b'\u0018\u00002\u00020\u0001B!\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r"
                + "\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\n"
                + "\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\t\u0010\n"
                + "¨\u0006\u0012"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/functionkey/FunctionKeyItemPreferenceController;",
            "Lcom/samsung/android/settings/widget/SecRadioButtonGearPreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "item",
            "Lcom/samsung/android/settings/usefulfeature/functionkey/item/FunctionKeyItem;",
            "(Landroid/content/Context;Ljava/lang/String;Lcom/samsung/android/settings/usefulfeature/functionkey/item/FunctionKeyItem;)V",
            "getItem",
            "()Lcom/samsung/android/settings/usefulfeature/functionkey/item/FunctionKeyItem;",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getSummary",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public abstract class FunctionKeyItemPreferenceController
        extends SecRadioButtonGearPreferenceController {
    public static final int $stable = 8;
    private final FunctionKeyItem item;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionKeyItemPreferenceController(Context context, String str, FunctionKeyItem item) {
        super(context, str);
        Intrinsics.checkNotNullParameter(item, "item");
        this.item = item;
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        FunctionKeyItem functionKeyItem = this.item;
        SecRadioButtonGearPreference secRadioButtonGearPreference = this.mPreference;
        if (secRadioButtonGearPreference != null) {
            secRadioButtonGearPreference.setIcon(functionKeyItem.icon);
        }
        SecRadioButtonGearPreference secRadioButtonGearPreference2 = this.mPreference;
        if (secRadioButtonGearPreference2 != null) {
            secRadioButtonGearPreference2.setTitle(functionKeyItem.title);
        }
        SecRadioButtonGearPreference secRadioButtonGearPreference3 = this.mPreference;
        if (secRadioButtonGearPreference3 == null) {
            return;
        }
        secRadioButtonGearPreference3.setOrder(functionKeyItem.order);
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (KnoxUtils.isApplicationRestricted(this.mContext, this.mPreferenceKey, "hide")) {
            return 3;
        }
        if (KnoxUtils.isApplicationRestricted(this.mContext, this.mPreferenceKey, "grayout")) {
            return 5;
        }
        return this.item.availabilityStatus;
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    public final FunctionKeyItem getItem() {
        return this.item;
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getSummary() {
        return this.item.summary;
    }
}
