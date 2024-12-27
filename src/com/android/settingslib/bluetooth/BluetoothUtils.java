package com.android.settingslib.bluetooth;

import android.R;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.bluetooth.BluetoothUuid;
import android.bluetooth.SemBluetoothUuid;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settingslib.widget.AdaptiveIcon;
import com.android.settingslib.widget.AdaptiveOutlineDrawable;

import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import com.samsung.android.settingslib.bluetooth.BluetoothRestoredDevice;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.sec.ims.configuration.DATA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BluetoothUtils {
    public static final String[] BD_ROTATE_RIGHT = null;
    public static boolean mDexQuickPannelOn;
    public static boolean mQuickPannelOn;
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final String[] BD_ROTATE_LEFT = {
        "00",
        "02",
        "04",
        "06",
        "08",
        "0A",
        "0C",
        "0E",
        DATA.DM_FIELD_INDEX.SMS_OVER_IMS,
        DATA.DM_FIELD_INDEX.SIP_T1_TIMER,
        DATA.DM_FIELD_INDEX.SIP_T4_TIMER,
        DATA.DM_FIELD_INDEX.SIP_TB_TIMER,
        DATA.DM_FIELD_INDEX.SIP_TD_TIMER,
        "1A",
        "1C",
        "1E",
        DATA.DM_FIELD_INDEX.SIP_TF_TIMER,
        DATA.DM_FIELD_INDEX.SIP_TH_TIMER,
        DATA.DM_FIELD_INDEX.SIP_TJ_TIMER,
        DATA.DM_FIELD_INDEX.CAP_CACHE_EXP,
        DATA.DM_FIELD_INDEX.SRC_THROTTLE_PUBLISH,
        "2A",
        "2C",
        "2E",
        DATA.DM_FIELD_INDEX.LVC_BETA_SETTING,
        DATA.DM_FIELD_INDEX.AVAIL_CACHE_EXP,
        DATA.DM_FIELD_INDEX.FQDN_FOR_PCSCF,
        DATA.DM_FIELD_INDEX.PUBLISH_TIMER,
        DATA.DM_FIELD_INDEX.GZIP_FLAG,
        "3A",
        "3C",
        "3E",
        DATA.DM_FIELD_INDEX.T_DELAY,
        DATA.DM_FIELD_INDEX.MIN_SE,
        DATA.DM_FIELD_INDEX.SILENT_REDIAL_ENABLE,
        DATA.DM_FIELD_INDEX.PUBLISH_ERR_RETRY_TIMER,
        DATA.DM_FIELD_INDEX.RINGING_TIMER,
        "4A",
        "4C",
        "4E",
        DATA.DM_FIELD_INDEX.RTP_RTCP_TIMER,
        DATA.DM_FIELD_INDEX.URI_MEDIA_RSC_SERV_3WAY_CALL,
        DATA.DM_FIELD_INDEX.CAP_DISCOVERY,
        DATA.DM_FIELD_INDEX.SRC_AMR,
        DATA.DM_FIELD_INDEX.HD_VOICE,
        "5A",
        "5C",
        "5E",
        DATA.DM_FIELD_INDEX.AUDIO_RTP_PORT_START,
        DATA.DM_FIELD_INDEX.VIDEO_RTP_PORT_START,
        DATA.DM_FIELD_INDEX.AMR_WB_OCTET_ALIGNED,
        DATA.DM_FIELD_INDEX.AMR_OCTET_ALIGNED,
        DATA.DM_FIELD_INDEX.H264_VGA,
        "6A",
        "6C",
        "6E",
        DATA.DM_FIELD_INDEX.DTMF_WB,
        DATA.DM_FIELD_INDEX.VOLTE_PREF_SERVICE_STATUS,
        DATA.DM_FIELD_INDEX.DM_APP_ID,
        DATA.DM_FIELD_INDEX.DM_CON_REF,
        DATA.DM_FIELD_INDEX.ICSI,
        "7A",
        "7C",
        "7E",
        DATA.DM_FIELD_INDEX.RSC_ALLOC_MODE,
        DATA.DM_FIELD_INDEX.VOICE_DOMAIN_PREF_UTRAN,
        DATA.DM_FIELD_INDEX.REG_RETRY_BASE_TIME,
        DATA.DM_FIELD_INDEX.PHONE_CONTEXT_PARAM,
        DATA.DM_FIELD_INDEX.SS_DOMAIN_SETTING,
        "8A",
        "8C",
        "8E",
        DATA.DM_FIELD_INDEX.DM_POLLING_PERIOD,
        DATA.DM_FIELD_INDEX.CONF_FACTORY_URI,
        DATA.DM_FIELD_INDEX.LVC_ENABLED,
        DATA.DM_FIELD_INDEX.VOLTE_ENABLED_BY_USER,
        DATA.DM_FIELD_INDEX.USSD_CONTROL_PREF,
        "9A",
        "9C",
        "9E",
        "A0",
        "A2",
        "A4",
        "A6",
        "A8",
        "AA",
        "AC",
        "AE",
        "B0",
        "B2",
        "B4",
        "B6",
        "B8",
        "BA",
        "BC",
        "BE",
        "C0",
        "C2",
        "C4",
        "C6",
        "C8",
        "CA",
        "CC",
        "CE",
        "D0",
        "D2",
        "D4",
        "D6",
        "D8",
        "DA",
        "DC",
        "DE",
        "E0",
        "E2",
        "E4",
        "E6",
        "E8",
        "EA",
        CertProvisionProfile.KEY_TYPE_EC,
        "EE",
        "F0",
        "F2",
        "F4",
        "F6",
        "F8",
        "FA",
        "FC",
        "FE",
        "01",
        "03",
        "05",
        "07",
        "09",
        "0B",
        "0D",
        "0F",
        DATA.DM_FIELD_INDEX.SMS_WRITE_UICC,
        DATA.DM_FIELD_INDEX.SIP_T2_TIMER,
        DATA.DM_FIELD_INDEX.SIP_TA_TIMER,
        DATA.DM_FIELD_INDEX.SIP_TC_TIMER,
        DATA.DM_FIELD_INDEX.SIP_TE_TIMER,
        "1B",
        PeripheralBarcodeConstants.Symbology.Type.TYPE_1D,
        "1F",
        DATA.DM_FIELD_INDEX.SIP_TG_TIMER,
        DATA.DM_FIELD_INDEX.SIP_TI_TIMER,
        DATA.DM_FIELD_INDEX.SIP_TK_TIMER,
        DATA.DM_FIELD_INDEX.CAP_POLL_INTERVAL,
        DATA.DM_FIELD_INDEX.SUBSCRIBE_MAX_ENTRY,
        "2B",
        PeripheralBarcodeConstants.Symbology.Type.TYPE_2D,
        "2F",
        DATA.DM_FIELD_INDEX.EAB_SETTING,
        DATA.DM_FIELD_INDEX.PREF_CSCF_PORT,
        DATA.DM_FIELD_INDEX.POLL_LIST_SUB_EXP,
        DATA.DM_FIELD_INDEX.PUBLISH_TIMER_EXTEND,
        DATA.DM_FIELD_INDEX.TIMER_VZW,
        "3B",
        "3D",
        "3F",
        DATA.DM_FIELD_INDEX.IMS_TEST_MODE,
        DATA.DM_FIELD_INDEX.DCN_NUMBER,
        DATA.DM_FIELD_INDEX.T_LTE_911_FAIL,
        DATA.DM_FIELD_INDEX.SPEAKER_DEFAULT_VIDEO,
        DATA.DM_FIELD_INDEX.RINGBACK_TIMER,
        "4B",
        "4D",
        "4F",
        DATA.DM_FIELD_INDEX.DOMAIN_PUI,
        "53",
        DATA.DM_FIELD_INDEX.AMR_WB,
        DATA.DM_FIELD_INDEX.SRC_AMR_WB,
        DATA.DM_FIELD_INDEX.UDP_KEEP_ALIVE,
        "5B",
        "5D",
        "5F",
        DATA.DM_FIELD_INDEX.AUDIO_RTP_PORT_END,
        DATA.DM_FIELD_INDEX.VIDEO_RTP_PORT_END,
        DATA.DM_FIELD_INDEX.AMR_WB_BANDWITH_EFFICIENT,
        DATA.DM_FIELD_INDEX.AMR_BANDWITH_EFFICIENT,
        DATA.DM_FIELD_INDEX.H264_QVGA,
        "6B",
        "6D",
        "6F",
        DATA.DM_FIELD_INDEX.DTMF_NB,
        DATA.DM_FIELD_INDEX.SMS_PSI,
        DATA.DM_FIELD_INDEX.DM_USER_DISP_NAME,
        DATA.DM_FIELD_INDEX.PDP_CONTEXT_PREF,
        DATA.DM_FIELD_INDEX.ICSI_RSC_ALLOC_MODE,
        "7B",
        "7D",
        "7F",
        DATA.DM_FIELD_INDEX.VOICE_DOMAIN_PREF_EUTRAN,
        DATA.DM_FIELD_INDEX.IMS_VOICE_TERMINATION,
        DATA.DM_FIELD_INDEX.REG_RETRY_MAX_TIME,
        DATA.DM_FIELD_INDEX.PHONE_CONTEXT_PUID,
        DATA.DM_FIELD_INDEX.SS_CONTROL_PREF,
        "8B",
        "8D",
        "8F",
        DATA.DM_FIELD_INDEX.ICCID,
        DATA.DM_FIELD_INDEX.VOLTE_ENABLED,
        DATA.DM_FIELD_INDEX.EAB_SETTING_BY_USER,
        DATA.DM_FIELD_INDEX.LVC_ENABLED_BY_USER,
        DATA.DM_FIELD_INDEX.EMERGENCY_CONTROL_PREF,
        "9B",
        "9D",
        "9F",
        "A1",
        "A3",
        "A5",
        "A7",
        "A9",
        "AB",
        "AD",
        "AF",
        "B1",
        "B3",
        "B5",
        "B7",
        "B9",
        "BB",
        "BD",
        "BF",
        "C1",
        "C3",
        "C5",
        "C7",
        "C9",
        "CB",
        "CD",
        "CF",
        "D1",
        "D3",
        "D5",
        "D7",
        "D9",
        "DB",
        "DD",
        "DF",
        "E1",
        "E3",
        "E5",
        "E7",
        "E9",
        "EB",
        "ED",
        "EF",
        "F1",
        "F3",
        "F5",
        "F7",
        "F9",
        "FB",
        "FD",
        "FF"
    };
    public static final AnonymousClass2 mOnInitCallback = new AnonymousClass2();

    public static boolean compareSameWithGear(String str, String str2) {
        byte[] bArr = new byte[6];
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            if (str.charAt(i) != ':') {
                bArr[i2] = (byte) Integer.parseInt(str.substring(i, i + 2), 16);
                i2++;
                i++;
            }
            i++;
        }
        if (!String.format(Locale.US, "%02X", Byte.valueOf((byte) (bArr[0] | 192)))
                .equals(str2.substring(0, 2))) {
            return false;
        }
        for (int i3 = 1; i3 < 6; i3++) {
            int i4 = i3 * 3;
            if (!BD_ROTATE_LEFT[bArr[i3] & 255].equals(str2.substring(i4, i4 + 2))) {
                return false;
            }
        }
        return true;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap createBitmap =
                Bitmap.createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public static List getA2dpBondDevices(
            Context context, LocalBluetoothProfileManager localBluetoothProfileManager) {
        ArrayList arrayList;
        Cursor query;
        ArrayList arrayList2 = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                try {
                    query =
                            context.getContentResolver()
                                    .query(
                                            Uri.parse(
                                                    "content://com.samsung.bt.btservice.btsettingsprovider/bonddevice"),
                                            null,
                                            "bond_state== 2",
                                            null,
                                            "timestamp DESC");
                    try {
                    } catch (Throwable th) {
                        th = th;
                        cursor = query;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                } catch (IllegalStateException e) {
                    e = e;
                    arrayList = arrayList2;
                }
            } catch (IllegalStateException e2) {
                e = e2;
                arrayList = arrayList2;
            }
            if (query != null) {
                arrayList = arrayList2;
                try {
                    Log.e(
                            "BluetoothUtils",
                            "getA2dpBondDevices() :: cursor count: "
                                    + query.getCount()
                                    + ", Columns : "
                                    + query.getColumnCount());
                    query.moveToFirst();
                    int columnIndex = query.getColumnIndex("address");
                    int columnIndex2 = query.getColumnIndex("name");
                    int columnIndex3 = query.getColumnIndex("cod");
                    int columnIndex4 = query.getColumnIndex("bond_state");
                    int columnIndex5 = query.getColumnIndex("appearance");
                    int columnIndex6 = query.getColumnIndex("manufacturerdata");
                    int columnIndex7 = query.getColumnIndex(PhoneRestrictionPolicy.TIMESTAMP);
                    int columnIndex8 = query.getColumnIndex("linktype");
                    int columnIndex9 = query.getColumnIndex("uuids");
                    while (!query.isAfterLast()) {
                        BluetoothRestoredDevice bluetoothRestoredDevice =
                                new BluetoothRestoredDevice(query.getString(columnIndex));
                        bluetoothRestoredDevice.mName = query.getString(columnIndex2);
                        bluetoothRestoredDevice.mCod = query.getInt(columnIndex3);
                        bluetoothRestoredDevice.mBondState = query.getInt(columnIndex4);
                        bluetoothRestoredDevice.mAppearance = query.getInt(columnIndex5);
                        bluetoothRestoredDevice.mManufacturerData =
                                stringToByte(query.getString(columnIndex6));
                        bluetoothRestoredDevice.mTimeStamp = query.getLong(columnIndex7);
                        bluetoothRestoredDevice.mLinkType = query.getInt(columnIndex8);
                        String string = query.getString(columnIndex9);
                        String[] stringToken = getStringToken(string);
                        if (stringToken != null) {
                            bluetoothRestoredDevice.mUuids = makeParcelUuids(stringToken);
                        }
                        String[] stringToken2 = getStringToken(string);
                        if (stringToken2 != null
                                && BluetoothUuid.containsAnyUuid(
                                        makeParcelUuids(stringToken2), A2dpProfile.SINK_UUIDS)) {
                            arrayList.add(
                                    new CachedBluetoothDevice(
                                            context,
                                            localBluetoothProfileManager,
                                            bluetoothRestoredDevice));
                            Log.d(
                                    "BluetoothUtils",
                                    "getA2dpBondDevices - " + query.getString(columnIndex2));
                        }
                        query.moveToNext();
                    }
                    query.close();
                } catch (IllegalStateException e3) {
                    e = e3;
                }
                return arrayList;
            }
            try {
                Log.e("BluetoothUtils", "getA2dpBondDevices() :: query return empty list");
                if (query != null) {
                    query.close();
                }
                return arrayList2;
            } catch (IllegalStateException e4) {
                e = e4;
                arrayList = arrayList2;
            }
            cursor = query;
            Log.e("BluetoothUtils", "getA2dpBondDevices :: Occurs IllegalStateException");
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean getBooleanMetaData(BluetoothDevice bluetoothDevice, int i) {
        byte[] metadata;
        if (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(i)) == null) {
            return false;
        }
        return Boolean.parseBoolean(new String(metadata));
    }

    public static Pair getBtClassDrawableWithDescription(
            Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothClass bluetoothClass = cachedBluetoothDevice.mDevice.getBluetoothClass();
        if (bluetoothClass != null) {
            int majorDeviceClass = bluetoothClass.getMajorDeviceClass();
            if (majorDeviceClass == 256) {
                return new Pair(
                        context.getDrawable(R.drawable.ic_clear_normal),
                        context.getString(
                                com.android.settings.R.string.bluetooth_talkback_computer));
            }
            if (majorDeviceClass == 512) {
                return new Pair(
                        context.getDrawable(R.drawable.ic_signal_cellular_2_5_bar),
                        context.getString(com.android.settings.R.string.bluetooth_talkback_phone));
            }
            if (majorDeviceClass == 1280) {
                return new Pair(
                        context.getDrawable(HidProfile.getHidClassDrawable(bluetoothClass)),
                        context.getString(
                                com.android.settings.R.string.bluetooth_talkback_input_peripheral));
            }
            if (majorDeviceClass == 1536) {
                return new Pair(
                        context.getDrawable(R.drawable.ic_voice_search_api_holo_dark),
                        context.getString(
                                com.android.settings.R.string.bluetooth_talkback_imaging));
            }
        }
        if (cachedBluetoothDevice.isHearingAidDevice()) {
            return new Pair(
                    context.getDrawable(R.drawable.ic_clear_mtrl_alpha),
                    context.getString(
                            com.android.settings.R.string.bluetooth_talkback_hearing_aids));
        }
        int i = 0;
        for (LocalBluetoothProfile localBluetoothProfile : cachedBluetoothDevice.getProfiles()) {
            int drawableResource = localBluetoothProfile.getDrawableResource(bluetoothClass);
            if (drawableResource != 0) {
                if ((localBluetoothProfile instanceof HearingAidProfile)
                        || (localBluetoothProfile instanceof HapClientProfile)) {
                    return new Pair(
                            context.getDrawable(drawableResource),
                            context.getString(
                                    com.android.settings.R.string.bluetooth_talkback_hearing_aids));
                }
                if (i == 0) {
                    i = drawableResource;
                }
            }
        }
        if (i != 0) {
            return new Pair(context.getDrawable(i), null);
        }
        if (bluetoothClass != null) {
            if (bluetoothClass.doesClassMatch(0)) {
                return new Pair(
                        context.getDrawable(R.drawable.ic_clear_material),
                        context.getString(
                                com.android.settings.R.string.bluetooth_talkback_headset));
            }
            if (bluetoothClass.doesClassMatch(1)) {
                return new Pair(
                        context.getDrawable(R.drawable.ic_clear_holo_light),
                        context.getString(
                                com.android.settings.R.string.bluetooth_talkback_headphone));
            }
        }
        return new Pair(
                context.getDrawable(R.drawable.ic_visibility).mutate(),
                context.getString(com.android.settings.R.string.bluetooth_talkback_bluetooth));
    }

    public static Pair getBtDrawableWithDescription(
            Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        Pair btClassDrawableWithDescription =
                getBtClassDrawableWithDescription(context, cachedBluetoothDevice);
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        int dimensionPixelSize =
                context.getResources()
                        .getDimensionPixelSize(com.android.settings.R.dimen.bt_nearby_icon_size);
        Resources resources = context.getResources();
        if (isAdvancedDetailsHeader(bluetoothDevice)) {
            String stringMetaData = getStringMetaData(bluetoothDevice, 5);
            Uri parse = stringMetaData == null ? null : Uri.parse(stringMetaData);
            if (parse != null) {
                try {
                    context.getContentResolver().takePersistableUriPermission(parse, 1);
                } catch (SecurityException e) {
                    Log.e(
                            "BluetoothUtils",
                            "Failed to take persistable permission for: " + parse,
                            e);
                }
                try {
                    Bitmap bitmap =
                            MediaStore.Images.Media.getBitmap(context.getContentResolver(), parse);
                    if (bitmap != null) {
                        Bitmap createScaledBitmap =
                                Bitmap.createScaledBitmap(
                                        bitmap, dimensionPixelSize, dimensionPixelSize, false);
                        bitmap.recycle();
                        return new Pair(
                                new BitmapDrawable(resources, createScaledBitmap),
                                (String) btClassDrawableWithDescription.second);
                    }
                } catch (IOException e2) {
                    Log.e("BluetoothUtils", "Failed to get drawable for: " + parse, e2);
                } catch (SecurityException e3) {
                    Log.e("BluetoothUtils", "Failed to get permission for: " + parse, e3);
                }
            }
        }
        return new Pair(
                (Drawable) btClassDrawableWithDescription.first,
                (String) btClassDrawableWithDescription.second);
    }

    public static Pair getBtRainbowDrawableWithDescription(
            Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        Resources resources = context.getResources();
        Pair btDrawableWithDescription =
                getBtDrawableWithDescription(context, cachedBluetoothDevice);
        if (btDrawableWithDescription.first instanceof BitmapDrawable) {
            return new Pair(
                    new AdaptiveOutlineDrawable(
                            resources,
                            ((BitmapDrawable) btDrawableWithDescription.first).getBitmap()),
                    (String) btDrawableWithDescription.second);
        }
        int i = cachedBluetoothDevice.mGroupId;
        int hashCode =
                i != -1
                        ? new Integer(i).hashCode()
                        : cachedBluetoothDevice.mDevice.getAddress().hashCode();
        Drawable drawable = (Drawable) btDrawableWithDescription.first;
        Resources resources2 = context.getResources();
        int[] intArray = resources2.getIntArray(com.android.settings.R.array.bt_icon_fg_colors);
        int[] intArray2 = resources2.getIntArray(com.android.settings.R.array.bt_icon_bg_colors);
        int abs = Math.abs(hashCode % intArray2.length);
        drawable.setTint(intArray[abs]);
        AdaptiveIcon adaptiveIcon = new AdaptiveIcon(context, drawable);
        adaptiveIcon.setBackgroundColor(intArray2[abs]);
        return new Pair(adaptiveIcon, (String) btDrawableWithDescription.second);
    }

    public static int getConnectionStateSummary(int i) {
        if (i == 0) {
            return com.android.settings.R.string.bluetooth_disconnected;
        }
        if (i == 1) {
            return com.android.settings.R.string.bluetooth_connecting;
        }
        if (i == 2) {
            return com.android.settings.R.string.bluetooth_connected;
        }
        if (i != 3) {
            return 0;
        }
        return com.android.settings.R.string.bluetooth_disconnecting;
    }

    public static String getControlUriMetaData(BluetoothDevice bluetoothDevice) {
        String stringMetaData = getStringMetaData(bluetoothDevice, 25);
        if (TextUtils.isEmpty(stringMetaData)) {
            return null;
        }
        Locale locale = Locale.ENGLISH;
        Matcher matcher =
                Pattern.compile(
                                "<HEARABLE_CONTROL_SLICE_WITH_WIDTH>(.*?)</HEARABLE_CONTROL_SLICE_WITH_WIDTH>")
                        .matcher(stringMetaData);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static CachedBluetoothDevice getDeviceForGroupConnectionState(
            CachedBluetoothDevice cachedBluetoothDevice) {
        int maxConnectionState = cachedBluetoothDevice.getMaxConnectionState();
        if (maxConnectionState == 2) {
            return cachedBluetoothDevice;
        }
        for (CachedBluetoothDevice cachedBluetoothDevice2 : cachedBluetoothDevice.mMemberDevices) {
            if (maxConnectionState != cachedBluetoothDevice2.getMaxConnectionState()) {
                int maxConnectionState2 = cachedBluetoothDevice.getMaxConnectionState();
                int maxConnectionState3 = cachedBluetoothDevice2.getMaxConnectionState();
                if (maxConnectionState2 == 0
                        ? maxConnectionState3 != 0
                        : !(maxConnectionState2 == 1
                                ? maxConnectionState3 != 2
                                : maxConnectionState2 != 3
                                        || (maxConnectionState3 != 1
                                                && maxConnectionState3 != 2))) {
                    cachedBluetoothDevice = cachedBluetoothDevice2;
                }
                if (cachedBluetoothDevice.getMaxConnectionState() == 2) {
                    break;
                }
            }
        }
        return cachedBluetoothDevice;
    }

    public static Drawable getHostOverlayIconDrawable(
            Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        LocalBluetoothManager localBluetoothManager;
        AudioCastProfile audioCastProfile;
        int color =
                "com.android.systemui".equals(context.getPackageName().toLowerCase())
                        ? context.getResources()
                                .getColor(
                                        com.android.settings.R.color
                                                .qs_detail_item_device_bt_icon_tint_color)
                        : context.getResources()
                                .getColor(com.android.settings.R.color.bt_device_icon_tint_color);
        if (cachedBluetoothDevice == null) {
            Log.d("BluetoothUtils", "getHostOverlayIconDrawable - cachedBluetoothDevice is null");
            Drawable drawable =
                    context.getResources()
                            .getDrawable(
                                    com.android.settings.R.drawable
                                            .list_ic_sound_accessory_default);
            drawable.setTint(color);
            return drawable;
        }
        Drawable iconDrawable = cachedBluetoothDevice.getIconDrawable();
        String address = cachedBluetoothDevice.mDevice.getAddress();
        if (SemBluetoothCastAdapter.isBluetoothCastSupported()
                && (localBluetoothManager =
                                LocalBluetoothManager.getInstance(context, mOnInitCallback))
                        != null
                && (audioCastProfile =
                                localBluetoothManager.mLocalCastProfileManager.mAudioCastProfile)
                        != null
                && Settings.Secure.getInt(
                                audioCastProfile.mContext.getContentResolver(),
                                "bluetooth_cast_mode",
                                1)
                        == 1) {
            SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
            List list =
                    (List)
                            (semBluetoothAudioCast == null
                                            ? new ArrayList()
                                            : semBluetoothAudioCast.getConnectedDevices())
                                    .stream()
                                            .filter(new BluetoothUtils$$ExternalSyntheticLambda1(2))
                                            .filter(
                                                    new BluetoothUtils$$ExternalSyntheticLambda3(
                                                            audioCastProfile, 0))
                                            .filter(new BluetoothUtils$$ExternalSyntheticLambda1(3))
                                            .map(new BluetoothUtils$$ExternalSyntheticLambda5())
                                            .collect(Collectors.toList());
            if (!list.isEmpty() && list.contains(address)) {
                return getOverlayIconTintableDrawable(
                        context,
                        iconDrawable,
                        com.android.settings.R.drawable.sharing_ic_overlay,
                        com.android.settings.R.drawable.sharing_ic_tintable);
            }
        }
        iconDrawable.setTint(color);
        return iconDrawable;
    }

    public static int getIntMetaData(BluetoothDevice bluetoothDevice, int i) {
        byte[] metadata;
        if (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(i)) == null) {
            return -1;
        }
        try {
            return Integer.parseInt(new String(metadata));
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public static Drawable getOverlayIconTintableDrawable(
            Context context, Drawable drawable, int i, int i2) {
        int color =
                "com.android.systemui".equals(context.getPackageName().toLowerCase())
                        ? context.getResources()
                                .getColor(
                                        com.android.settings.R.color
                                                .qs_detail_item_device_bt_icon_tint_color)
                        : context.getResources()
                                .getColor(com.android.settings.R.color.bt_device_icon_tint_color);
        drawable.setTint(color);
        Bitmap drawableToBitmap = drawableToBitmap(drawable);
        Bitmap drawableToBitmap2 = drawableToBitmap(context.getResources().getDrawable(i));
        Drawable drawable2 = context.getResources().getDrawable(i2);
        drawable2.setTint(color);
        Bitmap drawableToBitmap3 = drawableToBitmap(drawable2);
        Bitmap createBitmap =
                Bitmap.createBitmap(
                        drawableToBitmap.getWidth(),
                        drawableToBitmap.getHeight(),
                        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(drawableToBitmap, 0.0f, 0.0f, (Paint) null);
        Paint paint = new Paint();
        paint.setFilterBitmap(false);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawBitmap(drawableToBitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawBitmap(drawableToBitmap3, 0.0f, 0.0f, paint);
        paint.setXfermode(null);
        return new BitmapDrawable(context.getResources(), createBitmap);
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x015d, code lost:

       if (r3 == null) goto L50;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List getRestoredDevices(
            android.content.Context r25,
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r26,
            boolean r27) {
        /*
            Method dump skipped, instructions count: 381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.bluetooth.BluetoothUtils.getRestoredDevices(android.content.Context,"
                    + " com.android.settingslib.bluetooth.LocalBluetoothProfileManager,"
                    + " boolean):java.util.List");
    }

    public static String getStringFromByteArray(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x ", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }

    public static String getStringMetaData(BluetoothDevice bluetoothDevice, int i) {
        byte[] metadata;
        if (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(i)) == null) {
            return null;
        }
        return new String(metadata);
    }

    public static String[] getStringToken(String str) {
        if (str == null || "null".equalsIgnoreCase(str) || str.isEmpty()) {
            return null;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
        String[] strArr = new String[stringTokenizer.countTokens()];
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            strArr[i] = stringTokenizer.nextToken();
            i++;
        }
        return strArr;
    }

    public static String getTetheringErrorWithWifi(Context context) {
        return isTablet()
                ? context.getString(
                        com.android.settings.R.string
                                .bluetooth_tethering_error_with_wifi_tablet_summary)
                : context.getString(
                        com.android.settings.R.string
                                .bluetooth_tethering_error_with_wifi_phone_summary);
    }

    public static boolean hasConnectedBroadcastSource(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothManager localBluetoothManager) {
        if (localBluetoothManager == null) {
            Log.d(
                    "BluetoothUtils",
                    "Skip check hasConnectedBroadcastSource due to bt manager is null");
            return false;
        }
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                localBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant == null) {
            Log.d(
                    "BluetoothUtils",
                    "Skip check hasConnectedBroadcastSource due to assistant profile is null");
            return false;
        }
        List allSources =
                localBluetoothLeBroadcastAssistant.getAllSources(cachedBluetoothDevice.mDevice);
        if (!allSources.isEmpty()
                && allSources.stream().anyMatch(new BluetoothUtils$$ExternalSyntheticLambda1(0))) {
            Log.d(
                    "BluetoothUtils",
                    "Lead device has connected broadcast source, device = "
                            + cachedBluetoothDevice.mDevice.getAnonymizedAddress());
            return true;
        }
        for (CachedBluetoothDevice cachedBluetoothDevice2 : cachedBluetoothDevice.mMemberDevices) {
            List allSources2 =
                    localBluetoothLeBroadcastAssistant.getAllSources(
                            cachedBluetoothDevice2.mDevice);
            if (!allSources2.isEmpty()
                    && allSources2.stream()
                            .anyMatch(new BluetoothUtils$$ExternalSyntheticLambda1(0))) {
                Log.d(
                        "BluetoothUtils",
                        "Member device has connected broadcast source, device = "
                                + cachedBluetoothDevice2.mDevice.getAnonymizedAddress());
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0011, code lost:

       if (r3[r1 + 1] == 1) goto L11;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean hasGearManufacturerData(byte[] r3) {
        /*
            if (r3 == 0) goto L14
            int r0 = r3.length
            int r1 = android.bluetooth.BluetoothManufacturerData.OFFSET_OLD_DEVICE_ID
            int r2 = r1 + 2
            if (r0 < r2) goto L14
            r0 = r3[r1]
            if (r0 != 0) goto L14
            r0 = 1
            int r1 = r1 + r0
            r3 = r3[r1]
            if (r3 != r0) goto L14
            goto L15
        L14:
            r0 = 0
        L15:
            java.lang.String r3 = "hasGearManufacturerData : "
            java.lang.String r1 = "BluetoothUtils"
            com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0.m(r3, r1, r0)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.bluetooth.BluetoothUtils.hasGearManufacturerData(byte[]):boolean");
    }

    public static boolean isAdvancedDetailsHeader(BluetoothDevice bluetoothDevice) {
        if (!DeviceConfig.getBoolean("settings_ui", "bt_advanced_header_enabled", true)) {
            Log.d("BluetoothUtils", "isAdvancedDetailsHeader: advancedEnabled is false");
            return false;
        }
        if (getBooleanMetaData(bluetoothDevice, 6)) {
            Log.d("BluetoothUtils", "isAdvancedDetailsHeader: untetheredHeadset is true");
            return true;
        }
        String stringMetaData = getStringMetaData(bluetoothDevice, 5);
        if ((stringMetaData == null ? null : Uri.parse(stringMetaData)) == null) {
            return false;
        }
        Log.d("BluetoothUtils", "isAdvancedDetailsHeader is true with main icon uri");
        return true;
    }

    public static boolean isBlackListDevice(BluetoothDevice bluetoothDevice) {
        String name = bluetoothDevice.getName();
        String address = bluetoothDevice.getAddress();
        if (name == null || address == null) {
            return false;
        }
        return (Pattern.matches("(?i).*BMW.*", name)
                        && Pattern.matches(
                                "(?i).*A0:56:B2.*|(?i).*B8:24:10.*|(?i).*9C:DF:03.*|(?i).*00:19:C0.*",
                                address))
                || Pattern.matches("(?i)MINI[0-9].*", name);
    }

    public static boolean isConnected(
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        return bluetoothLeBroadcastReceiveState.getBisSyncState().stream()
                .anyMatch(new BluetoothUtils$$ExternalSyntheticLambda1(4));
    }

    public static boolean isEnabledUltraPowerSaving(Context context) {
        if ((SemFloatingFeature.getInstance()
                                .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE")
                        || SemFloatingFeature.getInstance()
                                .getBoolean(
                                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING")
                        || SemFloatingFeature.getInstance()
                                .getBoolean(
                                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_BATTERY_CONVERSING"))
                && SemEmergencyManager.getInstance(context) != null) {
            return SemEmergencyManager.isEmergencyMode(context);
        }
        return false;
    }

    public static boolean isExclusivelyManagedBluetoothDevice(
            Context context, BluetoothDevice bluetoothDevice) {
        String str;
        boolean z;
        byte[] metadata = bluetoothDevice.getMetadata(29);
        if (metadata == null) {
            Log.d(
                    "BluetoothUtils",
                    "Bluetooth device "
                            + bluetoothDevice.getName()
                            + " doesn't have exclusive manager");
            str = null;
        } else {
            str = new String(metadata);
        }
        if (str == null) {
            return false;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString != null) {
            str = unflattenFromString.getPackageName();
        }
        try {
            z = context.getPackageManager().getApplicationInfo(str, 0).enabled;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("BluetoothUtils", "Package " + str + " is not installed/enabled");
            z = false;
        }
        if (!z) {
            return false;
        }
        DialogFragment$$ExternalSyntheticOutline0.m(
                "Found exclusively managed app ", str, "BluetoothUtils");
        return true;
    }

    public static boolean isGalaxyWatchDevice(
            String str, BluetoothClass bluetoothClass, byte[] bArr, ParcelUuid[] parcelUuidArr) {
        int deviceClass = bluetoothClass == null ? 7936 : bluetoothClass.getDeviceClass();
        if (deviceClass == 7936 || parcelUuidArr == null) {
            return "SM-V700".equalsIgnoreCase(str)
                    || "Samsung Galaxy Gear".equalsIgnoreCase(str)
                    || str.toLowerCase().startsWith("galaxy gear")
                    || hasGearManufacturerData(bArr);
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                new StringBuilder("isGalaxyWatchDevice: uuids = "),
                Arrays.toString(parcelUuidArr),
                "BluetoothUtils");
        if (deviceClass == 1796) {
            return SemBluetoothUuid.isUuidPresent(
                            parcelUuidArr,
                            ParcelUuid.fromString("a49eb41e-cb06-495c-9f4f-bb80a90cdf00"))
                    || SemBluetoothUuid.isUuidPresent(
                            parcelUuidArr,
                            ParcelUuid.fromString("5e8945b0-9525-11e3-a5e2-0800200c9a66"))
                    || hasGearManufacturerData(bArr);
        }
        return false;
    }

    public static boolean isRTL(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 192) == 128;
    }

    public static boolean isSatelliteModeOn(Context context) {
        String string =
                Settings.Global.getString(context.getContentResolver(), "satellite_mode_radios");
        return string != null
                && string.contains("bluetooth")
                && Settings.Global.getInt(context.getContentResolver(), "satellite_mode_enabled", 0)
                        == 1;
    }

    public static boolean isSupportSmep(BluetoothDevice bluetoothDevice) {
        byte[] m;
        int i = Build.VERSION.SEM_PLATFORM_INT;
        if (i >= 120100) {
            return (bluetoothDevice == null
                            || (m =
                                            BluetoothUtils$$ExternalSyntheticOutline0.m(
                                                    SmepTag.SUPPORTED_FEATURES, bluetoothDevice))
                                    == null
                            || m.length < 1)
                    ? false
                    : true;
        }
        WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                .m(i, "isSupportSmep() OneUI version is low : ", "BluetoothUtils");
        return false;
    }

    public static boolean isSyncDevice(byte[] bArr, String str, ParcelUuid[] parcelUuidArr) {
        String[] stringToken;
        boolean z = DEBUG;
        if (bArr != null) {
            byte[] bArr2 = new ManufacturerData(bArr).mData.mDeviceId;
            int i = bArr2[1] & 255;
            byte b = bArr2[0];
            if (((b == 1 || b == 2 || b == 3) && i >= 1 && i <= 255)
                    || (b == 65 && i >= 1 && i <= 255)) {
                if (z) {
                    Log.d("BluetoothUtils", "isSyncDevice :: DeviceId");
                }
                return true;
            }
        }
        if (str != null && str.length() > 0 && (stringToken = getStringToken(str)) != null) {
            for (String str2 : stringToken) {
                if ("e7ab2241-ca64-4a69-ac02-05f5c6fe2d62".equals(str2)) {
                    if (z) {
                        Log.d("BluetoothUtils", "isSyncDevice :: UUID");
                    }
                    return true;
                }
            }
        }
        if (parcelUuidArr == null
                || !new HashSet(Arrays.asList(parcelUuidArr))
                        .contains(ParcelUuid.fromString("e7ab2241-ca64-4a69-ac02-05f5c6fe2d62"))) {
            if (z) {
                Log.d("BluetoothUtils", "isSyncDevice :: It is not synced device");
            }
            return false;
        }
        if (z) {
            Log.d("BluetoothUtils", "isSyncDevice :: UUID from pacelUUID");
        }
        return true;
    }

    public static boolean isTablet() {
        String str = SystemProperties.get("ro.build.characteristics");
        return str != null && str.contains("tablet");
    }

    public static ParcelUuid[] makeParcelUuids(String[] strArr) {
        ParcelUuid[] parcelUuidArr = new ParcelUuid[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            try {
                parcelUuidArr[i] = ParcelUuid.fromString(strArr[i]);
            } catch (Exception e) {
                Log.d("BluetoothUtils", "failed makeParcelUuids");
                e.printStackTrace();
            }
        }
        return parcelUuidArr;
    }

    public static void showToast(final Context context, final String str) {
        new Handler(Looper.getMainLooper())
                .postDelayed(
                        new Runnable() { // from class:
                                         // com.android.settingslib.bluetooth.BluetoothUtils.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Toast.makeText(
                                                new ContextThemeWrapper(
                                                        context,
                                                        R.style.Theme.DeviceDefault.Settings),
                                                str,
                                                0)
                                        .show();
                            }
                        },
                        0L);
    }

    public static void stopTethering(Context context) {
        ((ConnectivityManager) context.getSystemService("connectivity")).stopTethering(2);
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, mOnInitCallback);
        if (localBluetoothManager == null) {
            try {
                Thread.sleep(500L);
                return;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(100L);
                if (!localBluetoothManager.mProfileManager.mPanProfile.isNapEnabled()) {
                    return;
                }
            } catch (InterruptedException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public static byte[] stringToByte(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            try {
                bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
            } catch (NumberFormatException unused) {
                Log.d("BluetoothUtils", "stringToByte : Wrong format - ".concat(str));
                return null;
            }
        }
        return bArr;
    }

    public static void updateDeviceName(Context context) {
        LocalBluetoothAdapter localBluetoothAdapter = LocalBluetoothAdapter.getInstance();
        String stringForUser =
                Settings.System.getStringForUser(context.getContentResolver(), "device_name", -2);
        if (stringForUser == null) {
            stringForUser = Settings.Global.getString(context.getContentResolver(), "device_name");
        }
        if (localBluetoothAdapter == null
                || stringForUser == null
                || stringForUser.equals(localBluetoothAdapter.mAdapter.getName())) {
            return;
        }
        localBluetoothAdapter.mAdapter.setName(stringForUser);
        Log.d("BluetoothUtils", "updateDeviceName :: change device name to ".concat(stringForUser));
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.bluetooth.BluetoothUtils$2, reason: invalid class name */
    public final class AnonymousClass2 implements LocalBluetoothManager.BluetoothManagerCallback {
        @Override // com.android.settingslib.bluetooth.LocalBluetoothManager.BluetoothManagerCallback
        public final void onBluetoothManagerInitialized() {}
    }
}
