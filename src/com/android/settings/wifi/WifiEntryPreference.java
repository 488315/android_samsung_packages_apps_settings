package com.android.settings.wifi;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.Utils;
import com.android.settingslib.wifi.WifiUtils;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiEntryPreference extends RestrictedPreference
        implements WifiEntry.WifiEntryCallback, View.OnClickListener {
    public CharSequence mContentDescription;
    public final StateListDrawable mFrictionSld;
    public final WifiUtils.InternetIconInjector mIconInjector;
    public int mLevel;
    public WifiEntry mWifiEntry;
    public static final int[] STATE_SECURED = {R.attr.state_encrypted};
    public static final int[] FRICTION_ATTRS = {R.attr.wifi_friction};
    public static final int[] WIFI_CONNECTION_STRENGTH = {
        R.string.accessibility_no_wifi,
        R.string.accessibility_wifi_one_bar,
        R.string.accessibility_wifi_two_bars,
        R.string.accessibility_wifi_three_bars,
        R.string.accessibility_wifi_signal_full
    };

    public WifiEntryPreference(Context context, WifiEntry wifiEntry) {
        this(context, wifiEntry, new WifiUtils.InternetIconInjector(context));
    }

    public CharSequence buildContentDescription() {
        Context context = getContext();
        CharSequence title = getTitle();
        CharSequence summary = getSummary();
        if (!TextUtils.isEmpty(summary)) {
            title = TextUtils.concat(title, ",", summary);
        }
        int level = this.mWifiEntry.getLevel();
        if (level >= 0 && level < 5) {
            title =
                    TextUtils.concat(
                            title, ",", context.getString(WIFI_CONNECTION_STRENGTH[level]));
        }
        CharSequence[] charSequenceArr = new CharSequence[3];
        charSequenceArr[0] = title;
        charSequenceArr[1] = ",";
        charSequenceArr[2] =
                this.mWifiEntry.getSecurity$1() == 0
                        ? context.getString(R.string.accessibility_wifi_security_type_none)
                        : context.getString(R.string.accessibility_wifi_security_type_secured);
        return TextUtils.concat(charSequenceArr);
    }

    public int getIconColorAttr() {
        return (this.mWifiEntry.hasInternetAccess() && this.mWifiEntry.getConnectedState() == 2)
                ? android.R.attr.colorAccent
                : android.R.attr.colorControlNormal;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final int getSecondTargetResId() {
        return R.layout.access_point_friction_widget;
    }

    @Override // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        TextView textView;
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mWifiEntry.mInjector.mWifiManager.isVerboseLoggingEnabled()
                && (textView = (TextView) preferenceViewHolder.findViewById(android.R.id.summary))
                        != null) {
            textView.setMaxLines(100);
        }
        Drawable icon = getIcon();
        if (icon != null) {
            icon.setLevel(this.mLevel);
        }
        preferenceViewHolder.itemView.setContentDescription(this.mContentDescription);
        preferenceViewHolder.findViewById(R.id.two_target_divider).setVisibility(4);
        ImageButton imageButton = (ImageButton) preferenceViewHolder.findViewById(R.id.icon_button);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.friction_icon);
        this.mWifiEntry.getClass();
        imageButton.setVisibility(8);
        if (imageView != null) {
            imageView.setVisibility(0);
            if (this.mFrictionSld == null) {
                return;
            }
            if (this.mWifiEntry.getSecurity$1() != 0 && this.mWifiEntry.getSecurity$1() != 4) {
                this.mFrictionSld.setState(STATE_SECURED);
            }
            imageView.setImageDrawable(this.mFrictionSld.getCurrent());
        }
    }

    public void onClick(View view) {
        view.getId();
    }

    @Override // com.android.wifitrackerlib.WifiEntry.WifiEntryCallback
    public void onUpdated() {
        refresh();
    }

    public void refresh() {
        setTitle(this.mWifiEntry.getTitle());
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry instanceof HotspotNetworkEntry) {
            updateHotspotIcon(((HotspotNetworkEntry) wifiEntry).getDeviceType());
        } else {
            this.mLevel = wifiEntry.getLevel();
            updateIcon(this.mWifiEntry.shouldShowXLevelIcon(), this.mLevel);
        }
        setSummary(this.mWifiEntry.getSummary(false));
        this.mContentDescription = buildContentDescription();
    }

    public void updateHotspotIcon(int i) {
        Drawable drawable =
                getContext()
                        .getDrawable(
                                com.android.settingslib.wifi.WifiUtils.getHotspotIconResource(i));
        if (drawable == null) {
            setIcon((Drawable) null);
        } else {
            drawable.setTintList(Utils.getColorAttr(getContext(), getIconColorAttr()));
            setIcon(drawable);
        }
    }

    public void updateIcon(boolean z, int i) {
        if (i == -1) {
            setIcon((Drawable) null);
            return;
        }
        Context context = this.mIconInjector.context;
        if (i < 0) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Wi-Fi level is out of range! level:", "WifiUtils");
            i = 0;
        } else if (i >= 5) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Wi-Fi level is out of range! level:", "WifiUtils");
            i = 4;
        }
        Drawable drawable =
                context.getDrawable(
                        z
                                ? com.android.settingslib.wifi.WifiUtils.NO_INTERNET_WIFI_PIE[i]
                                : com.android.settingslib.wifi.WifiUtils.WIFI_PIE[i]);
        if (drawable == null) {
            setIcon((Drawable) null);
        } else {
            drawable.setTintList(Utils.getColorAttr(getContext(), getIconColorAttr()));
            setIcon(drawable);
        }
    }

    public WifiEntryPreference(
            Context context,
            WifiEntry wifiEntry,
            WifiUtils.InternetIconInjector internetIconInjector) {
        super(context);
        TypedArray typedArray;
        this.mLevel = -1;
        setLayoutResource(R.layout.preference_access_point);
        try {
            typedArray = getContext().getTheme().obtainStyledAttributes(FRICTION_ATTRS);
        } catch (Resources.NotFoundException unused) {
            typedArray = null;
        }
        this.mFrictionSld =
                typedArray != null ? (StateListDrawable) typedArray.getDrawable(0) : null;
        this.mIconInjector = internetIconInjector;
        this.mWifiEntry = wifiEntry;
        wifiEntry.setListener(this);
        refresh();
    }
}
