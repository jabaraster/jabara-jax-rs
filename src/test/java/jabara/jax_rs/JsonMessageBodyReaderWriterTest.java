/**
 * 
 */
package jabara.jax_rs;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.core.util.StringKeyIgnoreCaseMultivaluedMap;

/**
 * 
 * @author jabaraster
 */
public class JsonMessageBodyReaderWriterTest {

    JsonMessageBodyReaderWriter testee;

    /**
     * Test method for
     * {@link jabara.jax_rs.JsonMessageBodyReaderWriter#readFrom(java.lang.Class, java.lang.reflect.Type, java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap, java.io.InputStream)}
     * .
     * 
     * @throws IOException
     * @throws WebApplicationException
     */
    @SuppressWarnings({ "nls", "boxing" })
    @Test
    public void _readFrom() throws WebApplicationException, IOException {
        final String s = "[\"a\",\"b\",\"c\"]"; //$NON-NLS-1$
        final ByteArrayInputStream in = new ByteArrayInputStream(s.getBytes(Charset.forName("UTF-8")));

        @SuppressWarnings("unchecked")
        final List<String> list = (List<String>) this.testee.readFrom( //
                Object.class //
                , null //
                , new Annotation[0] //
                , MediaType.APPLICATION_JSON_TYPE //
                , new StringKeyIgnoreCaseMultivaluedMap<String>() //
                , in //
                );
        assertThat(3, is(list.size()));
    }

    /**
     * Test method for
     * {@link jabara.jax_rs.JsonMessageBodyReaderWriter#writeTo(java.lang.Object, java.lang.Class, java.lang.reflect.Type, java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap, java.io.OutputStream)}
     * 
     * @throws IOException
     * @throws WebApplicationException
     */
    @SuppressWarnings("nls")
    @Test
    public void _writeTo() throws WebApplicationException, IOException {
        final String[] ary = { "a", "b", "c" };
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.testee.writeTo( //
                ary //
                , String.class //
                , null //
                , new Annotation[0] //
                , MediaType.APPLICATION_JSON_TYPE //
                , new StringKeyIgnoreCaseMultivaluedMap<Object>() //
                , out //
                );

        assertThat(new String(out.toByteArray()), is("[\"a\",\"b\",\"c\"]"));
    }

    @Before
    public void before() {
        this.testee = new JsonMessageBodyReaderWriter();
    }

}
