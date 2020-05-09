package com.web.sys;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.web.sys.utils.T;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
//@EnableSwagger2             //启动swagger注解
//@ComponentScan("com.web.sys.mapper")
//@EnableSwagger2Doc
@MapperScan("com.web.sys.dao")
public class SysApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysApplication.class, args);
    }


    /**
     * 处理时间格式
     * @return
     */
    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                String[] formats = new String[]{ "yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH","yyyy-MM-dd HH:mm", "yyyy-MM-dd",};
                Date date = null;
                for(String format:formats){
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        date = sdf.parse((String) source);
                        break;
                    } catch (Exception e) {
                        T.print(source,"ERR",format);
                    }
                }
                T.print(source,date);
              return date;
            }
        };

    }

}
