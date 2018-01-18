package org.m2m.api.jmh;

import org.m2m.api.ModelA;
import org.m2m.api.ModelB;
import org.m2m.api.mapper.ModelKnowledger;
import org.m2m.api.mapper.ModelKnowledgerCacheMemory;
import org.m2m.api.mapper.ModelMapperDirectMemoryAccess;
import org.m2m.api.mapper.ModelMapperObject;
import org.m2m.api.mapping.ModelMapping;
import org.m2m.api.model.support.ModelDefinitionSupport;
import org.m2m.api.model.support.ModelDefinitionSupportJavaType;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.concurrent.TimeUnit;
//@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchmarkCopy {

    @State(Scope.Benchmark)
    public static class BenchmarckArg {
        private ModelDefinitionSupport modelDefinition = new ModelDefinitionSupportJavaType();
        ModelKnowledger knowledger = new ModelKnowledgerCacheMemory();
        ModelMapperObject mapperObject = new ModelMapperDirectMemoryAccess(knowledger);

        public BenchmarckArg() {
            final ModelMapping mapping = ModelMapping.newInstance(knowledger, modelDefinition);
            mapping.map(ModelA.class, ModelB.class)
                    .field("field1","field3")
                    .field("fiel2","field4")
                    .register();
        }
    }

    @Benchmark
    @Measurement(iterations = 1, time = 2000, timeUnit = TimeUnit.MILLISECONDS) // 20 iterations of 2000ms each
   public void benchmarkCode(BenchmarckArg arg) {
    final ModelA a = new ModelA();
        a.setField1("field value 0");
        a.setField5("field value 1");
        a.setField6(1);
        final ModelB bResult = arg.mapperObject.map(a, ModelB.class);
        System.out.println(bResult);
    }

}
