package com.android.settings.vpn2;

import android.content.Context;
import android.content.res.Resources;
import android.os.UserHandle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.widget.GearPreference;
import com.android.settingslib.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ManageablePreference extends GearPreference {
    public boolean mIsAlwaysOn;
    public boolean mIsInsecureVpn;
    public int mState;
    public int mUserId;

    public ManageablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public final void setState$1(int i) {
        if (this.mState != i) {
            this.mState = i;
            updateSummary();
            notifyHierarchyChanged();
        }
    }

    public final void updateSummary() {
        Resources resources = getContext().getResources();
        String[] stringArray = resources.getStringArray(R.array.vpn_states);
        int i = this.mState;
        String str = i == -1 ? ApnSettings.MVNO_NONE : stringArray[i];
        if (this.mIsInsecureVpn) {
            String string = resources.getString(R.string.vpn_insecure_summary);
            if (!TextUtils.isEmpty(str)) {
                string =
                        AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(str, " / ", string);
            }
            SpannableString spannableString = new SpannableString(string);
            spannableString.setSpan(
                    new ForegroundColorSpan(
                            Utils.getColorAttrDefaultColor(
                                    getContext(), android.R.attr.colorError)),
                    0,
                    string.length(),
                    34);
            setSummary(spannableString);
            return;
        }
        if (!this.mIsAlwaysOn) {
            setSummary(str);
            return;
        }
        String string2 = resources.getString(R.string.vpn_always_on_summary_active);
        if (!TextUtils.isEmpty(str)) {
            string2 = AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(str, " / ", string2);
        }
        setSummary(string2);
    }

    public ManageablePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsAlwaysOn = false;
        this.mIsInsecureVpn = false;
        this.mState = -1;
        setPersistent();
        setOrder(0);
        int myUserId = UserHandle.myUserId();
        this.mUserId = myUserId;
        this.mHelper.checkRestrictionAndSetDisabled(myUserId, "no_config_vpn");
    }
}
