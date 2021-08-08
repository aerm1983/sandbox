package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import com.google.gson.JsonSyntaxException;
import java.lang.IllegalStateException;
import java.sql.ResultSet;


@RestController
@RequestMapping("")
public class Controller {

	private static final Logger log =  LoggerFactory.getLogger(Controller.class);
	private static Gson gson = new Gson();
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	
	@GetMapping(
			path="/" 
			// consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			// produces = { MediaType.APPLICATION_JSON_VALUE } 
			)
	public String Controller01(
			@RequestHeader Map<String, String> reqHeaders 
			// @RequestBody String reqBodyString
			) {
		
		log.info("................ INICIO_TEST_STORED_PROCEDURE ................");
		
		
		jdbcTemplate.execute("alter session set current_schema = NOVO03003");
		
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				// .withCatalogName("")
				.withSchemaName("NOVO03003")
                .withProcedureName("SELECT_VTIS_TOKEN_TYPE")
                .returningResultSet("REFCURSOR_OUT", BeanPropertyRowMapper.newInstance(TokenType.class));
		
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("NAME_IN", "alberto");
		
		Map<String,?> out = simpleJdbcCall.execute(in);

		String respBodyStr = "";
		
		if (out != null) {
			List<TokenType> listTokenType = (List) out.get("REFCURSOR_OUT");
			TokenType tokenType = null;
			
			for ( int i = 0 ; i < listTokenType.size() ; i++ ) {
				tokenType = listTokenType.get(i);
				respBodyStr += tokenType.getClass() + "\n";
				respBodyStr += tokenType.toString() + "\n";
				respBodyStr += tokenType.getTOKEN_TYPE_ID() + "\n";
				respBodyStr += tokenType.getTOKEN_TYPE() + "\n";
				respBodyStr += "\n";
			}
		}
		
		return respBodyStr;

	}
	
	
	
	
	
	
	@GetMapping(
			path="/2" 
			// consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			// produces = { MediaType.APPLICATION_JSON_VALUE } 
			)
	public String Controller02(
			@RequestHeader Map<String, String> reqHeaders 
			// @RequestBody String reqBodyString
			) {
		
		log.info("................ INICIO_TEST_STORED_PROCEDURE ................");
		
		
		// jdbcTemplate.execute("alter session set current_schema = NOVO03003");
		
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				// .withCatalogName("")
				.withSchemaName("NOVO03003")
                .withProcedureName("GENERAL_GET")
                .returningResultSet("TABLE_DATA", BeanPropertyRowMapper.newInstance(TokenType.class));
		
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("TABLE_NAME", "VTIS_TOKEN_TYPE")
				.addValue("TABLE_PROPERTIE", "TOKEN_TYPE_ID, TOKEN_TYPE")
				.addValue("COLUMN_WHERE", null)
				.addValue("COLUMN_VALUES", null);
		
		Map<String,Object> out = simpleJdbcCall.execute(in);

		String respBodyStr = "";
		
		if (out != null) {
			
			respBodyStr = "TABLE_NAME: " + out.get("TABLE_NAME").toString() + "\n\n";
			
			List<TokenType> listTokenType = (List) out.get("TABLE_DATA");
			TokenType tokenType = null;
			
			for ( int i = 0 ; i < listTokenType.size() ; i++ ) {
				tokenType = listTokenType.get(i);
				respBodyStr += tokenType.getClass() + "\n";
				respBodyStr += tokenType.toString() + "\n";
				respBodyStr += tokenType.getTOKEN_TYPE_ID() + "\n";
				respBodyStr += tokenType.getTOKEN_TYPE() + "\n";
				respBodyStr += "\n";
			}
		}
		
		return respBodyStr;

	}
	
	
}
