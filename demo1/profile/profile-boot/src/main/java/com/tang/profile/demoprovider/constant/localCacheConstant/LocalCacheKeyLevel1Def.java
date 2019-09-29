package com.tang.profile.demoprovider.constant.localCacheConstant;

/**
 * < 1这边为了配和前台展示，对属性名进行了微调，方便前台查询的时候反射找key;<br>
 * 2静态变量的变量名会被反射查找出来作为前台展示的一级key，所以命名最好稍微有意义一些;<br>
 * 3其实也可以取静态变量的变量值，但那样就要写get和set方法，因为静态变量的大写，IDEA自动生成的方法名不大写，反射会找不到get方法，要手动改自动生成的方法的方法名才可以，这样很麻烦><br>
 * 4为了避免3中的麻烦操作，请务必使得变量名和变量值完全一致<br>
 * 
 * @author tang.jian<br>
 * @CreateDate 2018/10/23 <br>
 */
// 这里的一级key命名最好尽量接近缓存的数据的实际意义。
public final class LocalCacheKeyLevel1Def {
    /**
     * < 所有的1级key必为String,2级key是Object >
     */
    public static final String all_person_with_id = "all_person_with_id";

    /**
     * < >
     */
    public static final String all_person = "all_person";

    /**
     * < >
     */
    private LocalCacheKeyLevel1Def() {

    }

}
