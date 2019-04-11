package com.general;

import java.io.IOException;

import org.jtransfo.JTransfo;
import org.jtransfo.spring.JTransfoSpringFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.general.service.ServiceImageFtp;

@SpringBootApplication
@EnableJpaRepositories("com.general.dao")
@EntityScan("com.general.model")
@ComponentScan("com.general")
@EnableScheduling
public class GeneralWebServiceApplication {

	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(GeneralWebServiceApplication.class, args);
		
	}
	
	@Bean
	@Scope("singleton")
	public JTransfoSpringFactory jTransfoFactory() {
							
		JTransfoSpringFactory c = new JTransfoSpringFactory();
		
		return c;
	}
	
	@Bean
	@Scope("singleton")
	public JTransfo jTransfo() {
		
		return this.jTransfoFactory().get();
	} 
}
