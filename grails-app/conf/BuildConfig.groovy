grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
		mavenCentral()
        grailsCentral()
       
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
		mavenRepo "http://repo.grails.org/grails/core"
		//mavenRepo "http://maven.nuxeo.org/nexus/content/groups/public"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        // runtime 'mysql:mysql-connector-java:5.1.29'
        // runtime 'org.postgresql:postgresql:9.3-1101-jdbc41'
        test "org.grails:grails-datastore-test-support:1.0.2-grails-2.4"
		runtime 'mysql:mysql-connector-java:5.1.33'		
		runtime 'commons-beanutils:commons-beanutils:1.8.3'
		runtime 'javax.mail:mail:1.4.7'
		runtime 'org.glassfish.jersey.ext:jersey-spring3:2.19'
		runtime 'org.glassfish.jersey.containers:jersey-container-servlet:2.19'
        runtime 'org.glassfish.jersey.media:jersey-media-json-jackson:2.19'
		runtime 'org.glassfish.jersey.media:jersey-media-moxy:2.19'
        runtime 'javax.ws.rs:javax.ws.rs-api:2.0'
        runtime 'org.apache.velocity:velocity:1.7'
        runtime 'org.mockito:mockito-core:1.10.16'
        runtime 'commons-configuration:commons-configuration:1.10'
		runtime 'com.sun.jersey:jersey-core:1.19'
		runtime 'com.sun.jersey:jersey-client:1.19'
		runtime 'com.sun.jersey.contribs:jersey-apache-client4:1.19'		
		runtime 'org.glassfish.hk2:hk2-api:2.4.0-b27'
		runtime 'org.glassfish.hk2:hk2-locator:2.4.0-b27'
        runtime 'org.glassfish.hk2:hk2-utils:2.4.0-b27'
        runtime 'javax.inject:javax.inject:1'
        runtime 'com.google.guava:guava:18.0'
		runtime 'com.itextpdf:itextpdf:5.0.6'
		runtime 'com.netflix.archaius:archaius-core:0.6.6'
        runtime 'com.netflix.netflix-commons:netflix-commons-util:0.1.1'
		runtime 'com.netflix.netflix-commons:netflix-statistics:0.1.1'
		runtime 'org.apache.httpcomponents:httpcore:4.4.1'
        runtime 'org.apache.httpcomponents:httpclient:4.5'
		runtime 'com.netflix.servo:servo-internal:0.9.2'
        runtime 'com.netflix.servo:servo-core:0.9.2'
		runtime 'io.reactivex:rxjava:1.0.9'
		runtime 'com.netflix.ribbon:ribbon-loadbalancer:2.1.0'
        runtime 'com.netflix.ribbon:ribbon-core:2.1.0'
		runtime 'com.netflix.ribbon:ribbon-httpclient:2.1.0'
		

		
	
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.55.2" // or ":tomcat:8.0.20"

        // plugins for the compile step
        compile ":scaffolding:2.1.2"
        compile ':cache:1.1.8'
        compile ":asset-pipeline:2.1.5"
		compile ":spring-security-core:2.0-RC4" 
		
		compile ":spring-security-ldap:2.0-RC4"
		
		//compile ":spring-security-ui:1.0-RC2"
		//compile ":acegi:0.5.3.2"
        // plugins needed at runtime but not for compilation
        runtime ":hibernate4:4.3.8.1" // or ":hibernate:3.6.10.18"
        runtime ":database-migration:1.4.0"
        runtime ":jquery:1.11.1"
		compile ":mail:1.0.7"
		compile ":calendar:1.2.1"
		compile ':quartz:1.0.1'
		compile ":remote-pagination:0.4.8"
		compile ":phonenumbers:0.10"
		compile ":audit-logging:1.0.5"
		//runtime ':db-reverse-engineer:0.5.1'
		
        // Uncomment these to enable additional asset-pipeline capabilities
        //compile ":sass-asset-pipeline:1.9.0"
        //compile ":less-asset-pipeline:1.10.0"
        //compile ":coffee-asset-pipeline:1.8.0"
        //compile ":handlebars-asset-pipeline:1.3.0.3"
		compile "org.grails.plugins:spring-mobile:1.1.3"
    }
}
