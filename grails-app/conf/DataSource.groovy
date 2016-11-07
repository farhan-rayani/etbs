import com.flydubai.etbs.util.DESCodec;

dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
	passwordEncryptionCodec = DESCodec
    dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
//    cache.region.factory_class = 'org.hibernate.cache.SingletonEhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
    flush.mode = 'manual' // OSIV session flush mode outside of transactional context
}

// environment specific settings
environments {
    development {
        dataSource {
		  //jndiName = "java:comp/env/etbsDatasource"
			url = 'jdbc:mysql://localhost:3306/etbs_new'
			username = 'root'
			password= 'j3sUZ+Mi1Zo='
			
		}
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
	
	uat {
		dataSource {
		   url = 'jdbc:mysql://10.1.17.13:3306/etbs_new'
		   driverClassName = 'com.mysql.jdbc.Driver'
		   username = 'root'
		   password= 'j3sUZ+Mi1Zo='
		   properties {
			  validationQuery = "SELECT 1"
			  testOnBorrow = true
			  testWhileIdle = true
			  testOnReturn = false
			  maxActive = 50
			  maxIdle = 25
			  minIdle = 5
			  initialSize = 5
			  minEvictableIdleTimeMillis = 1800000
			  timeBetweenEvictionRunsMillis = 1800000
			  maxWait = 10000
		   }
		}
	}
	
    production {
        dataSource {
		    url = 'jdbc:mysql://10.1.10.171:3309/etbs_new'
		   driverClassName = 'com.mysql.jdbc.Driver'
		   username = 'etbsproduser'
		   password = 'aiy62sOOsWyixQ2rKg19lA=='
		   properties {
			  validationQuery = "SELECT 1"
			  testOnBorrow = true
			  testWhileIdle = true
			  testOnReturn = false
			  maxActive = 50
			  maxIdle = 25
			  minIdle = 5
			  initialSize = 5
			  minEvictableIdleTimeMillis = 1800000
			  timeBetweenEvictionRunsMillis = 1800000
			  maxWait = 10000
		   }
		}
    }
}
