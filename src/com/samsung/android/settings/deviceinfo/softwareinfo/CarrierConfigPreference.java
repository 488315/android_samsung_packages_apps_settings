package com.samsung.android.settings.deviceinfo.softwareinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CarrierConfigPreference extends Preference {
    public int mColorUpdateButton;
    public final Context mContext;
    public final SharedPreferences.Editor mEditor;
    public final SharedPreferences mPref;
    public String mTextUpdateButton;

    public CarrierConfigPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mColorUpdateButton = -16776961;
        this.mTextUpdateButton = ApnSettings.MVNO_NONE;
        this.mContext = context;
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("com.settings.preference.config.update", 0);
        this.mPref = sharedPreferences;
        this.mEditor = sharedPreferences.edit();
        setLayoutResource(R.layout.sec_widget_preference_config);
        if (this.mPref.getInt("com.settings.update_button", 0) != 1) {
            this.mEditor.putInt("com.settings.update_button", 0).commit();
            setSelectable(true);
        } else {
            this.mTextUpdateButton = this.mContext.getString(R.string.update_button_text);
            this.mColorUpdateButton = -16776961;
            setSelectable(false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0066  */
    @Override // androidx.preference.Preference
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBindViewHolder(androidx.preference.PreferenceViewHolder r11) {
        /*
            r10 = this;
            super.onBindViewHolder(r11)
            android.content.SharedPreferences r0 = r10.mPref
            java.lang.String r1 = "com.settings.update_button"
            r2 = 0
            int r0 = r0.getInt(r1, r2)
            r1 = 2131366050(0x7f0a10a2, float:1.8351983E38)
            android.view.View r1 = r11.findViewById(r1)
            android.widget.TextView r1 = (android.widget.TextView) r1
            r3 = 2131365726(0x7f0a0f5e, float:1.8351325E38)
            android.view.View r11 = r11.findViewById(r3)
            android.widget.TextView r11 = (android.widget.TextView) r11
            android.content.Context r3 = r10.mContext
            android.content.ContentResolver r4 = r3.getContentResolver()
            java.lang.String r3 = "version"
            java.lang.String[] r6 = new java.lang.String[]{r3}
            java.lang.String r3 = "content://com.samsung.ims.entitlementconfig.provider/config"
            android.net.Uri r5 = android.net.Uri.parse(r3)
            r7 = 0
            r8 = 0
            r9 = 0
            android.database.Cursor r3 = r4.query(r5, r6, r7, r8, r9)
            if (r3 == 0) goto L53
            boolean r4 = r3.moveToFirst()     // Catch: java.lang.Throwable -> L4e
            if (r4 == 0) goto L53
            java.lang.String r4 = r3.getString(r2)     // Catch: java.lang.Throwable -> L4e
            if (r4 == 0) goto L53
            java.lang.String r4 = r3.getString(r2)     // Catch: java.lang.Throwable -> L4e
            r3.close()
            goto L61
        L4e:
            r10 = move-exception
            r3.close()
            throw r10
        L53:
            if (r3 == 0) goto L58
            r3.close()
        L58:
            android.content.Context r3 = r10.mContext
            r4 = 2132020221(0x7f140bfd, float:1.96788E38)
            java.lang.String r4 = r3.getString(r4)
        L61:
            r11.setText(r4)
            if (r0 == 0) goto L84
            r1.setVisibility(r2)
            java.lang.String r11 = r10.mTextUpdateButton
            r1.setText(r11)
            int r11 = r10.mColorUpdateButton
            r1.setTextColor(r11)
            r11 = 1
            if (r0 != r11) goto L7f
            com.samsung.android.settings.deviceinfo.softwareinfo.CarrierConfigPreference$1 r11 = new com.samsung.android.settings.deviceinfo.softwareinfo.CarrierConfigPreference$1
            r11.<init>()
            r1.setOnClickListener(r11)
            goto L89
        L7f:
            r10 = 0
            r1.setOnClickListener(r10)
            goto L89
        L84:
            r10 = 8
            r1.setVisibility(r10)
        L89:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.softwareinfo.CarrierConfigPreference.onBindViewHolder(androidx.preference.PreferenceViewHolder):void");
    }

    public final void setUpdateButtonVisibility(int i) {
        this.mEditor.putInt("com.settings.update_button", i).commit();
        if (i == 1) {
            this.mTextUpdateButton = this.mContext.getString(R.string.update_button_text);
            this.mColorUpdateButton = -16776961;
            setSelectable(false);
        } else if (i == 2) {
            this.mTextUpdateButton = this.mContext.getString(R.string.updating_button_text);
            this.mColorUpdateButton = -16776961;
            setSelectable(false);
        } else if (i == 3) {
            this.mTextUpdateButton = this.mContext.getString(R.string.error_button_text);
            this.mColorUpdateButton = -7829368;
            setSelectable(true);
        } else {
            setSelectable(true);
        }
        notifyChanged();
    }

    public CarrierConfigPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CarrierConfigPreference(Context context, AttributeSet attributeSet) {
        this(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context,
                        R.attr.dialogPreferenceStyle,
                        android.R.attr.dialogPreferenceStyle));
    }

    public CarrierConfigPreference(Context context) {
        this(context, null);
    }
}
