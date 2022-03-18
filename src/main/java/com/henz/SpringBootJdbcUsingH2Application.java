package com.henz;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.henz.entity.Person;
import com.henz.jdbc.PersonJdbcDao;

/*
 * 
 * how to launch H2 (in memory db) console:
 * 
 * put a config in application.prop
 * 
 * open localhost:8080/h2-console
 * 
 * 
 * then create a table by adding data.sql file in resources. this file will be loaded by spring when 
 * launching the app
 * 
 * 
 * then add records e.g. in h2 console
 * 
 * insert into person values(1,'joel','frenkendorf',sysdate());
 * 
 * or add it also into data.sql. records will be added on start
 * 
 * */


/*
 * to see what happens in auto config, add logging.level.root=debug to app prop file
 * 
 * */

@SpringBootApplication
public class SpringBootJdbcUsingH2Application implements CommandLineRunner{
	
	@Autowired
	PersonJdbcDao dao;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJdbcUsingH2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		logger.info("all users -> {}", dao.findAll());
		
		logger.info("user id 1 -> {}", dao.findById(1));
		
		logger.info("delete id 1 -> {}", dao.deleteById(1));
		
		logger.info("insert id 3 -> {}", dao.insert(new Person(3, "mani", "bärschwil", new Date())));
		
		//use java.util.Date
		logger.info("update id 3 -> {}", dao.update(new Person(3, "manfred", "bärschwil", new Date())));
		
		//using custom row mapper
		logger.info("all users -> {}", dao.findAllUsingCustomRowMapper());
	}

}
