package com.taotao.aop;

import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbContent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by niemengquan on 2017/8/28.
 */
@Component
@Aspect
public class ReidsSyncForTbContentAspect {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;

    /**
     * 在多个表达式之间使用  || , or 表示  或 ，使用  && , and 表示  与 ， ！ 表示  非
     * 配置切入点，该方法无方法体，只要是方便同类中恰方法使用此配置的切入点
     */
    @Pointcut("execution(* com.taotao.service.ContentService.insert*(..))" +
            "||execution(* com.taotao.service.ContentService.update*(..))" +
            "||execution(* com.taotao.service.ContentService.delete*(..))")
    public void pointcutForInsert() {
    }
  /*  @Pointcut("execution(* com.taotao.service.ContentService.update*(..))")
    public void pointcutForUpdate(){}
    @Pointcut("execution(* com.taotao.service.ContentService.delete*(..))")
    public void pointcutForDelete(){}*/

    @After("pointcutForInsert()")
    public void afterForInsert(JoinPoint pjp) throws Throwable {
        clearRedisCacheForTbCoentent(pjp);
    }
 /*   @After("pointcutForUpdate()")
    public void afterForUpdate(JoinPoint pjp){
        clearRedisCacheForTbCoentent(pjp);
    }
    @After("pointcutForDelete()")
    public void afterFoDelete(JoinPoint pjp){
        clearRedisCacheForTbCoentent(pjp);
    }*/

    /**
     * 清除redis对大广告位（tb_Content表）的缓存
     *
     * @param pjp
     */
    private void clearRedisCacheForTbCoentent(JoinPoint pjp) {
        try {
            Object[] args = pjp.getArgs();//获取方法的参数
            TbContent content = (TbContent) args[0];
            HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
