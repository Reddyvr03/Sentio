package net.engineeringdigest.journalApp.scheduler;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.Service.EmailService;
import net.engineeringdigest.journalApp.Service.SentimentAnalysisService;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

//    @Scheduled(cron = "0 0/1 * 1/1 * *")
    public void fetchUsersAndSaMail(){
        List<User> users = userRepositoryImpl.getUsersForSA();
        for (User user:users){
            List<JournalEntity> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x ->x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment,Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment:sentiments){
                if (sentiment != null) {
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }
             Sentiment mostFrequesntSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment,Integer>entry:sentimentCounts.entrySet()){
                if (entry.getValue()>maxCount){
                    maxCount =entry.getValue();
                    mostFrequesntSentiment = entry.getKey();
                }
            }
            if (mostFrequesntSentiment != null) {
                emailService.sendEmail(user.getEmail(),"sentiment for lat 7 days",mostFrequesntSentiment.toString());
            }
            log.info("Mail Sent");
        }
    }

    @Scheduled(cron = "0 0/5 * 1/1 * *")
    public void clearAppCache(){
        appCache.init();
    }
}
