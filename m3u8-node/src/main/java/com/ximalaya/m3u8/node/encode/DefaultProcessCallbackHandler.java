package com.ximalaya.m3u8.node.encode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultProcessCallbackHandler implements ProcessCallbackHandler {

    private final Logger log = LoggerFactory.getLogger(DefaultProcessCallbackHandler.class);

    private String result;

    @Override
    public String handle(InputStream errorStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
                if (log.isDebugEnabled()) {
                    log.debug(line);
                }
            }
        } catch (IOException e) {
            log.error("read from process error ", e);
            throw e;
        } finally {
            try {
                errorStream.close();
            } catch (IOException e) {
                log.error("close errorStream error", e);
            }
        }
        setResult(sb.toString());
        return getResult();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
