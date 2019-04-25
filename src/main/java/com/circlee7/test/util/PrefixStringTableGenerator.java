package com.circlee7.test.util;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class PrefixStringTableGenerator extends TableGenerator{

	
	
	private final String KEY_PREFIX = "key_prefix";
	private final int KEY_NUMBER_LENGTH = 4;
	
	private String keyPrefixValue;
	
	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		
		keyPrefixValue = ConfigurationHelper.getString(KEY_PREFIX, params, "");
		
		super.configure(type, params, serviceRegistry);
	}
	
	@Override
	public Serializable generate(final SharedSessionContractImplementor session, final Object obj) {
		
		Long longId = (Long)super.generate(session, obj);
		
		int longIdLength = longId.toString().length();
		longIdLength  = longIdLength > KEY_NUMBER_LENGTH ? longIdLength : KEY_NUMBER_LENGTH;
		
		return String.format(keyPrefixValue + "%0"+longIdLength+"d", longId);
	}
	
}
