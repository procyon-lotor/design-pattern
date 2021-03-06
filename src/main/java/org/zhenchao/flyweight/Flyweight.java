package org.zhenchao.flyweight;

/**
 * 抽象享元
 *
 * @author zhenchao.wang 2017-04-09 17:48
 * @version 1.0.0
 */
public interface Flyweight {

    /**
     * 操作函数
     *
     * @param externalState 外部状态
     */
    void operate(Object externalState);

}
