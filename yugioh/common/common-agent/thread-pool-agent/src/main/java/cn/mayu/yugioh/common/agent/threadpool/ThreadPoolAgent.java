package cn.mayu.yugioh.common.agent.threadpool;

import cn.mayu.yugioh.common.agent.init.ThreadPoolInfo;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import java.lang.instrument.Instrumentation;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 线程池监控agent
 * @author: YgoPlayer
 * @time: 2021/7/16 4:21 下午
 */
public class ThreadPoolAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println(agentArgs);
        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, module) ->
                builder
                .visit(
                        Advice.to(ThreadPoolBeforeExecuteAdvice.class)
                                .on(ElementMatchers.named("beforeExecute")));
        AgentBuilder.Listener listener = new AgentBuilder.Listener() {

            @Override
            public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {

            }

            @Override
            public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {

            }

            @Override
            public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

            }
        };

        new AgentBuilder
                .Default()
                .disableClassFormatChanges()
                .ignore(ElementMatchers.noneOf(ThreadPoolExecutor.class))
                .with(AgentBuilder.InitializationStrategy.NoOp.INSTANCE)
                .with(AgentBuilder.RedefinitionStrategy.REDEFINITION)
                .with(AgentBuilder.TypeStrategy.Default.REDEFINE)
                .with(AgentBuilder.InjectionStrategy.UsingUnsafe.INSTANCE)
                .type(ElementMatchers.is(ThreadPoolExecutor.class))
                .transform(transformer)
                .with(listener)
                .installOn(inst);

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                ThreadPoolInfo.getAll().entrySet().forEach(entry -> {
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                });
            }
        },  0L, 1000L);
    }
}


