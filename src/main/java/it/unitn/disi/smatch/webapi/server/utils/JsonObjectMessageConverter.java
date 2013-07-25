package it.unitn.disi.smatch.webapi.server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

/**
 * Utility class for converting JSON messages.
 *
 * @author Viktor Pravdin <pravdin@disi.unitn.it>
 * @date Dec 5, 2011 2:02:27 PM
 */
public class JsonObjectMessageConverter extends AbstractHttpMessageConverter<JSONObject> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public static final Charset CHARSET = Charset.forName("ISO-8859-1");
    private static final MediaType TYPEJSON = new MediaType("application",
            "json", DEFAULT_CHARSET);
    private static final MediaType TYPETEXT = new MediaType("text", "plain",
            DEFAULT_CHARSET);
    private static final MediaType FILETYPE = new MediaType("application",
            "octet-stream", CHARSET);
    private static MediaType[] types = {TYPEJSON, TYPETEXT, FILETYPE};
    private static final int KB = 1024;

    public JsonObjectMessageConverter() {
        super(types);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return JSONObject.class.isAssignableFrom(clazz);
    }

    @Override
    protected JSONObject readInternal(Class<? extends JSONObject> clazz,
            HttpInputMessage inputMessage) throws IOException {
        InputStream ins = inputMessage.getBody();
        StringWriter out = new StringWriter();
        char[] buff = new char[KB];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(ins,
                    "UTF-8"));
            int n;
            while ((n = reader.read(buff)) != -1) {
                out.write(buff, 0, n);
            }
        } finally {
            ins.close();
        }
        String result = out.toString();
        try {
            return new JSONObject(result);
        } catch (JSONException ex) {
            throw new HttpMessageNotReadableException("Failed to parse JSON: "
                    + ex.getMessage(), ex);
        }
    }

    @Override
    protected void writeInternal(JSONObject t, HttpOutputMessage outputMessage)
            throws IOException {
        try {
            outputMessage.getBody().write(t.toString(2).
                    getBytes(DEFAULT_CHARSET));
        } catch (JSONException e) {
            throw new HttpMessageNotWritableException("Error while converting the JSON content: "
                    + e.getMessage(), e);
        }
    }
}
