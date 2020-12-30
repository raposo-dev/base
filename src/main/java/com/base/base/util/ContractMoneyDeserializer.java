package com.base.base.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ContractMoneyDeserializer extends JsonDeserializer<Number> {

  @Override
  public BigDecimal deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    String text =
        parser.getText().replace("€", "").replace(",", "").replace(" ", "").replace(".", "");
    BigDecimal number = new BigDecimal(text);

    return number.divide(new BigDecimal(100),2, RoundingMode.UNNECESSARY);
  }
}
