package com.ximalaya.m3u8.node.encode;

import org.apache.commons.lang.StringUtils;

//import com.nali.common.util.StringUtil;

/**
 * 用于linux系统中执行ffmpeg命令 shell: ffmpeg -y -i $1 -vn -ac $2 -ar $3 -ab $4 -codec $5 -f $6 -> $7 每个参数都有默认值，防止参数为空执行错误
 * 
 * @author rick
 * @version 2012-8-30
 */
public class LinuxFFmpegShell {

    public static String defaultShellPaht = "/usr/local/webserver/ffmpeg.sh";

    public final static String defaultTracks = "2";

    public final static String defaultSampling = "22050";

    public final static String defaultCodec = "libmp3lame";

    public final static String defaultFormat = "mp3";

    public static String[] toCmdArgs(String shellPath, TranscodeOption option) {
        if (null == option || StringUtils.isEmpty(option.getSourceFile().trim())
                || StringUtils.isEmpty(option.getTargetFile()) || StringUtils.isEmpty(option.getBitRate())) {
            throw new IllegalArgumentException("source file and target file path can't be null");
        }
        String[] args = new String[7];
        args[0] = shellPath;
        args[1] = option.getSourceFile();
        args[2] = StringUtils.isEmpty(option.getTracks().trim()) ? defaultTracks : option.getTracks();
        args[3] = StringUtils.isEmpty(option.getSampling().trim()) ? defaultSampling : option.getSampling();
        args[4] = option.getBitRate() + TranscodeOption.bitrateUnit;
//		args[5] = StringUtil.isEmpty(option.getCodec()) ? defaultCodec : option.getCodec();
        args[5] = StringUtils.isEmpty(option.getFormat().trim()) ? defaultFormat : option.getFormat();
        args[6] = option.getTargetFile();
        return args;
    }
}
