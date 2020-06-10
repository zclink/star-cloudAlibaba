package com.gupaoedu.sca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NacosConfigApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext =SpringApplication.run(NacosConfigApplication.class, args);
		//可以观察到 修改配置 可以正常捕捉到修改后的内容
		while (true) {
			String info = applicationContext.getEnvironment().getProperty("info");
			String name = applicationContext.getEnvironment().getProperty("username");
			System.out.println(info);
			System.out.println(name);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
