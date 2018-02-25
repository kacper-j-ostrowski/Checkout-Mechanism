package pl.ostrowski;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = { "pl.ostrowski.checkout", "pl.ostrowski.repositories", "pl.ostrowski.operations", "pl.ostrowski.item","pl.ostrowski.entities"})
public class CheckoutProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckoutProjectApplication.class, args);
	}
	
	@Bean  
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){  
	    return hemf.getSessionFactory();  
	}   
}