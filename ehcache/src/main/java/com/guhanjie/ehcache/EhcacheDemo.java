package com.guhanjie.ehcache;

import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheDemo {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource("ehcache.xml");
		CacheManager manager = CacheManager.create(url);
		String names[] = manager.getCacheNames();
		for (int i = 0; i < names.length; i++) {
			System.out.println(names[i]);
		}

		Cache cache = manager.getCache(names[0]);

		cache.put(new Element("name", "admin"));
		Element element = cache.get("name");

		System.out.println(element.getValue());
		Object obj = element.getObjectValue();
		System.out.println((String) obj);

		Thread.sleep(3000);

		ThreadAction action = new ThreadAction();
		action.setCacheName(cache.getName());
		action.setManager(manager);
		action.start();

		System.out.println("----main  thread is return !----");
	}

}

class ThreadAction extends Thread {
	private String cacheName;
	private CacheManager manager;

	@Override
	public void run() {
		try {
			Thread.sleep(3000);
			Cache cache = manager.getCache(cacheName);
			Element element = cache.get("name");
			System.out.println("从缓存中取得数据:" + element.getValue());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CacheManager getManager() {
		return manager;
	}
	public void setManager(CacheManager manager) {
		this.manager = manager;
	}
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
}