package com.riozenc.api.support.transaction;

import com.riozenc.titanTool.spring.transaction.registry.TransactionServiceRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionServiceSupport extends TransactionServiceRegistryPostProcessor {

}