package com.tang.profile.demoprovider.constant.localCacheConstant;

/**
 * < 这边为了配和前台展示，对一些文件进行了微调，方便前台查询的时候反射找key ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/12/11 <br>
 */
public class LocalCacheKeyLevel2Def {
    /**
     * < 默认的keyValue,也就是二级索引的key,当二级索引对应的dataObject是一个list的时候， 也就是说只有一个list，而不是多个包装类型，
     * 所以二级索引的key没有必要单独设置唯一标识，这时建议使用这里定义的def值 >
     */
    public static final String all_in_one = "all_in_one";

    /**
     * < >
     */
    private LocalCacheKeyLevel2Def() {

    }
}
