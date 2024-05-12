package com.crud.tasks.scheduler;

import com.crud.tasks.trello.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.trello.client.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private static final String SUBJECT = "Tasks: Once a day email";
    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;

    @Scheduled(cron = "0 0 23 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String taskMessage = "Currently in database you got: " + size + " task";
        if (size != 1) {
            taskMessage += "s";
        }
        simpleEmailService.send(
                new Mail(
                        adminConfig.getAdminMail(),
                        SUBJECT,
                        taskMessage,
                        null
                )
        );
    }
}