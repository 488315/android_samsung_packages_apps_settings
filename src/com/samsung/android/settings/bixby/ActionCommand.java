package com.samsung.android.settings.bixby;

import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bixby.target.AboutDeviceAction;
import com.samsung.android.settings.bixby.target.AppsAction;
import com.samsung.android.settings.bixby.target.BiometricsAndSecurityAction;
import com.samsung.android.settings.bixby.target.BluetoothAction;
import com.samsung.android.settings.bixby.target.DataUsage;
import com.samsung.android.settings.bixby.target.DisplayAction;
import com.samsung.android.settings.bixby.target.DisplayColorResetAction;
import com.samsung.android.settings.bixby.target.FaceRecognitionAction;
import com.samsung.android.settings.bixby.target.FingerPrintsAction;
import com.samsung.android.settings.bixby.target.FingerSensorGestureAction;
import com.samsung.android.settings.bixby.target.FontAction;
import com.samsung.android.settings.bixby.target.FontStyleAction;
import com.samsung.android.settings.bixby.target.GeneralManagementAction;
import com.samsung.android.settings.bixby.target.GetJumpToAppResultAction;
import com.samsung.android.settings.bixby.target.GotoJumpToAppMenuAction;
import com.samsung.android.settings.bixby.target.HardPressAction;
import com.samsung.android.settings.bixby.target.HotspotAction;
import com.samsung.android.settings.bixby.target.LanguageAction;
import com.samsung.android.settings.bixby.target.LanguageAndInputAction;
import com.samsung.android.settings.bixby.target.LockScreenAction;
import com.samsung.android.settings.bixby.target.MusicShareAction;
import com.samsung.android.settings.bixby.target.MuteDurationAction;
import com.samsung.android.settings.bixby.target.NfcAction;
import com.samsung.android.settings.bixby.target.NoDisturbAction;
import com.samsung.android.settings.bixby.target.NotificationReminderAction;
import com.samsung.android.settings.bixby.target.NotificationSoundsAction;
import com.samsung.android.settings.bixby.target.OneHandModeAction;
import com.samsung.android.settings.bixby.target.ResetSettingsAction;
import com.samsung.android.settings.bixby.target.RingtoneAction;
import com.samsung.android.settings.bixby.target.SamsungKeyboardAction;
import com.samsung.android.settings.bixby.target.ScreenKeyboardAction;
import com.samsung.android.settings.bixby.target.ScreenSaverAction;
import com.samsung.android.settings.bixby.target.ScreenTimeoutAction;
import com.samsung.android.settings.bixby.target.ScreenZoomAction;
import com.samsung.android.settings.bixby.target.SendSOSMessageAction;
import com.samsung.android.settings.bixby.target.SettingsMainAction;
import com.samsung.android.settings.bixby.target.SimStatusAction;
import com.samsung.android.settings.bixby.target.SmartCaptureAction;
import com.samsung.android.settings.bixby.target.SoundAction;
import com.samsung.android.settings.bixby.target.SoundAndVibrationAction;
import com.samsung.android.settings.bixby.target.StatusAction;
import com.samsung.android.settings.bixby.target.StatusBarAction;
import com.samsung.android.settings.bixby.target.UserManualAction;
import com.samsung.android.settings.bixby.target.VibrationPatternAction;
import com.samsung.android.settings.bixby.target.VolumeAction;
import com.samsung.android.settings.bixby.target.WhiteBalanceAction;
import com.samsung.android.settings.bixby.target.WifiP2pAction;
import com.samsung.android.settings.bixby.target.WifiSettingsAction;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.bixby.utils.BixbyUtils;
import com.samsung.android.util.SemLog;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.IMSParameter;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ActionCommand {
    public static ActionCommand sInstance;
    public Context mContext;

    public static Bundle createResult(String str) {
        return AbsAdapter$1$$ExternalSyntheticOutline0.m("result", str);
    }

    public static ActionCommand getInstance(Context context) {
        if (sInstance == null) {
            ActionCommand actionCommand = new ActionCommand();
            actionCommand.mContext = context;
            sInstance = actionCommand;
        }
        return sInstance;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v12, types: [com.samsung.android.settings.bixby.target.GetJumpToAppResultAction$1] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.samsung.android.settings.bixby.target.SoundAction, com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v10, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v11, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v12, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v13, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v14, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v15, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v16, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v17, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v18, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v19, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v20, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v21, types: [com.samsung.android.settings.bixby.target.MuteDurationAction] */
    /* JADX WARN: Type inference failed for: r7v22, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v23, types: [com.samsung.android.settings.bixby.target.BluetoothAction] */
    /* JADX WARN: Type inference failed for: r7v24, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v25 */
    /* JADX WARN: Type inference failed for: r7v26, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v27, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v28, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v29, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v3, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v30, types: [com.samsung.android.settings.bixby.target.NfcAction] */
    /* JADX WARN: Type inference failed for: r7v31, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v32, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v33, types: [com.samsung.android.settings.bixby.target.WifiSettingsAction] */
    /* JADX WARN: Type inference failed for: r7v34, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v35, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v36, types: [com.samsung.android.settings.bixby.target.WifiSettingsAction] */
    /* JADX WARN: Type inference failed for: r7v37, types: [com.samsung.android.settings.bixby.target.WifiP2pAction, com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v38, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v39, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v4, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v40, types: [com.samsung.android.settings.bixby.target.HotspotAction, com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v41, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v42, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v43, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v44, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v45, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v46, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v47, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v48, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v49, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v5, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v50, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v6, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v7, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    /* JADX WARN: Type inference failed for: r7v8, types: [com.samsung.android.settings.bixby.target.SamsungKeyboardAction] */
    /* JADX WARN: Type inference failed for: r7v9, types: [com.samsung.android.settings.bixby.target.actions.Action] */
    public final Bundle execute(String str, Bundle bundle, String str2) {
        char c;
        Bundle createResult;
        GetJumpToAppResultAction getJumpToAppResultAction;
        SemLog.i("ActionCommand", "actionType : " + str + ", target : " + str2);
        Context context = this.mContext;
        str2.getClass();
        ?? r7 = 0;
        switch (str2.hashCode()) {
            case -2051748129:
                if (str2.equals("do_not_disturb")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1890801222:
                if (str2.equals("recover_color_settings")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1724631124:
                if (str2.equals("biometrics_and_security")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1703095581:
                if (str2.equals("vibration_pattern")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1674631940:
                if (str2.equals("finger_prints")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1671283060:
                if (str2.equals("samsung_keyboard")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1613589672:
                if (str2.equals("language")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1539906063:
                if (str2.equals("font_size")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1336569598:
                if (str2.equals("screen_saver")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1268497459:
                if (str2.equals("onehand_mode")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1236583518:
                if (str2.equals("ringtone")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1182816058:
                if (str2.equals("white_balance")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1032713865:
                if (str2.equals("get_jump_to_app_result")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -892481550:
                if (str2.equals(IMSParameter.CALL.STATUS)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -863275205:
                if (str2.equals("language_and_input")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -842114310:
                if (str2.equals("sound_and_vibration")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -822501798:
                if (str2.equals("screen_keyboard")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -810883302:
                if (str2.equals("volume")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -604364715:
                if (str2.equals("settings_main")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -510335092:
                if (str2.equals("temporary_mute_duration")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -492120639:
                if (str2.equals("font_style")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -482323367:
                if (str2.equals("bluetooth_connect")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -381820416:
                if (str2.equals("lock_screen")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -342656301:
                if (str2.equals("sound_mode")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -339464518:
                if (str2.equals("user_manual")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case -307472109:
                if (str2.equals("reset_settings")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case -52539576:
                if (str2.equals("about_device")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case -42893370:
                if (str2.equals("screen_zoom")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case 108971:
                if (str2.equals("nfc")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 3000946:
                if (str2.equals("apps")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case 7678959:
                if (str2.equals("hard_press")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 84474627:
                if (str2.equals("wifiQrcode")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case 172752680:
                if (str2.equals("send_sos_message")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 248328774:
                if (str2.equals("status_bar")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case 605219416:
                if (str2.equals("wifiSettings")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case 620554046:
                if (str2.equals("wifidirect")) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case 860063886:
                if (str2.equals("screen_timeout")) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case 875380826:
                if (str2.equals("sim_status")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case 1099603663:
                if (str2.equals("hotspot")) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case 1188101912:
                if (str2.equals("notification_sounds")) {
                    c = '\'';
                    break;
                }
                c = 65535;
                break;
            case 1338327366:
                if (str2.equals("notification_reminder")) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case 1390715898:
                if (str2.equals("general_management")) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case 1407455445:
                if (str2.equals("face_recognition")) {
                    c = '*';
                    break;
                }
                c = 65535;
                break;
            case 1449953626:
                if (str2.equals("finger_sensor_gesture")) {
                    c = '+';
                    break;
                }
                c = 65535;
                break;
            case 1588900165:
                if (str2.equals("music_share")) {
                    c = ',';
                    break;
                }
                c = 65535;
                break;
            case 1621485772:
                if (str2.equals("data_usage")) {
                    c = '-';
                    break;
                }
                c = 65535;
                break;
            case 1644670668:
                if (str2.equals("goto_jump_to_app_menu")) {
                    c = '.';
                    break;
                }
                c = 65535;
                break;
            case 1671764162:
                if (str2.equals("display")) {
                    c = '/';
                    break;
                }
                c = 65535;
                break;
            case 1968882350:
                if (str2.equals("bluetooth")) {
                    c = '0';
                    break;
                }
                c = 65535;
                break;
            case 2069703376:
                if (str2.equals("smart_capture")) {
                    c = '1';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                r7 = new NoDisturbAction(context, bundle);
                break;
            case 1:
                r7 = new DisplayColorResetAction(context, bundle);
                break;
            case 2:
                r7 = new BiometricsAndSecurityAction(context, bundle);
                break;
            case 3:
                r7 = new VibrationPatternAction(context, bundle);
                break;
            case 4:
                r7 = new FingerPrintsAction(context, bundle);
                break;
            case 5:
                r7 = new SamsungKeyboardAction(context, bundle);
                break;
            case 6:
                r7 = new LanguageAction(context, bundle);
                break;
            case 7:
                r7 = new FontAction(context, bundle);
                break;
            case '\b':
                r7 = new ScreenSaverAction(context, bundle);
                break;
            case '\t':
                r7 = new OneHandModeAction(context, bundle);
                break;
            case '\n':
                r7 = new RingtoneAction(context, bundle);
                break;
            case 11:
                r7 = new WhiteBalanceAction(context, bundle);
                break;
            case '\f':
                final GetJumpToAppResultAction getJumpToAppResultAction2 = new GetJumpToAppResultAction(context, bundle);
                getJumpToAppResultAction2.mResult = "time_out";
                getJumpToAppResultAction2.mLatch = null;
                final Looper mainLooper = Looper.getMainLooper();
                getJumpToAppResultAction2.mHandler = new Handler(mainLooper) { // from class: com.samsung.android.settings.bixby.target.GetJumpToAppResultAction.1
                    public AnonymousClass1(final Looper mainLooper2) {
                        super(mainLooper2);
                    }

                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        super.handleMessage(message);
                        Preference$$ExternalSyntheticOutline0.m(new StringBuilder("handleMessage() msg : "), message.what, "GetJumpToAppResultAction");
                        if (message.what != 1) {
                            return;
                        }
                        GetJumpToAppResultAction getJumpToAppResultAction3 = GetJumpToAppResultAction.this;
                        getJumpToAppResultAction3.mContext.getApplicationContext();
                        getJumpToAppResultAction3.getClass();
                        if (!getJumpToAppResultAction3.mContext.getContentResolver().call(new Uri.Builder().scheme("content").authority("com.samsung.android.settings.intelligence.search.provider.SettingSearchProvider").build(), "isIndexingComplete", ApnSettings.MVNO_NONE, new Bundle()).getBoolean("isIndexingComplete")) {
                            sendMessageDelayed(obtainMessage(1), 500L);
                            return;
                        }
                        String value = getJumpToAppResultAction3.getValue();
                        getJumpToAppResultAction3.getClass();
                        String str3 = "time_out";
                        try {
                            Cursor query = getJumpToAppResultAction3.mContext.getContentResolver().query(Uri.parse("content://com.samsung.android.settings.intelligence.search.provider.SettingSearchProvider"), null, value, null, null);
                            if (query != null) {
                                try {
                                    if (query.getCount() > 0) {
                                        str3 = String.valueOf(query.getCount());
                                        query.moveToNext();
                                        String string = query.getString(query.getColumnIndex("data_title"));
                                        String string2 = query.getString(query.getColumnIndex("intent_action"));
                                        if (query.getCount() == 1 && string != null && value != null && string.replaceAll("\\s+", ApnSettings.MVNO_NONE).toLowerCase().contains(value.replaceAll("\\s+", ApnSettings.MVNO_NONE).toLowerCase())) {
                                            SemLog.d("GetJumpToAppResultAction", "return search result to intent form : ".concat(value));
                                            str3 = string2;
                                        }
                                    }
                                } finally {
                                }
                            }
                            if (query != null) {
                                query.close();
                            }
                        } catch (Exception unused) {
                            SemLog.d("GetJumpToAppResultAction", "fail to search : " + getJumpToAppResultAction3.getValue());
                        }
                        getJumpToAppResultAction3.mResult = str3;
                        getJumpToAppResultAction3.mLatch.countDown();
                    }
                };
                getJumpToAppResultAction = getJumpToAppResultAction2;
                r7 = getJumpToAppResultAction;
                break;
            case '\r':
                r7 = new StatusAction(context, bundle);
                break;
            case 14:
                r7 = new LanguageAndInputAction(context, bundle);
                break;
            case 15:
                r7 = new SoundAndVibrationAction(context, bundle);
                break;
            case 16:
                r7 = new ScreenKeyboardAction(context, bundle);
                break;
            case 17:
                r7 = new VolumeAction(context, bundle);
                break;
            case 18:
                r7 = new SettingsMainAction(context, bundle);
                break;
            case 19:
                r7 = new MuteDurationAction(context, bundle);
                break;
            case 20:
                r7 = new FontStyleAction(context, bundle);
                break;
            case 21:
            case '0':
                r7 = new BluetoothAction(context, bundle);
                break;
            case 22:
                r7 = new LockScreenAction(context, bundle);
                break;
            case 23:
                ?? soundAction = new SoundAction(context, bundle);
                soundAction.mAudioManager = null;
                getJumpToAppResultAction = soundAction;
                r7 = getJumpToAppResultAction;
                break;
            case 24:
                r7 = new UserManualAction(context, bundle);
                break;
            case 25:
                r7 = new ResetSettingsAction(context, bundle);
                break;
            case 26:
                r7 = new AboutDeviceAction(context, bundle);
                break;
            case 27:
                r7 = new ScreenZoomAction(context, bundle);
                break;
            case 28:
                r7 = new NfcAction(context, bundle);
                break;
            case 29:
                r7 = new AppsAction(context, bundle);
                break;
            case 30:
                r7 = new HardPressAction(context, bundle);
                break;
            case 31:
                bundle.putString("target", str2);
                r7 = new WifiSettingsAction(context, bundle);
                break;
            case ' ':
                r7 = new SendSOSMessageAction(context, bundle);
                break;
            case '!':
                r7 = new StatusBarAction(context, bundle);
                break;
            case '\"':
                r7 = new WifiSettingsAction(context, bundle);
                break;
            case '#':
                r7 = new WifiP2pAction(context, bundle);
                r7.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
                break;
            case '$':
                r7 = new ScreenTimeoutAction(context, bundle);
                break;
            case '%':
                r7 = new SimStatusAction(context, bundle);
                break;
            case '&':
                r7 = new HotspotAction(context, bundle);
                r7.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
                r7.mSemWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
                r7.mUserManager = (UserManager) context.getSystemService("user");
                break;
            case '\'':
                r7 = new NotificationSoundsAction(context, bundle);
                break;
            case '(':
                r7 = new NotificationReminderAction(context, bundle);
                break;
            case ')':
                r7 = new GeneralManagementAction(context, bundle);
                break;
            case '*':
                r7 = new FaceRecognitionAction(context, bundle);
                break;
            case '+':
                r7 = new FingerSensorGestureAction(context, bundle);
                break;
            case ',':
                r7 = new MusicShareAction(context, bundle);
                BluetoothAdapter.getDefaultAdapter();
                break;
            case '-':
                r7 = new DataUsage(context, bundle);
                break;
            case '.':
                r7 = new GotoJumpToAppMenuAction(context, bundle);
                break;
            case '/':
                r7 = new DisplayAction(context, bundle);
                break;
            case '1':
                r7 = new SmartCaptureAction(context, bundle);
                break;
        }
        if (r7 == 0) {
            SemLog.w("ActionCommand", "fail to create action");
            return createResult("fail");
        }
        str.getClass();
        switch (str) {
            case "change":
                createResult = r7.doChangeAction();
                break;
            case "enable":
                try {
                    createResult = Action.createResult("fail");
                    break;
                } catch (ActivityNotFoundException e) {
                    createResult = createResult("temporary_unavailable");
                    SemLog.e("ActionCommand", e.getMessage());
                    break;
                }
            case "available":
                createResult = r7.doSupportAction();
                break;
            case "supported":
                createResult = r7.doSupportAction();
                break;
            case "get":
                createResult = r7.doGetAction();
                break;
            case "set":
                createResult = r7.doSetAction();
                break;
            case "goto":
            case "open":
                try {
                    createResult = r7.doGotoAction();
                    break;
                } catch (ActivityNotFoundException e2) {
                    createResult = createResult("temporary_unavailable");
                    SemLog.e("ActionCommand", e2.getMessage());
                    break;
                }
            case "try_to_disconnect":
                createResult = r7.doTryToDisconnectAction();
                break;
            case "try_to_connect":
                createResult = r7.doTryToConnectAction();
                break;
            case "connect":
                createResult = r7.doConnectAction();
                break;
            case "get_entry_list":
                createResult = r7.doGetEntryListAction();
                break;
            case "disable":
                try {
                    createResult = r7.doDisableAction();
                    break;
                } catch (ActivityNotFoundException e3) {
                    createResult = createResult("temporary_unavailable");
                    SemLog.e("ActionCommand", e3.getMessage());
                    break;
                }
            default:
                createResult = createResult("not_supported");
                break;
        }
        String string = createResult.getString("result");
        Log.i("ActionCommand", "action result : " + string);
        if (TextUtils.equals(string, "not_supported")) {
            BixbyUtils.startSettingsMain(this.mContext);
        } else if (TextUtils.equals(string, "temporary_unavailable")) {
            BixbyUtils.startSettingsMain(this.mContext);
        }
        return createResult;
    }
}
