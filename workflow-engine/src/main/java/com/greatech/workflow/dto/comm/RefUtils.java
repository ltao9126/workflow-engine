package com.greatech.workflow.dto.comm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Administrator on 2017/7/24.
 */
public class RefUtils {
    private static final Log logger = LogFactory.getLog(RefUtils.class);

    /**
     * 根据类名 获取实例
     *
     * @param classes
     * @param <T>
     * @return
     */
    public static <T> T instanceBean(String classes) {
        try {
            Class<?> clazz = Class.forName(classes);
            Object o = clazz.newInstance();
            if (null != o) {
                return (T) o;
            } else {
                throw new IllegalArgumentException("RefUtils:can not be instance of the class");
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            logger.error("RefUtils:can not be instance of the class：" + classes, e);
            throw new IllegalArgumentException("RefUtils:can not be instance of the class");
        }
    }
}
