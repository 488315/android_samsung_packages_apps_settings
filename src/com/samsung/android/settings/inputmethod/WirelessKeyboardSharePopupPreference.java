package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class WirelessKeyboardSharePopupPreference extends PreferenceGroup
        implements View.OnCreateContextMenuListener {
    public String mHost;
    public int mIndex;
    public boolean mIsVisibleCheckImage;
    public boolean mLongClickable;
    public WirelessKeyboardShareFragment$$ExternalSyntheticLambda1 mOnMenuItemClickListener;
    public final WirelessKeyboardShareDBUtil mWirelessKeyboardShareDBUtil;

    public WirelessKeyboardSharePopupPreference(Context context, AttributeSet attributeSet) {
        super(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, R.attr.preferenceStyle, android.R.attr.preferenceStyle),
                0);
        this.mIsVisibleCheckImage = false;
        this.mHost = ApnSettings.MVNO_NONE;
        this.mLongClickable = false;
        setLayoutResource(R.layout.sec_wireless_keyboard_share_popup_preference);
        if (WirelessKeyboardShareDBUtil.mWirelessKeyboardShareDBUtil == null) {
            WirelessKeyboardShareDBUtil.mWirelessKeyboardShareDBUtil =
                    new WirelessKeyboardShareDBUtil(context);
        }
        this.mWirelessKeyboardShareDBUtil =
                WirelessKeyboardShareDBUtil.mWirelessKeyboardShareDBUtil;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ((ImageView) preferenceViewHolder.findViewById(R.id.check_image_view))
                .setVisibility(this.mIsVisibleCheckImage ? 0 : 4);
        View view = preferenceViewHolder.itemView;
        if (!this.mLongClickable) {
            this = null;
        }
        view.setOnCreateContextMenuListener(this);
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        PreferenceManager.OnPreferenceTreeClickListener onPreferenceTreeClickListener;
        if (getIntent() != null
                || getFragment() != null
                || getPreferenceCount() == 0
                || (onPreferenceTreeClickListener =
                                getPreferenceManager().mOnPreferenceTreeClickListener)
                        == null) {
            return;
        }
        onPreferenceTreeClickListener.onPreferenceTreeClick(this);
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public final void onCreateContextMenu(
            ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        String str = this.mHost;
        String loadByString = this.mWirelessKeyboardShareDBUtil.loadByString();
        boolean z = false;
        ((!loadByString.equals(str) || this.mWirelessKeyboardShareDBUtil.loadByBoolean(3))
                        ? contextMenu.add(0, 0, 0, R.string.wireless_keyboard_share_switch)
                        : contextMenu.add(0, 3, 0, R.string.wireless_keyboard_share_back))
                .setOnMenuItemClickListener(this.mOnMenuItemClickListener);
        contextMenu
                .add(0, 1, 0, R.string.wireless_keyboard_share_remove)
                .setOnMenuItemClickListener(this.mOnMenuItemClickListener);
        contextMenu
                .add(0, 2, 0, R.string.wireless_keyboard_share_change_device)
                .setOnMenuItemClickListener(this.mOnMenuItemClickListener);
        MenuItem item = contextMenu.getItem(2);
        if (loadByString.equals(str) && this.mWirelessKeyboardShareDBUtil.loadByBoolean(0)) {
            z = true;
        }
        item.setEnabled(z);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.preference.Preference, java.lang.Comparable
    public final int compareTo(Preference preference) {
        double order = getOrder();
        double order2 = preference.getOrder();
        return order != order2 ? order > order2 ? 1 : -1 : super.compareTo(preference);
    }
}
