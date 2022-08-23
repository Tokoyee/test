package com.aiit.scheduling;

import com.aiit.entity.Subscription;
import com.aiit.service.CourseService;
import com.aiit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class MsgScheduling {
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @PostConstruct
    @Scheduled(cron = "0 0/1 * * * ?")
    public void nowTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String now_date = formatter.format(date).split(" ")[0];
        String now_time = formatter.format(date).split(" ")[1];
        int minutes = Integer.parseInt(now_time.split(":")[0]) * 60 + Integer.parseInt(now_time.split(":")[1]);
        List<Subscription> subscriptions =  userService.getTodaySubscription(now_date);
        for(int i = 0;i<subscriptions.size();i++){
            if (subscriptions.get(i).getRemindTime() == minutes){
                String touser = subscriptions.get(i).getTouser();
                String subscriptionId = subscriptions.get(i).getSubscriptionId();
                userService.sendMassage(touser,subscriptionId);
                log.info("通知发送成功");
            }
        }

    }
}
