package com.samsung.android.settings.uwb.labs.view.uwbtest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.uwb.DistanceMeasurement;
import android.uwb.RangingMeasurement;
import android.uwb.RangingReport;
import android.uwb.RangingSession;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecEditTextPreference;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging;
import com.samsung.android.settings.uwb.labs.control.uwbtest.UwbTestController;
import com.samsung.android.settings.uwb.labs.view.uwbtest.UwbFiraTestFragmentController.AnonymousClass2;
import com.samsung.android.settings.uwb.labs.view.uwbtest.uwbconfig.UwbConfigController;
import com.samsung.android.settings.uwb.labs.view.uwbtest.uwbconfig.UwbConfigUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class UwbFiraTestFragment extends DashboardFragment implements UwbDataReceiver {
    public PreferenceCategory mConfigPreferences;
    public Context mContext;
    public final List mControllers = new ArrayList();
    public PreferenceScreen mPreferenceScreen;
    public UwbFiraOneToOneRangingPreference mRangingPreference;
    public UwbConfigUtil mUwbConfigUtil;
    public UwbFiraTestFragmentController mUwbFiraTestFragmentController;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        this.mContext = context;
        UwbFiraTestFragmentController uwbFiraTestFragmentController =
                new UwbFiraTestFragmentController(context, this);
        this.mUwbFiraTestFragmentController = uwbFiraTestFragmentController;
        uwbFiraTestFragmentController.mUwbDataReceiver = this;
        uwbFiraTestFragmentController.mUwbUiEventReceiver = this;
        Fragment fragment = uwbFiraTestFragmentController.mFragment;
        RelativeLayout relativeLayout =
                (RelativeLayout) fragment.getActivity().findViewById(R.id.button_bar);
        uwbFiraTestFragmentController.mBottomLayout = relativeLayout;
        if (relativeLayout == null) {
            Log.e("UwbFiraTestFragmentController", "bottomLayout null!!");
        } else {
            BottomNavigationView bottomNavigationView =
                    (BottomNavigationView)
                            ((LayoutInflater)
                                            fragment.getActivity()
                                                    .getSystemService("layout_inflater"))
                                    .inflate(
                                            R.layout.sec_uwb_labs_fira_test_bottom_layout,
                                            (ViewGroup) uwbFiraTestFragmentController.mBottomLayout,
                                            false);
            uwbFiraTestFragmentController.mTestButtonView = bottomNavigationView;
            if (bottomNavigationView == null) {
                Log.e("UwbFiraTestFragmentController", "mTestButtonView null!!");
            } else {
                uwbFiraTestFragmentController.mBottomLayout.addView(bottomNavigationView);
                uwbFiraTestFragmentController.mOpenMenu =
                        uwbFiraTestFragmentController.mTestButtonView.menu.getItem(0);
                uwbFiraTestFragmentController.mStartMenu =
                        uwbFiraTestFragmentController.mTestButtonView.menu.getItem(1);
                uwbFiraTestFragmentController.mStopMenu =
                        uwbFiraTestFragmentController.mTestButtonView.menu.getItem(2);
                uwbFiraTestFragmentController.mCloseMenu =
                        uwbFiraTestFragmentController.mTestButtonView.menu.getItem(3);
                uwbFiraTestFragmentController.updateBottomMenu();
                uwbFiraTestFragmentController.mTestButtonView.selectedListener =
                        uwbFiraTestFragmentController.new AnonymousClass2();
            }
            uwbFiraTestFragmentController.mBottomLayout.setVisibility(0);
        }
        ((ArrayList) this.mControllers).add(this.mUwbFiraTestFragmentController);
        return this.mControllers;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_uwb_connectivity_labs_firatest;
    }

    public final void onButtonClicked(int i) {
        if (i == 0) {
            this.mConfigPreferences.setVisible(false);
            this.mRangingPreference.setVisible(true);
        } else {
            if (i != 2) {
                return;
            }
            this.mConfigPreferences.setVisible(true);
            if (this.mRangingPreference.isVisible()) {
                this.mRangingPreference.setVisible(false);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("UwbFiraTestFragment", "CREATE: UwbFiraTestFragment");
        if (this.mContext == null) {
            this.mContext = getContext();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        UwbTestController uwbTestController;
        RangingSession rangingSession;
        super.onDestroy();
        if (this.mUwbFiraTestFragmentController == null
                || (uwbTestController = UwbFiraTestFragmentController.mController) == null) {
            return;
        }
        Ranging ranging = uwbTestController.mRanging;
        if (ranging.mState == 3 || (rangingSession = ranging.mRangingSession) == null) {
            return;
        }
        rangingSession.close();
    }

    @Override // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbDataReceiver
    public final void onReceive(RangingReport rangingReport) {
        float[] fArr;
        UwbFiraOneToOneRangingPreference uwbFiraOneToOneRangingPreference = this.mRangingPreference;
        if (uwbFiraOneToOneRangingPreference == null
                || this.mPreferenceScreen.findPreference(uwbFiraOneToOneRangingPreference.getKey())
                        == null) {
            return;
        }
        UwbFiraOneToOneRangingPreference uwbFiraOneToOneRangingPreference2 =
                this.mRangingPreference;
        uwbFiraOneToOneRangingPreference2.getClass();
        if (rangingReport == null || rangingReport.getMeasurements().size() == 0) {
            return;
        }
        RangingMeasurement rangingMeasurement =
                (RangingMeasurement) rangingReport.getMeasurements().get(0);
        uwbFiraOneToOneRangingPreference2.status = rangingMeasurement.getStatus();
        uwbFiraOneToOneRangingPreference2.nlos = rangingMeasurement.getLineOfSight();
        DistanceMeasurement distanceMeasurement = rangingMeasurement.getDistanceMeasurement();
        uwbFiraOneToOneRangingPreference2.distance = 0;
        uwbFiraOneToOneRangingPreference2.rssi = rangingMeasurement.getRssiDbm();
        if (distanceMeasurement != null) {
            uwbFiraOneToOneRangingPreference2.mTime++;
            uwbFiraOneToOneRangingPreference2.mSuccessCnt++;
            uwbFiraOneToOneRangingPreference2.distance =
                    (int) (distanceMeasurement.getMeters() * 100.0d);
            uwbFiraOneToOneRangingPreference2.mDistVal.add(
                    new Entry(
                            uwbFiraOneToOneRangingPreference2.mTime,
                            (int)
                                    (((RangingMeasurement) rangingReport.getMeasurements().get(0))
                                                    .getDistanceMeasurement()
                                                    .getMeters()
                                            * 100.0d)));
            LineDataSet lineDataSet = uwbFiraOneToOneRangingPreference2.mDistLineDataSet;
            lineDataSet.getClass();
            lineDataSet.calcMinMax$1();
            uwbFiraOneToOneRangingPreference2.mDistLineData.calcMinMax();
            uwbFiraOneToOneRangingPreference2.mDistChart.notifyDataSetChanged();
            LineChart lineChart = uwbFiraOneToOneRangingPreference2.mDistChart;
            ViewPortHandler viewPortHandler = lineChart.mViewPortHandler;
            viewPortHandler.getClass();
            viewPortHandler.mMinScaleX = 1.0f;
            viewPortHandler.limitTransAndScale(
                    viewPortHandler.mMatrixTouch, viewPortHandler.mContentRect);
            ViewPortHandler viewPortHandler2 = lineChart.mViewPortHandler;
            viewPortHandler2.getClass();
            viewPortHandler2.mMinScaleY = 1.0f;
            viewPortHandler2.limitTransAndScale(
                    viewPortHandler2.mMatrixTouch, viewPortHandler2.mContentRect);
            LineChart lineChart2 = uwbFiraOneToOneRangingPreference2.mDistChart;
            Matrix matrix = lineChart2.mFitScreenMatrixBuffer;
            ViewPortHandler viewPortHandler3 = lineChart2.mViewPortHandler;
            viewPortHandler3.mMinScaleX = 1.0f;
            viewPortHandler3.mMinScaleY = 1.0f;
            matrix.set(viewPortHandler3.mMatrixTouch);
            int i = 0;
            while (true) {
                fArr = viewPortHandler3.valsBufferForFitScreen;
                if (i >= 9) {
                    break;
                }
                fArr[i] = 0.0f;
                i++;
            }
            matrix.getValues(fArr);
            fArr[2] = 0.0f;
            fArr[5] = 0.0f;
            fArr[0] = 1.0f;
            fArr[4] = 1.0f;
            matrix.setValues(fArr);
            lineChart2.mViewPortHandler.refresh(matrix, lineChart2, false);
            lineChart2.calculateOffsets();
            lineChart2.postInvalidate();
            ((FragmentActivity) uwbFiraOneToOneRangingPreference2.mContext)
                    .runOnUiThread(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbFiraOneToOneRangingPreference.1
                                public final /* synthetic */ int $r8$classId;
                                public final /* synthetic */ UwbFiraOneToOneRangingPreference
                                        this$0;

                                public /* synthetic */ AnonymousClass1(
                                        UwbFiraOneToOneRangingPreference
                                                uwbFiraOneToOneRangingPreference22,
                                        int i2) {
                                    r2 = i2;
                                    r1 = uwbFiraOneToOneRangingPreference22;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (r2) {
                                        case 0:
                                            UwbFiraOneToOneRangingPreference
                                                    uwbFiraOneToOneRangingPreference3 = r1;
                                            uwbFiraOneToOneRangingPreference3.mDistChart.setData(
                                                    uwbFiraOneToOneRangingPreference3
                                                            .mDistLineData);
                                            r1.mDistChart.invalidate();
                                            break;
                                        default:
                                            ((TextView)
                                                            r1.mViewHolder.findViewById(
                                                                    R.id.textView_distance))
                                                    .setText(Integer.toString(r1.distance));
                                            ((TextView)
                                                            r1.mViewHolder.findViewById(
                                                                    R.id.textView_rssi))
                                                    .setText(Integer.toString(r1.rssi));
                                            ((TextView)
                                                            r1.mViewHolder.findViewById(
                                                                    R.id.textView_rangingCount))
                                                    .setText(Integer.toString(r1.mRangingCnt));
                                            ((TextView)
                                                            r1.mViewHolder.findViewById(
                                                                    R.id.textView_successCount))
                                                    .setText(Integer.toString(r1.mSuccessCnt));
                                            UwbFiraOneToOneRangingPreference
                                                    uwbFiraOneToOneRangingPreference4 = r1;
                                            if (uwbFiraOneToOneRangingPreference4.status == 0) {
                                                if (uwbFiraOneToOneRangingPreference4.nlos != 0) {
                                                    ((ImageView)
                                                                    uwbFiraOneToOneRangingPreference4
                                                                            .mViewHolder
                                                                            .findViewById(
                                                                                    R.id
                                                                                            .imageView_rangingStatus))
                                                            .setBackgroundColor(
                                                                    Color.parseColor("#D3FF9800"));
                                                    break;
                                                } else {
                                                    ((ImageView)
                                                                    uwbFiraOneToOneRangingPreference4
                                                                            .mViewHolder
                                                                            .findViewById(
                                                                                    R.id
                                                                                            .imageView_rangingStatus))
                                                            .setBackgroundColor(
                                                                    Color.parseColor("#21D633"));
                                                    break;
                                                }
                                            } else {
                                                ((ImageView)
                                                                uwbFiraOneToOneRangingPreference4
                                                                        .mViewHolder.findViewById(
                                                                        R.id
                                                                                .imageView_rangingStatus))
                                                        .setBackgroundColor(
                                                                Color.parseColor("#F44336"));
                                                break;
                                            }
                                    }
                                }
                            });
        } else {
            uwbFiraOneToOneRangingPreference22.mErrorCnt++;
        }
        uwbFiraOneToOneRangingPreference22.mRangingCnt =
                uwbFiraOneToOneRangingPreference22.mSuccessCnt
                        + uwbFiraOneToOneRangingPreference22.mErrorCnt;
        ((FragmentActivity) uwbFiraOneToOneRangingPreference22.mContext)
                .runOnUiThread(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbFiraOneToOneRangingPreference.1
                            public final /* synthetic */ int $r8$classId;
                            public final /* synthetic */ UwbFiraOneToOneRangingPreference this$0;

                            public /* synthetic */ AnonymousClass1(
                                    UwbFiraOneToOneRangingPreference
                                            uwbFiraOneToOneRangingPreference22,
                                    int i2) {
                                r2 = i2;
                                r1 = uwbFiraOneToOneRangingPreference22;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (r2) {
                                    case 0:
                                        UwbFiraOneToOneRangingPreference
                                                uwbFiraOneToOneRangingPreference3 = r1;
                                        uwbFiraOneToOneRangingPreference3.mDistChart.setData(
                                                uwbFiraOneToOneRangingPreference3.mDistLineData);
                                        r1.mDistChart.invalidate();
                                        break;
                                    default:
                                        ((TextView)
                                                        r1.mViewHolder.findViewById(
                                                                R.id.textView_distance))
                                                .setText(Integer.toString(r1.distance));
                                        ((TextView) r1.mViewHolder.findViewById(R.id.textView_rssi))
                                                .setText(Integer.toString(r1.rssi));
                                        ((TextView)
                                                        r1.mViewHolder.findViewById(
                                                                R.id.textView_rangingCount))
                                                .setText(Integer.toString(r1.mRangingCnt));
                                        ((TextView)
                                                        r1.mViewHolder.findViewById(
                                                                R.id.textView_successCount))
                                                .setText(Integer.toString(r1.mSuccessCnt));
                                        UwbFiraOneToOneRangingPreference
                                                uwbFiraOneToOneRangingPreference4 = r1;
                                        if (uwbFiraOneToOneRangingPreference4.status == 0) {
                                            if (uwbFiraOneToOneRangingPreference4.nlos != 0) {
                                                ((ImageView)
                                                                uwbFiraOneToOneRangingPreference4
                                                                        .mViewHolder.findViewById(
                                                                        R.id
                                                                                .imageView_rangingStatus))
                                                        .setBackgroundColor(
                                                                Color.parseColor("#D3FF9800"));
                                                break;
                                            } else {
                                                ((ImageView)
                                                                uwbFiraOneToOneRangingPreference4
                                                                        .mViewHolder.findViewById(
                                                                        R.id
                                                                                .imageView_rangingStatus))
                                                        .setBackgroundColor(
                                                                Color.parseColor("#21D633"));
                                                break;
                                            }
                                        } else {
                                            ((ImageView)
                                                            uwbFiraOneToOneRangingPreference4
                                                                    .mViewHolder.findViewById(
                                                                    R.id.imageView_rangingStatus))
                                                    .setBackgroundColor(
                                                            Color.parseColor("#F44336"));
                                            break;
                                        }
                                }
                            }
                        });
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        super.setPreferenceScreen(preferenceScreen);
        this.mPreferenceScreen = preferenceScreen;
        Context context = this.mContext;
        UwbConfigUtil uwbConfigUtil = new UwbConfigUtil();
        uwbConfigUtil.mContext = context;
        ArrayList arrayList = new ArrayList();
        uwbConfigUtil.mUwbConfigList = arrayList;
        arrayList.add(
                new UwbConfigUtil.UwbPreference(1, R.string.uwb_session_id, 0, 0, "session_id"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_device_type,
                        R.array.device_type,
                        R.array.device_type_values,
                        "device_type"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_device_role,
                        R.array.device_role,
                        R.array.device_role_values,
                        "device_role"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_device_addr, 0, 0, "device_address"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_dest_address_list, 0, 0, "dest_address_list"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_session_type,
                        R.array.session_type,
                        R.array.session_type_values,
                        "session_type"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_channel_number,
                        R.array.channel_number,
                        R.array.channel_number_values,
                        "channel_number"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_multi_node_mode,
                        R.array.multi_node_mode,
                        R.array.multi_node_mode_values,
                        "multi_node_mode"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_slot_duration, 0, 0, "slot_duration_rstu"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_ranging_interval, 0, 0, "ranging_interval_ms"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_slots_per_ranging_round, 0, 0, "slots_per_ranging_round"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_fcs_type,
                        R.array.mac_fcs_type,
                        R.array.mac_fcs_type_values,
                        "fcs_type"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_preamble_code_index,
                        R.array.preamble_index,
                        R.array.preamble_index,
                        "preamble_code_index"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0, R.string.uwb_sfd_id, R.array.sfd_id, R.array.sfd_id_values, "sfd_id"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_psdu_data_rate,
                        R.array.psdu_data_rate,
                        R.array.psdu_data_rate_values,
                        "psdu_data_rate"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_preamble_duration,
                        R.array.preamble_duration,
                        R.array.preamble_duration_values,
                        "preamble_duration"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_prf_mode,
                        R.array.prf_mode,
                        R.array.prf_mode_values,
                        "prf_mode"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(1, R.string.uwb_cap_size, 0, 0, "cap_size_range"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_scheduled_mode,
                        R.array.scheduled_mode,
                        R.array.scheduled_mode_values,
                        "scheduled_mode"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_mac_address_mode,
                        R.array.mac_address_mode,
                        R.array.mac_address_mode_values,
                        "mac_address_mode"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_session_priority, 0, 0, "session_priority"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        2,
                        R.string.uwb_has_ranging_result_report_message,
                        0,
                        0,
                        "has_result_report_phase"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        2, R.string.uwb_has_control_message, 0, 0, "has_control_message"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        2,
                        R.string.uwb_has_ranging_control_phase,
                        0,
                        0,
                        "has_ranging_control_phase"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_measurement_report_type,
                        R.array.measurement_report_type,
                        R.array.measurement_report_type_values,
                        "measurement_report_type"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        2, R.string.uwb_hopping_mode, 0, 0, "hopping_mode"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_block_stride_length, 0, 0, "block_stride_length"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1,
                        R.string.uwb_in_band_termination_attempt_count,
                        0,
                        0,
                        "in_band_termination_attempt_count"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_ranging_round_usage,
                        R.array.ranging_round_usage,
                        R.array.ranging_round_usage_values,
                        "ranging_round_usage"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_initiation_time, 0, 0, "initiation_time_ms"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1,
                        R.string.uwb_absolute_initiation_time,
                        0,
                        0,
                        "absolute_initiation_time_us"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1,
                        R.string.uwb_absolute_initiation_time,
                        0,
                        0,
                        "max_ranging_round_retries"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_rframe_config,
                        R.array.rframe_config,
                        R.array.rframe_config_values,
                        "rframe_config"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_sts_segment_count,
                        R.array.num_of_sts_segments,
                        R.array.num_of_sts_segments_values,
                        "sts_segment_count"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_sts_length,
                        R.array.sts_length,
                        R.array.sts_length_values,
                        "sts_length"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(1, R.string.uwb_session_key, 0, 0, "session_key"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_sub_session_key, 0, 0, "subsession_key"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_bprf_phr_data_rate,
                        R.array.bprf_phr_data_rate,
                        R.array.bprf_phr_data_rate_values,
                        "bprf_phr_data_rate"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        2,
                        R.string.uwb_is_tx_adaptive_payload_power_enabled,
                        0,
                        0,
                        "is_tx_adaptive_payload_power_enabled"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_sts_config,
                        R.array.sts_config,
                        R.array.sts_config_values,
                        "sts_config"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_sub_session_id, 0, 0, "sub_session_id"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_aoa_type,
                        R.array.aoa_type,
                        R.array.aoa_type_values,
                        "aoa_type"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(1, R.string.uwb_vendor_id, 0, 0, "vendor_id"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_static_sts_iv, 0, 0, "static_sts_iv"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        2,
                        R.string.uwb_is_rssi_reporting_enabled,
                        0,
                        0,
                        "is_rssi_reporting_enabled"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        2, R.string.uwb_is_diagnostics_enabled, 0, 0, "is_diagnostics_enabled"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        2, R.string.uwb_key_rotation_enabled, 0, 0, "is_key_rotation_enabled"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_key_rotation_rate, 0, 0, "key_rotation_rate"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_aoa_result_request,
                        R.array.aoa_result_request,
                        R.array.aoa_result_request_values,
                        "aoa_result_request"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_range_data_ntf_config,
                        R.array.range_data_ntf_config,
                        R.array.range_data_ntf_config_values,
                        "range_data_ntf_config"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1,
                        R.string.uwb_range_data_ntf_proximity_near,
                        0,
                        0,
                        "range_data_ntf_proximity_near"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1,
                        R.string.uwb_range_data_ntf_proximity_far,
                        0,
                        0,
                        "range_data_ntf_proximity_far"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        2,
                        R.string.uwb_has_time_of_flight_report,
                        0,
                        0,
                        "has_time_of_flight_report"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        2,
                        R.string.uwb_has_aoa_azimuth_report,
                        0,
                        0,
                        "has_angle_of_arrival_azimuth_report"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        2,
                        R.string.uwb_has_aoa_elevation_report,
                        0,
                        0,
                        "has_angle_of_arrival_elevation_report"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        2,
                        R.string.uwb_has_aoa_fom_report,
                        0,
                        0,
                        "has_angle_of_arrival_figure_of_merit_report"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1,
                        R.string.uwb_num_of_msrmt_focus_on_range,
                        0,
                        0,
                        "num_of_msrmt_focus_on_range"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1,
                        R.string.uwb_num_of_msrmt_focus_on_aoa_azimuth,
                        0,
                        0,
                        "num_of_msrmt_focus_on_aoa_azimuth"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1,
                        R.string.uwb_num_of_msrmt_focus_on_aoa_elevation,
                        0,
                        0,
                        "num_of_msrmt_focus_on_aoa_elevation"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1,
                        R.string.uwb_ranging_error_streak_timeout_ms,
                        0,
                        0,
                        "ranging_error_streak_timeout_ms"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_link_layer_mode, 0, 0, "link_layer_mode"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_min_frames_per_rr, 0, 0, "min_frames_per_rr"));
        arrayList.add(new UwbConfigUtil.UwbPreference(1, R.string.uwb_mtu_size, 0, 0, "mtu_size"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_inter_frame_interval, 0, 0, "inter_frame_interval"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_ultdoa_tx_interval_ms, 0, 0, "ul_tdoa_tx_interval"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_ultdoa_random_window_ms, 0, 0, "ul_tdoa_random_window"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_ultdoa_device_id_type,
                        R.array.ul_tdoa_device_id_type,
                        R.array.ul_tdoa_device_id_type_values,
                        "ul_tdoa_device_id_type"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1, R.string.uwb_ultdoa_device_id, 0, 0, "ul_tdoa_device_id"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_ultdoa_tx_timestamp_type,
                        R.array.ul_tdoa_tx_timestamp_type,
                        R.array.ul_tdoa_tx_timestamp_type_values,
                        "ul_tdoa_tx_timestamp_type"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        0,
                        R.string.uwb_filter_type,
                        R.array.filter_type,
                        R.array.filter_type_values,
                        "filter_type"));
        arrayList.add(
                new UwbConfigUtil.UwbPreference(
                        1,
                        R.string.uwb_max_num_of_measurements,
                        0,
                        0,
                        "max_number_of_measurements"));
        this.mUwbConfigUtil = uwbConfigUtil;
        UwbFiraOneToOneRangingPreference uwbFiraOneToOneRangingPreference =
                (UwbFiraOneToOneRangingPreference)
                        findPreference("uwb_fira_one_to_one_ranging_preference");
        this.mRangingPreference = uwbFiraOneToOneRangingPreference;
        uwbFiraOneToOneRangingPreference.getClass();
        this.mRangingPreference.setVisible(false);
        PreferenceCategory preferenceCategory = (PreferenceCategory) findPreference("fira_config");
        this.mConfigPreferences = preferenceCategory;
        preferenceCategory.setVisible(true);
        UwbConfigUtil uwbConfigUtil2 = this.mUwbConfigUtil;
        ArrayList<Preference> arrayList2 = null;
        if (uwbConfigUtil2.mUwbConfigList != null) {
            ArrayList arrayList3 = new ArrayList();
            Iterator it = ((ArrayList) uwbConfigUtil2.mUwbConfigList).iterator();
            while (it.hasNext()) {
                UwbConfigUtil.UwbPreference uwbPreference = (UwbConfigUtil.UwbPreference) it.next();
                int i = uwbPreference.type;
                int i2 = uwbPreference.title_id;
                String str = uwbPreference.key;
                if (i == 0) {
                    SecDropDownPreference secDropDownPreference =
                            new SecDropDownPreference(uwbConfigUtil2.mContext, null);
                    secDropDownPreference.setKey(str);
                    secDropDownPreference.setTitle(i2);
                    secDropDownPreference.setEntries(uwbPreference.entrys_id);
                    secDropDownPreference.setEntryValues(uwbPreference.entry_values_id);
                    arrayList3.add(secDropDownPreference);
                } else if (i == 1) {
                    SecEditTextPreference secEditTextPreference =
                            new SecEditTextPreference(
                                    uwbConfigUtil2.mContext, null, R.attr.editTextPreferenceStyle);
                    secEditTextPreference.mDialogLayoutResId =
                            R.layout.sec_widget_preference_dialog_edittext;
                    secEditTextPreference.setKey(str);
                    secEditTextPreference.setTitle(i2);
                    arrayList3.add(secEditTextPreference);
                } else if (i == 2) {
                    SecSwitchPreference secSwitchPreference =
                            new SecSwitchPreference(uwbConfigUtil2.mContext);
                    secSwitchPreference.setKey(str);
                    secSwitchPreference.setTitle(i2);
                    arrayList3.add(secSwitchPreference);
                }
            }
            arrayList2 = arrayList3;
        }
        for (Preference preference : arrayList2) {
            this.mConfigPreferences.addPreference(preference);
            addPreferenceController(
                    new UwbConfigController(
                            this.mContext,
                            preference.getKey(),
                            this.mUwbFiraTestFragmentController));
        }
    }

    @Override // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbDataReceiver
    public final void onStateReceive(int i) {}
}
