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
import java.util.ArrayList;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import net.arnx.jsonic.JSON;

import org.junit.Test;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.core.util.StringKeyIgnoreCaseMultivaluedMap;

/**
 * 
 * @author jabaraster
 */
@SuppressWarnings("static-method")
public class JsonMessageBodyReaderWriterTest {
    /**
     * Test method for
     * {@link jabara.jax_rs.JsonMessageBodyReaderWriter#writeTo(java.lang.Object, java.lang.Class, java.lang.reflect.Type, java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap, java.io.OutputStream)}
     * 
     * @throws IOException
     * @throws WebApplicationException
     */
    @SuppressWarnings("nls")
    @Test
    public void _writeTo_String配列() throws WebApplicationException, IOException {
        final String[] ary = { "a", "b", "c" };
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        new JsonMessageBodyReaderWriter<String[]>().writeTo( //
                ary //
                , String[].class //
                , null //
                , new Annotation[0] //
                , MediaType.APPLICATION_JSON_TYPE //
                , new StringKeyIgnoreCaseMultivaluedMap<Object>() //
                , out //
                );

        assertThat(new String(out.toByteArray()), is("[\"a\",\"b\",\"c\"]"));
    }

    /**
     * @throws IOException -
     */
    @SuppressWarnings("nls")
    @Test
    public void _要素の型がStringのListをデコード() throws IOException {
        final StringList source = new StringList("あ", "い");
        final byte[] data = toJsonData(source);
        final StringList ret = new JsonMessageBodyReaderWriter<StringList>().readFrom( //
                StringList.class //
                , null //
                , new Annotation[0] //
                , MediaType.APPLICATION_JSON_TYPE //
                , new MultivaluedMapImpl() //
                , new ByteArrayInputStream(data));
        assertThat(ret, is(source));
    }

    /**
     * @throws IOException
     */
    @SuppressWarnings({ "nls" })
    @Test
    public void _要素の型が具体的になっているListをデコード() throws IOException {
        final BeanList source = new BeanList(new Bean(0, "a"), new Bean(1, "b"));
        final byte[] data = toJsonData(source);
        final BeanList ret = new JsonMessageBodyReaderWriter<BeanList>().readFrom( //
                BeanList.class //
                , null //
                , new Annotation[0] //
                , MediaType.APPLICATION_JSON_TYPE //
                , new MultivaluedMapImpl() //
                , new ByteArrayInputStream(data));
        assertThat(ret, is(source));
    }

    private static byte[] toJsonData(final Object pSource) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSON.encode(pSource, out);
        return out.toByteArray();
    }

    static class Bean {
        int    code;
        String name;

        Bean() {
            //
        }

        Bean(final int pCode, final String pName) {
            this.code = pCode;
            this.name = pName;
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Bean other = (Bean) obj;
            if (this.code != other.code) {
                return false;
            }
            if (this.name == null) {
                if (other.name != null) {
                    return false;
                }
            } else if (!this.name.equals(other.name)) {
                return false;
            }
            return true;
        }

        /**
         * @return codeを返す.
         */
        public int getCode() {
            return this.code;
        }

        /**
         * @return nameを返す.
         */
        public String getName() {
            return this.name;
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + this.code;
            result = prime * result + (this.name == null ? 0 : this.name.hashCode());
            return result;
        }

        /**
         * @param pCode codeを設定.
         */
        public void setCode(final int pCode) {
            this.code = pCode;
        }

        /**
         * @param pName nameを設定.
         */
        public void setName(final String pName) {
            this.name = pName;
        }

    }

    static class BeanList extends ArrayList<Bean> {
        private static final long serialVersionUID = -9110458500835921775L;

        BeanList() {
            //
        }

        BeanList(final Bean... pBeans) {
            for (final Bean b : pBeans) {
                this.add(b);
            }
        }
    }

    static class StringList extends ArrayList<String> {
        private static final long serialVersionUID = 7815227246979357544L;

        StringList() {
            //
        }

        StringList(final String... ss) {
            for (final String s : ss) {
                this.add(s);
            }
        }
    }

}
