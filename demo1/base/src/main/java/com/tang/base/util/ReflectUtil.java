package com.tang.base.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * < 反射相关的一些静态方法 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/9 <br>
 */
public final class ReflectUtil {

    /**
     * < 日志 >
     */
    private static final Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    private ReflectUtil() {

    }

    /**
     * < 执行指定类的某个指定方法（空入参）。> <支持:类中有/没有注入其他bean> <限制:></> <1类本身被Spring管理，能从上下文拿到bean；></>
     * <2类种待执行的方法必须是无参数方法。（后续会写一个支持不定入参的出来，然后job实现就可以用这种不定入参的了。后续再补充）></>
     *
     * @param classPath < 类路径 >
     * @param funcName < 待执行的方法的方法名称 >
     */
    public static <T> T methodInvokeBean(String classPath, String funcName)
        throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        // Class<?> cls = Class.forName(classPath); // 取得Class对象,传入一个包名+类名的字符串得到Class对象
        // Object obj = cls.newInstance(); // 反射实例化对象
        // // 调用Class类的方法getMethod获取指定的方法
        // Method method = cls.getMethod(funcName1, String.class, String.class);
        // // 调用Method类的方法invoke运行指定的方法方法
        // Object objString = method.invoke(obj, billData, template); // 拿到流
        // invokeDmsCore.writeFileToDiskByPathAndDoc(objectToBytes(objString), filePath); // 拿到流生成文件
        Class<?> cls = Class.forName(classPath); // 取得Class对象,传入一个包名+类名的字符串得到Class对象
        Object obj = ApplicationContextHolder.getBeanByType(cls);
        Method method = getSpecialMethodByClassAndMethodName(cls, funcName);
        return (T) method.invoke(obj);
    }

    /**
     * < 执行指定类的某个指定方法（空入参）。> <限制:></> <1类本身不被Spring管理，且支持cls.newInstance()，如果是被Spring管理，虽然也能拿到实例，但实例中如果注入了其他bean,会为空></>
     * <2类种待执行的方法必须是无参数方法。（后续会写一个支持不定入参的出来，然后job实现就可以用这种不定入参的了。后续再补充）></>
     *
     * @param classPath < 类路径 >
     * @param funcName < 待执行的方法的方法名称 >
     */
    public static <T> T methodInvoke(String classPath, String funcName)
        throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> cls = Class.forName(classPath); // 取得Class对象,传入一个包名+类名的字符串得到Class对象
        Object obj = null; // 反射实例化对象
        obj = cls.newInstance();
        Method method = getSpecialMethodByClassAndMethodName(cls, funcName);
        return (T) method.invoke(obj);
    }

    /**
     * < 执行指定类的某个指定方法，返回<T>（不定入参，job的方法反射执行就是一个典型的调用例子）> < 对象本身调用方自己提供，可以是Spring管理的bean也可以是newInstance出来的;
     * 对于不由spring管理bean的类，建议使用class.newInstance()获取实例化对象； 对于由spring管理bean的类,建议使用上下文拿的bean或者注入拿到对象></>
     *
     * @param target < 反射类的对象实例 >
     * @param methodName < 待执行的方法的方法名称 >
     * @param args < 方法入参，例如： object [] param =new object [] {"方法的参数1"，"方法的参数2"}; >
     * @return 泛型 <br>
     */
    public static <T> T methodInvoke(Object target, String methodName, Object[] args) throws Exception {
        Object result = null;
        Class clazz = target.getClass();
        Class[] argtypes = new Class[0];
        if (args != null) {
            argtypes = new Class[args.length];
            ArrayList invoker = new ArrayList();
            int argLength = args.length;
            for (int i = 0; i < argLength; i++) {
                Object arg = args[i];
                invoker.add(arg == null ? null : arg.getClass());
            }
            invoker.toArray(argtypes);
        }
        Method method = getMethod(clazz, methodName, argtypes);
        return method == null ? null : (T) method.invoke(target, args);
    }

    /**
     * < 获取指定类的某个指定方法，不支持重载方法 > <br>
     *
     * @param classPath < 包名+类名 >
     * @param funcName < 方法名，不支持重载方法名 >
     * @return < 指定的方法，class或方法找不到会抛异常 >
     */
    public static Method getSpecialMethodByClassPathAndMethodName(String classPath, String funcName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(classPath); // 取得Class对象,根据包名+类名的字符串得到Class对象
        }
        catch (ClassNotFoundException e) {
            logger.debug("class not found,classPath is:", classPath);
            throw new RuntimeException(e);
        }
        if (null != cls) {
            return getSpecialMethodByClassAndMethodName(cls, funcName);
        }
        logger.debug("Class is null");
        throw new RuntimeException();
    }

    /**
     * <tang: 获取指定类的某个指定方法，不支持重载方法 > <br>
     *
     * @param thisClazz < 根据类路径找到的class >
     * @param methodName < 方法名，不支持重载方法名 >
     * @return < 指定的方法，方法找不到会抛异常 >
     */
    public static Method getSpecialMethodByClassAndMethodName(Class<?> thisClazz, String methodName) {
        Method method = null;
        Method[] ma = thisClazz.getDeclaredMethods(); // 获取本类中的所有方法，包括私有的(private、protected、默认以及public)的方法。
        // Method method = cls.getMethod(funcName, String.class, String.class); // 获取指定方法，支持重载
        // method.invoke(obj, billData, template); // 对应的执行方式
        // Method[] method = cls.getMethods(); // 获取本类以及父类或者父接口中所有的公共方法(public修饰符修饰的)
        for (Method m : ma) {
            if (m.getName().equals(methodName)) {
                method = m;
                break;
            }
        }
        if (null == method) {
            logger.info("methodName:{} not found!", methodName);
            throw new RuntimeException();
        }
        return method;
    }

    /**
     * 获取对象的属性
     *
     * @param fieldName
     * @param target
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getFieldValueByName(String fieldName, Object target) throws Exception {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        Method method = target.getClass().getMethod(getter, new Class[0]);
        Object e = method.invoke(target, new Object[0]);
        return (T) e;
    }

    /**
     * 获取所有字段名字
     *
     * @param target
     * @return
     */
    public static String[] getFiledName(Object target) throws Exception {
        Field[] fields = target.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取所有属性的值
     *
     * @param target
     * @return
     * @throws Exception
     */
    public static Object[] getFiledValues(Object target) throws Exception {
        String[] fieldNames = getFiledName(target);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; ++i) {
            value[i] = getFieldValueByName(fieldNames[i], target);
        }
        return value;
    }

    /**
     * < 递归获取方法引用,支持重载方法，如果找不到会向上去父类递归查询 > <br>
     *
     * @auther: tang
     * @param target < class >
     * @param methodName < 方法名 >
     * @param argTypes < 参数列表 >
     * @return < >
     */
    private static Method getMethod(Class<?> target, String methodName, Class<?>[] argTypes) {
        Method method = null;
        try {
            method = target.getDeclaredMethod(methodName, argTypes);
            method.setAccessible(true);
        }
        catch (NoSuchMethodException e) {
            method = getCatchMethod(target, methodName, argTypes);
        }
        if (method == null && target != Object.class) {
            return getMethod(target.getSuperclass(), methodName, argTypes);
        }
        return method;
    }

    /**
     * 当含有基础类型抛出NoSuchMethodException异常循环所有方法
     *
     * @param target
     * @param methodName
     * @param argTypes
     * @return
     */
    private static Method getCatchMethod(Class<?> target, String methodName, Class<?>[] argTypes) {
        Method method = null;
        Method[] methods = target.getDeclaredMethods();
        int methodsLength = methods.length;
        for (int i = 0; i < methodsLength; i++) {
            Method methodTmp = methods[i];
            int argsLength = methodTmp.getParameterTypes() == null ? 0 : methodTmp.getParameterTypes().length;
            if (methodTmp.getName().equals(methodName) && argsLength == argTypes.length) {
                methodTmp.setAccessible(true);
                method = methodTmp;
                break;
            }
        }
        return method;
    }

    /**
     * < 获取beanObj的属性property的set方法，执行beanObj的set方法，set的值是value 这么写好像没什么特别强的应用场景，当然，在大量的赋值时用这个可以少些很多重复代码> <br>
     *
     * @auther: tang.jian
     * @param beanObj < >
     * @param property < >
     * @param value < >
     */
    public static void setProperty(Object beanObj, String property, Object value) {
        PropertyDescriptor pd = null;
        try {
            pd = new PropertyDescriptor(property, beanObj.getClass());
        }
        catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
        Method setMethod = pd.getWriteMethod();
        if (setMethod == null) {
        }
        try {
            setMethod.invoke(beanObj, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * < 获取beanObj的属性property的get方法，执行beanObj的set方法，返回get的值(泛型)
     * 这么写是因为，比如说分了项目的时候，有些bean来自其他项目，本项目没有找个包装类型的时候，只需要知道属性名即可用反射来获取属性值 > <br>
     *
     * @auther: tang.jian
     * @param beanObj < >
     * @param property < >
     * @return < >
     */
    public static <T> T getProperty(Object beanObj, String property) {
        PropertyDescriptor pd = null;
        try {
            pd = new PropertyDescriptor(property, beanObj.getClass());
        }
        catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
        Method getMethod = pd.getReadMethod();
        try {
            return (T) getMethod.invoke(beanObj);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * < 返回的是object的所有属性名，可以自行写过滤条件过滤不需要的属性> <br>
     *
     * @auther: tang.jian
     * @taskId: < - ><br>
     * @param object < >
     * @return < >
     */
    public static List<String> getParamName(Object object) {
        List<String> ret = new ArrayList<>();
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
//            if (!name.equalsIgnoreCase(serialVersionUID) && !name.equalsIgnoreCase(time) && !name.equalsIgnoreCase(indexName)
//                && !name.equalsIgnoreCase(period)) {
//                ret.add(name);
//            }
            ret.add(name);
        }
        return ret;
    }

}
