[when]申请人的年龄小于 {age} 岁=applicant:Applicant( age < {age} )
[when]申请人的年龄大于 {age} 岁=applicant:Applicant( age > {age} )
[when]有一个贷款申请=loan:Loan()
[then]申请人不符合贷款资格=modify( applicant ) \{ setEligible( false )\}
[then]贷款被拒绝, 原因是: "{message}"=modify( loan ) \{setApproved(false), setComment( "{message}" )\}
[then]日志输出规则名称=System.out.println("Rule fired : [" + drools.getRule().getName()+"]");
