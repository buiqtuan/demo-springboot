package com.tawk.fun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawk.fun.adapter.LoadChatDataAdapter;
import com.tawk.fun.config.BeanContainer;
import com.tawk.fun.entity.ChatInfo;
import com.tawk.fun.service.DataFilteringService;
import com.tawk.fun.usecase.GetDataFromCache;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(value = SpringRunner.class)
@Suite.SuiteClasses(DataFilteringService.class)
@SpringBootTest(classes = BeanContainer.class)
public class DataFilteringServiceTest {

    private DataFilteringService dataFilteringService;

    @MockBean
    private GetDataFromCache getDataFromCache;

    private RestTemplate restTemplate = (new RestTemplateBuilder())
            .setConnectTimeout(Duration.ofMillis(10000))
            .build();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${tawk.web.chat.log.url}")
    private String sourceUrl;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private String fromString = "2019-04-02T00:00:00.000Z";

    private String toString = "2019-04-03T00:00:00.000Z";

    DateTimeFormatter ISO_OFFSET_DATE_TIME = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private OffsetDateTime fromDate;

    private OffsetDateTime toDate;

    private List<ChatInfo> fullData;

    @Before
    public void beforeTest() {
        this.fromDate = OffsetDateTime.parse(fromString, ISO_OFFSET_DATE_TIME);
        this.toDate = OffsetDateTime.parse(toString, ISO_OFFSET_DATE_TIME);

        this.dataFilteringService = new DataFilteringService(
            this.getDataFromCache,
            this.dateTimeFormatter
        );

        var loadChatAdapter = new LoadChatDataAdapter(
            this.restTemplate,
            this.objectMapper,
            this.sourceUrl
        );

        this.fullData = loadChatAdapter.loadAll();
    }

    @Test
    public void fromDateToDate_Test() {
        var chatInfoList = this.dataFilteringService.fromDateToDate(this.fullData, fromDate, toDate);

        Assert.assertNotNull(chatInfoList);
        Assert.assertTrue(chatInfoList.size() > 0);
        Assert.assertTrue(chatInfoList.get(0).getDate(this.dateTimeFormatter).compareTo(toDate) <= 0);
        Assert.assertTrue(chatInfoList.get(0).getDate(this.dateTimeFormatter).compareTo(fromDate) >= 0);
    }

    @Test
    public void fromDate_Test() {
        var chatInfoList = this.dataFilteringService.fromDate(this.fullData, fromDate);

        Assert.assertNotNull(chatInfoList);
        Assert.assertTrue(chatInfoList.size() > 0);
        Assert.assertTrue(chatInfoList.get(0).getDate(this.dateTimeFormatter).compareTo(fromDate) >= 0);
    }

    @Test
    public void toDate_Test() {
        var chatInfoList = this.dataFilteringService.toDate(this.fullData, toDate);

        Assert.assertNotNull(chatInfoList);
        Assert.assertTrue(chatInfoList.size() > 0);
        Assert.assertTrue(chatInfoList.get(0).getDate(this.dateTimeFormatter).compareTo(toDate) <= 0);
    }
}
