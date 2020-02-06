package io.transferfile.sftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "io.transferfile.sftp"})
@EnableJpaRepositories(basePackages = { "io.transferfile.sftp" })
@Configuration
public class TransferFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferFileApplication.class, args);
	}

}
