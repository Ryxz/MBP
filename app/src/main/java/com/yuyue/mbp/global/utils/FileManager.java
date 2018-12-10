package com.yuyue.mbp.global.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.yuyue.mbp.R;
import com.yuyue.mbp.global.GlobalContext;

import junit.framework.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Renyx on 2018/6/8
 */
public class FileManager {
    private static final String TAG = FileManager.class.getSimpleName();

    private static final String LOG = "log";
    private static final String BACKUP_DB = "DbBackup";
    private static final String BACKUP_PREF = "PrefBackup";
    private static final String APP = "MBP";
    private static final String EXPORT = "export";
    private static final String DOCUMENT_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) +
            File.separator + APP;
    private static final String DATABASES = "databases";
    private static final String PREFERENCES = "shared_prefs";

    public static final String DAILY = "daily";
    public static final String HISTORY = "history";

    private static volatile boolean cantReadBecauseOfAndroidBugPermissionProblem = false;

    public static boolean isExternalStorageMounted() {

        boolean canRead = Environment.getExternalStorageDirectory().canRead();
        boolean onlyRead = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
        boolean unMounted = Environment.getExternalStorageState().equals(Environment.MEDIA_UNMOUNTED);

        return !(!canRead || onlyRead || unMounted);
    }

    public static String getSdCardPath() {
        if (isExternalStorageMounted()) {
            File path = GlobalContext.getInstance().getExternalCacheDir();
            if (path != null) {
                return path.getAbsolutePath();
            } else {
                if (!cantReadBecauseOfAndroidBugPermissionProblem) {
                    cantReadBecauseOfAndroidBugPermissionProblem = true;
                    final Activity activity = GlobalContext.getInstance().getActivity();
                    if (activity == null || activity.isFinishing()) {
                        GlobalContext.getInstance().getUIHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(GlobalContext.getInstance(), R.string.please_deleted_cache_dir, Toast.LENGTH_SHORT).show();
                            }
                        });

                        return "";
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(activity)
                                    .setTitle(R.string.something_wrong)
                                    .setMessage(R.string.please_deleted_cache_dir)
                                    .setPositiveButton(R.string.ok,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {

                                                }
                                            })
                                    .show();

                        }
                    });
                }
            }
        } else {
            return "";
        }

        return "";
    }

    private static String mkdirsIfNotExist(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                return path;
            } else {
                return "";
            }
        }
        return path;
    }

    public static String getLogDir() {
        if (!isExternalStorageMounted()) {
            return "";
        } else {
            String path = getSdCardPath() + File.separator + LOG;
            return mkdirsIfNotExist(path);
        }
    }

    private static String getExportDir() {
        if (!isExternalStorageMounted()) {
            return "";
        } else {
            String path = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + APP + File.separator + EXPORT;
            return mkdirsIfNotExist(path);
        }
    }

    public static String getExportDir(String type) {
        if (!isExternalStorageMounted()) {
            return "";
        } else {
            String path = getExportDir() + File.separator + type;
            return mkdirsIfNotExist(path);
        }
    }

    public static boolean backupDatabase() {
        String dbDir = getDataDir(DATABASES);
        String toDir = getBackupDir(BACKUP_DB);

        boolean flag = false;
        if (!TextUtils.isEmpty(toDir)) {
            flag = copyDir(dbDir, toDir, ".backup");
        }

        return flag;
    }

    public static boolean restoreDatabase() {
        boolean flag = false;

        String dbDir = getDataDir(DATABASES);
        Assert.assertTrue(deleteFilesUnderDir(dbDir));

        flag = copyDir(getLatestBackupDir(BACKUP_DB), dbDir, "");
        Assert.assertTrue(flag);
        File[] dbDirFiles = new File(dbDir).listFiles();
        Assert.assertNotNull(dbDirFiles);
        for (File file : dbDirFiles) {
            flag = file.renameTo(new File(dbDir + File.separator + file.getName().replace(".backup", "")));
        }

        return flag;
    }

    public static boolean backupPreferences() {
        ApplicationInfo mApplicationInfo = GlobalContext.getInstance().getApplicationInfo();
        String prefFile = getDataDir(PREFERENCES) + File.separator + mApplicationInfo.packageName + "_preferences.xml";
        String destFile = getBackupDir(BACKUP_PREF) + File.separator + mApplicationInfo.packageName + "_preferences.backup";
        return copyFileUsingFileChannel(prefFile, destFile);
    }

    public static boolean restorePreferences() {
        ApplicationInfo mApplicationInfo = GlobalContext.getInstance().getApplicationInfo();

        String prefDir = getDataDir(PREFERENCES);
        Assert.assertTrue(deleteFilesUnderDir(prefDir));

        String backFile = getLatestBackupDir(BACKUP_PREF) + File.separator + mApplicationInfo.packageName +
                "_preferences.backup";
        String destFile = prefDir + File.separator + mApplicationInfo.packageName + "_preferences.xml";

        return copyFileUsingFileChannel(backFile, destFile);
    }

    private static String getDataDir(String type) {
        return GlobalContext.getInstance().getApplicationInfo().dataDir + File.separator + type;
    }

    private static boolean deleteFilesUnderDir(String dirPath) {
        boolean flag = false;
        File dbDir = new File(dirPath);
        Assert.assertTrue(dbDir.isDirectory());
        File[] dbDirFiles = dbDir.listFiles();
        if (dbDirFiles != null) {
            for (File file : dbDirFiles) {
                flag = file.delete();
            }
        }
        return flag;
    }

    private static String getLatestBackupDir(String type) {
        File dir = new File(DOCUMENT_DIR + File.separator + type);
        File[] files = dir.listFiles();
        Assert.assertNotNull(files);
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                long result = rhs.lastModified() - lhs.lastModified();
                if (result > 0) {
                    return 1;
                } else if (result < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        return files[0].getAbsolutePath();
    }

    private static String getBackupDir(String folderName) {
        if (!isExternalStorageMounted()) {
            return "";
        } else {
            String path = DOCUMENT_DIR + File.separator + folderName + File.separator +  new Date().getTime();
            return mkdirsIfNotExist(path);
        }
    }

    private static boolean copyDir(String fromDir, String toDir, String suffix) {
        boolean flag = false;

        File from = new File(fromDir);
        Assert.assertTrue(from.isDirectory());

        File[] files = from.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                String path = toDir + File.separator + file.getName();
                mkdirsIfNotExist(path);
                flag = copyDir(file.getAbsolutePath(), path, suffix);
            } else {
                flag = copyFileUsingFileChannel(file.getAbsolutePath(), toDir + File.separator + file.getName() + suffix);
            }
        }

        return flag;
    }

    private static void copyFile(String from, String to) {
        File fromFile = new File(from);
        File toFile = new File(to);

        Assert.assertTrue(fromFile.exists());

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(fromFile);
            fos = new FileOutputStream(toFile);

            byte[] buffer = new byte[1024 * 4];
            int length;

            while ((length = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean copyFileUsingFileChannel(String from, String to) {
        boolean flag = false;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fcIn = null;
        FileChannel fcOut = null;

        try {
            fis = new FileInputStream(from);
            fos = new FileOutputStream(to);
            fcIn = fis.getChannel();
            fcOut = fos.getChannel();

            fcIn.transferTo(0, fcIn.size(), fcOut);

            flag = true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            flag = false;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                fcIn.close();
                fis.close();
                fcOut.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
