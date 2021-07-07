package cn.mayu.yugioh.common.web.trace;

import lombok.Getter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

/**
 * @description: 包装request，实现body可重复读取
 * @author: YgoPlayer
 * @time: 2021/6/16 10:46 下午
 */
@Getter
public class ReadHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] cachedBytes;

    private final Map<String, String[]> parameterMap;

    public ReadHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.parameterMap = request.getParameterMap();
        ByteArrayOutputStream bodyOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(request.getInputStream(), bodyOutputStream);
        this.cachedBytes = bodyOutputStream.toByteArray();
    }

    @Override
    public ServletInputStream getInputStream() {
        return new CachedInputStream(new ByteArrayInputStream(cachedBytes));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> vector = new Vector<>(parameterMap.keySet());
        return vector.elements();
    }

    @Override
    public String getParameter(String name) {
        String[] results = parameterMap.get(name);
        return Objects.isNull(results) ? null : results[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return parameterMap.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    private static class CachedInputStream extends ServletInputStream {

        private final ByteArrayInputStream bodyInputStream;

        private CachedInputStream(ByteArrayInputStream bodyInputStream) {
            this.bodyInputStream = bodyInputStream;
        }

        @Override
        public boolean isFinished() {
            return bodyInputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() {
            return bodyInputStream.read();
        }

        @Override
        public void close() throws IOException {
            bodyInputStream.close();
        }
    }
}
