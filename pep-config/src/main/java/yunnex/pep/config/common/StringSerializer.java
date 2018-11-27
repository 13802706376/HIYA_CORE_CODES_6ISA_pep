package yunnex.pep.config.common;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringEscapeUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

/**
 * 序列化json时，对String类型的值做HTML转义。如不需要转义（比如json字符串），在字段上面指明使用原来的序列化方式即可， 如：
 * <code>
 *     @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.StringSerializer.class)
 *     private String methodParams;
 * </code>
 */
public class StringSerializer extends StdScalarSerializer<Object>
{
    private static final long serialVersionUID = 1L;

    public StringSerializer() { super(String.class, false); }

    @Override
    public boolean isEmpty(SerializerProvider prov, Object value) {
        String str = (String) value;
        return str.length() == 0;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(StringEscapeUtils.escapeHtml4((String) value));
    }

    @Override
    public final void serializeWithType(Object value, JsonGenerator gen, SerializerProvider provider,
                                        TypeSerializer typeSer) throws IOException {
        gen.writeString((String) value);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        return createSchemaNode("string", true);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        visitStringFormat(visitor, typeHint);
    }

}
