package org.reflections.scanners;

import org.reflections.*;
import org.reflections.adapters.*;
import java.util.*;

public class MethodParameterScanner extends AbstractScanner
{
    @Override
    public void scan(final Object cls, final Store store) {
        final MetadataAdapter md = this.getMetadataAdapter();
        for (final Object method : md.getMethods(cls)) {
            final String signature = md.getParameterNames(method).toString();
            if (this.acceptResult(signature)) {
                this.put(store, signature, md.getMethodFullKey(cls, method));
            }
            final String returnTypeName = md.getReturnTypeName(method);
            if (this.acceptResult(returnTypeName)) {
                this.put(store, returnTypeName, md.getMethodFullKey(cls, method));
            }
            final List<String> parameterNames = md.getParameterNames(method);
            for (int i = 0; i < parameterNames.size(); ++i) {
                for (final Object paramAnnotation : md.getParameterAnnotationNames(method, i)) {
                    if (this.acceptResult((String)paramAnnotation)) {
                        this.put(store, (String)paramAnnotation, md.getMethodFullKey(cls, method));
                    }
                }
            }
        }
    }
}
