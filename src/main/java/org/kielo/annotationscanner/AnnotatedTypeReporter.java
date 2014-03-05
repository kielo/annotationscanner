/*
 * Copyright 2013 Adam Dubiel, Przemek Hertel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kielo.annotationscanner;

import eu.infomas.annotation.AnnotationDetector;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Adam Dubiel
 */
public class AnnotatedTypeReporter extends PackageFilteringReporter implements AnnotationDetector.TypeReporter {

    private final Class<? extends Annotation> reportedClass;

    private final Set<Class<?>> annotatedClasses = new HashSet<Class<?>>();

    public AnnotatedTypeReporter(Class<? extends Annotation> reportedClass, String... packagesToScan) {
        super(reportedClass.getClassLoader(), packagesToScan);
        this.reportedClass = reportedClass;
    }

    public AnnotatedTypeReporter(ClassLoader classLoader, Class<? extends Annotation> reportedClass, String... packagesToScan) {
        super(classLoader, packagesToScan);
        this.reportedClass = reportedClass;
    }

    @Override
    public void reportTypeAnnotation(Class<? extends Annotation> annotation, String className) {
        if (isWanted(className)) {
            annotatedClasses.add(loadClass(className));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Annotation>[] annotations() {
        return new Class[]{reportedClass};
    }

    public Set<Class<?>> getAnnotatedClasses() {
        return Collections.unmodifiableSet(annotatedClasses);
    }
}
