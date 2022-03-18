package com.henz.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.tree.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henz.entity.Person;

/*
 * jdbc template auto config, like config for h2, data source etc
 * 
 * =========================
AUTO-CONFIGURATION REPORT
=========================

DataSourceAutoConfiguration matched:
   - @ConditionalOnClass found required classes 'javax.sql.DataSource', 'org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)

DataSourceTransactionManagerAutoConfiguration matched:
   - @ConditionalOnClass found required classes 'org.springframework.jdbc.core.JdbcTemplate', 'org.springframework.transaction.PlatformTransactionManager'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)

H2ConsoleAutoConfiguration matched:
   - @ConditionalOnClass found required class 'org.h2.server.web.WebServlet'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
   - found ConfigurableWebEnvironment (OnWebApplicationCondition)
   - @ConditionalOnProperty (spring.h2.console.enabled=true) matched (OnPropertyCondition)

JdbcTemplateAutoConfiguration matched:
   - @ConditionalOnClass found required classes 'javax.sql.DataSource', 'org.springframework.jdbc.core.JdbcTemplate'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
   - @ConditionalOnSingleCandidate (types: javax.sql.DataSource; SearchStrategy: all) found a primary bean from beans 'dataSource' (OnBeanCondition)

JdbcTemplateAutoConfiguration.JdbcTemplateConfiguration#jdbcTemplate matched:
   - @ConditionalOnMissingBean (types: org.springframework.jdbc.core.JdbcOperations; SearchStrategy: all) did not find any beans (OnBeanCondition)

 * 
 * 
 * */

@Repository
public class PersonJdbcDao {
	
	@Autowired //auto config hdbc template
	JdbcTemplate jdbcTemplate;

	
	//row mapper: implementation that converts a row into a new instance of the specified mapped target class
	public List<Person> findAll(){
		return jdbcTemplate.query("select * from person;", new BeanPropertyRowMapper(Person.class)); //default row mapper by spring
	}
	
	public List<Person> findAllUsingCustomRowMapper(){
		return jdbcTemplate.query("select * from person;", new PersonRowMapper()); //default row mapper by spring
	}
	
	//here we dont need a row mapper
	public int deleteById(int id) {
		return jdbcTemplate.update("delete from person where id =?",
				new Object[] {id}
		);
	}
	
	public int insert(Person person) {
		return jdbcTemplate.update("insert into person (id,name,location,birth_date) values(?,?,?,?)",
				new Object[] {person.getId(),person.getName(),person.getLocation(),person.getBirthDate()} //pay attention to the order. it will map to the ? order
		);
	}
	
	public int update(Person person) {
		return jdbcTemplate.update("update person set name =?, location =?, birth_date = ? where id = ?",
				new Object[] {person.getName(),person.getLocation(),person.getBirthDate(), person.getId()}
		);
	}
	
	public Person findById(int id) {
		return jdbcTemplate.queryForObject("select * from person where id =?",
				new Object[] {id},
				new BeanPropertyRowMapper<Person>(Person.class)
		);
	}
	
	/*
	 * custom row mapper
	 * 
	 * 
	 * */
	
	private class PersonRowMapper implements org.springframework.jdbc.core.RowMapper<Person>{

		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person person = new Person();
			person.setId(rs.getInt("id"));
			person.setName(rs.getString("name"));
			person.setLocation(rs.getString("location"));
			person.setBirthDate(rs.getTimestamp("birth_date"));
			
			return person;
		}
		
	}
}
