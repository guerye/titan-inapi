package com.riozenc.api.util;

import java.math.BigDecimal;

@FunctionalInterface
public interface ToBigDecimalFunction <T> {

    BigDecimal applyAsBigDecimal(T value);

}
