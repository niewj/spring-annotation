package com.niewj.condition;

import com.niewj.ConstUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * profile=product 时满足条件
 */
public class ConditionalProduct implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String profile = context.getEnvironment().getProperty(ConstUtil.PROFILES_ACTIVE);
        if (ConstUtil.PRODUCT.equalsIgnoreCase(profile)) {
            return true;
        }
        return false;
    }
}