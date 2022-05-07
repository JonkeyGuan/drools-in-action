[when]有一个客户, 如果它的积分大于或等于 {lowerPoints} 并且小于 {upperPoint} = c:Customer(points > {lowerPoints}, points <= {upperPoint})
[when]有一个客户, 如果它的积分大于或等于 {lowerPoints} = c:Customer(points > {lowerPoints})
[then]这个客户可以兑换的最高的礼品是 {giftName:ENUM:Gift.name} = Gift g = new Gift(); g.setName("{giftName}"); g.setCustomer(c.name); insert(g)
