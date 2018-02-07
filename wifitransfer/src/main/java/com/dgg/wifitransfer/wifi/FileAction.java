package com.dgg.wifitransfer.wifi;

/**
 * Created by qiqi on 18/1/25.
 * 操作文件  动作的参数
 */

public class FileAction {
    public static final int ACTION_TYPE_DELETE = 1;//删除文件
    public static final int ACTION_TYPE_UPLOAD = 2;//上传文件
    public static final int ACTION_TYPE_DOWNLOAD = 3;//下载文件

    public int actionType;
    public String filePath;

    public FileAction(int actionType, String filePath) {
        this.actionType = actionType;
        this.filePath = filePath;
    }

    /*获取文件的路径*/
    public String getFilePath() {
        return Constants.DIR + "/" + filePath;
    }
}
