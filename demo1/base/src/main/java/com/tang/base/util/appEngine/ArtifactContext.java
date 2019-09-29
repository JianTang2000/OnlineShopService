package com.tang.base.util.appEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * < 公共的工具类,可以在各个业务中心被调用.以类名.方法名的形式调用.各个业务中心的pom里面引用了base这个项目,打包的时候base会被打到每个业务中心里. * final类，有一个私有的构造方法和多个“静态”的public方法。
 * 服务于流程引擎的'组件上下文数据总线'工具. 基于ThreadLocal<Stack>，保存一个app流程的输入参数,同时也可以保存app流程里各个组件执行时自定义的上下文信息 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/21 <br>
 */
public final class ArtifactContext {

    /**
     * 线程上下文，ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。
     */
    private static ThreadLocal<Stack> context = new ThreadLocal<Stack>();

    /**
     * 私有构造方法
     */
    private ArtifactContext() {
    }

    /**
     * 组件引擎内部使用的push方法,push进一个map,在map里放各种上下文数据
     *
     * @param map 栈中实际保存的map
     */
    static void push(Map<? extends Object, ? extends Object> map) {
        getContextStack().push(map);
    }

    /**
     * 组件引擎内部使用的pop方法,app结束后应该要弹出map
     *
     * @return 栈中弹出的对象
     */
    static Object pop() {
        return getContextStack().pop();
    }

    /**
     * 组件引擎内部使用的get方法 获取栈顶元素
     *
     * @return 栈顶元素
     */
    @SuppressWarnings("unchecked")
    static Map<Object, Object> get() {
        return (Map<Object, Object>) getContextStack().peek();
    }

    /**
     * < 供外部使用的(app流程里各个组件执行时)上下文put方法
     * 向map的一个不存在的<key，value>里put,就是新加一组<key，value>(比如说要在上下文中增加某个key),下文可以取到，这个没有争议，
     * 向map的一个已经存在的<key，value>里put,会将新对象替换原来的value(比如说要改变上下文中某个key的值),但不会直接改变原value初始化时的那个包装对象//建议不要这样
     * > <br>
     *
     * @auther: tang
     * @param <K> 键泛型
     * @param <V> 值泛型
     * @param key 键
     * @param value 值<br>
     */
    public static <K, V> void put(K key, V value) {
        Map<Object, Object> map = get();
        map.put(key, value);
    }

    /**
     * < 供外部使用的(app流程里各个组件执行时)上下文get方法,(这里不提供key的remove，因为没有太大意义)
     * get拿一个不到的key的value,会为空,这没有争议,
     * get拿一个存在的key的value，value是包装类的情况下,用一个新的包装类接收这个value,然后修改这个'新的包装类'赋予其新的属性，value和原value初始化时的那个包装对象的值都会被修改(参考对象的引用)。//建议这样而不是用put去改值
     * > <br>
     *
     * @param <K> 键泛型
     * @param <V> 值泛型
     * @param key 键
     * @return 值<br>
     */
    @SuppressWarnings("unchecked")
    public static <K, V> V get(K key) {
        Stack<Object> stack = context.get();
        if (null != stack && !stack.isEmpty()) {
            Map<Object, Object> map = (Map<Object, Object>) stack.peek();
            if (map != null) {
                return (V) map.get(key);
            }
        }
        return null;
    }

    /**
     * 获取仓储上下文堆栈
     *
     * @return 返回栈
     */
    private static Stack<Object> getContextStack() {
        @SuppressWarnings("unchecked")
        Stack<Object> contextStack = context.get();
        if (contextStack == null || contextStack.isEmpty()) {
            contextStack = new Stack<Object>();
            Map<Object, Object> map = new HashMap<Object, Object>();
            contextStack.push(map);
            context.set(contextStack);
        }
        return contextStack;
    }
}
