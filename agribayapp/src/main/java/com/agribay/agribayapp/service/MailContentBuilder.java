package com.agribay.agribayapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@AllArgsConstructor
@Slf4j
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    public String build(String message) {
        Context context = new Context();
        log.info("Mail template generated and content is build using MailContentBuilder() class");
        context.setVariable("message", message);       // here we set the email message inside the thymleaf context object by using setVariable of context
        return templateEngine.process("mailTemplate", context);   // here we passing the html file name and the context to the templateEngine 
    }
}