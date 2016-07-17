package com.kklv.bmoe.diskLruCache;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;
import com.kklv.bmoe.object.BingImageSearchResult;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/15 10:10
 */
public class DiskLruCacheHelper {
    private static final String CACHE_NAME = "imageUrls";
    private static final String TAG = "DiskLruCacheHelper";

    private static DiskLruCache mDiskLruCache;
    private static DiskLruCacheHelper instance=null;
    private DiskLruCacheHelper(Context context){
        getDiskLruCache(context);
    }

    public static DiskLruCacheHelper getInstance(Context context){
        if(instance == null){
            instance=new DiskLruCacheHelper(context);
        }
        return instance;
    }
    private static void getDiskLruCache(Context context) {
        if(mDiskLruCache == null){
            File cacheDir = getDiskCacheDir(context, CACHE_NAME);
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            }
            try {
                mDiskLruCache = DiskLruCache.open(cacheDir, BingImageSearchResult.VERSION_CODE,
                        1, 10 * 1024 * 1024);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取Disk存储路径
     * @param context
     * @param uniqueName
     * @return
     */
    private static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        Log.i(TAG,"存储路径："+cachePath + File.separator + uniqueName);
        return new File(cachePath + File.separator + uniqueName);
    }

    private static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 将图片搜索结果写入Disk
     * @param keywords
     * @param response
     */
    public void writeBingImageSearchResult2Disk(String keywords, BingImageSearchResult response) {
        if(TextUtils.isEmpty(keywords)){
            keywords=response.getKey();
        }
        response.setKey(keywords);
        String key = hashKeyForDisk(keywords);

        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(response);
                editor.commit();
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从Disk读取图片搜索结果
     * @param keywords
     * @return
     */
    public BingImageSearchResult readBingImageSearchResultFromDisk(String keywords) {
        BingImageSearchResult result = null;
        String key = hashKeyForDisk(keywords);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                result = (BingImageSearchResult) objectInputStream.readObject();
                if (result != null && result.getValue().size() > 0) {
                    Log.i("kklv", "contentUrl:" + result.getValue().get(0).getContentUrl());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
