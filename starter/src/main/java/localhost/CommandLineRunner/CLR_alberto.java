package localhost.CommandLineRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import localhost.Async.AsyncHelper;

@Component
public class CLR_alberto implements CommandLineRunner{
	
	private static Logger log = LoggerFactory.getLogger(CLR_alberto.class);
	
    @Autowired
    AsyncHelper asyncHelper;
	
	@Value("${spring.application.name}")
    private String springApplicationName;
	
    @Value("${project.name}")
    private String projectName;

    @Value("${project.version}")
    private String projectVersion;
    
    @Value("${project.description}")
    private String projectDescription;
    
    @Value("${project.versionDate}")
    private String projectVersionDate;

    @Value("${project.versionComment}")
    private String projectVersionComment;
	
	@Override
	public void run(String... args) throws Exception {
	    log.info("run begin");
	    log.info("spring.application.name: {} -- project.name: {} -- project.version: {} -- project.description: {} -- project.versionDate: {} -- project.versionComment: {}", springApplicationName, projectName, projectVersion, projectDescription, projectVersionDate, projectVersionComment);

	    // main01(args);
	    main02Async();

	    log.info("run end");
	}

	public void main01(String[] args) {
		log.info("hello world Runnable Proccess!");
		log.info("spring.application.name: {}", springApplicationName);
		
		String argsStr = "'";
		int i = 0;
		
		for (i=0 ; i<args.length ; i++) {
			argsStr = argsStr + args[i] + "' ; '";
		}
		
		argsStr = argsStr.substring(0, argsStr.length() - 1);
		
		log.info("args: " +  args);
		log.info("argsStr: " +  argsStr);

	}
	
	public void main02Async () {
	    asyncHelper.asyncMain01();
	    asyncHelper.asyncMain02();
	}

}
