package com.riozenc.api.support.transaction;

import com.riozenc.titanTool.spring.transaction.registry.TransactionDAORegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionDAOSupport extends TransactionDAORegistryPostProcessor {

}
