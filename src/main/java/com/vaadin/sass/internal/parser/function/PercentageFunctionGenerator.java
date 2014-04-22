/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.sass.internal.parser.function;

import com.vaadin.sass.internal.parser.LexicalUnitImpl;

/**
 * PercentageFunctionGenerator is used for converting values into percentages
 * with rounding.
 * 
 * @author Vaadin
 */
public class PercentageFunctionGenerator extends
        AbstractSingleParameterFunctionGenerator {

    private static int PRECISION = 100000;
    private static int PERC_PRECISION_FACTOR = 100 * PRECISION;

    public PercentageFunctionGenerator() {
        super("percentage");
    }

    @Override
    protected LexicalUnitImpl computeForParam(LexicalUnitImpl firstParam) {
        StringBuilder builder = new StringBuilder();
        float value = firstParam.getFloatValue();
        value *= PERC_PRECISION_FACTOR;
        int intValue = Math.round(value);
        value = ((float) intValue) / PRECISION;

        int resultIntValue = (int) value;

        LexicalUnitImpl result = LexicalUnitImpl.createPercentage(
                firstParam.getLineNumber(), firstParam.getColumnNumber(), null,
                value);

        if (intValue == resultIntValue * PRECISION) {
            builder.append(resultIntValue);
            result.setIntegerValue(resultIntValue);
        } else {
            builder.append(value);
            result.setFloatValue(value);
        }

        result.setStringValue(builder.append('%').toString());

        return result;
    }
}