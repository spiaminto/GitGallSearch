package kr.granblue.gbfsearch.config;

import kr.granblue.gbfsearch.aop.LogTraceAspect;
import kr.granblue.gbfsearch.log.trace.LogTrace;
import kr.granblue.gbfsearch.log.trace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    // LogTrace 구현체 등록
    @Bean
    public LogTrace logTrace() { return new ThreadLocalLogTrace(); }

    // @Aspect 등록
    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }

}
