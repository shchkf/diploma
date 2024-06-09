package org.manager.managers;

import org.manager.dao.RulesDao;
import org.manager.models.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RulesManager {
    @Autowired
    private RulesDao rulesDao;

    public Rule createRule(Rule rule) {
        // Check if the Rule already exists
        Rule existingRule = rulesDao.findByNameAndResgroup(rule.getName(), rule.getResgroup());
        if (existingRule != null) {
            // If the Rule already exists, return the existing Rule
            return existingRule;
        }

        // If the Rule doesn't exist, create a new one and save it
        return rulesDao.create(rule);
    }

    public List<Rule> getAllRules(String nameFilter, String descriptionFilter, Long resgroupId) {
        // Construct the filter criteria
        Specification<Rule> specification = Specification.where(null);

        // Apply the name filter if provided
        if (StringUtils.isNotBlank(nameFilter)) {
            specification = specification.and(RuleSpecifications.nameContains(nameFilter));
        }

        // Apply the description filter if provided
        if (StringUtils.isNotBlank(descriptionFilter)) {
            specification = specification.and(RuleSpecifications.descriptionContains(descriptionFilter));
        }

        // Apply the Resgroup filter if provided
        if (resgroupId != null) {
            specification = specification.and(RuleSpecifications.resgroupEquals(resgroupId));
        }

        // Fetch all Rules that match the filter criteria
        return rulesDao.findAll(specification);
    }

    public Rule getRuleById(Long id) {
        return rulesDao.findById(id);
    }

    public Rule updateRule(Long id, Rule rule) {
        // Find the existing Rule
        Rule existingRule = rulesDao.findById(id);
        if (existingRule == null) {
            // If the Rule doesn't exist, return null
            return null;
        }

        // Check if the Rule has been modified
        boolean hasChanges = false;
        if (!existingRule.getName().equals(rule.getName())) {
            existingRule.setName(rule.getName());
            hasChanges = true;
        }
        if (!existingRule.getDescription().equals(rule.getDescription())) {
            existingRule.setDescription(rule.getDescription());
            hasChanges = true;
        }
        if (!existingRule.getCondition().equals(rule.getCondition())) {
            existingRule.setCondition(rule.getCondition());
            hasChanges = true;
        }
        if (!existingRule.getAction().equals(rule.getAction())) {
            existingRule.setAction(rule.getAction());
            hasChanges = true;
        }

        // If the Rule has been modified, update it in the database
        if (hasChanges) {
            return rulesDao.update(existingRule);
        } else {
            // If the Rule hasn't been modified, return the existing Rule
            return existingRule;
        }
    }

}