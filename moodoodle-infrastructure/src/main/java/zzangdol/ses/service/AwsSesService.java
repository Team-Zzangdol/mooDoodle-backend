package zzangdol.ses.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

@Slf4j
@Component
@AllArgsConstructor
public class AwsSesService {

    private final SesClient sesClient;
    private final SpringTemplateEngine templateEngine;

    public void sendVerificationEmail(String to, String verificationCode) {
        Context context = new Context();
        context.setVariable("verificationCode", verificationCode);
        String htmlBody = templateEngine.process("verificationEmail", context);

        SendEmailRequest request = SendEmailRequest.builder()
                .destination(Destination.builder().toAddresses(to).build())
                .message(Message.builder()
                        .subject(Content.builder().data("[무두들] 회원가입을 위해 메일을 인증해 주세요.").build())
                        .body(Body.builder().html(Content.builder().data(htmlBody).build()).build())
                        .build())
                .source("무두들 <no-reply@moodoodle.site>")
                .build();

        sesClient.sendEmail(request);
    }


}
