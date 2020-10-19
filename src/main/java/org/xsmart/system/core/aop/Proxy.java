package org.xsmart.system.core.aop;

public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
