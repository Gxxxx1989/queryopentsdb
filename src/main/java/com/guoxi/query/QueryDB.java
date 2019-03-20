package com.guoxi.query;

import com.aliyun.hitsdb.client.HiTSDB;
import com.aliyun.hitsdb.client.HiTSDBClientFactory;
import com.aliyun.hitsdb.client.HiTSDBConfig;
import com.aliyun.hitsdb.client.value.request.Query;
import com.aliyun.hitsdb.client.value.request.SubQuery;
import com.aliyun.hitsdb.client.value.response.QueryResult;
import com.aliyun.hitsdb.client.value.type.Aggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Component
public class QueryDB {

    @Value("${opentsdb.url}")
    private String url;

    @Value("${opentsdb.port}")
    private int port;

    @Scheduled(fixedRate = 6000)
    public void  Query() throws InterruptedException, IOException {

        // 创建 HiTSDB 对象
        HiTSDBConfig config = HiTSDBConfig.address("192.168.155.44", 4242).config();
        HiTSDB tsdb = HiTSDBClientFactory.connect(config);

       /* // 安全关闭客户端，以防数据丢失。
        System.out.println("关闭");
        tsdb.close();*/

        /*HiTSDBConfig config = HiTSDBConfig.address(url, port).asyncPut(false).addAddress(url,port).config(); // 设置回调接口
        HiTSDB tsdb = HiTSDBClientFactory.connect(config);*/

        long startTime=System.currentTimeMillis();
        long endTime=System.currentTimeMillis()+(1000*10);

        Query query = Query
                .timeRange(startTime, endTime)    // 设置查询时间条件
                .sub(SubQuery.metric("temperature").aggregator(Aggregator.AVG).tag("producer_code", "ijinus").build())    // 设置子查询
                .sub(SubQuery.metric("water_height").aggregator(Aggregator.SUM).tag("producer_code", "huilin").build())    // 设置子查询
                .build();

       /* SubQuery subQuery = SubQuery
                .metric("test-metric")
                .aggregator(Aggregator.AVG)
                .downsample("60m-avg")
                .tag("tagk1", "tagv1")
                .tag("tagk2", "tagv2")
                .build();*/
        List<QueryResult> result=tsdb.query(query);
        System.out.println("返回结果：" + result);

    }



}
