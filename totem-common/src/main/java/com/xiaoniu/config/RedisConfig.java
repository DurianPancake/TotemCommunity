package com.xiaoniu.config;

import com.xiaoniu.factory.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.*;

/**
 * @Author: LLH
 * @Date: 2019/6/11 15:54
 */
@Configuration
public class RedisConfig {

    /**
     * 使用yml文件配置
     */
    @Autowired
    private YamlPropertySourceFactory ymlFactory;

    private static final String CLASS_PATH = "properties/jedis.yml";

    /**
     * 从配置文件中读取并创建Redis集群对象
     * @return
     * @throws IOException
     */
    @Bean
    public JedisCluster newJedisCluster() throws IOException {
        EncodedResource resource = new EncodedResource(new ClassPathResource(CLASS_PATH));
        Properties properties = ymlFactory.loadYamlIntoProperties(resource);
        Set<Object> keySet = properties.keySet();
        List<JedisDev> devs = getJedisFromKeySet(keySet, properties);
        Set<HostAndPort> nodes = new HashSet<>();
        for (JedisDev dev : devs) {
            nodes.addAll(dev.getHostAndPorts());
        }
        return new JedisCluster(nodes);
    }

    /** 获取节点配置信息 */
    private List<JedisDev> getJedisFromKeySet(Set<Object> keySet, Properties properties) {
        Set<String> devNames = new HashSet<>();
        List<JedisDev> targetList = new ArrayList<>();
        for (Object key : keySet) {
            String key0 = ((String) key);
            if (key0.matches("^jedis\\..+\\.(host|ports)$")) {
                devNames.add(key0.substring(0, key0.lastIndexOf(".")));
            }
        }
        for (String dev : devNames) {
            targetList.add(new JedisDev(properties.getProperty(dev + ".host"), properties.getProperty(dev + ".ports")));
        }
        return targetList;
    }

    /** 内部类：封装了Jedis的设备Host和port */
    private class JedisDev {
        public String host;
        public String[] ports;

        public JedisDev(String host, String postStr) {
            this.host = host;
            this.ports = postStr.split(",");
        }

        public List<HostAndPort> getHostAndPorts() {
            List<HostAndPort> list = new ArrayList<>();
            for (String port : ports) {
                list.add(new HostAndPort(host, Integer.valueOf(port)));
            }
            return list;
        }
    }
}
