package com.ximalaya.m3u8.node.encode;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

//import com.nali.common.util.StringUtil;
//import com.ximalaya.audio.ffmpeg.BaseCommandOption;

/**
 * 转码参数，ffmpeg参数
 * 
 * @author rick
 * @version 2012-6-11
 */
public class TranscodeOption {

    public static final String bitrateUnit = "k";

    public static final String defaultCmd = "cmd";

    private boolean isOverWrite = true;

    private boolean isVn = true;

    private boolean isTrans = true;

    private String ffmpegPath;

    private String sourceFile;

    private String targetFile;

    private String codec;

    private String bitRate;

    private String sampling;

    private String tracks;

    private String format;

    public TranscodeOption() {
    }

    public TranscodeOption(String ffmpegPath) {
        this.setFfmpegPath(ffmpegPath);
    }

    /**
     * ffmpeg 生成命令
     */
    public String toString() {
        StringBuilder bf = new StringBuilder();
        if (isTrans) {
            bf.append(defaultCmd).append(" /c ");
        }
        bf.append(ffmpegPath).append("ffmpeg ");
        if (StringUtils.isEmpty(getSourceFile())) {
            throw new IllegalArgumentException("we need a input audio file");
        }
        // 生成的文件是否覆盖已存在文件
        if (isOverWrite()) {
            bf.append(" -y ");
        }
        // 输入文件
        bf.append(" -i \"").append(getSourceFile()).append("\"");
        // bf.append(" -i ").append(getSourceFile());
        if (isVn) {
            bf.append(" -vn ");
        }
        // 编码模式
        if (StringUtils.isNotEmpty(getCodec())) {
            bf.append(" -codec ").append(getCodec());
        }
        // 比特率
        if (StringUtils.isNotEmpty(getBitRate())) {
            bf.append(" -ab ").append(getBitRate() + bitrateUnit);
        }
        // 采样率
        if (StringUtils.isNotEmpty(getSampling())) {
            bf.append(" -ar ").append(getSampling());
        }
        // 声道数
        if (StringUtils.isNotEmpty(getTracks())) {
            bf.append(" -ac ").append(getTracks());
        }
        // 格式
        if (StringUtils.isNotEmpty(getFormat())) {
            bf.append(" -f ").append(getFormat());
        }
        if (StringUtils.isNotEmpty(getTargetFile())) {
            bf.append(" -> ").append(" \"").append(getTargetFile()).append("\"");//
        }
        return bf.toString();
    }

    /**
     * 生成命令行参数组
     */
    public String[] toCmdArray() {
        List<String> list = new ArrayList<String>();
        if (isTrans) {
            list.add(defaultCmd);
            list.add(BaseCommandOption.WINCMDOP);
        }
        list.add((StringUtils.isEmpty(ffmpegPath) ? "" : ffmpegPath) + "ffmpeg");
        if (StringUtils.isEmpty(getSourceFile())) {
            throw new IllegalArgumentException("we need a input audio file");
        }
        // 生成的文件是否覆盖已存在文件
        if (isOverWrite()) {
            list.add(BaseCommandOption.Y);
        }
        // 输入文件
        list.add(BaseCommandOption.INPUT);
        list.add(getSourceFile());
        if (isVn) {
            list.add(BaseCommandOption.NOVIDEO);
        }
        // 编码模式
        if (StringUtils.isNotEmpty(getCodec())) {
            list.add(BaseCommandOption.ACODEC);
            list.add(getCodec());
        }
        // 比特率
        if (StringUtils.isNotEmpty(getBitRate())) {
            list.add(BaseCommandOption.AB);
            list.add(getBitRate() + bitrateUnit);
        }
        // 采样率
        if (StringUtils.isNotEmpty(getSampling())) {
            list.add(BaseCommandOption.AR);
            list.add(getSampling());
        }
        // 声道数
        if (StringUtils.isNotEmpty(getTracks())) {
            list.add(BaseCommandOption.AC);
            list.add(getTracks());
        }
        // 格式
        if (StringUtils.isNotEmpty(getFormat())) {
            list.add(BaseCommandOption.FORMAT);
            list.add(getFormat());
        }
        if (StringUtils.isNotEmpty(getTargetFile())) {
            list.add(BaseCommandOption.OUT_SYMBOL);
            list.add(getTargetFile());
        }
        String[] options = new String[list.size()];
        return list.toArray(options);
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public String getSampling() {
        return sampling;
    }

    public void setSampling(String sampling) {
        this.sampling = sampling;
    }

    public boolean isOverWrite() {
        return isOverWrite;
    }

    public void setOverWrite(boolean isOverWrite) {
        this.isOverWrite = isOverWrite;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public String getTracks() {
        return tracks;
    }

    public void setTracks(String tracks) {
        this.tracks = tracks;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFfmpegPath() {
        return ffmpegPath;
    }

    public void setFfmpegPath(String ffmpegPath) {
        this.ffmpegPath = ffmpegPath;
    }

    public boolean isTrans() {
        return isTrans;
    }

    public void setTrans(boolean isTrans) {
        this.isTrans = isTrans;
    }

}
