/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at:
 *
 *      http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package com.amazon.ionhiveserde.objectinspectors;

import org.apache.hadoop.hive.serde2.objectinspector.primitive.FloatObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;
import org.apache.hadoop.io.FloatWritable;
import software.amazon.ion.IonFloat;
import software.amazon.ion.IonValue;

import static com.amazon.ionhiveserde.objectinspectors.IonUtil.isIonNull;

/**
 * Adapts an {@link IonFloat} for the float Hive type
 */
public class IonFloatToFloatObjectInspector extends AbstractIonPrimitiveJavaObjectInspector implements FloatObjectInspector {

    public IonFloatToFloatObjectInspector() {
        super(TypeInfoFactory.floatTypeInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getPrimitiveWritableObject(final Object o) {
        if (isIonNull((IonValue) o)) return null;

        return new FloatWritable(getPrimitiveJavaObject((IonFloat) o));
    }

    private void validateSize(final IonFloat ionFloat) {
        final float f = ionFloat.floatValue();
        final double d = ionFloat.doubleValue();

        if (Double.compare(f, d) != 0) {
            throw new IllegalArgumentException("insufficient precision for " + ionFloat.toString() + " as " + this.typeInfo.getTypeName());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float get(Object o) {
        return (float) getPrimitiveJavaObject(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getPrimitiveJavaObject(final Object o) {
        if (isIonNull((IonValue) o)) return null;

        return getPrimitiveJavaObject((IonFloat) o);
    }

    public float getPrimitiveJavaObject(final IonFloat ionValue) {
        validateSize(ionValue);
        return ionValue.floatValue();
    }
}
