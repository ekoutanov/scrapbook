package scrapbook.katana;

import com.fasterxml.jackson.annotation.JsonInclude.*;
import com.fasterxml.jackson.databind.*;
import com.obsidiandynamics.verifier.*;

public final class JsonCodec implements Codec {
  private static final JsonCodec defaultInstance = new JsonCodec(new ObjectMapper()
                                                                 .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                                                                 .configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true)
                                                                 .setSerializationInclusion(Include.NON_NULL));
  
  public static JsonCodec getDefault() {
    return defaultInstance;
  }
  
  private final ObjectMapper mapper;
  
  public JsonCodec(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public byte[] toBytes(Object obj) throws Exception {
    return mapper.writeValueAsBytes(obj);
  }

  @Override
  public <T> T toObject(byte[] bytes, Class<T> type) throws Exception {
    return mapper.readValue(bytes, type);
  }
}