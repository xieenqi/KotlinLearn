package com.loyo.oa.v2.activityui.commonview;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Environment;
import android.os.Handler;

import com.loyo.oa.v2.R;
import com.loyo.oa.v2.application.MainApp;
import com.loyo.oa.v2.common.Global;
import com.loyo.oa.v2.tool.LogUtil;

/**
 * Created by xeq on 16/11/11.
 */

public class RecordUtils {

    //    private static RecordUtils mInstance;
    private MediaRecorder recorder;
    private Context context;
    private MediaPlayer play;
    private String AUDIO_ROOTPATH, outPath, fileName;//录音存放路径、输出路径、输出文件名字
    private boolean isStart;
    private long startTime, endTime;
    Handler handler = new Handler();
    CallbackMicStatus callbackMicStatus;
    Timer timer;
    TimerTask task;

    private RecordUtils() {
    }

    public static RecordUtils getInstance(Context context) {
//        if (mInstance == null) {
        RecordUtils mInstance = new RecordUtils();
//        }
        mInstance.setContext(context);
        return mInstance;
    }

    /**
     * 初始化录音
     */
    public void initStaratRecord() {
        recorder = new MediaRecorder();
//		recorder.setAudioChannels(numChannels);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Global.Toast("储存卡不可用");
            return;
        }
        File ff = new File(AUDIO_ROOTPATH);
        if (!ff.exists()) {
            ff.mkdirs();
        }
        fileName = getDate() + ".aac";
        outPath = AUDIO_ROOTPATH + File.separator + fileName;
        recorder.setOutputFile(outPath);
         /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
			 * THREE_GPP(3gp格式，H263视频
			 * /ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
			 * recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
			 */
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setMaxDuration(60 * 1000);
        recorder.setOnErrorListener(null);
        recorder.setOnInfoListener(null);
        recorder.setPreviewDisplay(null);
        startRecord();
        LogUtil.d("本地录音文件名:  " + fileName);
    }

    /**
     * 开始录音
     */
    public void startRecord() {
        try {
            isStart = true;
            recorder.prepare();
            recorder.start();
            LogUtil.d("开始 前 时间:" + startTime);
            startTime = System.currentTimeMillis();
            LogUtil.d("开始 后 时间:" + startTime);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateMicStatus();
                    }
                });
            }
        };
        timer.schedule(task, 500, 500);
    }

    public void stopRecord() {
        try {
            if (recorder != null && isStart) {
                timer.cancel();
                task.cancel();
                LogUtil.d("结束 前 时间:" + endTime);
                endTime = System.currentTimeMillis();
                LogUtil.d("结束 后 时间:" + endTime);
                isStart = false;
                recorder.stop();
                recorder.reset();
                recorder.release();
                recorder = null;//这个必须有不然录音设备释放不成功
                startTime = 0;

            }
        } catch (IllegalStateException e) {
            LogUtil.d("录音停止异常 可能没有权限");
        } catch (RuntimeException e) {
            LogUtil.d("录音运行异常");
        }

    }


    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    public void releasaeFile(String Path) {
        if (Path == null) {
            return;
        }
        File f = new File(Path);
        if (f.exists()) {
            f.delete();
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getAUDIO_ROOTPATH() {
        return AUDIO_ROOTPATH;
    }

    public void setAUDIO_ROOTPATH(String aUDIO_ROOTPATH) {
        AUDIO_ROOTPATH = aUDIO_ROOTPATH;
    }


    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setCallbackMicStatus(CallbackMicStatus callbackMicStatus) {
        this.callbackMicStatus = callbackMicStatus;
    }

    public MediaPlayer voicePlay(String playPath) {
        clean_play();
        play = new MediaPlayer();
        try {
            play.setDataSource(playPath);
            play.prepare();
            play.start();
            play.prepareAsync();
//            play.setOnCompletionListener(Completion);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return play;
    }

    private MediaPlayer.OnCompletionListener Completion = new
            MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                }
            };

    //	private boolean scalea(float x,float y){
//		float bx=recorder_bu.getLeft()+x;
//		float by=recorder_bu.getTop()+y;
//		RectF rectf=new RectF(img.getLeft(),img.getTop(),img.getRight(),img.getBottom());
//		return rectf.contains(bx, by);
//	}
    public void clean_play() {
        if (play != null) {
            play.stop();
            play.reset();
            play.release();
            play = null;
        }
    }

    public String getFormat(long time) {
        SimpleDateFormat fromat = new SimpleDateFormat("ss", Locale.CHINA);
        return fromat.format(new Date(time));
    }

    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        return format.format(new Date());
    }


    /**
     * 更新话筒状态
     */
    private int BASE = 1;

    private void updateMicStatus() {
        if (recorder != null) {
            double ratio = (double) recorder.getMaxAmplitude() / BASE;
            double db = 0;// 分贝
            if (ratio > 1)
                db = 20 * Math.log10(ratio);
            LogUtil.d("分贝值：" + db);
            callbackMicStatus.setMicData(db);
        }
    }

    interface CallbackMicStatus {
        void setMicData(double db);
    }

    /**
     * 用户是否配置 录音权限     * @return
     */
    public static boolean permissionRecord() {
        if (PackageManager.PERMISSION_GRANTED == MainApp.getMainApp().getPackageManager().checkPermission("android.permission.RECORD_AUDIO", "com.loyo.oa.v2")
                && PackageManager.PERMISSION_GRANTED == MainApp.getMainApp().getPackageManager().checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", "com.loyo.oa.v2")) {
            return true;
        }
        return false;
    }

    /**
     * 播放提示音 录音准备完成
     */
    private void playPrompt() {
        SoundPool sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//        sp.load(context,R.raw.10, 1);
        try {
            context.getAssets().list("10.wav");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
