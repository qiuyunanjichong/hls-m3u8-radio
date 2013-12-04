package com.ximalaya.m3u8.node.encode;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.ximalaya.audio.model.LinuxFFmpegShell;
//import com.ximalaya.audio.model.TranscodeOption;

public class BaseCommandOption {

    private final static Logger log = LoggerFactory.getLogger(BaseCommandOption.class);

//    private final static MessageLogger log = LoggerFactory.getLogger(BaseCommandOption.class);

    public static final String WINCMD = "cmd";

    public static final String WINCMDOP = "/c";

    public static final String FFMPEG = "ffmpeg";

    public static final String Y = "-y";

    public static final String INPUT = "-i";

    public static final String NOVIDEO = "-vn";

    public static final String ACODEC = "-acodec";

    public static final String COPY = "copy";

    public static final String CONCAT = "concat:";

    public static final String CONCAT_SPLIT = "|";

    public static final String AB = "-ab";

    public static final String AR = "-ar";

    public static final String DEFAULT_AR = "22050";

    public static final String AC = "-ac";

    public static final String DEFAULT_AC = "2";

    public static final String FORMAT = "-f";

    public static final String DEFAULT_FORMAT = "mp3";

    public static final String MP3_CODEC_LIB = "libmp3lame";

    public static final String OUT_SYMBOL = "->";

    public static String BITRATE_UNIT = "k";

    private static boolean isWin = false;

    private static boolean isLinux = false;

    private static String LINUX_FFMPEG_SHELL_PAHT = null;

    // for m3u8-segmenter by caorong 
    public static final String BLANK = "-";

    public static final String MPEGTS = "mpegts";

    public static final String M3U8SEGMENTER = "m3u8-segmenter";

    public static final String SEGMENTDURATION = "-d";

    public static final String SEGMENTPATH = "-p";

    public static final String M3U8PATH = "-m";

    public static final String URLPREFIX = "-u";

    private static String PROPERTIES_FILE_PATH = "/transcoder.properties";
    static {
        String env = System.getProperty("os.name");
        if (log.isDebugEnabled()) {
            log.debug("current operate system :{}", env);
        }
        if (null != env) {
            if (env.toLowerCase().indexOf("win") >= 0) {
                isWin = true;
            } else if (env.toLowerCase().indexOf("linux") >= 0) {
                isLinux = true;
                // 取配置文件
                try {
                    Properties pro = new Properties();
                    pro.load(FFmpegCommandRunner.class.getResourceAsStream(PROPERTIES_FILE_PATH));
                    LINUX_FFMPEG_SHELL_PAHT = pro.getProperty("linux.ffmpeg.shell.path");
                } catch (Exception e) {
                    log.error("can not find transcoder properties file :{}", PROPERTIES_FILE_PATH);
                    throw new IllegalArgumentException("can't find transcoder properties file");
                }
            } else {
                throw new Error("unrecognized operation system!! only know win and linux, "
                        + "if your want to run this app on " + "any other system, should extend this class");
            }
        }
    }

    public static String[] toTranscodeCmdArrays(String input, String bitrate, String output) {
        if (isWin) {
            return toWinCmdArrays(input, bitrate, output);
        } else if (isLinux) {
            return toLinuxCmdArrays(input, bitrate, output);
        } else {
            throw new IllegalArgumentException("unrecognized operation system!!");
        }
    }

    public static String[] toTranscodeCmdArgs(TranscodeOption option) {
        if (StringUtils.isEmpty(option.getFormat())) {
            option.setFormat(DEFAULT_FORMAT);
        }
        if (StringUtils.isEmpty(option.getSampling())) {
            option.setSampling(DEFAULT_AR);
        }
        if (StringUtils.isEmpty(option.getTracks())) {
            option.setTracks(DEFAULT_AC);
        }
        if (isWin || !option.isTrans()) {
            return option.toCmdArray();
        } else if (isLinux) {
            return LinuxFFmpegShell.toCmdArgs(LINUX_FFMPEG_SHELL_PAHT, option);
        } else {
            throw new IllegalAccessError("unrecognized operation system!! ");
        }
    }

    public static String[] toWinCmdArrays(String input, String bitrate, String output) {
        List<String> args = new ArrayList<String>();
        args.add(WINCMD);
        args.add(WINCMDOP);
        args.add(FFMPEG);
        args.add(Y);
        args.add(INPUT);
        args.add(input);
        args.add(NOVIDEO);
        args.add(AR);
        args.add(DEFAULT_AR);
        args.add(AC);
        args.add(DEFAULT_AC);
        args.add(AB);
        args.add(bitrate + BITRATE_UNIT);
        args.add(ACODEC);
        args.add(MP3_CODEC_LIB);
        args.add(FORMAT);
        args.add(DEFAULT_FORMAT);
        args.add(OUT_SYMBOL);
        args.add(output);
        return args.toArray(new String[args.size()]);
    }

    public static String[] toLinuxCmdArrays(String input, String bitrate, String output) {
        List<String> args = new ArrayList<String>();
        args.add(LINUX_FFMPEG_SHELL_PAHT);
        args.add(input);
        args.add(DEFAULT_AC);
        args.add(DEFAULT_AR);
        args.add(bitrate + BITRATE_UNIT);
        args.add(MP3_CODEC_LIB);
        args.add(DEFAULT_FORMAT);
        args.add(output);
        return args.toArray(new String[args.size()]);
    }

    public static String[] toExtractVedioCmdArgs(String input, String output) {
        List<String> args = new ArrayList<String>();
        args.add(FFMPEG);
        args.add(Y);
        args.add(INPUT);
        args.add(input);
        args.add(NOVIDEO);
        args.add(output);
        return args.toArray(new String[args.size()]);
    }

    public static String[] toConcatAudioCmdArgs(List<String> audios, String outPut) {
        List<String> args = new ArrayList<String>();
        args.add(FFMPEG);
        args.add(Y);
        args.add(INPUT);
        StringBuilder bf = new StringBuilder();
        for (String audio : audios) {
            bf.append(audio).append(CONCAT_SPLIT);
        }
        String concat = bf.toString();
        concat = concat.substring(0, concat.length() - 1);
        args.add(CONCAT + concat);
        args.add(ACODEC);
        args.add(COPY);
        args.add(outPut);
        return args.toArray(new String[args.size()]);
    }

}
