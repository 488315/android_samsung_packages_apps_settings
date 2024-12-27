package com.samsung.android.settings.usefulfeature.labs.flexmodepanel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.core.SecSettingsBaseActivity;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class TouchPadGestureHelp extends SettingsPreferenceFragment {
    public Context mContext;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View findViewById;
        this.mContext = getContext();
        View inflate = layoutInflater.inflate(R.layout.sec_touchpad_gesture_help, (ViewGroup) null);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        ArrayList arrayList = new ArrayList();
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        arrayList.add(
                new TouchPadHelpModel(
                        Utils.isNightMode(this.mContext)
                                ? R.drawable.sec_flip_tap_dark
                                : R.drawable.sec_flip_tap,
                        0,
                        getString(R.string.sec_flex_mode_panel_touchpad_gesture_tap),
                        getString(R.string.sec_flex_mode_panel_touchpad_gesture_tap_summary)));
        arrayList.add(
                new TouchPadHelpModel(
                        Utils.isNightMode(this.mContext)
                                ? R.drawable.sec_flip_tap_with_2_fingers_dark
                                : R.drawable.sec_flip_tap_with_2_fingers,
                        1,
                        getString(R.string.sec_flex_mode_panel_touchpad_gesture_tap_two_finger),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_tap_two_finger_summary)));
        arrayList.add(
                new TouchPadHelpModel(
                        Utils.isNightMode(this.mContext)
                                ? R.drawable.sec_flip_swipe_with_2_fingers_dark
                                : R.drawable.sec_flip_swipe_with_2_fingers,
                        2,
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_swipe_with_two_fingers),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_swipe_with_two_fingers_summary)));
        arrayList.add(
                new TouchPadHelpModel(
                        Utils.isNightMode(this.mContext)
                                ? R.drawable.sec_flip_pinch_with_2_fingers_dark
                                : R.drawable.sec_flip_pinch_with_2_fingers,
                        3,
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_pinch_with_two_fingers),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_pinch_with_two_fingers_summary)));
        arrayList.add(
                new TouchPadHelpModel(
                        Utils.isNightMode(this.mContext)
                                ? R.drawable.sec_flip_touch_and_hold_dark
                                : R.drawable.sec_flip_touch_and_hold,
                        4,
                        getString(R.string.sec_flex_mode_panel_touchpad_gesture_touch_and_hold),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_touch_and_hold_summary)));
        arrayList.add(
                new TouchPadHelpModel(
                        Utils.isNightMode(this.mContext)
                                ? R.drawable.sec_flip_touch_and_hold_drag_dark
                                : R.drawable.sec_flip_touch_and_hold_drag,
                        5,
                        getString(R.string.sec_flex_mode_panel_touchpad_gesture_touch_hold_drag),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_touch_hold_drag_summary)));
        arrayList.add(
                new TouchPadHelpModel(
                        Utils.isNightMode(this.mContext)
                                ? R.drawable.sec_flip_tap_with_3_fingers_dark
                                : R.drawable.sec_flip_tap_with_3_fingers,
                        6,
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_tap_with_three_fingers),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_tap_with_three_fingers_summary)));
        arrayList.add(
                new TouchPadHelpModel(
                        Utils.isNightMode(this.mContext)
                                ? R.drawable.sec_flip_tap_with_4_fingers_dark
                                : R.drawable.sec_flip_tap_with_4_fingers,
                        7,
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_tap_with_four_fingers),
                        getString(
                                R.string
                                        .sec_flex_mode_panel_touchpad_gesture_tap_with_four_fingers_summary)));
        TouchPadHelpListAdapter touchPadHelpListAdapter =
                new TouchPadHelpListAdapter(arrayList, this);
        recyclerView.setHasFixedSize(true);
        getActivity();
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        recyclerView.setAdapter(touchPadHelpListAdapter);
        recyclerView.seslSetFillBottomEnabled(true);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.recyclerparent);
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
        linearLayout.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        linearLayout.setScrollBarStyle(33554432);
        recyclerView.semSetRoundedCorners(15);
        recyclerView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        recyclerView.semSetRoundedCornerOffset(listHorizontalPadding);
        if ((getActivity() instanceof SecSettingsBaseActivity)
                && (findViewById = getActivity().findViewById(R.id.round_corner)) != null) {
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        return inflate;
    }
}
