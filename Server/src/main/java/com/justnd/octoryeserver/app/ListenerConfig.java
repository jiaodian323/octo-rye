/**   
* @Title: ListenerConfig.java 
* @Package com.justnd.octoryeserver.app 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年3月16日 上午11:42:57  
*/
package com.justnd.octoryeserver.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** 
* @ClassName: ListenerConfig 
* @Description: TODO
* @author JD
* @date 2019年3月16日 上午11:42:57 
*  
*/
@Configuration
public class ListenerConfig {

    @Bean
    public ApplicationStartListener applicationStartListener(){
        return new ApplicationStartListener();
    }
}