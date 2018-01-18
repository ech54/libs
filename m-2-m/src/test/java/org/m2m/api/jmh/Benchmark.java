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
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.concurrent.TimeUnit;

public class Benchmark {

    public static void main(String[] arguments) {
        final Benchmark benchmark = new Benchmark();
        ModelMapperObject modelMapperObject = benchmark.setup();
        final BenchmarkCopy copy = new BenchmarkCopy();
        final BenchmarkCopy.BenchmarckArg arg = new BenchmarkCopy.BenchmarckArg();
        arg.mapperObject = modelMapperObject;
        copy.benchmarkCode(arg);
    }

    private ModelDefinitionSupport modelDefinition = new ModelDefinitionSupportJavaType();


    private ModelMapperObject setup() {
        ModelKnowledger knowledger = new ModelKnowledgerCacheMemory();
        ModelMapperObject mapperObject = new ModelMapperDirectMemoryAccess(knowledger);
        final ModelMapping mapping = ModelMapping.newInstance(knowledger, modelDefinition);
        mapping.map(ModelA.class, ModelB.class).register();
        return  mapperObject;
    }

}
