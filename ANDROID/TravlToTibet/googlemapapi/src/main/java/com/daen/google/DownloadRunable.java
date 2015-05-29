package com.daen.google;

/**
 * Created by 95 on 2015/5/29.
 */
public class DownloadRunable implements Runnable {

    private String URL;

    private DownloadCallback mCallbak;

    private String result;

    public DownloadRunable(String url, DownloadCallback callbak) {
        this.URL = url;
        this.mCallbak = callbak;
    }

    public static interface DownloadCallback {
        public void downloadSuccess(String result);
        public void downloadFaild();
    }

    @Override
    public void run() {
        try {
            result = HttpHelper.sendPost(URL);
            mCallbak.downloadSuccess(result);
        } catch (Exception e) {
            mCallbak.downloadFaild();
            e.printStackTrace();
        }
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public DownloadCallback getCallbak() {
        return mCallbak;
    }

    public void setCallbak(DownloadCallback mCallbak) {
        this.mCallbak = mCallbak;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
