/**
 * 
 */
package jabara.jax_rs;

import jabara.general.Empty;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

/**
 * @author jabaraster
 */
public class TextMessageBodyWriter implements MessageBodyWriter<Object> {

    /**
     * @see javax.ws.rs.ext.MessageBodyWriter#getSize(java.lang.Object, java.lang.Class, java.lang.reflect.Type, java.lang.annotation.Annotation[],
     *      javax.ws.rs.core.MediaType)
     */
    @SuppressWarnings("unused")
    @Override
    public long getSize( //
            final Object pT //
            , final Class<?> pType //
            , final Type pGenericType //
            , final Annotation[] pAnnotations //
            , final MediaType pMediaType //
    ) {
        return -1;
    }

    /**
     * @see javax.ws.rs.ext.MessageBodyWriter#isWriteable(java.lang.Class, java.lang.reflect.Type, java.lang.annotation.Annotation[],
     *      javax.ws.rs.core.MediaType)
     */
    @SuppressWarnings("unused")
    @Override
    public boolean isWriteable( //
            final Class<?> pType //
            , final Type pGenericType //
            , final Annotation[] pAnnotations //
            , final MediaType pMediaType //
    ) {
        return pMediaType.isCompatible(MediaType.TEXT_PLAIN_TYPE);
    }

    /**
     * @see javax.ws.rs.ext.MessageBodyWriter#writeTo(java.lang.Object, java.lang.Class, java.lang.reflect.Type, java.lang.annotation.Annotation[],
     *      javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap, java.io.OutputStream)
     */
    @SuppressWarnings("unused")
    @Override
    public void writeTo( //
            final Object pT //
            , final Class<?> pType //
            , final Type pGenericType //
            , final Annotation[] pAnnotations //
            , final MediaType pMediaType //
            , final MultivaluedMap<String, Object> pHttpHeaders //
            , final OutputStream pEntityStream //
    ) throws IOException {

        final String s = pT == null ? Empty.STRING : pT.toString();
        try {
            final String charset = getCharsetFrom(pMediaType.getParameters());
            final byte[] data = s.getBytes(charset);
            pEntityStream.write(data);

        } catch (final UnsupportedEncodingException e) {
            pEntityStream.write(s.getBytes(StandardCharsets.UTF_8));
        }
    }

    private static String getCharsetFrom(final Map<String, String> parameters) {
        final String charset = "charset"; //$NON-NLS-1$
        if (parameters.containsKey(charset)) {
            return parameters.get(charset);
        }
        return StandardCharsets.UTF_8.name();
    }
}
